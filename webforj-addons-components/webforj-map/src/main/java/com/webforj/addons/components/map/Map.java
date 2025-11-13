package com.webforj.addons.components.map;

import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.element.Element;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasSize;
import com.webforj.concern.HasStyle;

/**
 * Map component integrating OpenLayers with webforJ, providing coordinate transformations
 * (EPSG:4326 from/to EPSG:3857) and programmatic view manipulation.
 *
 * <p>This component wraps a custom HTML element {@code <w-map>} that manages its own OpenLayers
 * instance.
 *
 * @since 25.03
 */
@NodeName("w-map")
@JavaScript(
    value = "ws://w-map.js",
    attributes = {@Attribute(name = "type", value = "module")})
public final class Map extends ElementComposite
    implements HasClassName<Map>, HasSize<Map>, HasStyle<Map> {

  private final Element self = getBoundComponent();

  private final PropertyDescriptor<MapView> viewProperty =
      PropertyDescriptor.property("view", null);

  /** Creates a new Map component with default settings. */
  public Map() {
    self.setStyle("width", "100%");
    self.setStyle("height", "100%");
    self.setStyle("display", "block");
  }

  /**
   * Gets the view configuration.
   *
   * @return the view configuration, or null if not set
   */
  public MapView getView() {
    return get(viewProperty, true);
  }

  /**
   * Sets the view configuration.
   *
   * @param view the view configuration
   * @return this Map instance for method chaining
   */
  public Map setView(MapView view) {
    set(viewProperty, view);
    return this;
  }
}
