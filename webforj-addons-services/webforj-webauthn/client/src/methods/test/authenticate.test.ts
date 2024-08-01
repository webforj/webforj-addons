import { authenticate } from '../authenticate';
import { browserSupportsWebAuthn, browserSupportsWebAuthnAutofill } from '../../utils/browser-supports';
import { base64URLStringToBuffer, bufferToBase64URLString, bufferToUTF8String } from '../../utils/communications';
import { identifyAuthenticationError } from '../../utils/error';
import { webAuthnAbort } from '../../utils/webauthn-abort';
import { toAuthenticatorAttachment } from '../../utils/transformers';
import { AuthenticationCredential, AuthenticationResponseJSON, PublicKeyCredentialRequestOptionsJSON } from '../../types/index';

jest.mock('../../utils/browser-supports');
jest.mock('../../utils/communications');
jest.mock('../../utils/error');
jest.mock('../../utils/webauthn-abort');
jest.mock('../../utils/transformers');

describe('authenticate', () => {
  beforeEach(() => {
    jest.resetAllMocks();
  });

  it('throws an error if WebAuthn is not supported in the browser', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(false);

    const requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON = {
      challenge: 'challenge',
    };

    await expect(authenticate(requestOptionsJSON)).rejects.toThrow('WebAuthn is not supported in this browser');
  });

  it('throws an error if WebAuthn autofill is not supported in the browser', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (browserSupportsWebAuthnAutofill as jest.Mock).mockResolvedValue(false);

    const requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON = {
      challenge: 'challenge',
    };

    await expect(authenticate(requestOptionsJSON, true)).rejects.toThrow('Browser does not support WebAuthn autofill');
  });

  it('throws an error if no eligible input for WebAuthn autofill is found', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (browserSupportsWebAuthnAutofill as jest.Mock).mockResolvedValue(true);
    document.querySelectorAll = jest.fn().mockReturnValue([]);

    const requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON = {
      challenge: 'challenge',
    };

    await expect(authenticate(requestOptionsJSON, true)).rejects.toThrow(
      'No <input> with "webauthn" as the only or last value in its `autocomplete` attribute was detected'
    );
  });

  it('successfully completes authentication', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (base64URLStringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'base64'));
    (bufferToBase64URLString as jest.Mock).mockImplementation(buffer => Buffer.from(buffer).toString('base64url'));
    (bufferToUTF8String as jest.Mock).mockImplementation(buffer => new TextDecoder().decode(new Uint8Array(buffer)));
    (webAuthnAbort.createAbortSignal as jest.Mock).mockReturnValue(new AbortController().signal);
    (toAuthenticatorAttachment as jest.Mock).mockReturnValue('platform');

    const requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON = {
      challenge: 'challenge',
    };

    const mockCredential: AuthenticationCredential = {
      id: 'credential-id',
      rawId: new Uint8Array([0, 1, 2, 3]).buffer,
      response: {
        authenticatorData: new Uint8Array([4, 5, 6, 7]).buffer,
        clientDataJSON: new Uint8Array([8, 9, 10, 11]).buffer,
        signature: new Uint8Array([12, 13, 14, 15]).buffer,
        userHandle: new Uint8Array([16, 17, 18, 19]).buffer,
      },
      type: 'public-key',
      authenticatorAttachment: 'platform',
      getClientExtensionResults: jest.fn().mockReturnValue({}),
    };

    (navigator.credentials.get as jest.Mock).mockResolvedValue(mockCredential);

    const expectedResponse: AuthenticationResponseJSON = {
      id: 'credential-id',
      rawId: 'AAECAw',
      response: {
        authenticatorData: 'BAUGBw',
        clientDataJSON: 'CAkKCw',
        signature: 'DA0ODw',
        userHandle: '\u0010\u0011\u0012\u0013',
      },
      type: 'public-key',
      clientExtensionResults: {},
      authenticatorAttachment: 'platform',
    };

    const result = await authenticate(requestOptionsJSON);
    expect(result).toEqual(expectedResponse);
  });

  it('handles authentication error', async () => {
    (browserSupportsWebAuthn as jest.Mock).mockReturnValue(true);
    (base64URLStringToBuffer as jest.Mock).mockImplementation(value => Buffer.from(value, 'base64'));
    (bufferToBase64URLString as jest.Mock).mockImplementation(buffer => Buffer.from(buffer).toString('base64url'));
    (webAuthnAbort.createAbortSignal as jest.Mock).mockReturnValue(new AbortController().signal);

    const requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON = {
      challenge: 'challenge',
    };

    const mockError = new Error('Test error');
    (navigator.credentials.get as jest.Mock).mockRejectedValue(mockError);
    (identifyAuthenticationError as jest.Mock).mockReturnValue(mockError);

    await expect(authenticate(requestOptionsJSON)).rejects.toThrow('Test error');
  });
});
