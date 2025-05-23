package com.webforj.addons.components.multiselectcombo;

/**
 * Represents an item within a {@link MultiSelectCombo} component. Each {@code Item} encapsulates a
 * value, a label, optional prefix and suffix text, and a flag indicating whether the item is
 * disabled.
 *
 * @deprecated Use {@link MultiSelectComboItem} and its builder pattern instead. This class is
 *     scheduled for removal in a future release.
 * @author ElyasSalar
 * @since 1.00
 */
@Deprecated(since = "24.22", forRemoval = true)
public class Item {

  /** The value associated with the item. */
  private String value = "";

  /** The label displayed for the item. */
  private String label = "";

  /** The prefix text displayed before the label. */
  private String prefix;

  /** The suffix text displayed after the label. */
  private String suffix;

  /** A flag indicating whether the item is disabled. */
  private Boolean disabled = false;

  /**
   * Constructs an {@code Item} with the specified value and label.
   *
   * @param value The value associated with the item.
   * @param label The label displayed for the item.
   * @deprecated Use {@link MultiSelectComboItem#builder()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item(String value, String label) {
    this.value = value;
    this.label = label;
  }

  /**
   * Constructs an {@code Item} with the specified value, label, prefix, suffix, and disabled state.
   *
   * @param value The value associated with the item.
   * @param label The label displayed for the item.
   * @param prefix The prefix text displayed before the label.
   * @param suffix The suffix text displayed after the label.
   * @param disabled A flag indicating whether the item is disabled.
   * @deprecated Use {@link MultiSelectComboItem#builder()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item(String value, String label, String prefix, String suffix, Boolean disabled) {
    this.value = value;
    this.label = label;
    this.prefix = prefix;
    this.suffix = suffix;
    this.disabled = disabled;
  }

  /**
   * Retrieves the value associated with the item.
   *
   * @return The value of the item.
   * @deprecated Use {@link MultiSelectComboItem#getValue()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public String getValue() {
    return value;
  }

  /**
   * Sets the value associated with the item.
   *
   * @param value The value to set.
   * @return This {@code Item} instance for method chaining.
   * @deprecated Use {@link MultiSelectComboItem#setValue(String)} or the builder instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item setValue(String value) {
    this.value = value;
    return this;
  }

  /**
   * Retrieves the label displayed for the item.
   *
   * @return The label of the item.
   * @deprecated Use {@link MultiSelectComboItem#getLabel()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public String getLabel() {
    return label;
  }

  /**
   * Sets the label displayed for the item.
   *
   * @param label The label to set.
   * @return This {@code Item} instance for method chaining.
   * @deprecated Use {@link MultiSelectComboItem#setLabel(String)} or the builder instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item setLabel(String label) {
    this.label = label;
    return this;
  }

  /**
   * Retrieves the prefix text displayed before the label.
   *
   * @return The prefix text of the item.
   * @deprecated Use {@link MultiSelectComboItem#getPrefix()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets the prefix text displayed before the label.
   *
   * @param prefix The prefix text to set.
   * @return This {@code Item} instance for method chaining.
   * @deprecated Use {@link MultiSelectComboItem#setPrefix(String)} or the builder instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Retrieves the suffix text displayed after the label.
   *
   * @return The suffix text of the item.
   * @deprecated Use {@link MultiSelectComboItem#getSuffix()} instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public String getSuffix() {
    return suffix;
  }

  /**
   * Sets the suffix text displayed after the label.
   *
   * @param suffix The suffix text to set.
   * @return This {@code Item} instance for method chaining.
   * @deprecated Use {@link MultiSelectComboItem#setSuffix(String)} or the builder instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  /**
   * Retrieves the flag indicating whether the item is disabled.
   *
   * @return {@code true} if the item is disabled, {@code false} otherwise.
   * @deprecated Use {@link MultiSelectComboItem#isDisabled()} instead. Note the potential change
   *     from {@code Boolean} to {@code boolean}.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Boolean getDisabled() {
    return disabled;
  }

  /**
   * Sets the flag indicating whether the item is disabled.
   *
   * @param disabled The flag to set.
   * @return This {@code Item} instance for method chaining.
   * @deprecated Use {@link MultiSelectComboItem#setDisabled(boolean)} or the builder instead.
   */
  @Deprecated(since = "24.22", forRemoval = true)
  public Item setDisabled(Boolean disabled) {
    this.disabled = disabled;
    return this;
  }
}
