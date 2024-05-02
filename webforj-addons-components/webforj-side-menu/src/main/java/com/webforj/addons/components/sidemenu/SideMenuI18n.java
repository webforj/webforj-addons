package com.webforj.addons.components.sidemenu;

/**
 * The side menu translation object.
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public class SideMenuI18n {

  /**
   * The title of the items section in the {@link SideMenu} component.
   */
  private String itemsTitle = "Items";

  /**
   * The title of the favorites section in the {@link SideMenu} component.
   */
  private String favorites = "Favorites";

  /**
   * The text displayed when no items are found in the items section after a search in
   * {@link SideMenu}.
   */
  private String noItemFound = "No items found";

  /**
   * The text displayed when no favorite items are found in the favorites section after a search in
   * {@link SideMenu}.
   */
  private String noFavoriteItemFound = "No favorite items found";

  /**
   * Gets the favorites text.
   *
   * @return the favorites text
   */
  public String getFavorites() {
    return favorites;
  }

  /**
   * Sets the favorites text.
   *
   * @param favorites the favorites text
   */
  public SideMenuI18n setFavorites(String favorites) {
    this.favorites = favorites;
    return this;
  }

  /**
   * Gets the "No item found" text.
   *
   * @return the "No item found" text
   */
  public String getNoItemFound() {
    return noItemFound;
  }

  /**
   * Sets the "No item found" text.
   *
   * @param noItemFound the "No item found" text
   */
  public SideMenuI18n setNoItemFound(String noItemFound) {
    this.noItemFound = noItemFound;
    return this;
  }

  /**
   * Gets the "No favorite item found" text.
   *
   * @return the "No favorite item found" text
   */
  public String getNoFavoriteItemFound() {
    return noFavoriteItemFound;
  }

  /**
   * Sets the "No favorite item found" text.
   *
   * @param noFavoriteItemFound the "No favorite item found" text
   */
  public SideMenuI18n setNoFavoriteItemFound(String noFavoriteItemFound) {
    this.noFavoriteItemFound = noFavoriteItemFound;
    return this;
  }

  /**
   * Gets the items title text.
   *
   * @return the items title text
   */
  public String getItemsTitle() {
    return itemsTitle;
  }

  /**
   * Sets the items title text.
   *
   * @param itemsTitle the items title text
   */
  public SideMenuI18n setItemsTitle(String itemsTitle) {
    this.itemsTitle = itemsTitle;
    return this;
  }
}
