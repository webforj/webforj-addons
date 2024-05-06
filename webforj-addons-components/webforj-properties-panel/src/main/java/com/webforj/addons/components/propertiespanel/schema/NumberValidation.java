package com.webforj.addons.components.propertiespanel.schema;

/**
 * Represents validation rules for a number property in a properties panel.
 * It includes minimum and maximum value constraints.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class NumberValidation {

  /**
   * The minimum value allowed for the number property.
   */
  private Double min;

  /**
   * The maximum value allowed for the number property.
   */
  private Double max;

  /**
   * Constructs a new {@code ValidationRules}.
   */
  public NumberValidation(Double min, Double max) {
    this.min = min;
    this.max = max;
  }

  /**
   * Retrieves the minimum value allowed for the number property.
   * @return The minimum value allowed.
   */
  public Double getMin() {
    return min;
  }

  /**
   * Sets the minimum value allowed for the number property.
   * @param min The minimum value to set.
   */
  public NumberValidation setMin(Double min) {
    this.min = min;
    return this;
  }

  /**
   * Retrieves the maximum value allowed for the number property.
   * @return The maximum value allowed.
   */
  public Double getMax() {
    return max;
  }

  /**
   * Sets the maximum value allowed for the number property.
   * @param max The maximum value to set.
   */
  public NumberValidation setMax(Double max) {
    this.max = max;
    return this;
  }
}

