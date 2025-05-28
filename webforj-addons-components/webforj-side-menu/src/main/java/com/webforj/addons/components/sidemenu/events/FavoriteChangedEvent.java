package com.webforj.addons.components.sidemenu.events;

import com.google.gson.Gson;
import com.webforj.addons.components.sidemenu.SideMenu;
import com.webforj.addons.components.sidemenu.SideMenuItem;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;
import java.util.Map;

/**
 * Emitted when an item's favorite status is changed.
 *
 * @author ElyasSalar
 * @since 25.02
 */
@EventName("dwc-favorite-changed")
@EventOptions(data = {@EventOptions.EventData(key = "item", exp = "event.detail")})
public class FavoriteChangedEvent extends ComponentEvent<SideMenu> {

  /**
   * Creates a new favorite changed event.
   *
   * @param component the SideMenu component
   * @param payload the event map
   */
  public FavoriteChangedEvent(SideMenu component, Map<String, Object> payload) {
    super(component, payload);
  }

  /**
   * Gets the item whose favorite status was changed.
   *
   * @return the item
   */
  public SideMenuItem getItem() {
    final var gson = new Gson();
    final var itemJson = gson.toJson(this.getEventMap().get("item"));
    return gson.fromJson(itemJson, SideMenuItem.class);
  }
}
