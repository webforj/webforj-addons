package com.webforj.addons.components.iconpicker.events;

import com.webforj.addons.components.iconpicker.IconPicker;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import com.webforj.component.icons.Icon;
import java.util.Map;
import java.util.Objects;

/**
 * Emitted when the user picks an icon or removes the current one. Browsing the dropdown never emits
 * it, and neither does setting the value from the server.
 *
 * @author ElyasSalar
 * @since 26.03
 */
@EventName("dwc-icon-changed")
@EventOptions(
    data = {
      @EventOptions.EventData(key = "pool", exp = "event.detail.pool"),
      @EventOptions.EventData(key = "name", exp = "event.detail.name")
    })
public class IconChangedEvent extends ComponentEvent<IconPicker> {

  /**
   * Creates a new icon changed event.
   *
   * @param component the picker that fired the event
   * @param eventMap the event payload, keyed by {@code pool} and {@code name}
   */
  public IconChangedEvent(IconPicker component, Map<String, Object> eventMap) {
    super(component, eventMap);
  }

  /**
   * Gets the icon the user picked. Each call creates a new {@link Icon}.
   *
   * @return the picked icon, or null when the user removed the icon
   */
  public Icon getValue() {
    final String name = Objects.toString(getEventMap().get("name"), "");
    if (name.isEmpty()) {
      return null;
    }

    return new Icon(name, Objects.toString(getEventMap().get("pool"), ""));
  }
}
