package com.webforj.addons.services.webauthn.data;

import java.util.List;

/**
 * Represents a slightly-modified AuthenticatorAttestationResponse to simplify working with
 * ArrayBuffers that are Base64URL-encoded in the browser.
 *
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#iface-authenticatorattestationresponse"> 5.2.1.
 *     Information About Public Key Credential</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public class AuthenticatorAttestationResponse {
  /**
   * Contains the JSON-compatible serialization of client data passed to the authenticator by the
   * client in order to generate this credential. The exact JSON serialization MUST be preserved, as
   * the hash of the serialized client data has been computed over it.
   */
  private String clientDataJson;

  /**
   * Contains an attestation object, which is opaque to, and cryptographically protected against
   * tampering by, the client. The attestation object contains both authenticator data and an
   * attestation statement.
   */
  private String attestationObject;

  /** Contains the authenticator data contained within attestationObject. */
  private String authenticatorData;

  /** Represents the transports that the authenticator is believed to support. */
  private List<AuthenticatorTransport> transports;

  /** Represents the COSE algorithm identifier of the new credential. */
  private int publicKeyAlgorithm;

  /**
   * Represents the DER-encoded SubjectPublicKeyInfo structure associated with the new credential.
   *
   * <p>This field contains the cryptographic representation of the public key used by the new
   * credential, encoded in DER format according to X.509 standards. The SubjectPublicKeyInfo
   * structure encapsulates essential details about the public key, including the algorithm type,
   * parameters, and the key itself.
   *
   * <p>Accessing this field allows for cryptographic operations, such as signature verification and
   * authentication, within the WebAuthn. It serves as a crucial component in validating the
   * authenticity and integrity of credentials during authentication processes.
   */
  private String publicKey;

  /**
   * Retrieves the JSON-compatible serialization of client data passed to the authenticator.
   *
   * @return The client data JSON.
   */
  public String getClientDataJson() {
    return clientDataJson;
  }

  /**
   * Sets the JSON-compatible serialization of client data passed to the authenticator.
   *
   * @param clientDataJson The client data JSON.
   */
  public AuthenticatorAttestationResponse setClientDataJson(String clientDataJson) {
    this.clientDataJson = clientDataJson;
    return this;
  }

  /**
   * Retrieves the attestation object containing authenticator data and an attestation statement.
   *
   * @return The attestation object.
   */
  public String getAttestationObject() {
    return attestationObject;
  }

  /**
   * Sets the attestation object containing authenticator data and an attestation statement.
   *
   * @param attestationObject The attestation object.
   */
  public AuthenticatorAttestationResponse setAttestationObject(String attestationObject) {
    this.attestationObject = attestationObject;
    return this;
  }

  /**
   * Retrieves the authenticator data contained within attestationObject.
   *
   * @return The authenticator data.
   */
  public String getAuthenticatorData() {
    return authenticatorData;
  }

  /**
   * Sets the authenticator data contained within attestationObject.
   *
   * @param authenticatorData The authenticator data.
   */
  public AuthenticatorAttestationResponse setAuthenticatorData(String authenticatorData) {
    this.authenticatorData = authenticatorData;
    return this;
  }

  /**
   * Retrieves the transports that the authenticator is believed to support.
   *
   * @return The array of authenticator transports.
   */
  public List<AuthenticatorTransport> getTransports() {
    return transports;
  }

  /**
   * Sets the transports that the authenticator is believed to support.
   *
   * @param transports The array of authenticator transports.
   */
  public AuthenticatorAttestationResponse setTransports(List<AuthenticatorTransport> transports) {
    this.transports = transports;
    return this;
  }

  /**
   * Retrieves the COSE algorithm identifier of the new credential.
   *
   * @return The COSE algorithm identifier.
   */
  public int getPublicKeyAlgorithm() {
    return publicKeyAlgorithm;
  }

  /**
   * Sets the COSE algorithm identifier of the new credential.
   *
   * @param publicKeyAlgorithm The COSE algorithm identifier.
   */
  public AuthenticatorAttestationResponse setPublicKeyAlgorithm(int publicKeyAlgorithm) {
    this.publicKeyAlgorithm = publicKeyAlgorithm;
    return this;
  }

  /**
   * Retrieves the DER-encoded SubjectPublicKeyInfo structure associated with the new credential.
   *
   * @return The DER-encoded SubjectPublicKeyInfo structure as a string.
   */
  public String getPublicKey() {
    return publicKey;
  }

  /**
   * Sets the DER-encoded SubjectPublicKeyInfo structure associated with the new credential.
   *
   * @param publicKey The DER-encoded SubjectPublicKeyInfo structure to set.
   */
  public AuthenticatorAttestationResponse setPublicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }
}
