import { ViewOptions } from 'ol/View';
import { Coordinate } from 'ol/coordinate';
import { Extent } from 'ol/extent';
import { 
  transformToWebMercator, 
  transformExtentToWebMercator, 
  transformToWGS84, 
  transformExtentToWGS84, 
  PROJECTIONS 
} from '../coordinates';
import { ViewConfig, CoordinateObject, ExtentObject } from '../../types';

/**
 * Transforms a coordinate object to OpenLayers coordinate array in Web Mercator projection.
 * 
 * @param coordinate - Coordinate object with longitude and latitude properties
 * @returns Transformed coordinate in Web Mercator [x, y], or undefined if input is null/undefined
 */
export function coordinateObjectToCoordinate(coordinate: CoordinateObject | null | undefined): Coordinate | undefined {
  if (!coordinate) {
    return undefined;
  }
  return transformToWebMercator([coordinate.longitude, coordinate.latitude]);
}

/**
 * Transforms an OpenLayers coordinate array (in Web Mercator) back to a coordinate object (in WGS84).
 * 
 * @param coordinate - Coordinate array in Web Mercator [x, y]
 * @returns Coordinate object with longitude and latitude in WGS84, or undefined if input is null/undefined
 */
export function coordinateToCoordinateObject(coordinate: Coordinate | null | undefined): CoordinateObject | undefined {
  if (!coordinate) {
    return undefined;
  }
  const [longitude, latitude] = transformToWGS84(coordinate);
  return { longitude, latitude };
}

/**
 * Transforms an extent object to OpenLayers extent array in Web Mercator projection.
 * 
 * @param extent - Extent object with named coordinate properties
 * @returns Transformed extent in Web Mercator [minX, minY, maxX, maxY], or undefined if input is null/undefined
 */
export function extentObjectToExtent(extent: ExtentObject | null | undefined): Extent | undefined {
  if (!extent) {
    return undefined
  }
  return transformExtentToWebMercator([
    extent.minLongitude,
    extent.minLatitude,
    extent.maxLongitude,
    extent.maxLatitude
  ]);
}

/**
 * Transforms an OpenLayers extent array (in Web Mercator) back to an extent object (in WGS84).
 * 
 * @param extent - Extent array in Web Mercator [minX, minY, maxX, maxY]
 * @returns Extent object with named coordinate properties in WGS84, or undefined if input is null/undefined
 */
export function extentToExtentObject(extent: Extent | null | undefined): ExtentObject | undefined {
  if (!extent) {
    return undefined;
  }
  const [minLongitude, minLatitude, maxLongitude, maxLatitude] = transformExtentToWGS84(extent);
  return { minLongitude, minLatitude, maxLongitude, maxLatitude };
}

/**
 * Converts ViewConfig from the server into OpenLayers ViewOptions.
 * Transforms all coordinates from WGS84 to Web Mercator projection.
 * 
 * @param viewConfig - View configuration from server (MapView.java)
 * @returns ViewOptions ready for OpenLayers View constructor
 */
export function viewConfigToViewOptions(viewConfig: ViewConfig): ViewOptions {
  return {
    projection: PROJECTIONS.WEB_MERCATOR,
    center: coordinateObjectToCoordinate(viewConfig.center),
    zoom: viewConfig.zoom,
    minZoom: viewConfig.minZoom,
    maxZoom: viewConfig.maxZoom,
    rotation: viewConfig.rotation,
    extent: extentObjectToExtent(viewConfig.extent)
  };
}
