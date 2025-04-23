package com.webforj.addons.components.propertiespanel.schema;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.webforj.addons.components.propertiespanel.schema.variants.NumberVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NumberSchemaTest {

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
    @DisplayName("Serialize minimal NumberSchema")
    void serializeMinimalNumberSchema() {
      final var schema = new NumberSchema("age", "Age");
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with default value")
    void serializeWithDefaultValue() {
      final var schema = new NumberSchema("quantity", "Quantity").setDefaultValue(10.0);
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with validation (min only)")
    void serializeWithValidationMinOnly() {
      final var schema =
          new NumberSchema("price", "Price").setValidate(new NumberValidation(0.0, null));
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with validation (max only)")
    void serializeWithValidationMaxOnly() {
      final var schema =
          new NumberSchema("discount", "Discount").setValidate(new NumberValidation(null, 100.0));
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with validation (min and max)")
    void serializeWithValidationMinMax() {
      final var schema =
          new NumberSchema("rating", "Rating").setValidate(new NumberValidation(1.0, 5.0));
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with all properties")
    void serializeWithAllProperties() {
      final var schema =
          new NumberSchema("score", "User Score")
              .setDefaultValue(50.0)
              .setDescription("Score between 0 and 100")
              .setReadonly(false) // Not serialized if false by builder
              .setDisabled(true)
              .setValidate(new NumberValidation(0.0, 100.0));

      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize NumberSchema with null validation object")
    void serializeWithNullValidation() {
      final var schema = new NumberSchema("items", "Items");
      schema.setValidate(null);
      final var expectedJson = SchemaJsonBuilder.forNumberSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertFalse(actualJson.contains("validate"));
      assertJsonEquals(expectedJson, actualJson);
    }
  }

  @Nested
  @DisplayName("Deserialization Tests")
  class DeserializationTests {

    @Test
    @DisplayName("Deserialize minimal NumberSchema")
    void deserializeMinimalNumberSchema() {
      final var templateSchema = new NumberSchema("count", "Item Count");
      final var json = SchemaJsonBuilder.forNumberSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertEquals("count", schema.getName());
      assertEquals("Item Count", schema.getLabel());
      assertEquals("number", schema.getType());
      assertEquals(NumberVariant.NUMBER, schema.getVariant());
      assertNull(schema.getDefaultValue());
      assertNull(schema.getDescription());
      assertFalse(schema.isReadonly());
      assertFalse(schema.isDisabled());
      assertNull(schema.getValidate());
    }

    @Test
    @DisplayName("Deserialize NumberSchema with default value")
    void deserializeWithDefaultValue() {
      final var templateSchema = new NumberSchema("level", "Level").setDefaultValue(1.0);
      final var json = SchemaJsonBuilder.forNumberSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      final var schema = (NumberSchema) baseSchema;

      assertInstanceOf(NumberSchema.class, baseSchema);
      assertEquals(1.0, schema.getDefaultValue());
    }

    @Test
    @DisplayName("Deserialize NumberSchema with validation (min and max)")
    void deserializeWithValidation() {
      final var templateSchema =
          new NumberSchema("temp", "Temperature").setValidate(new NumberValidation(-10.5, 40.0));
      final var json = SchemaJsonBuilder.forNumberSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertNotNull(schema.getValidate());
      assertEquals(-10.5, schema.getValidate().getMin());
      assertEquals(40.0, schema.getValidate().getMax());
    }

    @Test
    @DisplayName("Deserialize NumberSchema with validation (min only)")
    void deserializeWithValidationMinOnly() {
      final var templateSchema =
          new NumberSchema("temp", "Temperature")
              .setValidate(new NumberValidation(-10.5, null)); // Max is null
      final var json = SchemaJsonBuilder.forNumberSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertNotNull(schema.getValidate());
      assertEquals(-10.5, schema.getValidate().getMin());
      assertNull(schema.getValidate().getMax());
    }

    @Test
    @DisplayName("Deserialize NumberSchema with all properties")
    void deserializeWithAllProperties() {
      final var templateSchema =
          new NumberSchema("percentage", "Completion %")
              .setDefaultValue(75.5)
              .setDescription("Range 0-100")
              .setReadonly(true)
              .setDisabled(true)
              .setValidate(new NumberValidation(0.0, 100.0));
      final var json = SchemaJsonBuilder.forNumberSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertEquals(templateSchema.getName(), schema.getName());
      assertEquals(templateSchema.getLabel(), schema.getLabel());
      assertEquals(templateSchema.getType(), schema.getType());
      assertEquals(templateSchema.getVariant(), schema.getVariant());
      assertEquals(templateSchema.getDefaultValue(), schema.getDefaultValue());
      assertEquals(templateSchema.getDescription(), schema.getDescription());
      assertEquals(templateSchema.isReadonly(), schema.isReadonly());
      assertEquals(templateSchema.isDisabled(), schema.isDisabled());
      assertNotNull(schema.getValidate());
      assertEquals(templateSchema.getValidate().getMin(), schema.getValidate().getMin());
      assertEquals(templateSchema.getValidate().getMax(), schema.getValidate().getMax());
    }

    @Test
    @DisplayName("Deserialize JSON with missing optional fields (defaultValue, validate)")
    void deserializeMissingOptionalFields() {
      final var jsonObject =
          SchemaJsonBuilder.forNumberSchema(new NumberSchema("basicNum", "Basic Number")).build();
      jsonObject.remove("defaultValue");
      jsonObject.remove("validate");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertNull(schema.getDefaultValue());
      assertNull(schema.getValidate());
    }

    @Test
    @DisplayName("Deserialize JSON with invalid variant value (should default to NUMBER)")
    void deserializeInvalidVariant() {
      final var jsonObject =
          SchemaJsonBuilder.forNumberSchema(new NumberSchema("invVar", "Invalid Variant")).build();
      jsonObject.addProperty("variant", "invalid-number-variant");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(NumberSchema.class, baseSchema);
      final var schema = (NumberSchema) baseSchema;

      assertNotNull(schema.getVariant());
      assertEquals(NumberVariant.NUMBER, schema.getVariant());
    }
  }

  @Nested
  @DisplayName("Constructor and Setter Tests")
  class ConstructorSetterTests {

    @Test
    @DisplayName("Constructors should throw NullPointerException for null name or label")
    void constructorNullNameLabel() {
      assertThrows(NullPointerException.class, () -> new NumberSchema(null, "Label"));
      assertThrows(NullPointerException.class, () -> new NumberSchema("Name", null));
      assertThrows(
          NullPointerException.class, () -> new NumberSchema(null, "Label", NumberVariant.NUMBER));
      assertThrows(
          NullPointerException.class, () -> new NumberSchema("Name", null, NumberVariant.NUMBER));
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null variant")
    void constructorNullVariant() {
      assertDoesNotThrow(() -> new NumberSchema("Name", "Label"));
      assertThrows(NullPointerException.class, () -> new NumberSchema("Name", "Label", null));
    }

    @Test
    @DisplayName("setVariant should throw NullPointerException for null argument")
    void setVariantNull() {
      NumberSchema schema = new NumberSchema("name", "Label");
      assertThrows(NullPointerException.class, () -> schema.setVariant(null));
    }

    @Test
    @DisplayName("setValidate allows null argument")
    void setValidateNull() {
      NumberSchema schema = new NumberSchema("name", "Label");
      assertDoesNotThrow(() -> schema.setValidate(null));
      assertNull(schema.getValidate());
    }

    @Test
    @DisplayName("Default variant should be NUMBER")
    void defaultVariantIsNumber() {
      NumberSchema schema = new NumberSchema("name", "Label");
      assertEquals(NumberVariant.NUMBER, schema.getVariant());
    }
  }
}
