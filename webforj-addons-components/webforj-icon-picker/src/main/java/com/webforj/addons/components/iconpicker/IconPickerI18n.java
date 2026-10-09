package com.webforj.addons.components.iconpicker;

import java.util.Objects;

/**
 * The texts the {@link IconPicker} shows, with English defaults.
 *
 * <p>A text may contain the placeholders {@code {count}}, {@code {query}} and {@code {pool}}, which
 * the picker replaces where the default text uses them. The picker merges the texts over its own
 * defaults, so every key keeps a value. Pool names and icon names are proper names and are never
 * translated. The empty-field text is the picker's placeholder, set with {@link
 * IconPicker#setPlaceholder(String)}.
 *
 * @author ElyasSalar
 * @since 26.03
 */
public class IconPickerI18n {

  private String search = "Search {count} icons";
  private String resultCount = "{count} icons";
  private String noMatches = "No icons match '{query}'";
  private String removeIcon = "Remove icon";
  private String loading = "Loading icons";
  private String loadFailed = "Couldn't load {pool} icons. Close and reopen to try again.";
  private String poolNotRegistered = "The {pool} pool isn't registered in this page.";
  private String outline = "Outline";
  private String filled = "Filled";
  private String solid = "Solid";
  private String regular = "Regular";
  private String brands = "Brands";

  /**
   * Gets the search field's placeholder.
   *
   * @return the text, {@code Search {count} icons} by default
   */
  public String getSearch() {
    return search;
  }

  /**
   * Sets the search field's placeholder, where {@code {count}} is the number of icons in the open
   * tab and variant.
   *
   * @param search the new placeholder, in which the picker replaces {@code {count}}
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setSearch(String search) {
    this.search = requireText(search, "search");
    return this;
  }

  /**
   * Gets the result count announced for a search.
   *
   * @return the text, {@code {count} icons} by default
   */
  public String getResultCount() {
    return resultCount;
  }

  /**
   * Sets the result count announced for a search, where {@code {count}} is the number of matches.
   *
   * @param resultCount the new announcement, in which the picker replaces {@code {count}}
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setResultCount(String resultCount) {
    this.resultCount = requireText(resultCount, "resultCount");
    return this;
  }

  /**
   * Gets the message shown when a search matches no icon.
   *
   * @return the text, {@code No icons match '{query}'} by default
   */
  public String getNoMatches() {
    return noMatches;
  }

  /**
   * Sets the message shown when a search matches no icon, where {@code {query}} is the search text.
   *
   * @param noMatches the new message, in which the picker replaces {@code {query}}
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setNoMatches(String noMatches) {
    this.noMatches = requireText(noMatches, "noMatches");
    return this;
  }

  /**
   * Gets the label of the action that removes the current icon.
   *
   * @return the text, {@code Remove icon} by default
   */
  public String getRemoveIcon() {
    return removeIcon;
  }

  /**
   * Sets the label of the action that removes the current icon.
   *
   * @param removeIcon the new label, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setRemoveIcon(String removeIcon) {
    this.removeIcon = requireText(removeIcon, "removeIcon");
    return this;
  }

  /**
   * Gets the message shown while a pool's icons load.
   *
   * @return the text, {@code Loading icons} by default
   */
  public String getLoading() {
    return loading;
  }

  /**
   * Sets the message shown while a pool's icons load.
   *
   * @param loading the new message, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setLoading(String loading) {
    this.loading = requireText(loading, "loading");
    return this;
  }

  /**
   * Gets the message shown when a pool's icons fail to load.
   *
   * @return the text, {@code Couldn't load {pool} icons. Close and reopen to try again.} by default
   */
  public String getLoadFailed() {
    return loadFailed;
  }

  /**
   * Sets the message shown when a pool's icons fail to load, where {@code {pool}} names the pool.
   *
   * @param loadFailed the new message, in which the picker replaces {@code {pool}}
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setLoadFailed(String loadFailed) {
    this.loadFailed = requireText(loadFailed, "loadFailed");
    return this;
  }

  /**
   * Gets the message shown in a custom pool's tab when the page has no DWC pool of that name.
   *
   * @return the text, {@code The {pool} pool isn't registered in this page.} by default
   */
  public String getPoolNotRegistered() {
    return poolNotRegistered;
  }

  /**
   * Sets the message shown in a custom pool's tab when the page has no DWC pool of that name, where
   * {@code {pool}} names the pool.
   *
   * @param poolNotRegistered the new message, in which the picker replaces {@code {pool}}
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setPoolNotRegistered(String poolNotRegistered) {
    this.poolNotRegistered = requireText(poolNotRegistered, "poolNotRegistered");
    return this;
  }

  /**
   * Gets the name of Tabler's outline variant.
   *
   * @return the text, {@code Outline} by default
   */
  public String getOutline() {
    return outline;
  }

  /**
   * Sets the name of Tabler's outline variant.
   *
   * @param outline the new variant name, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setOutline(String outline) {
    this.outline = requireText(outline, "outline");
    return this;
  }

  /**
   * Gets the name of Tabler's filled variant.
   *
   * @return the text, {@code Filled} by default
   */
  public String getFilled() {
    return filled;
  }

  /**
   * Sets the name of Tabler's filled variant.
   *
   * @param filled the new variant name, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setFilled(String filled) {
    this.filled = requireText(filled, "filled");
    return this;
  }

  /**
   * Gets the name of Font Awesome's solid variant.
   *
   * @return the text, {@code Solid} by default
   */
  public String getSolid() {
    return solid;
  }

  /**
   * Sets the name of Font Awesome's solid variant.
   *
   * @param solid the new variant name, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setSolid(String solid) {
    this.solid = requireText(solid, "solid");
    return this;
  }

  /**
   * Gets the name of Font Awesome's regular variant.
   *
   * @return the text, {@code Regular} by default
   */
  public String getRegular() {
    return regular;
  }

  /**
   * Sets the name of Font Awesome's regular variant.
   *
   * @param regular the new variant name, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setRegular(String regular) {
    this.regular = requireText(regular, "regular");
    return this;
  }

  /**
   * Gets the name of Font Awesome's brands variant.
   *
   * @return the text, {@code Brands} by default
   */
  public String getBrands() {
    return brands;
  }

  /**
   * Sets the name of Font Awesome's brands variant.
   *
   * @param brands the new variant name, shown as given
   * @return this object
   * @throws NullPointerException if the text is null
   */
  public IconPickerI18n setBrands(String brands) {
    this.brands = requireText(brands, "brands");
    return this;
  }

  private static String requireText(String text, String key) {
    return Objects.requireNonNull(text, "The i18n text '" + key + "' cannot be null");
  }
}
