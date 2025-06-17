package com.webforj.addons.components.sidemenu;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.webforj.PendingResult;
import com.webforj.addons.components.sidemenu.events.ChangedEvent;
import com.webforj.addons.components.sidemenu.events.FavoriteChangedEvent;
import com.webforj.addons.components.sidemenu.events.ItemClickedEvent;
import com.webforj.addons.components.sidemenu.events.SearchedEvent;
import com.webforj.addons.constant.GlobalConstants;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.concern.HasAttribute;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasJsExecution;
import com.webforj.concern.HasProperty;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * A Side Menu component designed for webforj. The {@code SideMenu} facilitates navigation through
 * hierarchical structures of pages or sections within an application. It offers customization
 * options, allowing developers to adjust the menu's appearance, behavior, and content to suit
 * specific application requirements.
 *
 * <p>Key features include support for favoriting items, reordering sections, and displaying
 * selected sections. Developers can also customize icons and enable search functionality to enhance
 * user navigation experiences.
 *
 * <p>The {@code SideMenu} is particularly useful in applications where efficient navigation between
 * different pages or sections is essential. It provides a flexible and intuitive means of
 * organizing and accessing application features, contributing to a seamless user experience.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-side-menu")
@JavaScript(
    value = GlobalConstants.DWC_CDN,
    top = true,
    attributes = {@Attribute(name = "type", value = "module")})
