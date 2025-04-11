package com.webforj.addons.services.webauthn.data;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration describing the Relying Party's requirements for user verification during WebAuthn
 * operations.
 *
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#enumdef-userverificationrequirement"> §5.10.6.
 *     User Verification Requirement Enumeration (enum UserVerificationRequirement)</a>
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#user-verification">User Verification</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public enum UserVerificationRequirement {

  /**
   * Indicates that the Relying Party does not want user verification employed during the operation.
   * This value is used when minimizing disruption to the user interaction flow is desired.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#enumdef-userverificationrequirement"> §5.10.6.
   *     User Verification Requirement Enumeration (enum UserVerificationRequirement)</a>
   */
  @SerializedName("discouraged")
  DISCOURAGED,

  /**
   * Indicates that the Relying Party prefers user verification for the operation if possible, but
   * will not fail the operation if the response does not have the User Verified (UV) flag set.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#enumdef-userverificationrequirement"> §5.10.6.
   *     User Verification Requirement Enumeration (enum UserVerificationRequirement)</a>
   */
  @SerializedName("preferred")
  PREFERRED,

  /**
   * Indicates that the Relying Party requires user verification for the operation and will fail the
   * operation if the response does not have the User Verified (UV) flag set.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#enumdef-userverificationrequirement"> §5.10.6.
   *     User Verification Requirement Enumeration (enum UserVerificationRequirement)</a>
   */
  @SerializedName("required")
  REQUIRED,
}
