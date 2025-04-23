package com.webforj.addons.components.sidemenu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("SideMenuItem Tests")
class SideMenuItemTest {

  private static final String DEFAULT_ID = "item-1";
  private static final String DEFAULT_LABEL = "Item 1";

  @Nested
  @DisplayName("Builder Tests")
  class BuilderTests {

    @Test
    @DisplayName("should build item with only mandatory fields")
    void buildWithMandatoryFields() {
      SideMenuItem item = SideMenuItem.builder().id(DEFAULT_ID).label(DEFAULT_LABEL).build();

      assertNotNull(item);
      assertEquals(DEFAULT_ID, item.getId());
      assertEquals(DEFAULT_LABEL, item.getLabel());
      assertNull(item.getIcon());
      assertNull(item.getLink());
      assertFalse(item.isNewTab());
      assertNull(item.getShortcut());
      assertFalse(item.isFavorite());
      assertNotNull(item.getChildren());
      assertTrue(item.getChildren().isEmpty());
    }

    @Test
    @DisplayName("should build item with all optional fields")
    void buildWithAllFields() {
      final var icon = "feather:home";
      final var link = "/home";
      final var newTab = true;
      final var shortcut = "Ctrl+H";
      final var favorite = true;
      final var child1 = SideMenuItem.builder().id("child-1").label("Child 1").build();
      final var children = Collections.singletonList(child1);

      final var item =
          SideMenuItem.builder()
              .id(DEFAULT_ID)
              .label(DEFAULT_LABEL)
              .icon(icon)
              .link(link)
              .newTab(newTab)
              .shortcut(shortcut)
              .favorite(favorite)
              .children(children)
              .build();

      assertNotNull(item);
      assertEquals(DEFAULT_ID, item.getId());
      assertEquals(DEFAULT_LABEL, item.getLabel());
      assertEquals(icon, item.getIcon());
      assertEquals(link, item.getLink());
      assertEquals(newTab, item.isNewTab());
      assertEquals(shortcut, item.getShortcut());
      assertEquals(favorite, item.isFavorite());
      assertNotNull(item.getChildren());
      assertEquals(1, item.getChildren().size());
      assertEquals(child1, item.getChildren().get(0));
    }

