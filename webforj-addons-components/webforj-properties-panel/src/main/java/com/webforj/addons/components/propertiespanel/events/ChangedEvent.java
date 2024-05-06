package com.webforj.addons.components.propertiespanel.events;

import com.webforj.addons.components.propertiespanel.PropertiesPanel;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when value of one of the properties changed.
 *
 * @author @ElyasSalar
 * @since 1.00
 */
@EventName("dwc-changed")
@EventOptions(data = {
  @EventOptions.EventData(key = "name", exp = "event.detail.name"),
  @EventOptions.EventData(key = "value", exp = "event.detail.value"),}
)
public class ChangedEvent extends ComponentEvent<PropertiesPanel> {

  /**
   * Creates a new event {@code dwc-changed} event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public ChangedEvent(PropertiesPanel component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the name of the corresponding property name of the changed value
   *
   * @return The property name.
   */
  public String getName() {
    return (String) this.getEventMap().get("name");
  }

  /**
   * Gets the changed value.
   *
   * @return The changed value.
   */
  public Object getValue() {
    return this.getEventMap().get("value");
  }
}
