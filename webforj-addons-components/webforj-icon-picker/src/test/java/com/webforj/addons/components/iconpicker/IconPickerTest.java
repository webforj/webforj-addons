package com.webforj.addons.components.iconpicker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webforj.addons.components.iconpicker.IconPicker.Pool;
import com.webforj.addons.components.iconpicker.events.IconChangedEvent;
import com.webforj.addons.components.iconpicker.events.OpenedChangedEvent;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconDefinition;
import com.webforj.component.icons.TablerIcon;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("IconPicker")
class IconPickerTest {

  private IconPicker picker;

  @BeforeEach
  void setUp() {
    picker = new IconPicker();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("fluentCalls")
  @DisplayName("returns the picker from a fluent setter")
  void returnsPickerFromFluentSetter(UnaryOperator<IconPicker> call) {
    final IconPicker returned = call.apply(picker);

    assertSame(picker, returned);
  }

  static Stream<Named<UnaryOperator<IconPicker>>> fluentCalls() {
    return Stream.of(
        Named.of("setValue", target -> target.setValue(new Icon("filled-star", "tabler"))),
        Named.of("setPools", target -> target.setPools(Pool.FONT_AWESOME, Pool.TABLER)),
        Named.of(
            "addCustomPool", target -> target.addCustomPool("brand", "Brand", List.of("logo"))),
        Named.of("removeCustomPool", target -> target.removeCustomPool("brand")),
        Named.of("setI18n", target -> target.setI18n(new IconPickerI18n())));
  }

  @Nested
  @DisplayName("value")
  class ValueTests {

    @Test
    @DisplayName("is no icon by default")
    void noneByDefault() {
      assertNull(picker.getValue());
    }

    @Test
    @DisplayName("round trips an icon's pool and name")
    void roundTripsIcon() {
      picker.setValue(new Icon("fab-github", "fa"));

      final Icon icon = picker.getValue();

      assertEquals("fa", icon.getPool());
      assertEquals("fab-github", icon.getName());
    }

    @Test
    @DisplayName("keeps a webforJ variant as part of the name")
    void keepsVariantName() {
      picker.setValue(TablerIcon.create("star", TablerIcon.Variate.FILLED));

      assertEquals("filled-star", picker.getValue().getName());
      assertEquals("tabler", picker.getValue().getPool());
    }

    @ParameterizedTest(name = "pool \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    @DisplayName("reads an icon without a pool as a tabler icon")
    void blankPoolIsTabler(String pool) {
      final IconDefinition<?> icon = mock(IconDefinition.class);
      when(icon.getName()).thenReturn("star");
      when(icon.getPool()).thenReturn(pool);

      picker.setValue(icon);

      assertEquals("tabler", picker.getValue().getPool());
    }

    @Test
    @DisplayName("is cleared by a null icon")
    void nullClears() {
      picker.setValue(new Icon("star", "tabler"));

      picker.setValue(null);

      assertNull(picker.getValue());
    }

    @Test
    @DisplayName("rejects an icon without a name")
    void rejectsIconWithoutName() {
      final IconDefinition<?> icon = mock(IconDefinition.class);
      when(icon.getName()).thenReturn(" ");

      assertThrows(IllegalArgumentException.class, () -> picker.setValue(icon));
      assertNull(picker.getValue());
    }

    @Test
    @DisplayName("gives a new icon on every call, so changing it leaves the picker alone")
    void newIconEachCall() {
      picker.setValue(new Icon("star", "tabler"));
      final Icon icon = picker.getValue();

      icon.setName("heart");

      assertNotSame(icon, picker.getValue());
      assertEquals("star", picker.getValue().getName());
    }
  }

  @Nested
  @DisplayName("reading the element's value")
  class ElementValueTests {

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"tabler:", "   ", ":", ""})
    @DisplayName("reads a value that names no icon as no icon")
    void noIconWithoutName(String value) {
      assertNull(IconPicker.iconOf(value));
    }

    @ParameterizedTest(name = "{0} -> pool {1}, name {2}")
    @CsvSource({
      "tabler:filled-star, tabler, filled-star",
      "fa:fab-github, fa, fab-github",
      "brand:logo:dark, brand, logo:dark",
      "device-desktop, tabler, device-desktop",
      ":star, tabler, star",
      "'  feather:activity  ', feather, activity"
    })
    @DisplayName("splits the value at the first colon, defaulting to the tabler pool")
    void splitsValue(String value, String pool, String name) {
      final Icon icon = IconPicker.iconOf(value);

      assertEquals(pool, icon.getPool());
      assertEquals(name, icon.getName());
    }
  }

  @Nested
  @DisplayName("properties")
  class PropertyTests {

