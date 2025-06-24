package com.webforj.addons.components.multiselectcombo;

import com.google.gson.annotations.SerializedName;
import com.webforj.PendingResult;
import com.webforj.addons.components.multiselectcombo.MultiSelectCombo.Expanse;
import com.webforj.addons.components.multiselectcombo.events.BlurredEvent;
import com.webforj.addons.components.multiselectcombo.events.FocusedEvent;
import com.webforj.addons.components.multiselectcombo.events.InputEvent;
import com.webforj.addons.components.multiselectcombo.events.KeydownEvent;
import com.webforj.addons.components.multiselectcombo.events.OpenedChangedEvent;
import com.webforj.addons.components.multiselectcombo.events.SelectedChangedEvent;
import com.webforj.addons.components.multiselectcombo.events.ValidatedEvent;
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
import com.webforj.concern.HasExpanse;
import com.webforj.concern.HasFocusStatus;
import com.webforj.concern.HasPlaceholder;
import com.webforj.concern.HasReadOnly;
import com.webforj.data.selection.SelectionRange;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A multi-select combo component designed for webforj. The {@code MultiSelectCombo} enables users
 * to efficiently select multiple items from a dropdown list, offering a convenient solution for
 * scenarios where users need to make multiple selections from a predefined set of options. By
 * presenting a dropdown list of available choices, users can easily navigate through the options
 * and select multiple items according to their preferences or requirements.
 *
 * <p>The {@code MultiSelectCombo} component is ideal for scenarios where users need to select
 * multiple items from a predefined list. It enhances user interaction by providing a flexible and
 * intuitive interface for selecting and managing multiple options effectively.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-multi-select-combo")
@JavaScript(
    value = GlobalConstants.DWC_CDN,
    top = true,
    attributes = {@Attribute(name = "type", value = "module")})
