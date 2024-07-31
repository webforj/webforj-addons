package com.webforj.addons.services.webauthn.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Represents the response from the client for the registration process.
 * <p>
 * This class simplifies working with ArrayBuffers that are Base64URL-encoded in
 * the browser, allowing them to be sent as JSON to the server.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#dom-registrationresponsejson-authenticatorattachment">RegistrationResponse</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class RegistrationResponse {

	/**
	 * The identifier of the credential returned by the authenticator.
	 */
	private String id;

	/**
	 * The raw identifier of the credential returned by the authenticator, encoded
	 * as a URL-safe base64 string.
	 */
	private String rawId;

	/**
	 * The response data returned by the authenticator.
	 */
	private AuthenticatorAttestationResponse response;

	/**
	 * The attachment modality of the authenticator, if available.
	 */
	private AuthenticatorAttachment authenticatorAttachment;

	/**
	 * Results of client extension processing during the registration.
	 */
	private AuthenticationExtensionsOutput clientExtensionResults;

	/**
	 * The type of the credential returned by the authenticator.
	 */
	private String type;

	/**
	 * Converts this RegistrationResponse instance to its JSON representation.
	 *
	 * @return JSON representation of this RegistrationResponse.
	 */
	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	/**
	 * Converts a JSON string to a RegistrationResponse instance.
	 *
	 * @param json JSON string representing RegistrationResponse.
	 * @return RegistrationResponse instance parsed from JSON.
	 * @throws JsonParseException If the JSON string is invalid.
	 */
	public static RegistrationResponse fromJson(String json) throws JsonParseException {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, RegistrationResponse.class);
	}

	/**
	 * Get the identifier of the credential returned by the authenticator.
	 *
	 * @return The identifier of the credential.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the identifier of the credential returned by the authenticator.
	 *
	 * @param id The identifier of the credential.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the raw identifier of the credential returned by the authenticator.
	 *
	 * @return The raw identifier of the credential.
	 */
	public String getRawId() {
		return rawId;
	}

	/**
	 * Set the raw identifier of the credential returned by the authenticator.
	 *
	 * @param rawId The raw identifier of the credential.
	 */
	public void setRawId(String rawId) {
		this.rawId = rawId;
	}

	/**
	 * Get the response data returned by the authenticator.
	 *
	 * @return The response data returned by the authenticator.
	 */
	public AuthenticatorAttestationResponse getResponse() {
		return response;
	}

	/**
	 * Set the response data returned by the authenticator.
	 *
	 * @param response The response data returned by the authenticator.
	 */
	public RegistrationResponse setResponse(AuthenticatorAttestationResponse response) {
		this.response = response;
		return this;
	}

	/**
	 * Get the attachment modality of the authenticator, if available.
	 *
	 * @return The attachment modality of the authenticator.
	 */
	public AuthenticatorAttachment getAuthenticatorAttachment() {
		return authenticatorAttachment;
	}

	/**
	 * Set the attachment modality of the authenticator.
	 *
	 * @param authenticatorAttachment The attachment modality of the authenticator.
	 */
	public RegistrationResponse setAuthenticatorAttachment(
			AuthenticatorAttachment authenticatorAttachment) {
		this.authenticatorAttachment = authenticatorAttachment;
		return this;
	}

	/**
	 * Get the client extension results.
	 *
	 * @return The client extension results.
	 */
	public AuthenticationExtensionsOutput getClientExtensionResults() {
		return clientExtensionResults;
	}

	/**
	 * Set the client extension results.
	 *
	 * @param clientExtensionResults The client extension results.
	 */
	public RegistrationResponse setClientExtensionResults(
			AuthenticationExtensionsOutput clientExtensionResults) {
		this.clientExtensionResults = clientExtensionResults;
		return this;
	}

	/**
	 * Get the type of the credential returned by the authenticator.
	 *
	 * @return The type of the credential.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of the credential returned by the authenticator.
	 *
	 * @param type The type of the credential.
	 */
	public RegistrationResponse setType(String type) {
		this.type = type;
		return this;
	}
}
