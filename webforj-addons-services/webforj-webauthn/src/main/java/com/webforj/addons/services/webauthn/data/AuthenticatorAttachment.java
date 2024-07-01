package com.webforj.addons.services.webauthn.data;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration describing authenticator attachment modalities. Relying Parties use this for
 * expressing a preferred authenticator attachment modality when creating a credential, and
 * informing the client of the Relying Party's belief about how to locate managing authenticators
 * when getting credentials.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-3/#enumdef-authenticatorattachment"> §5.4.5.
 *      Authenticator Attachment Enumeration (enum AuthenticatorAttachment)</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public enum AuthenticatorAttachment {

  /**
   * Indicates cross-platform attachment. Authenticators of this class can roam among client
   * platforms and are removable.
   */
  @SerializedName("cross-platform")
  CROSS_PLATFORM,

  /**
   * Indicates platform attachment. Authenticators of this class are usually not removable from the
   * platform.
   */
  @SerializedName("platform")
  PLATFORM
}
