package com.webforj.addons.services.webauthn.data;

/**
 * Class specifying requirements regarding authenticator attributes for WebAuthn operations.
 *
 * <p>
 * Authenticator Selection Criteria is used to specify requirements for authenticator attributes
 * during WebAuthn operations.
 * </p>
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#dictdef-authenticatorselectioncriteria"> §5.4.4.
 *      Authenticator Selection Criteria (dictionary AuthenticatorSelectionCriteria)</a>
 */
public class AuthenticatorSelectionCriteria {

  /**
   * Represents the attachment modality of eligible authenticators.
   *
   * <p>
   * The {@code authenticatorAttachment} attribute, if present, filters eligible authenticators to
   * only those attached with the specified modality, as defined in the
   * <a href="https://www.w3.org/TR/webauthn-3/#enum-attachment"> Authenticator Attachment
   * Enumeration</a>.
   * </p>
   */
  private AuthenticatorAttachment authenticatorAttachment;

  /**
   * Represents the requirement for creating client-side discoverable credentials (passkeys).
   *
   * <p>
   * The {@code residentKey} attribute specifies the extent to which the Relying Party desires to
   * create a client-side discoverable credential (passkey). When set, it determines whether the
   * {@code requireResidentKey} member should be emitted when generating WebAuthn options. The
   * default value, if not set, is {@link ResidentKeyRequirement#DISCOURAGED}.
   * </p>
   *
   * <p>
   * For backwards compatibility with WebAuthn Level 1, when this attribute is set to
   * {@link ResidentKeyRequirement#REQUIRED}, the {@code requireResidentKey} member is emitted and
   * set to {@code true}. Otherwise, it is emitted and set to {@code false}.
   * </p>
   */
  private ResidentKeyRequirement residentKey;

  /**
   * Represents whether resident key is required for backward compatibility.
   *
   * <p>
   * This attribute is automatically set based on the
   * {@link AuthenticatorSelectionCriteria#residentKey} attribute. If
   * {@link AuthenticatorSelectionCriteria#residentKey} is set to
   * {@link ResidentKeyRequirement#REQUIRED}, {@code requireResidentKey} will be {@code true};
   * otherwise, it will be {@code false}.
   * </p>
   */
  private boolean requireResidentKey;

  /**
   * Represents the requirement for user verification during WebAuthn operations.
   *
   * <p>
   * The {@code userVerification} attribute describes the Relying Party's requirements for user
   * verification during the {@code navigator.credentials.create()} operation. Eligible
   * authenticators are filtered based on their capability to satisfy this requirement. The default
   * value, if not set, is {@link UserVerificationRequirement#PREFERRED}.
   * </p>
   */
  private UserVerificationRequirement userVerification;

  /**
   * Constructs an {@code AuthenticatorSelectionCriteria} object with the specified preferences.
   *
   * @param authenticatorAttachment The preferred attachment modality of authenticators, or null if
   *        not specified.
   * @param residentKey The requirement for creating resident keys (client-side discoverable
   *        credentials), or null if not specified.
   * @param userVerification The requirement for user verification during the operation, or null if
   *        not specified.
   */
  public AuthenticatorSelectionCriteria(AuthenticatorAttachment authenticatorAttachment,
      ResidentKeyRequirement residentKey, UserVerificationRequirement userVerification) {
    this.authenticatorAttachment = authenticatorAttachment;
    this.residentKey = residentKey;
    this.userVerification = userVerification;
    this.requireResidentKey =
        residentKey != null && this.residentKey.equals(ResidentKeyRequirement.REQUIRED);
  }

  /**
   * Constructs an {@code AuthenticatorSelectionCriteria} object with the specified preferences.
   */
  public AuthenticatorSelectionCriteria() {
    this(null, null, null);
  }

  /**
   * Retrieves the preferred authenticator attachment modality.
   *
   * @return An Optional containing the authenticator attachment modality if specified, otherwise an
   *         empty Optional.
   */
  public AuthenticatorAttachment getAuthenticatorAttachment() {
    return authenticatorAttachment;
  }

  /**
   * Sets the preferred authenticator attachment modality.
   *
   * @param authenticatorAttachment The authenticator attachment modality to set.
   */
  public AuthenticatorSelectionCriteria setAuthenticatorAttachment(
      AuthenticatorAttachment authenticatorAttachment) {
    this.authenticatorAttachment = authenticatorAttachment;
    return this;
  }

  /**
   * Retrieves the requirement for creating resident keys.
   *
   * @return An Optional containing the resident key requirement if specified, otherwise an empty
   *         Optional.
   */
  public ResidentKeyRequirement getResidentKey() {
    return residentKey;
  }

  /**
   * Sets the requirement for creating resident keys.
   *
   * @param residentKey The resident key requirement to set.
   */
  public AuthenticatorSelectionCriteria setResidentKey(ResidentKeyRequirement residentKey) {
    this.residentKey = residentKey;
    return this;
  }

  /**
   * Retrieves the requirement for user verification during the operation.
   *
   * @return An Optional containing the user verification requirement if specified, otherwise an
   *         empty Optional.
   */
  public UserVerificationRequirement getUserVerification() {
    return userVerification;
  }

  /**
   * Sets the requirement for user verification during the operation.
   *
   * @param userVerification The user verification requirement to set.
   */
  public AuthenticatorSelectionCriteria setUserVerification(
      UserVerificationRequirement userVerification) {
    this.userVerification = userVerification;
    return this;
  }
}
