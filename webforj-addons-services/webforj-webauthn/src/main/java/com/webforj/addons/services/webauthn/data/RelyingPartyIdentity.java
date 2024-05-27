package com.webforj.addons.services.webauthn.data;

/**
 * Represents additional attributes of the Relying Party when creating a new credential.
 *
 * <p>
 * {@code RelyingPartyIdentity} provides supplementary information about the Relying Party during
 * the process of creating a new credential. It includes attributes such as the name and unique
 * identifier of the Relying Party.
 * </p>
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#dictdef-publickeycredentialrpentity"> §5.4.2.
 *      Relying Party Parameters for Credential Generation (dictionary
 *      PublicKeyCredentialRpEntity)</a>
 */
public class RelyingPartyIdentity {

  /**
   * The human-readable name of the Relying Party.
   *
   * <p>
   * The {@code name} field represents the human-readable name of the Relying Party. It typically
   * identifies the organization or entity responsible for authentication.
   * </p>
   */
  private final String name;

  /**
   * The unique identifier for the Relying Party, also known as the RP ID.
   *
   * <p>
   * The {@code id} field serves as a unique identifier for the Relying Party. It defines the scope
   * where users' credentials are valid, ensuring authentication within specific domains.
   * </p>
   *
   * @see <a href="https://www.w3.org/TR/webauthn-3/#rp-id">RP ID</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#scope">RP ID: scope</a>
   */
  private final String id;

  /**
   * Constructs a new {@code RelyingPartyIdentity} with the specified name and ID.
   *
   * @param name The human-readable name of the Relying Party.
   * @param id The unique identifier (RP ID) of the Relying Party.
   */
  public RelyingPartyIdentity(String name, String id) {
    this.name = name;
    this.id = id;
  }

  /**
   * Constructs a new {@code RelyingPartyIdentity} with the specified name .
   *
   * @param name The human-readable name of the Relying Party.
   */
  public RelyingPartyIdentity(String name) {
    this(name, null);
  }

  /**
   * Retrieves the human-readable name of the Relying Party.
   *
   * @return The name of the Relying Party.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the unique identifier (RP ID) of the Relying Party.
   *
   * @return The unique identifier of the Relying Party.
   */
  public String getId() {
    return id;
  }
}
