/**
 * Coordinate object structure with named longitude and latitude properties.
 * This is the format sent from the server.
 */
export interface CoordinateObject {
  longitude: number;
  latitude: number;
}

/**
 * Extent/bounds object structure with named coordinate properties.
 * This is the format sent from the server.
 */
export interface ExtentObject {
  minLongitude: number;
  minLatitude: number;
  maxLongitude: number;
  maxLatitude: number;
}