public class MultiSelectCombo extends ElementComposite
    implements HasPlaceholder<MultiSelectCombo>,
        HasClassName<MultiSelectCombo>,
        HasFocusStatus,
        HasReadOnly<MultiSelectCombo>,
        HasExpanse<MultiSelectCombo, Expanse>,
        HasClientAutoValidation<MultiSelectCombo>,
        HasClientAutoValidationOnLoad<MultiSelectCombo>,
        HasClientValidation<MultiSelectCombo>,
        HasClientValidationStyle<MultiSelectCombo> {

  /** The placement of where the dropdown list should appear. */
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

  /** The expansiveness of the component. */
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
    XLARGE
  }

  /** The possible behaviours for highlighting input value. */
  public enum HighlightBehaviours {
    KEY,
    MOUSE,
    REQUEST
  }

  /** The placement of validation popover. */
  public enum ValidationPopoverPlacement {
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

  /** Property for specifying the placement of the list. */
  private final PropertyDescriptor<Placement> placementProp =
      PropertyDescriptor.property("placement", Placement.BOTTOM);

  /** Property for the expansiveness of the component. */
  private final PropertyDescriptor<Expanse> expanseProp =
      PropertyDescriptor.property("expanse", Expanse.MEDIUM);

  /** Property for specifying the renderer for each item in the list. */
  private final PropertyDescriptor<String> rendererProp =
      PropertyDescriptor.property("renderer", null);

  /** Property for the list of items displayed by the component. */
  private final PropertyDescriptor<List<MultiSelectComboItem>> itemsProp =
      PropertyDescriptor.property("items", null);

  /** Property for the list of selected items in the component. */
  private final PropertyDescriptor<List<String>> selectedProp =
      PropertyDescriptor.property("selected", null);

  /** Property for automatically focusing on the component upon loading the page. */
  private final PropertyDescriptor<Boolean> autoFocusProp =
      PropertyDescriptor.attribute("autofocus", false);

  /** Property for indicating whether the component has focus. */
  private final PropertyDescriptor<Boolean> hasFocusProp =
      PropertyDescriptor.property("hasFocus", false);

  /** Property for indicating whether the component is required. */
  private final PropertyDescriptor<Boolean> requiredProp =
      PropertyDescriptor.attribute("required", false);

  /** Property for specifying the tab traversable behavior of the component. */
  private final PropertyDescriptor<Integer> tabTraversableProp =
      PropertyDescriptor.property("tabTraversable", 0);

  /** Property for the value of the input associated with the component. */
  private final PropertyDescriptor<String> inputValueProp =
      PropertyDescriptor.property("inputValue", "");

  /** Property for defining highlight behaviors for the component. */
  private final PropertyDescriptor<List<HighlightBehaviours>> highlightBehaviorsProp =
      PropertyDescriptor.property("highlightBehaviors", new ArrayList<>());

  /** Property for specifying the name attribute of the component. */
  private final PropertyDescriptor<String> nameProp = PropertyDescriptor.attribute("name", "");

  /** Property for specifying the label attribute of the component. */
  private final PropertyDescriptor<String> labelProp = PropertyDescriptor.property("label", "");

  /** Property for specifying the placeholder text of the component. */
  private final PropertyDescriptor<String> placeholderProp =
      PropertyDescriptor.attribute("placeholder", "");

  /** Property for defining the size of the input associated with the component. */
  private final PropertyDescriptor<Float> inputSizeProp =
      PropertyDescriptor.property("inputSize", null);

  /** Property for allowing custom values in the component. */
  private final PropertyDescriptor<Boolean> allowCustomValueProp =
      PropertyDescriptor.property("allowCustomValue", false);

  /** Property for specifying the maximum number of rows displayed by the component. */
  private final PropertyDescriptor<Integer> maxRowCountProp =
      PropertyDescriptor.property("maxRowCount", null);

  /** Property for controlling whether the dropdown opens on arrow key press. */
  private final PropertyDescriptor<Boolean> openOnArrowProp =
      PropertyDescriptor.property("openOnArrow", true);

  /** Property for disabling the component. */
  private final PropertyDescriptor<Boolean> disabledProp =
      PropertyDescriptor.attribute("disabled", false);

  /** Property for setting the component to read-only mode. */
  private final PropertyDescriptor<Boolean> readonlyProp =
      PropertyDescriptor.attribute("readonly", false);

  /** Property for specifying the distance between the component and its dropdown list. */
  private final PropertyDescriptor<Integer> distanceProp =
      PropertyDescriptor.property("distance", null);

  /** Property for specifying the maximum length of chips in the component. */
  private final PropertyDescriptor<Integer> maxChipsLengthProp =
      PropertyDescriptor.property("maxChipsLength", 65);

  /** Property for indicating whether the dropdown is opened. */
  private final PropertyDescriptor<Boolean> openedProp =
      PropertyDescriptor.property("opened", false);

  /** Property for specifying the width of the opened dropdown. */
  private final PropertyDescriptor<Integer> openWidthProp =
      PropertyDescriptor.property("openWidth", null);

  /** Property for specifying the height of the opened dropdown. */
  private final PropertyDescriptor<Integer> openHeightProp =
      PropertyDescriptor.property("openHeight", null);

  /** Property for specifying the skidding of the opened dropdown. */
  private final PropertyDescriptor<Integer> skiddingProp =
      PropertyDescriptor.property("skidding", null);

  /** Property for enforcing automatic validation to the input. */
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
  private final PropertyDescriptor<ValidationStyle> validationStyleProp =
      PropertyDescriptor.property("validationStyle", null);

  /** Property for automatically switching on the valid property after validation. */
  private final PropertyDescriptor<Boolean> autoWasValidatedProp =
      PropertyDescriptor.property("autoWasValidated", false);

  /** Property for specifying the validation icon. */
  private final PropertyDescriptor<String> validationIconProp =
      PropertyDescriptor.property("validationIcon", "");

  /** Property for specifying the distance of the validation popover from the control. */
  private final PropertyDescriptor<Integer> validationPopoverDistanceProp =
      PropertyDescriptor.property("validationPopoverDistance", 6);

  /** Property for specifying the placement of the validation popover. */
  private final PropertyDescriptor<ValidationPopoverPlacement> validationPopoverPlacementProp =
      PropertyDescriptor.property("validationPopoverPlacement", ValidationPopoverPlacement.BOTTOM);

  /** Property for specifying the skidding of the validation popover along the control. */
  private final PropertyDescriptor<Integer> validationPopoverSkiddingProp =
      PropertyDescriptor.property("validationPopoverSkidding", 0);

  /**
   * Add a listener for the opened event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<OpenedChangedEvent> addOpenedChangedListener(
      EventListener<OpenedChangedEvent> listener) {
    return super.addEventListener(OpenedChangedEvent.class, listener);
  }

  /**
   * Add a listener for the input change event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<InputEvent> addInputListener(EventListener<InputEvent> listener) {
    return super.addEventListener(InputEvent.class, listener);
  }

  /**
   * Add a listener for the focus event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<FocusedEvent> addFocusedListener(
      EventListener<FocusedEvent> listener) {
    return super.addEventListener(FocusedEvent.class, listener);
  }

  /**
   * Add a listener for the blur event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<BlurredEvent> addBlurredListener(
      EventListener<BlurredEvent> listener) {
    return super.addEventListener(BlurredEvent.class, listener);
  }

  /**
   * Add a listener for the change of selected options' event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<SelectedChangedEvent> addSelectedChangedListener(
      EventListener<SelectedChangedEvent> listener) {
    return super.addEventListener(SelectedChangedEvent.class, listener);
  }

  /**
   * Add a listener for the keydown on the component part event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<KeydownEvent> addKeydownListener(
      EventListener<KeydownEvent> listener) {
    return super.addEventListener(KeydownEvent.class, listener);
  }

  /**
   * Add a listener for the validation status change event.
   *
   * @param listener the listener
   * @return the control
   */
  public ListenerRegistration<ValidatedEvent> addValidatedListener(
      EventListener<ValidatedEvent> listener) {
    return super.addEventListener(ValidatedEvent.class, listener);
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
   * Clears the input field and removes all selected items from the component.
   *
   * <p>This method resets the component, clearing any text in the input field and deselecting all
   * currently selected items. It is helpful in scenarios where the user needs to start fresh
   * without any previous selections.
   *
   * @return a pending result which will be resolved with void.
   */
  public PendingResult<Object> clear() {
    return getBoundComponent().callJsFunctionAsync("clear");
  }

  /**
   * Adds a single item to the combo box list.
   *
   * @param item The {@link MultiSelectComboItem} to add. Cannot be null.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   * @throws NullPointerException if item is null.
   */
  public MultiSelectCombo addItem(MultiSelectComboItem item) {
    Objects.requireNonNull(item, "Item cannot be null");
    final var updatedItems = getMutableItemsList();
    updatedItems.add(item);
    return setItems(updatedItems);
  }

  /**
   * Convenience method to create and add a simple item with only a label and value.
   *
   * @param label The text displayed to the user. Cannot be null.
   * @param value The internal value associated with the item. Cannot be null.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo addItem(String label, String value) {
    final var item = MultiSelectComboItem.builder().label(label).value(value).build();
    return addItem(item);
  }

  /**
   * Adds multiple items to the combo box list.
   *
   * @param items The {@link MultiSelectComboItem}s to add. Cannot be null.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   * @throws NullPointerException if items array is null or contains null elements.
   */
  public MultiSelectCombo addItems(MultiSelectComboItem... items) {
    Objects.requireNonNull(items, "Items array cannot be null");
    if (Arrays.stream(items).anyMatch(Objects::isNull)) {
      throw new NullPointerException("Items array cannot contain null elements");
    }
    final var updatedItems = getMutableItemsList();
    updatedItems.addAll(Arrays.asList(items));
    if (!updatedItems.isEmpty()) {
      return setItems(updatedItems);
    }
    return this;
  }

  /**
   * Adds a collection of items to the combo box list.
   *
   * @param items A collection of {@link MultiSelectComboItem}s to add. Cannot be null.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   * @throws NullPointerException if the collection is null or contains null elements.
   */
  public MultiSelectCombo addItems(Collection<MultiSelectComboItem> items) {
    Objects.requireNonNull(items, "Items collection cannot be null");
    if (items.stream().anyMatch(Objects::isNull)) {
      throw new NullPointerException("Items collection cannot contain null elements");
    }

    final var updatedItems = getMutableItemsList();
    updatedItems.addAll(items);
    if (!updatedItems.isEmpty()) {
      return setItems(updatedItems);
    }
    return this;
  }

  /**
   * Removes a specific item from the combo box list based on the item's value.
   *
   * @param item The {@link MultiSelectComboItem} to remove. Comparison is based on the item's
   *     value.
   * @return {@code true} if the item was found and removed; {@code false} if the item was not found
   *     or if the item is null.
   */
  public boolean removeItem(MultiSelectComboItem item) {
    if (item == null) {
      return false;
    }
    return removeItemByValue(item.getValue());
  }

  /**
   * Removes an item from the combo box list based on its value.
   *
   * @param value The value of the item to remove.
   * @return {@code true} if the item was found and removed; {@code false} if the value is null or
   *     the item was not found.
   */
  public boolean removeItemByValue(String value) {
    if (value == null) {
      return false;
    }
    final var currentItems = getItems();
    if (currentItems == null || currentItems.isEmpty()) {
      return false;
    }

    final var updatedItems = new ArrayList<>(currentItems);
    boolean removed = updatedItems.removeIf(currentItem -> value.equals(currentItem.getValue()));

    if (removed) {
      setItems(updatedItems);
    }
    return removed;
  }

  /**
   * Removes all items from the combo box list.
   *
   * @return {@code true} if items were cleared successfully; {@code false} if there were no items
   *     to clear.
   */
  public boolean clearItems() {
    final var currentItems = getItems();
    boolean hadItems = currentItems != null && !currentItems.isEmpty();
    setItems(Collections.emptyList());
    return hadItems;
  }

  private List<MultiSelectComboItem> getMutableItemsList() {
    final var currentItems = getItems();
    if (currentItems == null) {
      return new ArrayList<>();
    } else {
      return new ArrayList<>(currentItems);
    }
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

  /**
   * Retrieves the label of the control.
   *
   * @return The label value.
   */
  public String getLabel() {
    return get(this.labelProp);
  }

  /**
   * Sets the label of the control.
   *
   * @param label The label value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setLabel(String label) {
    set(this.labelProp, label);
    return this;
  }

  /**
   * Retrieves the placement of the control.
   *
   * @return The placement value.
   */
  public Placement getPlacement() {
    return get(this.placementProp);
  }

  /**
   * Sets the placement of the control.
   *
   * @param placement The placement value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setPlacement(Placement placement) {
    set(this.placementProp, placement);
    return this;
  }

  /**
   * Retrieves the expansiveness of the control.
   *
   * @return The expanse value.
   */
  @Override
  public Expanse getExpanse() {
    return get(this.expanseProp);
  }

  /**
   * Sets the expansiveness of the control.
   *
   * @param expanse The expanse value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setExpanse(Expanse expanse) {
    set(this.expanseProp, expanse);
    return this;
  }

  /**
   * Retrieves the list of items of the control.
   *
   * @return The items value.
   */
  public List<MultiSelectComboItem> getItems() {
    return get(this.itemsProp);
  }

  /**
   * Sets the list of items of the control.
   *
   * @param multiSelectComboItems The items value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setItems(List<MultiSelectComboItem> multiSelectComboItems) {
    set(this.itemsProp, multiSelectComboItems);
    return this;
  }

  /**
   * Retrieves the renderer of the control.
   *
   * @return The renderer value.
   */
  public String getRenderer() {
    return get(this.rendererProp);
  }

  /**
   * Sets the renderer of the control.
   *
   * @param renderer The renderer value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setRenderer(String renderer) {
    set(this.rendererProp, renderer);
    return this;
  }

  /**
   * Retrieves the list of selected items of the control.
   *
   * @return The selected value.
   */
  public List<String> getSelected() {
    return get(this.selectedProp, true, List.class);
  }

  /**
   * Sets the list of selected items of the control.
   *
   * @param selected The selected value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setSelected(List<String> selected) {
    set(this.selectedProp, selected);
    return this;
  }

  /**
   * Retrieves the value indicating whether autofocus is enabled.
   *
   * @return The autofocus value.
   */
  public Boolean getAutoFocus() {
    return get(this.autoFocusProp);
  }

  /**
   * Sets the value indicating whether autofocus is enabled.
   *
   * @param autoFocus The autofocus value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setAutoFocus(Boolean autoFocus) {
    set(this.autoFocusProp, autoFocus);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasFocus() {
    return get(this.hasFocusProp);
  }

  /**
   * Retrieves the value indicating whether the control is required.
   *
   * @return The required value.
   */
  public Boolean getRequired() {
    return get(this.requiredProp);
  }

  /**
   * Sets the value indicating whether the control is required.
   *
   * @param required The required value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setRequired(Boolean required) {
    set(this.requiredProp, required);
    return this;
  }

  /**
   * Checks whether the user can focus to the component using the tab key.
   *
   * <p>It's important to note that if the component is set as focusable but is disabled, the user
   * won't be able to navigate to the component using the Tab key or focus it programmatically.
   * However, the component will still be included in the tab order.
   *
   * @return true if the user can navigate to the component with the Tab key, false if not.
   */
  public boolean isFocusable() {
    final var tabTraversable = get(this.tabTraversableProp);
    return tabTraversable > -1;
  }

  /**
   * Sets whether the user can focus to the component using the tab key.
   *
   * <p>This method allows you to enable or disable focusing the component. When enabled, the
   * component will be part of the tab order. When disabled the component is excluded from tab
   * navigation.
   *
   * @param focusable true to enable focusing the component, false to disable it.
   * @return the component itself.
   */
  public MultiSelectCombo setFocusable(boolean focusable) {
    final var tabTraversable = focusable ? 0 : -1;
    set(this.tabTraversableProp, tabTraversable);
    return this;
  }

  /**
   * Retrieves the value of the input associated with the control.
   *
   * @return The inputValue value.
   */
  public String getInputValue() {
    return get(this.inputValueProp);
  }

  /**
   * Sets the value of the input associated with the control.
   *
   * @param inputValue The inputValue value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setInputValue(String inputValue) {
    set(this.inputValueProp, inputValue);
    return this;
  }

  /**
   * Retrieves the highlight behaviors of the control.
   *
   * @return The highlightBehaviors value.
   */
  public List<HighlightBehaviours> getHighlightBehaviors() {
    return get(this.highlightBehaviorsProp);
  }

  /**
   * Sets the highlight behaviors of the control.
   *
   * @param highlightBehaviors The highlightBehaviors value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setHighlightBehaviors(List<HighlightBehaviours> highlightBehaviors) {
    set(this.highlightBehaviorsProp, highlightBehaviors);
    return this;
  }

  /**
   * Retrieves the name attribute of the control.
   *
   * @return The name value.
   */
  @Override
  public String getName() {
    return get(this.nameProp);
  }

  /**
   * Sets the name attribute of the control.
   *
   * @param name The name value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setName(String name) {
    set(this.nameProp, name);
    return this;
  }

  /**
   * Retrieves the size of the input associated with the control.
   *
   * @return The inputSize value.
   */
  public Float getInputSize() {
    return get(this.inputSizeProp);
  }

  /**
   * Sets the size of the input associated with the control.
   *
   * @param inputSize The inputSize value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setInputSize(Float inputSize) {
    set(this.inputSizeProp, inputSize);
    return this;
  }

  /**
   * Retrieves the value indicating whether custom values are allowed.
   *
   * @return The allowCustomValue value.
   */
  public Boolean getAllowCustomValue() {
    return get(this.allowCustomValueProp);
  }

  /**
   * Sets the value indicating whether custom values are allowed.
   *
   * @param allowCustomValue The allowCustomValue value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setAllowCustomValue(Boolean allowCustomValue) {
    set(this.allowCustomValueProp, allowCustomValue);
    return this;
  }

  /**
   * Retrieves the maximum number of rows displayed by the control.
   *
   * @return The maxRowCount value.
   */
  public Integer getMaxRowCount() {
    return get(this.maxRowCountProp);
  }

  /**
   * Sets the maximum number of rows displayed by the control.
   *
   * @param maxRowCount The maxRowCount value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setMaxRowCount(Integer maxRowCount) {
    set(this.maxRowCountProp, maxRowCount);
    return this;
  }

  /**
   * Retrieves the value indicating whether the dropdown opens on arrow key press.
   *
   * @return The openOnArrow value.
   */
  public Boolean getOpenOnArrow() {
    return get(this.openOnArrowProp);
  }

  /**
   * Sets the value indicating whether the dropdown opens on arrow key press.
   *
   * @param openOnArrow The openOnArrow value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setOpenOnArrow(Boolean openOnArrow) {
    set(this.openOnArrowProp, openOnArrow);
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setPlaceholder(String placeholder) {
    super.set(this.placeholderProp, placeholder);
    return this;
  }

  /**
   * Retrieves the value indicating whether the control is disabled.
   *
   * @return The disabled value.
   */
  public Boolean getDisabled() {
    return get(this.disabledProp);
  }

  /**
   * Sets the value indicating whether the control is disabled.
   *
   * @param disabled The disabled value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setDisabled(Boolean disabled) {
    set(this.disabledProp, disabled);
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setReadOnly(boolean readonly) {
    super.set(this.readonlyProp, readonly);
    return this;
  }

  /**
   * Retrieves the distance between the control and its suggestion list.
   *
   * @return The distance value.
   */
  public int getDistance() {
    return get(this.distanceProp);
  }

  /**
   * Sets the distance between the control and its suggestion list.
   *
   * @param distance The distance value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setDistance(int distance) {
    set(this.distanceProp, distance);
    return this;
  }

  /**
   * Retrieves the maximum length of chips in the control.
   *
   * @return The maxChipsLength value.
   */
  public int getMaxChipsLength() {
    return get(this.maxChipsLengthProp);
  }

  /**
   * Sets the maximum length of chips in the control.
   *
   * @param maxChipsLength The maxChipsLength value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setMaxChipsLength(int maxChipsLength) {
    set(this.maxChipsLengthProp, maxChipsLength);
    return this;
  }

  /**
   * Retrieves the value indicating whether the dropdown is opened.
   *
   * @return The opened value.
   */
  public Boolean getOpened() {
    return get(this.openedProp);
  }

  /**
   * Sets the value indicating whether the dropdown is opened.
   *
   * @param opened The opened value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setOpened(Boolean opened) {
    set(this.openedProp, opened);
    return this;
  }

  /**
   * Retrieves the width of the opened dropdown.
   *
   * @return The openWidth value.
   */
  public int getOpenWidth() {
    return get(this.openWidthProp);
  }

  /**
   * Sets the width of the opened dropdown.
   *
   * @param openWidth The openWidth value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setOpenWidth(int openWidth) {
    set(this.openWidthProp, openWidth);
    return this;
  }

  /**
   * Retrieves the height of the opened dropdown.
   *
   * @return The openHeight value.
   */
  public int getOpenHeight() {
    return get(this.openHeightProp);
  }

  /**
   * Sets the height of the opened dropdown.
   *
   * @param openHeight The openHeight value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setOpenHeight(int openHeight) {
    set(this.openHeightProp, openHeight);
    return this;
  }

  /**
   * Retrieves the skidding value of the opened dropdown.
   *
   * @return The skidding value.
   */
  public int getSkidding() {
    return get(this.skiddingProp);
  }

  /**
   * Sets the skidding value of the opened dropdown.
   *
   * @param skidding The skidding value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setSkidding(int skidding) {
    set(this.skiddingProp, skidding);
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setAutoClientValidate(boolean autoValidate) {
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setAutoValidateOnLoad(boolean autoValidateOnLoad) {
    set(this.autoValidateOnLoadProp, autoValidateOnLoad);
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
   * Sets the invalid property value.
   *
   * @param valid The value indicating whether the component is valid.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValid(boolean valid) {
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setInvalid(boolean invalid) {
    set(this.invalidProp, invalid);
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setInvalidMessage(String invalidMessage) {
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValidator(String validator) {
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
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  @Override
  public MultiSelectCombo setValidationStyle(ValidationStyle validationStyle) {
    set(this.validationStyleProp, validationStyle);
    return this;
  }

  /**
   * Retrieves the auto-was-validated property value.
   *
   * @return The value indicating whether the component automatically switches to valid state after
   *     validation.
   */
  public boolean isAutoWasValidated() {
    return get(this.autoWasValidatedProp);
  }

  /**
   * Sets the auto-was-validated property value.
   *
   * @param autoWasValidated The value indicating whether the component automatically switches to
   *     valid state after validation.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setAutoWasValidated(boolean autoWasValidated) {
    set(this.autoWasValidatedProp, autoWasValidated);
    return this;
  }

  /**
   * Retrieves the validation icon property value.
   *
   * @return The validation icon value.
   */
  public String getValidationIcon() {
    return get(this.validationIconProp);
  }

  /**
   * Sets the validation icon property value.
   *
   * @param validationIcon The validation icon value.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValidationIcon(String validationIcon) {
    set(this.validationIconProp, validationIcon);
    return this;
  }

  /**
   * Retrieves the validation popover distance property value.
   *
   * @return The distance in pixels from which to offset the validation message away from the
   *     control.
   */
  public int getValidationPopoverDistance() {
    return get(this.validationPopoverDistanceProp);
  }

  /**
   * Sets the validation popover distance property value.
   *
   * @param validationPopoverDistance The distance in pixels from which to offset the validation
   *     message away from the control.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValidationPopoverDistance(int validationPopoverDistance) {
    set(this.validationPopoverDistanceProp, validationPopoverDistance);
    return this;
  }

  /**
   * Retrieves the validation popover placement property value.
   *
   * @return The preferred placement of the validation message.
   */
  public ValidationPopoverPlacement getValidationPopoverPlacement() {
    return get(this.validationPopoverPlacementProp);
  }

  /**
   * Sets the validation popover placement property value.
   *
   * @param validationPopoverPlacement The preferred placement of the validation message.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValidationPopoverPlacement(
      ValidationPopoverPlacement validationPopoverPlacement) {
    set(this.validationPopoverPlacementProp, validationPopoverPlacement);
    return this;
  }

  /**
   * Retrieves the validation popover skidding property value.
   *
   * @return The distance in pixels from which to offset the validation message along the control.
   */
  public int getValidationPopoverSkidding() {
    return get(this.validationPopoverSkiddingProp);
  }

  /**
   * Sets the validation popover skidding property value.
   *
   * @param validationPopoverSkidding The distance in pixels from which to offset the validation
   *     message along the control.
   * @return This {@code MultiSelectCombo} instance for method chaining.
   */
  public MultiSelectCombo setValidationPopoverSkidding(int validationPopoverSkidding) {
    set(this.validationPopoverSkiddingProp, validationPopoverSkidding);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public MultiSelectCombo addClassName(String... classNames) {
    this.getBoundComponent().addClassName(classNames);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public MultiSelectCombo removeClassName(String... classNames) {
    this.getBoundComponent().removeClassName(classNames);
    return this;
  }
}
