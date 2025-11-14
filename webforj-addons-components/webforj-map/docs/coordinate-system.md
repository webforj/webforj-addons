# Coordinate System

Understanding coordinates, extents, and projections in the Map component.

## Coordinate Format

The Map component uses **WGS84 (EPSG:4326)** for all public APIs - the standard used by GPS and mapping services.

### Coordinate Order

Coordinates use `[longitude, latitude]` ordering:

- **Longitude**: -180 to 180 degrees (west to east)
- **Latitude**: -90 to 90 degrees (south to north)

```java
import com.webforj.addons.components.map.Coordinate;

// Create coordinate: longitude first, latitude second
Coordinate nyc = new Coordinate(-74.006, 40.7128);

// Access values
double lon = nyc.getLongitude(); // -74.006
double lat = nyc.getLatitude();  // 40.7128

// Convert to array [lon, lat]
double[] coords = nyc.toArray(); // [-74.006, 40.7128]
```

## Automatic Projection Transformation

Internally, OpenLayers uses **Web Mercator (EPSG:3857)** for rendering. The component automatically transforms coordinates between projections:

- **Java API** → WGS84 (degrees)
- **Internal Rendering** → Web Mercator (meters)
- **Return Values** → WGS84 (degrees)

All transformations are handled automatically - you only work with WGS84 coordinates.

### Why Two Projections?

- **WGS84**: Human-friendly degrees, used by GPS
- **Web Mercator**: Optimized for web tile rendering

## Working with Coordinates

### Setting Map Center

```java
import com.webforj.addons.components.map.Coordinate;
import com.webforj.addons.components.map.Map;
import com.webforj.addons.components.map.MapView;

Map map = new Map();

// Update center using toBuilder() to preserve other settings
MapView parisView = map.getView().toBuilder()
    .center(2.3522, 48.8566)
    .build();
map.setView(parisView);

// Or using Coordinate object
Coordinate tokyo = new Coordinate(139.6917, 35.6895);
MapView tokyoView = map.getView().toBuilder()
    .center(tokyo)
    .build();
map.setView(tokyoView);
```

### City Selector Example

```java
import com.webforj.addons.components.map.*;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;

@Route("/city-selector")
public class CitySelectorView extends Composite<Div> {

  public CitySelectorView() {
    Map map = new Map();
    
    MapView view = MapView.builder()
        .center(0, 0)
        .zoom(2.0)
        .build();
    map.setView(view);
    
    Button londonBtn = new Button("London");
    londonBtn.onClick(e -> {
      MapView londonView = map.getView().toBuilder()
          .center(-0.1276, 51.5074)
          .zoom(10.0)
          .build();
      map.setView(londonView);
    });
    
    Button tokyoBtn = new Button("Tokyo");
    tokyoBtn.onClick(e -> {
      MapView tokyoView = map.getView().toBuilder()
          .center(139.6917, 35.6895)
          .zoom(10.0)
          .build();
      map.setView(tokyoView);
    });
    
    Button sydneyBtn = new Button("Sydney");
    sydneyBtn.onClick(e -> {
      MapView sydneyView = map.getView().toBuilder()
          .center(151.2093, -33.8688)
          .zoom(10.0)
          .build();
      map.setView(sydneyView);
    });
    
    getBoundComponent().add(map, londonBtn, tokyoBtn, sydneyBtn);
  }
}
```

## Extents

Extents define rectangular geographic boundaries.

### Extent Format

Format: `[minLongitude, minLatitude, maxLongitude, maxLatitude]`

```java
import com.webforj.addons.components.map.Extent;

// Constructor: minLon, minLat, maxLon, maxLat
Extent usa = new Extent(-125.0, 24.0, -66.0, 49.0);
Extent europe = new Extent(-10.0, 36.0, 40.0, 71.0);
Extent africa = new Extent(-18.0, -35.0, 52.0, 38.0);
```

### Extent Operations

```java
import com.webforj.addons.components.map.*;

// Create extent from corners
Coordinate bottomLeft = new Coordinate(-10.0, 36.0);
Coordinate topRight = new Coordinate(40.0, 71.0);
Extent europe = Extent.fromCorners(bottomLeft, topRight);

// Create extent from center and size
Coordinate center = new Coordinate(15.0, 53.5);
Extent europeFromCenter = Extent.fromCenter(center, 50.0, 35.0);

// Get extent properties
double[] bounds = europe.toArray();           // [-10.0, 36.0, 40.0, 71.0]
double minLon = europe.getMinLongitude();     // -10.0
double maxLat = europe.getMaxLatitude();      // 71.0
double width = europe.getWidth();             // 50.0 degrees
double height = europe.getHeight();           // 35.0 degrees
Coordinate centerCoord = europe.getCenter();
```

### Using Extents for View Constraints

Restrict panning to a specific area:

```java
import com.webforj.addons.components.map.*;

Map map = new Map();

Extent europeExtent = new Extent(-10.0, 36.0, 40.0, 71.0);

MapView view = MapView.builder()
    .center(10.0, 50.0)
    .zoom(4.0)
    .extent(europeExtent)  // Users can't pan outside this area
    .build();
map.setView(view);
```

### Fitting View to Extent

```java
import com.webforj.addons.components.map.*;

Map map = new Map();

// Fit view to show entire extent
Extent europe = new Extent(-10.0, 36.0, 40.0, 71.0);
Coordinate europeCenter = europe.getCenter();

MapView europeView = map.getView().toBuilder()
    .center(europeCenter)
    .zoom(4.0)  // Adjust zoom to fit the extent
    .build();
map.setView(europeView);
```

## Coordinate Validation

All coordinates and extents are validated on construction:

```java
// Valid coordinates
new Coordinate(-122.4, 37.8);     // San Francisco
new Coordinate(0, 0);              // Null Island
new Coordinate(180, 90);           // Valid extremes

// Invalid - throws IllegalArgumentException
new Coordinate(200, 50);           // lon > 180
new Coordinate(-50, 100);          // lat > 90
new Coordinate(Double.NaN, 45);    // Non-finite

// Valid extent
new Extent(-10, -10, 10, 10);

// Invalid - throws IllegalArgumentException
new Extent(10, 10, 5, 20);         // minLon >= maxLon
new Extent(-10, 20, 10, 10);       // minLat >= maxLat
new Extent(200, 0, 250, 50);       // Invalid longitude
```
