package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;
import com.webforj.addons.components.propertiespanel.schema.variants.StringVariant;
import java.util.Objects;

/**
 * Represents a schema for a string property in a properties panel. Extends the {@link BaseSchema}
 * class and adds additional attributes specific to string properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class StringSchema extends BaseSchema<StringSchema> {

  /** The default value for the string property. */
  private String defaultValue;

  /** The UI variant for the string property (e.g., text field or text area). */
  private StringVariant variant;

  /**
   * Constructs a new {@code StringSchema} with a name and label, defaulting the variant to {@link
   * StringVariant#TEXT}.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @throws NullPointerException if any of the parameters are null.
   */
  public StringSchema(String name, String label) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    this.variant = StringVariant.TEXT;
    this.setType("string");
    this.setLabel(label);
    this.setName(name);
  }

  /**
   * Constructs a new {@code StringSchema} with a name, label, and a specific variant.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @param variant The desired UI variant (e.g., {@link StringVariant#TEXTAREA}).
   * @throws NullPointerException if any of the parameters are null.
   */
  public StringSchema(String name, String label, StringVariant variant) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    Objects.requireNonNull(variant, "variant cannot be null");
    this.setType("string");
    this.setLabel(label);
    this.setName(name);
    this.variant = variant;
  }

  /**
   * Deserializes a JSON string into a {@code StringSchema} object using Gson.
   *
   * @param json The JSON string to deserialize.
   * @return A StringSchema object deserialized from the JSON string.
   */
  public static StringSchema fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, StringSchema.class);
  }

  /**
   * Serializes the {@code StringSchema} object into a JSON string using Gson.
   *
   * @return A JSON string representing the StringSchema object.
   */
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  /**
   * Returns the specific type instance for chaining {@link BaseSchema} methods.
   *
   * @return The current instance of this class.
   */
  @Override
  protected StringSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the string property.
   *
   * @return The default value for the string property.
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the string property.
   *
   * @param defaultValue The default value to set for the string property.
   * @return This {@code StringSchema} instance for method chaining.
   */
  public StringSchema setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Retrieves the UI variant for the string property.
   *
   * @return The {@link StringVariant} indicating how the property should be displayed (e.g., TEXT
   *     or TEXTAREA).
   */
  public StringVariant getVariant() {
    return variant;
  }

  /**
   * Sets the UI variant for the string property.
   *
   * @param variant The {@link StringVariant} to set (e.g., {@link StringVariant#TEXTAREA}). If
   *     null, defaults to {@link StringVariant#TEXT}.
   * @return This {@code StringSchema} instance for method chaining.
   */
  public StringSchema setVariant(StringVariant variant) {
    Objects.requireNonNull(variant, "variant cannot be null");
    this.variant = variant;
    return this;
  }
}
