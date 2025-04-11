package com.webforj.addons.components.multiselectcombo.events;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;

/**
 * Emitted when the input changed.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@EventName("dwc-input")
@EventOptions(data = {@EventOptions.EventData(key = "value", exp = "event.detail")})
public class InputEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param component the component
   * @param eventMap the event map
   */
  public InputEvent(MultiSelectCombo component, Map<String, Object> eventMap) {
    super(component, eventMap);
  }

  /**
   * Retrieves the input value associated with this event.
   *
   * @return A String representing the current input value.
   */
  public String getValue() {
    return (String) this.getEventMap().get("value");
  }
}
