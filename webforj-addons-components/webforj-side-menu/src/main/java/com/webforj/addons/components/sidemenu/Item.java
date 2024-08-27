package com.webforj.addons.components.sidemenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual item within the side menu of a webforj application.
 * <p>
 * Developers can use {@code Item} instances to construct hierarchical menus
 * with nested levels of items. Each item provides essential information for
 * navigation and interaction within the side menu component.
 * </p>
 *
 * @since 1.00
 * @author ElyasSalar
 */
public class Item {
	/**
	 * A unique identifier for the item.
	 */
	private String id;

	/**
	 * The icon associated with the item. It can be a string of the following
	 * values:
	 * <ul>
	 * <li>URL: (ex: /path/to/image.png)
	 * <li>Data URL: (ex: data:image/jpeg;base64,/9j/4SDpRXhpZgAAT....)
	 * <li>icon name: An icon to load from the default dwc icons pool. (ex:
	 * {@code "x"})
	 * <li>pool and name:ICON_NAME: An icon to load from the passed pool. (ex:
	 * {@code feather:x"})
	 * </ul>
	 */
	private String icon;

	/**
	 * The display text or caption for the item.
	 */
	private String caption;

	/**
	 * The URL or link associated with the item. When users interact with the item,
	 * they are navigated to the specified link.
	 */
	private String link;

	/**
	 * Indicates whether the link should be opened in a new tab. If true, the link
	 * opens in a new browser tab/window; if false, it opens in the current tab.
	 */
	private boolean newTab;

	/**
	 * An shortcut key associated with the item. This key provides users with a
	 * quick keyboard shortcut to access the item.
	 */
	private String shortcut;

	/**
	 * Indicates whether the item is marked as a favorite. Users can mark items as
	 * favorites to quickly access them.
	 */
	private boolean favorite;

	/**
	 * The children items of the current item. It represents nested items contained
	 * within the current menu item.
	 */
	private List<Item> children;

	/**
	 * Constructs a new {@code SideMenu} instance with the specified properties.
	 */
	public Item(String id, String icon, String caption, String link, boolean favorite,
			List<Item> children) {
		this.id = id;
		this.icon = icon;
		this.caption = caption;
		this.link = link;
		this.favorite = favorite;
		this.children = children;
	}

	/**
	 * Constructs a new {@code SideMenu} instance with the specified properties.
	 */
	public Item(String id, String icon, String caption, String link, boolean favorite) {
		this(id, icon, caption, link, favorite, List.of());
	}

	/**
	 * Gets the unique identifier for the item.
	 *
	 * @return The unique identifier for the item.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for the item.
	 *
	 * @param id The unique identifier to set for the item.
	 * @return The updated instance of the item.
	 */
	public Item setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the icon associated with the item.
	 *
	 * @return The icon associated with the item.
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon associated with the item.
	 *
	 * @param icon The icon to set for the item.
	 * @return The updated instance of the item.
	 */
	public Item setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Gets the display text or caption for the item.
	 *
	 * @return The display text or caption for the item.
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the display text or caption for the item.
	 *
	 * @param caption The display text or caption to set for the item.
	 * @return The updated instance of the item.
	 */
	public Item setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	/**
	 * Gets the URL or link associated with the item.
	 *
	 * @return The URL or link associated with the item.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the URL or link associated with the item.
	 *
	 * @param link The URL or link to set for the item.
	 * @return The updated instance of the item.
	 */
	public Item setLink(String link) {
		this.link = link;
		return this;
	}

	/**
	 * Checks if the link should be opened in a new tab.
	 *
	 * @return True if the link should be opened in a new tab, otherwise false.
	 */
	public boolean isNewTab() {
		return newTab;
	}

	/**
	 * Sets whether the link should be opened in a new tab.
	 *
	 * @param newTab True if the link should be opened in a new tab, otherwise
	 *            false.
	 * @return The updated instance of the item.
	 */
	public Item setNewTab(boolean newTab) {
		this.newTab = newTab;
		return this;
	}

	/**
	 * Gets the shortcut key associated with the item.
	 *
	 * @return The shortcut key associated with the item.
	 */
	public String getShortcut() {
		return shortcut;
	}

	/**
	 * Sets the shortcut key associated with the item.
	 *
	 * @param shortcut The shortcut key to set for the item.
	 * @return The updated instance of the item.
	 */
	public Item setShortcut(String shortcut) {
		this.shortcut = shortcut;
		return this;
	}

	/**
	 * Checks if the item is marked as a favorite.
	 *
	 * @return True if the item is marked as a favorite, otherwise false.
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 * Sets whether the item is marked as a favorite.
	 *
	 * @param favorite True if the item is marked as a favorite, otherwise false.
	 * @return The updated instance of the item.
	 */
	public Item setFavorite(boolean favorite) {
		this.favorite = favorite;
		return this;
	}

	/**
	 * Gets the children items of the current item.
	 *
	 * @return The children items of the current item.
	 */
	public List<Item> getChildren() {
		return children;
	}

	/**
	 * Sets the children items of the current item.
	 *
	 * @param children The children items to set for the current item.
	 * @return The updated instance of the item.
	 */
	public Item setChildren(List<Item> children) {
		this.children = children;
		return this;
	}

}