    @Test
    @DisplayName("label is empty by default")
    void labelEmptyByDefault() {
      assertEquals("", picker.getLabel());
    }

    @Test
    @DisplayName("label round trips")
    void labelRoundTrips() {
      picker.setLabel("Icon");

      assertEquals("Icon", picker.getLabel());
    }

    @Test
    @DisplayName("label treats null as empty")
    void labelTreatsNullAsEmpty() {
      picker.setLabel("Icon");

      picker.setLabel(null);

      assertEquals("", picker.getLabel());
    }

    @Test
    @DisplayName("helper text is empty by default and treats null as empty")
    void helperText() {
      assertEquals("", picker.getHelperText());

      picker.setHelperText("Shown in the toolbar");
      final String set = picker.getHelperText();
      picker.setHelperText(null);

      assertEquals("Shown in the toolbar", set);
      assertEquals("", picker.getHelperText());
    }

    @Test
    @DisplayName("is valid by default and can be marked invalid")
    void invalid() {
      assertFalse(picker.isInvalid());

      picker.setInvalid(true);

      assertTrue(picker.isInvalid());
    }

    @Test
    @DisplayName("invalid message round trips and treats null as empty")
    void invalidMessage() {
      picker.setInvalidMessage("Not allowed");
      final String set = picker.getInvalidMessage();
      picker.setInvalidMessage(null);

      assertEquals("Not allowed", set);
      assertEquals("", picker.getInvalidMessage());
    }

    @Test
    @DisplayName("placeholder defaults to the element's text")
    void placeholderDefault() {
      assertEquals("Choose an icon", picker.getPlaceholder());
    }

    @Test
    @DisplayName("placeholder round trips")
    void placeholderRoundTrips() {
      picker.setPlaceholder("Pick one");

      assertEquals("Pick one", picker.getPlaceholder());
    }

    @Test
    @DisplayName("placeholder treats null as empty")
    void placeholderTreatsNullAsEmpty() {
      picker.setPlaceholder("Pick one");

      picker.setPlaceholder(null);

      assertEquals("", picker.getPlaceholder());
    }

    @Test
    @DisplayName("is enabled by default")
    void enabledByDefault() {
      assertTrue(picker.isEnabled());
    }

    @ParameterizedTest(name = "enabled {0}")
    @ValueSource(booleans = {true, false})
    @DisplayName("enablement round trips")
    void enablementRoundTrips(boolean isEnabled) {
      picker.setEnabled(!isEnabled);

      picker.setEnabled(isEnabled);

      assertEquals(isEnabled, picker.isEnabled());
    }

    @Test
    @DisplayName("is editable by default")
    void editableByDefault() {
      assertFalse(picker.isReadOnly());
    }

    @ParameterizedTest(name = "read only {0}")
    @ValueSource(booleans = {true, false})
    @DisplayName("read only round trips")
    void readOnlyRoundTrips(boolean isReadOnly) {
      picker.setReadOnly(!isReadOnly);

      picker.setReadOnly(isReadOnly);

      assertEquals(isReadOnly, picker.isReadOnly());
    }

    @Test
    @DisplayName("is optional by default")
    void optionalByDefault() {
      assertFalse(picker.isRequired());
    }

    @ParameterizedTest(name = "required {0}")
    @ValueSource(booleans = {true, false})
    @DisplayName("required round trips")
    void requiredRoundTrips(boolean isRequired) {
      picker.setRequired(!isRequired);

      picker.setRequired(isRequired);

      assertEquals(isRequired, picker.isRequired());
    }

    @Test
    @DisplayName("expanse defaults to medium")
    void expanseDefault() {
      assertEquals(IconPicker.Expanse.MEDIUM, picker.getExpanse());
    }

    @Test
    @DisplayName("expanse round trips")
    void expanseRoundTrips() {
      picker.setExpanse(IconPicker.Expanse.XLARGE);

      assertEquals(IconPicker.Expanse.XLARGE, picker.getExpanse());
    }

    @Test
    @DisplayName("expanse rejects null")
    void expanseRejectsNull() {
      assertThrows(NullPointerException.class, () -> picker.setExpanse(null));
    }

    @Test
    @DisplayName("the dropdown is closed by default")
    void closedByDefault() {
      assertFalse(picker.isOpened());
    }

    @Test
    @DisplayName("i18n defaults to the English texts")
    void i18nDefault() {
      assertEquals("Search {count} icons", picker.getI18n().getSearch());
    }

    @Test
    @DisplayName("i18n round trips")
    void i18nRoundTrips() {
      final IconPickerI18n i18n = new IconPickerI18n().setSearch("Suche in {count} Icons");

      picker.setI18n(i18n);

      assertSame(i18n, picker.getI18n());
    }

