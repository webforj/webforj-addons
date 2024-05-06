package com.webforj.addons.components.propertiespanel.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a schema for an enum property in a properties panel. Extends the BaseSchema
 * class and adds additional attributes specific to enum properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class EnumSchema extends BaseSchema<EnumSchema> {

  /**
   * The default value for the enum property.
   */
  private String defaultValue;

  /**
   * The list of options available for the enum property.
   */
  private List<EnumOption> options = new ArrayList<>();

  /**
   * Constructs a new {@code EnumSchema}.
   */
  public EnumSchema(String name, String label) {
    this.setType("enum").setName(name).setLabel(label);
  }

  /**
   * Allows for chaining {@code EnumSchema} to the {@link BaseSchema} methods.
   * @return The current instance of this class
   */
  @Override
  protected EnumSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the enum property.
   * @return The default value for the enum property.
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the enum property.
   * @param defaultValue The default value to set for the enum property.
   */
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * Retrieves the list of options available for the enum property.
   * @return The list of options available for the enum property.
   */
  public List<EnumOption> getOptions() {
    return options;
  }

  /**
   * Sets the list of options available for the enum property.
   * @param options The list of options to set for the enum property.
   */
  public EnumSchema setOptions(List<EnumOption> options) {
    this.options = options;
    return this;
  }
}
