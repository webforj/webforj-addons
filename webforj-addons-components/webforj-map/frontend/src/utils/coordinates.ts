import { Coordinate } from 'ol/coordinate';
import { Extent } from 'ol/extent';
import { transform } from 'ol/proj';

/**
 * Projection constants to avoid typos in projection code strings
 */
export const PROJECTIONS = {
  /** EPSG:4326 - WGS84 Geographic coordinate system (longitude/latitude) */
  WGS84: 'EPSG:4326',
  /** EPSG:3857 - Web Mercator projection (used by most web maps) */
  WEB_MERCATOR: 'EPSG:3857'
} as const;

/**
 * Transforms a coordinate from EPSG:4326 (WGS84) to EPSG:3857 (Web Mercator)
 */
export function transformToWebMercator(coordinate: Coordinate): Coordinate {
  return transform(coordinate, PROJECTIONS.WGS84, PROJECTIONS.WEB_MERCATOR) as Coordinate;
}

/**
 * Transforms a coordinate from EPSG:3857 (Web Mercator) to EPSG:4326 (WGS84)
 */
export function transformToWGS84(coordinate: Coordinate): Coordinate {
  return transform(coordinate, PROJECTIONS.WEB_MERCATOR, PROJECTIONS.WGS84) as Coordinate;
}

/**
 * Transforms an extent from EPSG:4326 to EPSG:3857
 */
export function transformExtentToWebMercator(extent: Extent): Extent {
  const [minX, minY, maxX, maxY] = extent;
  const [newMinX, newMinY] = transformToWebMercator([minX, minY]);
  const [newMaxX, newMaxY] = transformToWebMercator([maxX, maxY]);
  return [newMinX, newMinY, newMaxX, newMaxY];
}

/**
 * Transforms an extent from EPSG:3857 to EPSG:4326
 */
export function transformExtentToWGS84(extent: Extent): Extent {
  const [minX, minY, maxX, maxY] = extent;
  const [newMinX, newMinY] = transformToWGS84([minX, minY]);
  const [newMaxX, newMaxY] = transformToWGS84([maxX, maxY]);
  return [newMinX, newMinY, newMaxX, newMaxY];
}