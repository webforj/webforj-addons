package com.webforj.addons.services.webauthn.data;

/**
 * Represents a user account entity for use in WebAuthn operations, as defined by the W3C WebAuthn
 * specification.
 *
 * <p>This class encapsulates user account information required for creating public key credentials,
 * adhering to the specifications outlined in the W3C WebAuthn Level 3 Recommendation.
 *
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#dictdef-publickeycredentialuserentity"> WebAuthn
 *     Level 3 Recommendation - PublicKeyCredentialUserEntity</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public class UserIdentity {

  /**
   * Represents the human-readable identifier for the user account.
   *
   * <p>The {@code name} field stores a human-readable identifier associated with the user account.
   * It serves as a unique identifier but is primarily intended for internal use and not for display
   * purposes.
   */
  private final String name;

  /**
   * Represents the user-friendly display name for the user account.
   *
   * <p>The {@code displayName} field holds a user-friendly name intended for display purposes. It
   * is typically used to represent the user's name or a recognizable alias in user interfaces.
   */
  private final String displayName;

  /**
   * Represents the unique user handle for the account.
   *
   * <p>The {@code id} field contains a unique identifier, known as the user handle, associated with
   * the user account. It is an opaque byte sequence used to uniquely identify the user within the
   * system.
   */
  private final String id;

  /**
   * Constructs a UserIdentity object with the specified attributes.
   *
   * @param name The human-palatable identifier for the user account.
   * @param displayName A human-palatable name for the user account.
   * @param id The user handle for the account.
   */
  public UserIdentity(String id, String name, String displayName) {
    this.displayName = displayName;
    this.name = name;
    this.id = id;
  }

  /**
   * Constructs a UserIdentity object with the specified attributes. This constructor allows the
   * user to create a UserIdentity object without specifying the user handle. The user handle is set
   * to a random value by default.
   *
   * @param name The human-palatable identifier for the user account.
   * @param displayName A human-palatable name for the user account.
   */
  public UserIdentity(String name, String displayName) {
    this(ByteArray.generateRandom().getBase64Url(), name, displayName);
  }

  /**
   * Retrieves the human-palatable identifier for the user account.
   *
   * @return The name of the user account.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the human-palatable name for the user account.
   *
   * @return The display name of the user account.
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Retrieves the user handle for the account.
   *
   * @return The user handle.
   */
  public String getId() {
    return id;
  }
}
