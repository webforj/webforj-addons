package com.webforj.addons.components.multiselectcombo;

import com.google.gson.annotations.SerializedName;
import com.webforj.addons.components.multiselectcombo.events.InputEvent;
import com.webforj.addons.components.multiselectcombo.events.OpenedChangedEvent;
import com.webforj.addons.components.multiselectcombo.events.SelectedChangedEvent;
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
 * A multi-select combo component designed for webforj. The
 * {@code MultiSelectCombo} enables users to efficiently select multiple items
 * from a dropdown list, offering a convenient solution for scenarios where
 * users need to make multiple selections from a predefined set of options. By
 * presenting a dropdown list of available choices, users can easily navigate
 * through the options and select multiple items according to their preferences
 * or requirements.
 * <p>
 * The {@code MultiSelectCombo} component is ideal for scenarios where users
 * need to select multiple items from a predefined list. It enhances user
 * interaction by providing a flexible and intuitive interface for selecting and
 * managing multiple options effectively.
 * </p>
 *
 * @author ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-multi-select-combo")
@JavaScript(value = "https://d3hx2iico687v8.cloudfront.net/1.0.0/dwc-addons.esm.js", top = true, attributes = {
		@Attribute(name = "type", value = "module")})
public class MultiSelectCombo extends ElementComposite
		implements
			HasPlaceholder<MultiSelectCombo>,
			HasReadOnly<MultiSelectCombo>,
			HasExpanse<MultiSelectCombo, MultiSelectCombo.Expanse>,
			HasClientAutoValidation<MultiSelectCombo>,
			HasClientAutoValidationOnLoad<MultiSelectCombo>,
			HasClientValidation<MultiSelectCombo>,
			HasClientValidationStyle<MultiSelectCombo> {

	/**
	 * The placement of where the dropdown list should appear
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
	 * The expansiveness of the component.
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
		XLARGE
	}

	/**
	 * The possible behaviours for highlighting input value.
	 */
	public enum HighlightBehaviours {
		KEY, MOUSE, REQUEST
	}

	/**
	 * Property for specifying the placement of the list.
	 */
	private final PropertyDescriptor<Placement> placementProp = PropertyDescriptor
			.property("placement", Placement.BOTTOM);

	/**
	 * Property for the expansiveness of the component.
	 */
	private final PropertyDescriptor<Expanse> expanseProp = PropertyDescriptor.property("expanse",
			Expanse.MEDIUM);

	/**
	 * Property for specifying the renderer for each item in the list.
	 */
	private final PropertyDescriptor<String> rendererProp = PropertyDescriptor.property("renderer",
			null);

	/**
	 * Property for the list of items displayed by the component.
	 */
	private final PropertyDescriptor<List<Item>> itemsProp = PropertyDescriptor.property("items",
			null);

	/**
	 * Property for the list of selected items in the component.
	 */
	private final PropertyDescriptor<List<String>> selectedProp = PropertyDescriptor
			.property("selected", null);

	/**
	 * Property for automatically focusing on the component upon loading the page.
	 */
	private final PropertyDescriptor<Boolean> autoFocusProp = PropertyDescriptor
			.attribute("autofocus", false);

	/**
	 * Property for indicating whether the component has focus.
	 */
	private final PropertyDescriptor<Boolean> hasFocusProp = PropertyDescriptor.property("hasFocus",
			false);

	/**
	 * Property for indicating whether the component is required.
	 */
	private final PropertyDescriptor<Boolean> requiredProp = PropertyDescriptor
			.attribute("required", false);

	/**
	 * Property for specifying the tab traversable behavior of the component.
	 */
	private final PropertyDescriptor<Integer> tabTraversableProp = PropertyDescriptor
			.property("tabTraversable", 0);

	/**
	 * Property for the value of the input associated with the component.
	 */
	private final PropertyDescriptor<String> inputValueProp = PropertyDescriptor
			.property("inputValue", "");

	/**
	 * Property for defining highlight behaviors for the component.
	 */
	private final PropertyDescriptor<List<HighlightBehaviours>> highlightBehaviorsProp = PropertyDescriptor
			.property("highlightBehaviors", new ArrayList<>());

	/**
	 * Property for specifying the name attribute of the component.
	 */
	private final PropertyDescriptor<String> nameProp = PropertyDescriptor.attribute("name", "");

	/**
	 * Property for specifying the label attribute of the component.
	 */
	private final PropertyDescriptor<String> labelProp = PropertyDescriptor.property("label", "");

	/**
	 * Property for specifying the placeholder text of the component.
	 */
	private final PropertyDescriptor<String> placeholderProp = PropertyDescriptor
			.attribute("placeholder", "");

	/**
	 * Property for defining the size of the input associated with the component.
	 */
	private final PropertyDescriptor<Float> inputSizeProp = PropertyDescriptor.property("inputSize",
			null);

	/**
	 * Property for allowing custom values in the component.
	 */
	private final PropertyDescriptor<Boolean> allowCustomValueProp = PropertyDescriptor
			.property("allowCustomValue", false);

	/**
	 * Property for specifying the maximum number of rows displayed by the
	 * component.
	 */
	private final PropertyDescriptor<Integer> maxRowCountProp = PropertyDescriptor
			.property("maxRowCount", null);

	/**
	 * Property for controlling whether the dropdown opens on arrow key press.
	 */
	private final PropertyDescriptor<Boolean> openOnArrowProp = PropertyDescriptor
			.property("openOnArrow", true);

	/**
	 * Property for disabling the component.
	 */
	private final PropertyDescriptor<Boolean> disabledProp = PropertyDescriptor
			.attribute("disabled", false);

	/**
	 * Property for setting the component to read-only mode.
	 */
	private final PropertyDescriptor<Boolean> readonlyProp = PropertyDescriptor
			.attribute("readonly", false);

	/**
	 * Property for specifying the distance between the component and its dropdown
	 * list.
	 */
	private final PropertyDescriptor<Integer> distanceProp = PropertyDescriptor.property("distance",
			null);

	/**
	 * Property for specifying the maximum length of chips in the component.
	 */
	private final PropertyDescriptor<Integer> maxChipsLengthProp = PropertyDescriptor
			.property("maxChipsLength", 65);

	/**
	 * Property for indicating whether the dropdown is opened.
	 */
	private final PropertyDescriptor<Boolean> openedProp = PropertyDescriptor.property("opened",
			false);

	/**
	 * Property for specifying the width of the opened dropdown.
	 */
	private final PropertyDescriptor<Integer> openWidthProp = PropertyDescriptor
			.property("openWidth", null);

	/**
	 * Property for specifying the height of the opened dropdown.
	 */
	private final PropertyDescriptor<Integer> openHeightProp = PropertyDescriptor
			.property("openHeight", null);

	/**
	 * Property for specifying the skidding of the opened dropdown.
	 */
	private final PropertyDescriptor<Integer> skiddingProp = PropertyDescriptor.property("skidding",
			null);

	/**
	 * Property for enforcing automatic validation to the input.
	 */
	private final PropertyDescriptor<Boolean> autoValidateProp = PropertyDescriptor
			.property("autoValidate", true);

	/**
	 * Property for triggering a validation on the input when the component loaded.
	 */
	private final PropertyDescriptor<Boolean> autoValidateOnLoadProp = PropertyDescriptor
			.property("autoValidateOnLoad", false);

	/**
	 * Attribute determining whether the component is valid or invalid.
	 */
	private final PropertyDescriptor<Boolean> invalidProp = PropertyDescriptor.attribute("invalid",
			false);

	/**
	 * Property for error message to display to the user when the component is
	 * invalid.
	 */
	private final PropertyDescriptor<String> invalidMessageProp = PropertyDescriptor
			.property("invalidMessage", null);

	/**
	 * Property for A JavaScript expression or function to validate the component on
	 * the client.
	 * <p>
	 * This expression can be a direct return statement or a function. If the
	 * expression includes the return keyword, it is used as is; otherwise, it is
	 * wrapped with {@code return} and {@code ;} to create a function.
	 * <p>
	 * The expression has access to the following parameters:
	 * <ul>
	 * <li><strong>value</strong>: The control's value.</li>
	 * <li><strong>x</strong>: Alias for value.</li>
	 * <li><strong>text</strong>: Same as value, included for consistency with the
	 * legacy control and form validation APIs.</li>
	 * <li><strong>component</strong>: The instance of the client component.</li>
	 * <li><strong>control</strong>: Alias for component.</li>
	 * </ul>
	 */
	private final PropertyDescriptor<String> validatorProp = PropertyDescriptor
			.property("validator", null);

	/**
	 * Property for specifying the appearance of the validation message. When
	 * {@code popover}, the invalid message will be displayed as a popover;
	 * otherwise it will be displayed as an inline message.
	 */
	private final PropertyDescriptor<ValidationStyle> validationStyleProp = PropertyDescriptor
			.property("validationStyle", null);

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
	public Expanse getExpanse() {
		return get(this.expanseProp);
	}

	/**
	 * Sets the expansiveness of the control.
	 *
	 * @param expanse The expanse value.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setExpanse(Expanse expanse) {
		set(this.expanseProp, expanse);
		return this;
	}

	/**
	 * Retrieves the list of items of the control.
	 *
	 * @return The items value.
	 */
	public List<Item> getItems() {
		return get(this.itemsProp);
	}

	/**
	 * Sets the list of items of the control.
	 *
	 * @param items The items value.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setItems(List<Item> items) {
		set(this.itemsProp, items);
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

	/**
	 * Retrieves the value indicating whether the control has focus.
	 *
	 * @return The hasFocus value.
	 */
	public Boolean getHasFocus() {
		return get(this.hasFocusProp);
	}

	/**
	 * Sets the value indicating whether the control has focus.
	 *
	 * @param hasFocus The hasFocus value.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setHasFocus(Boolean hasFocus) {
		set(this.hasFocusProp, hasFocus);
		return this;
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
	 * Retrieves the tab traversable value of the control.
	 *
	 * @return The tabTraversable value.
	 */
	public Integer getTabTraversable() {
		return get(this.tabTraversableProp);
	}

	/**
	 * Sets the tab traversable value of the control.
	 *
	 * @param tabTraversable The tabTraversable value.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setTabTraversable(Integer tabTraversable) {
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
	public String getName() {
		return get(this.nameProp);
	}

	/**
	 * Sets the name attribute of the control.
	 *
	 * @param name The name value.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
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
	 * Retrieves the current value of the {@code placeholder} property, which
	 * represents the placeholder text displayed in the input field when it is
	 * empty.
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
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
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
	 * Retrieves the current value of the {@code readonly} property, indicating
	 * whether the input field is in read-only mode.
	 *
	 * @return {@code true} if the input field is in read-only mode, {@code false}
	 *         otherwise.
	 */
	public boolean isReadOnly() {
		return super.get(this.readonlyProp);
	}

	/**
	 * Sets whether the input field is in read-only mode.
	 *
	 * @param readonly {@code true} if the input field is in read-only mode,
	 *            {@code false} otherwise.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
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
	public boolean isAutoClientValidate() {
		return get(this.autoValidateProp);
	}

	/**
	 * Sets the auto-validate value of the input element.
	 *
	 * @param autoValidate The auto-validate property.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
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
	 * @param autoValidateOnLoad The value indicating whether to trigger validation
	 *            on load.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setAutoValidateOnLoad(boolean autoValidateOnLoad) {
		set(this.autoValidateOnLoadProp, autoValidateOnLoad);
		return this;
	}

	/**
	 * Retrieves the invalid property value.
	 *
	 * @return The value indicating whether the component is valid or invalid.
	 */
	public boolean isInvalid() {
		return get(this.invalidProp, true);
	}

	/**
	 * Sets the invalid property value.
	 *
	 * @param invalid The value indicating whether the component is valid or
	 *            invalid.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setInvalid(boolean invalid) {
		set(this.invalidProp, invalid);
		return this;
	}

	/**
	 * Retrieves the invalid message property value.
	 *
	 * @return The error message to display when the component is invalid.
	 */
	public String getInvalidMessage() {
		return get(this.invalidMessageProp);
	}

	/**
	 * Sets the invalid message property value.
	 *
	 * @param invalidMessage The error message to display when the component is
	 *            invalid.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setInvalidMessage(String invalidMessage) {
		set(this.invalidMessageProp, invalidMessage);
		return this;
	}

	/**
	 * Retrieves the validator property value.
	 *
	 * @return The JavaScript expression or function to validate the component on
	 *         the client.
	 */
	public String getValidator() {
		return get(this.validatorProp);
	}

	/**
	 * Sets the validator property value.
	 *
	 * @param validator The JavaScript expression or function to validate the
	 *            component on the client.
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
	public ValidationStyle getValidationStyle() {
		return get(this.validationStyleProp);
	}

	/**
	 * Sets the validation style property value.
	 *
	 * @param validationStyle The validation style indicating how the invalid
	 *            message is displayed.
	 * @return This {@code MultiSelectCombo} instance for method chaining.
	 */
	public MultiSelectCombo setValidationStyle(ValidationStyle validationStyle) {
		set(this.validationStyleProp, validationStyle);
		return this;
	}
}
