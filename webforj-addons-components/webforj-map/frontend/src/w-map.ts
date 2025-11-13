import OlMap from 'ol/Map';
import View, { ViewOptions } from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { defaults as defaultControls } from 'ol/control/defaults';
import { ViewManager } from './services/view-manager';
import { ViewConfig } from './types';
import { 
  viewConfigToViewOptions, 
  coordinateToCoordinateObject, 
  extentToExtentObject 
} from './utils/transformers';
import styles from './styles/w-map.css?inline';

/**
 * Custom HTML element for webforJ map integration.
 * 
 * Each instance manages its own OpenLayers map with methods exposed directly on the element.
 * Supports OSM tile layers with center positioning, zoom control, and extent fitting.
 * 
 * Uses Shadow DOM for encapsulation. All coordinate parameters are passed as arrays
 * [longitude, latitude] directly from Java, eliminating the need for object converters.
 * 
 * @since 25.03
 */
export class WMapElement extends HTMLElement {
  private map: OlMap;
  private _view: ViewConfig = {};
  private readonly viewManager: ViewManager;
  declare shadowRoot: ShadowRoot;
  private readonly mapContainer: HTMLDivElement;

  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
    this.addStyles();
    this.mapContainer = this.createContainerElement();
    this.shadowRoot.appendChild(this.mapContainer);
    this.map = this.createMap();

    this.viewManager = new ViewManager(this.map);
  }

  /**
   * Gets the current view configuration.
   * Transforms OpenLayers view properties from Web Mercator back to WGS84 format.
   * 
   * @returns The current view configuration in server format
   */
  get view(): ViewConfig {
    const view = this.map.getView();
    const projection = view.getProjection();
    
    return this._view = {
      center: coordinateToCoordinateObject(view.getCenter()),
      zoom: view.getZoom(),
      minZoom: view.getMinZoom(),
      maxZoom: view.getMaxZoom(),
      rotation: view.getRotation(),
      extent: extentToExtentObject(projection.getExtent())
    }
  }

  /**
   * Sets the view configuration of the map.
   * Delegates to ViewManager to create a new View instance with the provided configuration.
   * All coordinates are in WGS84 format and will be transformed to Web Mercator.
   * 
   * @param viewConfig - View configuration matching MapView.java properties
   * @throws {Error} When map is not initialized
   */
  set view(viewConfig: ViewConfig) {
    this._view = viewConfig;
    this.viewManager.setView(viewConfig);
  }

  /**
   * Called when element is disconnected from the DOM.
   * Cleans up the OpenLayers map instance.
   */
  disconnectedCallback(): void {
    this.destroyMap();
  }

  /**
   * Destroys the map instance and cleans up resources.
   */
  destroyMap(): void {
    if (this.map) {
      this.map.setTarget(undefined);
      this.map.dispose();
    }
  }

  /**
   * Adds styles to the Shadow DOM to ensure OpenLayers controls and attribution
   * are properly positioned in the bottom right corner.
   */
  private addStyles(): void {
    const style = document.createElement('style');
    style.textContent = styles;
    this.shadowRoot.appendChild(style);
  }

  /**
   * Creates the container element for the map.
   * 
   * @returns The container element
   */
  private createContainerElement(): HTMLDivElement {
    const container = document.createElement('div');
    container.style.width = '100%';
    container.style.height = '100%';
    return container;
  }

  private createMap(): OlMap {
    const viewConfig: ViewOptions = viewConfigToViewOptions(this._view);

    // TODO: TEMPORARY - Remove when proper layer/source support is added (PR #167)
    // Hardcoded default OSM layer so the map displays something on screen.
    // Future PRs will add Java API for configuring layers and sources.
    const defaultLayer = new TileLayer({
      source: new OSM()
    });

    return new OlMap({
      target: this.mapContainer,
      view: new View(viewConfig),
      layers: [defaultLayer],
      controls: defaultControls({ 
        rotate: false,
        attribution: true,
        zoom: true
      })
    });
  }

  /**
   * Gets the OpenLayers map instance.
   * 
   * @returns The OpenLayers map instance, or null if not initialized
   */
  getMap(): OlMap | null {
    return this.map;
  }
}

customElements.define('w-map', WMapElement);
