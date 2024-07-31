package com.webforj.addons.components.sidemenu;

import com.google.gson.annotations.SerializedName;
import com.webforj.addons.components.sidemenu.events.ChangedEvent;
import com.webforj.addons.components.sidemenu.events.SearchedEvent;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * A Side Menu component designed for webforj. The {@code SideMenu} facilitates
 * navigation through hierarchical structures of pages or sections within an
 * application. It offers customization options, allowing developers to adjust
 * the menu's appearance, behavior, and content to suit specific application
 * requirements.
 * <p>
 * Key features include support for favoriting items, reordering sections, and
 * displaying selected sections. Developers can also customize icons and enable
 * search functionality to enhance user navigation experiences.
 * </p>
 * <p>
 * The {@code SideMenu} is particularly useful in applications where efficient
 * navigation between different pages or sections is essential. It provides a
 * flexible and intuitive means of organizing and accessing application
 * features, contributing to a seamless user experience.
 * </p>
 *
 * @since 1.00
 * @author ElyasSalar
 */
@NodeName("dwc-side-menu")
@JavaScript(value = "https://d3hx2iico687v8.cloudfront.net/0.0.1/dwc-addons.esm.js", top = true, attributes = {
		@Attribute(name = "type", value = "module")})
public class SideMenu extends ElementComposite {

	/**
	 * Enum representing {@code sections} in a side menu component. These are the
	 * possible valid values user can set to the {@code sections} which will define
	 * visibility and the order of the {@code sections}.
	 */
	public enum Section {
		/**
		 * This section contains items available in the side menu.
		 */
		@SerializedName("items")
		ITEMS,

		/**
		 * This section contains favorite items that users have marked.
		 */
		@SerializedName("favorites")
		FAVORITES;
	}

	/**
	 * Property for the icon displayed when an item is marked as a favorite.
	 */
	private final PropertyDescriptor<String> favoriteFilledIconProp = PropertyDescriptor
			.property("favoriteFilledIcon", "tabler:star-filled");

	/**
	 * Property for the icon displayed when an item is not marked as a favorite.
	 */
	private final PropertyDescriptor<String> favoriteIconProp = PropertyDescriptor
			.property("favoriteIcon", "tabler:star");

	/**
	 * Property for the icon displayed for links that open in a new tab.
	 */
	private final PropertyDescriptor<String> newTabIconProp = PropertyDescriptor
			.property("newTabIcon", "chevron-right");

	/**
	 * Property for the placeholder text in the search input.
	 */
	private final PropertyDescriptor<String> placeholderProp = PropertyDescriptor
			.property("placeholder", "search");

	/**
	 * Property for the search term in the search input.
	 */
	private final PropertyDescriptor<String> searchTermProp = PropertyDescriptor
			.property("searchTerm", "");

	/**
	 * Translation object for the static texts to enable passing translations keys
	 * for multilingual functionality
	 */
	private final PropertyDescriptor<SideMenuI18n> i18nProp = PropertyDescriptor.property("i18n",
			null);

	/**
	 * Property for defining the sections displayed in the side menu.
	 */
	private final PropertyDescriptor<String> sectionsProp = PropertyDescriptor.property("sections",
			"items, favorites");

	/**
	 * Property for the initially selected item in the side menu.
	 */
	private final PropertyDescriptor<String> selectedProp = PropertyDescriptor.property("selected",
			"");

	/**
	 * Property for the list of items to be displayed in the side menu.
	 */
	private final PropertyDescriptor<List<Item>> itemsProp = PropertyDescriptor.property("items",
			new ArrayList<>());

	/**
	 * Adds a listener for the changed event, which is triggered when item selection
	 * changed.
	 *
	 * @param listener The event listener to add.
	 * @return A registration object that can be used to unregister the listener if
	 *         needed.
	 */
	public ListenerRegistration<ChangedEvent> addChangedListener(
			EventListener<ChangedEvent> listener) {
		return this.addEventListener(ChangedEvent.class, listener);
	}

	/**
	 * Adds a listener for the searched event, which is triggered when a search made
	 * for an item.
	 *
	 * @param listener The event listener to add.
	 * @return A registration object that can be used to unregister the listener if
	 *         needed.
	 */
	public ListenerRegistration<SearchedEvent> addSearchedListener(
			EventListener<SearchedEvent> listener) {
		return this.addEventListener(SearchedEvent.class, listener);
	}

	/**
	 * Gets the icon displayed when an item is marked as a favorite.
	 *
	 * @return The icon displayed when an item is marked as a favorite.
	 */
	public String getFavoriteFilledIcon() {
		return super.get(favoriteFilledIconProp);
	}

