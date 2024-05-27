package com.webforj.addons.services.webauthn.data;

import java.util.EnumSet;

/**
 * This class represents the attributes specified by a caller when referring to a public key
 * credential as an input parameter to the {@code navigator.credentials.create()} or
 * {@code navigator.credentials.get()} methods. It mirrors the fields of the PublicKeyCredential
 * object returned by the latter methods.
 *
 * <p>
 * The {@code PublicKeyCredentialDescriptor} object is used to describe the credentials to be used
 * or retrieved. It includes the type of the credential, the credential ID, and an optional hint as
 * to how the client might communicate with the managing authenticator of the public key credential.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#dictdef-publickeycredentialdescriptor"> §5.10.3.
 *      Credential Descriptor (dictionary PublicKeyCredentialDescriptor)</a>
 */
public class PublicKeyCredentialDescriptor {

  /**
   * The type of the credential the caller is referring to.
   */
  private final String type = "public-key";

  /**
   * The credential ID of the public key credential the caller is referring to.
   */
  private final String id;


  /**
   * An optional hint as to how the client might communicate with the managing authenticator of the
   * public key credential the caller is referring to.
   *
   * <p>
   * This should be stored along with the ID and used unmodified whenever creating a
   * {@code PublicKeyCredentialDescriptor} for this credential.
   */
  private EnumSet<AuthenticatorTransport> transports;

  /**
   * Creates a new instance of PublicKeyCredentialDescriptor.
   *
   * @param id The credential ID.
   * @param transports The transports used for communication with the managing authenticator.
   */
  public PublicKeyCredentialDescriptor(String id, EnumSet<AuthenticatorTransport> transports) {
    this.id = id;
    this.transports = transports;
  }

  /**
   * Creates a new instance of PublicKeyCredentialDescriptor.
   *
   * @param id The credential ID.
   */
  public PublicKeyCredentialDescriptor(String id) {
    this.id = id;
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
   * Returns the transports used for communication with the managing authenticator.
   *
   * @return An optional sorted set of AuthenticatorTransport values.
   */
  public EnumSet<AuthenticatorTransport> getTransports() {
    return transports;
  }
}
