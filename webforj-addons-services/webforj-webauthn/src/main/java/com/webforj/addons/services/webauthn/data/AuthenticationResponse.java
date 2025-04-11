package com.webforj.addons.services.webauthn.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Represents the response returned by the {@code navigator.credentials.get()} function. This class
 * encapsulates the data returned from the client-side authentication process.
 *
 * @see <a href=
 *     "https://www.w3.org/TR/webauthn-3/#ref-for-dictdef-authenticationresponsejson">§5.5. JSON
 *     Type Representations</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public class AuthenticationResponse {
  /**
   * The identifier of the returned credential, which is chosen by the authenticator. This
   * identifier is used to look up credentials for use and is expected to be globally unique with
   * high probability across all credentials of the same type and across all authenticators.
   */
  private String id;

  /**
   * The raw identifier of the returned credential. This identifier is used internally and is
   * typically an encoded version of the ID.
   */
  private String rawId;

  /**
   * The response object containing data returned by the authenticator during the assertion process.
   */
  private AuthenticatorAssertionResponse response;

  /** The attachment modality of the authenticator during the assertion process, if applicable. */
  private AuthenticatorAttachment authenticatorAttachment;

  /**
   * The results of processing client extensions requested by the Relying Party upon the invocation
   * of {@code navigator.credentials.get()}.
   */
  private AuthenticationExtensionsOutput clientExtensionResults;

  /** The type of the credential. For WebAuthn, this will typically be "public-key". */
  private String type;

  /**
   * Converts this {@code AuthenticationResponse} instance to its JSON representation.
   *
   * @return JSON representation of this {@code AuthenticationResponse}.
   */
  public String toJson() {
    Gson gson = new GsonBuilder().create();
    return gson.toJson(this);
  }

  /**
   * Converts a JSON string to a AuthenticationResponse instance.
   *
   * @param json JSON string representing {@code AuthenticationResponse}.
   * @return {@code AuthenticationResponse} instance parsed from JSON.
   * @throws JsonParseException If the JSON string is invalid.
   */
  public static AuthenticationResponse fromJson(String json) throws JsonParseException {
    Gson gson = new GsonBuilder().create();
    return gson.fromJson(json, AuthenticationResponse.class);
  }

  /**
   * Retrieves the credential ID.
   *
   * @return The credential ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the credential ID.
   *
   * @param id The credential ID.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Retrieves the raw credential ID.
   *
   * @return The raw credential ID.
   */
  public String getRawId() {
    return rawId;
  }

  /**
   * Sets the raw credential ID.
   *
   * @param rawId The raw credential ID.
   */
  public void setRawId(String rawId) {
    this.rawId = rawId;
  }

  /**
   * Retrieves the authenticator assertion response.
   *
   * @return The authenticator assertion response.
   */
  public AuthenticatorAssertionResponse getResponse() {
    return response;
  }

  /**
   * Sets the authenticator assertion response.
   *
   * @param response The authenticator assertion response.
   */
  public AuthenticationResponse setResponse(AuthenticatorAssertionResponse response) {
    this.response = response;
    return this;
  }

  /**
   * Retrieves the authenticator attachment.
   *
   * @return The authenticator attachment.
   */
  public AuthenticatorAttachment getAuthenticatorAttachment() {
    return authenticatorAttachment;
  }

  /**
   * Sets the authenticator attachment.
   *
   * @param authenticatorAttachment The authenticator attachment.
   */
  public void setAuthenticatorAttachment(AuthenticatorAttachment authenticatorAttachment) {
    this.authenticatorAttachment = authenticatorAttachment;
  }

  /**
   * Retrieves the client extension results.
   *
   * @return The client extension results.
   */
  public AuthenticationExtensionsOutput getClientExtensionResults() {
    return clientExtensionResults;
  }

  /**
   * Sets the client extension results.
   *
   * @param clientExtensionResults The client extension results.
   */
  public AuthenticationResponse setClientExtensionResults(
      AuthenticationExtensionsOutput clientExtensionResults) {
    this.clientExtensionResults = clientExtensionResults;
    return this;
  }

  /**
   * Retrieves the credential type.
   *
   * @return The credential type.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the credential type.
   *
   * @param type The credential type.
   */
  public AuthenticationResponse setType(String type) {
    this.type = type;
    return this;
  }
}
