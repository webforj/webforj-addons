package com.webforj.addons.components.multiselectcombo.events;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.addons.components.multiselectcombo.MultiSelectComboItem;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.List;
import java.util.Map;

/**
 * Emitted when the selected options changed.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@EventName("dwc-selected-changed")
@EventOptions(data = {@EventOptions.EventData(key = "selected", exp = "event.detail")})
public class SelectedChangedEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param component the component
   * @param eventMap the event map
   */
  public SelectedChangedEvent(MultiSelectCombo component, Map<String, Object> eventMap) {
    super(component, eventMap);
  }

  /** Retrieves a list of selected items from the component's event map. */
  public List<MultiSelectComboItem> getSelectedItems() {
    final var gson = new Gson();
    final var type = new TypeToken<List<MultiSelectComboItem>>() {}.getType();
    final var json = gson.toJson(this.getEventMap().get("selected"), List.class);
    return gson.fromJson(json, type);
  }
}
