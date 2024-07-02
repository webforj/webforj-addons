package com.webforj.addons.components.suggestionedit;

import com.google.gson.annotations.SerializedName;
import com.webforj.addons.components.suggestionedit.events.BlurredEvent;
import com.webforj.addons.components.suggestionedit.events.FocusedEvent;
import com.webforj.addons.components.suggestionedit.events.ModifiedEvent;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.ExpanseBase;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.concern.*;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a suggestion input component that allows users to enter text and receive suggestions.
 *
 * <p>
 * The {@code SuggestionEdit} component is a user interface element designed to facilitate input by
 * providing suggestions or autocomplete options based on user input. It allows users to enter text
 * and view a list of suggestions based on the entered text.
 * </p>
 *
 * <p>
 * This component supports events such as modification of the input value, focus and blur events,
 * which can be listened to by registering event listeners. This component can shine in cases where
 * developers don't want to show a huge list to avoid poor performance and only show suggestions and
 * picks based on the text passed to the component input.
 * </p>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-suggestion-edit")
@JavaScript(value = "https://d3hx2iico687v8.cloudfront.net/0.0.1/dwc-addons.esm.js", top = true,
    attributes = {@Attribute(name = "type", value = "module")})
public class SuggestionEdit extends ElementComposite
    implements HasMaxLength<SuggestionEdit>, HasMinLength<SuggestionEdit>,
    HasEnablement<SuggestionEdit>, HasExpanse<SuggestionEdit, SuggestionEdit.Expanse>,
    HasLabel<SuggestionEdit>, HasPlaceholder<SuggestionEdit>, HasReadOnly<SuggestionEdit> {

  /**
   * The control's expanse
   */
  public enum Expanse implements ExpanseBase {
    @SerializedName("xs")
    XSMALL,

    @SerializedName("s")
    SMALL,

    @SerializedName("m")
    MEDIUM,

    @SerializedName("l")
    LARGE,

    @SerializedName("xl")
    XLARGE,
  }

  /**
   * The placement of where the suggestions list should appear
   */
  public enum Placement {
    @SerializedName("bottom")
    BOTTOM,

    @SerializedName("bottom-end")
    BOTTOM_END,

    @SerializedName("bottom-start")
    BOTTOM_START,

    @SerializedName("left")
    LEFT,

    @SerializedName("left-end")
    LEFT_END,

    @SerializedName("left-start")
    LEFT_START,

    @SerializedName("right")
    RIGHT,

    @SerializedName("right-end")
    RIGHT_END,

    @SerializedName("right-start")
    RIGHT_START,

    @SerializedName("top")
    TOP,

    @SerializedName("top-end")
    TOP_END,

    @SerializedName("top-start")
    TOP_START
  }

  /**
   * The possible behaviours for highlighting input value.
   */
  public enum HighlightBehaviours {
    KEY, MOUSE, REQUEST
  }

  /**
   * Property for automatically focusing on the component on loading the page.
   */
  private final PropertyDescriptor<Boolean> autofocusProp =
      PropertyDescriptor.attribute("autofocus", false);

  /**
   * Property for blurring the component when an item is selected.
   */
  private final PropertyDescriptor<Boolean> blurOnSelectionProp =
      PropertyDescriptor.attribute("blur-on-selection", false);

  /**
   * Property for clearing the component when the {@code Escape} key is pressed.
   */
  private final PropertyDescriptor<Boolean> clearWithEscapeProp =
      PropertyDescriptor.attribute("clear-with-escape", true);

  /**
   * Property for allowing clearing of the component.
   */
  private final PropertyDescriptor<Boolean> clearableProp =
      PropertyDescriptor.attribute("clearable", true);

  /**
   * Property for opening the component dropdown when focused.
   */
  private final PropertyDescriptor<Boolean> openOnFocusProp =
      PropertyDescriptor.attribute("open-on-focus", false);

  /**
   * Property for disabling the component.
   */
  private final PropertyDescriptor<Boolean> disabledProp =
      PropertyDescriptor.property("disabled", false);

  /**
   * Property for the distance between the component and its suggestion list.
   */
  private final PropertyDescriptor<Integer> distanceProp =
      PropertyDescriptor.attribute("distance", 3);

  /**
   * Property for the expansiveness of the component.
   */
  private final PropertyDescriptor<Expanse> expanseProp =
      PropertyDescriptor.property("expanse", Expanse.MEDIUM);

  /**
   * Property for indicating whether the component has focus.
   */
  private final PropertyDescriptor<Boolean> hasFocusProp =
      PropertyDescriptor.property("hasFocus", false);

  /**
   * Property for highlighting behaviors of the component.
   */
  private final PropertyDescriptor<List<HighlightBehaviours>> highlightBehavioursProp =
      PropertyDescriptor.property("highlightBehaviors", new ArrayList<>());

  /**
   * Property for the label text of the component.
   */
  private final PropertyDescriptor<String> labelProp = PropertyDescriptor.attribute("label", null);

  /**
   * Property for limiting the maximum number of rows in the suggestion list to be shown.
   */
  private final PropertyDescriptor<Integer> maxRowCountProp =
      PropertyDescriptor.attribute("max-row-count", null);

  /**
   * Property for limiting the maximum length of the component input value.
   */
  private final PropertyDescriptor<Integer> maxLengthProp =
      PropertyDescriptor.attribute("maxlength", null);

  /**
   * Property for limiting the minimum length of the component input value.
   */
  private final PropertyDescriptor<Integer> minLengthProp =
      PropertyDescriptor.attribute("minlength", null);

  /**
   * Property for the name attribute of the component.
   */
  private final PropertyDescriptor<String> nameProp = PropertyDescriptor.attribute("name", null);

  /**
   * Property for the height of the component when open.
   */
  private final PropertyDescriptor<String> openHeightProp =
      PropertyDescriptor.attribute("open-height", "");

  /**
   * Property for the width of the component when open.
   */
  private final PropertyDescriptor<String> openWidthProp =
      PropertyDescriptor.attribute("open-width", "");

  /**
   * Property for the pattern of the component input value.
   */
  private final PropertyDescriptor<String> patternProp =
      PropertyDescriptor.attribute("pattern", null);

  /**
   * Property for the placeholder text of the input.
   */
  private final PropertyDescriptor<String> placeholderProp =
      PropertyDescriptor.attribute("placeholder", null);

  /**
   * Property for the {@link Placement} where the suggestion list is shown.
   */
  private final PropertyDescriptor<Placement> placementProp =
      PropertyDescriptor.property("placement", Placement.BOTTOM);

  /**
   * Property for indicating whether the component is read-only.
   */
  private final PropertyDescriptor<Boolean> readonlyProp =
      PropertyDescriptor.attribute("readonly", false);

  /**
   * Property for indicating whether the component is required.
   */
  private final PropertyDescriptor<Boolean> requiredProp =
      PropertyDescriptor.attribute("required", false);

  /**
   * Property for the size attribute of the component.
   */
  private final PropertyDescriptor<Integer> sizeProp = PropertyDescriptor.attribute("size", null);

  /**
   * Property for the skidding of the suggestion list from the input.
   */
  private final PropertyDescriptor<Integer> skiddingProp =
      PropertyDescriptor.attribute("skidding", 0);

  /**
   * Property for the list of suggestions for the component.
   */
  private final PropertyDescriptor<List<String>> suggestionsProp =
      PropertyDescriptor.property("suggestions", new ArrayList<>());

  /**
   * Property for indicating the tab traversable behavior of the component.
   */
  private final PropertyDescriptor<Integer> tabTraversableProp =
      PropertyDescriptor.attribute("tab-traversable", 0);

  /**
   * Property for the value of the component.
   */
  private final PropertyDescriptor<String> valueProp = PropertyDescriptor.property("value", "");

  /**
   * Adds a listener for the modified event, which is triggered when the input value is modified.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<ModifiedEvent> addModifiedListener(
      EventListener<ModifiedEvent> listener) {
    return this.addEventListener(ModifiedEvent.class, listener);
  }

  /**
   * Adds a listener for the focused event, which is triggered when the input field gains focus.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<FocusedEvent> addFocusedListener(
      EventListener<FocusedEvent> listener) {
    return this.addEventListener(FocusedEvent.class, listener);
  }

  /**
   * Adds a listener for the blurred event, which is triggered when the input field loses focus.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<BlurredEvent> addBlurredListener(
      EventListener<BlurredEvent> listener) {
    return this.addEventListener(BlurredEvent.class, listener);
  }

  /**
   * Retrieves the current value of the {@code autofocus} property, indicating whether the input
   * field should automatically receive focus when the component is rendered.
   *
   * @return {@code true} if autofocus is enabled, {@code false} otherwise.
   */
  public boolean getAutofocus() {
    return super.get(this.autofocusProp);
  }

  /**
   * Sets whether the input field should automatically receive focus when the component is rendered.
   *
   * @param autofocus {@code true} if autofocus is enabled, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setAutofocus(boolean autofocus) {
    super.set(this.autofocusProp, autofocus);
    return this;
  }

  /**
   * Retrieves the current value of the {@code blurOnSelection} property, indicating whether the
   * input field should automatically lose focus when a suggestion is selected.
   *
   * @return {@code true} if blurring on selection is enabled, {@code false} otherwise.
   */
  public boolean getBlurOnSelection() {
    return super.get(this.blurOnSelectionProp);
  }

  /**
   * Sets whether the input field should automatically blur (lose focus) when a suggestion is
   * selected.
   *
   * @param blurOnSelection {@code true} if blurring on selection is enabled, {@code false}
   *        otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setBlurOnSelection(boolean blurOnSelection) {
    super.set(this.blurOnSelectionProp, blurOnSelection);
    return this;
  }

  /**
   * Retrieves the current value of the {@code clearWithEscape} property, indicating whether the
   * input field should be cleared when the Escape key is pressed.
   *
   * @return {@code true} if clearing with Escape is enabled, {@code false} otherwise.
   */
  public boolean getClearWithEscape() {
    return super.get(this.clearWithEscapeProp);
  }

  /**
   * Sets whether the input field should be cleared when the {@code Escape} key is pressed.
   *
   * @param clearWithEscape {@code true} if clearing with Escape is enabled, {@code false}
   *        otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setClearWithEscape(boolean clearWithEscape) {
    super.set(this.clearWithEscapeProp, clearWithEscape);
    return this;
  }

  /**
   * Retrieves the current value of the {@code clearable} property, indicating whether the input
   * field should show cross icon for clearing the input value (i.e., have a clear icon button).
   *
   * @return {@code true} if the input field is shows a cross icon, {@code false} otherwise.
   */
  public boolean getClearable() {
    return super.get(this.clearableProp);
  }

  /**
   * Sets whether the input field should show a cross icon for clearing the input value (i.e., have
   * a clear icon button).
   *
   * @param clearable {@code true} if the input field is shows a cross icon, {@code false}
   *        otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setClearable(boolean clearable) {
    super.set(this.clearableProp, clearable);
    return this;
  }

  /**
   * Retrieves the current value of the {@code openOnFocus} property, indicating whether the
   * suggestion list should automatically open when the input field gains focus. Assuming there are
   * {@code suggestions} set
   *
   * @return {@code true} if opening on focus is enabled, {@code false} otherwise.
   */
  public boolean getOpenOnFocus() {
    return super.get(this.openOnFocusProp);
  }

  /**
   * Sets whether the suggestion list should automatically open when the input field gains focus.
   *
   * @param openOnFocus {@code true} if opening on focus is enabled, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setOpenOnFocus(boolean openOnFocus) {
    super.set(this.openOnFocusProp, openOnFocus);
    return this;
  }

  /**
   * Retrieves the current value of the {@code disabled} property, indicating whether the input
   * field is disabled.
   *
   * @return {@code true} if the input field is disabled, {@code false} otherwise.
   */
  public boolean getDisabled() {
    return super.get(this.disabledProp);
  }

  /**
   * Sets whether the input field is disabled.
   *
   * @param disabled {@code true} if the input field is disabled, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setDisabled(boolean disabled) {
    super.set(this.disabledProp, disabled);
    return this;
  }

  /**
   * Retrieves the current value of the {@code distance} property, which represents distance in
   * pixel from the input to the suggestions list.
   *
   * @return The distance value.
   */
  public int getDistance() {
    return super.get(this.distanceProp);
  }

  /**
   * Sets the distance in pixel from the input to the suggestions list.
   *
   * @param distance The distance value.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setDistance(int distance) {
    super.set(this.distanceProp, distance);
    return this;
  }

  /**
   * Retrieves the current value {@link Expanse} of the {@code expanse} property, indicating the
   * control's expanse.
   *
   * @return The expanse value.
   */
  public Expanse getExpanse() {
    return super.get(this.expanseProp);
  }

  /**
   * Sets the control's expanse.
   *
   * @param expanse The expanse value {@link Expanse}.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setExpanse(Expanse expanse) {
    super.set(this.expanseProp, expanse);
    return this;
  }

  /**
   * Retrieves the current value {@link HighlightBehaviours} of the {@code highlightBehaviours}
   * property, indicating the possible behaviors for highlighting input value.
   *
   * @return The list of highlight behaviors.
   */
  public List<HighlightBehaviours> getHighlightBehaviours() {
    return super.get(this.highlightBehavioursProp);
  }

  /**
   * Sets the possible behaviors for highlighting input value.
   *
   * @param highlightBehaviours The list of highlight behaviors.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setHighlightBehaviours(List<HighlightBehaviours> highlightBehaviours) {
    super.set(this.highlightBehavioursProp, highlightBehaviours);
    return this;
  }

  /**
   * Retrieves the current value of the {@code label} property, which represents the label of the
   * input field.
   *
   * @return The label of the input field.
   */
  public String getLabel() {
    return super.get(this.labelProp);
  }

  /**
   * Sets the label of the input field.
   *
   * @param label The label of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setLabel(String label) {
    super.set(this.labelProp, label);
    return this;
  }

  /**
   * Retrieves the current value of the {@code maxRowCount} property, which represents the maximum
   * number of suggestions to display.
   *
   * @return The maximum number of suggestions to display.
   */
  public int getMaxRowCount() {
    return super.get(this.maxRowCountProp);
  }

  /**
   * Sets the maximum number of suggestions to display.
   *
   * @param maxRowCount The maximum number of suggestions to display.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setMaxRowCount(int maxRowCount) {
    super.set(this.maxRowCountProp, maxRowCount);
    return this;
  }

  /**
   * Retrieves the current value of the {@code maxlength} property, indicating the maximum character
   * length of the input field.
   *
   * @return The maximum length of the input field.
   */
  public int getMaxLength() {
    return super.get(this.maxLengthProp);
  }

  /**
   * Sets the maximum character length of the input field.
   *
   * @param maxLength The maximum length of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setMaxLength(int maxLength) {
    super.set(this.maxLengthProp, maxLength);
    return this;
  }

  /**
   * Retrieves the current value of the {@code minlength} property, indicating the minimum character
   * length of the input field.
   *
   * @return The minimum length of the input field.
   */
  public int getMinLength() {
    return super.get(this.minLengthProp);
  }

  /**
   * Sets the minimum character length of the input field.
   *
   * @param minLength The minimum length of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setMinLength(int minLength) {
    super.set(this.minLengthProp, minLength);
    return this;
  }

  /**
   * Retrieves the current value of the {@code name} property, which represents the name of the
   * input field.
   *
   * @return The name of the input field.
   */
  public String getName() {
    return super.get(this.nameProp);
  }

  /**
   * Sets the name of the input field.
   *
   * @param name The name of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setName(String name) {
    super.set(this.nameProp, name);
    return this;
  }

  /**
   * Retrieves the current value of the {@code openHeight} property, which represents the max-height
   * of the suggestions list when it is open.
   *
   * @return The height of the suggestions list when it is open.
   */
  public String getOpenHeight() {
    return super.get(this.openHeightProp);
  }

  /**
   * Sets the max-height of the suggestions list when it is open.
   *
   * @param openHeight The height of the suggestions list when it is open.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setOpenHeight(String openHeight) {
    super.set(this.openHeightProp, openHeight);
    return this;
  }

  /**
   * Retrieves the current value of the {@code openWidth} property, which represents the max-width
   * of the suggestions list when it is open.
   *
   * @return The width of the suggestions list when it is open.
   */
  public String getOpenWidth() {
    return super.get(this.openWidthProp);
  }

  /**
   * Sets the max width of the suggestions list when it is open.
   *
   * @param openWidth The width of the suggestions list when it is open.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setOpenWidth(String openWidth) {
    super.set(this.openWidthProp, openWidth);
    return this;
  }

  /**
   * Retrieves the current value of the {@code pattern} property, which represents the regular
   * expression pattern that the input field's value must match.
   *
   * @return The regular expression pattern.
   */
  public String getPattern() {
    return super.get(this.patternProp);
  }

  /**
   * Sets the regular expression pattern that the input field's value must match.
   *
   * @param pattern The regular expression pattern.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setPattern(String pattern) {
    super.set(this.patternProp, pattern);
    return this;
  }

  /**
   * Retrieves the current value of the {@code placeholder} property, which represents the
   * placeholder text displayed in the input field when it is empty.
   *
   * @return The placeholder text.
   */
  public String getPlaceholder() {
    return super.get(this.placeholderProp);
  }

  /**
   * Sets the placeholder text displayed in the input field when it is empty.
   *
   * @param placeholder The placeholder text.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setPlaceholder(String placeholder) {
    super.set(this.placeholderProp, placeholder);
    return this;
  }

  /**
   * Retrieves the current value of the {@code placement} property, indicating the placement of
   * where the suggestions list should appear relative to the input field.
   *
   * @return The placement of the suggestions list.
   */
  public Placement getPlacement() {
    return super.get(this.placementProp);
  }

  /**
   * Sets the placement {@link Placement} of where the suggestions list should appear relative to
   * the input field.
   *
   * @param placement The placement of the suggestions list.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setPlacement(Placement placement) {
    super.set(this.placementProp, placement);
    return this;
  }

  /**
   * Retrieves the current value of the {@code readonly} property, indicating whether the input
   * field is in read-only mode.
   *
   * @return {@code true} if the input field is in read-only mode, {@code false} otherwise.
   */
  public boolean isReadOnly() {
    return super.get(this.readonlyProp);
  }

  /**
   * Sets whether the input field is in read-only mode.
   *
   * @param readonly {@code true} if the input field is in read-only mode, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setReadOnly(boolean readonly) {
    super.set(this.readonlyProp, readonly);
    return this;
  }

  /**
   * Retrieves the current value of the {@code required} property, indicating whether the input
   * field is required.
   *
   * @return {@code true} if the input field is required, {@code false} otherwise.
   */
  public boolean getRequired() {
    return super.get(this.requiredProp);
  }

  /**
   * Sets whether the input field is required.
   *
   * @param required {@code true} if the input field is required, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setRequired(boolean required) {
    super.set(this.requiredProp, required);
    return this;
  }

  /**
   * Retrieves the current value of the {@code size} property, which represents the size of the
   * input field.
   *
   * @return The size of the input field.
   */
  public int getSize() {
    return super.get(this.sizeProp);
  }

  /**
   * Sets the size of the input field.
   *
   * @param size The size of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setSize(int size) {
    super.set(this.sizeProp, size);
    return this;
  }

  /**
   * Retrieves the current value of the {@code skidding} property, which represents the skidding
   * offset of the suggestions list from the input field.
   *
   * @return The skidding offset of the suggestions list.
   */
  public int getSkidding() {
    return super.get(this.skiddingProp);
  }

  /**
   * Sets the skidding offset of the suggestions list from the input field.
   *
   * @param skidding The skidding offset of the suggestions list.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setSkidding(int skidding) {
    super.set(this.skiddingProp);
    return this;
  }

  /**
   * Retrieves the current value of the {@code suggestions} property, which represents the list of
   * suggestions to be displayed.
   *
   * @return The list of suggestions.
   */
  public List<String> getSuggestions() {
    return super.get(this.suggestionsProp);
  }

  /**
   * Sets the list of suggestions to be displayed.
   *
   * @param suggestions The list of suggestions.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setSuggestions(List<String> suggestions) {
    super.set(this.suggestionsProp, suggestions);
    return this;
  }

  /**
   * Retrieves the current value of the {@code tabTraversable} property, which represents the tab
   * index of the input field for keyboard navigation.
   *
   * @return The tab index of the input field.
   */
  public int getTabTraversable() {
    return super.get(this.tabTraversableProp);
  }

  /**
   * Sets the tab index of the {@code input} field for keyboard navigation.
   *
   * @param tabTraversable The tab index of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setTabTraversable(int tabTraversable) {
    super.set(this.tabTraversableProp, tabTraversable);
    return this;
  }

  /**
   * Retrieves the current value of the input field.
   *
   * @return The value of the input field.
   */
  public String getValue() {
    return super.get(this.valueProp);
  }

  /**
   * Sets the value of the input field.
   *
   * @param value The value to set.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setValue(String value) {
    super.set(this.valueProp, value);
    return this;
  }
}
