package com.webforj.addons.components.map;

import java.util.Objects;

/**
 * Geographic bounding box in EPSG:4326 (WGS84) format with [minLon, minLat, maxLon, maxLat]
 * ordering. Defines a rectangular area with validated bounds ensuring min values are less than max
 * values.
 *
 * @since 25.03
 */
public final class Extent {

  private final double minLongitude;
  private final double minLatitude;
  private final double maxLongitude;
  private final double maxLatitude;

  /**
   * Creates a new extent with the specified bounds.
   *
   * @param minLongitude the minimum longitude (-180 to 180)
   * @param minLatitude the minimum latitude (-90 to 90)
   * @param maxLongitude the maximum longitude (-180 to 180)
   * @param maxLatitude the maximum latitude (-90 to 90)
   * @throws IllegalArgumentException if any coordinate is outside valid ranges or if min values are
   *     not less than max values
   */
  public Extent(double minLongitude, double minLatitude, double maxLongitude, double maxLatitude) {
    // Validate using Coordinate constructor which will throw if invalid
    new Coordinate(minLongitude, minLatitude);
    new Coordinate(maxLongitude, maxLatitude);

    if (minLongitude >= maxLongitude) {
      throw new IllegalArgumentException(
          String.format(
              "Invalid extent: minLongitude (%.6f) must be less than maxLongitude (%.6f)",
              minLongitude, maxLongitude));
    }

    if (minLatitude >= maxLatitude) {
      throw new IllegalArgumentException(
          String.format(
              "Invalid extent: minLatitude (%.6f) must be less than maxLatitude (%.6f)",
              minLatitude, maxLatitude));
    }

    this.minLongitude = minLongitude;
    this.minLatitude = minLatitude;
    this.maxLongitude = maxLongitude;
    this.maxLatitude = maxLatitude;
  }

  /**
   * Creates a new extent from two coordinate points.
   *
   * @param bottomLeft the bottom-left (southwest) corner
   * @param topRight the top-right (northeast) corner
   * @return a new extent
   * @throws IllegalArgumentException if the coordinates do not form a valid extent
   */
  public static Extent fromCorners(Coordinate bottomLeft, Coordinate topRight) {
    Objects.requireNonNull(bottomLeft, "Bottom-left coordinate cannot be null");
    Objects.requireNonNull(topRight, "Top-right coordinate cannot be null");

    return new Extent(
        bottomLeft.getLongitude(), bottomLeft.getLatitude(),
        topRight.getLongitude(), topRight.getLatitude());
  }

  /**
   * Creates a new extent from a center coordinate and size.
   *
   * @param center the center coordinate
   * @param widthDegrees the width in degrees (longitude)
   * @param heightDegrees the height in degrees (latitude)
   * @return a new extent
   * @throws IllegalArgumentException if center is null or size values are invalid
   */
  public static Extent fromCenter(Coordinate center, double widthDegrees, double heightDegrees) {
    Objects.requireNonNull(center, "Center coordinate cannot be null");

    if (widthDegrees <= 0) {
      throw new IllegalArgumentException("Width must be positive, got: " + widthDegrees);
    }
    if (heightDegrees <= 0) {
      throw new IllegalArgumentException("Height must be positive, got: " + heightDegrees);
    }

    double halfWidth = widthDegrees / 2.0;
    double halfHeight = heightDegrees / 2.0;

    return new Extent(
        center.getLongitude() - halfWidth,
        center.getLatitude() - halfHeight,
        center.getLongitude() + halfWidth,
        center.getLatitude() + halfHeight);
  }

  /**
   * Gets the minimum longitude (west bound).
   *
   * @return the minimum longitude
   */
  public double getMinLongitude() {
    return minLongitude;
  }

  /**
   * Gets the minimum latitude (south bound).
   *
   * @return the minimum latitude
   */
  public double getMinLatitude() {
    return minLatitude;
  }

  /**
   * Gets the maximum longitude (east bound).
   *
   * @return the maximum longitude
   */
  public double getMaxLongitude() {
    return maxLongitude;
  }

  /**
   * Gets the maximum latitude (north bound).
   *
   * @return the maximum latitude
   */
  public double getMaxLatitude() {
    return maxLatitude;
  }

  /**
   * Gets the bottom-left (southwest) corner coordinate.
   *
   * @return the bottom-left coordinate
   */
  public Coordinate getBottomLeft() {
    return new Coordinate(minLongitude, minLatitude);
  }

  /**
   * Gets the top-right (northeast) corner coordinate.
   *
   * @return the top-right coordinate
   */
  public Coordinate getTopRight() {
    return new Coordinate(maxLongitude, maxLatitude);
  }

  /**
   * Gets the center coordinate of this extent.
   *
   * @return the center coordinate
   */
  public Coordinate getCenter() {
    return new Coordinate((minLongitude + maxLongitude) / 2.0, (minLatitude + maxLatitude) / 2.0);
  }

  /**
   * Gets the width of this extent in degrees.
   *
   * @return the width in longitude degrees
   */
  public double getWidth() {
    return maxLongitude - minLongitude;
  }

  /**
   * Gets the height of this extent in degrees.
   *
   * @return the height in latitude degrees
   */
  public double getHeight() {
    return maxLatitude - minLatitude;
  }

  /**
   * Converts this extent to a double array in [minX, minY, maxX, maxY] format.
   *
   * @return a new array containing the extent bounds
   */
  public double[] toArray() {
    return new double[] {minLongitude, minLatitude, maxLongitude, maxLatitude};
  }
}
