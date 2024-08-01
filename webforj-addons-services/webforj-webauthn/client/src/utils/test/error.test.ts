import { ErrorNames, WebAuthnErrorCode } from "../../constants/error";
import { identifyRegistrationError, identifyAuthenticationError, WebAuthnError } from "../../utils/error";
import { isValidDomain } from '../../utils/validation';

jest.mock('../../utils/validation', () => ({
  isValidDomain: jest.fn(),
}));

describe("errors", () => {
  describe('WebAuthnError', () => {
    it('should create a WebAuthnError instance with all properties', () => {
      const errorMessage = 'WebAuthn operation failed';
      const errorCode = WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR;
      const causeError = new Error('Underlying cause of WebAuthn failure');
      
      const webAuthnError = new WebAuthnError({
        message: errorMessage,
        code: errorCode,
        cause: causeError,
        name: 'WebAuthnError',
      });
  
      expect(webAuthnError instanceof Error).toBeTruthy();
      expect(webAuthnError instanceof WebAuthnError).toBeTruthy();
      expect(webAuthnError.message).toBe(errorMessage);
      expect(webAuthnError.code).toBe(errorCode);
      expect(webAuthnError.name).toBe('WebAuthnError');
      expect(webAuthnError.cause).toBe(causeError);
      expect(webAuthnError.stack).toBe(causeError.stack);
    });
  
    it('should default to cause name if name is not provided', () => {
      const errorMessage = 'WebAuthn operation failed';
      const errorCode = WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR;
      const causeError = new Error('Underlying cause of WebAuthn failure');
  
      const webAuthnError = new WebAuthnError({
        message: errorMessage,
        code: errorCode,
        cause: causeError,
      });
  
      expect(webAuthnError.name).toBe(causeError.name);
    });
  
    it('should default to cause.name if name is not provided', () => {
      const errorMessage = 'WebAuthn operation failed';
      const errorCode = WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR;
      const causeError = { name: "Error", message: 'Unknown error', stack: 'at unknown' } as Error;
  
      const webAuthnError = new WebAuthnError({
        message: errorMessage,
        code: errorCode,
        cause: causeError,
      });
  
      expect(webAuthnError.name).toBe(causeError.name);
    });
  });

  describe('identifyAuthenticationError', () => {
    const mockOptions = {
      publicKey: {
        rpId: 'example.com',
        challenge: new ArrayBuffer(5),
      },
      signal: new AbortController().signal,
    };
  
    it('returns a WebAuthnError for AbortError with signal', () => {
      const name = ErrorNames.AbortError;
      const code = WebAuthnErrorCode.CEREMONY_ABORTED;
      const cause = new Error("Abort error");
      const error = new WebAuthnError({ cause, name, code, message: cause.message });
  
      const detailedError = identifyAuthenticationError({ error, options: mockOptions });
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(code);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for NotAllowedError', () => {
      const error = new Error('Operation not allowed');
      error.name = ErrorNames.NotAllowedError;
  
      const detailedError = identifyAuthenticationError({ error, options: mockOptions });
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.PASSTHROUGH_SEE_CAUSE_PROPERTY);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for SecurityError with invalid domain', () => {
      const error = new Error('Security error');
      error.name = ErrorNames.SecurityError;
      const hostname = 'invalid-domain';
      (isValidDomain as jest.Mock).mockReturnValueOnce(false)
      Object.defineProperty(window.location, "hostname", { value: hostname, writable: true });
  
      const invalidDomainOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey, rpId: hostname },
      };
  
      const detailedError = identifyAuthenticationError({ error, options: invalidDomainOptions });
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.INVALID_DOMAIN);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for SecurityError with invalid RP ID', () => {
      const error = new Error('Security error');
      error.name = ErrorNames.SecurityError;
      (isValidDomain as jest.Mock).mockReturnValueOnce(true)
      Object.defineProperty(window.location, "hostname", { value: "another-example.com", writable: true });
  
      const invalidRpIdOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey, rpId: 'another-example' },
      };
  
      const detailedError = identifyAuthenticationError({ error, options: invalidRpIdOptions });
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.INVALID_RP_ID);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for UnknownError', () => {
      const error = new Error('Unknown error');
      error.name = ErrorNames.UnknownError;
  
      const detailedError = identifyAuthenticationError({ error, options: mockOptions });
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns original in WebAuthnError when the error cannot be identified', () => {
      const error = new Error('Some unknown error');
  
      const detailedError = identifyAuthenticationError({ error, options: mockOptions });

      const expectedError = new WebAuthnError({
        message: "Unknown Error",
        name: ErrorNames.UnknownError,
        code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
        cause: error,
      });
  
      expect(detailedError.name).toBe(expectedError.name);
      expect(detailedError.code).toBe(expectedError.code);
      expect(detailedError.cause).toBe(expectedError.cause);
      expect(detailedError.message).toBe(expectedError.message);
    });
  
    it('throws an error when publicKey is missing in options', () => {
      const error = new Error('Some error');
      error.name = ErrorNames.AbortError;
  
      const invalidOptions = {
        ...mockOptions,
        publicKey: undefined,
      };
  
      expect(() => {
        identifyAuthenticationError({ error, options: invalidOptions });
      }).toThrow('options was missing required publicKey property');
    });
  });

  describe('identifyRegistrationError', () => {
    const mockOptions: CredentialCreationOptions = {
      publicKey: {
        rp: { id: 'example.com', name: "example" },
        user: { id: new Uint8Array(10), name: 'user', displayName: 'User' },
        challenge: new ArrayBuffer(5),
        pubKeyCredParams: [{ type: 'public-key', alg: -7 }],
      },
      signal: new AbortController().signal,
    };
  
    it('returns a WebAuthnError for AbortError with signal', () => {
      const error = new Error('Abort error');
      error.name = ErrorNames.AbortError;
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.CEREMONY_ABORTED);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for ConstraintError with missing discoverable credential support', () => {
      const error = new Error('Constraint error');
      error.name = ErrorNames.ConstraintError;
  
      const optionsWithDiscoverableCredentials: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: {
          ...mockOptions.publicKey!,
          authenticatorSelection: { requireResidentKey: true },
        },
      };
  
      const detailedError = identifyRegistrationError({ error, options: optionsWithDiscoverableCredentials }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for ConstraintError with missing user verification support', () => {
      const error = new Error('Constraint error');
      error.name = ErrorNames.ConstraintError;
  
      const optionsWithUserVerification: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: {
          ...mockOptions.publicKey!,
          authenticatorSelection: { userVerification: 'required' },
        },
      };
  
      const detailedError = identifyRegistrationError({ error, options: optionsWithUserVerification }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for InvalidStateError', () => {
      const error = new Error('Invalid state error');
      error.name = ErrorNames.InvalidStateError;
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_PREVIOUSLY_REGISTERED);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for NotAllowedError', () => {
      const error = new Error('Operation not allowed');
      error.name = ErrorNames.NotAllowedError;
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.PASSTHROUGH_SEE_CAUSE_PROPERTY);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for NotSupportedError with no valid pubKeyCredParams', () => {
      const error = new Error('Not supported error');
      error.name = ErrorNames.NotSupportedError;
  
      const optionsWithInvalidPubKeyCredParams: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey!, pubKeyCredParams: [] },
      };
  
      const detailedError = identifyRegistrationError({ error, options: optionsWithInvalidPubKeyCredParams }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.MALFORMED_PUBKEYCREDPARAMS);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for NotSupportedError with no supported pubKeyCredParams algorithms', () => {
      const error = new Error('Not supported error');
      error.name = ErrorNames.NotSupportedError;
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for SecurityError with invalid domain', () => {
      const error = new Error('Security error');
      error.name = ErrorNames.SecurityError;
      const webauthnError = new WebAuthnError({
        code: WebAuthnErrorCode.INVALID_DOMAIN,
        name: error.name,
        cause: error,
      });
      (isValidDomain as jest.Mock).mockReturnValueOnce(false);
      Object.defineProperty(window.location, 'hostname', { value: 'invalid-domain', writable: true });
  
      const invalidDomainOptions: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey!, rp: { id: 'invalid-domain', name: "invalid" } },
      };
  
      const detailedError = identifyRegistrationError({ error, options: invalidDomainOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(webauthnError.code);
      expect(detailedError.cause).toBe(webauthnError.cause);
    });
  
    it('returns a WebAuthnError for SecurityError with invalid RP ID', () => {
      const error = new Error('Security error');
      error.name = ErrorNames.SecurityError;
      (isValidDomain as jest.Mock).mockReturnValueOnce(true);
      Object.defineProperty(window.location, 'hostname', { value: 'another-example.com', writable: true });
  
      const invalidRpIdOptions: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey!, rp: { id: 'another-example', name: "another" } },
      };
  
      const detailedError = identifyRegistrationError({ error, options: invalidRpIdOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.INVALID_RP_ID);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for TypeError with invalid user ID length', () => {
      const error = new Error('Type error');
      error.name = ErrorNames.TypeError;
  
      const invalidUserIdOptions: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: { ...mockOptions.publicKey!, user: { id: new Uint8Array(65), name: "name", displayName: "display" } },
      };
  
      const detailedError = identifyRegistrationError({ error, options: invalidUserIdOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.INVALID_USER_ID_LENGTH);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns a WebAuthnError for UnknownError', () => {
      const error = new Error('Unknown error');
      error.name = ErrorNames.UnknownError;
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions }) as WebAuthnError;
  
      expect(detailedError).toBeInstanceOf(WebAuthnError);
      expect(detailedError.code).toBe(WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR);
      expect(detailedError.cause).toBe(error);
    });
  
    it('returns original error when it cannot be identified', () => {
      const error = new Error('Some unknown error');
  
      const detailedError = identifyRegistrationError({ error, options: mockOptions });
  
      expect(detailedError).toBe(error);
    });
  
    it('throws an error when publicKey is missing in options', () => {
      const error = new Error('Some error');
      error.name = ErrorNames.AbortError;
  
      const invalidOptions: CredentialCreationOptions = {
        ...mockOptions,
        publicKey: undefined,
      };
  
      expect(() => {
        identifyRegistrationError({ error, options: invalidOptions });
      }).toThrow('options was missing required publicKey property');
    });
  });
})