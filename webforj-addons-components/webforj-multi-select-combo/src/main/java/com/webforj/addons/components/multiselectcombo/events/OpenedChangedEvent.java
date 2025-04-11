package com.webforj.addons.components.multiselectcombo.events;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;

/**
 * Emitted when the open state of the dropdown changes.
 *
 * @author ElyasSalar
 */
@EventName("dwc-opened-changed")
@EventOptions(data = {@EventOptions.EventData(key = "opened", exp = "event.detail")})
public class OpenedChangedEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param component the component
   * @param eventMap the event map
   */
  public OpenedChangedEvent(MultiSelectCombo component, Map<String, Object> eventMap) {
    super(component, eventMap);
  }

  /**
   * Retrieves the value associated with the event.
   *
   * @return A boolean value indicating whether the dropdown is open or closed
   */
  public boolean isOpened() {
    return (boolean) this.getEventMap().get("opened");
  }
}
