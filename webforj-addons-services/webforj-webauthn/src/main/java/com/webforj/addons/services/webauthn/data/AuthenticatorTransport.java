package com.webforj.addons.services.webauthn.data;

import com.google.gson.annotations.SerializedName;

/**
 * Enum representing different types of authenticator transports.
 *
 * <p>Authenticators may communicate with clients using a variety of transports. This enumeration
 * defines hints as to how clients might communicate with a particular authenticator in order to
 * obtain an assertion for a specific credential. Note that these hints represent the Relying
 * Party's best belief as to how an authenticator may be reached. A Relying Party may obtain a list
 * of transport hints from some attestation statement formats or via some out-of-band mechanism.
 *
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#enumdef-authenticatortransport"> §5.10.4.
 *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public enum AuthenticatorTransport {
  /**
   * Indicates the respective authenticator can be contacted over Bluetooth Low Energy (BLE).
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-ble"> 5.10.4.
   *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("ble")
  BLE,

  /**
   * Indicates the respective authenticator supports hybrid transport.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-hybrid"> 5.10.4.
   *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("hybrid")
  HYBRID,

  /**
   * Indicates the respective authenticator can be contacted internally.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-internal"> 5.10.4.
   *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("internal")
  INTERNAL,

  /**
   * Indicates the respective authenticator can be contacted over Near Field Communication (NFC).
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-nfc"> 5.10.4.
   *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("nfc")
  NFC,

  /**
   * Indicates the respective authenticator can be contacted using a smart card.
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-smart-card">
   *     5.10.4. Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("smart-card")
  SMART_CARD,

  /**
   * Indicates the respective authenticator can be contacted over Universal Serial Bus (USB).
   *
   * @see <a href= "https://www.w3.org/TR/webauthn-3/#dom-authenticatortransport-usb"> 5.10.4.
   *     Authenticator Transport Enumeration (enum AuthenticatorTransport)</a>
   */
  @SerializedName("usb")
  USB,
}
