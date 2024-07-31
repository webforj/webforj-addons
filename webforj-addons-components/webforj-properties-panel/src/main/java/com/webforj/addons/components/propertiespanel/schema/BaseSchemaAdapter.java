package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * A custom Gson adapter for serializing and deserializing instances of
 * {@link BaseSchema}. This adapter handles the polymorphic nature of
 * {@link BaseSchema} by determining the correct concrete subclass based on the
 * {@code type} field present in the JSON representation.
 *
 * <p>
 * The supported schema types are:
 * <ul>
 * <li>string - maps to {@link StringSchema}</li>
 * <li>number - maps to {@link NumberSchema}</li>
 * <li>enum - maps to {@link EnumSchema}</li>
 * <li>boolean - maps to {@link BooleanSchema}</li>
 * </ul>
 * </p>
 */
public class BaseSchemaAdapter
		implements
			JsonDeserializer<BaseSchema<?>>,
			JsonSerializer<BaseSchema<?>> {

	/**
	 * Deserializes the JSON element into an appropriate subclass of
	 * {@link BaseSchema} based on the {@code type} field.
	 *
	 * @param json the JSON element being deserialized
	 * @param typeOfT the type of the Object to deserialize to
	 * @param context the context for deserialization
	 * @return the deserialized {@link BaseSchema} object
	 * @throws JsonParseException if the {@code type} field is unknown or missing
	 */
	@Override
	public BaseSchema<?> deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("type").getAsString();
		return switch (type) {
			case "string" -> context.deserialize(jsonObject, StringSchema.class);
			case "number" -> context.deserialize(jsonObject, NumberSchema.class);
			case "enum" -> context.deserialize(jsonObject, EnumSchema.class);
			case "boolean" -> context.deserialize(jsonObject, BooleanSchema.class);
			default -> throw new JsonParseException("Unknown type: " + type);
		};
	}

	/**
	 * Serializes the {@link BaseSchema} object into its JSON representation. The
	 * correct subclass is determined by the {@link BaseSchema#getType()} method.
	 *
	 * @param src the object that needs to be serialized
	 * @param typeOfSrc the actual type (fully genericized version) of the source
	 *            object
	 * @param context the context for serialization
	 * @return the serialized JSON element
	 * @throws JsonParseException if the {@code type} field is unknown
	 */
	@Override
	public JsonElement serialize(BaseSchema<?> src, Type typeOfSrc,
			JsonSerializationContext context) {
		return switch (src.getType()) {
			case "string" -> context.serialize(src, StringSchema.class);
			case "number" -> context.serialize(src, NumberSchema.class);
			case "enum" -> context.serialize(src, EnumSchema.class);
			case "boolean" -> context.serialize(src, BooleanSchema.class);
			default -> throw new JsonParseException("Unknown type: " + src.getType());
		};
	}
}
