package com.webforj.addons.services.webauthn.data;

/**
 * Represents parameters for creating a new public key credential. These parameters include the
 * cryptographic algorithm and the type of credential to be created.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3#dictdef-publickeycredentialparameters">§5.3.
 *      Parameters for Credential Generation (dictionary PublicKeyCredentialParameters)</a>
 */
public class PublicKeyCredentialParameters {

  /** The cryptographic algorithm to be used with the newly generated credential. */
  private int alg;

  /** The type of credential to be created. */
  private String type;

  /**
   * Constructs a PublicKeyCredentialParameters object with the specified algorithm.
   *
   * @param alg The cryptographic algorithm to be used.
   */
  public PublicKeyCredentialParameters(COSEAlgorithmIdentifier alg) {
    this.alg = alg.getValue();
    this.type = "public-key";
  }

  /**
   * Gets the cryptographic algorithm associated with these parameters.
   *
   * @return The cryptographic algorithm.
   */
  public int getAlg() {
    return alg;
  }

  /**
   * Sets the cryptographic algorithm for these parameters.
   *
   * @param alg The cryptographic algorithm to be set.
   */
  public void setAlg(int alg) {
    this.alg = alg;
  }

  /**
   * Gets the type of credential associated with these parameters.
   *
   * @return The credential type.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type of credential for these parameters.
   *
   * @param type The credential type to be set.
   */
  public void setType(String type) {
    this.type = type;
  }
}
