package com.webforj.addons.components.propertiespanel.schema;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** A helper class to build JSON representations of schema objects for testing purposes. */
public class SchemaJsonBuilder {

  private final JsonObject jsonObject;
  private static final Gson internalGson = new Gson();

  private SchemaJsonBuilder(BaseSchema<?> schema) {
    this.jsonObject = new JsonObject();
    jsonObject.addProperty("name", schema.getName());
    jsonObject.addProperty("label", schema.getLabel());
    jsonObject.addProperty("type", schema.getType());
    jsonObject.addProperty("disabled", schema.isDisabled());
    jsonObject.addProperty("readonly", schema.isReadonly());
    if (schema.getDescription() != null) {
      jsonObject.addProperty("description", schema.getDescription());
    }
  }

  /** Starts building JSON for a StringSchema. */
  public static SchemaJsonBuilder forStringSchema(StringSchema schema) {
    SchemaJsonBuilder builder = new SchemaJsonBuilder(schema);
    if (schema.getDefaultValue() != null) {
      builder.jsonObject.addProperty("defaultValue", schema.getDefaultValue());
    }
    builder.jsonObject.addProperty("variant", schema.getVariant().getValue());
    return builder;
  }

  /** Starts building JSON for a NumberSchema. */
  public static SchemaJsonBuilder forNumberSchema(NumberSchema schema) {
    SchemaJsonBuilder builder = new SchemaJsonBuilder(schema);
    if (schema.getDefaultValue() != null) {
      builder.jsonObject.addProperty("defaultValue", schema.getDefaultValue());
    }
    builder.jsonObject.addProperty("variant", schema.getVariant().getValue());

    NumberValidation validation = schema.getValidate();
    if (validation != null) {
      JsonObject validateJson = new JsonObject();
      if (validation.getMin() != null) {
        validateJson.addProperty("min", validation.getMin());
      }
      if (validation.getMax() != null) {
        validateJson.addProperty("max", validation.getMax());
      }
      if (!validateJson.isEmpty()) {
        builder.jsonObject.add("validate", validateJson);
      }
    }
    return builder;
  }

  /** Starts building JSON for a BooleanSchema. */
  public static SchemaJsonBuilder forBooleanSchema(BooleanSchema schema) {
    SchemaJsonBuilder builder = new SchemaJsonBuilder(schema);
    if (schema.getDefaultValue() != null) {
      builder.jsonObject.addProperty("defaultValue", schema.getDefaultValue());
    }
    builder.jsonObject.addProperty("variant", schema.getVariant().getValue());
    return builder;
  }

  /** Starts building JSON for an EnumSchema. */
  public static SchemaJsonBuilder forEnumSchema(EnumSchema schema) {
    SchemaJsonBuilder builder = new SchemaJsonBuilder(schema);
    if (schema.getDefaultValue() != null) {
      builder.jsonObject.addProperty("defaultValue", schema.getDefaultValue());
    }
    builder.jsonObject.addProperty("variant", schema.getVariant().getValue());

    if (schema.getOptions() != null && !schema.getOptions().isEmpty()) {
      JsonArray optionsArray = new JsonArray();
      for (EnumOption option : schema.getOptions()) {
        optionsArray.add(JsonParser.parseString(internalGson.toJson(option)));
      }
      builder.jsonObject.add("options", optionsArray);
    } else {
      builder.jsonObject.add("options", new JsonArray());
    }
    return builder;
  }

  /** Returns the built JsonObject. */
  public JsonObject build() {
    return jsonObject;
  }

  /** Returns the built JSON as a string. */
  @Override
  public String toString() {
    return jsonObject.toString();
  }
}
