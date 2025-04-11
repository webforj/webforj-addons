package com.webforj.addons.components.suggestionedit;

/**
 * Represents a single suggestion item in the {@link SuggestionEdit} component.
 *
 * <p>The {@code Suggestion} class defines the properties of each suggestion that can be shown in
 * the SuggestionEdit component. Each suggestion has a {@code value} and an {@code id}, and may have
 * additional properties for display and behavior customization.
 *
 * <p>Instances of this class are used in the list of suggestions that the {@link SuggestionEdit}
 * component displays based on user input.
 *
 * @since 1.00
 * @author ElyasSalar
 */
public class Suggestion {

  /**
   * The id of the suggestion. This is the actual identifier that will be used to identify a
   * suggestion.
   */
  private String id;

  /**
   * The value of the suggestion. This is the text that will be displayed to the user in the
   * suggestion list.
   */
  private String value;

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
   * Constructs a new Suggestion with the specified {@code id}, {@code value}, {@code prefix},
   * {@code suffix}, and {@code disabled} state.
   *
   * @param id The id of the suggestion.
   * @param value The value of the suggestion.
   * @param prefix The prefix of the suggestion.
   * @param suffix The suffix of the suggestion.
   * @param disabled Whether the suggestion is disabled.
   */
  public Suggestion(String id, String value, String prefix, String suffix, boolean disabled) {
    this.id = id;
    this.value = value;
    this.prefix = prefix;
    this.suffix = suffix;
    this.disabled = disabled;
  }

  /**
   * Constructs a new Suggestion with the specified {@code id} and {@code value}.
   *
   * @param id The id of the suggestion.
   * @param value The value of the suggestion.
   */
  public Suggestion(String id, String value) {
    this(id, value, null, null, false);
  }

  /**
   * Constructs a new Suggestion with the specified {@code id}, {@code value}, and {@code disabled}
   * state.
   *
   * @param id The id of the suggestion.
   * @param value The value of the suggestion.
   * @param disabled Whether the suggestion is disabled.
   */
  public Suggestion(String id, String value, boolean disabled) {
    this(id, value, null, null, disabled);
  }

  /**
   * Returns the id of the suggestion.
   *
   * @return The id of the suggestion.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the suggestion.
   *
   * @param id The id of the suggestion.
   */
  public Suggestion setId(String id) {
    this.id = id;
    return this;
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
   */
  public Suggestion setValue(String value) {
    this.value = value;
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
   */
  public Suggestion setPrefix(String prefix) {
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
   */
  public Suggestion setSuffix(String suffix) {
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
   */
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }
}
