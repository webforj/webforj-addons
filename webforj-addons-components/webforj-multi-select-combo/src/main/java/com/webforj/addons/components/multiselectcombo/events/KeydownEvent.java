package com.webforj.addons.components.multiselectcombo.events;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.element.annotation.EventOptions;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when a key pressed down on the component
 *
 * @author @ElyasSalar
 * @since 24.20
 */
@EventName("dwc-keydown")
@EventOptions(data = {@EventOptions.EventData(key = "altKey", exp = "event.detail.altKey"),
		@EventOptions.EventData(key = "code", exp = "event.detail.code"),
		@EventOptions.EventData(key = "key", exp = "event.detail.key"),
		@EventOptions.EventData(key = "ctrlKey", exp = "event.detail.ctrlKey"),
		@EventOptions.EventData(key = "metaKey", exp = "event.detail.metaKey"),
		@EventOptions.EventData(key = "shiftKey", exp = "event.detail.shiftKey"),})
public class KeydownEvent extends ComponentEvent<MultiSelectCombo> {

	/**
	 * Creates a new event {@code dwc-keydown} event.
	 *
	 * @param component the component that fired the event
	 * @param payload the event map
	 */
	public KeydownEvent(MultiSelectCombo component, Map<String, Object> payload) {
		super(component, payload);
	}

	/**
	 * Checks if the Alt key was pressed during the keydown event.
	 *
	 * @return {@code true} if the Alt key was pressed, {@code false} otherwise
	 */
	public boolean isAltKey() {
		return (boolean) getEventMap().get("altKey");
	}

	/**
	 * Checks if the Ctrl key was pressed during the keydown event.
	 *
	 * @return {@code true} if the Ctrl key was pressed, {@code false} otherwise
	 */
	public boolean isCtrlKey() {
		return (boolean) getEventMap().get("ctrlKey");
	}

	/**
	 * Checks if the Meta key was pressed during the keydown event (often referred
	 * to as Command key on macOS).
	 *
	 * @return {@code true} if the Meta key was pressed, {@code false} otherwise
	 */
	public boolean isMetaKey() {
		return (boolean) getEventMap().get("metaKey");
	}

	/**
	 * Checks if the Shift key was pressed during the keydown event.
	 *
	 * @return {@code true} if the Shift key was pressed, {@code false} otherwise
	 */
	public boolean isShiftKey() {
		return (boolean) getEventMap().get("shiftKey");
	}

	/**
	 * Gets the key code of the pressed key according to the DOM Level 3
	 * KeyboardEvent standard.
	 *
	 * @see <a href=
	 *      "https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/code">MDN
	 *      documentation for key codes</a>
	 *
	 * @return the key code as a String
	 */
	public String getCode() {
		return (String) getEventMap().get("code");
	}

	/**
	 * Gets the physical key value as a string (e.g., "Enter", "ArrowDown").
	 *
	 * @return the physical key value as a String
	 */
	public String getKey() {
		return (String) getEventMap().get("key");
	}
}
