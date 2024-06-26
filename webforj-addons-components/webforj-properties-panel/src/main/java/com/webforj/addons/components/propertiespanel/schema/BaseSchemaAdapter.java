package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.*;

import java.lang.reflect.Type;

public class BaseSchemaAdapter
    implements JsonDeserializer<BaseSchema<?>>, JsonSerializer<BaseSchema<?>> {
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
