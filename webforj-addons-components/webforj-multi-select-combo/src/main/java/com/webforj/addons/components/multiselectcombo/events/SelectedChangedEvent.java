package com.webforj.addons.components.multiselectcombo.events;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Emitted when the selected options changed.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@EventName("dwc-selected-changed")
public class SelectedChangedEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param control the control
   * @param eventMap the event map
   */
  public SelectedChangedEvent(MultiSelectCombo control, Map<String, Object> eventMap) {
    super(control, eventMap);
  }
}
