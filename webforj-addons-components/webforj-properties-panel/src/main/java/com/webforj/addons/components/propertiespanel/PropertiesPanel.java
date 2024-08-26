package com.webforj.addons.components.propertiespanel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.PendingResult;
import com.webforj.addons.components.propertiespanel.events.ChangedEvent;
import com.webforj.addons.components.propertiespanel.schema.*;
import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.concern.*;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code PropertiesPanel} component provides methods for manipulating a UI
 * properties panel control. This component allows developers to manage and
 * interact with properties within a UI context. It enables the dynamic
 * generation of key-value pairs based on the provided schema structure.
 * <p>
 * The {@code PropertiesPanel} component is offers functionality for handling
 * various types of properties including {@code string}, {@code number},
 * {@code boolean}, and {@code enum}. Developers can define schemas to structure
 * and organize properties effectively, facilitating flexible and adaptable
 * property management scenarios.
 * </p>
 *
 * @author ElyasSalar
 * @since 1.00
 */
@NodeName("dwc-properties-panel")
@JavaScript(value = "https://d3hx2iico687v8.cloudfront.net/1.0.0/dwc-addons.esm.js", top = true, attributes = {
		@Attribute(name = "type", value = "module")})
public class PropertiesPanel extends ElementComposite {

	/**
	 * Property that must be provided to the component as a list of
	 * {@link SchemaGroup} which will be represented as a group of fields property.
	 */
	private final PropertyDescriptor<List<SchemaGroup>> schemaProp = PropertyDescriptor
			.property("schema", new ArrayList<>());

	/**
	 * Adds a listener for the changed event, which is triggered when the value of a
	 * property changes.
	 *
	 * @param listener The event listener to add.
	 * @return A registration object that can be used to unregister the listener if
	 *         needed.
	 */
	public ListenerRegistration<ChangedEvent> addChangedListener(
			EventListener<ChangedEvent> listener) {
		return this.addEventListener(ChangedEvent.class, listener);
	}

	/**
	 * Retrieves all the properties of the component with their assigned value.
	 * Initially, when the {@code scheme} property is passed, then this method will
	 * have all the properties defined in the {@code scheme} and with their default
	 * values or if changed, with the latest values.
	 *
	 * @return The a {@link PendingResult} that will have the properties.
	 */
	public PendingResult<HashMap<String, Object>> getProperties() {
		return this.getElement().callJsFunctionAsync("getProperties").thenApply(propertiesJson -> {
			Type type = new TypeToken<HashMap<String, Object>>() {
			}.getType();
			return new Gson().fromJson((String) propertiesJson, type);
		});
	}

	/**
	 * Retrieves the schema associated with the component.
	 *
	 * @return The schema associated with the component.
	 */
	public List<SchemaGroup> getSchema() {
		return get(schemaProp);
	}

	/**
	 * Sets the schema associated with the component.
	 *
	 * @param schema The schema to set for the component.
	 */
	public PropertiesPanel setSchema(List<SchemaGroup> schema) {
		set(schemaProp, schema);
		return this;
	}
}
