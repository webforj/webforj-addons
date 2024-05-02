package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;


/**
 * Event fired when suggestion input focused
 *
 * @author @ElyasSalar
 */
@EventName("dwc-focused")
public class FocusedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new event `dwc-focused` event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public FocusedEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }
}
