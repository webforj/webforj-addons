package com.webforj.addons.components.sidemenu;

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

@DisplayName("SideMenu Item Manipulation Tests")
@ExtendWith(MockitoExtension.class)
class SideMenuTest {

  @Spy SideMenu sideMenu = new SideMenu();

  @Captor ArgumentCaptor<List<SideMenuItem>> itemsListCaptor;

  private SideMenuItem item1;
  private SideMenuItem item2;
  private SideMenuItem item3;
  private SideMenuItem child11;
  private SideMenuItem grandchild111;

  @BeforeEach
  void setUp() {
    item1 = SideMenuItem.builder().id("item-1").label("Item 1").build();
    child11 = SideMenuItem.builder().id("child-1-1").label("Child 1.1").build();
    grandchild111 = SideMenuItem.builder().id("grandchild-1-1-1").label("Grandchild 1.1.1").build();
    item1 =
        new SideMenuItem(item1)
            .setChildren(
                Collections.singletonList(
                    new SideMenuItem(child11)
                        .setChildren(Collections.singletonList(grandchild111))));
    item2 = SideMenuItem.builder().id("item-2").label("Item 2").build();
    item3 = SideMenuItem.builder().id("item-3").label("Item 3").build();

    sideMenu.setItems(new ArrayList<>(Arrays.asList(item1, item2)));
    clearInvocations(sideMenu);
  }

  private Optional<SideMenuItem> findInList(List<SideMenuItem> items, String id) {
    if (items == null) {
      return Optional.empty();
    }
    for (SideMenuItem item : items) {
      if (item.getId().equals(id)) {
        return Optional.of(item);
      }
      final var foundInChildren = findInList(item.getChildren(), id);
      if (foundInChildren.isPresent()) {
        return foundInChildren;
      }
    }
    return Optional.empty();
  }

  @Nested
  @DisplayName("addItem(SideMenuItem item)")
  class AddSingleItemTests {