    @Test
    @DisplayName("i18n rejects null")
    void i18nRejectsNull() {
      assertThrows(NullPointerException.class, () -> picker.setI18n(null));
    }
  }

  @Nested
  @DisplayName("pools")
  class PoolsTests {

    @Test
    @DisplayName("shows every built-in pool by default")
    void everyPoolByDefault() {
      assertEquals(
          List.of(Pool.TABLER, Pool.FEATHER, Pool.FONT_AWESOME, Pool.DWC), picker.getPools());
    }

    @Test
    @DisplayName("keeps the given order and shows a repeated pool once")
    void keepsOrder() {
      picker.setPools(Pool.FONT_AWESOME, Pool.TABLER, Pool.FONT_AWESOME);

      assertEquals(List.of(Pool.FONT_AWESOME, Pool.TABLER), picker.getPools());
    }

    @Test
    @DisplayName("shows no built-in pool when given none")
    void noPools() {
      picker.setPools();

      assertEquals(List.of(), picker.getPools());
    }

    @Test
    @DisplayName("rejects a null array or a null pool")
    void rejectsNull() {
      assertThrows(NullPointerException.class, () -> picker.setPools((Pool[]) null));
      assertThrows(NullPointerException.class, () -> picker.setPools(Pool.DWC, null));
    }

    @Test
    @DisplayName("lists the chosen built-in pools, then the custom pools, as the element's tabs")
    void composesTabs() {
      final String tabs = IconPicker.tabsOf(List.of(Pool.DWC, Pool.TABLER), List.of("brand"));

      assertEquals("dwc,tabler,brand", tabs);
    }
  }

  @Nested
  @DisplayName("custom pools")
  class CustomPoolTests {

    @Test
    @DisplayName("has no custom pools by default")
    void noneByDefault() {
      assertEquals(List.of(), picker.getCustomPools());
    }

    @Test
    @DisplayName("lists the pools' names in the order they were added")
    void accumulates() {
      picker.addCustomPool("brand", "Brand", List.of("logo"));

      picker.addCustomPool("flags", "Flags", Map.of("de", List.of("germany")));

      assertEquals(List.of("brand", "flags"), picker.getCustomPools());
    }

    @Test
    @DisplayName("removes a pool by name and ignores a name it doesn't have")
    void removes() {
      picker.addCustomPool("brand", "Brand", List.of("logo"));
      picker.addCustomPool("flags", "Flags", List.of("de"));

      picker.removeCustomPool("brand");
      picker.removeCustomPool("missing");

      assertEquals(List.of("flags"), picker.getCustomPools());
    }

    @Test
    @DisplayName("rejects removing a null name")
    void rejectsRemovingNull() {
      assertThrows(NullPointerException.class, () -> picker.removeCustomPool(null));
    }

