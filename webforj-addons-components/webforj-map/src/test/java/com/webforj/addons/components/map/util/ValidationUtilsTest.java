package com.webforj.addons.components.map.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ValidationUtils Tests")
class ValidationUtilsTest {

  @Nested
  @DisplayName("validateZoomLevel Tests")
  class ValidateZoomLevelTests {

    @Test
    @DisplayName("should accept null zoom level")
    void acceptNullZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomLevel(null, "Test zoom"));
    }

    @Test
    @DisplayName("should accept zoom at minimum and maximum boundary")
    void acceptMinMaxZoom() {
      assertDoesNotThrow(
          () ->
              ValidationUtils.validateZoomLevel(
                  (double) ValidationUtils.MIN_ZOOM_LEVEL, "Test zoom"));
      assertDoesNotThrow(
          () ->
              ValidationUtils.validateZoomLevel(
                  (double) ValidationUtils.MAX_ZOOM_LEVEL, "Test zoom"));
      assertDoesNotThrow(() -> ValidationUtils.validateZoomLevel(10.0, "Test zoom"));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for zoom below minimum")
    void rejectZoomBelowMin() {
      assertThrows(
          IllegalArgumentException.class,
          () ->
              ValidationUtils.validateZoomLevel(
                  (double) ValidationUtils.MIN_ZOOM_LEVEL - 1, "Test zoom"));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException for zoom above maximum")
    void rejectZoomAboveMax() {
      assertThrows(
          IllegalArgumentException.class,
          () ->
              ValidationUtils.validateZoomLevel(
                  (double) ValidationUtils.MAX_ZOOM_LEVEL + 1, "Test zoom"));
    }
  }

  @Nested
  @DisplayName("validateZoomRange Tests")
  class ValidateZoomRangeTests {

    @Test
    @DisplayName("should accept when both minZoom and maxZoom are null")
    void acceptBothNull() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomRange(null, null));
    }

    @Test
    @DisplayName("should accept when only minZoom is set")
    void acceptOnlyMinZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomRange(5.0, null));
    }

    @Test
    @DisplayName("should accept when only maxZoom is set")
    void acceptOnlyMaxZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomRange(null, 15.0));
    }

    @Test
    @DisplayName("should accept when minZoom <= maxZoom")
    void acceptValidRange() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomRange(5.0, 15.0));
      assertDoesNotThrow(() -> ValidationUtils.validateZoomRange(10.0, 10.0));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when minZoom > maxZoom")
    void rejectInvalidRange() {
      assertThrows(
          IllegalArgumentException.class, () -> ValidationUtils.validateZoomRange(15.0, 5.0));
    }
  }

  @Nested
  @DisplayName("validateZoomInRange Tests")
  class ValidateZoomInRangeTests {

    @Test
    @DisplayName("should accept null zoom")
    void acceptNullZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(null, 5.0, 15.0));
    }

    @Test
    @DisplayName("should accept zoom within range and at boundaries")
    void acceptZoomInRange() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(10.0, 5.0, 15.0));
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(5.0, 5.0, 15.0));
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(15.0, 5.0, 15.0));
    }

    @Test
    @DisplayName("should accept zoom when only minZoom is set")
    void acceptWithOnlyMinZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(10.0, 5.0, null));
    }

    @Test
    @DisplayName("should accept zoom when only maxZoom is set")
    void acceptWithOnlyMaxZoom() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(10.0, null, 15.0));
    }

    @Test
    @DisplayName("should accept zoom when neither min nor max are set")
    void acceptWithNoConstraints() {
      assertDoesNotThrow(() -> ValidationUtils.validateZoomInRange(10.0, null, null));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when zoom < minZoom")
    void rejectZoomBelowMin() {
      assertThrows(
          IllegalArgumentException.class,
          () -> ValidationUtils.validateZoomInRange(3.0, 5.0, 15.0));
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when zoom > maxZoom")
    void rejectZoomAboveMax() {
      assertThrows(
          IllegalArgumentException.class,
          () -> ValidationUtils.validateZoomInRange(20.0, 5.0, 15.0));
    }
  }
}
