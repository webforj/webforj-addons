package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when the suggestion dropdown is opened.
 *
 * @author @ElyasSalar
 * @since 24.12
 */
@EventName("dwc-opened")
public class OpenedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new {@code dwc-opened} event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public OpenedEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }
}
