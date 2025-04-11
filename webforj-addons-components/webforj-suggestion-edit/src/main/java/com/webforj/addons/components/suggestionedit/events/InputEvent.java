package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;

/**
 * Event representing user input in the SuggestionEdit field.
 *
 * <p>The {@code InputEvent} is emitted whenever the user types in the SuggestionEdit field. This
 * event is fired on any input change, regardless of whether the input matches a suggestion or not.
 *
 * @since 1.00
 * @author ElyasSalar
 */
@EventName("dwc-input")
@EventOptions(data = {@EventOptions.EventData(key = "value", exp = "event.detail")})
public class InputEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new {@code InputEvent} with the specified source and input value.
   *
   * @param component The component that fired the event.
   * @param payload The event map.
   */
  public InputEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the current input value of the SuggestionEdit field.
   *
   * @return The current input value.
   */
  public String getValue() {
    return (String) this.getEventMap().get("value");
  }
}
