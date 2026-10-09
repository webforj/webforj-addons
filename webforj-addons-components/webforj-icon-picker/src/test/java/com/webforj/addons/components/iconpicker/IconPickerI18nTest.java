package com.webforj.addons.components.iconpicker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("IconPickerI18n")
class IconPickerI18nTest {

  private static final Map<String, String> DEFAULTS =
      new TreeMap<>(
          Map.ofEntries(
              Map.entry("search", "Search {count} icons"),
              Map.entry("resultCount", "{count} icons"),
              Map.entry("noMatches", "No icons match '{query}'"),
              Map.entry("removeIcon", "Remove icon"),
              Map.entry("loading", "Loading icons"),
              Map.entry("loadFailed", "Couldn't load {pool} icons. Close and reopen to try again."),
              Map.entry("poolNotRegistered", "The {pool} pool isn't registered in this page."),
              Map.entry("outline", "Outline"),
              Map.entry("filled", "Filled"),
              Map.entry("solid", "Solid"),
              Map.entry("regular", "Regular"),
              Map.entry("brands", "Brands")));

  @ParameterizedTest(name = "{0}")
  @MethodSource("texts")
  @DisplayName("getter returns the English default")
  void getterReturnsDefault(I18nText text) {
    final String value = text.getter().apply(new IconPickerI18n());

    assertEquals(DEFAULTS.get(text.key()), value);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("texts")
  @DisplayName("setter stores the text")
  void setterStoresText(I18nText text) {
    final var i18n = new IconPickerI18n();

    text.setter().apply(i18n, "Changed");

    assertEquals("Changed", text.getter().apply(i18n));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("texts")
  @DisplayName("setter returns the same object")
  void setterReturnsSameObject(I18nText text) {
    final var i18n = new IconPickerI18n();

    final IconPickerI18n returned = text.setter().apply(i18n, "Changed");

    assertSame(i18n, returned);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("texts")
  @DisplayName("setter rejects null and keeps the default")
  void setterRejectsNull(I18nText text) {
    final var i18n = new IconPickerI18n();

    assertThrows(NullPointerException.class, () -> text.setter().apply(i18n, null));
    assertEquals(DEFAULTS.get(text.key()), text.getter().apply(i18n));
  }

  static Stream<I18nText> texts() {
    return Stream.of(
        new I18nText("search", IconPickerI18n::getSearch, IconPickerI18n::setSearch),
        new I18nText("resultCount", IconPickerI18n::getResultCount, IconPickerI18n::setResultCount),
        new I18nText("noMatches", IconPickerI18n::getNoMatches, IconPickerI18n::setNoMatches),
        new I18nText("removeIcon", IconPickerI18n::getRemoveIcon, IconPickerI18n::setRemoveIcon),
        new I18nText("loading", IconPickerI18n::getLoading, IconPickerI18n::setLoading),
        new I18nText("loadFailed", IconPickerI18n::getLoadFailed, IconPickerI18n::setLoadFailed),
        new I18nText(
            "poolNotRegistered",
            IconPickerI18n::getPoolNotRegistered,
            IconPickerI18n::setPoolNotRegistered),
        new I18nText("outline", IconPickerI18n::getOutline, IconPickerI18n::setOutline),
        new I18nText("filled", IconPickerI18n::getFilled, IconPickerI18n::setFilled),
        new I18nText("solid", IconPickerI18n::getSolid, IconPickerI18n::setSolid),
        new I18nText("regular", IconPickerI18n::getRegular, IconPickerI18n::setRegular),
        new I18nText("brands", IconPickerI18n::getBrands, IconPickerI18n::setBrands));
  }

  @Test
  @DisplayName("serializes to an object holding exactly the element's keys")
  void serializesDefaults() {
    final JsonObject json = new Gson().toJsonTree(new IconPickerI18n()).getAsJsonObject();

    assertEquals(DEFAULTS, toMap(json));
  }

  @Test
  @DisplayName("serializes a changed text under its key")
  void serializesChangedText() {
    final IconPickerI18n i18n = new IconPickerI18n().setNoMatches("Keine Icons zu '{query}'");

    final JsonObject json = new Gson().toJsonTree(i18n).getAsJsonObject();

    assertEquals("Keine Icons zu '{query}'", json.get("noMatches").getAsString());
    assertEquals(DEFAULTS.get("removeIcon"), json.get("removeIcon").getAsString());
  }

  private static Map<String, String> toMap(JsonObject json) {
    final var texts = new TreeMap<String, String>();
    json.entrySet().forEach(entry -> texts.put(entry.getKey(), entry.getValue().getAsString()));
    return texts;
  }

  private record I18nText(
      String key,
      Function<IconPickerI18n, String> getter,
      BiFunction<IconPickerI18n, String, IconPickerI18n> setter) {

    @Override
    public String toString() {
      return key;
    }
  }
}
