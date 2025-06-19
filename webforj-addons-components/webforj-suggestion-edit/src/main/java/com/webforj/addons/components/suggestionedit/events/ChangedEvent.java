package com.webforj.addons.components.suggestionedit.events;

import com.google.gson.Gson;
import com.webforj.addons.components.suggestionedit.SuggestionEdit;
import com.webforj.addons.components.suggestionedit.SuggestionItem;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
   * <p>This method returns an {@link Optional} containing the suggestion if available, or an empty
   * {@code Optional} if the event detail is not available or if the client component did not
   * provide a suggestion. This can occur in certain edge cases or when the event is triggered
   * without a valid suggestion context.
   *
   * @return An {@code Optional} containing the new suggestion, or empty if no suggestion is
   *     available.
   */
  public Optional<SuggestionItem> getSuggestion() {
    final Object suggestionData = this.getEventMap().get("suggestion");
    if (suggestionData == null) {
      return Optional.empty();
    }

    final var gson = new Gson();
    final var json = gson.toJson(suggestionData, HashMap.class);
    final SuggestionItem suggestion = gson.fromJson(json, SuggestionItem.class);
    return Optional.ofNullable(suggestion);
  }
}
