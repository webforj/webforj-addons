package com.webforj.addons.services.webauthn.data;

/**
 * Represents the output of client extension processing for Credential
 * Properties extension during WebAuthn operations.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3/#dictdef-credentialpropertiesoutput">CredentialPropertiesOutput</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class CredentialPropertiesOutput {

	/**
	 * Property indicating whether the {@code PublicKeyCredential} returned as a
	 * result of a registration ceremony is a client-side discoverable credential.
	 * If true, the credential is a discoverable credential. If false, the
	 * credential is a server-side credential.
	 */
	private boolean rk;

	/**
	 * Constructs a new instance of CredentialPropertiesOutput.
	 *
	 * @param rk A boolean indicating whether the credential is a client-side
	 *            discoverable credential.
	 */
	public CredentialPropertiesOutput(boolean rk) {
		this.rk = rk;
	}

	/**
	 * Retrieves the boolean indicating whether the credential is a client-side
	 * discoverable credential.
	 *
	 * @return True if the credential is a discoverable credential, otherwise false.
	 */
	public boolean isRk() {
		return rk;
	}

	/**
	 * Sets the boolean indicating whether the credential is a client-side
	 * discoverable credential.
	 *
	 * @param rk True if the credential is a discoverable credential, otherwise
	 *            false.
	 */
	public void setRk(boolean rk) {
		this.rk = rk;
	}
}
