import { CoordinateObject, ExtentObject } from './geometry';

/**
 * View configuration matching MapView.java properties.
 */
export interface ViewConfig {
  center?: CoordinateObject;
  zoom?: number;
  minZoom?: number;
  maxZoom?: number;
  extent?: ExtentObject;
  rotation?: number;
}

export type { CoordinateObject, ExtentObject } from './geometry';
