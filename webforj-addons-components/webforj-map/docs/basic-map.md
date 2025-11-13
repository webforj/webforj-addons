# Basic Map Usage

Learn how to create and configure maps using the webforJ Map component.

## Simple Map

Create a basic map with default configuration, note that the map should have a height and width of more than 0 to render:

```java
import com.webforj.addons.components.map.Map;
import com.webforj.addons.components.map.MapView;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;

@Route("/simple-map")
public class SimpleMapView extends Composite<Div> {

  public SimpleMapView() {
    Map map = new Map();
    
    MapView view = MapView.builder()
        .center(0, 0)
        .zoom(3.0)
        .build();
    map.setView(view);
    
    getBoundComponent().setStyle("height", "500px");
    getBoundComponent().add(map);
  }
}
```

The map automatically sets its size to 100% width and height, displaying OpenLayers' default OSM (OpenStreetMap) tiles.

## Custom Center and Zoom

```java
Map map = new Map();

// New York City
MapView view = MapView.builder()
    .center(-73.9352, 40.7304)
    .zoom(12.0)
    .build();
map.setView(view);
```

## Programmatic View Control

Update map view at runtime using `setView()`. Use `toBuilder()` on the current view to modify specific properties while preserving others:

```java
import com.webforj.component.button.Button;

public class InteractiveMapView extends Composite<Div> {
  private final Map map;

  public InteractiveMapView() {
    map = new Map();
    
    MapView view = MapView.builder()
        .center(0, 0)
        .zoom(2.0)
        .build();
    map.setView(view);

    Button londonBtn = new Button("London");
    londonBtn.onClick(e -> {
      MapView londonView = map.getView().toBuilder()
          .center(-0.1276, 51.5074)
          .zoom(11.0)
          .build();
      map.setView(londonView);
    });

    Button tokyoBtn = new Button("Tokyo");
    tokyoBtn.onClick(e -> {
      MapView tokyoView = map.getView().toBuilder()
          .center(139.6917, 35.6895)
          .zoom(11.0)
          .build();
      map.setView(tokyoView);
    });

    getBoundComponent().setStyle("height", "500px");
    getBoundComponent().add(map, londonBtn, tokyoBtn);
  }
}
```

## Map Rotation

Set rotation during initialization or update at runtime:

```java
Map map = new Map();

// Initial rotation in degrees
MapView view = MapView.builder()
    .center(-73.9352, 40.7304)
    .zoom(12.0)
    .rotationDegrees(45.0)
    .build();
map.setView(view);

// Update rotation at runtime using toBuilder()
MapView rotatedView = map.getView().toBuilder()
    .rotation(Math.PI / 4)  // radians
    .build();
map.setView(rotatedView);
```

## Extent Constraints

Constrain panning to a geographic area using MapView extent:

```java
import com.webforj.addons.components.map.Extent;

Map map = new Map();

// Constrain to continental USA
Extent usaExtent = new Extent(-125.0, 24.0, -66.0, 49.0);

MapView view = MapView.builder()
    .center(-98.5795, 39.8283)
    .zoom(4.0)
    .extent(usaExtent)
    .build();
map.setView(view);
```

## Fit to Extent

Fit the view to show a specific area by updating the view configuration:

```java
import com.webforj.addons.components.map.Extent;

public class ExtentMapView extends Composite<Div> {
  private final Map map;

  public ExtentMapView() {
    map = new Map();
    
    MapView view = MapView.builder()
        .center(0, 0)
        .zoom(2.0)
        .build();
    map.setView(view);

    Button europeBtn = new Button("Show Europe");
    europeBtn.onClick(e -> {
      Extent europe = new Extent(-10.0, 35.0, 40.0, 70.0);
      // Calculate center from extent
      MapView europeView = map.getView().toBuilder()
          .center(15.0, 52.5)  // Center of Europe
          .zoom(4.0)
          .build();
      map.setView(europeView);
    });

    getBoundComponent().setStyle("height", "500px");
    getBoundComponent().add(map, europeBtn);
  }
}
```

## Reading Current View State

Access the current view configuration using the `getView()` method:

```java
import com.webforj.addons.components.map.MapView;
import com.webforj.component.button.Button;

Button statusBtn = new Button("Get Status");
statusBtn.onClick(e -> {
  MapView currentView = map.getView();
  if (currentView != null) {
    System.out.println("Center: " + currentView.getCenter().longitude() + 
                      ", " + currentView.getCenter().latitude());
    System.out.println("Zoom: " + currentView.getZoom());
    System.out.println("Rotation: " + currentView.getRotation() + " radians");
  }
});
```

## Zoom Level Constraints

Limit zoom levels to prevent excessive zoom in/out. Zoom levels are floating-point values typically ranging from 0 to 28:

```java
Map map = new Map();

MapView view = MapView.builder()
    .center(0, 0)
    .zoom(5.0)
    .minZoom(3.0)
    .maxZoom(18.0)
    .build();
map.setView(view);
```

## Complete Example

Comprehensive map with view controls:

```java
import com.webforj.addons.components.map.*;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;

@Route("/complete-map")
public class CompleteMapView extends Composite<Div> {
  private final Map map;

  public CompleteMapView() {
    map = new Map();
    
    // Configure view with constraints
    Extent worldExtent = new Extent(-180.0, -85.0, 180.0, 85.0);
    
    MapView view = MapView.builder()
        .center(-73.9352, 40.7304)
        .zoom(12.0)
        .minZoom(3.0)
        .maxZoom(18.0)
        .extent(worldExtent)
        .build();
    map.setView(view);

    // Control buttons
    Button londonBtn = new Button("Go to London");
    londonBtn.onClick(e -> {
      MapView londonView = map.getView().toBuilder()
          .center(-0.1276, 51.5074)
          .zoom(11.0)
          .build();
      map.setView(londonView);
    });

    Button rotateBtn = new Button("Rotate 45°");
    rotateBtn.onClick(e -> {
      MapView rotatedView = map.getView().toBuilder()
          .rotation(Math.PI / 4)
          .build();
      map.setView(rotatedView);
    });

    Button resetBtn = new Button("Reset View");
    resetBtn.onClick(e -> {
      MapView resetView = map.getView().toBuilder()
          .center(-73.9352, 40.7304)
          .zoom(12.0)
          .rotation(0.0)
          .build();
      map.setView(resetView);
    });

    Button statusBtn = new Button("Get Status");
    statusBtn.onClick(e -> {
      MapView currentView = map.getView();
      if (currentView != null) {
        System.out.println("Center: " + currentView.getCenter().longitude() + 
                          ", " + currentView.getCenter().latitude());
        System.out.println("Zoom: " + currentView.getZoom());
        System.out.println("Rotation: " + currentView.getRotation());
      }
    });

    getBoundComponent().setStyle("height", "500px");
    getBoundComponent().add(map, londonBtn, rotateBtn, resetBtn, statusBtn);
  }
}
```
