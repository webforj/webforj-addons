package com.webforj.addons.components.propertiespanel.schema;

/**
 * Represents a schema for a boolean property in a properties panel. Extends the BaseSchema class
 * and adds additional attributes specific to boolean properties.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class BooleanSchema extends BaseSchema<BooleanSchema> {

  /**
   * The default value for the boolean property.
   */
  private Boolean defaultValue;

  /**
   * Constructs a new {@code BooleanSchema}.
   */
  public BooleanSchema(String name, String label) {
    this.setType("boolean").setLabel(label).setName(name);
  }

  /**
   * Allows for chaining {@code BooleanScheme} to the {@link BaseSchema} methods.
   *
   * @return The current instance of this class
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
   */
  public BooleanSchema setDefaultValue(Boolean defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }
}
