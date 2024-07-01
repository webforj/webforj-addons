package com.webforj.addons.services.webauthn.data;

import com.google.gson.Gson;

/**
 * This class represents the client data passed to {@code navigator.credentials.create()} or
 * {@code navigator.credentials.get()}. It contains information such as the cryptographic challenge,
 * origin, cross-origin status, token binding, and type of operation. This class is part of the Web
 * Authentication (WebAuthn) Level 3 specification.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#dom-authenticatorresponse-clientdatajson">Web
 *      Authentication (WebAuthn) Level 3: Client Data</a>
 * @see <a href=
 *      "https://developer.mozilla.org/en-US/docs/Web/API/AuthenticatorResponse/clientDataJSON">MDN
 *      Web Docs: AuthenticatorResponse.clientDataJSON</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class ClientDataJSON {

  private static final Gson gson = new Gson();

  /**
   * The base64url-encoded version of the cryptographic challenge sent from the relying party's
   * server.
   */
  private String challenge;

  /**
   * The fully qualified origin of the relying party.
   */
  private String origin;

  /**
   * Indicates whether the calling context is an <iframe> that is not same origin with its ancestor
   * frames.
   */
  private boolean crossOrigin;

  /**
   * Contains the fully qualified top-level origin of the relying party. This property is set only
   * if {@code crossOrigin} is {@code true}.
   */
  private String topOrigin;

  /**
   * Describes the state of the token binding protocol for communication with the relying party. It
   * consists of two properties: {@code status} and {@code id}.
   */
  private TokenBinding tokenBinding;

  /**
   * Indicates the type of operation: "webauthn.get" or "webauthn.create".
   */
  private String type;

  /**
   * Parses the client data JSON from a base64url-encoded string and constructs a
   * {@code ClientDataJSON} object.
   *
   * @param base64Url The base64url-encoded string containing the JSON data.
   * @return The parsed {@code ClientDataJSON} object.
   */
  public static ClientDataJSON fromBase64Url(String base64Url) {
    byte[] bytes = ByteArray.fromBase64Url(base64Url).getBytes();
    String json = ByteArray.convertToUTF8String(bytes);
    return gson.fromJson(json, ClientDataJSON.class);
  }

  /**
   * Gets the base64url-encoded version of the cryptographic challenge sent from the relying party's
   * server.
   *
   * @return The challenge.
   */
  public String getChallenge() {
    return challenge;
  }

  /**
   * Gets the fully qualified origin of the relying party.
   *
   * @return The origin of the relying party.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Checks whether the calling context is an <iframe> that is not same origin with its ancestor
   * frames.
   *
   * @return {@code true} if cross-origin, {@code false} otherwise.
   */
  public boolean isCrossOrigin() {
    return crossOrigin;
  }

  /**
   * Gets the fully qualified top-level origin of the relying party. This property is set only if
   * {@code crossOrigin} is {@code true}.
   *
   * @return The top-level origin, or {@code null} if not set.
   */
  public String getTopOrigin() {
    return topOrigin;
  }

  /**
   * Gets the token binding associated with the client data.
   *
   * @return The token binding, or {@code null} if not present.
   */
  public TokenBinding getTokenBinding() {
    return tokenBinding;
  }

  /**
   * Gets the type of operation: "webauthn.get" or "webauthn.create".
   *
   * @return The operation type.
   */
  public String getType() {
    return type;
  }
}
