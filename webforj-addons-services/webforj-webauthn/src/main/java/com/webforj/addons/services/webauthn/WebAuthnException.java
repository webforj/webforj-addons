package com.webforj.addons.services.webauthn;

/**
 * Exception thrown when a WebAuthn ceremony (registration or authentication) fails on the
 * client-side. This exception carries the structured error information from the browser's Web
 * Authentication API, including a {@link WebAuthnErrorCode} that identifies the specific failure
 * reason.
 *
 * <p>Consumers can use {@link #getCode()} to programmatically distinguish between different failure
 * scenarios such as user cancellation, missing authenticator capabilities, or invalid
 * configuration.
 *
 * @see WebAuthnErrorCode
 * @see <a href="https://www.w3.org/TR/webauthn-3/#sctn-api">Web Authentication API</a>
 */
public class WebAuthnException extends RuntimeException {

  private final WebAuthnErrorCode code;
  private final String errorName;

  /**
   * Constructs a new {@code WebAuthnException} with the specified details.
   *
   * @param message the error message from the client
   * @param code the {@link WebAuthnErrorCode} identifying the failure reason
   * @param errorName the original DOM exception name (e.g. {@code "NotAllowedError"}, {@code
   *     "AbortError"})
   */
  public WebAuthnException(String message, WebAuthnErrorCode code, String errorName) {
    super(message);
    this.code = code;
    this.errorName = errorName;
  }

  /**
   * Returns the error code identifying the specific failure reason.
   *
   * @return the {@link WebAuthnErrorCode}
   */
  public WebAuthnErrorCode getCode() {
    return code;
  }

  /**
   * Returns the original DOM exception name from the browser (e.g. {@code "NotAllowedError"},
   * {@code "AbortError"}, {@code "InvalidStateError"}).
   *
   * @return the error name, or {@code null} if not available
   */
  public String getErrorName() {
    return errorName;
  }
}
