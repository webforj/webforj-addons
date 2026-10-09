package com.webforj.addons.components.iconpicker;

import com.google.gson.annotations.SerializedName;
import com.webforj.PendingResult;
import com.webforj.addons.components.iconpicker.events.IconChangedEvent;
import com.webforj.addons.components.iconpicker.events.OpenedChangedEvent;
import com.webforj.addons.constant.GlobalConstants;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.ExpanseBase;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.ElementAnnotationProcessor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.event.BlurEvent;
import com.webforj.component.event.FocusEvent;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconDefinition;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasEnablement;
import com.webforj.concern.HasExpanse;
import com.webforj.concern.HasFocus;
import com.webforj.concern.HasHelperText;
import com.webforj.concern.HasI18n;
import com.webforj.concern.HasLabel;
import com.webforj.concern.HasPlaceholder;
import com.webforj.concern.HasReadOnly;
import com.webforj.concern.HasRequired;
import com.webforj.concern.HasStyle;
import com.webforj.concern.HasValue;
import com.webforj.concern.HasVisibility;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.data.validation.InvalidAware;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A field for choosing an icon from the icon pools DWC draws, with pool tabs, search and a grid of
 * previews.
 *
 * @author ElyasSalar
 * @since 26.03
 */
@NodeName("dwc-icon-picker")
@JavaScript(
    value = GlobalConstants.DWC_CDN,
    top = true,
    attributes = {@Attribute(name = "type", value = "module")})
