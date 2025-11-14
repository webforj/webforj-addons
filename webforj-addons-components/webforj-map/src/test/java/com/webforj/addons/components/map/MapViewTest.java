package com.webforj.addons.components.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MapView Tests")
class MapViewTest {

  private static final double DELTA = 0.0001;

  @Nested
  @DisplayName("Builder Tests")
  class BuilderTests {

    @Test
    @DisplayName("should build with no configuration")
    void buildEmpty() {
      MapView view = MapView.builder().build();

      assertNotNull(view);
      assertNull(view.getCenter());
      assertNull(view.getZoom());
      assertNull(view.getMinZoom());
      assertNull(view.getMaxZoom());
      assertNull(view.getExtent());
      assertNull(view.getRotation());
    }

    @Test
    @DisplayName("should build with all properties")
    void buildWithAllProperties() {
      Coordinate center = new Coordinate(-74.006, 40.7128);
      Extent extent = new Extent(-74.1, 40.6, -73.9, 40.8);

      MapView view =
          MapView.builder()
              .center(center)
              .zoom(10)
              .minZoom(5)
              .maxZoom(15)
              .extent(extent)
              .rotation(Math.PI / 4)
              .build();

      assertEquals(center, view.getCenter());
      assertEquals(10, view.getZoom());
      assertEquals(5, view.getMinZoom());
      assertEquals(15, view.getMaxZoom());
      assertEquals(extent, view.getExtent());
      assertEquals(Math.PI / 4, view.getRotation(), DELTA);
    }

    @Test
    @DisplayName("should build center from longitude and latitude")
    void buildCenterFromCoordinates() {
      MapView view = MapView.builder().center(-74.006, 40.7128).build();

      assertNotNull(view.getCenter());
      assertEquals(-74.006, view.getCenter().getLongitude(), DELTA);
      assertEquals(40.7128, view.getCenter().getLatitude(), DELTA);
    }

    @Test
    @DisplayName("should convert rotation from degrees to radians")
    void buildRotationInDegrees() {
      MapView view = MapView.builder().rotationDegrees(45.0).build();

      assertEquals(Math.PI / 4, view.getRotation(), DELTA);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for negative zoom")
    void rejectNegativeZoom() {
      MapView.Builder builder = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder.zoom(-1));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for invalid minZoom")
    void rejectInvalidMinZoom() {
      MapView.Builder builder1 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder1.minZoom(-1));

      MapView.Builder builder2 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder2.minZoom(29));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for invalid maxZoom")
    void rejectInvalidMaxZoom() {
      MapView.Builder builder1 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder1.maxZoom(-1));

      MapView.Builder builder2 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder2.maxZoom(29));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when minZoom > maxZoom")
    void rejectInvalidZoomRange() {
      MapView.Builder builder = MapView.builder().minZoom(15).maxZoom(10);
      assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when zoom < minZoom")
    void rejectZoomBelowMin() {
      MapView.Builder builder = MapView.builder().zoom(5).minZoom(10);
      assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when zoom > maxZoom")
    void rejectZoomAboveMax() {
      MapView.Builder builder = MapView.builder().zoom(15).maxZoom(10);
      assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for non-finite rotation")
    void rejectNonFiniteRotation() {
      MapView.Builder builder1 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder1.rotation(Double.NaN));

      MapView.Builder builder2 = MapView.builder();
      assertThrows(
          IllegalArgumentException.class, () -> builder2.rotation(Double.POSITIVE_INFINITY));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for non-finite rotation degrees")
    void rejectNonFiniteRotationDegrees() {
      MapView.Builder builder1 = MapView.builder();
      assertThrows(IllegalArgumentException.class, () -> builder1.rotationDegrees(Double.NaN));

      MapView.Builder builder2 = MapView.builder();
      assertThrows(
          IllegalArgumentException.class, () -> builder2.rotationDegrees(Double.NEGATIVE_INFINITY));
    }
  }

  @Nested
  @DisplayName("toBuilder Tests")
  class ToBuilderTests {

    @Test
    @DisplayName("should create builder with current view properties")
    void copyToBuilder() {
      Coordinate center = new Coordinate(-74.006, 40.7128);
      MapView original =
          MapView.builder().center(center).zoom(10).minZoom(5).maxZoom(15).rotation(1.0).build();

      MapView copy = original.toBuilder().build();

      assertEquals(original.getCenter(), copy.getCenter());
      assertEquals(original.getZoom(), copy.getZoom());
      assertEquals(original.getMinZoom(), copy.getMinZoom());
      assertEquals(original.getMaxZoom(), copy.getMaxZoom());
      assertEquals(original.getRotation(), copy.getRotation());
    }

    @Test
    @DisplayName("should allow modifications to copied builder")
    void modifyCopiedBuilder() {
      MapView original = MapView.builder().zoom(10).minZoom(5).maxZoom(20).build();
      MapView modified = original.toBuilder().zoom(15).build();

      assertEquals(10, original.getZoom());
      assertEquals(15, modified.getZoom());
    }
  }
}
