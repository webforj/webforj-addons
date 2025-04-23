package com.webforj.addons.components.multiselectcombo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("MultiSelectCombo Item Manipulation Tests")
@ExtendWith(MockitoExtension.class)
class MultiSelectComboTest {

  @Spy MultiSelectCombo multiSelectCombo = new MultiSelectCombo();

  @Captor ArgumentCaptor<List<MultiSelectComboItem>> itemsListCaptor;

  private MultiSelectComboItem item1;
  private MultiSelectComboItem item2;
  private MultiSelectComboItem item3;

  @BeforeEach
  void setUp() {
    item1 = MultiSelectComboItem.builder().label("Label 1").value("value1").build();
    item2 = MultiSelectComboItem.builder().label("Label 2").value("value2").disabled(true).build();
    item3 = MultiSelectComboItem.builder().label("Label 3").value("value3").build();

    multiSelectCombo.setItems(new ArrayList<>(Arrays.asList(item1, item2)));
    clearInvocations(multiSelectCombo);
  }

  @Nested
  @DisplayName("addItem(MultiSelectComboItem item)")
  class AddSingleItemTests {

    @Test
    @DisplayName("should add item to an empty list")
    void addItemToEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.addItem(item3);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      List<MultiSelectComboItem> capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals(item3, capturedList.get(0));
      assertSame(multiSelectCombo, returned, "Should return instance for chaining");
    }

