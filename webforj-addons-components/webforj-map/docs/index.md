# webforJ Map Component

The webforJ Map component provides a Java API for integrating interactive maps into webforJ applications using OpenLayers.

## Core Features

- **Flexible View Configuration**: Center, zoom (floating-point precision), rotation, and extent constraints via MapView builder
- **Coordinate System**: WGS84 (EPSG:4326) coordinates, automatically transformed to Web Mercator (EPSG:3857) for rendering
- **Reactive View Property**: Update entire map view configuration with `setView()` and retrieve current state with `getView()`
- **Automatic Sizing**: Map automatically fills 100% width and height of its container
- **Shadow DOM**: Isolated styles for proper web component encapsulation

## Documentation

- [Basic Map Usage](basic-map.md) - Creating maps and controlling views
- [Coordinate System](coordinate-system.md) - Working with coordinates and extents
