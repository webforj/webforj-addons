package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when the suggestion dropdown is closed.
 *
 * @since 1.00
 */
@EventName("dwc-closed")
public class ClosedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new {@code dwc-closed} event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public ClosedEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }
}
