package com.webforj.addons.services.webauthn.data;

import com.google.gson.annotations.SerializedName;

/**
 * This enum defines the various status values associated with Token Binding, a protocol used for
 * establishing secure bindings between clients and servers to prevent token export and replay
 * attacks. The tokenBinding field, which may be optional, provides insights into the Token Binding
 * protocol's usage during communication with the Relying Party, with the absence of this field
 * indicating lack of support for token binding.
 */
public enum TokenBindingStatus {
  /**
   * Indicates that token binding was employed during communication with the Relying Party,
   * requiring the presence of an associated ID.
   */
  @SerializedName("present")
  PRESENT,

  /**
   * Signifies that the client supports token binding but did not negotiate it during communication.
   */
  @SerializedName("supported")
  SUPPORTED,

  /**
   * denotes that token binding is not supported.
   */
  @SerializedName("not-supported")
  NOT_SUPPORTED
}
