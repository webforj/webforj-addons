package com.webforj.addons.services.simplerouter;

import com.webforj.App;
import com.webforj.Page;
import com.webforj.Request;
import com.webforj.addons.services.simplerouter.event.SimpleRouteMatchEvent;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.environment.ObjectTable;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleRouter is a singleton class that manages URL routing within a web application. It allows
 * the registration of event listeners for specific routes and handles navigation by updating the
 * URL and dispatching route match events.
 */
public class SimpleRouter {

  private Map<String, EventDispatcher> eventMap = new HashMap<>();

  private String currentRoute = "";
  private String baseUrl;

  /**
   * Private constructor to prevent instantiation. Initializes the base URL and retrieves the
   * current URL from the Request object.
   */
  private SimpleRouter() {
    String url = Request.getCurrent().getUrl();
    String name = App.getApplicationName();
    if (ObjectTable.get("webforj_base_url") != null) {
      this.baseUrl = ObjectTable.get("webforj_base_url").toString();
    } else {
      this.baseUrl = url.substring(0, url.indexOf(name) + name.length());
    }
  }

  /**
   * Returns the singleton instance of SimpleRouter.
   *
   * @return the singleton instance of SimpleRouter
   */
  public static SimpleRouter getInstance() {
    String key = "webforj.SimpleRouter.instance";
    if (ObjectTable.contains(key)) {
      return (SimpleRouter) ObjectTable.get(key);
    }

    SimpleRouter instance = new SimpleRouter();
    ObjectTable.put(key, instance);

    return instance;
  }

  /**
   * Updates the browser URL without reloading the page.
   *
   * @param routeString the new route string to set in the URL
   */
  private void updateUrl(String routeString) {
    Page.getCurrent()
        .executeJs(
            "window.history.replaceState({},'title','" + baseUrl + "/" + routeString + "');");
  }

  /**
   * Registers an event listener for the specified route strings.
   *
   * @param listener the event listener to be notified on route match
   * @param routeStrings the route strings to match against
   */
  public void onRouteMatch(EventListener<SimpleRouteMatchEvent> listener, String... routeStrings) {
    for (String routeString : routeStrings) {
      EventDispatcher dispatcher;
      if (eventMap.containsKey(routeString)) {
        dispatcher = eventMap.get(routeString);
      } else {
        dispatcher = new EventDispatcher();
        eventMap.put(routeString, dispatcher);
      }
      dispatcher.addListener(SimpleRouteMatchEvent.class, listener);
    }
  }

  /**
   * Returns the current route string.
   *
   * @return the current route string
   */
  public String getCurrentRoute() {
    return currentRoute;
  }

  /** Navigates to the current URL's corresponding route within the application. */
  public void navigate() {
    String url = Request.getCurrent().getUrl();
    if (url.startsWith(this.baseUrl + "/")) {
      url = url.substring(this.baseUrl.length() + 1);
      navigate(url);
    }
  }

  /**
   * Navigates to the specified route string. Matches the route string with registered routes and
   * dispatches corresponding events.
   *
   * @param routeString the route string to navigate to
   */
  public void navigate(String routeString) {
    boolean done = false;

    for (Map.Entry<String, EventDispatcher> entry : eventMap.entrySet()) {
      String registeredRouteString = entry.getKey();
      EventDispatcher dispatcher = entry.getValue();
      Route r = new Route(registeredRouteString);

      if (r.matches(routeString)) {
        if (!done) {
          currentRoute = routeString;
          done = true;
        }
        dispatcher.dispatchEvent(new SimpleRouteMatchEvent(this, r));
      }
    }

    if (done) {
      updateUrl(currentRoute);
    }
  }

  /** Clears all registered event listeners from all routes. */
  public void clearAllListeners() {
    if (eventMap.isEmpty()) {
      return;
    }
    eventMap.values().forEach(e -> e.removeAllListeners());
  }
}
