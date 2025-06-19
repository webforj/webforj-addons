package com.webforj.addons.components.suggestionedit;

import java.util.Objects;

/**
 * Represents a single suggestion item in the {@link SuggestionEdit} component.
 *
 * <p>The {@code SuggestionItem} class defines the properties of each suggestion that can be shown
 * in the SuggestionEdit component. Each suggestion has a {@code value} for identification and a
 * {@code label} for display, and may have additional properties for display and behavior
 * customization.
 *
 * <p>Instances of this class are used in the list of suggestions that the {@link SuggestionEdit}
 * component displays based on user input. The structure aligns with the client-side {@code
 * DwcListboxItem} interface for consistency.
 *
 * @since 1.00
 * @author ElyasSalar
 */
public class SuggestionItem {

  private static final String VALUE_NULL_ERROR_MESSAGE = "Value cannot be null";
  private static final String LABEL_NULL_ERROR_MESSAGE = "Label cannot be null";

  /**
   * The value of the suggestion. This is the actual identifier that will be used to identify a
   * suggestion.
   */
  private String value;

  /**
   * The label of the suggestion. This is the text that will be displayed to the user in the
   * suggestion list and input field.
   */
  private String label;

  /**
   * The prefix of the suggestion. This can be used to set any prefix for certain suggestions like
   * text, signs, icons, and images. It supports HTML strings.
   */
  private String prefix;

  /**
   * The suffix of the suggestion. This can be used to set any suffix for certain suggestions like
   * text, signs, icons, and images. It supports HTML strings.
   */
  private String suffix;

  /** Indicates whether this suggestion is disabled. Disabled suggestions cannot be selected. */
  private boolean disabled;

  /**
   * Private constructor used by the Builder.
   *
   * @param builder The builder instance containing the properties to set.
   */
  private SuggestionItem(Builder builder) {
    Objects.requireNonNull(builder.value, VALUE_NULL_ERROR_MESSAGE);
    Objects.requireNonNull(builder.label, LABEL_NULL_ERROR_MESSAGE);
    this.value = builder.value;
    this.label = builder.label;
    this.prefix = builder.prefix;
    this.suffix = builder.suffix;
    this.disabled = builder.disabled;
  }

  /**
   * Returns the value of the suggestion.
   *
   * @return The value of the suggestion.
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the suggestion.
   *
   * @param value The value of the suggestion.
   * @return This SuggestionItem instance for method chaining.
   */
  public SuggestionItem setValue(String value) {
    Objects.requireNonNull(value, VALUE_NULL_ERROR_MESSAGE);
    this.value = value;
    return this;
  }

  /**
   * Returns the label of the suggestion.
   *
   * @return The label of the suggestion.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the label of the suggestion.
   *
   * @param label The label of the suggestion.
   * @return This SuggestionItem instance for method chaining.
   */
  public SuggestionItem setLabel(String label) {
    Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
    this.label = label;
    return this;
  }

  /**
   * Returns the prefix of the suggestion.
   *
   * @return The prefix of the suggestion.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Sets the prefix of the suggestion.
   *
   * @param prefix The prefix of the suggestion.
   * @return This SuggestionItem instance for method chaining.
   */
  public SuggestionItem setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Returns the suffix of the suggestion.
   *
   * @return The suffix of the suggestion.
   */
  public String getSuffix() {
    return suffix;
  }

  /**
   * Sets the suffix of the suggestion.
   *
   * @param suffix The suffix of the suggestion.
   * @return This SuggestionItem instance for method chaining.
   */
  public SuggestionItem setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  /**
   * Returns whether the suggestion is disabled.
   *
   * @return {@code true} if the suggestion is disabled, {@code false} otherwise.
   */
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Sets whether the suggestion is disabled.
   *
   * @param disabled {@code true} if the suggestion should be disabled, {@code false} otherwise.
   * @return This SuggestionItem instance for method chaining.
   */
  public SuggestionItem setDisabled(boolean disabled) {
    this.disabled = disabled;
    return this;
  }

  /**
   * Creates a new builder instance to construct a {@code SuggestionItem}. This is the recommended
   * way to create items.
   *
   * @return An {@link IValue} instance to start the building process.
   */
  public static IValue builder() {
    return new Builder();
  }

  /** Enforces setting the value first. */
  public interface IValue {
    /**
     * Sets the mandatory value for the suggestion item.
     *
     * @param value The value used for identification. Cannot be null.
     * @return The next step in the builder process (setting the label).
     */
    ILabel value(String value);
  }

  /** Enforces setting the label second. */
  public interface ILabel {
    /**
     * Sets the mandatory display label for the suggestion item.
     *
     * @param label The text displayed to the user. Cannot be null.
     * @return The next step in the builder process (setting optional attributes).
     */
    IBuild label(String label);
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
     * Constructs the final {@code SuggestionItem} instance.
     *
     * @return The fully configured {@code SuggestionItem}.
     */
    SuggestionItem build();
  }

  /** Private implementation of the builder steps. */
  private static class Builder implements IValue, ILabel, IBuild {
    private String value;
    private String label;
    private String prefix;
    private String suffix;
    private boolean disabled = false;

    private Builder() {}

    @Override
    public ILabel value(String value) {
      Objects.requireNonNull(value, VALUE_NULL_ERROR_MESSAGE);
      this.value = value;
      return this;
    }

    @Override
    public IBuild label(String label) {
      Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
      this.label = label;
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
    public SuggestionItem build() {
      return new SuggestionItem(this);
    }
  }
}
