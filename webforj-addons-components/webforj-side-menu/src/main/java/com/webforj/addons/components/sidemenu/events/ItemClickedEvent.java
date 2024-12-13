package com.webforj.addons.components.sidemenu.events;

import com.google.gson.Gson;
import com.webforj.addons.components.sidemenu.Item;
import com.webforj.addons.components.sidemenu.SideMenu;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when an item is clicked.
 *
 * @since 24.20
 * @author @ElyasSalar
 */
@EventName("dwc-item-clicked")
@EventOptions(data = {@EventOptions.EventData(key = "item", exp = "event.detail")})
public class ItemClickedEvent extends ComponentEvent<SideMenu> {

	/**
	 * Creates a new event {@code dwc-item-clicked} event.
	 *
	 * @param component the component that fired the event
	 * @param payload the event map
	 */
	public ItemClickedEvent(SideMenu component, Map<String, Object> payload) {
		super(component, payload);
	}

	/**
	 * Gets the clicked item from the side menu.
	 *
	 * @return The clicked item.
	 */
	public Item getItem() {
		final var gson = new Gson();
		final var itemJson = gson.toJson(this.getEventMap().get("item"));
		return gson.fromJson(itemJson, Item.class);
	}
}
