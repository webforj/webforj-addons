package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.annotations.JsonAdapter;

/**
 * Base class representing a schema for a property in a properties panel. It
 * defines common attributes shared by all property schemas.
 *
 * @author ElyasSalar
 * @since 1.00
 */
@JsonAdapter(BaseSchemaAdapter.class)
public abstract class BaseSchema<T extends BaseSchema<T>> {

	/**
	 * The unique identifier or key for the property.
	 */
	private String name;

	/**
	 * The display name for the property.
	 */
	private String label;

	/**
	 * The data type of the property, the possible values are:
	 * <ol>
	 * <li>string</li>
	 * <li>boolean</li>
	 * <li>number</li>
	 * <li>enum</li>
	 * </ol>
	 */
	private String type;

	/**
	 * Indicating whether the property is read-only.
	 */
	private boolean readonly;

	/**
	 * Indicating whether the property is disabled or not interactive.
	 */
	private boolean disabled;

	/**
	 * Enforces the extending schemas to override this and return their instance,
	 * this will allow chaining this {@code BaseScheme} methods to the instance,
	 * scheme class that extends this.
	 */
	protected abstract T getThis();

	/**
	 * Retrieves the unique identifier or key for the property.
	 *
	 * @return The name of the property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the unique identifier or key for the property.
	 *
	 * @param name The name to set for the property.
	 */
	public T setName(String name) {
		this.name = name;
		return this.getThis();
	}

	/**
	 * Retrieves the human-readable label or display name for the property.
	 *
	 * @return The label of the property.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the human-readable label or display name for the property.
	 *
	 * @param label The label to set for the property.
	 */
	public T setLabel(String label) {
		this.label = label;
		return this.getThis();
	}

	/**
	 * Retrieves the data type of the property.
	 *
	 * @return The data type of the property, the possible values are:
	 *         <ol>
	 *         <li>string</li>
	 *         <li>boolean</li>
	 *         <li>number</li>
	 *         <li>enum</li>
	 *         </ol>
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the data type of the property.
	 *
	 * @param type The data type to set for the property.
	 */
	protected T setType(String type) {
		this.type = type;
		return this.getThis();
	}

	/**
	 * Checks if the property is read-only.
	 *
	 * @return True if the property is read-only, otherwise false.
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * Sets whether the property is read-only.
	 *
	 * @param readonly True if the property is read-only, otherwise false.
	 */
	public T setReadonly(boolean readonly) {
		this.readonly = readonly;
		return this.getThis();
	}

	/**
	 * Checks if the property is disabled.
	 *
	 * @return True if the property is disabled, otherwise false.
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets whether the property is disabled.
	 *
	 * @param disabled True if the property is disabled, otherwise false.
	 */
	public T setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this.getThis();
	}
}
