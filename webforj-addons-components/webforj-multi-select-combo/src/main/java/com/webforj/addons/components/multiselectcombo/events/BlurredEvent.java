package com.webforj.addons.components.multiselectcombo.events;

import com.webforj.addons.components.multiselectcombo.MultiSelectCombo;
import com.webforj.component.element.annotation.EventName;
import com.webforj.component.event.ComponentEvent;

import java.util.Map;

/**
 * Event fired when multi-select combo input blurred
 *
 * @author @ElyasSalar
 * @since 24.20
 */
@EventName("dwc-blurred")
public class BlurredEvent extends ComponentEvent<MultiSelectCombo> {

	/**
	 * Creates a new event {@code dwc-blurred} event.
	 *
	 * @param component the component that fired the event
	 * @param payload the event map
	 */
	public BlurredEvent(MultiSelectCombo component, Map<String, Object> payload) {
		super(component, payload);
	}
}
