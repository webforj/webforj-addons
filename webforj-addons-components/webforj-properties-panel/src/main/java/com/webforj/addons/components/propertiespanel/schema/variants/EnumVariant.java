package com.webforj.addons.components.propertiespanel.schema.variants;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the possible UI variants for an enum property in the Properties Panel.
 *
 * @author ElyasSalar
 * @since 24.22
 */
public enum EnumVariant {

  /** Represents a choicebox/listbox input field. */
  @SerializedName("listbox")
  LISTBOX("listbox");

  private final String value;

  EnumVariant(String value) {
    this.value = value;
  }

  /**
   * Gets the string value of the variant.
   *
   * @return The string value corresponding to the enum constant.
   */
  public String getValue() {
    return value;
  }

  /**
   * Finds the EnumVariant enum constant corresponding to the given string value.
   *
   * @param value The string value to match (case-insensitive).
   * @return The corresponding EnumVariant, or null if no match is found.
   */
  public static EnumVariant fromString(String value) {
    if (value != null) {
      for (EnumVariant variant : EnumVariant.values()) {
        if (value.equalsIgnoreCase(variant.value)) {
          return variant;
        }
      }
    }
    return null;
  }
}
