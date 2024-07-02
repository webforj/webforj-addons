package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Emitted when the input is validated.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@EventName("dwc-validated")
@EventOptions(data = {@EventOptions.EventData(key = "invalid", exp = "event.detail"),})
public class ValidatedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new event.
   *
   * @param control the control
   * @param eventMap the event map
   */
  public ValidatedEvent(SuggestionEdit control, Map<String, Object> eventMap) {
    super(control, eventMap);
  }

  /**
   * Gets the result of the component validation.
   *
   * @return The invalidity value.
   */
  public boolean isInvalid() {
    return (boolean) this.getEventMap().get("invalid");
  }
}
