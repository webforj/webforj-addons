import OlMap from 'ol/Map';
import View from 'ol/View';
import { viewConfigToViewOptions } from '../utils/transformers';
import { ViewConfig } from '../types';

/**
 * Manages view-related operations for the map component.
 * Handles updating the map view with new configurations.
 * 
 * All coordinate inputs are in WGS84 format and transformed to Web Mercator internally.
 */
export class ViewManager {
  constructor(private readonly map: OlMap) {}

  /**
   * Sets the view configuration of the map.
   * Creates a new View instance with the provided configuration.
   * All coordinates are in WGS84 format and will be transformed to Web Mercator.
   * 
   * @param viewConfig - View configuration matching MapView.java properties
   */
  async setView(viewConfig: ViewConfig): Promise<void> {
    const viewOptions = viewConfigToViewOptions(viewConfig);
    this.map.setView(new View(viewOptions));
  }
}
