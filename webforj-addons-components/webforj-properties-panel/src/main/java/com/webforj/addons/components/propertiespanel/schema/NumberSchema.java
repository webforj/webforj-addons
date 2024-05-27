package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;

/**
 * Represents a schema for a number property in a properties panel. Extends the BaseSchema class and
 * adds additional attributes specific to number properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class NumberSchema extends BaseSchema<NumberSchema> {

  /**
   * The default value for the number property.
   */
  private Double defaultValue;

  /**
   * The validation rules for the number property.
   */
  private NumberValidation validate;

  /**
   * Constructs a new {@code NumberSchema}.
   */
  public NumberSchema(String name, String label) {
    this.setType("number").setLabel(label).setName(name);
  }

  /**
   * Deserializes a JSON string into a NumberSchema object using Gson.
   *
   * @param json The JSON string to deserialize.
   * @return A NumberSchema object deserialized from the JSON string.
   */
  public static NumberSchema fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, NumberSchema.class);
  }

  /**
   * Serializes the NumberSchema object into a JSON string using Gson.
   *
   * @return A JSON string representing the NumberSchema object.
   */
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  /**
   * Allows for chaining {@code NumberSchema} to the {@link BaseSchema} methods.
   *
   * @return The current instance of this class
   */
  @Override
  protected NumberSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the number property.
   *
   * @return The default value for the number property.
   */
  public Double getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the number property.
   *
   * @param defaultValue The default value to set for the number property.
   */
  public NumberSchema setDefaultValue(Double defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Retrieves the validation rules for the number property.
   *
   * @return The validation rules for the number property.
   */
  public NumberValidation getValidate() {
    return validate;
  }

  /**
   * Sets the validation rules for the number property.
   *
   * @param validate The validation rules to set for the number property.
   */
  public NumberSchema setValidate(NumberValidation validate) {
    this.validate = validate;
    return this;
  }
}