    @Test
    @DisplayName("should add item to the end of an existing list")
    void addItemToExistingList() {
      final var returned = multiSelectCombo.addItem(item3);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(3, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException when adding null item")
    void addNullItem() {
      assertThrows(NullPointerException.class, () -> multiSelectCombo.addItem(null),
        "Item cannot be null");
      verify(multiSelectCombo, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItem(String label, String value)")
  class AddLabelValueItemTests {

    @Test
    @DisplayName("should add item with label/value to an empty list")
    void addItemToEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.addItem("New Label", "new_value");

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals("New Label", capturedList.get(0).getLabel());
      assertEquals("new_value", capturedList.get(0).getValue());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should add item with label/value to an existing list")
    void addItemToExistingList() {
      final var returned = multiSelectCombo.addItem("New Label", "new_value");

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(3, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals("New Label", capturedList.get(2).getLabel());
      assertEquals("new_value", capturedList.get(2).getValue());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException if label is null")
    void addNullLabel() {
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItem(null, "value"),
        "Label cannot be null (from builder)");
      verify(multiSelectCombo, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if value is null")
    void addNullValue() {
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItem("Label", null),
        "Value cannot be null (from builder)");
      verify(multiSelectCombo, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItems(MultiSelectComboItem... items)")
  class AddVarArgsItemsTests {

    @Test
    @DisplayName("should add multiple items to an empty list")
    void addItemsToEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.addItems(item1, item2);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(2, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should add multiple items to an existing list")
    void addItemsToExistingList() {
      final var item4 = MultiSelectComboItem.builder().label("Label 4").value("value4").build();
      final var returned = multiSelectCombo.addItems(item3, item4);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(4, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
      assertEquals(item4, capturedList.get(3));
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing when adding empty varargs")
    void addEmptyVarArgs() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.addItems();
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException if items array is null")
    void addNullVarArgsArray() {
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItems((MultiSelectComboItem[]) null),
        "Items array cannot be null");
      verify(multiSelectCombo, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if items array contains null")
    void addVarArgsWithNullElement() {
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItems(item1, null, item2),
        "Items array cannot contain null elements");
      verify(multiSelectCombo, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItems(Collection<MultiSelectComboItem> items)")
  class AddCollectionItemsTests {

    @Test
    @DisplayName("should add collection items to an empty list")
    void addCollectionToEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);
      final var itemsToAdd = Arrays.asList(item1, item2);

      MultiSelectCombo returned = multiSelectCombo.addItems(itemsToAdd);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(2, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should add collection items to an existing list")
    void addCollectionToExistingList() {
      // Initial state: [item1, item2]
      final var item4 =
        MultiSelectComboItem.builder().label("Label 4").value("value4").build();
      final var itemsToAdd = Arrays.asList(item3, item4);
      final var returned = multiSelectCombo.addItems(itemsToAdd);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(4, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
      assertEquals(item4, capturedList.get(3));
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing when adding empty collection")
    void addEmptyCollection() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.addItems(new ArrayList<>());
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException if collection is null")
    void addNullCollection() {
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItems((Collection<MultiSelectComboItem>) null),
        "Items collection cannot be null");
      verify(multiSelectCombo, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if collection contains null")
    void addCollectionWithNullElement() {
      final var itemsToAdd = Arrays.asList(item1, null, item2);
      assertThrows(NullPointerException.class,
        () -> multiSelectCombo.addItems(itemsToAdd),
        "Items collection cannot contain null elements");
      verify(multiSelectCombo, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("removeItem(MultiSelectComboItem item)")
  class RemoveItemObjectTests {

    @Test
    @DisplayName("should call removeItemByValue with correct value")
    void removeItemDelegatesCorrectly() {
      final var returned = multiSelectCombo.removeItem(item1);

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals("value2", capturedList.get(0).getValue());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing if item is null")
    void removeNullItemObject() {
      final var returned = multiSelectCombo.removeItem(null);
      verify(multiSelectCombo, never()).removeItemByValue(any());
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing if item to remove is not in the list")
    void removeNonExistentItemObject() {
      final var nonExistentItem = MultiSelectComboItem.builder()
        .label("Non Existent")
        .value("non_existent_value")
        .build();
      final var returned = multiSelectCombo.removeItem(nonExistentItem);
      verify(multiSelectCombo).removeItemByValue("non_existent_value");
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }
  }

  @Nested
  @DisplayName("removeItemByValue(String value)")
  class RemoveItemByValueTests {

    @Test
    @DisplayName("should remove item by value")
    void removeItemValue() {
      final var returned = multiSelectCombo.removeItemByValue("value1");

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals("value2", capturedList.get(0).getValue());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should remove correct item if multiple have same label but different value")
    void removeByDistinctValue() {
      final var item1b = MultiSelectComboItem.builder().label("Label 1").value("value1b").build();
      multiSelectCombo.addItems(item1b);
      clearInvocations(multiSelectCombo);

      multiSelectCombo.removeItemByValue("value1");

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(2, capturedList.size());
      assertTrue(capturedList.stream().anyMatch(it -> "value2".equals(it.getValue())));
      assertTrue(capturedList.stream().anyMatch(it -> "value1b".equals(it.getValue())));
      assertFalse(capturedList.stream().anyMatch(it -> "value1".equals(it.getValue())));
    }


    @Test
    @DisplayName("should do nothing if value not found")
    void removeNonExistentValue() {
      final var returned = multiSelectCombo.removeItemByValue("non_existent_value");
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing if value is null")
    void removeNullValue() {
      final var returned = multiSelectCombo.removeItemByValue(null);
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing if list is empty")
    void removeFromEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);
      final var returned = multiSelectCombo.removeItemByValue("value1");
      verify(multiSelectCombo, never()).setItems(any());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should do nothing if list is null initially (handled by getItems)")
    void removeFromNullList() {
      final var comboWithNull = spy(new MultiSelectCombo());
      doReturn(null).when(comboWithNull).getItems();

      final var returned = comboWithNull.removeItemByValue("value1");

      verify(comboWithNull, never()).setItems(any());
      assertSame(comboWithNull, returned);
    }

  }

  @Nested
  @DisplayName("clearItems()")
  class ClearItemsTests {

    @Test
    @DisplayName("should set items to an empty list when clearing non-empty list")
    void clearNonEmptyList() {
      final var returned = multiSelectCombo.clearItems();

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertNotNull(capturedList);
      assertTrue(capturedList.isEmpty());
      assertSame(multiSelectCombo, returned);
    }

    @Test
    @DisplayName("should set items to an empty list when clearing an empty list")
    void clearEmptyList() {
      multiSelectCombo.setItems(new ArrayList<>());
      clearInvocations(multiSelectCombo);

      final var returned = multiSelectCombo.clearItems();

      verify(multiSelectCombo).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertNotNull(capturedList);
      assertTrue(capturedList.isEmpty());
      assertSame(multiSelectCombo, returned);
    }
  }
}