public class IconPicker extends ElementComposite
    implements HasValue<IconPicker, IconDefinition<?>>,
        HasLabel<IconPicker>,
        HasPlaceholder<IconPicker>,
        HasEnablement<IconPicker>,
        HasReadOnly<IconPicker>,
        HasRequired<IconPicker>,
        HasExpanse<IconPicker, IconPicker.Expanse>,
        HasHelperText<IconPicker>,
        InvalidAware<IconPicker>,
        HasFocus<IconPicker>,
        HasClassName<IconPicker>,
        HasStyle<IconPicker>,
        HasVisibility<IconPicker>,
        HasI18n<IconPicker, IconPickerI18n> {
  /** The sizes the picker's field supports. */
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

  /** The icon pools built into the picker, in their default tab order. */
  public enum Pool {
    TABLER("tabler"),
    FEATHER("feather"),
    FONT_AWESOME("fa"),
    DWC("dwc");

    private final String dwcName;

    Pool(String dwcName) {
      this.dwcName = dwcName;
    }

    String dwcName() {
      return dwcName;
    }
  }

  static final String POOL_LIST_SEPARATOR = ",";
  static final String NAME_SEPARATOR = ":";
  static final Set<String> BUILT_IN_POOLS =
      Arrays.stream(Pool.values()).map(Pool::dwcName).collect(Collectors.toUnmodifiableSet());
  private static final String DEFAULT_POOL = Pool.TABLER.dwcName();
  private static final String DEFAULT_PLACEHOLDER = "Choose an icon";

  private final PropertyDescriptor<String> valueProp = PropertyDescriptor.property("value", "");
  private final PropertyDescriptor<String> labelProp = PropertyDescriptor.property("label", "");
  private final PropertyDescriptor<String> placeholderProp =
      PropertyDescriptor.attribute("placeholder", DEFAULT_PLACEHOLDER);
  private final PropertyDescriptor<String> helperTextProp =
      PropertyDescriptor.property("helperText", "");
  private final PropertyDescriptor<Boolean> invalidProp =
      PropertyDescriptor.property("invalid", false);
  private final PropertyDescriptor<String> invalidMessageProp =
      PropertyDescriptor.property("invalidMessage", "");
  private final PropertyDescriptor<Boolean> disabledProp =
      PropertyDescriptor.attribute("disabled", false);
  private final PropertyDescriptor<Boolean> readOnlyProp =
      PropertyDescriptor.attribute("readonly", false);
  private final PropertyDescriptor<Boolean> requiredProp =
      PropertyDescriptor.attribute("required", false);
  private final PropertyDescriptor<Expanse> expanseProp =
      PropertyDescriptor.property("expanse", Expanse.MEDIUM);
  private final PropertyDescriptor<String> poolsProp = PropertyDescriptor.attribute("pools", "");
  private final PropertyDescriptor<List<CustomPool>> customPoolsProp =
      PropertyDescriptor.property("customPools", List.of());
  private final PropertyDescriptor<Boolean> openedProp =
      PropertyDescriptor.property("opened", false);
  private final PropertyDescriptor<IconPickerI18n> i18nProp =
      PropertyDescriptor.property("i18n", new IconPickerI18n());

  private List<Pool> pools = List.of(Pool.values());
  private boolean hasValueChangeBridge;

  /** Creates a picker with no icon chosen. */
  public IconPicker() {
    synchronize(valueProp, ElementAnnotationProcessor.processEventName(IconChangedEvent.class));
    synchronize(openedProp, ElementAnnotationProcessor.processEventName(OpenedChangedEvent.class));
  }

  /**
   * Adds a listener for value changes made in the browser, fired when the user picks or removes an
   * icon.
   *
   * @param listener the listener called with the picked icon, or with null after a removal
   * @return a registration for removing the listener
   */
  @Override
  public ListenerRegistration<ValueChangeEvent<IconDefinition<?>>> addValueChangeListener(
      EventListener<ValueChangeEvent<IconDefinition<?>>> listener) {
    final ListenerRegistration<ValueChangeEvent<IconDefinition<?>>> registration =
        getEventDispatcher().addListener(ValueChangeEvent.class, listener);

    if (!hasValueChangeBridge) {
      addIconChangedListener(this::handleIconChanged);
      hasValueChangeBridge = true;
    }

    return registration;
  }

  private void handleIconChanged(IconChangedEvent event) {
    getEventDispatcher()
        .dispatchEvent(new ValueChangeEvent<IconDefinition<?>>(this, event.getValue()));
  }

  /**
   * Adds a listener for icons picked or removed in the browser.
   *
   * @param listener the listener called with the picked icon, or with an empty value after a
   *     removal
   * @return a registration for removing the listener
   */
  public ListenerRegistration<IconChangedEvent> addIconChangedListener(
      EventListener<IconChangedEvent> listener) {
    return addEventListener(IconChangedEvent.class, listener);
  }

  /**
   * Alias for {@link #addIconChangedListener(EventListener)}.
   *
   * @param listener the listener called with the picked icon, or with an empty value after a
   *     removal
   * @return a registration for removing the listener
   */
  public ListenerRegistration<IconChangedEvent> onIconChanged(
      EventListener<IconChangedEvent> listener) {
    return addIconChangedListener(listener);
  }

  /**
   * Adds a listener for the dropdown opening or closing.
   *
   * @param listener the listener called with the dropdown's new state
   * @return a registration for removing the listener
   */
  public ListenerRegistration<OpenedChangedEvent> addOpenedChangedListener(
      EventListener<OpenedChangedEvent> listener) {
    return addEventListener(OpenedChangedEvent.class, listener);
  }

  /**
   * Alias for {@link #addOpenedChangedListener(EventListener)}.
   *
   * @param listener the listener called with the dropdown's new state
   * @return a registration for removing the listener
   */
  public ListenerRegistration<OpenedChangedEvent> onOpenedChanged(
      EventListener<OpenedChangedEvent> listener) {
    return addOpenedChangedListener(listener);
  }

  /**
   * Opens the dropdown.
   *
   * @return a pending result that completes once the browser ran the call
   */
  public PendingResult<Object> open() {
    return getElement().callJsFunctionAsync("open");
  }

  /**
   * Closes the dropdown.
   *
   * @return a pending result that completes once the browser ran the call
   */
  public PendingResult<Object> close() {
    return getElement().callJsFunctionAsync("close");
  }

  /**
   * Gets the chosen icon. Each call creates a new {@link Icon}, so changing the returned icon
   * doesn't change the picker.
   *
   * @return the chosen icon, or null when no icon is chosen
   */
  @Override
  public Icon getValue() {
    return iconOf(Objects.requireNonNullElse(get(valueProp), ""));
  }

  /**
   * Reads a value the element holds, {@code pool:name}, as an icon. A missing or empty pool means
   * {@code tabler}, as {@code dwc-icon} reads it, and a value without a name means no icon.
   */
  static Icon iconOf(String value) {
    final String trimmed = value.trim();
    final int separatorIndex = trimmed.indexOf(NAME_SEPARATOR);
    String pool = DEFAULT_POOL;
    String name = trimmed;
    if (separatorIndex >= 0) {
      pool = poolOrDefault(trimmed.substring(0, separatorIndex));
      name = trimmed.substring(separatorIndex + NAME_SEPARATOR.length());
    }
    if (name.isEmpty()) {
      return null;
    }

    return new Icon(name, pool);
  }

  /**
   * Chooses an icon without firing an {@link IconChangedEvent} or a {@link ValueChangeEvent}. An
   * icon without a pool is read as a {@code tabler} icon, as {@code dwc-icon} reads it.
   *
   * @param value the icon to choose, or null to clear the icon
   * @return this picker
   * @throws IllegalArgumentException if the icon has a null or blank name
   */
  @Override
  public IconPicker setValue(IconDefinition<?> value) {
    if (value == null) {
      set(valueProp, "");
      return this;
    }

    final String name = value.getName();
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("The icon has a null or blank name: '" + name + "'");
    }

    set(valueProp, poolOrDefault(value.getPool()) + NAME_SEPARATOR + name);
    return this;
  }

  private static String poolOrDefault(String pool) {
    if (pool == null || pool.isBlank()) {
      return DEFAULT_POOL;
    }

    return pool;
  }

  @Override
  public String getLabel() {
    return get(labelProp);
  }

  /**
   * Sets the field label.
   *
   * @param label the label; null is treated as an empty string
   * @return this picker
   */
  @Override
  public IconPicker setLabel(String label) {
    set(labelProp, Objects.requireNonNullElse(label, ""));
    return this;
  }

  /**
   * Gets the text shown while no icon is chosen.
   *
   * @return the placeholder, {@code Choose an icon} unless set
   */
  @Override
  public String getPlaceholder() {
    return get(placeholderProp);
  }

  /**
   * Sets the text shown while no icon is chosen.
   *
   * @param placeholder the placeholder; null is treated as an empty string
   * @return this picker
   */
  @Override
  public IconPicker setPlaceholder(String placeholder) {
    set(placeholderProp, Objects.requireNonNullElse(placeholder, ""));
    return this;
  }

  @Override
  public String getHelperText() {
    return get(helperTextProp);
  }

  /**
   * Sets the text shown under the field to help the user choose.
   *
   * @param helperText the text; null is treated as an empty string, which shows none
   * @return this picker
   */
  @Override
  public IconPicker setHelperText(String helperText) {
    set(helperTextProp, Objects.requireNonNullElse(helperText, ""));
    return this;
  }

  @Override
  public boolean isInvalid() {
    return get(invalidProp);
  }

  /**
   * Marks the field as invalid or valid. An invalid field shows its invalid message, if it has one.
   *
   * @param invalid whether the field is invalid
   * @return this picker
   */
  @Override
  public IconPicker setInvalid(boolean invalid) {
    set(invalidProp, invalid);
    return this;
  }

  @Override
  public String getInvalidMessage() {
    return get(invalidMessageProp);
  }

  /**
   * Sets the message shown under the field while it is invalid.
   *
   * @param message the message; null is treated as an empty string, which shows none
   * @return this picker
   */
  @Override
  public IconPicker setInvalidMessage(String message) {
    set(invalidMessageProp, Objects.requireNonNullElse(message, ""));
    return this;
  }

  @Override
  public boolean isEnabled() {
    return !Boolean.TRUE.equals(get(disabledProp));
  }

  /**
   * Enables or disables the picker. A disabled picker doesn't open its dropdown.
   *
   * @param enabled true to enable the picker
   * @return this picker
   */
  @Override
  public IconPicker setEnabled(boolean enabled) {
    set(disabledProp, !enabled);
    return this;
  }

  @Override
  public boolean isReadOnly() {
    return Boolean.TRUE.equals(get(readOnlyProp));
  }

  /**
   * Sets whether the picker is read only. A read-only picker shows its icon but doesn't open its
   * dropdown.
   *
   * @param readOnly true to make the picker read only
   * @return this picker
   */
  @Override
  public IconPicker setReadOnly(boolean readOnly) {
    set(readOnlyProp, readOnly);
    return this;
  }

  @Override
  public boolean isRequired() {
    return Boolean.TRUE.equals(get(requiredProp));
  }

  /**
   * Sets whether the picker requires an icon. A required picker hides the action that removes the
   * current icon.
   *
   * @param required true to require an icon
   * @return this picker
   */
  @Override
  public IconPicker setRequired(boolean required) {
    set(requiredProp, required);
    return this;
  }

  @Override
  public Expanse getExpanse() {
    return get(expanseProp);
  }

  /**
   * Sets the size of the field.
   *
   * @param expanse the size, {@link Expanse#MEDIUM} unless set
   * @return this picker
   * @throws NullPointerException if the size is null
   */
  @Override
  public IconPicker setExpanse(Expanse expanse) {
    set(expanseProp, Objects.requireNonNull(expanse, "The expanse cannot be null"));
    return this;
  }

  /**
   * Gets the built-in pools shown as tabs.
   *
   * @return an unmodifiable list of the pools in tab order; every built-in pool unless {@link
   *     #setPools(Pool...)} chose others
   */
  public List<Pool> getPools() {
    return pools;
  }

  /**
   * Sets the built-in pools shown as tabs, in tab order. The custom pools follow them, in the order
   * they were added. With no pools given, only the custom pools show; a picker with neither shows
   * every built-in pool.
   *
   * @param pools the built-in pools in tab order; a pool given twice shows once
   * @return this picker
   * @throws NullPointerException if the array or a pool is null
   */
  public IconPicker setPools(Pool... pools) {
    Objects.requireNonNull(pools, "The pools cannot be null");

    this.pools =
        Arrays.stream(pools)
            .map(pool -> Objects.requireNonNull(pool, "A pool cannot be null"))
            .distinct()
            .toList();
    syncPools();
    return this;
  }

  private void syncPools() {
    set(poolsProp, tabsOf(pools, getCustomPools()));
  }

  /** The element's list of tabs: the built-in pools, then the custom pools. */
  static String tabsOf(List<Pool> builtInPools, List<String> customPools) {
    final Stream<String> builtIn = builtInPools.stream().map(Pool::dwcName);
    return Stream.concat(builtIn, customPools.stream())
        .collect(Collectors.joining(POOL_LIST_SEPARATOR));
  }

  /**
   * Gets the names of the custom pools added to the picker.
   *
   * @return an unmodifiable list of the names in the order the pools were added
   */
  public List<String> getCustomPools() {
    return get(customPoolsProp).stream().map(CustomPool::getName).toList();
  }

  /**
   * Adds a custom pool whose icons are found by name only. The app must register a DWC pool of the
   * same name itself; until it does, the pool's tab says the pool isn't registered.
   *
   * @param name the name the app registered the pool under in DWC
   * @param label the text on the pool's tab
   * @param iconNames the icon names, in tab order
   * @return this picker
   * @throws NullPointerException if any argument or icon name is null
   * @throws IllegalArgumentException if the name is blank, starts or ends with white space,
   *     contains a comma or a colon, names a built-in pool or was already added, or if the label or
   *     an icon name is blank
   */
  public IconPicker addCustomPool(String name, String label, Collection<String> iconNames) {
    return appendCustomPool(CustomPool.of(name, label, iconNames));
  }

  /**
   * Adds a custom pool whose icons are found by name and by tags. The app must register a DWC pool
   * of the same name itself; until it does, the pool's tab says the pool isn't registered.
   *
   * @param name the name the app registered the pool under in DWC
   * @param label the text on the pool's tab
   * @param iconNamesWithTags the icon names mapped to the tags search matches them by, in the map's
   *     iteration order
   * @return this picker
   * @throws NullPointerException if any argument, icon name, tag collection or tag is null
   * @throws IllegalArgumentException if the name is blank, starts or ends with white space,
   *     contains a comma or a colon, names a built-in pool or was already added, or if the label or
   *     an icon name is blank
   */
  public IconPicker addCustomPool(
      String name, String label, Map<String, ? extends Collection<String>> iconNamesWithTags) {
    return appendCustomPool(CustomPool.withTags(name, label, iconNamesWithTags));
  }

  private IconPicker appendCustomPool(CustomPool pool) {
    final List<CustomPool> addedPools = get(customPoolsProp);
    final boolean isDuplicate =
        addedPools.stream().anyMatch(existing -> existing.getName().equals(pool.getName()));
    if (isDuplicate) {
      throw new IllegalArgumentException("A pool named '" + pool.getName() + "' was already added");
    }

    set(customPoolsProp, Stream.concat(addedPools.stream(), Stream.of(pool)).toList());
    syncPools();
    return this;
  }

  /**
   * Removes a custom pool and its tab. A name no custom pool has is ignored.
   *
   * @param name the name the pool was added under
   * @return this picker
   * @throws NullPointerException if the name is null
   */
  public IconPicker removeCustomPool(String name) {
    Objects.requireNonNull(name, "The pool name cannot be null");

    set(
        customPoolsProp,
        get(customPoolsProp).stream().filter(pool -> !pool.getName().equals(name)).toList());
    syncPools();
    return this;
  }

  /**
   * Checks whether the dropdown is open, as last reported by the browser.
   *
   * @return true if the dropdown is open
   */
  public boolean isOpened() {
    return Boolean.TRUE.equals(get(openedProp));
  }

  /**
   * Gets the texts the picker shows.
   *
   * @return the texts last set, or the English defaults
   */
  @Override
  public IconPickerI18n getI18n() {
    return get(i18nProp);
  }

  /**
   * Sets the texts the picker shows. The object is sent as it is now; changes made to it later
   * reach the picker only when it is set again.
   *
   * @param i18n the texts to show
   * @return this picker
   * @throws NullPointerException if the texts are null
   */
  @Override
  public IconPicker setI18n(IconPickerI18n i18n) {
    set(i18nProp, Objects.requireNonNull(i18n, "The i18n texts cannot be null"));
    return this;
  }

  /**
   * Moves the keyboard focus to the picker's field once it is in the page.
   *
   * @return this picker
   */
  @Override
  public IconPicker focus() {
    getElement().callJsFunctionVoidAsync("setFocus");
    return this;
  }

  @Override
  public boolean isFocusable() {
    return getElement().isFocusable();
  }

  @Override
  public IconPicker setFocusable(boolean focusable) {
    getElement().setFocusable(focusable);
    return this;
  }

  @Override
  public ListenerRegistration<FocusEvent> addFocusListener(EventListener<FocusEvent> listener) {
    return getElement().addFocusListener(listener);
  }

  @Override
  public ListenerRegistration<BlurEvent> addBlurListener(EventListener<BlurEvent> listener) {
    return getElement().addBlurListener(listener);
  }
}
