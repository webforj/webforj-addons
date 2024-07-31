package com.webforj.addons.services.webauthn.data;

/**
 * Represents the response returned by the authenticator during an assertion
 * process. This class encapsulates the data returned by the authenticator and
 * is used to facilitate the communication of authentication responses between
 * the client and server.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#dictdef-authenticatorassertionresponsejson">
 *      §5.5 JSON Type Representations</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class AuthenticatorAssertionResponse {

	/**
	 * The client data JSON, encoded as a Base64URLString. This contains the data
	 * passed to the authenticator by the client in order to generate the assertion.
	 */
	private String clientDataJSON;

	/**
	 * The authenticator data, encoded as a Base64URLString. This contains
	 * information about the authenticator and the context of the assertion.
	 */
	private String authenticatorData;

	/**
	 * The signature, encoded as a Base64URLString. This is the signature generated
	 * by the authenticator over the client data hash.
	 */
	private String signature;

	/**
	 * The user handle, if available, encoded as a Base64URLString. This is an
	 * identifier for the user associated with the authenticator.
	 */
	private String userHandle;

	/**
	 * Retrieves the client data JSON.
	 *
	 * @return The client data JSON.
	 */
	public String getClientDataJSON() {
		return clientDataJSON;
	}

	/**
	 * Sets the client data JSON.
	 *
	 * @param clientDataJSON The client data JSON.
	 */
	public void setClientDataJSON(String clientDataJSON) {
		this.clientDataJSON = clientDataJSON;
	}

	/**
	 * Retrieves the authenticator data.
	 *
	 * @return The authenticator data.
	 */
	public String getAuthenticatorData() {
		return authenticatorData;
	}

	/**
	 * Sets the authenticator data.
	 *
	 * @param authenticatorData The authenticator data.
	 */
	public void setAuthenticatorData(String authenticatorData) {
		this.authenticatorData = authenticatorData;
	}

	/**
	 * Retrieves the signature.
	 *
	 * @return The signature.
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * Sets the signature.
	 *
	 * @param signature The signature.
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * Retrieves the user handle.
	 *
	 * @return The user handle.
	 */
	public String getUserHandle() {
		return userHandle;
	}

	/**
	 * Sets the user handle.
	 *
	 * @param userHandle The user handle.
	 */
	public void setUserHandle(String userHandle) {
		this.userHandle = userHandle;
	}
}
