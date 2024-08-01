package com.webforj.addons.services.webauthn;

import com.webforj.Page;
import com.webforj.PendingResult;
import com.webforj.component.html.HtmlComponent;
import com.webforj.component.html.elements.Div;
import com.webforj.addons.services.webauthn.data.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.List;

/**
 * RelyingParty class facilitates Web Authentication operations, including
 * registration and authentication, by invoking
 * {@code navigator.credentials.get()} and
 * {@code navigator.credentials.create()} in the client-side JavaScript
 * environment. This class adheres to the Web Authentication specification
 * defined by W3C.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/">Web Authentication
 *      (WebAuthn) Level 3</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class RelyingParty {

	// FIXME: Should be used temporarily
	/**
	 * Represents any HTML element to execute JavaScript functions. There should be
	 * other ways provided for executing async JavaScript in the client, until then,
	 * we will use a random element.
	 */
	public final Div anyElement = new Div();

	/**
	 * The identity of the relying party associated with this instance. This field
	 * represents the identity information of the relying party (RP) with which this
	 * instance is associated. The relying party identity typically includes details
	 * such as the RP's name, ID, and potentially other identifying information.
	 *
	 * @see <a href="https://www.w3.org/TR/webauthn-3/#relying-party">Relying
	 *      Party</a>
	 */
	private final RelyingPartyIdentity relyingPartyIdentity;

	/**
	 * Constructs a new RelyingParty instance with the specified relying party
	 * identity.
	 *
	 * <p>
	 * This constructor initializes a new instance of the RelyingParty class with
	 * the provided relying party identity. The relying party identity contains
	 * information about the relying party (RP) associated with this RelyingParty
	 * instance, such as the RP's name, ID, and potentially other identifying
	 * information.
	 * </p>
	 */
	public RelyingParty(RelyingPartyIdentity relyingPartyIdentity) {
		this.relyingPartyIdentity = relyingPartyIdentity;
		Page.getCurrent().addInlineJavaScript("context://services/webauthn.js");
	}

	/**
	 * Initiates a registration request with the provided options.
	 *
	 * @param registerOptions The {@code PublicKeyCredentialCreationOptions}
	 *            specifying parameters for registration.
	 * @return A PendingResult containing the {@code RegistrationResponse}
	 *         asynchronously obtained from the client.
	 */
	public PendingResult<RegistrationResponse> register(
			PublicKeyCredentialCreationOptions registerOptions) {
		registerOptions.setRp(this.relyingPartyIdentity).setPubKeyCredParams(
				this.filterAvailableAlgorithms(registerOptions.getPubKeyCredParams()));
		String options = registerOptions.toJson();
		return this.anyElement.getElement()
				.executeJsAsync("window.dwcWebAuthn.register(%s);".formatted(options))
				.thenApply(response -> RegistrationResponse.fromJson(response.toString()))
				.thenApply(this::validateRegistrationResponse);
	}

	/**
	 * Initiates an authentication request with the provided options.
	 *
	 * @param authenticateOptions The {@code PublicKeyCredentialGetOptions}
	 *            specifying parameters for authentication.
	 * @param autofill A boolean indicating whether autofill is enabled.
	 * @return A PendingResult containing the {@code AuthenticationResponse}
	 *         asynchronously obtained from the client.
	 */
	public PendingResult<AuthenticationResponse> authenticate(
			PublicKeyCredentialGetOptions authenticateOptions, boolean autofill) {
		authenticateOptions.setRpId(relyingPartyIdentity.getId());
		String options = authenticateOptions.toJson();
		return this.anyElement.getElement()
				.executeJsAsync(
						"window.dwcWebAuthn.authenticate(%s, %b)".formatted(options, autofill))
				.thenApply(response -> AuthenticationResponse.fromJson(response.toString()))
				.thenApply(this::validateAuthenticationResponse);
	}

	/**
	 * Initiates an authentication request with the provided options but sets
	 * autofill to false by default
	 *
	 * @param authenticateOptions The {@code PublicKeyCredentialGetOptions}
	 *            specifying parameters for authentication.
	 * @return A PendingResult containing the {@code AuthenticationResponse}
	 *         asynchronously obtained from the client.
	 */
	public PendingResult<AuthenticationResponse> authenticate(
			PublicKeyCredentialGetOptions authenticateOptions) {
		return this.authenticate(authenticateOptions, false);
	}

	/**
	 * Cancels an active WebAuthn operation, it can be authentication or
	 * registration, the last operation reference will always be stored and can have
	 * only one active operation at a time.
	 * <p>
	 * Therefore, canceling a ceremony aborts the last operation which is also the
	 * only active one.
	 */
	public void cancelCeremony() {
		Page.getCurrent().executeJs("window.dwcWebAuthn.webAuthnAbort.cancelCeremony()");
	}

	/**
	 * Checks whether the current browser environment supports the Web
	 * Authentication API (WebAuthn).
	 *
	 * @return True if WebAuthn is supported in the current environment, false
	 *         otherwise.
	 */
	public static boolean isWebAuthnSupported() {
		return (boolean) Page.getCurrent()
				.executeJs("window.dwcWebAuthn.browserSupportsWebAuthn()");
	}

	/**
	 * Checks whether the current browser environment supports Web Authentication
	 * API (WebAuthn) autofill functionality.
	 *
	 * @return True if WebAuthn autofill is supported, false otherwise.
	 */
	public static PendingResult<Boolean> isWebAuthnAutofillSupported(
			HtmlComponent<Div> anyElement) {
		return anyElement.getElement()
				.executeJsAsync("window.dwcWebAuthn.browserSupportsWebAuthnAutofill()")
				.thenApply(String::valueOf).thenApply(Boolean::parseBoolean);
	}

	/**
	 * Checks whether a platform authenticator is available for use in the current
	 * browser environment.
	 *
	 * @return True if a platform authenticator is available, false otherwise.
	 */
	public static PendingResult<Boolean> isPlatformAuthenticatorAvailable(
			HtmlComponent<Div> anyElement) {
		return anyElement.getElement()
				.executeJsAsync("window.dwcWebAuthn.platformAuthenticatorIsAvailable()")
				.thenApply(String::valueOf).thenApply(Boolean::parseBoolean);
	}

	/**
	 * Validates the registration response received from the client.
	 *
	 * @param response The {@code RegistrationResponse} to validate.
	 * @return The validated {@code RegistrationResponse} if successful.
	 * @throws RuntimeException If validation fails due to missing or unexpected
	 *             data in the response.
	 */
	public RegistrationResponse validateRegistrationResponse(RegistrationResponse response)
			throws RuntimeException {
		ClientDataJSON clientDataJSON = ClientDataJSON
				.fromBase64Url(response.getResponse().getClientDataJSON());

		if (response.getId() == null) {
			throw new RuntimeException("Missing credential id");
		}

		if (!response.getId().equals(response.getRawId())) {
			throw new RuntimeException("Credential ID was not base64url-encoded");
		}

		if (!response.getType().equals("public-key")) {
			throw new RuntimeException(
					"Unexpected credential type " + response.getType() + ", expected 'public-key'");
		}

		if (!clientDataJSON.getType().equals("webauthn.create")) {
			throw new RuntimeException(
					"Unexpected registration response type \"%s\", expected one of: webauthn.create"
							.formatted(clientDataJSON.getType()));
		}

		if (clientDataJSON.getChallenge() == null) {
			throw new RuntimeException("Challenge cannot be null");
		}

		if (clientDataJSON.getOrigin().equals(this.relyingPartyIdentity.getId())) {
			throw new RuntimeException(
					"Unexpected registration response origin \"%s\", expected \"%s\"".formatted(
							clientDataJSON.getOrigin(), this.relyingPartyIdentity.getId()));
		}

		if (response.getResponse().getAttestationObject() == null) {
			throw new RuntimeException("attestationObject cannot be null");
		}

		COSEAlgorithmIdentifier.fromValue(response.getResponse().getPublicKeyAlgorithm());

		return response;
	}

	/**
	 * Validates the authentication response received from the client.
	 *
	 * @param response The {@code AuthenticationResponse} to validate.
	 * @return The validated {@code AuthenticationResponse} if successful.
	 * @throws RuntimeException If validation fails due to missing or unexpected
	 *             data in the response.
	 */
	public AuthenticationResponse validateAuthenticationResponse(AuthenticationResponse response)
			throws RuntimeException {
		ClientDataJSON clientDataJSON = ClientDataJSON
				.fromBase64Url(response.getResponse().getClientDataJSON());

		if (response.getId() == null) {
			throw new RuntimeException("Missing credential id");
		}

		if (!response.getId().equals(response.getRawId())) {
			throw new RuntimeException("Credential ID was not base64url-encoded");
		}

		if (!response.getType().equals("public-key")) {
			throw new RuntimeException(
					"Unexpected credential type " + response.getType() + ", expected 'public-key'");
		}

		if (!clientDataJSON.getType().equals("webauthn.get")) {
			throw new RuntimeException(
					"Unexpected registration response type \"%s\", expected one of: webauthn.create"
							.formatted(clientDataJSON.getType()));
		}

		if (clientDataJSON.getChallenge() == null) {
			throw new RuntimeException("Challenge cannot be null");
		}

		if (clientDataJSON.getOrigin().equals(this.relyingPartyIdentity.getId())) {
			throw new RuntimeException(
					"Unexpected registration response origin \"%s\", expected \"%s\"".formatted(
							clientDataJSON.getOrigin(), this.relyingPartyIdentity.getId()));
		}

		return response;
	}

	/**
	 * Filters the list of {@code PublicKeyCredentialParameters} to include only
	 * algorithms for which both {@link KeyFactory} and {@link Signature} are
	 * available, and logs warnings for any unsupported algorithms.
	 *
	 * @param pubKeyCredParams The list of {@code PublicKeyCredentialParameters} to
	 *            filter.
	 * @return A new {@link List} containing only the supported algorithms in the
	 *         current Java Cryptography Architecture (JCA) context.
	 */
	private List<PublicKeyCredentialParameters> filterAvailableAlgorithms(
			List<PublicKeyCredentialParameters> pubKeyCredParams) {
		return pubKeyCredParams.stream().filter(this::isAlgorithmSupported).toList();
	}

	/**
	 * Validates whether a given algorithm is supported by checking if a
	 * {@link KeyFactory} is available for generating keys and a {@link Signature}
	 * is available for signing.
	 *
	 * @param param The {@code PublicKeyCredentialParameters} containing the
	 *            algorithm to validate.
	 * @return {@code true} if the algorithm is supported, {@code false} otherwise.
	 */
	private boolean isAlgorithmSupported(PublicKeyCredentialParameters param) {
		try {
			validateKeyFactory(param);
			validateSignature(param);
			return true;
		} catch (NoSuchAlgorithmException e) {
			System.out.println(String.valueOf(param.getAlg()) + e);
			return false;
		}
	}

	/**
	 * Validates whether a {@link KeyFactory} is available for the given algorithm.
	 *
	 * @param param The {@code PublicKeyCredentialParameters} containing the
	 *            algorithm to validate.
	 * @throws NoSuchAlgorithmException If no {@link KeyFactory} is available for
	 *             the algorithm.
	 */
	private void validateKeyFactory(PublicKeyCredentialParameters param)
			throws NoSuchAlgorithmException {
		switch (COSEAlgorithmIdentifier.fromValue(param.getAlg())) {
			case EdDSA :
				KeyFactory.getInstance("EdDSA");
				break;
			case ES256 :
			case ES384 :
			case ES512 :
				KeyFactory.getInstance("EC");
				break;
			case RS256 :
			case RS384 :
			case RS512 :
			case RS1 :
				KeyFactory.getInstance("RSA");
				break;
			default :
				throw new NoSuchAlgorithmException("Unsupported algorithm: " + param.getAlg());
		}
	}

	/**
	 * Validates whether a {@link Signature} is available for the given algorithm.
	 *
	 * @param param The {@code PublicKeyCredentialParameters} containing the
	 *            algorithm to validate.
	 * @throws NoSuchAlgorithmException If no {@link Signature} is available for the
	 *             algorithm.
	 */
	private void validateSignature(PublicKeyCredentialParameters param)
			throws NoSuchAlgorithmException {
		int alg = param.getAlg();
		COSEAlgorithmIdentifier algorithmIdentifier = COSEAlgorithmIdentifier.fromValue(alg);
		Signature.getInstance(algorithmIdentifier.getJavaAlgorithmName());
	}
}
