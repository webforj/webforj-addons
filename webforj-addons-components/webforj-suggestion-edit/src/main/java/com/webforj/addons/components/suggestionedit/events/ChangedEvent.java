package com.webforj.addons.components.suggestionedit.events;

import com.google.gson.Gson;
import com.webforj.addons.components.suggestionedit.Suggestion;
import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Event representing the change in value of the SuggestionEdit field to a suggestion.
 *
 * <p>The {@code ChangedEvent} is emitted when the value of the component changes to a suggestion.
 * This change can occur either by clicking on a suggestion in the dropdown or by pressing the Enter
 * key on a suggestion.
 *
 * @since 1.00
 * @author ElyasSalar
 */
@EventName("dwc-changed")
@EventOptions(data = {@EventOptions.EventData(key = "suggestion", exp = "event.detail")})
public class ChangedEvent extends ComponentEvent<SuggestionEdit> {

  /**
   * Creates a new {@code ChangedEvent} with the specified source and input value.
   *
   * @param component The component that fired the event.
   * @param payload The event map.
   */
  public ChangedEvent(SuggestionEdit component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the selected suggestion of the SuggestionEdit field.
   *
   * @return The new suggestion.
   */
  public Suggestion getSuggestion() {
    final var gson = new Gson();
    final var json = gson.toJson(this.getEventMap().get("suggestion"), HashMap.class);
    return gson.fromJson(json, Suggestion.class);
  }
}
