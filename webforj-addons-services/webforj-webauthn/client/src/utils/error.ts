import { ErrorNames, WebAuthnErrorCode } from "../constants/error";
import { isValidDomain } from "../utils/validation";

/**
 * A custom error class used to provide detailed information about errors raised during 
 * Web Authentication API operations (`navigator.credentials.create()` or `navigator.credentials.get()`).
 * 
 * This error class extends the standard Error class and adds additional properties to 
 * specify the error code and cause of the error. The error code corresponds to one of 
 * the defined WebAuthnErrorCode values.
 * 
 * @extends Error
 * @param {Object} options - Options for constructing the error.
 * @param {string} options.message - The error message.
 * @param {WebAuthnErrorCode} options.code - The error code.
 * @param {Error} options.cause - The original error causing this error.
 * @param {string} [options.name] - The name of the error.
 */
export class WebAuthnError extends Error {
  code: typeof WebAuthnErrorCode[keyof typeof WebAuthnErrorCode];
  cause: Error;

  constructor({
    message,
    code,
    cause,
    name,
  }: {
    message?: string;
    code?: typeof WebAuthnErrorCode[keyof typeof WebAuthnErrorCode];
    cause: Error;
    name?: string;
  }) {
    // @ts-ignore
    super(message, { cause });
    this.name = name ?? cause.name;
    this.code = code ?? WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR;
    this.stack = cause?.stack;
  }
}

/**
 * Attempts to identify the cause of an error raised during a Web Authentication API authentication operation.
 * 
 * This function analyzes the provided error and authentication options to determine the specific reason
 * why the authentication failed. It returns a more detailed error instance (WebAuthnError) with an associated
 * error code to provide additional context about the failure.
 * 
 * @param {Object} params - Parameters for identifying the authentication error.
 * @param {Error} params.error - The error raised during the authentication operation.
 * @param {CredentialRequestOptions} params.options - The options used in the authentication operation.
 * @returns {WebAuthnError | Error} A detailed error instance (WebAuthnError) if the cause of the error
 *                                   can be identified, otherwise returns the original error instance.
 * 
 * @example
 * // Example usage:
 * const authenticationOptions = {
 *   publicKey: {
 *     // publicKey options
 *   }
 * };
 * 
 * try {
 *   await navigator.credentials.get({ publicKey: authenticationOptions });
 * } catch (error) {
 *   const detailedError = identifyAuthenticationError({ error, options: authenticationOptions });
 *   console.error(detailedError);
 * }
 */
