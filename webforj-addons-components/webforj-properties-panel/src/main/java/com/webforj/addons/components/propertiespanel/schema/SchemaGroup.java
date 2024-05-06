package com.webforj.addons.components.propertiespanel.schema;


import com.webforj.addons.components.propertiespanel.PropertiesPanel;

import java.util.List;

/**
 * Represents a group of properties within a schema, designed for display in a {@link PropertiesPanel}.
 * {@code SchemaGroup} organizes related properties together, facilitating structured and organized presentation.
 * It supports collapsible and expandable behavior for efficient use of space.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class SchemaGroup {

  /**
   * The label representing the group of properties.
   */
  private String label;

  /**
   * The list of properties belonging to this group.
   */
  private List<BaseSchema<?>> properties;

  /**
   * Constructs a {@code SchemaGroup} with the specified label.
   *
   * @param label      The label of the schema group.
   */
  public SchemaGroup(String label) {
    this.label = label;
  }

  /**
   * Constructs a {@code SchemaGroup} with the specified label and properties.
   *
   * @param label      The label of the schema group.
   */
  public SchemaGroup(String label, List<BaseSchema<?>> properties) {
    this.label = label;
    this.properties = properties;
  }

  /**
   * Retrieves the label that serves as a descriptive name for the
   * group of properties of the schema group.
   *
   * @return The label of the schema group.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the label provides a descriptive name for the group
   * of properties of the schema group.
   *
   * @param label The label of the schema group.
   */
  public SchemaGroup setLabel(String label) {
    this.label = label;
    return this;
  }

  /**
   * Retrieves the list of properties within a group are related and often
   * share common characteristics or purposes belonging to this group.
   *
   * @return The list of properties within the group.
   */
  public List<?> getProperties() {
    return properties;
  }

  /**
   * Sets the list of properties within a group are related and often
   * share common characteristics or purposes belonging to this group.
   *
   * @param properties The list of properties to be assigned to the group.
   */
  public SchemaGroup setProperties(List<BaseSchema<?>> properties) {
    this.properties = properties;
    return this;
  }
}

