package com.webforj.addons.components.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Extent Tests")
class ExtentTest {

  private static final double DELTA = 0.0001;

  @Nested
  @DisplayName("Constructor Tests")
  class ConstructorTests {

    @Test
    @DisplayName("should create extent with valid bounds")
    void createValidExtent() {
      Extent extent = new Extent(-74.1, 40.6, -73.9, 40.8);

      assertEquals(-74.1, extent.getMinLongitude(), DELTA);
      assertEquals(40.6, extent.getMinLatitude(), DELTA);
      assertEquals(-73.9, extent.getMaxLongitude(), DELTA);
      assertEquals(40.8, extent.getMaxLatitude(), DELTA);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when minLongitude >= maxLongitude")
    void rejectInvalidLongitudeRange() {
      assertThrows(IllegalArgumentException.class, () -> new Extent(-73.9, 40.6, -74.1, 40.8));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when minLatitude >= maxLatitude")
    void rejectInvalidLatitudeRange() {
      assertThrows(IllegalArgumentException.class, () -> new Extent(-74.1, 40.8, -73.9, 40.6));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for invalid coordinate values")
    void rejectInvalidCoordinates() {
      assertThrows(IllegalArgumentException.class, () -> new Extent(-200, 40.6, -73.9, 40.8));
      assertThrows(IllegalArgumentException.class, () -> new Extent(-74.1, 100, -73.9, 40.8));
      assertThrows(IllegalArgumentException.class, () -> new Extent(-74.1, 40.6, 200, 40.8));
      assertThrows(IllegalArgumentException.class, () -> new Extent(-74.1, 40.6, -73.9, 100));
    }
  }

  @Nested
  @DisplayName("Factory Method Tests")
  class FactoryMethodTests {

    @Test
    @DisplayName("fromCorners() should create extent from bottom-left and top-right coordinates")
    void createFromCorners() {
      Coordinate bottomLeft = new Coordinate(-74.1, 40.6);
      Coordinate topRight = new Coordinate(-73.9, 40.8);

      Extent extent = Extent.fromCorners(bottomLeft, topRight);

      assertEquals(-74.1, extent.getMinLongitude(), DELTA);
      assertEquals(40.6, extent.getMinLatitude(), DELTA);
      assertEquals(-73.9, extent.getMaxLongitude(), DELTA);
      assertEquals(40.8, extent.getMaxLatitude(), DELTA);
    }

    @Test
    @DisplayName("fromCorners() should throw NullPointerException for null arguments")
    void rejectNullArguments() {
      Coordinate topRight = new Coordinate(-73.9, 40.8);
      Coordinate bottomLeft = new Coordinate(-74.1, 40.6);

      assertThrows(NullPointerException.class, () -> Extent.fromCorners(null, topRight));
      assertThrows(NullPointerException.class, () -> Extent.fromCorners(bottomLeft, null));
    }

    @Test
    @DisplayName("fromCenter() should create extent with specified width and height")
    void createFromCenter() {
      Coordinate center = new Coordinate(-74.0, 40.7);
      double width = 0.2;
      double height = 0.2;

      Extent extent = Extent.fromCenter(center, width, height);

      assertEquals(-74.1, extent.getMinLongitude(), DELTA);
      assertEquals(40.6, extent.getMinLatitude(), DELTA);
      assertEquals(-73.9, extent.getMaxLongitude(), DELTA);
      assertEquals(40.8, extent.getMaxLatitude(), DELTA);
    }

    @Test
    @DisplayName("fromCenter() should throw NullPointerException for invalid arguments")
    void rejectNullCenter() {
      Coordinate center = new Coordinate(-74.0, 40.7);
      assertThrows(NullPointerException.class, () -> Extent.fromCenter(null, 1.0, 1.0));
      assertThrows(IllegalArgumentException.class, () -> Extent.fromCenter(center, 0.0, 1.0));
      assertThrows(IllegalArgumentException.class, () -> Extent.fromCenter(center, 1.0, -1.0));
    }
  }

  @Nested
  @DisplayName("Derived Properties Tests")
  class DerivedPropertiesTests {

    private final Extent extent = new Extent(-74.1, 40.6, -73.9, 40.8);

    @Test
    @DisplayName("getBottomLeft() should return southwest corner coordinate")
    void getBottomLeft() {
      Coordinate bottomLeft = extent.getBottomLeft();

      assertEquals(-74.1, bottomLeft.getLongitude(), DELTA);
      assertEquals(40.6, bottomLeft.getLatitude(), DELTA);
    }

    @Test
    @DisplayName("getTopRight() should return northeast corner coordinate")
    void getTopRight() {
      Coordinate topRight = extent.getTopRight();

      assertEquals(-73.9, topRight.getLongitude(), DELTA);
      assertEquals(40.8, topRight.getLatitude(), DELTA);
    }

    @Test
    @DisplayName("getCenter() should return center coordinate")
    void getCenter() {
      Coordinate center = extent.getCenter();

      assertEquals(-74.0, center.getLongitude(), DELTA);
      assertEquals(40.7, center.getLatitude(), DELTA);
    }

    @Test
    @DisplayName("getWidth() should return longitude difference")
    void getWidth() {
      double width = extent.getWidth();

      assertEquals(0.2, width, DELTA);
    }

    @Test
    @DisplayName("getHeight() should return latitude difference")
    void getHeight() {
      double height = extent.getHeight();

      assertEquals(0.2, height, DELTA);
    }
  }
}
