package com.webforj.addons.services.webauthn.data;


/**
 * Represents the registration options for a WebAuthn operation, as defined in the WebAuthn
 * specification version 3. This class allows specifying extensions to be used during credential
 * creation or authentication.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#iface-authentication-extensions-client-inputs">WebAuthn
 *      Extensions input</a>
 */
public class AuthenticationExtensionsInput {
  /**
   * Identifier for the relying party's application
   */
  private String appid;

  /**
   * Indicates if client requests authenticator's credential properties extension
   */
  private boolean credProps;

  /**
   * Indicates if client requests newly generated HMAC-Secret extension input
   */
  private boolean hmacCreateSecret;

  /**
   * Constructs a new AuthenticationExtensionsClientInput instance.
   *
   * @param appid The identifier for the relying party's application.
   * @param credProps A boolean indicating whether the client requests the authenticator's
   *        credential properties extension.
   * @param hmacCreateSecret A boolean indicating whether the client requests a newly generated
   *        HMAC-Secret extension input.
   */
  public AuthenticationExtensionsInput(String appid, boolean credProps, boolean hmacCreateSecret) {
    this.appid = appid;
    this.credProps = credProps;
    this.hmacCreateSecret = hmacCreateSecret;
  }

  /**
   * Gets the identifier for the relying party's application.
   *
   * @return The appid.
   */
  public String getAppid() {
    return appid;
  }

  /**
   * Sets the identifier for the relying party's application.
   *
   * @param appid The appid to set.
   */
  public void setAppid(String appid) {
    this.appid = appid;
  }

  /**
   * Checks if the client requests the authenticator's credential properties extension.
   *
   * @return True if the extension is requested, false otherwise.
   */
  public boolean getCredProps() {
    return credProps;
  }

  /**
   * Sets whether the client requests the authenticator's credential properties extension.
   *
   * @param credProps The value indicating if the extension is requested.
   */
  public void setCredProps(boolean credProps) {
    this.credProps = credProps;
  }

  /**
   * Checks if the client requests a newly generated HMAC-Secret extension input.
   *
   * @return True if the extension input is requested, false otherwise.
   */
  public boolean getHmacCreateSecret() {
    return hmacCreateSecret;
  }

  /**
   * Sets whether the client requests a newly generated HMAC-Secret extension input.
   *
   * @param hmacCreateSecret The value indicating if the extension input is requested.
   */
  public void setHmacCreateSecret(boolean hmacCreateSecret) {
    this.hmacCreateSecret = hmacCreateSecret;
  }
}
