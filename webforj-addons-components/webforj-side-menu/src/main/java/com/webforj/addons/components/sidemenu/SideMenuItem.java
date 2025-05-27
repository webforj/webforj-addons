package com.webforj.addons.components.sidemenu;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents an individual item within the side menu of a webforj application.
 *
 * <p>Developers use {@code SideMenuItem} instances to construct hierarchical menus. Each item
 * provides essential information for navigation and interaction within the {@link SideMenu}
 * component. Items are typically created using the {@link #builder()} pattern.
 *
 * @since 24.22
 * @author ElyasSalar
 */
public class SideMenuItem {

  private static final String ID_NULL_ERROR_MESSAGE = "ID cannot be null";
  private static final String LABEL_NULL_ERROR_MESSAGE = "Label cannot be null";

  /** A unique identifier for the item, used for selection and events. */
  private String id;

  /**
   * The icon associated with the item. It can be a string representing:
   *
   * <ul>
   *   <li>URL: (e.g., {@code /path/to/image.png})
   *   <li>Data URL: (e.g., {@code data:image/jpeg;base64,/9j/4...})
   *   <li>Icon name: From the default dwc icons pool (e.g., {@code "x"}).
   *   <li>Pool and name: {@code POOL_NAME:ICON_NAME} (e.g., {@code "feather:x"}).
   * </ul>
   */
  private String icon;

  // FIXME: The serialized name of the label should be here until client side code is updated.
  /** The display text or label for the item shown to the user. */
  @SerializedName("caption")
  private String label;

  /**
   * The URL or link associated with the item. When users interact with the item, they might be
   * navigated to this link.
   */
  private String link;

  /**
   * Indicates whether the link should be opened in a new tab. If true, the link opens in a new
   * browser tab/window; otherwise, it opens in the current tab (defaults to false).
   */
  private boolean newTab;

  /**
   * An optional shortcut key hint associated with the item. This can provide users with a visual
   * cue for keyboard shortcuts.
   */
  private String shortcut;

  /**
   * Indicates whether the item is marked as a favorite (defaults to false). Users might be able to
   * mark items as favorites for quick access.
   */
  private boolean favorite;

  /**
   * Child items of the current item, representing nested menu levels. Defaults to an empty list.
   */
  private List<SideMenuItem> children;

  /**
   * Private constructor used by the Builder.
   *
   * @param builder The builder instance containing the properties to set.
   */
  private SideMenuItem(Builder builder) {
    Objects.requireNonNull(builder.id, ID_NULL_ERROR_MESSAGE);
    Objects.requireNonNull(builder.label, LABEL_NULL_ERROR_MESSAGE);
    this.id = builder.id;
    this.label = builder.label;
    this.icon = builder.icon;
    this.link = builder.link;
    this.newTab = builder.newTab;
    this.shortcut = builder.shortcut;
    this.favorite = builder.favorite;
    this.children = (builder.children != null) ? builder.children : Collections.emptyList();
  }

  /**
   * Copy constructor to create a new {@code SideMenuItem} based on an existing one. This is useful
   * for creating a new item with the same properties as an existing one.
   *
   * @param item The existing {@code SideMenuItem} to copy.
   */
  public SideMenuItem(SideMenuItem item) {
    this.id = item.id;
    this.label = item.label;
    this.icon = item.icon;
    this.link = item.link;
    this.newTab = item.newTab;
    this.shortcut = item.shortcut;
    this.favorite = item.favorite;
    this.children = (item.children != null) ? item.children : Collections.emptyList();
  }

  /**
   * Gets the unique identifier for the item.
   *
   * @return The non-null unique identifier for the item.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the icon associated with the item.
   *
   * @return The icon string (URL, data URL, icon name, or pool:name), or null if not set.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * Gets the display text or label for the item.
   *
   * @return The non-null display label for the item.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Gets the URL or link associated with the item.
   *
   * @return The URL or link, or null if not set.
   */
  public String getLink() {
    return link;
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
   * Gets the shortcut key hint associated with the item.
   *
   * @return The shortcut key hint, or null if not set.
   */
  public String getShortcut() {
    return shortcut;
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
   * Gets the children items of the current item. Returns an empty list if there are no children.
   *
   * @return An unmodifiable list of child {@code SideMenuItem}s.
   */
  public List<SideMenuItem> getChildren() {
    return Collections.unmodifiableList(children);
  }

  /**
   * Sets the unique identifier for the item.
   *
   * @param id The non-null unique identifier to set.
   * @return This {@code SideMenuItem} instance for method chaining.
   * @throws NullPointerException if id is null.
   */
  public SideMenuItem setId(String id) {
    Objects.requireNonNull(id, ID_NULL_ERROR_MESSAGE);
    this.id = id;
    return this;
  }

  /**
   * Sets the icon associated with the item.
   *
   * @param icon The icon string (URL, data URL, icon name, or pool:name) to set. Can be null.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setIcon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Sets the display text or label for the item.
   *
   * @param label The non-null display label to set.
   * @return This {@code SideMenuItem} instance for method chaining.
   * @throws NullPointerException if label is null.
   */
  public SideMenuItem setLabel(String label) {
    Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
    this.label = label;
    return this;
  }

  /**
   * Sets the URL or link associated with the item.
   *
   * @param link The URL or link to set. Can be null.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setLink(String link) {
    this.link = link;
    return this;
  }

  /**
   * Sets whether the link should be opened in a new tab.
   *
   * @param newTab True if the link should be opened in a new tab, false otherwise.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setNewTab(boolean newTab) {
    this.newTab = newTab;
    return this;
  }

  /**
   * Sets the shortcut key hint associated with the item.
   *
   * @param shortcut The shortcut key hint to set. Can be null.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setShortcut(String shortcut) {
    this.shortcut = shortcut;
    return this;
  }

  /**
   * Sets whether the item is marked as a favorite.
   *
   * @param favorite True to mark as favorite, false otherwise.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setFavorite(boolean favorite) {
    this.favorite = favorite;
    return this;
  }

  /**
   * Sets the children items of the current item. The provided list will replace any existing
   * children.
   *
   * @param children The list of child {@code SideMenuItem}s to set. If null, children will be set
   *     to an empty list.
   * @return This {@code SideMenuItem} instance for method chaining.
   */
  public SideMenuItem setChildren(List<SideMenuItem> children) {
    Objects.requireNonNull(children, "Children list cannot be null");
    this.children = new ArrayList<>(children);
    return this;
  }

  /**
   * Creates a new builder instance to construct a {@code SideMenuItem}. This is the recommended way
   * to create items.
   *
   * @return An {@link IId} instance to start the building process.
   */
  public static IId builder() {
    return new Builder();
  }

  /** Enforces setting the ID first. */
  public interface IId {
    /**
     * Sets the mandatory unique identifier for the item.
     *
     * @param id The unique identifier. Cannot be null.
     * @return The next step in the builder process (setting the label).
     */
    ILabel id(String id);
  }

  /** Enforces setting the label second. */
  public interface ILabel {
    /**
     * Sets the mandatory display label for the item.
     *
     * @param label The text displayed to the user. Cannot be null.
     * @return The next step in the builder process (setting optional attributes).
     */
    IBuild label(String label);
  }

  /** Allows setting optional properties and building the item. */
  public interface IBuild extends IId, ILabel {
    /**
     * Sets the optional icon for the item.
     *
     * @param icon The icon string (URL, data URL, icon name, or pool:name).
     * @return This builder instance for further configuration.
     */
    IBuild icon(String icon);

    /**
     * Sets the optional URL or link for the item.
     *
     * @param link The URL or link.
     * @return This builder instance for further configuration.
     */
    IBuild link(String link);

    /**
     * Sets whether the link should open in a new tab. Defaults to false if not called.
     *
     * @param newTab True to open in a new tab, false otherwise.
     * @return This builder instance for further configuration.
     */
    IBuild newTab(boolean newTab);

    /**
     * Sets the optional shortcut key hint for the item.
     *
     * @param shortcut The shortcut hint text.
     * @return This builder instance for further configuration.
     */
    IBuild shortcut(String shortcut);

    /**
     * Sets whether the item is marked as a favorite. Defaults to false if not called.
     *
     * @param favorite True to mark as favorite, false otherwise.
     * @return This builder instance for further configuration.
     */
    IBuild favorite(boolean favorite);

    /**
     * Sets the child items for this item, creating a nested level. If not called, the item will
     * have no children.
     *
     * @param children A list of {@code SideMenuItem}s.
     * @return This builder instance for further configuration.
     */
    IBuild children(List<SideMenuItem> children);

    /**
     * Constructs the final {@code SideMenuItem} instance.
     *
     * @return The fully configured {@code SideMenuItem}.
     */
    SideMenuItem build();
  }

  /** Private implementation of the builder steps. */
  private static class Builder implements IId, ILabel, IBuild {
    private String id;
    private String label;
    private String icon;
    private String link;
    private boolean newTab = false;
    private String shortcut;
    private boolean favorite = false;
    private List<SideMenuItem> children;

    private Builder() {}

    @Override
    public ILabel id(String id) {
      Objects.requireNonNull(id, ID_NULL_ERROR_MESSAGE);
      this.id = id;
      return this;
    }

    @Override
    public IBuild label(String label) {
      Objects.requireNonNull(label, LABEL_NULL_ERROR_MESSAGE);
      this.label = label;
      return this;
    }

    @Override
    public IBuild icon(String icon) {
      this.icon = icon;
      return this;
    }

    @Override
    public IBuild link(String link) {
      this.link = link;
      return this;
    }

    @Override
    public IBuild newTab(boolean newTab) {
      this.newTab = newTab;
      return this;
    }

    @Override
    public IBuild shortcut(String shortcut) {
      this.shortcut = shortcut;
      return this;
    }

    @Override
    public IBuild favorite(boolean favorite) {
      this.favorite = favorite;
      return this;
    }

    @Override
    public IBuild children(List<SideMenuItem> children) {
      this.children = (children != null) ? new ArrayList<>(children) : null;
      return this;
    }

    @Override
    public SideMenuItem build() {
      return new SideMenuItem(this);
    }
  }
}
