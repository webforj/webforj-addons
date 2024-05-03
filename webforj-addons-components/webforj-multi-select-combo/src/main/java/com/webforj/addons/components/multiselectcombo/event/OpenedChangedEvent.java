package com.webforj.addons.components.multiselectcombo.event;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Emitted when the open state of the dropdown changes.
 *
 * @author ElyasSalar
 */
@EventName("dwc-opened-changed")
public class OpenedChangedEvent extends ComponentEvent<MultiSelectCombo> {

  /**
   * Creates a new event.
   *
   * @param control the control
   * @param eventMap the event map
   */
  public OpenedChangedEvent(MultiSelectCombo control,
                            Map<String, Object> eventMap) {
    super(control, eventMap);
  }
}
