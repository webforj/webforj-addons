package com.webforj.addons.components.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Coordinate Tests")
class CoordinateTest {

  private static final double VALID_LONGITUDE = -74.006;
  private static final double VALID_LATITUDE = 40.7128;
  private static final double DELTA = 0.0001;

  @Nested
  @DisplayName("Constructor Tests")
  class ConstructorTests {

    @Test
    @DisplayName("should create coordinate with valid longitude and latitude")
    void createValidCoordinate() {
      Coordinate coordinate = new Coordinate(VALID_LONGITUDE, VALID_LATITUDE);

      assertEquals(VALID_LONGITUDE, coordinate.longitude(), DELTA);
      assertEquals(VALID_LATITUDE, coordinate.latitude(), DELTA);
    }

    @Test
    @DisplayName("should create coordinate at boundary values")
    void createCoordinateAtBoundaries() {
      Coordinate minBounds = new Coordinate(-180.0, -90.0);
      Coordinate maxBounds = new Coordinate(180.0, 90.0);

      assertEquals(-180.0, minBounds.longitude(), DELTA);
      assertEquals(-90.0, minBounds.latitude(), DELTA);
      assertEquals(180.0, maxBounds.longitude(), DELTA);
      assertEquals(90.0, maxBounds.latitude(), DELTA);
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for longitude > 180")
    void rejectLongitudeAboveMax() {
      assertThrows(IllegalArgumentException.class, () -> new Coordinate(180.1, VALID_LATITUDE));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for longitude < -180")
    void rejectLongitudeBelowMin() {
      assertThrows(IllegalArgumentException.class, () -> new Coordinate(-180.1, VALID_LATITUDE));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for latitude > 90")
    void rejectLatitudeAboveMax() {
      assertThrows(IllegalArgumentException.class, () -> new Coordinate(VALID_LONGITUDE, 90.1));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for latitude < -90")
    void rejectLatitudeBelowMin() {
      assertThrows(IllegalArgumentException.class, () -> new Coordinate(VALID_LONGITUDE, -90.1));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for NaN or Infinite values")
    void rejectNonFiniteValues() {
      assertThrows(
          IllegalArgumentException.class, () -> new Coordinate(Double.NaN, VALID_LATITUDE));
      assertThrows(
          IllegalArgumentException.class, () -> new Coordinate(VALID_LONGITUDE, Double.NaN));
      assertThrows(
          IllegalArgumentException.class,
          () -> new Coordinate(Double.POSITIVE_INFINITY, VALID_LATITUDE));
      assertThrows(
          IllegalArgumentException.class,
          () -> new Coordinate(VALID_LONGITUDE, Double.NEGATIVE_INFINITY));
    }
  }

  @Nested
  @DisplayName("toArray Tests")
  class ToArrayTests {

    @Test
    @DisplayName("should convert coordinate to array in [longitude, latitude] order")
    void convertToArray() {
      Coordinate coordinate = new Coordinate(VALID_LONGITUDE, VALID_LATITUDE);
      double[] array = coordinate.toArray();

      assertNotNull(array);
      assertEquals(2, array.length);
      assertEquals(VALID_LONGITUDE, array[0], DELTA);
      assertEquals(VALID_LATITUDE, array[1], DELTA);
    }

    @Test
    @DisplayName("should create new array instance on each call")
    void createNewArrayInstance() {
      Coordinate coordinate = new Coordinate(VALID_LONGITUDE, VALID_LATITUDE);
      double[] array1 = coordinate.toArray();
      double[] array2 = coordinate.toArray();

      assertNotSame(array1, array2, "Should return new array instance each time");
      assertArrayEquals(array1, array2, DELTA);
    }
  }
}