	/**
	 * Sets the icon displayed when an item is marked as a favorite.
	 *
	 * @param favoriteFilledIcon The icon displayed when an item is marked as a
	 *            favorite.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setFavoriteFilledIcon(String favoriteFilledIcon) {
		super.set(favoriteFilledIconProp, favoriteFilledIcon);
		return this;
	}

	/**
	 * Gets the icon displayed when an item is not marked as a favorite.
	 *
	 * @return The icon displayed when an item is not marked as a favorite.
	 */
	public String getFavoriteIcon() {
		return super.get(favoriteIconProp);
	}

	/**
	 * Sets the icon displayed when an item is not marked as a favorite.
	 *
	 * @param favoriteIcon The icon displayed when an item is not marked as a
	 *            favorite.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setFavoriteIcon(String favoriteIcon) {
		super.set(favoriteIconProp, favoriteIcon);
		return this;
	}

	/**
	 * Gets the icon displayed for links that open in a new tab.
	 *
	 * @return The icon displayed for links that open in a new tab.
	 */
	public String getNewTabIcon() {
		return super.get(newTabIconProp);
	}

	/**
	 * Sets the icon displayed for links that open in a new tab.
	 *
	 * @param newTabIcon The icon displayed for links that open in a new tab.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setNewTabIcon(String newTabIcon) {
		super.set(newTabIconProp, newTabIcon);
		return this;
	}

	/**
	 * Gets the placeholder text in the search input.
	 *
	 * @return The placeholder text in the search input.
	 */
	public String getPlaceholder() {
		return super.get(placeholderProp);
	}

	/**
	 * Sets the placeholder text in the search input.
	 *
	 * @param placeholder The placeholder text in the search input.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setPlaceholder(String placeholder) {
		super.set(placeholderProp, placeholder);
		return this;
	}

	/**
	 * Gets the initial search term in the search input.
	 *
	 * @return The search term in the search input.
	 */
	public String getSearchTerm() {
		return super.get(searchTermProp);
	}

	/**
	 * Sets the search term in the search input.
	 *
	 * @param searchTerm The initial search term in the search input.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setSearchTerm(String searchTerm) {
		super.set(searchTermProp, searchTerm);
		return this;
	}

	/**
	 * Gets the translation object for static texts.
	 *
	 * @return The translation object of the current static texts.
	 */
	public SideMenuI18n getI18n() {
		return super.get(i18nProp);
	}

	/**
	 * Sets the translation object for static texts.
	 *
	 * @param i18n The new translation object for the static texts.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setI18n(SideMenuI18n i18n) {
		super.set(i18nProp, i18n);
		return this;
	}

	/**
	 * Gets the sections displayed in the side menu.
	 *
	 * @return The sections displayed in the side menu.
	 */
	public String getSections() {
		return super.get(sectionsProp);
	}

	/**
	 * Sets the sections to define visibility and order in the side menu component.
	 * Users can pass enum values to set the sections. The sections are concatenated
	 * and separated by a comma.
	 *
	 * @param sections The sections displayed in the side menu.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setSections(Section... sections) {
		Set<Section> uniqueSections = new HashSet<>(Arrays.asList(sections));

		if (uniqueSections.size() > Section.values().length) {
			throw new IllegalArgumentException(
					"Number of sections exceeds the number of enum values.");
		}

		StringBuilder concatenatedSections = new StringBuilder();
		for (Section section : uniqueSections) {
			if (!concatenatedSections.isEmpty()) {
				concatenatedSections.append(", ");
			}
			concatenatedSections.append(section.name());
		}

		super.set(sectionsProp, concatenatedSections.toString());
		return this;
	}

	/**
	 * Gets the selected item in the side menu.
	 *
	 * @return The selected item in the side menu.
	 */
	public String getSelected() {
		return super.get(selectedProp);
	}

	/**
	 * Sets the selected item in the side menu.
	 *
	 * @param selected The selected item in the side menu.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setSelected(String selected) {
		super.set(selectedProp, selected);
		return this;
	}

	/**
	 * Gets the list of items to be displayed in the side menu.
	 *
	 * @return The list of items to be displayed in the side menu.
	 */
	public List<Item> getItems() {
		return super.get(itemsProp);
	}

	/**
	 * Sets the list of items to be displayed in the side menu.
	 *
	 * @param items The list of items to be displayed in the side menu.
	 * @return The updated instance of the side menu.
	 */
	public SideMenu setItems(List<Item> items) {
		super.set(itemsProp, items);
		return this;
	}
}