    @Test
    @DisplayName("should throw NullPointerException when mandatory ID is null")
    void buildWithNullId() {
      final var builder = SideMenuItem.builder();
      final var exception =
          assertThrows(
              NullPointerException.class,
              () -> {
                builder.id(null);
              });
      assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("should throw NullPointerException when mandatory label is null")
    void buildWithNullLabel() {
      final var builderStep1 = SideMenuItem.builder();
      final var builderStep2 = builderStep1.id(DEFAULT_ID);
      final var exception =
          assertThrows(
              NullPointerException.class,
              () -> {
                builderStep2.label(null);
              });
      assertEquals("Label cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("should handle null children list by defaulting to empty")
    void buildWithNullChildren() {
      final var item =
          SideMenuItem.builder().id(DEFAULT_ID).label(DEFAULT_LABEL).children(null).build();

      assertNotNull(item.getChildren());
      assertTrue(item.getChildren().isEmpty());
    }

    @Test
    @DisplayName("should handle empty children list")
    void buildWithEmptyChildren() {
      final var item =
          SideMenuItem.builder()
              .id(DEFAULT_ID)
              .label(DEFAULT_LABEL)
              .children(new ArrayList<>())
              .build();

      assertNotNull(item.getChildren());
      assertTrue(item.getChildren().isEmpty());
    }

    @Test
    @DisplayName("should create internal copy when building with children list")
    void buildWithChildrenCreatesCopy() {
      final var child1 = SideMenuItem.builder().id("c1").label("C1").build();
      final var originalChildren = new ArrayList<>(Collections.singletonList(child1));

      final var item =
          SideMenuItem.builder()
              .id(DEFAULT_ID)
              .label(DEFAULT_LABEL)
              .children(originalChildren)
              .build();

      originalChildren.add(SideMenuItem.builder().id("c2").label("C2").build());

      assertNotNull(item.getChildren());
      assertEquals(1, item.getChildren().size());
      assertEquals("c1", item.getChildren().get(0).getId());
    }
  }

  @Nested
  @DisplayName("Copy Constructor Tests")
  class CopyConstructorTests {

    private SideMenuItem sourceItem;

    @BeforeEach
    void setUpSourceItem() {
      final var child = SideMenuItem.builder().id("child-1").label("Child 1").build();
      sourceItem =
          SideMenuItem.builder()
              .id(DEFAULT_ID)
              .label(DEFAULT_LABEL)
              .icon("feather:copy")
              .link("/copy")
              .newTab(true)
              .shortcut("Ctrl+C")
              .favorite(true)
              .children(Collections.singletonList(child))
              .build();
    }

    @Test
    @DisplayName("should correctly copy all properties from source item")
    void copyAllProperties() {
      final var copiedItem = new SideMenuItem(sourceItem);

      assertEquals(sourceItem.getId(), copiedItem.getId());
      assertEquals(sourceItem.getLabel(), copiedItem.getLabel());
      assertEquals(sourceItem.getIcon(), copiedItem.getIcon());
      assertEquals(sourceItem.getLink(), copiedItem.getLink());
      assertEquals(sourceItem.isNewTab(), copiedItem.isNewTab());
      assertEquals(sourceItem.getShortcut(), copiedItem.getShortcut());
      assertEquals(sourceItem.isFavorite(), copiedItem.isFavorite());
      assertNotNull(copiedItem.getChildren());
      assertEquals(sourceItem.getChildren().size(), copiedItem.getChildren().size());
      assertEquals(
          sourceItem.getChildren().get(0).getId(), copiedItem.getChildren().get(0).getId());
    }

    @Test
    @DisplayName("should create a distinct object instance")
    void copyCreatesDistinctObject() {
      final var copiedItem = new SideMenuItem(sourceItem);
      assertNotSame(sourceItem, copiedItem, "Copied object should be a different instance");
    }

    @Test
    @DisplayName("should create a distinct children list instance")
    void copyCreatesDistinctChildrenList() {
      assertFalse(
          sourceItem.getChildren().isEmpty(), "Source item must have children for this test");

      final var copiedItem = new SideMenuItem(sourceItem);

      assertNotSame(
          sourceItem.getChildren(),
          copiedItem.getChildren(),
          "Copied item's children list should be a different instance");
    }

    @Test
    @DisplayName("should correctly copy item with only mandatory fields")
    void copyMinimalItem() {
      final var minimalSource =
          SideMenuItem.builder().id("minimal-id").label("Minimal Label").build();
      final var copiedItem = new SideMenuItem(minimalSource);

      assertEquals("minimal-id", copiedItem.getId());
      assertEquals("Minimal Label", copiedItem.getLabel());
      assertNull(copiedItem.getIcon());
      assertNull(copiedItem.getLink());
      assertFalse(copiedItem.isNewTab());
      assertNull(copiedItem.getShortcut());
      assertFalse(copiedItem.isFavorite());
      assertNotNull(copiedItem.getChildren());
      assertTrue(copiedItem.getChildren().isEmpty());
    }
  }

  @Nested
  @DisplayName("Setter Tests")
  class SetterTests {

    private SideMenuItem item;

    @BeforeEach
    void setUp() {
      item = SideMenuItem.builder().id(DEFAULT_ID).label(DEFAULT_LABEL).build();
    }

    @Test
    @DisplayName("setId() should throw NullPointerException for null ID")
    void setIdNull() {
      final var exception =
          assertThrows(
              NullPointerException.class,
              () -> {
                item.setId(null);
              });
      assertEquals("ID cannot be null", exception.getMessage());
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
    @DisplayName("setChildren() should throw NullPointerException for null list")
    void setChildrenNull() {
      final var exception =
          assertThrows(
              NullPointerException.class,
              () -> {
                item.setChildren(null);
              });
      assertEquals("Children list cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("setChildren() should create an internal copy of the provided list")
    void setChildrenCreatesCopy() {
      final var child1 = SideMenuItem.builder().id("c1").label("C1").build();
      final var originalChildren = new ArrayList<>(Collections.singletonList(child1));

      item.setChildren(originalChildren);

      originalChildren.add(SideMenuItem.builder().id("c2").label("C2").build());

      assertNotNull(item.getChildren());
      assertEquals(1, item.getChildren().size());
      assertEquals("c1", item.getChildren().get(0).getId());
      assertNotSame(originalChildren, item.getChildren());
    }
  }
}
