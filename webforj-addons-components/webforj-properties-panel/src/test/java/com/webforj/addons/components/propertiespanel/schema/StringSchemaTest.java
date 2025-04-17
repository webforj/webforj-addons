package com.webforj.addons.components.propertiespanel.schema;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webforj.addons.components.propertiespanel.schema.variants.StringVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringSchemaTest {

  private Gson gson;

  private void assertJsonEquals(String expected, String actual) {
    final var expectedJson = JsonParser.parseString(expected);
    final var actualJson = JsonParser.parseString(actual);
    assertEquals(expectedJson, actualJson, "JSON outputs do not match");
  }

  @BeforeEach
  void setUp() {
    gson =
        new GsonBuilder().registerTypeAdapter(BaseSchema.class, new BaseSchemaAdapter()).create();
  }

  @Nested
  @DisplayName("Serialization Tests")
  class SerializationTests {

    @Test
    @DisplayName("Serialize minimal StringSchema (default variant)")
    void serializeMinimalStringSchema() {
      final var schema = new StringSchema("firstName", "First Name");
      final var expectedJson = SchemaJsonBuilder.forStringSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize StringSchema with TEXTAREA variant")
    void serializeStringSchemaTextarea() {
      final var schema =
          new StringSchema("description", "Product Description", StringVariant.TEXTAREA);
      final var expectedJson = SchemaJsonBuilder.forStringSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize StringSchema with all properties")
    void serializeStringSchemaWithAllProperties() {
      final var schema =
          new StringSchema("notes", "Additional Notes", StringVariant.TEXTAREA)
              .setDefaultValue("Initial notes here.")
              .setDescription("Enter any relevant notes.")
              .setReadonly(true)
              .setDisabled(false);

      final var expectedJson = SchemaJsonBuilder.forStringSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize StringSchema after setting variant explicitly")
    void serializeStringSchemaAfterSettingVariant() {
      final var schema =
          new StringSchema("code", "Code Snippet").setVariant(StringVariant.TEXTAREA);
      final var expectedJson = SchemaJsonBuilder.forStringSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }
  }

  @Nested
  @DisplayName("Deserialization Tests")
  class DeserializationTests {

    @Test
    @DisplayName("Deserialize minimal StringSchema (default variant)")
    void deserializeMinimalStringSchema() {
      final var templateSchema = new StringSchema("minName", "Minimal Name");
      final var json = SchemaJsonBuilder.forStringSchema(templateSchema).build().toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(StringSchema.class, baseSchema);
      final var schema = (StringSchema) baseSchema;

      assertEquals("minName", schema.getName());
      assertEquals("Minimal Name", schema.getLabel());
      assertEquals("string", schema.getType());
      assertEquals(StringVariant.TEXT, schema.getVariant());
      assertNull(schema.getDefaultValue());
      assertNull(schema.getDescription());
      assertFalse(schema.isReadonly());
      assertFalse(schema.isDisabled());
    }

    @Test
    @DisplayName("Deserialize StringSchema with TEXTAREA variant")
    void deserializeStringSchemaTextarea() {
      final var templateSchema = new StringSchema("desc", "Description", StringVariant.TEXTAREA);
      final var json = SchemaJsonBuilder.forStringSchema(templateSchema).build().toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(StringSchema.class, baseSchema);
      final var schema = (StringSchema) baseSchema;

      assertEquals("desc", schema.getName());
      assertEquals("Description", schema.getLabel());
      assertEquals(StringVariant.TEXTAREA, schema.getVariant());
    }

    @Test
    @DisplayName("Deserialize StringSchema with all properties")
    void deserializeStringSchemaWithAllProperties() {
      final var templateSchema =
          new StringSchema("fullNotes", "Full Notes", StringVariant.TEXTAREA)
              .setDefaultValue("Default full notes.")
              .setDescription("Enter detailed notes.")
              .setReadonly(true)
              .setDisabled(true);

      final var json = SchemaJsonBuilder.forStringSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(StringSchema.class, baseSchema);
      final var schema = (StringSchema) baseSchema;

      assertEquals(templateSchema.getName(), schema.getName());
      assertEquals(templateSchema.getLabel(), schema.getLabel());
      assertEquals(templateSchema.getType(), schema.getType());
      assertEquals(templateSchema.getVariant(), schema.getVariant());
      assertEquals(templateSchema.getDefaultValue(), schema.getDefaultValue());
      assertEquals(templateSchema.getDescription(), schema.getDescription());
      assertEquals(templateSchema.isReadonly(), schema.isReadonly());
      assertEquals(templateSchema.isDisabled(), schema.isDisabled());
    }

    @Test
    @DisplayName("Deserialize JSON with missing variant field should default to TEXT")
    void deserializeMissingVariant() {
      final var jsonObject =
          SchemaJsonBuilder.forStringSchema(new StringSchema("missingVar", "Missing Variant"))
              .build();
      jsonObject.remove("variant");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(StringSchema.class, baseSchema);
      final var schema = (StringSchema) baseSchema;

      assertEquals(StringVariant.TEXT, schema.getVariant());
    }

    @Test
    @DisplayName("Deserialize JSON with invalid variant value")
    void deserializeInvalidVariant() {
      final var jsonObject =
          SchemaJsonBuilder.forStringSchema(new StringSchema("invalidVar", "Invalid Variant"))
              .build();
      jsonObject.addProperty("variant", "invalid-enum-value");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(StringSchema.class, baseSchema);
      final var schema = (StringSchema) baseSchema;

      assertNotNull(
          schema.getVariant(),
          "Variant should not be null after deserialization, even with invalid input (should default)");
      assertEquals(StringVariant.TEXT, schema.getVariant());
    }

    @Test
    @DisplayName("Deserialize JSON with missing mandatory fields (name/label)")
    void deserializeMissingMandatoryFields() {
      final var jsonObject = new JsonObject();
      jsonObject.addProperty("type", "string");

      final var baseSchema = gson.fromJson(jsonObject.toString(), BaseSchema.class);
      assertNotNull(baseSchema);
      assertInstanceOf(StringSchema.class, baseSchema);
      assertNull(baseSchema.getName());
      assertNull(baseSchema.getLabel());
    }
  }

  @Nested
  @DisplayName("Constructor and Setter Tests")
  class ConstructorSetterTests {

    @Test
    @DisplayName("Constructor should throw NullPointerException for null name")
    void constructorNullName() {
      assertThrows(NullPointerException.class, () -> new StringSchema(null, "Label"));
      assertThrows(
          NullPointerException.class, () -> new StringSchema(null, "Label", StringVariant.TEXT));
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null label")
    void constructorNullLabel() {
      assertThrows(NullPointerException.class, () -> new StringSchema("Name", null));
      assertThrows(
          NullPointerException.class, () -> new StringSchema("Name", null, StringVariant.TEXT));
    }

    @Test
    @DisplayName(
        "Constructor should throw NullPointerException for null variant (when variant provided)")
    void constructorNullVariant() {
      assertDoesNotThrow(() -> new StringSchema("Name", "Label"));
      assertThrows(NullPointerException.class, () -> new StringSchema("Name", "Label", null));
    }

    @Test
    @DisplayName("setVariant should throw NullPointerException for null argument")
    void setVariantNull() {
      StringSchema schema = new StringSchema("name", "Label");
      assertThrows(NullPointerException.class, () -> schema.setVariant(null));
    }

    @Test
    @DisplayName("Default variant should be TEXT")
    void defaultVariantIsText() {
      StringSchema schema = new StringSchema("name", "Label");
      assertEquals(StringVariant.TEXT, schema.getVariant());
    }
  }
}
