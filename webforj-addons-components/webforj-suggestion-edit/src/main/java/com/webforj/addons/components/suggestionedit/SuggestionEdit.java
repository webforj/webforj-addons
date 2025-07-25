package com.webforj.addons.components.suggestionedit;

import com.google.gson.annotations.SerializedName;
import com.webforj.PendingResult;
import com.webforj.addons.components.suggestionedit.SuggestionEdit.Expanse;
import com.webforj.addons.components.suggestionedit.events.BlurredEvent;
import com.webforj.addons.components.suggestionedit.events.ChangedEvent;
import com.webforj.addons.components.suggestionedit.events.ClosedEvent;
import com.webforj.addons.components.suggestionedit.events.FocusedEvent;
import com.webforj.addons.components.suggestionedit.events.InputEvent;
import com.webforj.addons.components.suggestionedit.events.OpenedEvent;
import com.webforj.addons.constant.GlobalConstants;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.ExpanseBase;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasClientAutoValidation;
import com.webforj.concern.HasClientAutoValidationOnLoad;
import com.webforj.concern.HasClientValidation;
import com.webforj.concern.HasClientValidationStyle;
import com.webforj.concern.HasEnablement;
import com.webforj.concern.HasExpanse;
import com.webforj.concern.HasFocusStatus;
import com.webforj.concern.HasJsExecution;
import com.webforj.concern.HasLabel;
import com.webforj.concern.HasMaxLength;
import com.webforj.concern.HasMinLength;
import com.webforj.concern.HasPlaceholder;
import com.webforj.concern.HasReadOnly;
import com.webforj.concern.HasRequired;
import com.webforj.concern.HasStyle;
import com.webforj.data.selection.SelectionRange;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a suggestion input component that allows users to enter text and receive suggestions.
 *
 * <p>The {@code SuggestionEdit} component is a user interface element designed to facilitate input
 * by providing suggestions or autocomplete options based on user input. It allows users to enter
 * text and view a list of suggestions based on the entered text.
 *
 * <p>This component supports events such as modification of the input value, focus and blur events,
 * which can be listened to by registering event listeners. This component can shine in cases where
 * developers don't want to show a huge list to avoid poor performance and only show suggestions and
 * picks based on the text passed to the component input.
 *
 * @author @ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-suggestion-edit")
@JavaScript(
    value = GlobalConstants.DWC_CDN,
    top = true,
    attributes = {@Attribute(name = "type", value = "module")})
