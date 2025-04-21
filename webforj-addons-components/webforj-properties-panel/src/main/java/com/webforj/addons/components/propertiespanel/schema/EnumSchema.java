package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;
import com.webforj.addons.components.propertiespanel.schema.variants.EnumVariant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a schema for an enum property in a properties panel. Extends the {@link BaseSchema}
 * class and adds additional attributes specific to enum properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class EnumSchema extends BaseSchema<EnumSchema> {

  /** The default value for the enum property. */
  private String defaultValue;

  /** The list of options available for the enum property. */
  private List<EnumOption> options = new ArrayList<>();

  /** The UI variant for the enum property. */
  private EnumVariant variant;

  /**
   * Default constructor for {@code EnumSchema}. Initializes the variant to {@link
   * EnumVariant#LISTBOX}.
   */
  private EnumSchema() {
    this.variant = EnumVariant.LISTBOX;
    this.setType("enum");
  }

  /**
   * Constructs a new {@code EnumSchema} with a name and label, defaulting the variant to {@link
   * EnumVariant#LISTBOX}.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @throws NullPointerException if name or label is null.
   */
  public EnumSchema(String name, String label) {
    this(name, label, EnumVariant.LISTBOX);
  }

  /**
   * Constructs a new {@code EnumSchema} with a name, label, and a specific variant.
   *
   * @param name The unique name (key) for the property.
   * @param label The human-readable label for the property.
   * @param variant The desired UI variant (currently only {@link EnumVariant#LISTBOX}).
   * @throws NullPointerException if name, label, or variant is null.
   */
  public EnumSchema(String name, String label, EnumVariant variant) {
    this();
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(label, "label cannot be null");
    Objects.requireNonNull(variant, "variant cannot be null");
    this.setName(name);
    this.setLabel(label);
    this.variant = variant;
  }

  /**
   * Deserializes a JSON string into an {@code EnumSchema} object using Gson.
   *
   * @param json The JSON string to deserialize.
   * @return An EnumSchema object deserialized from the JSON string.
   */
  public static EnumSchema fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, EnumSchema.class);
  }

  /**
   * Serializes the {@code EnumSchema} object into a JSON string using Gson.
   *
   * @return A JSON string representing the EnumSchema object.
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
  protected EnumSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the enum property.
   *
   * @return The default value for the enum property.
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the enum property.
   *
   * @param defaultValue The default value to set for the enum property.
   * @return This {@code EnumSchema} instance for method chaining.
   */
  public EnumSchema setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Retrieves the list of options available for the enum property.
   *
   * @return The list of options available for the enum property.
   */
  public List<EnumOption> getOptions() {
    return options;
  }

  /**
   * Sets the list of options available for the enum property.
   *
   * @param options The list of options to set for the enum property.
   * @return This {@code EnumSchema} instance for method chaining.
   */
  public EnumSchema setOptions(List<EnumOption> options) {
    this.options = options == null ? new ArrayList<>() : options;
    return this;
  }

  /**
   * Retrieves the UI variant for the enum property.
   *
   * @return The {@link EnumVariant} indicating how the property should be displayed.
   */
  public EnumVariant getVariant() {
    return variant;
  }

  /**
   * Sets the UI variant for the enum property.
   *
   * @param variant The {@link EnumVariant} to set. Cannot be null.
   * @return This {@code EnumSchema} instance for method chaining.
   * @throws NullPointerException if variant is null.
   */
  public EnumSchema setVariant(EnumVariant variant) {
    Objects.requireNonNull(variant, "variant cannot be null");
    this.variant = variant;
    return this;
  }
}
