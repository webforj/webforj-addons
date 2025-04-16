package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;
import com.webforj.addons.components.propertiespanel.schema.variants.NumberVariant;
import java.util.Objects;

/**
 * Represents a schema for a number property in a properties panel. Extends the {@link BaseSchema}
 * class and adds additional attributes specific to number properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class NumberSchema extends BaseSchema<NumberSchema> {

  /** The default value for the number property. */
  private Double defaultValue;

  /** The validation rules for the number property. */
  private NumberValidation validate;

  /** The UI variant for the number property. */
  private NumberVariant variant;

  /**
   * Constructs a new {@code NumberSchema} with a name and label, defaulting the variant to {@link
   * NumberVariant#NUMBER}.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @throws NullPointerException if name or label is null.
   */
  public NumberSchema(String name, String label) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    this.setType("number");
    this.setLabel(label);
    this.setName(name);
    this.variant = NumberVariant.NUMBER;
  }

  /**
   * Constructs a new {@code NumberSchema} with a name, label, and a specific variant.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @param variant The desired UI variant (currently only {@link NumberVariant#NUMBER}).
   * @throws NullPointerException if name, label, or variant is null.
   */
  public NumberSchema(String name, String label, NumberVariant variant) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    Objects.requireNonNull(variant, "variant cannot be null");
    this.setType("number");
    this.setLabel(label);
    this.setName(name);
    this.variant = variant;
  }

  /**
   * Deserializes a JSON string into a {@code NumberSchema} object using Gson.
   *
   * @param json The JSON string to deserialize.
   * @return A NumberSchema object deserialized from the JSON string.
   */
  public static NumberSchema fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, NumberSchema.class);
  }

  /**
   * Serializes the {@code NumberSchema} object into a JSON string using Gson.
   *
   * @return A JSON string representing the NumberSchema object.
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
   * @return This {@code NumberSchema} instance for method chaining.
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
   * @return This {@code NumberSchema} instance for method chaining.
   */
  public NumberSchema setValidate(NumberValidation validate) {
    this.validate = validate;
    return this;
  }

  /**
   * Retrieves the UI variant for the number property.
   *
   * @return The {@link NumberVariant} indicating how the property should be displayed.
   */
  public NumberVariant getVariant() {
    return variant;
  }

  /**
   * Sets the UI variant for the number property.
   *
   * @param variant The {@link NumberVariant} to set. Cannot be null.
   * @return This {@code NumberSchema} instance for method chaining.
   * @throws NullPointerException if variant is null.
   */
  public NumberSchema setVariant(NumberVariant variant) {
    Objects.requireNonNull(variant, "variant cannot be null");
    this.variant = variant;
    return this;
  }
}
