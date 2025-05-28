package com.webforj.addons.components.sidemenu.events;

import com.google.gson.Gson;
import com.webforj.addons.components.sidemenu.SideMenu;
import com.webforj.addons.components.sidemenu.SideMenuItem;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;
import java.util.Optional;

/**
 * Event fired when item selection changed.
 *
 * @since 1.00
 * @author @ElyasSalar
 */
@EventName("dwc-changed")
@EventOptions(
    data = {
      @EventOptions.EventData(key = "selected", exp = "event.detail.selected"),
      @EventOptions.EventData(key = "deselected", exp = "event.detail.deselected")
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
   * Gets the newly selected item from the side menu.
   *
   * @return The new selected item.
   */
  public SideMenuItem getSelectedItem() {
    final var gson = new Gson();
    final var itemJson = gson.toJson(this.getEventMap().get("selected"));
    return gson.fromJson(itemJson, SideMenuItem.class);
  }

  /**
   * Gets the deselected item from the side menu.
   *
   * <p>In the context of this application, a "deselected item" may not always exist. For example,
   * during the first selection, there is no prior item to deselect. To accommodate this, the method
   * returns an {@code Optional<SideMenuItem>} instead of {@code null}. This makes it explicit to
   * the caller that the absence of a value is a normal, expected state and encourages proper
   * handling of such cases.
   *
   * @return An {@code Optional} containing the deselected item if one exists, or an empty {@code
   *     Optional} otherwise.
   */
  public Optional<SideMenuItem> getDeselectedItem() {
    final var item = this.getEventMap().get("deselected");
    if (item == null) {
      return Optional.empty();
    }
    final var gson = new Gson();
    final var itemJson = gson.toJson(item);
    final var deselectedItem = gson.fromJson(itemJson, SideMenuItem.class);
    return Optional.of(deselectedItem);
  }
}
