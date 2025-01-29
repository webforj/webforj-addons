package com.webforj.addons.components.propertiespanel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.PendingResult;
import com.webforj.addons.components.propertiespanel.events.ChangedEvent;
import com.webforj.addons.components.propertiespanel.schema.*;
import com.webforj.addons.constant.GlobalConstants;
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
@JavaScript(value = GlobalConstants.DWC_CDN, top = true, attributes = {
		@Attribute(name = "type", value = "module")})
public class PropertiesPanel extends ElementComposite
		implements
			HasClassName<PropertiesPanel>,
			HasProperty<PropertiesPanel>,
			HasStyle<PropertiesPanel>,
			HasJsExecution {

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
	 * Updates the value of a property based on its name and applies the change to
	 * the corresponding UI element.
	 * <p>
	 * This method ensures that the provided value is assigned to the specified
	 * property, triggering any necessary updates in the UI. The expected value type
	 * depends on the schema definition of the property:
	 * <ul>
	 * <li><b>String schema type:</b> Accepts values of type {@code String},
	 * {@code Number}, or {@code Boolean}</li>
	 * <li><b>Number schema type:</b> Accepts values of type {@code String} or
	 * {@code Number}</li>
	 * <li><b>Boolean schema type:</b> Accepts values of type {@code Boolean}.</li>
	 * <li><b>Enum schema type:</b> Accepts values of type {@code Number}
	 * (representing an index in an enumeration).</li>
	 * </ul>
	 * If the provided value does not match the expected type for the property, the
	 * client-side implementation rejects the update and log an error.
	 * </p>
	 *
	 * @param name The name of the property to update.
	 * @param value The new value to assign to the property. The type must adhere to
	 *            the expected schema constraints.
	 * @return A {@link PendingResult} that completes when the property update has
	 *         been processed.
	 */
	public PendingResult<Void> setSchemaProperty(String name, Object value) {
		return this.getElement().callJsFunctionAsync("setProperty", name, value)
				.thenApply(result -> null);
	}

	/**
	 * Expands a specific header based on its index within the schema.
	 *
	 * @param index The index of the header to expand.
	 * @return A {@link PendingResult} that completes when the expansion is applied.
	 */
	public PendingResult<Void> expandByIndex(int index) {
		return this.getElement().callJsFunctionAsync("expandByIndex", index)
				.thenApply(result -> null);
	}

	/**
	 * Collapses a specific header based on its index within the schema.
	 *
	 * @param index The index of the header to collapse.
	 * @return A {@link PendingResult} that completes when the collapse action is
	 *         applied.
	 */
	public PendingResult<Void> collapseByIndex(int index) {
		return this.getElement().callJsFunctionAsync("collapseByIndex", index)
				.thenApply(result -> null);
	}

	/**
	 * Expands all headers in the properties panel.
	 *
	 * @return A {@link PendingResult} that completes when all headers have been
	 *         expanded.
	 */
	public PendingResult<Void> expandAll() {
		return this.getElement().callJsFunctionAsync("expandAll").thenApply(result -> null);
	}

	/**
	 * Collapses all headers in the properties panel.
	 *
	 * @return A {@link PendingResult} that completes when all headers have been
	 *         collapsed.
	 */
	public PendingResult<Void> collapseAll() {
		return this.getElement().callJsFunctionAsync("collapseAll").thenApply(result -> null);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesPanel addClassName(String... classNames) {
		this.getBoundComponent().addClassName(classNames);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesPanel removeClassName(String... classNames) {
		this.getBoundComponent().removeClassName(classNames);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object executeJs(String js) {
		return this.getBoundComponent().executeJs(js);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PendingResult<Object> executeJsAsync(String js) {
		return this.getBoundComponent().executeJsAsync(js);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeJsVoidAsync(String js) {
		this.getBoundComponent().executeJsVoidAsync(js);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStyle(String property) {
		return this.getBoundComponent().getStyle(property);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getComputedStyle(String property) {
		return this.getBoundComponent().getComputedStyle(property);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesPanel setStyle(String property, String value) {
		this.getBoundComponent().setStyle(property, value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesPanel removeStyle(String property) {
		this.getBoundComponent().removeStyle(property);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesPanel setProperty(String property, Object value) {
		this.getBoundComponent().setProperty(property, value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <V> V getProperty(String property, Type typeOfV) {
		return this.getBoundComponent().getProperty(property, typeOfV);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <V> V getProperty(String property, Class<V> classOfV) {
		return this.getBoundComponent().getProperty(property, classOfV);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getProperty(String property) {
		return this.getBoundComponent().getProperty(property);
	}
}