    @Test
    @DisplayName("rejects a pool name that was already added")
    void rejectsDuplicate() {
      picker.addCustomPool("brand", "Brand", List.of("logo"));

      assertThrows(
          IllegalArgumentException.class,
          () -> picker.addCustomPool("brand", "Other", List.of("mark")));
      assertEquals(List.of("brand"), picker.getCustomPools());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("callsWithNullArgument")
    @DisplayName("rejects a null argument")
    void rejectsNullArgument(Consumer<IconPicker> addCustomPool) {
      assertThrows(NullPointerException.class, () -> addCustomPool.accept(picker));
      assertEquals(List.of(), picker.getCustomPools());
    }

    static Stream<Named<Consumer<IconPicker>>> callsWithNullArgument() {
      return Stream.of(
          Named.of("null name", target -> target.addCustomPool(null, "Brand", List.of("logo"))),
          Named.of("null label", target -> target.addCustomPool("brand", null, List.of("logo"))),
          Named.of(
              "null names",
              target -> target.addCustomPool("brand", "Brand", (Collection<String>) null)),
          Named.of(
              "null names with tags",
              target -> target.addCustomPool("brand", "Brand", (Map<String, List<String>>) null)));
    }

    @ParameterizedTest(name = "rejects pool name \"{0}\"")
    @ValueSource(strings = {"", " ", "brand,flags", "brand ", "brand:dark", "tabler", "fa"})
    @DisplayName("rejects a blank, padded, built-in or comma or colon holding pool name")
    void rejectsInvalidPoolName(String name) {
      assertThrows(
          IllegalArgumentException.class,
          () -> picker.addCustomPool(name, "Brand", List.of("logo")));
      assertEquals(List.of(), picker.getCustomPools());
    }

    @Test
    @DisplayName("rejects a blank label")
    void rejectsBlankLabel() {
      assertThrows(
          IllegalArgumentException.class,
          () -> picker.addCustomPool("brand", "  ", List.of("logo")));
    }

    @Test
    @DisplayName("rejects a null icon name")
    void rejectsNullIconName() {
      final List<String> names = Arrays.asList("logo", null);

      assertThrows(NullPointerException.class, () -> picker.addCustomPool("brand", "Brand", names));
      assertEquals(List.of(), picker.getCustomPools());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("callsWithBlankIconName")
    @DisplayName("rejects a blank icon name")
    void rejectsBlankIconName(Consumer<IconPicker> addCustomPool) {
      assertThrows(IllegalArgumentException.class, () -> addCustomPool.accept(picker));
      assertEquals(List.of(), picker.getCustomPools());
    }

    static Stream<Named<Consumer<IconPicker>>> callsWithBlankIconName() {
      return Stream.of(
          Named.of("in names", target -> target.addCustomPool("brand", "Brand", List.of(""))),
          Named.of(
              "in names with tags",
              target -> target.addCustomPool("brand", "Brand", Map.of(" ", List.of("tag")))));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("namesWithNullTags")
    @DisplayName("rejects null tags")
    void rejectsNullTags(Map<String, List<String>> namesWithTags) {
      assertThrows(
          NullPointerException.class, () -> picker.addCustomPool("brand", "Brand", namesWithTags));
    }

    static Stream<Named<Map<String, List<String>>>> namesWithNullTags() {
      return Stream.of(
          Named.of("null tag list", Collections.singletonMap("logo", null)),
          Named.of("null tag", Map.of("logo", Arrays.asList("brand", null))));
    }

    @Test
    @DisplayName("returns an unmodifiable list of names")
    void unmodifiable() {
      picker.addCustomPool("brand", "Brand", List.of("logo"));
      final List<String> pools = picker.getCustomPools();

      assertThrows(UnsupportedOperationException.class, () -> pools.remove(0));
    }
  }

  @Nested
  @DisplayName("events")
  class EventTests {

    @Test
    @DisplayName("fires a value change event for an icon picked in the browser")
    void valueChangeOnPick() {
      final List<ValueChangeEvent<IconDefinition<?>>> received = new ArrayList<>();
      picker.addValueChangeListener(received::add);

      fireIconChanged(Map.of("pool", "tabler", "name", "filled-star"));

      assertEquals(1, received.size());
      assertEquals("filled-star", received.get(0).getValue().getName());
      assertEquals("tabler", received.get(0).getValue().getPool());
      assertSame(picker, received.get(0).getSource());
    }

    @Test
    @DisplayName("fires a value change event with no icon when the icon is removed")
    void valueChangeOnRemove() {
      final List<ValueChangeEvent<IconDefinition<?>>> received = new ArrayList<>();
      picker.onValueChange(received::add);
      final Map<String, Object> payload = new HashMap<>();
      payload.put("pool", null);
      payload.put("name", null);

      fireIconChanged(payload);

      assertEquals(1, received.size());
      assertNull(received.get(0).getValue());
    }

    @Test
    @DisplayName("notifies each value change listener once per icon change")
    void notifiesEachListenerOnce() {
      final List<String> received = new ArrayList<>();
      picker.addValueChangeListener(event -> received.add("first:" + event.getValue().getName()));
      picker.addValueChangeListener(event -> received.add("second:" + event.getValue().getName()));

      fireIconChanged(Map.of("pool", "fa", "name", "fas-house"));

      assertEquals(List.of("first:fas-house", "second:fas-house"), received);
    }

    @Test
    @DisplayName("stops notifying a removed value change listener")
    void removedValueChangeListener() {
      final List<ValueChangeEvent<IconDefinition<?>>> received = new ArrayList<>();
      final ListenerRegistration<ValueChangeEvent<IconDefinition<?>>> registration =
          picker.addValueChangeListener(received::add);
      registration.remove();

      fireIconChanged(Map.of("pool", "tabler", "name", "star"));

      assertTrue(received.isEmpty());
    }

    @Test
    @DisplayName("registers icon changed listeners under both names")
    void iconChangedListeners() {
      picker.addIconChangedListener(event -> {});
      picker.onIconChanged(event -> {});

      assertEquals(2, picker.getEventListeners(IconChangedEvent.class).size());
    }

    @Test
    @DisplayName("registers opened changed listeners under both names")
    void openedChangedListeners() {
      picker.addOpenedChangedListener(event -> {});
      picker.onOpenedChanged(event -> {});

      assertEquals(2, picker.getEventListeners(OpenedChangedEvent.class).size());
    }

    private void fireIconChanged(Map<String, Object> payload) {
      final var event = new IconChangedEvent(picker, payload);
      picker.getEventListeners(IconChangedEvent.class).forEach(listener -> listener.onEvent(event));
    }
  }
}
