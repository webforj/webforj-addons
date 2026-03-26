package com.webforj.addons.services.webauthn;

/**
 * Enumeration of error codes that may be returned during WebAuthn registration or authentication
 * ceremonies. These codes mirror the error codes defined in the client-side TypeScript
 * implementation and provide detailed context about the cause of a failure.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#sctn-api">Web Authentication API</a>
 * 
 */
public enum WebAuthnErrorCode {

  /** The ceremony was aborted via an {@code AbortSignal}. */
  CEREMONY_ABORTED,

  /** The current domain is not valid for WebAuthn operations. */
  INVALID_DOMAIN,

  /** The Relying Party ID does not match the current domain. */
  INVALID_RP_ID,

  /** The user ID length is outside the allowed range (1-64 bytes). */
  INVALID_USER_ID_LENGTH,

  /** The {@code pubKeyCredParams} array is malformed or empty. */
  MALFORMED_PUBKEYCREDPARAMS,

  /** A general authenticator error occurred. */
  AUTHENTICATOR_GENERAL_ERROR,

  /** The authenticator does not support discoverable credentials (resident keys). */
  AUTHENTICATOR_MISSING_DISCOVERABLE_CREDENTIAL_SUPPORT,

  /** The authenticator does not support user verification. */
  AUTHENTICATOR_MISSING_USER_VERIFICATION_SUPPORT,

  /** The authenticator was previously registered for this user and relying party. */
  AUTHENTICATOR_PREVIOUSLY_REGISTERED,

  /** None of the requested public key credential algorithms are supported. */
  AUTHENTICATOR_NO_SUPPORTED_PUBKEYCREDPARAMS_ALG,

  /**
   * The error details are in the {@link WebAuthnException#getCause()} property. This typically
   * wraps a {@code NotAllowedError} whose message contains the browser's native error text
   * (including user cancellation).
   */
  PASSTHROUGH_SEE_CAUSE_PROPERTY;

  /**
   * Returns the {@code WebAuthnErrorCode} matching the given string, or
   * {@link #AUTHENTICATOR_GENERAL_ERROR} if no match is found.
   *
   * @param code the string code from the client error payload
   * @return the matching enum constant
   */
  public static WebAuthnErrorCode fromString(String code) {
    if (code == null) {
      return AUTHENTICATOR_GENERAL_ERROR;
    }
    try {
      return valueOf(code);
    } catch (IllegalArgumentException e) {
      return AUTHENTICATOR_GENERAL_ERROR;
    }
  }
}
