package com.webforj.addons.services.webauthn.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.util.List;

/**
 * Parameters for a call to {@code navigator.credentials.get()}.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#dictdef-publickeycredentialcreationoptions">§5.4.
 *      Options for Credential Creation (dictionary
 *      PublicKeyCredentialGetOptions)</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class PublicKeyCredentialGetOptions {

	/**
	 * A challenge provided by the relying party, which the selected authenticator
	 * signs along with other data when generating an authentication assertion. This
	 * helps prevent replay attacks and ensures the authenticity of the request.
	 *
	 * @see <a href=
	 *      "https://www.w3.org/TR/webauthn-3/#sctn-cryptographic-challenges">§13.1
	 *      Cryptographic Challenges</a>
	 */
	private final String challenge;

	/**
	 * Specifies the time, in milliseconds, that the caller is willing to wait for
	 * the operation to complete. This is treated as a hint and may be overridden by
	 * the client.
	 */
	private long timeout = 300000L;

	/**
	 * Specifies the identifier claimed by the relying party. If omitted, the value
	 * will be set by the client.
	 */
	private String rpId;

	/**
	 * A list of {@link PublicKeyCredentialDescriptor} objects representing public
	 * key credentials acceptable to the caller. The list is ordered by the caller's
	 * preference, with the most preferred credential first.
	 */
	private List<PublicKeyCredentialDescriptor> allowCredentials;

	/**
	 * Describes the relying party's requirements regarding user verification for
	 * the {@code navigator.credentials.get()} operation. Eligible authenticators
	 * are filtered based on this requirement. If not set, the default in the
	 * browser is {@link UserVerificationRequirement#PREFERRED}.
	 *
	 * @see UserVerificationRequirement
	 * @see <a href=
	 *      "https://www.w3.org/TR/webauthn-3/#enum-userVerificationRequirement">§5.8.6.
	 *      User Verification Requirement Enumeration (enum
	 *      UserVerificationRequirement)</a>
	 * @see <a href="https://www.w3.org/TR/webauthn-3/#user-verification">User
	 *      Verification</a>
	 */
	private UserVerificationRequirement userVerification;

	/**
	 * Additional parameters requesting additional processing by the client and
	 * authenticator.
	 *
	 * <p>
	 * For example, if transaction confirmation is sought from the user, then the
	 * prompt string might be included as an extension.
	 */
	private AuthenticationExtensionsInput extensions;

	/**
	 * Constructs a new instance of PublicKeyCredentialGetOptions.
	 */
	public PublicKeyCredentialGetOptions() {
		this.challenge = ByteArray.generateRandom().getBase64Url();
	}

	/**
	 * Converts this PublicKeyCredentialGetOptions instance to its JSON
	 * representation.
	 *
	 * @return JSON representation of this PublicKeyCredentialGetOptions.
	 */
	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	/**
	 * Converts a JSON string to a PublicKeyCredentialGetOptions instance.
	 *
	 * @param json JSON string representing PublicKeyCredentialGetOptions.
	 * @return PublicKeyCredentialGetOptions instance parsed from JSON.
	 * @throws JsonParseException If the JSON string is invalid.
	 */
	public static PublicKeyCredentialGetOptions fromJson(String json) throws JsonParseException {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, PublicKeyCredentialGetOptions.class);
	}

	/**
	 * Retrieves the challenge provided by the relying party.
	 *
	 * @return The challenge ByteArray.
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * Retrieves the timeout value.
	 *
	 * @return The timeout value.
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Sets the timeout value.
	 *
	 * @param timeout The timeout value.
	 */
	public PublicKeyCredentialGetOptions setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Retrieves the relying party's identifier.
	 *
	 * @return The relying party's identifier.
	 */
	public String getRpId() {
		return rpId;
	}

	/**
	 * Sets the relying party's identifier.
	 *
	 * @param rpId The relying party's identifier.
	 */
	public PublicKeyCredentialGetOptions setRpId(String rpId) {
		this.rpId = rpId;
		return this;
	}

	/**
	 * Retrieves the list of public key credentials acceptable to the caller.
	 *
	 * @return The list of public key credential descriptors.
	 */
	public List<PublicKeyCredentialDescriptor> getAllowCredentials() {
		return allowCredentials;
	}

	/**
	 * Sets the list of public key credentials acceptable to the caller.
	 *
	 * @param allowCredentials The list of public key credential descriptors.
	 */
	public PublicKeyCredentialGetOptions setAllowCredentials(
			List<PublicKeyCredentialDescriptor> allowCredentials) {
		this.allowCredentials = allowCredentials;
		return this;
	}

	/**
	 * Retrieves the user verification requirement.
	 *
	 * @return The user verification requirement.
	 */
	public UserVerificationRequirement getUserVerification() {
		return userVerification;
	}

	/**
	 * Sets the user verification requirement.
	 *
	 * @param userVerification The user verification requirement.
	 */
	public PublicKeyCredentialGetOptions setUserVerification(
			UserVerificationRequirement userVerification) {
		this.userVerification = userVerification;
		return this;
	}

	/**
	 * Retrieves the additional parameters requesting additional processing by the
	 * client and authenticator.
	 *
	 * @return The authentication extensions input.
	 */
	public AuthenticationExtensionsInput getExtensions() {
		return extensions;
	}

	/**
	 * Sets the additional parameters requesting additional processing by the client
	 * and authenticator.
	 *
	 * @param extensions The authentication extensions input.
	 */
	public PublicKeyCredentialGetOptions setExtensions(AuthenticationExtensionsInput extensions) {
		this.extensions = extensions;
		return this;
	}
}
