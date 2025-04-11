package com.webforj.addons.services.webauthn.data;

/**
 * Represents the output of client extension processing during WebAuthn operations.
 *
 * @see <a href=
 *     "https://www.w3.org/TR/webauthn-3/#iface-authentication-extensions-client-outputs">WebAuthn
 *     Extensions output</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public class AuthenticationExtensionsOutput {

  /** A boolean indicating whether the client supports the App ID extension. */
  private boolean appid;

  /** The output of client extension processing for Credential Properties extension. */
  private CredentialPropertiesOutput credProps;

  /** A boolean indicating whether the client supports the HMAC Create Secret extension. */
  private boolean hmacCreateSecret;

  /**
   * Constructs a new instance of AuthenticatorExtensionsOutput.
   *
   * @param appid A boolean indicating whether the client supports the App ID extension.
   * @param credProps The output of client extension processing for Credential Properties extension.
   * @param hmacCreateSecret A boolean indicating whether the client supports the HMAC Create Secret
   *     extension.
   */
  public AuthenticationExtensionsOutput(
      boolean appid, CredentialPropertiesOutput credProps, boolean hmacCreateSecret) {
    this.appid = appid;
    this.credProps = credProps;
    this.hmacCreateSecret = hmacCreateSecret;
  }

  /**
   * Retrieves the boolean indicating whether the client supports the App ID extension.
   *
   * @return True if the client supports the App ID extension, otherwise false.
   */
  public boolean isAppidSupported() {
    return appid;
  }

  /**
   * Sets the boolean indicating whether the client supports the App ID extension.
   *
   * @param appid True if the client supports the App ID extension, otherwise false.
   */
  public void setAppid(boolean appid) {
    this.appid = appid;
  }

  /**
   * Retrieves the output of client extension processing for Credential Properties extension.
   *
   * @return The output of client extension processing for Credential Properties extension.
   */
  public CredentialPropertiesOutput getCredProps() {
    return credProps;
  }

  /**
   * Sets the output of client extension processing for Credential Properties extension.
   *
   * @param credProps The output of client extension processing for Credential Properties extension.
   */
  public void setCredProps(CredentialPropertiesOutput credProps) {
    this.credProps = credProps;
  }

  /**
   * Retrieves the boolean indicating whether the client supports the HMAC Create Secret extension.
   *
   * @return True if the client supports the HMAC Create Secret extension, otherwise false.
   */
  public boolean isHmacCreateSecretSupported() {
    return hmacCreateSecret;
  }

  /**
   * Sets the boolean indicating whether the client supports the HMAC Create Secret extension.
   *
   * @param hmacCreateSecret True if the client supports the HMAC Create Secret extension, otherwise
   *     false.
   */
  public void setHmacCreateSecret(boolean hmacCreateSecret) {
    this.hmacCreateSecret = hmacCreateSecret;
  }
}
