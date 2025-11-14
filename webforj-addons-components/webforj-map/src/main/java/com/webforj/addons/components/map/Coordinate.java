package com.webforj.addons.components.map;

/**
 * Geographic coordinate in EPSG:4326 (WGS84) format with [longitude, latitude] ordering. Longitude
 * ranges from -180 to 180 degrees, latitude from -90 to 90 degrees.
 *
 * @since 25.03
 */
public final class Coordinate {

  private final double longitude;
  private final double latitude;

  /**
   * Creates a new coordinate with the specified longitude and latitude.
   *
   * @param longitude the longitude in degrees (-180 to 180)
   * @param latitude the latitude in degrees (-90 to 90)
   * @throws IllegalArgumentException if longitude or latitude are outside valid ranges
   */
  public Coordinate(double longitude, double latitude) {
    validateLongitude(longitude);
    validateLatitude(latitude);
    this.longitude = longitude;
    this.latitude = latitude;
  }

  /**
   * Gets the longitude value.
   *
   * @return the longitude in degrees
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Gets the latitude value.
   *
   * @return the latitude in degrees
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Converts this coordinate to a double array in [longitude, latitude] format.
   *
   * @return a new array containing [longitude, latitude]
   */
  public double[] toArray() {
    return new double[] {longitude, latitude};
  }

  /**
   * Validates that longitude is within the valid range.
   *
   * @param longitude the longitude to validate
   * @throws IllegalArgumentException if longitude is outside valid range
   */
  private void validateLongitude(double longitude) {
    if (Double.isNaN(longitude) || Double.isInfinite(longitude)) {
      throw new IllegalArgumentException("Longitude must be a finite number");
    }
    if (longitude < -180.0 || longitude > 180.0) {
      throw new IllegalArgumentException(
          String.format("Invalid longitude: %.6f. Must be between -180 and 180", longitude));
    }
  }

  /**
   * Validates that latitude is within the valid range.
   *
   * @param latitude the latitude to validate
   * @throws IllegalArgumentException if latitude is outside valid range
   */
  private void validateLatitude(double latitude) {
    if (Double.isNaN(latitude) || Double.isInfinite(latitude)) {
      throw new IllegalArgumentException("Latitude must be a finite number");
    }
    if (latitude < -90.0 || latitude > 90.0) {
      throw new IllegalArgumentException(
          String.format("Invalid latitude: %.6f. Must be between -90 and 90", latitude));
    }
  }
}
