package com.webforj.addons.components.multiselectcombo;

import java.util.Objects;

/**
 * Represents a selectable item within a {@link MultiSelectCombo} component.
 *
 * <p>Each {@code MultiSelectComboItem} encapsulates essential properties for an item in the combo
 * box's dropdown list, including its underlying value, display label, optional visual prefixes or
 * suffixes, and its enabled/disabled state.
 *
 * @author ElyasSalar
 * @since 24.22
 */
public class MultiSelectComboItem {
  private static final String VALUE_NULL_ERROR_MESSAGE = "Value cannot be null";
  private static final String LABEL_NULL_ERROR_MESSAGE = "Label cannot be null";

  /** The internal value associated with the item, used for identification and data binding. */
  private String value;

  /** The text displayed to the user for this item in the dropdown list. */
  private String label;

  /** Optional HTML or text content displayed before the item's label. */
  private String prefix;

  /** Optional HTML or text content displayed after the item's label. */
  private String suffix;

  /** A flag indicating whether the item can be selected by the user. */
  private boolean disabled;

  /**
   * Private constructor used by the Builder.
   *
   * @param builder The builder instance containing the properties to set.
   */
  private MultiSelectComboItem(Builder builder) {
    Objects.requireNonNull(builder.label, LABEL_NULL_ERROR_MESSAGE);
    Objects.requireNonNull(builder.value, VALUE_NULL_ERROR_MESSAGE);

    this.value = builder.value;
    this.label = builder.label;
    this.prefix = builder.prefix;
    this.suffix = builder.suffix;
    this.disabled = builder.disabled;
  }

  /**
   * Retrieves the internal value associated with this item. This value is typically used for data
   * processing or identification when the item is selected.
   *
   * @return The non-null internal value of the item.
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the internal value associated with this item.
   *
   * @param value The non-null value to set.
   * @return This {@code MultiSelectComboItem} instance for method chaining.
   * @throws NullPointerException if value is null.
   */
  public MultiSelectComboItem setValue(String value) {
    Objects.requireNonNull(value, VALUE_NULL_ERROR_MESSAGE);
    this.value = value;
    return this;
  }

  /**
   * Retrieves the text label displayed for this item in the combo box list.
   *
   * @return The non-null display label of the item.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the text label displayed for this item.
   *
   * @param label The non-null label to set.
   * @return This {@code MultiSelectComboItem} instance for method chaining.
   * @throws NullPointerException if label is null.
   */
  public MultiSelectComboItem setLabel(String label) {
    Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
    this.label = label;
    return this;
  }

  /**
   * Retrieves the optional prefix content (HTML or text) displayed before the item's label.
   *
   * @return The prefix content, or null if none is set.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets the optional prefix content (HTML or text) displayed before the item's label.
   *
   * @param prefix The prefix content to set.
   * @return This {@code MultiSelectComboItem} instance for method chaining.
   */
  public MultiSelectComboItem setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Retrieves the optional suffix content (HTML or text) displayed after the item's label.
   *
   * @return The suffix content, or null if none is set.
   */
  public String getSuffix() {
    return suffix;
  }

  /**
   * Sets the optional suffix content (HTML or text) displayed after the item's label.
   *
   * @param suffix The suffix content to set.
   * @return This {@code MultiSelectComboItem} instance for method chaining.
   */
  public MultiSelectComboItem setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  /**
   * Retrieves the flag indicating whether the item is disabled (cannot be selected). Defaults to
   * false if not explicitly set.
   *
   * @return {@code true} if the item is disabled, {@code false} otherwise.
   */
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Sets the flag indicating whether the item is disabled (cannot be selected).
   *
   * @param disabled The disabled state to set (true for disabled, false for enabled). If null, it
   *     defaults to false.
   * @return This {@code MultiSelectComboItem} instance for method chaining.
   */
  public MultiSelectComboItem setDisabled(boolean disabled) {
    this.disabled = disabled;
    return this;
  }

  /**
   * Creates a new builder instance to construct a {@code MultiSelectComboItem}. This is the
   * recommended way to create items.
   *
   * @return An {@link ILabel} instance to start the building process.
   */
  public static ILabel builder() {
    return new Builder();
  }

  /** Enforces setting the label first. */
  public interface ILabel {
    /**
     * Sets the mandatory display label for the item.
     *
     * @param label The text displayed to the user. Cannot be null.
     * @return The next step in the builder process (setting the value).
     */
    IValue label(String label);
  }

  /** Enforces setting the value second. */
  public interface IValue {
    /**
     * Sets the mandatory internal value for the item.
     *
     * @param value The internal value associated with the item. Cannot be null.
     * @return The next step in the builder process (setting optional attributes).
     */
    IBuild value(String value);
  }

  /** Allows setting optional properties and building the item. */
  public interface IBuild {
    /**
     * Sets the optional prefix content (HTML or text) displayed before the item's label.
     *
     * @param prefix The prefix content.
     * @return This builder instance for further configuration.
     */
    IBuild prefix(String prefix);

    /**
     * Sets the optional suffix content (HTML or text) displayed after the item's label.
     *
     * @param suffix The suffix content.
     * @return This builder instance for further configuration.
     */
    IBuild suffix(String suffix);

    /**
     * Sets the disabled state of the item. If not called, the item defaults to enabled (false).
     *
     * @param disabled True to disable the item, false to enable it.
     * @return This builder instance for further configuration.
     */
    IBuild disabled(boolean disabled);

    /**
     * Constructs the final {@code MultiSelectComboItem} instance.
     *
     * @return The fully configured {@code MultiSelectComboItem}.
     */
    MultiSelectComboItem build();
  }

  /** Private implementation of the builder steps. */
  private static class Builder implements ILabel, IValue, IBuild {
    private String value;
    private String label;
    private String prefix;
    private String suffix;
    private boolean disabled = false;

    private Builder() {}

    @Override
    public IValue label(String label) {
      Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
      this.label = label;
      return this;
    }

    @Override
    public IBuild value(String value) {
      Objects.requireNonNull(value, VALUE_NULL_ERROR_MESSAGE);
      this.value = value;
      return this;
    }

    @Override
    public IBuild prefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    @Override
    public IBuild suffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    @Override
    public IBuild disabled(boolean disabled) {
      this.disabled = disabled;
      return this;
    }

    @Override
    public MultiSelectComboItem build() {
      return new MultiSelectComboItem(this);
    }
  }
}
