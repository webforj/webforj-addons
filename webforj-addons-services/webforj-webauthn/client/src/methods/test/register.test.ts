import { register } from '../register';
import { browserSupportsWebAuthn, safeBrowserApiCall } from '../../utils/browser-supports';
import { base64URLStringToBuffer, utf8StringToBuffer, bufferToBase64URLString } from '../../utils/communications';
import { webAuthnAbort } from '../../utils/webauthn-abort';
import { identifyRegistrationError } from '../../utils/error';
import { RegistrationResponseJSON, PublicKeyCredentialCreationOptionsJSON, RegistrationCredential } from '../../types/index';

jest.mock('../../utils/browser-supports');
jest.mock('../../utils/communications');
jest.mock('../../utils/webauthn-abort');
jest.mock('../../utils/error');

describe('register', () => {
  beforeEach(() => {
    jest.resetAllMocks();
  });

  it('throws an error if WebAuthn is not supported in the browser', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(false);

    const creationOptions: PublicKeyCredentialCreationOptionsJSON = {
      rp: { name: 'Example RP' },
      user: { id: '123', name: 'username', displayName: 'User Name' },
      challenge: 'challenge',
      pubKeyCredParams: [{ alg: -7, type: 'public-key' }],
    };

    await expect(register(creationOptions)).rejects.toThrow('WebAuthn is not supported in this browser');
  });

  it('successfully completes registration', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (base64URLStringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'base64'));
    (bufferToBase64URLString as jest.Mock).mockImplementation(buffer => Buffer.from(buffer).toString('base64url'));
    (utf8StringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'utf-8'));
    (webAuthnAbort.createAbortSignal as jest.Mock).mockReturnValue(new AbortController().signal);
    (safeBrowserApiCall as jest.Mock).mockImplementation((fn) => fn());

    const creationOptions: PublicKeyCredentialCreationOptionsJSON = {
      rp: { name: 'Example RP' },
      user: { id: '123', name: 'username', displayName: 'User Name' },
      challenge: 'challenge',
      pubKeyCredParams: [{ alg: -7, type: 'public-key' }],
    };

    const mockCredential: RegistrationCredential = {
      id: 'credential-id',
      rawId: new Uint8Array([0, 1, 2, 3]).buffer,
      response: {
        attestationObject: new Uint8Array([4, 5, 6, 7]).buffer,
        clientDataJSON: new Uint8Array([8, 9, 10, 11]).buffer,
        getTransports: jest.fn().mockReturnValue(['usb']),
        getPublicKey: jest.fn().mockReturnValue(new Uint8Array([12, 13, 14, 15]).buffer),
        getAuthenticatorData: jest.fn().mockReturnValue(new Uint8Array([16, 17, 18, 19]).buffer),
        getPublicKeyAlgorithm: jest.fn().mockReturnValue(42),
      },
      type: 'public-key',
      authenticatorAttachment: 'platform',
      getClientExtensionResults: jest.fn().mockReturnValue({}),
    };

    (navigator.credentials.create as jest.Mock).mockResolvedValue(mockCredential);

    const expectedResponse: RegistrationResponseJSON = {
      id: 'credential-id',
      rawId: 'AAECAw',
      response: {
        attestationObject: 'BAUGBw',
        clientDataJSON: 'CAkKCw',
        transports: ['usb'],
        publicKey: 'DA0ODw',
        authenticatorData: 'EBESEw',
        publicKeyAlgorithm: 42,
      },
      type: 'public-key',
      clientExtensionResults: {},
      authenticatorAttachment: 'platform',
    };

    const result = await register(creationOptions);
    expect(result).toEqual(expectedResponse);
  });

  it('handles registration error', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (base64URLStringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'base64'));
    (utf8StringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'utf-8'));
    (webAuthnAbort.createAbortSignal as jest.Mock).mockReturnValue(new AbortController().signal);

    const creationOptions: PublicKeyCredentialCreationOptionsJSON = {
      rp: { name: 'Example RP' },
      user: { id: '123', name: 'username', displayName: 'User Name' },
      challenge: 'challenge',
      pubKeyCredParams: [{ alg: -7, type: 'public-key' }],
    };

    const mockError = new Error('Test error');
    (navigator.credentials.create as jest.Mock).mockRejectedValue(mockError);
    (identifyRegistrationError as jest.Mock).mockReturnValue(mockError);

    await expect(register(creationOptions)).rejects.toThrow('Test error');
  });
});
