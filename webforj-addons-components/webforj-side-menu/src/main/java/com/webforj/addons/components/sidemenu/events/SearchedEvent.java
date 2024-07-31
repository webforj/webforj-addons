package com.webforj.addons.components.sidemenu.events;

import com.webforj.addons.components.sidemenu.Item;
import com.webforj.addons.components.sidemenu.SideMenu;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when a new search made on the search input from {@link SideMenu}.
 *
 * @since 1.00
 * @author @ElyasSalar
 */
@EventName("dwc-searched")
@EventOptions(data = {@EventOptions.EventData(key = "detail", exp = "event.detail"),})
public class SearchedEvent extends ComponentEvent<SideMenu> {

	/**
	 * Creates a new event {@code dwc-searched} event.
	 *
	 * @param component the component that fired the event
	 * @param payload the event map
	 */
	public SearchedEvent(SideMenu component, Map<String, Object> payload) {
		super(component, payload);
	}

	/**
	 * Gets the newly selected item from the side menu
	 *
	 * @return The new selected item.
	 */
	public Item getSearched() {
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
