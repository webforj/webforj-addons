package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;
import com.webforj.addons.components.propertiespanel.schema.variants.BooleanVariant;
import java.util.Objects;

/**
 * Represents a schema for a boolean property in a properties panel. Extends the {@link BaseSchema}
 * class and adds additional attributes specific to boolean properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class BooleanSchema extends BaseSchema<BooleanSchema> {

  /** The default value for the boolean property. */
  private Boolean defaultValue;

  /** The UI variant for the boolean property. */
  private BooleanVariant variant;

  /**
   * Constructs a new {@code BooleanSchema} with a name and label, defaulting the variant to {@link
   * BooleanVariant#CHECKBOX}.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @throws NullPointerException if name or label is null.
   */
  public BooleanSchema(String name, String label) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    this.setType("boolean");
    this.setLabel(label);
    this.setName(name);
    this.variant = BooleanVariant.CHECKBOX;
  }

  /**
   * Constructs a new {@code BooleanSchema} with a name, label, and a specific variant.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @param variant The desired UI variant (currently only {@link BooleanVariant#CHECKBOX}).
   * @throws NullPointerException if name, label, or variant is null.
   */
  public BooleanSchema(String name, String label, BooleanVariant variant) {
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    Objects.requireNonNull(variant, "variant cannot be null");
    this.setType("boolean");
    this.setLabel(label);
    this.setName(name);
    this.variant = variant;
  }

  /**
   * Deserializes a JSON string into a {@code BooleanSchema} object using Gson.
   *
   * @param json The JSON string to deserialize.
   * @return A BooleanSchema object deserialized from the JSON string.
   */
  public static BooleanSchema fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, BooleanSchema.class);
  }

  /**
   * Serializes the {@code BooleanSchema} object into a JSON string using Gson.
   *
   * @return A JSON string representing the {@code BooleanSchema} object.
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
  protected BooleanSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the boolean property.
   *
   * @return The default value for the boolean property.
   */
  public Boolean getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the boolean property.
   *
   * @param defaultValue The default value to set for the boolean property.
   * @return This {@code BooleanSchema} instance for method chaining.
   */
  public BooleanSchema setDefaultValue(Boolean defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Retrieves the UI variant for the boolean property.
   *
   * @return The {@link BooleanVariant} indicating how the property should be displayed.
   */
  public BooleanVariant getVariant() {
    return variant;
  }

  /**
   * Sets the UI variant for the boolean property.
   *
   * @param variant The {@link BooleanVariant} to set. Cannot be null.
   * @return This {@code BooleanSchema} instance for method chaining.
   * @throws NullPointerException if variant is null.
   */
  public BooleanSchema setVariant(BooleanVariant variant) {
    Objects.requireNonNull(variant, "variant cannot be null");
    this.variant = variant;
    return this;
  }
}
