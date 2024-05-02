package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;


/**
 * Event fired when suggestion input blurred
 *
 * @author @ElyasSalar
 */
@EventName("dwc-blurred")
public class BlurredEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new event `dwc-blurred` event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public BlurredEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }
}
