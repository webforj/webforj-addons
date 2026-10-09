package com.webforj.addons.components.iconpicker;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A pool the app registered with DWC itself, announced to the {@link IconPicker} with the names of
 * its icons, in the shape the element reads. Instances are immutable and are created through {@link
 * IconPicker#addCustomPool(String, String, Collection)} and {@link IconPicker#addCustomPool(String,
 * String, Map)}.
 *
 * @author ElyasSalar
 * @since 26.03
 */
final class CustomPool {

  private final String name;
  private final String label;
  private final List<PoolIcon> icons;

  private CustomPool(String name, String label, List<PoolIcon> icons) {
    this.name = requireCustomPoolName(name);
    this.label = requireText(label, "The pool label");
    this.icons = List.copyOf(icons);
  }

  /**
   * Creates a pool whose icons have no tags.
   *
   * @param name the name the app registered the pool under in DWC
   * @param label the text on the pool's tab
   * @param names the icon names, in tab order
   * @return a pool holding a copy of the names, each icon with no tags
   * @throws NullPointerException if any argument or icon name is null
   * @throws IllegalArgumentException if the name is not a valid pool name, or the label or an icon
   *     name is blank
   */
  static CustomPool of(String name, String label, Collection<String> names) {
    Objects.requireNonNull(names, "The icon names cannot be null");

    final List<PoolIcon> icons =
        names.stream().map(iconName -> new PoolIcon(iconName, List.of())).toList();
    return new CustomPool(name, label, icons);
  }

  /**
   * Creates a pool whose icons carry tags for search.
   *
   * @param name the name the app registered the pool under in DWC
   * @param label the text on the pool's tab
   * @param namesWithTags the icon names mapped to the tags search matches them by, in the map's
   *     iteration order
   * @return a pool holding a copy of the names and tags
   * @throws NullPointerException if any argument, icon name, tag collection or tag is null
   * @throws IllegalArgumentException if the name is not a valid pool name, or the label or an icon
   *     name is blank
   */
  static CustomPool withTags(
      String name, String label, Map<String, ? extends Collection<String>> namesWithTags) {
    Objects.requireNonNull(namesWithTags, "The icon names with tags cannot be null");

    final List<PoolIcon> icons =
        namesWithTags.entrySet().stream()
            .map(entry -> new PoolIcon(entry.getKey(), entry.getValue()))
            .toList();
    return new CustomPool(name, label, icons);
  }

  /**
   * Checks that a pool name can be given to the picker.
   *
   * @param name the name to check
   * @return the same name, so the check can be used inside an expression
   * @throws NullPointerException if the name is null
   * @throws IllegalArgumentException if the name is blank, starts or ends with white space, or
   *     contains a comma or a colon
   */
  static String requirePoolName(String name) {
    requireText(name, "The pool name");
    if (!name.equals(name.strip())) {
      throw new IllegalArgumentException(
          "The pool name '" + name + "' cannot start or end with white space");
    }
    if (name.contains(IconPicker.POOL_LIST_SEPARATOR)) {
      throw new IllegalArgumentException("The pool name '" + name + "' cannot contain a comma");
    }
    if (name.contains(IconPicker.NAME_SEPARATOR)) {
      throw new IllegalArgumentException("The pool name '" + name + "' cannot contain a colon");
    }

    return name;
  }

  private static String requireCustomPoolName(String name) {
    requirePoolName(name);
    if (IconPicker.BUILT_IN_POOLS.contains(name)) {
      throw new IllegalArgumentException(
          "The pool name '" + name + "' is taken by a built-in pool");
    }

    return name;
  }

  /**
   * Gets the DWC pool name, which must match a pool registered in the page.
   *
   * @return the name, never blank and never containing a comma
   */
  String getName() {
    return name;
  }

  /**
   * Gets the label of the pool's tab.
   *
   * @return the label, never blank
   */
  String getLabel() {
    return label;
  }

  /**
   * Gets the pool's icons in tab order.
   *
   * @return an unmodifiable list of the icons
   */
  List<PoolIcon> getIcons() {
    return icons;
  }

  private static String requireText(String text, String subject) {
    Objects.requireNonNull(text, subject + " cannot be null");
    if (text.isBlank()) {
      throw new IllegalArgumentException(subject + " cannot be blank, but is '" + text + "'");
    }

    return text;
  }

  /**
   * One icon of a {@link CustomPool}: its DWC name and the tags search matches it by.
   *
   * @author ElyasSalar
   * @since 26.03
   */
  static final class PoolIcon {

    private final String name;
    private final List<String> tags;

    private PoolIcon(String name, Collection<String> tags) {
      this.name = requireText(name, "The icon name");
      this.tags = List.copyOf(Objects.requireNonNull(tags, "The tags of '" + name + "' are null"));
    }

    /**
     * Gets the icon's DWC name.
     *
     * @return the name, never blank
     */
    String getName() {
      return name;
    }

    /**
     * Gets the tags search matches the icon by.
     *
     * @return an unmodifiable list of the tags, empty when the icon has none
     */
    List<String> getTags() {
      return tags;
    }
  }
}