public class SideMenu extends ElementComposite
    implements HasClassName<SideMenu>,
        HasProperty<SideMenu>,
        HasStyle<SideMenu>,
        HasJsExecution,
        HasAttribute<SideMenu> {

  /**
   * Enum representing {@code sections} in a side menu component. These are the possible valid
   * values user can set to the {@code sections} which will define visibility and the order of the
   * {@code sections}.
   */
  public enum Section {
    /** This section contains items available in the side menu. */
    @SerializedName("items")
    ITEMS,

    /** This section contains favorite items that users have marked. */
    @SerializedName("favorites")
    FAVORITES;
  }

  /** Property for the icon displayed when an item is marked as a favorite. */
  private final PropertyDescriptor<String> favoriteFilledIconProp =
      PropertyDescriptor.property("favoriteFilledIcon", "tabler:filled-star");

  /** Property for the icon displayed when an item is not marked as a favorite. */
  private final PropertyDescriptor<String> favoriteIconProp =
      PropertyDescriptor.property("favoriteIcon", "tabler:star");

  /** Property for the icon displayed for links that open in a new tab. */
  private final PropertyDescriptor<String> newTabIconProp =
      PropertyDescriptor.property("newTabIcon", "chevron-right");

  /** Property for the placeholder text in the search input. */
  private final PropertyDescriptor<String> placeholderProp =
      PropertyDescriptor.property("placeholder", "search");

  /**
   * Controls the visibility of the "Expand All" and "Collapse All" buttons in the header of the
   * items section.
   */
  private final PropertyDescriptor<Boolean> expandCollapseButtonsVisibleProp =
      PropertyDescriptor.property("expandCollapseButtonsVisible", true);

  /** Property for the search term in the search input. */
  private final PropertyDescriptor<String> searchTermProp =
      PropertyDescriptor.property("searchTerm", "");

  /** Controls the visibility of the search field. */
  private final PropertyDescriptor<Boolean> searchVisibleProp =
      PropertyDescriptor.property("searchVisible", true);

  /**
   * Translation object for the static texts to enable passing translations keys for multilingual
   * functionality.
   */
  private final PropertyDescriptor<SideMenuI18n> i18nProp =
      PropertyDescriptor.property("i18n", null);

  /** Property for defining the sections displayed in the side menu. */
  private final PropertyDescriptor<String> sectionsProp =
      PropertyDescriptor.property("sections", "items, favorites");

  /** Property for the initially selected item in the side menu. */
  private final PropertyDescriptor<String> selectedProp =
      PropertyDescriptor.property("selected", "");

  /** Property for the list of items to be displayed in the side menu. */
  private final PropertyDescriptor<List<SideMenuItem>> itemsProp =
      PropertyDescriptor.property("items", new ArrayList<>());

  /**
   * Adds a listener for the item clicked event, which is triggered when an item is clicked.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<ItemClickedEvent> addItemClickedListener(
      EventListener<ItemClickedEvent> listener) {
    return this.addEventListener(ItemClickedEvent.class, listener);
  }

  /**
   * Adds a listener for the changed event, which is triggered when item selection changed.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<ChangedEvent> addChangedListener(
      EventListener<ChangedEvent> listener) {
    return this.addEventListener(ChangedEvent.class, listener);
  }

  /**
   * Adds a listener for the searched event, which is triggered when a search made for an item.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<SearchedEvent> addSearchedListener(
      EventListener<SearchedEvent> listener) {
    return this.addEventListener(SearchedEvent.class, listener);
  }

  /**
   * Adds a listener for the favorite changed event, which is triggered when an item's favorite
   * status is changed.
   *
   * @param listener The event listener to add.
   * @return A registration object that can be used to unregister the listener if needed.
   */
  public ListenerRegistration<FavoriteChangedEvent> addFavoriteChangedListener(
      EventListener<FavoriteChangedEvent> listener) {
    return this.addEventListener(FavoriteChangedEvent.class, listener);
  }

  /**
   * Adds a single item to the top level of the side menu.
   *
   * @param item The {@link SideMenuItem} to add. Cannot be null.
   * @return This {@code SideMenu} instance for method chaining.
   * @throws NullPointerException if item is null.
   */
  public SideMenu addItem(SideMenuItem item) {
    Objects.requireNonNull(item, "Item cannot be null");
    final var updatedItems = getMutableItemsList();
    updatedItems.add(item);
    return setItems(updatedItems);
  }

  /**
   * Adds a {@link SideMenuItem} as a child to an existing item identified by its unique ID. The
   * search for the parent item is performed recursively throughout the menu structure. If the
   * parent item is found, it is replaced with a new instance containing the added child in its
   * children list. The entire menu item list is updated accordingly.
   *
   * @param parentId The unique ID of the parent item to which the child should be added. Cannot be
   *     null.
   * @param childItem The {@link SideMenuItem} to add as a child. Cannot be null.
   * @return This {@code SideMenu} instance for method chaining.
   * @throws NullPointerException if {@code parentId} or {@code childItem} is null.
   * @throws IllegalArgumentException if no item with the specified {@code parentId} is found within
   *     the current menu items.
   */
  public SideMenu addItem(String parentId, SideMenuItem childItem) {
    Objects.requireNonNull(parentId, "Parent ID cannot be null");
    Objects.requireNonNull(childItem, "Child item cannot be null");

    final var changed = new AtomicBoolean(false);
    BiFunction<SideMenuItem, List<SideMenuItem>, List<SideMenuItem>> addModifier =
        (parent, siblings) -> {
          final var newChildren = new ArrayList<>(parent.getChildren());
          newChildren.add(childItem);
          final var updatedParent = new SideMenuItem(parent).setChildren(newChildren);

          return siblings.stream()
              .map(sibling -> sibling.getId().equals(parentId) ? updatedParent : sibling)
              .toList();
        };

    final var currentItems = getMutableItemsList();
    final var updatedItems =
        traverseAndModify(
            currentItems, item -> parentId.equals(item.getId()), addModifier, changed);

    if (!changed.get()) {
      throw new IllegalArgumentException("Parent item with ID '" + parentId + "' not found.");
    }
    if (updatedItems != currentItems) {
      return setItems(updatedItems);
    }
    return this;
  }

  /**
   * Adds multiple items to the top level of the side menu.
   *
   * @param items The {@link SideMenuItem}s to add. Cannot be null.
   * @return This {@code SideMenu} instance for method chaining.
   * @throws NullPointerException if items array is null or contains null elements.
   */
  public SideMenu addItems(SideMenuItem... items) {
    Objects.requireNonNull(items, "Items array cannot be null");
    if (Arrays.stream(items).anyMatch(Objects::isNull)) {
      throw new NullPointerException("Items array cannot contain null elements");
    }
    if (items.length == 0) {
      return this;
    }
    final var updatedItems = getMutableItemsList();
    updatedItems.addAll(Arrays.asList(items));
    setItems(updatedItems);
    return this;
  }

  /**
   * Adds a collection of items to the top level of the side menu.
   *
   * @param items A collection of {@link SideMenuItem}s to add. Cannot be null.
   * @return This {@code SideMenu} instance for method chaining.
   * @throws NullPointerException if the collection is null or contains null elements.
   */
  public SideMenu addItems(Collection<SideMenuItem> items) {
    Objects.requireNonNull(items, "Items collection cannot be null");
    if (items.stream().anyMatch(Objects::isNull)) {
      throw new NullPointerException("Items collection cannot contain null elements");
    }
    if (items.isEmpty()) {
      return this;
    }
    final var updatedItems = getMutableItemsList();
    updatedItems.addAll(items);
    setItems(updatedItems);
    return this;
  }

  /**
   * Removes an item from the menu, searching recursively by the item's ID.
   *
   * @param item The {@link SideMenuItem} to remove. Its ID will be used for removal. Returns
   *     immediately if the item is null.
   * @return {@code true} if the item was found and removed; {@code false} if the item was not found
   *     or item was null.
   */
  public boolean removeItem(SideMenuItem item) {
    if (item == null) {
      return false;
    }
    return removeItemById(item.getId());
  }

  /**
   * Removes an item from the side menu, searching recursively by its unique ID. If an item with the
   * specified ID is found, it and its entire subtree are removed from the menu structure.
   *
   * @param itemId The unique ID of the item to remove. If null, the method returns immediately
   *     without making changes.
   * @return {@code true} if the item was found and removed; {@code false} if the item was not found
   *     or itemId was null.
   */
  public boolean removeItemById(String itemId) {
    if (itemId == null) {
      return false;
    }
    final var changed = new AtomicBoolean(false);

    BiFunction<SideMenuItem, List<SideMenuItem>, List<SideMenuItem>> removeModifier =
        (target, siblings) ->
            siblings.stream().filter(sibling -> !sibling.getId().equals(itemId)).toList();

    final var currentItems = getMutableItemsList();
    final var updatedItems =
        traverseAndModify(
            currentItems, item -> itemId.equals(item.getId()), removeModifier, changed);

    if (changed.get()) {
      setItems(updatedItems);
      return true;
    }
    return false;
  }

  /**
   * Removes all items from the side menu.
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

  /**
   * Gets the list of favorite items from the side menu asynchronously.
   *
   * @return A {@link PendingResult} that will resolve to the list of favorite items.
   */
  public PendingResult<List<SideMenuItem>> getFavoriteItems() {
    return this.getBoundComponent()
        .callJsFunctionAsync("getFavoriteItems")
        .thenApply(
            result -> {
              if (result == null) {
                return Collections.emptyList();
              }

              final var gson = new Gson();
              final var listType = new TypeToken<List<SideMenuItem>>() {}.getType();

              try {
                final List<SideMenuItem> favoriteItems = gson.fromJson((String) result, listType);
                return favoriteItems != null ? favoriteItems : Collections.emptyList();
              } catch (Exception e) {
                return Collections.emptyList();
              }
            });
  }

  /**
   * Gets the currently selected item from the side menu asynchronously.
   *
   * @return A {@link PendingResult} that will resolve to the selected item, or null if no item is
   *     selected.
   */
  public PendingResult<SideMenuItem> getSelectedItem() {
    return this.getBoundComponent()
        .callJsFunctionAsync("getSelectedItem")
        .thenApply(
            result -> {
              if (result == null || "null".equals(result.toString())) {
                return null;
              }

              final var gson = new Gson();

              try {
                return gson.fromJson((String) result, SideMenuItem.class);
              } catch (Exception e) {
                return null;
              }
            });
  }

  /**
   * Recursively traverses the item tree, applying modifications based on a predicate and a modifier
   * function. Handles immutable-style updates by creating new lists and item instances where
   * necessary.
   *
   * <p>This method first checks if any item at the current level matches the predicate. If a match
   * is found, the modifier function is applied to the entire list of siblings, the flag is set, and
   * the resulting list is immediately returned, unwinding the recursion from that point.
   *
   * <p>If no item at the current level matches the predicate, the method proceeds to recursively
   * call itself on the children of each item. If any child list is modified (detected by reference
   * change), the parent item is reconstructed with the new children, the flag is set, and a new
   * list for the current level is built lazily. If no modifications occurred directly or in the
   * children, the original list reference is returned to optimize for unchanged subtrees.
   *
   * @param items The list of {@link SideMenuItem}s at the current recursion level. Should be
   *     treated as immutable within this function's logic, returning new lists on modification.
   * @param predicate A {@link Predicate} to identify the target item(s) for modification based on
   *     their properties (e.g., matching an ID).
   * @param modifier A {@link BiFunction} that takes the matched item and its original sibling list,
   *     and returns a potentially new list reflecting the modification (e.g., item removed, item
   *     replaced/updated).
   * @param flag An {@link AtomicBoolean} flag shared across the recursive calls. It will be set to
   *     {@code true} if any modification occurs, either because the predicate matched an item
   *     directly or because a child list was modified during recursion.
   * @return A new list instance containing the modified items if changes occurred at this level or
   *     below; otherwise, returns the original {@code items} list reference if no changes were
   *     made.
   */
  private List<SideMenuItem> traverseAndModify(
      List<SideMenuItem> items,
      Predicate<SideMenuItem> predicate,
      BiFunction<SideMenuItem, List<SideMenuItem>, List<SideMenuItem>> modifier,
      AtomicBoolean flag) {
    if (items == null || items.isEmpty()) {
      return items;
    }
    for (final var item : items) {
      if (predicate.test(item)) {
        final var modifiedList = modifier.apply(item, items);
        if (modifiedList != items) {
          flag.set(true);
        }
        return modifiedList;
      }
    }
    List<SideMenuItem> newItems = null;

    for (int i = 0; i < items.size(); i++) {
      final var currentItem = items.get(i);
      final var originalChildren = currentItem.getChildren();
      final var modifiedChildren = traverseAndModify(originalChildren, predicate, modifier, flag);
      final boolean childrenChanged = (modifiedChildren != originalChildren);

      if (newItems == null && childrenChanged) {
        newItems = new ArrayList<>(items.size());
        for (int j = 0; j < i; j++) {
          newItems.add(items.get(j));
        }
        flag.set(true);
      }
      if (newItems != null) {
        if (childrenChanged) {
          final var updatedItem = new SideMenuItem(currentItem).setChildren(modifiedChildren);
          newItems.add(updatedItem);
        } else {
          newItems.add(currentItem);
        }
      }
    }
    return newItems != null ? newItems : items;
  }

  /**
   * Finds an item by its ID recursively.
   *
   * @param itemId The ID of the item to find.
   * @return An Optional containing the found {@link SideMenuItem}, or empty if not found.
   */
  public Optional<SideMenuItem> findItemById(String itemId) {
    if (itemId == null) {
      return Optional.empty();
    }
    return recursiveFindById(getItems(), itemId);
  }

  /**
   * Recursive helper to find an item by ID.
   *
   * @param items The list of items to search within at the current level.
   * @param itemId The ID to search for.
   * @return An Optional containing the found item, or empty.
   */
  private Optional<SideMenuItem> recursiveFindById(List<SideMenuItem> items, String itemId) {
    if (items == null || items.isEmpty()) {
      return Optional.empty();
    }

    for (SideMenuItem item : items) {
      if (itemId.equals(item.getId())) {
        return Optional.of(item);
      }
      final var foundInChildren = recursiveFindById(item.getChildren(), itemId);
      if (foundInChildren.isPresent()) {
        return foundInChildren;
      }
    }
    return Optional.empty();
  }

  /**
   * Retrieves a mutable copy of the current top-level item list.
   *
   * @return A new mutable ArrayList containing the current items.
   */
  private List<SideMenuItem> getMutableItemsList() {
    final var currentItems = super.get(itemsProp);
    return currentItems == null ? new ArrayList<>() : new ArrayList<>(currentItems);
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
   * @param favoriteFilledIcon The icon displayed when an item is marked as a favorite.
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
   * @param favoriteIcon The icon displayed when an item is not marked as a favorite.
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
   * Gets the visibility state of the "Expand All" and "Collapse All" buttons.
   *
   * @return {@code true} if the "Expand All" and "Collapse All" buttons are visible; {@code false}
   *     otherwise.
   */
  public boolean isExpandCollapseButtonsVisible() {
    return super.get(expandCollapseButtonsVisibleProp);
  }

  /**
   * Sets the visibility state of the "Expand All" and "Collapse All" buttons.
   *
   * @param expandCollapseButtons {@code true} to display the "Expand All" and "Collapse All"
   *     buttons; {@code false} to hide them.
   * @return The updated instance of the side menu.
   */
  public SideMenu setExpandCollapseButtonsVisible(boolean expandCollapseButtons) {
    super.set(expandCollapseButtonsVisibleProp, expandCollapseButtons);
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
   * Gets the visibility state of the search field.
   *
   * @return {@code true} if the search field is visible; {@code false} otherwise.
   */
  public boolean isSearchVisible() {
    return super.get(searchVisibleProp);
  }

  /**
   * Sets the visibility state of the search field.
   *
   * @param searchVisible {@code true} to display the search field; {@code false} to hide it and
   *     disable search functionality.
   * @return The updated instance of the side menu.
   */
  public SideMenu setSearchVisible(boolean searchVisible) {
    super.set(searchVisibleProp, searchVisible);
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
   * Sets the sections to define visibility and order in the side menu component. Users can pass
   * enum values to set the sections. The sections are concatenated and separated by a comma.
   *
   * @param sections The sections displayed in the side menu.
   * @return The updated instance of the side menu.
   */
  public SideMenu setSections(Section... sections) {
    Set<Section> uniqueSections = new LinkedHashSet<>(Arrays.asList(sections));

    if (uniqueSections.size() > Section.values().length) {
      throw new IllegalArgumentException("Number of sections exceeds the number of enum values.");
    }

    StringBuilder concatenatedSections = new StringBuilder();
    for (Section section : uniqueSections) {
      if (!concatenatedSections.isEmpty()) {
        concatenatedSections.append(", ");
      }
      concatenatedSections.append(section.name().toLowerCase());
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
  public List<SideMenuItem> getItems() {
    final var items = super.get(itemsProp);
    return items == null ? Collections.emptyList() : Collections.unmodifiableList(items);
  }

  /**
   * Sets the list of items to be displayed in the side menu.
   *
   * @param items The list of items to be displayed in the side menu.
   * @return The updated instance of the side menu.
   */
  public SideMenu setItems(List<SideMenuItem> items) {
    Objects.requireNonNull(items, "Items cannot be null");
    super.set(itemsProp, items);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu addClassName(String... classNames) {
    this.getBoundComponent().addClassName(classNames);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu removeClassName(String... classNames) {
    this.getBoundComponent().removeClassName(classNames);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Object getProperty(String property) {
    return this.getBoundComponent().getProperty(property);
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu setProperty(String property, Object value) {
    this.getBoundComponent().setProperty(property, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String getAttribute(String attribute) {
    return this.getBoundComponent().getAttribute(attribute);
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu setAttribute(String property, String value) {
    this.getBoundComponent().setAttribute(property, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String getStyle(String property) {
    return this.getBoundComponent().getStyle(property);
  }

  /** {@inheritDoc} */
  @Override
  public String getComputedStyle(String property) {
    return this.getBoundComponent().getComputedStyle(property);
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu setStyle(String property, String value) {
    this.getBoundComponent().setStyle(property, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public SideMenu removeStyle(String property) {
    this.getBoundComponent().removeStyle(property);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Object executeJs(String js) {
    return super.getBoundComponent().executeJs(js);
  }

  /** {@inheritDoc} */
  @Override
  public PendingResult<Object> executeJsAsync(String js) {
    return super.getBoundComponent().executeJsAsync(js);
  }

  /** {@inheritDoc} */
  @Override
  public void executeJsVoidAsync(String js) {
    super.getBoundComponent().executeJsVoidAsync(js);
  }
}
