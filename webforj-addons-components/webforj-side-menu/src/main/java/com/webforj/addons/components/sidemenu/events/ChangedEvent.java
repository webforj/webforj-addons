package com.webforj.addons.components.sidemenu.events;

import com.webforj.addons.components.sidemenu.Item;
import com.webforj.addons.components.sidemenu.SideMenu;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when item selection changed.
 *
 * @since 1.00
 * @author @ElyasSalar
 */
@EventName("dwc-changed")
@EventOptions(data = {
  @EventOptions.EventData(key = "selected", exp = "event.detail.selected"),
  @EventOptions.EventData(key = "deselected", exp = "event.detail.deselected"),
})
public class ChangedEvent extends ComponentEvent<SideMenu> {

  /**
   * Creates a new event {@code dwc-changed} event.
   *
   * @param component the component that fired the event
   * @param payload the event map
   */
  public ChangedEvent(SideMenu component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the newly selected item from the side menu
   *
   * @return The new selected item.
   */
  public Item getSelectedItem() {
    return (Item) this.getEventMap().get("selected");
  }

  /**
   * Gets the deselected item from the side menu.
   *
   * @return The deselected item.
   */
  public Item getDeselectedItem() {
    return (Item) this.getEventMap().get("deselected");
  }
}
