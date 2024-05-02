package com.webforj.addons.components.suggestionedit.events;

import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;


/**
 * Event fired when suggestion input value modified
 *
 * @author @ElyasSalar
 */
@EventName("dwc-modified")
@EventOptions(data = {@EventOptions.EventData(key = "detail", exp = "event.detail"),})
public class ModifiedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new event `dwc-modified` event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public ModifiedEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the detail property of the event
   *
   * @return the string value of suggestion input
   */
  public String getDetail() {
    return (String) this.getEventMap().get("detail");
  }
}
