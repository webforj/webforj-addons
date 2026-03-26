import type {
  AuthenticationCredential,
  AuthenticationResponseJSON,
  PublicKeyCredentialRequestOptionsJSON,
  WebAuthnResponse,
} from '../types';

import { bufferToBase64URLString, base64URLStringToBuffer, bufferToUTF8String } from '../utils/communications';
import { browserSupportsWebAuthn, browserSupportsWebAuthnAutofill } from '../utils/browser-supports';
import { identifyAuthenticationError } from '../utils/error';
import { webAuthnAbort } from '../utils/webauthn-abort';
import { toAuthenticatorAttachment, toPublicKeyCredentialDescriptor } from '../utils/transformers';
import { ErrorNames, WebAuthnErrorCode } from 'constants/error';

/**
 * Begin authenticator "login" via WebAuthn assertion
 *
 * @param requestOptionsJSON Generated options from the server that includes credentials
 * @param useBrowserAutofill Initialize conditional UI to enable logging in via browser
 * autofill prompts
 */
export async function authenticate(
  requestOptionsJSON: PublicKeyCredentialRequestOptionsJSON,
  useBrowserAutofill = false,
): Promise<WebAuthnResponse<AuthenticationResponseJSON>> {
  if (!browserSupportsWebAuthn()) {
    return {
      success: false,
      error: {
        code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
        message: 'WebAuthn is not supported in this browser',
        name: ErrorNames.NotSupportedError,
      },
    };
  }

  // We need to avoid passing empty array to avoid blocking retrieval
  // of public key
  let allowCredentials: PublicKeyCredentialDescriptor[];
  if (requestOptionsJSON.allowCredentials?.length !== 0) {
    allowCredentials = requestOptionsJSON.allowCredentials?.map(
      toPublicKeyCredentialDescriptor,
    );
  }

  // Preparing the public key by converting some of the values
  // to Uint8Arrays before passing the credentials to the navigator
  const publicKey: PublicKeyCredentialRequestOptions = {
    ...requestOptionsJSON,
    challenge: base64URLStringToBuffer(requestOptionsJSON.challenge),
    allowCredentials,
  };

  /**
   * Authentication options to be passed to `navigator.credentials.get(...)`
   */
  const options: CredentialRequestOptions = {};

  /**
   * Set up the page to prompt the user to select a credential for authentication via the browser's
   * input autofill mechanism.
   */
  if (useBrowserAutofill) {
    if (!(await browserSupportsWebAuthnAutofill())) {
      return {
        success: false,
        error: {
          code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
          message: 'Browser does not support WebAuthn autofill',
          name: ErrorNames.NotSupportedError,
        },
      };
    }

    const eligibleInputs = document.querySelectorAll(
      'input[autocomplete$=\'webauthn\']',
    );

    if (eligibleInputs.length < 1) {
      return {
        success: false,
        error: {
          code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
          message: 'No <input> with "webauthn" as the only or last value in its `autocomplete` attribute was detected',
          name: ErrorNames.NotSupportedError,
        },
      };
    }

    options.mediation = 'conditional';
    // Conditional UI requires an empty allow list
    publicKey.allowCredentials = [];
  }

  options.publicKey = publicKey;
  options.signal = webAuthnAbort.createAbortSignal();

  let credential: AuthenticationCredential;
  try {
    credential = (await navigator.credentials.get(options)) as AuthenticationCredential;
  } catch (err) {
    const identified = identifyAuthenticationError({ error: err as Error, options });
    return {
      success: false,
      error: {
        code: identified.code,
        message: identified.message,
        name: identified.name,
      },
    };
  }

  if (!credential) {
    return {
      success: false,
      error: {
        code: WebAuthnErrorCode.AUTHENTICATOR_GENERAL_ERROR,
        message: 'Authentication was not completed',
        name: ErrorNames.UnknownError,
      },
    };
  }

  const { id, rawId, response, type } = credential;

  let userHandle = undefined;
  if (response.userHandle) {
    userHandle = bufferToUTF8String(response.userHandle);
  }

  return {
    success: true,
    data: {
      id,
      rawId: bufferToBase64URLString(rawId),
      response: {
        authenticatorData: bufferToBase64URLString(response.authenticatorData),
        clientDataJson: bufferToBase64URLString(response.clientDataJSON),
        signature: bufferToBase64URLString(response.signature),
        userHandle,
      },
      type,
      clientExtensionResults: credential.getClientExtensionResults(),
      authenticatorAttachment: toAuthenticatorAttachment(
        credential.authenticatorAttachment,
      ),
    },
  };
}
