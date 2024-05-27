package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;

/**
 * Represents a schema for a string property in a properties panel. Extends the BaseSchema class and
 * adds additional attributes specific to string properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class StringSchema extends BaseSchema<StringSchema> {

  /**
   * The default value for the string property.
   */
  private String defaultValue;

  /**
   * Constructs a new {@code StringSchema}.
   */
  public StringSchema(String name, String label) {
    this.setType("string");
    this.setLabel(label);
    this.setName(name);
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
   * Allows for chaining {@code StringSchema} to the {@link BaseSchema} methods.
   *
   * @return The current instance of this class
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
   */
  public StringSchema setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }
}
