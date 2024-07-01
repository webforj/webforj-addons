package com.webforj.addons.services.webauthn.data;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration describing the Relying Party's requirements for client-side discoverable credentials,
 * also known as passkeys.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#enumdef-residentkeyrequirement"> §5.4.6. Resident
 *      Key Requirement Enumeration (enum ResidentKeyRequirement)</a>
 * @see <a href="https://www.w3.org/TR/webauthn-3/#client-side-discoverable-credential"> Client-side
 *      discoverable Credential</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public enum ResidentKeyRequirement {

  /**
   * Indicates that the client and authenticator will try to create a server-side credential if
   * possible, and a discoverable credential (passkey) otherwise.
   *
   * @see <a href="https://www.w3.org/TR/webauthn-3/#enum-residentkeyrequirement"> §5.4.6. Resident
   *      Key Requirement Enumeration (enum ResidentKeyRequirement)</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#client-side-discoverable-credential">
   *      Client-side discoverable Credential</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#server-side-credential"> Server-side
   *      Credential</a>
   */
  @SerializedName("discouraged")
  DISCOURAGED,

  /**
   * Indicates that the client and authenticator will try to create a discoverable credential
   * (passkey) if possible, and a server-side credential otherwise.
   *
   * @see <a href="https://www.w3.org/TR/webauthn-3/#enum-residentkeyrequirement"> §5.4.6. Resident
   *      Key Requirement Enumeration (enum ResidentKeyRequirement)</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#client-side-discoverable-credential">
   *      Client-side discoverable Credential</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#server-side-credential"> Server-side
   *      Credential</a>
   */
  @SerializedName("preferred")
  PREFERRED,

  /**
   * Indicates that the client and authenticator will try to create a discoverable credential
   * (passkey), and fail the registration if that is not possible.
   *
   * @see <a href="https://www.w3.org/TR/webauthn-3/#enum-residentkeyrequirement"> §5.4.6. Resident
   *      Key Requirement Enumeration (enum ResidentKeyRequirement)</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#client-side-discoverable-credential">
   *      Client-side discoverable Credential</a>
   * @see <a href="https://www.w3.org/TR/webauthn-3/#server-side-credential"> Server-side
   *      Credential</a>
   */
  @SerializedName("required")
  REQUIRED
}
