package com.webforj.addons.components.propertiespanel.schema.variants;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Defines the possible UI variants for a boolean property in the Properties Panel.
 *
 * @author ElyasSalar
 * @since 24.22
 */
@JsonAdapter(VariantEnumAdapter.class)
public enum BooleanVariant {

  /** Represents a standard checkbox input field. */
  @SerializedName("checkbox")
  CHECKBOX("checkbox");

  private final String value;

  BooleanVariant(String value) {
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
   * Finds the {@code BooleanVariant} enum constant corresponding to the given string value.
   *
   * @param value The string value to match (case-insensitive).
   * @return The corresponding {@code BooleanVariant}, or {@code CHECKBOX} if no match is found.
   */
  public static BooleanVariant fromValue(String value) {
    if (value != null) {
      for (BooleanVariant variant : BooleanVariant.values()) {
        if (value.equalsIgnoreCase(variant.value)) {
          return variant;
        }
      }
    }
    return CHECKBOX;
  }
}
