package com.webforj.addons.components.multiselectcombo.event;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Emitted when the input changed.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@EventName("dwc-input")
public class InputEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param control the control
   * @param eventMap the event map
   */
  public InputEvent(MultiSelectCombo control, Map<String, Object> eventMap) {
    super(control, eventMap);
  }
}
