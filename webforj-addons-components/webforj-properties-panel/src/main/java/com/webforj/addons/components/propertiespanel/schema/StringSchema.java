package com.webforj.addons.components.propertiespanel.schema;

/**
 * Represents a schema for a string property in a properties panel. Extends the BaseSchema
 * class and adds additional attributes specific to string properties.
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
   * Allows for chaining {@code StringSchema} to the {@link BaseSchema} methods.
   * @return The current instance of this class
   */
  @Override
  protected StringSchema getThis() {
    return this;
  }

  /**
   * Retrieves the default value for the string property.
   * @return The default value for the string property.
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value for the string property.
   * @param defaultValue The default value to set for the string property.
   */
  public StringSchema setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }
}
