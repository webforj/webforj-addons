package com.webforj.addons.components.propertiespanel.schema;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.webforj.addons.components.propertiespanel.schema.variants.BooleanVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BooleanSchemaTest {

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
    @DisplayName("Serialize minimal BooleanSchema")
    void serializeMinimalBooleanSchema() {
      final var schema = new BooleanSchema("isActive", "Is Active");
      final var expectedJson = SchemaJsonBuilder.forBooleanSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize BooleanSchema with default value true")
    void serializeWithDefaultValueTrue() {
      final var schema = new BooleanSchema("isEnabled", "Is Enabled").setDefaultValue(true);
      final var expectedJson = SchemaJsonBuilder.forBooleanSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize BooleanSchema with default value false")
    void serializeWithDefaultValueFalse() {
      final var schema = new BooleanSchema("isVisible", "Is Visible").setDefaultValue(false);
      final var expectedJson = SchemaJsonBuilder.forBooleanSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize BooleanSchema with all properties")
    void serializeWithAllProperties() {
      final var schema =
          new BooleanSchema("isConfirmed", "Is Confirmed")
              .setDefaultValue(true)
              .setDescription("Indicates user confirmation")
              .setReadonly(true)
              .setDisabled(true);

      final var expectedJson = SchemaJsonBuilder.forBooleanSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }
  }

  @Nested
  @DisplayName("Deserialization Tests")
  class DeserializationTests {

    @Test
    @DisplayName("Deserialize minimal BooleanSchema")
    void deserializeMinimalBooleanSchema() {
      final var templateSchema = new BooleanSchema("canEdit", "Can Edit");
      final var json = SchemaJsonBuilder.forBooleanSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;

      assertEquals("canEdit", schema.getName());
      assertEquals("Can Edit", schema.getLabel());
      assertEquals("boolean", schema.getType());
      assertEquals(BooleanVariant.CHECKBOX, schema.getVariant());
      assertNull(schema.getDefaultValue());
      assertNull(schema.getDescription());
      assertFalse(schema.isReadonly());
      assertFalse(schema.isDisabled());
    }

    @Test
    @DisplayName("Deserialize BooleanSchema with default value true")
    void deserializeWithDefaultValueTrue() {
      final var templateSchema =
          new BooleanSchema("hasLicense", "Has License").setDefaultValue(true);
      final var json = SchemaJsonBuilder.forBooleanSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;
      assertEquals(Boolean.TRUE, schema.getDefaultValue());
    }

    @Test
    @DisplayName("Deserialize BooleanSchema with default value false")
    void deserializeWithDefaultValueFalse() {
      final var templateSchema = new BooleanSchema("isAdmin", "Is Admin").setDefaultValue(false);
      final var json = SchemaJsonBuilder.forBooleanSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;
      assertEquals(Boolean.FALSE, schema.getDefaultValue());
    }

    @Test
    @DisplayName("Deserialize BooleanSchema with all properties")
    void deserializeWithAllProperties() {
      final var templateSchema =
          new BooleanSchema("isComplete", "Is Complete")
              .setDefaultValue(true)
              .setDescription("Task completion status")
              .setReadonly(true)
              .setDisabled(true);
      final var json = SchemaJsonBuilder.forBooleanSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;

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
    @DisplayName("Deserialize JSON with missing optional fields (defaultValue)")
    void deserializeMissingOptionalFields() {
      final var jsonObject =
          SchemaJsonBuilder.forBooleanSchema(new BooleanSchema("basicBool", "Basic Boolean"))
              .build();
      jsonObject.remove("defaultValue");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;

      assertNull(schema.getDefaultValue());
    }

    @Test
    @DisplayName("Deserialize JSON with invalid variant value (should default to CHECKBOX)")
    void deserializeInvalidVariant() {
      final var jsonObject =
          SchemaJsonBuilder.forBooleanSchema(new BooleanSchema("invVarBool", "Invalid Variant"))
              .build();
      jsonObject.addProperty("variant", "invalid-boolean-variant");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(BooleanSchema.class, baseSchema);
      final var schema = (BooleanSchema) baseSchema;

      assertNotNull(schema.getVariant());
      assertEquals(BooleanVariant.CHECKBOX, schema.getVariant());
    }
  }

  @Nested
  @DisplayName("Constructor and Setter Tests")
  class ConstructorSetterTests {

    @Test
    @DisplayName("Constructors should throw NullPointerException for null name or label")
    void constructorNullNameLabel() {
      assertThrows(NullPointerException.class, () -> new BooleanSchema(null, "Label"));
      assertThrows(NullPointerException.class, () -> new BooleanSchema("Name", null));
      assertThrows(
          NullPointerException.class,
          () -> new BooleanSchema(null, "Label", BooleanVariant.CHECKBOX));
      assertThrows(
          NullPointerException.class,
          () -> new BooleanSchema("Name", null, BooleanVariant.CHECKBOX));
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null variant")
    void constructorNullVariant() {
      assertDoesNotThrow(() -> new BooleanSchema("Name", "Label")); // Default variant constructor
      assertThrows(
          NullPointerException.class,
          () -> new BooleanSchema("Name", "Label", null)); // Variant constructor
    }

    @Test
    @DisplayName("setVariant should throw NullPointerException for null argument")
    void setVariantNull() {
      final var schema = new BooleanSchema("name", "Label");
      assertThrows(NullPointerException.class, () -> schema.setVariant(null));
    }

    @Test
    @DisplayName("setDefaultValue allows null argument")
    void setDefaultValueNull() {
      final var schema = new BooleanSchema("name", "Label");
      assertDoesNotThrow(() -> schema.setDefaultValue(null));
      assertNull(schema.getDefaultValue());
    }

    @Test
    @DisplayName("Default variant should be CHECKBOX")
    void defaultVariantIsCheckbox() {
      final var schema = new BooleanSchema("name", "Label");
      assertEquals(BooleanVariant.CHECKBOX, schema.getVariant());
    }
  }
}
