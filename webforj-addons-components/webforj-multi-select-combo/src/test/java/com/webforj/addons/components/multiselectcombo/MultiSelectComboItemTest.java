package com.webforj.addons.components.multiselectcombo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MultiSelectComboItem Tests")
class MultiSelectComboItemTest {

  private static final String DEFAULT_LABEL = "Default Label";
  private static final String DEFAULT_VALUE = "default_value";

  @Nested
  @DisplayName("Builder Tests")
  class BuilderTests {

    @Test
    @DisplayName("should build item with only mandatory fields")
    void buildWithMandatoryFields() {
      final var item =
        MultiSelectComboItem.builder().label(DEFAULT_LABEL).value(DEFAULT_VALUE).build();

      assertNotNull(item);
      assertEquals(DEFAULT_LABEL, item.getLabel());
      assertEquals(DEFAULT_VALUE, item.getValue());
      assertNull(item.getPrefix());
      assertNull(item.getSuffix());
      assertFalse(item.isDisabled(), "Disabled should default to false");
    }

    @Test
    @DisplayName("should build item with all optional fields")
    void buildWithAllFields() {
      final var prefix = "Pre:";
      final var suffix = ":Suf";
      final boolean disabled = true;

      final var item = MultiSelectComboItem.builder()
          .label(DEFAULT_LABEL)
          .value(DEFAULT_VALUE)
          .prefix(prefix)
          .suffix(suffix)
          .disabled(disabled)
          .build();

      assertNotNull(item);
      assertEquals(DEFAULT_LABEL, item.getLabel());
      assertEquals(DEFAULT_VALUE, item.getValue());
      assertEquals(prefix, item.getPrefix());
      assertEquals(suffix, item.getSuffix());
      assertEquals(disabled, item.isDisabled());
    }

    @Test
    @DisplayName("should throw NullPointerException when mandatory label is null")
    void buildWithNullLabel() {
      final var builderStep1 = MultiSelectComboItem.builder();
      final var exception = assertThrows(NullPointerException.class, () -> {
        builderStep1.label(null);
      });
      assertEquals("Label cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("should throw NullPointerException when mandatory value is null")
    void buildWithNullValue() {
      final var builderStep2 = MultiSelectComboItem.builder().label(DEFAULT_LABEL);
      final var exception = assertThrows(NullPointerException.class, () -> {
        builderStep2.value(null);
      });
      assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("should have default value for disabled field when not set")
    void buildWithDefaultDisabled() {
      final var item =
        MultiSelectComboItem.builder().label(DEFAULT_LABEL).value(DEFAULT_VALUE).build();
      assertFalse(item.isDisabled());
    }
  }

  @Nested
  @DisplayName("Getter Tests")
  class GetterTests {

    private MultiSelectComboItem item;
    private final String prefix = "Prefix ";
    private final String suffix = " Suffix";

    @BeforeEach
    void setUp() {
      item =
        MultiSelectComboItem.builder()
          .label(DEFAULT_LABEL)
          .value(DEFAULT_VALUE)
          .prefix(prefix)
          .suffix(suffix)
          .disabled(true)
          .build();
    }

    @Test
    @DisplayName("getLabel() returns correct value")
    void getLabel() {
      assertEquals(DEFAULT_LABEL, item.getLabel());
    }

    @Test
    @DisplayName("getValue() returns correct value")
    void getValue() {
      assertEquals(DEFAULT_VALUE, item.getValue());
    }

    @Test
    @DisplayName("getPrefix() returns correct value")
    void getPrefix() {
      assertEquals(prefix, item.getPrefix());
    }

    @Test
    @DisplayName("getSuffix() returns correct value")
    void getSuffix() {
      assertEquals(suffix, item.getSuffix());
    }

    @Test
    @DisplayName("isDisabled() returns correct value")
    void isDisabled() {
      assertTrue(item.isDisabled());
    }
  }

  @Nested
  @DisplayName("Setter Tests")
  class SetterTests {

    private MultiSelectComboItem item;

    @BeforeEach
    void setUp() {
      item = MultiSelectComboItem.builder().label(DEFAULT_LABEL).value(DEFAULT_VALUE).build();
    }

    @Test
    @DisplayName("setLabel() updates value and returns this")
    void setLabel() {
      final var newLabel = "New Label";
      final var returnedItem = item.setLabel(newLabel);
      assertEquals(newLabel, item.getLabel());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setValue() updates value and returns this")
    void setValue() {
      final var newValue = "new_value";
      final var returnedItem = item.setValue(newValue);
      assertEquals(newValue, item.getValue());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setPrefix() updates value and returns this")
    void setPrefix() {
      final var newPrefix = "New Pre";
      final var returnedItem = item.setPrefix(newPrefix);
      assertEquals(newPrefix, item.getPrefix());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setPrefix() accepts null")
    void setPrefixNull() {
      item.setPrefix("Initial");
      final var returnedItem = item.setPrefix(null);
      assertNull(item.getPrefix());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setSuffix() updates value and returns this")
    void setSuffix() {
      final var newSuffix = "New Suf";
      final var returnedItem = item.setSuffix(newSuffix);
      assertEquals(newSuffix, item.getSuffix());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setSuffix() accepts null")
    void setSuffixNull() {
      item.setSuffix("Initial");
      final var returnedItem = item.setSuffix(null);
      assertNull(item.getSuffix());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setDisabled() updates value and returns this")
    void setDisabled() {
      var returnedItem = item.setDisabled(true);
      assertTrue(item.isDisabled());
      assertSame(item, returnedItem);

      returnedItem = item.setDisabled(false);
      assertFalse(item.isDisabled());
      assertSame(item, returnedItem);
    }

    @Test
    @DisplayName("setLabel() should throw NullPointerException for null label")
    void setLabelNull() {
      final var exception =
        assertThrows(
          NullPointerException.class,
          () -> {
            item.setLabel(null);
          });
      assertEquals("Label cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("setValue() should throw NullPointerException for null value")
    void setValueNull() {
      final var exception =
        assertThrows(
          NullPointerException.class,
          () -> {
            item.setValue(null);
          });
      assertEquals("Value cannot be null", exception.getMessage());
    }
  }
}