public class SuggestionEdit extends ElementComposite
    implements HasMaxLength<SuggestionEdit>,
        HasMinLength<SuggestionEdit>,
        HasEnablement<SuggestionEdit>,
        HasExpanse<SuggestionEdit, Expanse>,
        HasLabel<SuggestionEdit>,
        HasPlaceholder<SuggestionEdit>,
        HasReadOnly<SuggestionEdit>,
        HasClassName<SuggestionEdit>,
        HasStyle<SuggestionEdit>,
        HasFocusStatus,
        HasJsExecution,
        HasRequired<SuggestionEdit>,
        HasClientAutoValidation<SuggestionEdit>,
        HasClientAutoValidationOnLoad<SuggestionEdit>,
        HasClientValidation<SuggestionEdit>,
        HasClientValidationStyle<SuggestionEdit> {

  /** The control's expanse. */
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

  /** The placement of where the suggestions list should appear. */
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

  /** The possible behaviours for highlighting input value. */
  public enum HighlightBehaviours {
    KEY,
    MOUSE,
    REQUEST
  }

  /** Property for automatically focusing on the component on loading the page. */
  private final PropertyDescriptor<Boolean> autofocusProp =
      PropertyDescriptor.attribute("autofocus", false);

  /** Property for blurring the component when an item is selected. */
  private final PropertyDescriptor<Boolean> blurOnSelectionProp =
      PropertyDescriptor.property("blurOnSelection", false);

  /** Property for clearing the component when the {@code Escape} key is pressed. */
  private final PropertyDescriptor<Boolean> clearWithEscapeProp =
      PropertyDescriptor.property("clearWithEscape", true);

  /** Property for allowing clearing of the component. */
  private final PropertyDescriptor<Boolean> clearableProp =
      PropertyDescriptor.property("clearable", true);

  /** Property for disabling the component. */
  private final PropertyDescriptor<Boolean> disabledProp =
      PropertyDescriptor.attribute("disabled", false);

  /** Property for the distance between the component and its suggestion list. */
  private final PropertyDescriptor<Integer> distanceProp =
      PropertyDescriptor.property("distance", 3);

  /** Property for the expansiveness of the component. */
  private final PropertyDescriptor<Expanse> expanseProp =
      PropertyDescriptor.property("expanse", Expanse.MEDIUM);

  /** Property for indicating whether the component has focus. */
  private final PropertyDescriptor<Boolean> hasFocusProp =
      PropertyDescriptor.property("hasFocus", false);

  /** Property for highlighting behaviors of the component. */
  private final PropertyDescriptor<List<HighlightBehaviours>> highlightBehavioursProp =
      PropertyDescriptor.property("highlightBehaviors", new ArrayList<>());

  /** Property for the label text of the component. */
  private final PropertyDescriptor<String> labelProp = PropertyDescriptor.property("label", null);

  /** Property for limiting the maximum number of rows in the suggestion list to be shown. */
  private final PropertyDescriptor<Integer> maxRowCountProp =
      PropertyDescriptor.property("maxRowCount", null);

  /** Property for limiting the maximum length of the component input value. */
  private final PropertyDescriptor<Integer> maxLengthProp =
      PropertyDescriptor.property("maxlength", null);

  /** Property for limiting the minimum length of the component input value. */
  private final PropertyDescriptor<Integer> minLengthProp =
      PropertyDescriptor.property("minlength", null);

  /** Property for the name attribute of the component. */
  private final PropertyDescriptor<String> nameProp = PropertyDescriptor.attribute("name", null);

  /** Property for the height of the component when open. */
  private final PropertyDescriptor<String> openHeightProp =
      PropertyDescriptor.property("openHeight", "");

  /** Property for the width of the component when open. */
  private final PropertyDescriptor<String> openWidthProp =
      PropertyDescriptor.property("openWidth", "");

  /** Property for the pattern of the component input value. */
  private final PropertyDescriptor<String> patternProp =
      PropertyDescriptor.attribute("pattern", null);

  /** Property for the placeholder text of the input. */
  private final PropertyDescriptor<String> placeholderProp =
      PropertyDescriptor.property("placeholder", null);

  /** Property for the {@link Placement} where the suggestion list is shown. */
  private final PropertyDescriptor<Placement> placementProp =
      PropertyDescriptor.property("placement", Placement.BOTTOM);

  /** Property for indicating whether the component is read-only. */
  private final PropertyDescriptor<Boolean> readonlyProp =
      PropertyDescriptor.attribute("readonly", false);

  /** Property for indicating whether the component is required. */
  private final PropertyDescriptor<Boolean> requiredProp =
      PropertyDescriptor.attribute("required", false);

  /** Property for the size attribute of the component. */
  private final PropertyDescriptor<Integer> sizeProp = PropertyDescriptor.attribute("size", null);

  /** Property for the skidding of the suggestion list from the input. */
  private final PropertyDescriptor<Integer> skiddingProp =
      PropertyDescriptor.property("skidding", 0);

  /** Property for the list of suggestions for the component. */
  private final PropertyDescriptor<List<SuggestionItem>> suggestionsProp =
      PropertyDescriptor.property("suggestions", new ArrayList<>());

  /** Property for indicating the tab traversable behavior of the component. */
  private final PropertyDescriptor<Integer> tabTraversableProp =
      PropertyDescriptor.property("tabTraversable", 0);

  /** Property for indicating whether the dropdown should toggle on input click. */
  private final PropertyDescriptor<Boolean> toggleOnClickProp =
      PropertyDescriptor.property("toggleOnClick", true);

  /** Property for whether the dropdown should open on arrow key down on the input. */
  private final PropertyDescriptor<Boolean> openOnArrowProp =
      PropertyDescriptor.property("openOnArrow", true);

  /** Property for the value of the component. */
  private final PropertyDescriptor<String> valueProp = PropertyDescriptor.property("value", "");

  /**
   * Property specifies the debounce delay for events triggered by typing input values. When the
   * user types, the input event is debounced by the specified delay to reduce the number of event
   * triggers.
   */
  private final PropertyDescriptor<Integer> debounceProp =
      PropertyDescriptor.property("debounce", 250);

  /**
   * Adds a listener for the input event, which is triggered when the input value is modified.
   * Property for enforcing automatic validation to the input.
   */
  private final PropertyDescriptor<Boolean> autoValidateProp =
      PropertyDescriptor.property("autoValidate", true);

  /** Property for triggering a validation on the input when the component loaded. */
  private final PropertyDescriptor<Boolean> autoValidateOnLoadProp =
      PropertyDescriptor.property("autoValidateOnLoad", false);

  /** Attribute determining whether the component is valid. */
  private final PropertyDescriptor<Boolean> validProp = PropertyDescriptor.property("valid", false);

  /** Attribute determining whether the component is invalid. */
  private final PropertyDescriptor<Boolean> invalidProp =
      PropertyDescriptor.property("invalid", false);

  /** Property for error message to display to the user when the component is invalid. */
  private final PropertyDescriptor<String> invalidMessageProp =
      PropertyDescriptor.property("invalidMessage", null);

  /**
   * Property for A JavaScript expression or function to validate the component on the client.
   *
   * <p>This expression can be a direct return statement or a function. If the expression includes
   * the return keyword, it is used as is; otherwise, it is wrapped with {@code return} and {@code
   * ;} to create a function.
   *
   * <p>The expression has access to the following parameters:
   *
   * <ul>
   *   <li><strong>value</strong>: The control's value.
   *   <li><strong>x</strong>: Alias for value.
   *   <li><strong>text</strong>: Same as value, included for consistency with the legacy control
   *       and form validation APIs.
   *   <li><strong>component</strong>: The instance of the client component.
   *   <li><strong>control</strong>: Alias for component.
   * </ul>
   */
  private final PropertyDescriptor<String> validatorProp =
      PropertyDescriptor.property("validator", null);

  /**
   * Property for specifying the appearance of the validation message. When {@code popover}, the
   * invalid message will be displayed as a popover; otherwise it will be displayed as an inline
   * message.
   */
  private final PropertyDescriptor<HasClientValidationStyle.ValidationStyle> validationStyleProp =
      PropertyDescriptor.property("validationStyle", null);

  /**
   * Adds a listener for the modified event, which is triggered when the input value is modified.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<InputEvent> addInputListener(EventListener<InputEvent> listener) {
    return this.addEventListener(InputEvent.class, listener);
  }

  /**
   * Adds a listener for the change event, which is triggered when the value is changed to a
   * suggestion.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<ChangedEvent> addChangedListener(
      EventListener<ChangedEvent> listener) {
    return this.addEventListener(ChangedEvent.class, listener);
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
   * Adds a listener for the opened event, which is triggered when the suggestions dropdown is
   * opened.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<OpenedEvent> addOpenedListener(EventListener<OpenedEvent> listener) {
    return this.addEventListener(OpenedEvent.class, listener);
  }

  /**
   * Adds a listener for the closed event, which is triggered when the suggestions dropdown is
   * closed.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<ClosedEvent> addClosedListener(EventListener<ClosedEvent> listener) {
    return this.addEventListener(ClosedEvent.class, listener);
  }

  /**
   * Sets focus on the component's input field, allowing user interaction.
   *
   * <p>This method programmatically shifts focus to the component's input field, simulating a user
   * click or tab action. This can be used to ensure the component is ready for input, particularly
   * in forms or interactive interfaces.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> setFocus() {
    return getBoundComponent().callJsFunctionAsync("setFocus");
  }

  /**
   * Removes focus from the component's input field, preventing further user interaction.
   *
   * <p>This method programmatically removes focus from the input field of the component, simulating
   * a user action that would cause the component to lose focus. It is useful when you need to shift
   * focus to other parts of the UI.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> removeFocus() {
    return getBoundComponent().callJsFunctionAsync("removeFocus");
  }

  /**
   * Retrieves the current caret position within the component's input field.
   *
   * <p>This method returns the index of the caret's position, which can be used to determine where
   * in the text the user is currently focused. This is especially useful for implementing advanced
   * input manipulation or analysis.
   *
   * @return the zero-based index position of the caret.
   */
  public PendingResult<Object> getCursorPos() {
    return getBoundComponent().callJsFunctionAsync("getCursorPos");
  }

  /**
   * Sets the caret position within the component's input field.
   *
   * <p>This method moves the caret to the specified position, allowing precise control over where
   * user input or text manipulation should occur.
   *
   * @param index the zero-based index where the caret should be positioned.
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> setCursorPos(int index) {
    return getBoundComponent().callJsFunctionAsync("setCursorPos", index);
  }

  /**
   * Retrieves the current text selection range within the input field.
   *
   * <p>This method returns the start and end positions of the text selection, enabling programmatic
   * interaction with the selected text. It is useful for implementing text-related features like
   * copy, cut, or formatting.
   *
   * @return an object of {@link SelectionRange} describing the selection range.
   */
  public PendingResult<Object> getSelectionRange() {
    return getBoundComponent().callJsFunctionAsync("getSelectionRange");
  }

  /**
   * Sets the text selection range within the component's input field.
   *
   * <p>This method selects the text between the specified start and end positions, allowing for
   * programmatic highlighting or text manipulation.
   *
   * @param start the start position of the selection.
   * @param end the end position of the selection.
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> setSelectionRange(int start, int end) {
    return getBoundComponent().callJsFunctionAsync("setSelectionRange", start, end);
  }

  /**
   * Opens the dropdown menu, displaying all available options.
   *
   * <p>This method triggers the dropdown to expand, allowing users to view and select from the list
   * of available options. It is useful when you want to programmatically open the dropdown in
   * response to other UI actions.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> open() {
    return getBoundComponent().callJsFunctionAsync("open");
  }

  /**
   * Closes the dropdown menu, hiding all available options.
   *
   * <p>This method programmatically closes the dropdown menu, typically used to hide the options
   * list after a selection is made or in response to other UI actions.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> close() {
    return getBoundComponent().callJsFunctionAsync("close");
  }

  /**
   * Toggles the dropdown menu, opening or closing it based on its current state.
   *
   * <p>This method alternates the dropdown's state, ensuring it opens if it is currently closed or
   * closes if it is currently open. It provides dynamic control over the menu's visibility.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> toggle() {
    return getBoundComponent().callJsFunctionAsync("toggle");
  }

  /**
   * Activates a specific item in the dropdown menu.
   *
   * <p>This method sets a specified item as active, highlighting it for the user. The item can be
   * identified by its index or an HTML element.
   *
   * @param index an index identifying the item to activate.
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> activateItem(int index) {
    return getBoundComponent().callJsFunctionAsync("activateItem", index);
  }

  /**
   * Scrolls to a specified item in the dropdown menu.
   *
   * <p>This method ensures the specified item is brought into view, useful for navigating long
   * lists of options programmatically.
   *
   * @param index the zero-based index of the item to scroll to.
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> scrollToIndex(int index) {
    return getBoundComponent().callJsFunctionAsync("scrollToIndex", index);
  }

  /**
   * Selects a specific item in the dropdown menu.
   *
   * <p>This method marks an item as selected, allowing programmatic selection of dropdown options.
   * The item can be identified by its index or an HTML element.
   *
   * @param index an index identifying the item to select.
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> selectItem(int index) {
    return getBoundComponent().callJsFunctionAsync("selectItem", index);
  }

  /**
   * Validates the input value of the component.
   *
   * <p>This method programmatically triggers the validation of the input value. It is useful for
   * ensuring that the current input satisfies any predefined constraints or rules. The result of
   * the validation can be used to check whether the input is valid or not.
   *
   * @return a pending result which will be resolved with the validation outcome. It returns a
   *     boolean where {@code true} indicates valid input and {@code false} indicates invalid input.
   */
  public PendingResult<Object> validate() {
    return getBoundComponent().callJsFunctionAsync("validate");
  }

  /** {@inheritDoc} */
  @Override
  public Object executeJs(String js) {
    return super.getElement().executeJs(js);
  }

  /** {@inheritDoc} */
  @Override
  public PendingResult<Object> executeJsAsync(String js) {
    return super.getElement().executeJsAsync(js);
  }

  /** {@inheritDoc} */
  @Override
  public void executeJsVoidAsync(String js) {
    super.getElement().executeJsVoidAsync(js);
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
   *     otherwise.
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
   *     otherwise.
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
   *     otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setClearable(boolean clearable) {
    super.set(this.clearableProp, clearable);
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
  @Override
  public Expanse getExpanse() {
    return super.get(this.expanseProp);
  }

  /**
   * Sets the control's expanse.
   *
   * @param expanse The expanse value {@link Expanse}.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setExpanse(Expanse expanse) {
    super.set(this.expanseProp, expanse);
    return this;
  }

  /**
   * Retrieves the current focus state of the component in the document.
   *
   * @return The focus value.
   */
  @Override
  public boolean hasFocus() {
    return super.get(this.hasFocusProp);
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
  @Override
  public String getLabel() {
    return super.get(this.labelProp);
  }

  /**
   * Sets the label of the input field.
   *
   * @param label The label of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public int getMaxLength() {
    return super.get(this.maxLengthProp);
  }

  /**
   * Sets the maximum character length of the input field.
   *
   * @param maxLength The maximum length of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public int getMinLength() {
    return super.get(this.minLengthProp);
  }

  /**
   * Sets the minimum character length of the input field.
   *
   * @param minLength The minimum length of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public String getName() {
    return super.get(this.nameProp);
  }

  /**
   * Sets the name of the input field.
   *
   * @param name The name of the input field.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public String getPlaceholder() {
    return super.get(this.placeholderProp);
  }

  /**
   * Sets the placeholder text displayed in the input field when it is empty.
   *
   * @param placeholder The placeholder text.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public boolean isReadOnly() {
    return super.get(this.readonlyProp);
  }

  /**
   * Sets whether the input field is in read-only mode.
   *
   * @param readonly {@code true} if the input field is in read-only mode, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  @Override
  public boolean isRequired() {
    return super.get(this.requiredProp);
  }

  /**
   * Sets whether the input field is required.
   *
   * @param required {@code true} if the input field is required, {@code false} otherwise.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
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
  public List<SuggestionItem> getSuggestions() {
    return super.get(this.suggestionsProp);
  }

  /**
   * Sets the list of suggestions to be displayed.
   *
   * @param suggestions The list of suggestions.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setSuggestions(List<SuggestionItem> suggestions) {
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
   * Retrieves whether the dropdown should toggle on input click.
   *
   * @return {@code true} if dropdown toggling on input click is enabled, otherwise {@code false}.
   */
  public boolean isToggleOnClick() {
    return super.get(this.toggleOnClickProp);
  }

  /**
   * Sets whether the dropdown should toggle on input click.
   *
   * @param toggleOnClick {@code true} to enable dropdown toggling on input click, {@code false} to
   *     disable.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setToggleOnClick(boolean toggleOnClick) {
    super.set(this.toggleOnClickProp, toggleOnClick);
    return this;
  }

  /**
   * Retrieves whether the dropdown should open on arrow key down on the input.
   *
   * @return {@code true} if dropdown opening on arrow key down is enabled, otherwise {@code false}.
   */
  public boolean isOpenOnArrow() {
    return super.get(this.openOnArrowProp, true);
  }

  /**
   * Sets whether the dropdown should open on arrow key down on the input.
   *
   * @param openOnArrow {@code true} to enable dropdown opening on arrow key down, {@code false} to
   *     disable.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setOpenOnArrow(boolean openOnArrow) {
    super.set(this.openOnArrowProp, openOnArrow);
    return this;
  }

  /**
   * Retrieves the current value of the input field.
   *
   * @return The value of the input field.
   */
  public String getValue() {
    return super.get(this.valueProp, true);
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

  /**
   * Retrieves the debounce delay for events triggered by typing input values.
   *
   * @return The debounce delay in milliseconds.
   */
  public int getDebounce() {
    return super.get(this.debounceProp);
  }

  /**
   * Sets the debounce delay for events triggered by typing input values.
   *
   * @param debounce The debounce delay in milliseconds.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setDebounce(int debounce) {
    super.set(this.debounceProp, debounce);
    return this;
  }

  /**
   * Retrieves the auto-validate value of the input element.
   *
   * @return The auto-validate value.
   */
  @Override
  public boolean isAutoClientValidate() {
    return get(this.autoValidateProp);
  }

  /**
   * Sets the auto-validate value of the input element.
   *
   * @param autoValidate The auto-validate property.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setAutoClientValidate(boolean autoValidate) {
    set(this.autoValidateProp, autoValidate);
    return this;
  }

  /**
   * Retrieves the auto-validate-on-load property value.
   *
   * @return The value indicating whether to trigger validation on load.
   */
  public boolean isAutoValidateOnLoad() {
    return get(this.autoValidateOnLoadProp);
  }

  /**
   * Sets the auto-validate-on-load property value.
   *
   * @param autoValidateOnLoad The value indicating whether to trigger validation on load.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setAutoValidateOnLoad(boolean autoValidateOnLoad) {
    set(this.autoValidateOnLoadProp, autoValidateOnLoad);
    return this;
  }

  /**
   * Adds a CSS class to the list of CSS classes for the component.
   *
   * @param classNames the name of the CSS class to be added.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit addClassName(String... classNames) {
    super.getElement().addClassName(classNames);
    return this;
  }

  /**
   * Removes a CSS class from the list of CSS classes for the component.
   *
   * @param classNames the name of the CSS class to be removed.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit removeClassName(String... classNames) {
    super.getElement().removeClassName(classNames);
    return this;
  }

  /**
   * Retrieves the valid property value.
   *
   * @return The value indicating whether the component is valid.
   */
  public boolean isValid() {
    return get(this.validProp, true);
  }

  /**
   * Sets the valid property value.
   *
   * @param valid The value indicating whether the component is valid.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setValid(boolean valid) {
    set(this.validProp, valid);
    return this;
  }

  /**
   * Retrieves the invalid property value.
   *
   * @return The value indicating whether the component is invalid.
   */
  @Override
  public boolean isInvalid() {
    return get(this.invalidProp, true);
  }

  /**
   * Sets the invalid property value.
   *
   * @param invalid The value indicating whether the component is invalid.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setInvalid(boolean invalid) {
    set(this.invalidProp, invalid);
    return this;
  }

  /**
   * Gets the computed value of a CSS property.
   *
   * <p>This method is used to obtain the computed value of a CSS property for the component. The
   * computed value represents the final value that is applied to the element after considering all
   * styles applied to it, including styles inherited from parent elements and user-agent defaults.
   *
   * @param property The CSS property to be retrieved
   * @return String containing all computed styles
   */
  @Override
  public String getComputedStyle(String property) {
    return super.getElement().getComputedStyle(property);
  }

  /**
   * Gets the value of a CSS property.
   *
   * <p>This method is intended to be used to retrieve the value of a CSS property of a component.
   *
   * @param property The CSS property to be retrieved
   * @return String containing the value of the CSS property
   */
  @Override
  public String getStyle(String property) {
    return super.getElement().getStyle(property);
  }

  /**
   * Sets a CSS property to a specific value.
   *
   * <p>This method is intended to be used to modify a single CSS property of a component.
   *
   * @param property The CSS property to be changed
   * @param value The value to be assigned to the CSS property
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setStyle(String property, String value) {
    super.getElement().setStyle(property, value);
    return this;
  }

  /**
   * Removes a CSS property to a specific value.
   *
   * @param property The CSS property to be changed
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit removeStyle(String property) {
    super.getElement().removeStyle(property);
    return this;
  }

  /**
   * Retrieves the invalid message property value.
   *
   * @return The error message to display when the component is invalid.
   */
  @Override
  public String getInvalidMessage() {
    return get(this.invalidMessageProp);
  }

  /**
   * Sets the invalid message property value.
   *
   * @param invalidMessage The error message to display when the component is invalid.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setInvalidMessage(String invalidMessage) {
    set(this.invalidMessageProp, invalidMessage);
    return this;
  }

  /**
   * Retrieves the validator property value.
   *
   * @return The JavaScript expression or function to validate the component on the client.
   */
  public String getValidator() {
    return get(this.validatorProp);
  }

  /**
   * Sets the validator property value.
   *
   * @param validator The JavaScript expression or function to validate the component on the client.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  public SuggestionEdit setValidator(String validator) {
    set(this.validatorProp, validator);
    return this;
  }

  /**
   * Retrieves the validation style property value.
   *
   * @return The validation style indicating how the invalid message is displayed.
   */
  @Override
  public ValidationStyle getValidationStyle() {
    return get(this.validationStyleProp);
  }

  /**
   * Sets the validation style property value.
   *
   * @param validationStyle The validation style indicating how the invalid message is displayed.
   * @return This {@code SuggestionEdit} instance for method chaining.
   */
  @Override
  public SuggestionEdit setValidationStyle(ValidationStyle validationStyle) {
    set(this.validationStyleProp, validationStyle);
    return this;
  }
}