    @Test
    @DisplayName("should add item to an empty list")
    void addItemToEmptyList() {
      sideMenu.setItems(new ArrayList<>());
      clearInvocations(sideMenu);

      final var returned = sideMenu.addItem(item3);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      List<SideMenuItem> capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals(item3, capturedList.get(0));
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should add item to the end of an existing list")
    void addItemToExistingList() {
      sideMenu.addItem(item3);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(3, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
    }

    @Test
    @DisplayName("should throw NullPointerException when adding null item")
    void addNullItem() {
      assertThrows(NullPointerException.class, () -> sideMenu.addItem(null), "Item cannot be null");
      verify(sideMenu, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItem(String parentId, SideMenuItem childItem)")
  class AddChildItemTests {

    private SideMenuItem newChild;

    @BeforeEach
    void setupChild() {
      newChild = SideMenuItem.builder().id("new-child").label("New Child").build();
    }

    @Test
    @DisplayName("should add child to a top-level parent")
    void addChildToTopLevel() {
      final var returned = sideMenu.addItem("item-1", newChild);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();

      assertEquals(2, capturedList.size(), "Top-level size should remain the same");

      final var updatedParentOpt = findInList(capturedList, "item-1");
      assertTrue(updatedParentOpt.isPresent(), "Parent item-1 should still exist");
      final var updatedParent = updatedParentOpt.get();

      assertEquals(
          2, updatedParent.getChildren().size(), "Parent item-1 should have 2 children now");
      assertTrue(
          findInList(updatedParent.getChildren(), "child-1-1").isPresent(),
          "Original child should exist");
      assertTrue(
          findInList(updatedParent.getChildren(), "new-child").isPresent(),
          "New child should exist");

      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should add child to a nested parent")
    void addChildToNestedParent() {
      final var returned = sideMenu.addItem("child-1-1", newChild);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();

      Optional<SideMenuItem> topParentOpt = findInList(capturedList, "item-1");
      assertTrue(topParentOpt.isPresent());
      SideMenuItem topParent = topParentOpt.get();
      assertNotSame(item1, topParent, "Top parent should be recreated due to child modification");

      Optional<SideMenuItem> nestedParentOpt = findInList(topParent.getChildren(), "child-1-1");
      assertTrue(nestedParentOpt.isPresent());
      SideMenuItem nestedParent = nestedParentOpt.get();
      assertNotSame(child11, nestedParent, "Nested parent should be recreated");

      assertEquals(
          2, nestedParent.getChildren().size(), "Nested parent should have 2 children now");
      assertTrue(
          findInList(nestedParent.getChildren(), "grandchild-1-1-1").isPresent(),
          "Original grandchild should exist");
      assertTrue(
          findInList(nestedParent.getChildren(), "new-child").isPresent(),
          "New child should exist");

      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException if parentId not found")
    void addChildToNonExistentParent() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sideMenu.addItem("non-existent-id", newChild),
          "Parent item with ID 'non-existent-id' not found.");
      verify(sideMenu, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if parentId is null")
    void addChildWithNullParentId() {
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItem(null, newChild),
          "Parent ID cannot be null");
      verify(sideMenu, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if childItem is null")
    void addChildWithNullChildItem() {
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItem("item-1", null),
          "Child item cannot be null");
      verify(sideMenu, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItems(SideMenuItem... items)")
  class AddVarArgsItemsTests {

    @Test
    @DisplayName("should add multiple items to an empty list")
    void addItemsToEmptyList() {
      sideMenu.setItems(new ArrayList<>());
      clearInvocations(sideMenu);

      final var returned = sideMenu.addItems(item1, item2);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(2, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should add multiple items to an existing list")
    void addItemsToExistingList() {
      final var item4 = SideMenuItem.builder().id("item-4").label("Item 4").build();
      final var returned = sideMenu.addItems(item3, item4);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(4, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
      assertEquals(item4, capturedList.get(3));
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should do nothing when adding empty varargs")
    void addEmptyVarArgs() {
      final var returned = sideMenu.addItems();
      verify(sideMenu, never()).setItems(any());
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException if items array is null")
    void addNullVarArgsArray() {
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItems((SideMenuItem[]) null),
          "Items array cannot be null");
      verify(sideMenu, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if items array contains null")
    void addVarArgsWithNullElement() {
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItems(item1, null, item2),
          "Items array cannot contain null elements");
      verify(sideMenu, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("addItems(Collection<SideMenuItem> items)")
  class AddCollectionItemsTests {

    @Test
    @DisplayName("should add collection items to an empty list")
    void addCollectionToEmptyList() {
      sideMenu.setItems(new ArrayList<>());
      clearInvocations(sideMenu);
      final var itemsToAdd = Arrays.asList(item1, item2);

      final var returned = sideMenu.addItems(itemsToAdd);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(2, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should add collection items to an existing list")
    void addCollectionToExistingList() {
      final var item4 = SideMenuItem.builder().id("item-4").label("Item 4").build();
      final var itemsToAdd = Arrays.asList(item3, item4);
      final var returned = sideMenu.addItems(itemsToAdd);

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(4, capturedList.size());
      assertEquals(item1, capturedList.get(0));
      assertEquals(item2, capturedList.get(1));
      assertEquals(item3, capturedList.get(2));
      assertEquals(item4, capturedList.get(3));
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should do nothing when adding empty collection")
    void addEmptyCollection() {
      final var returned = sideMenu.addItems(new ArrayList<>());
      verify(sideMenu, never()).setItems(any());
      assertSame(sideMenu, returned);
    }

    @Test
    @DisplayName("should throw NullPointerException if collection is null")
    void addNullCollection() {
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItems((Collection<SideMenuItem>) null),
          "Items collection cannot be null");
      verify(sideMenu, never()).setItems(any());
    }

    @Test
    @DisplayName("should throw NullPointerException if collection contains null")
    void addCollectionWithNullElement() {
      final var itemsToAdd = Arrays.asList(item1, null, item2);
      assertThrows(
          NullPointerException.class,
          () -> sideMenu.addItems(itemsToAdd),
          "Items collection cannot contain null elements");
      verify(sideMenu, never()).setItems(any());
    }
  }

  @Nested
  @DisplayName("removeItem(SideMenuItem item)")
  class RemoveItemObjectTests {

    @Test
    @DisplayName("should call removeItemById with correct ID and return true when successful")
    void removeItemDelegatesCorrectly() {
      final var returned = sideMenu.removeItem(item1);

      verify(sideMenu).removeItemById(item1.getId());
      assertTrue(returned, "Should return true when item was removed");
    }

    @Test
    @DisplayName("should return false if item is null")
    void removeNullItemObject() {
      final var returned = sideMenu.removeItem(null);
      verify(sideMenu, never()).removeItemById(any());
      verify(sideMenu, never()).setItems(any());
      assertFalse(returned, "Should return false when item is null");
    }
  }

  @Nested
  @DisplayName("removeItemById(String itemId)")
  class RemoveItemIdTests {

    @Test
    @DisplayName("should return true when removing top-level item")
    void removeTopLevelItem() {
      final var returned = sideMenu.removeItemById("item-2");

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertEquals(1, capturedList.size());
      assertEquals("item-1", capturedList.get(0).getId());
      assertSame(item1, capturedList.get(0), "Item 1 should be original instance");
      assertTrue(returned, "Should return true when item was removed");
    }

    @Test
    @DisplayName("should return true when removing nested item")
    void removeNestedItem() {
      final var returned = sideMenu.removeItemById("child-1-1");

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();

      assertEquals(2, capturedList.size());

      Optional<SideMenuItem> topParentOpt = findInList(capturedList, "item-1");
      assertTrue(topParentOpt.isPresent());
      SideMenuItem topParent = topParentOpt.get();
      assertNotSame(item1, topParent, "Top parent should be recreated");

      assertEquals(0, topParent.getChildren().size(), "Item 1 should have no children now");

      Optional<SideMenuItem> otherItemOpt = findInList(capturedList, "item-2");
      assertTrue(otherItemOpt.isPresent());
      assertSame(item2, otherItemOpt.get(), "Item 2 should be original instance");

      assertTrue(returned, "Should return true when item was removed");
    }

    @Test
    @DisplayName("should return true when removing deeply nested item")
    void removeDeeplyNestedItem() {
      final var returned = sideMenu.removeItemById("grandchild-1-1-1");

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();

      assertEquals(2, capturedList.size());

      Optional<SideMenuItem> topParentOpt = findInList(capturedList, "item-1");
      assertTrue(topParentOpt.isPresent());
      SideMenuItem topParent = topParentOpt.get();
      assertNotSame(item1, topParent, "Top parent should be recreated");

      Optional<SideMenuItem> nestedParentOpt = findInList(topParent.getChildren(), "child-1-1");
      assertTrue(nestedParentOpt.isPresent());
      SideMenuItem nestedParent = nestedParentOpt.get();
      assertNotSame(child11, nestedParent, "Nested parent should be recreated");

      assertEquals(
          0, nestedParent.getChildren().size(), "Nested parent should have no children now");

      assertTrue(returned, "Should return true when item was removed");
    }

    @Test
    @DisplayName("should return false if item ID not found")
    void removeNonExistentItem() {
      final var returned = sideMenu.removeItemById("non-existent-id");
      verify(sideMenu, never()).setItems(any());
      assertFalse(returned, "Should return false when item was not found");
    }

    @Test
    @DisplayName("should return false if item ID is null")
    void removeNullItemId() {
      final var returned = sideMenu.removeItemById(null);
      verify(sideMenu, never()).setItems(any());
      assertFalse(returned, "Should return false when item ID is null");
    }
  }

  @Nested
  @DisplayName("findItemById(String itemId)")
  class FindItemByIdTests {

    @Test
    @DisplayName("should find a top-level item")
    void findTopLevelItem() {
      Optional<SideMenuItem> found = sideMenu.findItemById("item-2");
      assertTrue(found.isPresent(), "Item 2 should be found");
      assertEquals("item-2", found.get().getId());
      assertSame(item2, found.get(), "Should return the correct instance");
    }

    @Test
    @DisplayName("should find a nested item")
    void findNestedItem() {
      Optional<SideMenuItem> found = sideMenu.findItemById("child-1-1");
      assertTrue(found.isPresent(), "Child 1.1 should be found");
      assertEquals("child-1-1", found.get().getId());
    }

    @Test
    @DisplayName("should find a deeply nested item")
    void findDeeplyNestedItem() {
      Optional<SideMenuItem> found = sideMenu.findItemById("grandchild-1-1-1");
      assertTrue(found.isPresent(), "Grandchild 1.1.1 should be found");
      assertEquals("grandchild-1-1-1", found.get().getId());
    }

    @Test
    @DisplayName("should return empty Optional if ID not found")
    void findNonExistentItem() {
      Optional<SideMenuItem> found = sideMenu.findItemById("non-existent-id");
      assertFalse(found.isPresent(), "Should not find non-existent ID");
    }

    @Test
    @DisplayName("should return empty Optional if ID is null")
    void findNullId() {
      Optional<SideMenuItem> found = sideMenu.findItemById(null);
      assertFalse(found.isPresent(), "Should return empty for null ID");
    }

    @Test
    @DisplayName("should return empty Optional if items list is empty")
    void findInEmptyList() {
      sideMenu.setItems(new ArrayList<>());
      Optional<SideMenuItem> found = sideMenu.findItemById("item-1");
      assertFalse(found.isPresent(), "Should not find anything in an empty list");
    }
  }

  @Nested
  @DisplayName("clearItems()")
  class ClearItemsTests {

    @Test
    @DisplayName("should return true when clearing non-empty list")
    void clearNonEmptyList() {
      final var returned = sideMenu.clearItems();

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertNotNull(capturedList);
      assertTrue(capturedList.isEmpty());
      assertTrue(returned, "Should return true when items were present and cleared");
    }

    @Test
    @DisplayName("should return false when clearing an empty list")
    void clearEmptyList() {
      sideMenu.setItems(new ArrayList<>());
      clearInvocations(sideMenu);

      final var returned = sideMenu.clearItems();

      verify(sideMenu).setItems(itemsListCaptor.capture());
      final var capturedList = itemsListCaptor.getValue();
      assertNotNull(capturedList);
      assertTrue(capturedList.isEmpty());
      assertFalse(returned, "Should return false when no items were present to clear");
    }
  }
}
