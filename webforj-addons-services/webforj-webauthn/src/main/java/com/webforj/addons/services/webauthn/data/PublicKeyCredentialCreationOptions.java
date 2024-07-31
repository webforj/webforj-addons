package com.webforj.addons.services.webauthn.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.util.List;

/**
 * Represents parameters for creating a new public key credential. These
 * parameters include the cryptographic algorithm and the type of credential to
 * be created.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#dictdef-publickeycredentialcreationoptions">
 *      §5.4. Options for Credential Creation (dictionary
 *      PublicKeyCredentialCreationOptions)</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class PublicKeyCredentialCreationOptions {

	/**
	 * Information about the Relying Party (RP) responsible for the request.
	 *
	 * <p>
	 * The {@code rp} field contains data about the Relying Party, including its
	 * name and unique identifier. It identifies the entity initiating the
	 * authentication process. For further details, refer to
	 * {@code RelyingPartyIdentity}.
	 * </p>
	 */
	private RelyingPartyIdentity rp;

	/**
	 * Details about the user account associated with the request.
	 *
	 * <p>
	 * The {@code user} field holds information regarding the user account, such as
	 * username, display name, and user handle. It provides essential data for
	 * linking public key credentials with the user's identity.
	 * </p>
	 */
	private final UserIdentity user;

	/**
	 * A challenge used for generating the attestation object of the newly created
	 * credential.
	 *
	 * <p>
	 * The {@code challenge} field represents a cryptographic challenge intended to
	 * prevent replay attacks during the credential creation process. Refer to the
	 * <a href="https://www.w3.org/TR/webauthn-3/#sctn-cryptographic-challenges">
	 * §13.1 Cryptographic Challenges</a> security consideration for further
	 * information.
	 * </p>
	 */
	private final String challenge;

	/**
	 * Defines the preferred properties of the credential to be created.
	 *
	 * <p>
	 * The {@code pubKeyCredParams} field specifies the desired characteristics of
	 * the credential, such as cryptographic algorithms and key types. It represents
	 * a sequence ordered from most preferred to least preferred, allowing the
	 * client to make the best effort in creating the most preferred credential
	 * possible.
	 * </p>
	 */
	private List<PublicKeyCredentialParameters> pubKeyCredParams = List.of(
			new PublicKeyCredentialParameters(COSEAlgorithmIdentifier.EdDSA),
			new PublicKeyCredentialParameters(COSEAlgorithmIdentifier.ES256),
			new PublicKeyCredentialParameters(COSEAlgorithmIdentifier.RS256));

	/**
	 * Indicates the maximum time, in milliseconds, that the caller is willing to
	 * wait for the operation to complete.
	 *
	 * <p>
	 * The {@code timeout} field serves as a hint, suggesting the maximum duration
	 * for the credential creation operation.
	 * </p>
	 */
	private long timeout = 300000L;

	/**
	 * Specifies the credentials that should not be used for the operation.
	 *
	 * <p>
	 * The {@code excludeCredentials} field contains a set of public key credentials
	 * that the Relying Party excludes from use during credential creation. It helps
	 * prevent the creation of multiple credentials for the same account on a single
	 * authenticator.
	 * </p>
	 */
	private List<PublicKeyCredentialDescriptor> excludeCredentials;

	/**
	 * Defines criteria for selecting authenticators to participate in the
	 * operation.
	 *
	 * <p>
	 * The {@code authenticatorSelection} field specifies preferences for
	 * authenticator selection, including factors such as attachment modality and
	 * user verification requirement.
	 * </p>
	 */
	private AuthenticatorSelectionCriteria authenticatorSelection;

	/**
	 * Indicates the Relying Party's preference for attestation conveyance.
	 *
	 * <p>
	 * The {@code attestation} field expresses the Relying Party's preference
	 * regarding the conveyance of attestation during credential creation.
	 * </p>
	 */
	private AttestationConveyancePreference attestation;

	/**
	 * Provides additional parameters for client and authenticator processing.
	 *
	 * <p>
	 * The {@code extensions} field offers additional input data for authentication
	 * extensions used during credential creation. It allows for customization and
	 * extension of the credential creation process with domain-specific
	 * functionality. For a list of registered WebAuthn Extensions, consult the IANA
	 * "WebAuthn Extension Identifier" registry.
	 * </p>
	 */
	private AuthenticationExtensionsInput extensions;

	/**
	 * Constructs a new instance of {@code PublicKeyCredentialCreationOptions}.
	 */
	public PublicKeyCredentialCreationOptions(UserIdentity user) {
		this.user = user;
		this.challenge = ByteArray.generateRandom().getBase64Url();
	}

	/**
	 * Converts this {@code PublicKeyCredentialCreationOptions} instance to its JSON
	 * representation.
	 *
	 * @return JSON representation of this
	 *         {@code PublicKeyCredentialCreationOptions}.
	 */
	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	/**
	 * Converts a JSON string to a {@code PublicKeyCredentialCreationOptions}
	 * instance.
	 *
	 * @param json JSON string representing
	 *            {@code PublicKeyCredentialCreationOptions}.
	 * @return {@code PublicKeyCredentialCreationOptions} instance parsed from JSON.
	 * @throws JsonParseException If the JSON string is invalid.
	 */
	public static PublicKeyCredentialCreationOptions fromJson(String json)
			throws JsonParseException {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, PublicKeyCredentialCreationOptions.class);
	}

	/**
	 * Retrieves information about the Relying Party (RP) responsible for the
	 * request.
	 *
	 * @return Data about the Relying Party.
	 */
	public RelyingPartyIdentity getRp() {
		return rp;
	}

	/**
	 * Sets information about the Relying Party (RP) responsible for the request.
	 *
	 * @param rp Data about the Relying Party.
	 */
	public PublicKeyCredentialCreationOptions setRp(RelyingPartyIdentity rp) {
		this.rp = rp;
		return this;
	}

	/**
	 * Retrieves details about the user account associated with the request.
	 *
	 * @return Information about the user account.
	 */
	public UserIdentity getUser() {
		return user;
	}

	/**
	 * Retrieves the challenge used for generating the attestation object of the
	 * newly created credential.
	 *
	 * @return The challenge.
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * Retrieves the preferred properties of the credential to be created.
	 *
	 * @return The list of preferred properties.
	 */
	public List<PublicKeyCredentialParameters> getPubKeyCredParams() {
		return pubKeyCredParams;
	}

	/**
	 * Sets the preferred properties of the credential to be created.
	 *
	 * @param pubKeyCredParams The list of preferred properties.
	 */
	public PublicKeyCredentialCreationOptions setPubKeyCredParams(
			List<PublicKeyCredentialParameters> pubKeyCredParams) {
		this.pubKeyCredParams = pubKeyCredParams;
		return this;
	}

	/**
	 * Retrieves the maximum time, in milliseconds, that the caller is willing to
	 * wait for the operation to complete.
	 *
	 * @return The timeout value.
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * Sets the maximum time, in milliseconds, that the caller is willing to wait
	 * for the operation to complete.
	 *
	 * @param timeout The timeout value.
	 */
	public PublicKeyCredentialCreationOptions setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Retrieves the set of credentials that should not be used for the operation.
	 *
	 * @return The set of excluded credentials.
	 */
	public List<PublicKeyCredentialDescriptor> getExcludeCredentials() {
		return excludeCredentials;
	}

	/**
	 * Sets the set of credentials that should not be used for the operation.
	 *
	 * @param excludeCredentials The set of excluded credentials.
	 */
	public PublicKeyCredentialCreationOptions setExcludeCredentials(
			List<PublicKeyCredentialDescriptor> excludeCredentials) {
		this.excludeCredentials = excludeCredentials;
		return this;
	}

	/**
	 * Retrieves the criteria for selecting authenticators to participate in the
	 * operation.
	 *
	 * @return The authenticator selection criteria.
	 */
	public AuthenticatorSelectionCriteria getAuthenticatorSelection() {
		return authenticatorSelection;
	}

	/**
	 * Sets the criteria for selecting authenticators to participate in the
	 * operation.
	 *
	 * @param authenticatorSelection The authenticator selection criteria.
	 */
	public PublicKeyCredentialCreationOptions setAuthenticatorSelection(
			AuthenticatorSelectionCriteria authenticatorSelection) {
		this.authenticatorSelection = authenticatorSelection;
		return this;
	}

	/**
	 * Retrieves the Relying Party's preference for attestation conveyance.
	 *
	 * @return The attestation preference.
	 */
	public AttestationConveyancePreference getAttestation() {
		return attestation;
	}

	/**
	 * Sets the Relying Party's preference for attestation conveyance.
	 *
	 * @param attestation The attestation preference.
	 */
	public PublicKeyCredentialCreationOptions setAttestation(
			AttestationConveyancePreference attestation) {
		this.attestation = attestation;
		return this;
	}

	/**
	 * Retrieves additional parameters for client and authenticator processing.
	 *
	 * @return The authentication extensions input.
	 */
	public AuthenticationExtensionsInput getExtensions() {
		return extensions;
	}

	/**
	 * Sets additional parameters for client and authenticator processing.
	 *
	 * @param extensions The authentication extensions input.
	 */
	public PublicKeyCredentialCreationOptions setExtensions(
			AuthenticationExtensionsInput extensions) {
		this.extensions = extensions;
		return this;
	}
}
