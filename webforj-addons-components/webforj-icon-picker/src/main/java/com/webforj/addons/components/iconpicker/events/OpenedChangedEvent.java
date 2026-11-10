package com.webforj.addons.components.iconpicker.events;

import com.webforj.addons.components.iconpicker.IconPicker;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;

/**
 * Emitted when the picker's dropdown opens or closes.
 *
 * @author ElyasSalar
 * @since 26.03
 */
@EventName("dwc-opened-changed")
@EventOptions(data = {@EventOptions.EventData(key = "opened", exp = "event.detail")})
public class OpenedChangedEvent extends ComponentEvent<IconPicker> {

  /**
   * Creates a new opened changed event.
   *
   * @param component the picker that fired the event
   * @param eventMap the event payload, keyed by {@code opened}
   */
  public OpenedChangedEvent(IconPicker component, Map<String, Object> eventMap) {
    super(component, eventMap);
  }

  /**
   * Checks whether the dropdown is open after the change.
   *
   * @return true when the dropdown opened, false when it closed or the payload has no state
   */
  public boolean isOpened() {
    return Boolean.parseBoolean(String.valueOf(getEventMap().get("opened")));
  }
}
