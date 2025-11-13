package com.webforj.addons.components.map;

import com.webforj.addons.components.map.util.ValidationUtils;

/**
 * View configuration for a map including center coordinate, zoom level and constraints, rotation
 * angle, and extent boundaries.
 *
 * @since 25.03
 */
public final class MapView {

  private final Coordinate center;
  private final Double zoom;
  private final Double minZoom;
  private final Double maxZoom;
  private final Extent extent;
  private final Double rotation;

  private MapView(Builder builder) {
    this.center = builder.center;
    this.zoom = builder.zoom;
    this.minZoom = builder.minZoom;
    this.maxZoom = builder.maxZoom;
    this.extent = builder.extent;
    this.rotation = builder.rotation;
  }

  /**
   * Gets the center coordinate of the view.
   *
   * @return the center coordinate, or null if not set
   */
  public Coordinate getCenter() {
    return center;
  }

  /**
   * Gets the zoom level of the view.
   *
   * @return the zoom level, or null if not set
   */
  public Double getZoom() {
    return zoom;
  }

  /**
   * Gets the minimum zoom level.
   *
   * @return the minimum zoom level, or null if not set
   */
  public Double getMinZoom() {
    return minZoom;
  }

  /**
   * Gets the maximum zoom level.
   *
   * @return the maximum zoom level, or null if not set
   */
  public Double getMaxZoom() {
    return maxZoom;
  }

  /**
   * Gets the extent constraint for the view.
   *
   * @return the extent constraint, or null if not set
   */
  public Extent getExtent() {
    return extent;
  }

  /**
   * Gets the rotation angle in radians.
   *
   * @return the rotation angle, or null if not set
   */
  public Double getRotation() {
    return rotation;
  }

  /**
   * Creates a new builder for MapView configuration.
   *
   * @return a new builder instance
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Creates a new builder initialized with this view's configuration.
   *
   * @return a new builder with current settings
   */
  public Builder toBuilder() {
    return new Builder()
        .center(center)
        .zoom(zoom)
        .minZoom(minZoom)
        .maxZoom(maxZoom)
        .extent(extent)
        .rotation(rotation);
  }

  /** Builder class for creating MapView instances. */
  public static class Builder {
    private Coordinate center;
    private Double zoom;
    private Double minZoom;
    private Double maxZoom;
    private Extent extent;
    private Double rotation;

    private Builder() {}

    /**
     * Sets the center coordinate of the view.
     *
     * @param center the center coordinate
     * @return this builder
     */
    public Builder center(Coordinate center) {
      this.center = center;
      return this;
    }

    /**
     * Sets the center coordinate using longitude and latitude.
     *
     * @param longitude the longitude in degrees
     * @param latitude the latitude in degrees
     * @return this builder
     * @throws IllegalArgumentException if coordinates are invalid
     */
    public Builder center(double longitude, double latitude) {
      this.center = new Coordinate(longitude, latitude);
      return this;
    }

    /**
     * Sets the zoom level of the view.
     *
     * @param zoom the zoom level (typically 0-20)
     * @return this builder
     * @throws IllegalArgumentException if zoom is negative
     */
    public Builder zoom(double zoom) {
      if (zoom < 0) {
        throw new IllegalArgumentException("Zoom level cannot be negative: " + zoom);
      }
      this.zoom = zoom;
      return this;
    }

    /**
     * Sets the minimum zoom level.
     *
     * @param minZoom the minimum zoom level
     * @return this builder
     * @throws IllegalArgumentException if minZoom is negative
     */
    public Builder minZoom(double minZoom) {
      ValidationUtils.validateZoomLevel(minZoom, "Minimum zoom level");
      this.minZoom = minZoom;
      return this;
    }

    /**
     * Sets the maximum zoom level.
     *
     * @param maxZoom the maximum zoom level
     * @return this builder
     * @throws IllegalArgumentException if maxZoom is negative
     */
    public Builder maxZoom(double maxZoom) {
      ValidationUtils.validateZoomLevel(maxZoom, "Maximum zoom level");
      this.maxZoom = maxZoom;
      return this;
    }

    /**
     * Sets the extent constraint for the view.
     *
     * @param extent the extent constraint
     * @return this builder
     */
    public Builder extent(Extent extent) {
      this.extent = extent;
      return this;
    }

    /**
     * Sets the rotation angle in radians.
     *
     * @param rotation the rotation angle in radians, or null to unset
     * @return this builder
     * @throws IllegalArgumentException if rotation is not finite
     */
    public Builder rotation(Double rotation) {
      if (rotation != null && !Double.isFinite(rotation)) {
        throw new IllegalArgumentException("Rotation must be a finite number");
      }
      this.rotation = rotation;
      return this;
    }

    /**
     * Sets the rotation angle in degrees.
     *
     * @param degrees the rotation angle in degrees
     * @return this builder
     * @throws IllegalArgumentException if degrees is not finite
     */
    public Builder rotationDegrees(double degrees) {
      if (!Double.isFinite(degrees)) {
        throw new IllegalArgumentException("Rotation degrees must be a finite number");
      }
      this.rotation = Math.toRadians(degrees);
      return this;
    }

    /**
     * Builds a new MapView instance.
     *
     * @return a new MapView with the configured settings
     * @throws IllegalArgumentException if the configuration is invalid
     */
    public MapView build() {
      validateConfiguration();
      return new MapView(this);
    }

    /**
     * Validates the builder configuration.
     *
     * @throws IllegalArgumentException if the configuration is invalid
     */
    private void validateConfiguration() {
      ValidationUtils.validateZoomRange(minZoom, maxZoom);
      ValidationUtils.validateZoomInRange(zoom, minZoom, maxZoom);
    }
  }
}
