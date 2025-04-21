package com.webforj.addons.components.propertiespanel.schema.variants;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Defines the possible UI variants for a number property in the Properties Panel.
 *
 * @author ElyasSalar
 * @since 24.22
 */
@JsonAdapter(VariantEnumAdapter.class)
public enum NumberVariant {

  /** Represents a standard number input field. */
  @SerializedName("number")
  NUMBER("number");

  private final String value;

  NumberVariant(String value) {
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
   * Finds the {@code NumberVariant} enum constant corresponding to the given string value.
   *
   * @param value The string value to match (case-insensitive).
   * @return The corresponding {@code NumberVariant}, or {@code NUMBER} if no match is found.
   */
  public static NumberVariant fromValue(String value) {
    if (value != null) {
      for (NumberVariant variant : NumberVariant.values()) {
        if (value.equalsIgnoreCase(variant.value)) {
          return variant;
        }
      }
    }
    return NUMBER;
  }
}
