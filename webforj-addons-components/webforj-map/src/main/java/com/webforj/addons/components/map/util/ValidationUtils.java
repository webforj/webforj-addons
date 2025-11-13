package com.webforj.addons.components.map.util;

/**
 * Utility class for common validation operations used across map configuration builders.
 *
 * <p>Provides centralized validation logic to reduce code duplication and ensure consistent
 * validation behavior across different builder classes.
 *
 * @since 25.03
 */
public final class ValidationUtils {

  /** Minimum allowed zoom level. */
  public static final int MIN_ZOOM_LEVEL = 0;

  /** Maximum allowed zoom level. */
  public static final int MAX_ZOOM_LEVEL = 28;

  private ValidationUtils() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  /**
   * Validates that a zoom level is within the allowed range (0-28).
   *
   * @param zoom the zoom level to validate
   * @param fieldName the name of the field being validated (for error messages)
   * @throws IllegalArgumentException if zoom is not null and is outside the valid range
   */
  public static void validateZoomLevel(Double zoom, String fieldName) {
    if (zoom != null && (zoom < MIN_ZOOM_LEVEL || zoom > MAX_ZOOM_LEVEL)) {
      throw new IllegalArgumentException(
          String.format(
              "%s must be between %d and %d: %.2f",
              fieldName, MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL, zoom));
    }
  }

  /**
   * Validates that min and max zoom levels are correctly ordered.
   *
   * @param minZoom the minimum zoom level
   * @param maxZoom the maximum zoom level
   * @throws IllegalArgumentException if both are set and minZoom &gt; maxZoom
   */
  public static void validateZoomRange(Double minZoom, Double maxZoom) {
    if (minZoom != null && maxZoom != null && minZoom > maxZoom) {
      throw new IllegalArgumentException(
          String.format(
              "Min zoom (%.2f) cannot be greater than max zoom (%.2f)", minZoom, maxZoom));
    }
  }

  /**
   * Validates that a zoom level is within the specified min/max range.
   *
   * @param zoom the zoom level to validate
   * @param minZoom the minimum allowed zoom level (optional)
   * @param maxZoom the maximum allowed zoom level (optional)
   * @throws IllegalArgumentException if zoom is outside the specified range
   */
  public static void validateZoomInRange(Double zoom, Double minZoom, Double maxZoom) {
    if (zoom != null) {
      if (minZoom != null && zoom < minZoom) {
        throw new IllegalArgumentException(
            String.format("Zoom (%.2f) cannot be less than min zoom (%.2f)", zoom, minZoom));
      }
      if (maxZoom != null && zoom > maxZoom) {
        throw new IllegalArgumentException(
            String.format("Zoom (%.2f) cannot be greater than max zoom (%.2f)", zoom, maxZoom));
      }
    }
  }
}
