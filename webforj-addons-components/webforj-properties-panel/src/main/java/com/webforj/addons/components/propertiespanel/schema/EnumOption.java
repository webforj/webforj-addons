package com.webforj.addons.components.propertiespanel.schema;

/**
 * Represents an option for an enum property in a properties panel.
 *
 * @author ElyasSalar
 * @since 1.00
 */
public class EnumOption {

	/**
	 * The name serves as a unique identifier for the option within its context.
	 */
	private String name;

	/**
	 * The label provides a human-readable representation of the option, typically
	 * displayed to users within a user interface.
	 */
	private String label;

	/**
	 * The value represents the actual data associated with the option. It is used
	 * programmatically to perform operations or comparisons based on the selected
	 * option.
	 */
	private String value;

	/**
	 * Constructs a new {@code EnumOption} with the specified name, label, and
	 * value.
	 *
	 * @param name The name of the option.
	 * @param label The label of the option.
	 * @param value The value of the option.
	 */
	public EnumOption(String name, String label, String value) {
		this.name = name;
		this.label = label;
		this.value = value;
	}

	/**
	 * Retrieves the name of the option.
	 *
	 * @return The name of the option.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the option.
	 *
	 * @param name The name of the option.
	 */
	public EnumOption setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Retrieves the label of the option.
	 *
	 * @return The label of the option.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label of the option.
	 *
	 * @param label The label of the option.
	 */
	public EnumOption setLabel(String label) {
		this.label = label;
		return this;
	}

	/**
	 * Retrieves the value of the option.
	 *
	 * @return The value of the option.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the option.
	 *
	 * @param value The value of the option.
	 */
	public EnumOption setValue(String value) {
		this.value = value;
		return this;
	}
}
