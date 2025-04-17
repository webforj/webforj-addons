package com.webforj.addons.components.propertiespanel.schema.variants;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Defines the possible UI variants for a string property in the Properties Panel.
 *
 * @author ElyasSalar
 * @since 24.22
 */
@JsonAdapter(VariantEnumAdapter.class)
public enum StringVariant {

  /** Represents a single-line text input field. */
  @SerializedName("text")
  TEXT("text"),

  /** Represents a multi-line text area input field. */
  @SerializedName("textarea")
  TEXTAREA("textarea");

  private final String value;

  StringVariant(String value) {
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
   * Finds the {@code StringVariant} enum constant corresponding to the given string value.
   *
   * @param value The string value to match (case-insensitive).
   * @return The corresponding {@code StringVariant}, or null if no match is found.
   */
  public static StringVariant fromValue(String value) {
    if (value != null) {
      for (StringVariant variant : StringVariant.values()) {
        if (value.equalsIgnoreCase(variant.value)) {
          return variant;
        }
      }
    }
    return StringVariant.TEXT;
  }
}
