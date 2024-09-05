package com.webforj.addons.services.simplerouter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Route class represents a route pattern and provides methods to match and
 * extract parameters from a route string. It supports route segments with
 * different data types such as string, integer, number, and boolean.
 */
public class Route {

	/**
	 * Represents a segment of the route.
	 */
	private static class RouteSegment {
		private final int position;

		/**
		 * Enum representing the type of the segment.
		 */
		public enum SegmentType {
			STR, INT, NUM, BOOL
		}

		private SegmentType type;
		private final String name;

		/**
		 * Constructs a RouteSegment with the specified position and segment string.
		 *
		 * @param position the position of the segment in the route
		 * @param segStr the segment string
		 */
		public RouteSegment(int position, String segStr) {
			this.position = position;

			String[] tmp = segStr.split(":");
			this.name = tmp[1];
			if (tmp.length == 3) {
				switch (tmp[2]) {
					case "int" :
						this.type = SegmentType.INT;
						break;
					case "num" :
						this.type = SegmentType.NUM;
						break;
					case "bool" :
						this.type = SegmentType.BOOL;
						break;
					default :
						this.type = SegmentType.STR;
				}
			}
		}

		@Override
		public String toString() {
			return "RouteSegment{" + "position=" + position + ", type='" + type + '\'' + ", name='"
					+ name + '\'' + '}';
		}
	}

	private final String route;
	private final Pattern routePattern;
	private final HashMap<String, RouteSegment> routeSegments = new HashMap<>();

	/**
	 * Constructs a Route with the specified route string.
	 *
	 * @param route the route string
	 */
	public Route(String route) {
		this.route = route;

		final StringBuilder routePatterns = new StringBuilder("^");
		String[] a = Arrays.stream(route.split("/")).map(s -> s.split("\\?"))
				.flatMap(Arrays::stream).map(s -> s.split("&")).flatMap(Arrays::stream)
				.toArray(String[]::new);
		for (int i = 0; i < a.length; i++) {
			if (i > 0) {
				routePatterns.append("/");
			}
			if (a[i].equals("*")) {
				routePatterns.append(".*");
				continue;
			}
			if (a[i].startsWith(":")) {
				routePatterns.append(".*");
				RouteSegment seg = new RouteSegment(i, a[i]);
				routeSegments.put(seg.name, seg);
			} else {
				routePatterns.append(a[i]);
			}
		}
		this.routePattern = Pattern.compile(routePatterns.toString(), Pattern.CASE_INSENSITIVE);
	}

	/**
	 * Checks if the given route string matches the route pattern.
	 *
	 * @param routeString the route string to check
	 * @return true if the route string matches the pattern, false otherwise
	 */
	public boolean matches(String routeString) {
		Matcher matcher = this.routePattern.matcher(routeString + "/");

		if (!matcher.matches()) {
			return false;
		}

		if (this.routeSegments.isEmpty()) {
			return true;
		}

		return this.areRouteSegmentsValid(routeString);
	}

	/**
	 * Validates route segments against the route string.
	 *
	 * @param routeString the route string to check
	 * @return true if all route segments are valid, false otherwise
	 */
	private boolean areRouteSegmentsValid(String routeString) {
		for (RouteSegment rs : routeSegments.values()) {
			if (!isSegmentValid(routeString, rs)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a single route segment is valid.
	 *
	 * @param routeString the route string to check
	 * @param rs the route segment to validate
	 * @return true if the route segment is valid, false otherwise
	 */
	private boolean isSegmentValid(String routeString, RouteSegment rs) {
		return switch (rs.type) {
			case STR -> getString(routeString, rs.name) != null;
			case BOOL -> getBool(routeString, rs.name) != null;
			case INT -> getInt(routeString, rs.name) != null;
			case NUM -> getNum(routeString, rs.name) != null;
		};
	}

	/**
	 * Retrieves the string value of a route segment.
	 *
	 * @param routeString the route string
	 * @param segmentName the name of the segment
	 * @return the string value of the segment, or null if not found
	 */
	public String getString(String routeString, String segmentName) {
		if (routeSegments.containsKey(segmentName)) {
			RouteSegment segment = routeSegments.get(segmentName);
			String[] tmp = Arrays.stream(routeString.split("/")).map(s -> s.split("\\?"))
					.flatMap(Arrays::stream).map(s -> s.split("&")).flatMap(Arrays::stream)
					.toArray(String[]::new);
			if (tmp.length < segment.position) {
				return null;
			}
			if (segment.position < tmp.length)
				return tmp[segment.position];
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves the integer value of a route segment.
	 *
	 * @param routeString the route string
	 * @param segmentName the name of the segment
	 * @return the integer value of the segment, or null if not found or not an
	 *         integer
	 */
	public Integer getInt(String routeString, String segmentName) {
		String tmp = getString(routeString, segmentName);
		if (tmp != null && routeSegments.get(segmentName).type == RouteSegment.SegmentType.INT) {
			try {
				return Integer.parseInt(tmp);
			} catch (NumberFormatException error) {
				System.err.println("Error parsing integer from string: " + tmp);
			}
		}
		return null;
	}

	/**
	 * Retrieves the numeric value of a route segment as a BigDecimal.
	 *
	 * @param routeString the route string
	 * @param segmentName the name of the segment
	 * @return the numeric value of the segment, or null if not found or not a
	 *         number
	 */
	public BigDecimal getNum(String routeString, String segmentName) {
		String tmp = getString(routeString, segmentName);
		if (tmp != null && routeSegments.get(segmentName).type == RouteSegment.SegmentType.NUM) {
			try {
				return new BigDecimal(tmp);
			} catch (NumberFormatException error) {
				System.err.println("Error parsing integer from string: " + tmp);
			}
		}
		return null;
	}

	/**
	 * Retrieves the boolean value of a route segment.
	 *
	 * @param routeString the route string
	 * @param segmentName the name of the segment
	 * @return the boolean value of the segment, or null if not found or not a
	 *         boolean
	 */
	public Boolean getBool(String routeString, String segmentName) {
		String tmp = getString(routeString, segmentName);
		if (tmp != null && routeSegments.get(segmentName).type == RouteSegment.SegmentType.BOOL) {
			return !(tmp.equalsIgnoreCase("false") || tmp.equals("0"));
		}
		return null;
	}

	/**
	 * Returns the original route string.
	 *
	 * @return the original route string
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * Returns the compiled route pattern.
	 *
	 * @return the compiled route pattern
	 */
	public Pattern getRoutePattern() {
		return routePattern;
	}

}
