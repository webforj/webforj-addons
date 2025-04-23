package com.webforj.addons.components.propertiespanel.schema;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.webforj.addons.components.propertiespanel.schema.variants.EnumVariant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EnumSchemaTest {

  private Gson gson;
  private List<EnumOption> sampleOptions;

  private void assertJsonEquals(String expected, String actual) {
    final var expectedJson = JsonParser.parseString(expected);
    final var actualJson = JsonParser.parseString(actual);
    assertEquals(expectedJson, actualJson, "JSON outputs do not match");
  }

  @BeforeEach
  void setUp() {
    gson =
        new GsonBuilder().registerTypeAdapter(BaseSchema.class, new BaseSchemaAdapter()).create();
    sampleOptions =
        List.of(
            new EnumOption("opt1", "Option 1", "VALUE_1"),
            new EnumOption("opt2", "Option 2", "VALUE_2"));
  }

  @Nested
  @DisplayName("Serialization Tests")
  class SerializationTests {

    @Test
    @DisplayName("Serialize minimal EnumSchema")
    void serializeMinimalEnumSchema() {
      final var schema = new EnumSchema("status", "Status");
      final var expectedJson = SchemaJsonBuilder.forEnumSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize EnumSchema with options")
    void serializeWithOptions() {
      final var schema = new EnumSchema("color", "Color").setOptions(sampleOptions);
      final var expectedJson = SchemaJsonBuilder.forEnumSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize EnumSchema with default value")
    void serializeWithDefaultValue() {
      final var schema =
          new EnumSchema("priority", "Priority")
              .setOptions(sampleOptions)
              .setDefaultValue("VALUE_1");
      final var expectedJson = SchemaJsonBuilder.forEnumSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize EnumSchema with all properties")
    void serializeWithAllProperties() {
      final var schema =
          new EnumSchema("category", "Category", EnumVariant.LISTBOX)
              .setOptions(sampleOptions)
              .setDefaultValue("VALUE_2")
              .setDescription("Select the item category")
              .setReadonly(false)
              .setDisabled(true);

      final var expectedJson = SchemaJsonBuilder.forEnumSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);

      assertJsonEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Serialize EnumSchema with null options list")
    void serializeWithNullOptions() {
      final var schema = new EnumSchema("size", "Size");
      schema.setOptions(null);
      final var expectedJson = SchemaJsonBuilder.forEnumSchema(schema).build().toString();
      final var actualJson = gson.toJson(schema, BaseSchema.class);
      assertTrue(
          actualJson.contains("\"options\":[]"),
          "JSON should contain empty options array for null input");
      assertJsonEquals(expectedJson, actualJson);
    }
  }

  @Nested
  @DisplayName("Deserialization Tests")
  class DeserializationTests {

    @Test
    @DisplayName("Deserialize minimal EnumSchema")
    void deserializeMinimalEnumSchema() {
      final var templateSchema = new EnumSchema("mode", "Mode");
      final var json = SchemaJsonBuilder.forEnumSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;

      assertEquals("mode", schema.getName());
      assertEquals("Mode", schema.getLabel());
      assertEquals("enum", schema.getType());
      assertEquals(EnumVariant.LISTBOX, schema.getVariant());
      assertNull(schema.getDefaultValue());
      assertNull(schema.getDescription());
      assertNotNull(schema.getOptions());
      assertTrue(schema.getOptions().isEmpty());
      assertFalse(schema.isReadonly());
      assertFalse(schema.isDisabled());
    }

    @Test
    @DisplayName("Deserialize EnumSchema with options")
    void deserializeWithOptions() {
      final var templateSchema = new EnumSchema("font", "Font").setOptions(sampleOptions);
      final var json = SchemaJsonBuilder.forEnumSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;

      assertNotNull(schema.getOptions());
      assertEquals(2, schema.getOptions().size());
      assertEquals("Option 1", schema.getOptions().get(0).getLabel());
      assertEquals("VALUE_1", schema.getOptions().get(0).getValue());
      assertEquals("Option 2", schema.getOptions().get(1).getLabel());
      assertEquals("VALUE_2", schema.getOptions().get(1).getValue());
    }

    @Test
    @DisplayName("Deserialize EnumSchema with default value")
    void deserializeWithDefaultValue() {
      final var templateSchema =
          new EnumSchema("alignment", "Alignment")
              .setOptions(sampleOptions)
              .setDefaultValue("VALUE_2");
      final var json = SchemaJsonBuilder.forEnumSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;
      assertEquals("VALUE_2", schema.getDefaultValue());
    }

    @Test
    @DisplayName("Deserialize EnumSchema with all properties")
    void deserializeWithAllProperties() {
      final var templateSchema =
          new EnumSchema("style", "Style", EnumVariant.LISTBOX)
              .setOptions(sampleOptions)
              .setDefaultValue("VALUE_1")
              .setDescription("Select text style")
              .setReadonly(true)
              .setDisabled(true);
      final var json = SchemaJsonBuilder.forEnumSchema(templateSchema).build().toString();
      final var baseSchema = gson.fromJson(json, BaseSchema.class);

      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;

      assertEquals(templateSchema.getName(), schema.getName());
      assertEquals(templateSchema.getLabel(), schema.getLabel());
      assertEquals(templateSchema.getType(), schema.getType());
      assertEquals(templateSchema.getVariant(), schema.getVariant());
      assertEquals(templateSchema.getDefaultValue(), schema.getDefaultValue());
      assertEquals(templateSchema.getDescription(), schema.getDescription());
      assertEquals(templateSchema.isReadonly(), schema.isReadonly());
      assertEquals(templateSchema.isDisabled(), schema.isDisabled());
      assertNotNull(schema.getOptions());
      assertEquals(templateSchema.getOptions().size(), schema.getOptions().size());
      assertEquals(
          templateSchema.getOptions().get(0).getValue(), schema.getOptions().get(0).getValue());
      assertEquals(
          templateSchema.getOptions().get(1).getValue(), schema.getOptions().get(1).getValue());
    }

    @Test
    @DisplayName("Deserialize JSON with missing optional fields (defaultValue)")
    void deserializeMissingOptionalFields() {
      final var jsonObject =
          SchemaJsonBuilder.forEnumSchema(
                  new EnumSchema("basicEnum", "Basic Enum").setOptions(sampleOptions))
              .build();
      jsonObject.remove("defaultValue");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;

      assertNull(schema.getDefaultValue());
      assertNotNull(schema.getOptions());
      assertEquals(sampleOptions.size(), schema.getOptions().size());
    }

    @Test
    @DisplayName("Deserialize JSON with invalid variant value (should default to LISTBOX)")
    void deserializeInvalidVariant() {
      final var jsonObject =
          SchemaJsonBuilder.forEnumSchema(new EnumSchema("invVarEnum", "Invalid Variant")).build();
      jsonObject.addProperty("variant", "invalid-enum-variant");
      final var json = jsonObject.toString();

      final var baseSchema = gson.fromJson(json, BaseSchema.class);
      assertInstanceOf(EnumSchema.class, baseSchema);
      final var schema = (EnumSchema) baseSchema;

      assertNotNull(schema.getVariant());
      assertEquals(EnumVariant.LISTBOX, schema.getVariant());
    }
  }

  @Nested
  @DisplayName("Constructor and Setter Tests")
  class ConstructorSetterTests {

    @Test
    @DisplayName("Constructors should throw NullPointerException for null name or label")
    void constructorNullNameLabel() {
      assertThrows(NullPointerException.class, () -> new EnumSchema(null, "Label"));
      assertThrows(NullPointerException.class, () -> new EnumSchema("Name", null));
      assertThrows(
          NullPointerException.class, () -> new EnumSchema(null, "Label", EnumVariant.LISTBOX));
      assertThrows(
          NullPointerException.class, () -> new EnumSchema("Name", null, EnumVariant.LISTBOX));
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null variant")
    void constructorNullVariant() {
      assertDoesNotThrow(() -> new EnumSchema("Name", "Label"));
      assertThrows(NullPointerException.class, () -> new EnumSchema("Name", "Label", null));
    }

    @Test
    @DisplayName("setVariant should throw NullPointerException for null argument")
    void setVariantNull() {
      final var schema = new EnumSchema("name", "Label");
      assertThrows(NullPointerException.class, () -> schema.setVariant(null));
    }

    @Test
    @DisplayName("setOptions handles null argument by setting empty list")
    void setOptionsNull() {
      final var schema = new EnumSchema("name", "Label");
      assertDoesNotThrow(() -> schema.setOptions(null));
      assertNotNull(schema.getOptions());
      assertTrue(schema.getOptions().isEmpty());
    }

    @Test
    @DisplayName("setDefaultValue allows null argument")
    void setDefaultValueNull() {
      final var schema = new EnumSchema("name", "Label");
      assertDoesNotThrow(() -> schema.setDefaultValue(null));
      assertNull(schema.getDefaultValue());
    }

    @Test
    @DisplayName("Default variant should be LISTBOX")
    void defaultVariantIsListbox() {
      final var schema = new EnumSchema("name", "Label");
      assertEquals(EnumVariant.LISTBOX, schema.getVariant());
    }
  }
}