export function identifyAuthenticationError({
  error,
  options,
}: {
  error: Error;
  options: CredentialRequestOptions;
}): WebAuthnError {
  const { publicKey } = options;

  if (!publicKey) {
    throw Error('options was missing required publicKey property');
  }

  if (error.name === ErrorNames.AbortError) {
    if (options.signal instanceof AbortSignal) {
      return new WebAuthnError({
        message: 'Authentication ceremony was sent an abort signal',
        code: WebAuthnErrorCode.CEREMONY_ABORTED,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.NotAllowedError) {
    return new WebAuthnError({
      message: error.message,
      code: WebAuthnErrorCode.PASSTHROUGH_SEE_CAUSE_PROPERTY,
      cause: error,
    });
  } else if (error.name === ErrorNames.SecurityError) {
    const effectiveDomain = window.location.hostname;
    if (!isValidDomain(effectiveDomain)) {
      return new WebAuthnError({
        message: `${window.location.hostname} is an invalid domain`,
        code: WebAuthnErrorCode.INVALID_DOMAIN,
        cause: error,
      });
    } else if (publicKey.rpId !== effectiveDomain) {
      return new WebAuthnError({
        message: `The RP ID "${publicKey.rpId}" is invalid for this domain`,
        code: WebAuthnErrorCode.INVALID_RP_ID,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.UnknownError) {
    return new WebAuthnError({
      message:
        'The authenticator was unable to process the specified options, or could not create a new assertion signature',
      code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
      cause: error,
    });
  }

  return new WebAuthnError({
    message: "Unknown Error",
    name: ErrorNames.UnknownError,
    code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
    cause: error,
  });
}

/**
 * Attempts to identify the cause of an error raised during a Web Authentication API registration operation.
 * 
 * This function analyzes the provided error and registration options to determine the specific reason
 * why the registration failed. It returns a more detailed error instance (WebAuthnError) with an associated
 * error code to provide additional context about the failure.
 * 
 * @param {Object} params - Parameters for identifying the registration error.
 * @param {Error} params.error - The error raised during the registration operation.
 * @param {CredentialCreationOptions} params.options - The options used in the registration operation.
 * @returns {WebAuthnError | Error} A detailed error instance (WebAuthnError) if the cause of the error
 *                                   can be identified, otherwise returns the original error instance.
 * 
 * @example
 * // Example usage:
 * const registrationOptions = {
 *   publicKey: {
 *     // publicKey options
 *   }
 * };
 * 
 * try {
 *   await navigator.credentials.create({ publicKey: registrationOptions });
 * } catch (error) {
 *   const detailedError = identifyRegistrationError({ error, options: registrationOptions });
 *   console.error(detailedError);
 * }
 */
export function identifyRegistrationError({
  error,
  options,
}: {
  error: Error;
  options: CredentialCreationOptions;
}): WebAuthnError | Error {
  const { publicKey } = options;

  if (!publicKey) {
    throw Error('options was missing required publicKey property');
  }

  if (error.name === ErrorNames.AbortError) {
    if (options.signal instanceof AbortSignal) {
      return new WebAuthnError({
        message: 'Registration ceremony was sent an abort signal',
        code: WebAuthnErrorCode.CEREMONY_ABORTED,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.ConstraintError) {
    if (publicKey.authenticatorSelection?.requireResidentKey === true) {
      return new WebAuthnError({
        message:
          'Discoverable credentials were required but no available authenticator supported it',
        code: WebAuthnErrorCode.AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT,
        cause: error,
      });
    } else if (
      publicKey.authenticatorSelection?.userVerification === 'required'
    ) {
      return new WebAuthnError({
        message: 'User verification was required but no available authenticator supported it',
        code: WebAuthnErrorCode.AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.InvalidStateError) {
    return new WebAuthnError({
      message: 'The authenticator was previously registered',
      code: WebAuthnErrorCode.AUTHENTICATOR_PREVIOUSLY_REGISTERED,
      cause: error,
    });
  } else if (error.name === ErrorNames.NotAllowedError) {
    return new WebAuthnError({
      message: error.message,
      code: WebAuthnErrorCode.PASSTHROUGH_SEE_CAUSE_PROPERTY,
      cause: error,
    });
  } else if (error.name === ErrorNames.NotSupportedError) {
    const validPubKeyCredParams = publicKey.pubKeyCredParams.filter(
      (param) => param.type === 'public-key',
    );

    if (validPubKeyCredParams.length === 0) {
      return new WebAuthnError({
        message: 'No entry in pubKeyCredParams was of type "public-key"',
        code: WebAuthnErrorCode.MALFORMED_PUBKEYCREDPARAMS,
        cause: error,
      });
    }

    return new WebAuthnError({
      message:
        'No available authenticator supported any of the specified pubKeyCredParams algorithms',
      code: WebAuthnErrorCode.AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG,
      cause: error,
    });
  } else if (error.name === ErrorNames.SecurityError) {
    const effectiveDomain = window.location.hostname;
    if (!isValidDomain(effectiveDomain)) {
      return new WebAuthnError({
        message: `${window.location.hostname} is an invalid domain`,
        code: WebAuthnErrorCode.INVALID_DOMAIN,
        cause: error,
      });
    } else if (publicKey.rp.id !== effectiveDomain) {
      return new WebAuthnError({
        message: `The RP ID "${publicKey.rp.id}" is invalid for this domain`,
        code: WebAuthnErrorCode.INVALID_RP_ID,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.TypeError) {
    if (publicKey.user.id.byteLength < 1 || publicKey.user.id.byteLength > 64) {
      return new WebAuthnError({
        message: 'User ID was not between 1 and 64 characters',
        code: WebAuthnErrorCode.INVALID_USER_ID_LENGTH,
        cause: error,
      });
    }
  } else if (error.name === ErrorNames.UnknownError) {
    return new WebAuthnError({
      message:
        'The authenticator was unable to process the specified options, or could not create a new credential',
      code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
      cause: error,
    });
  }

  return error;
}
