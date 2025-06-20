package com.webforj.addons.constant;

/** This class contains global constants used throughout the components module. */
public class GlobalConstants {

  private GlobalConstants() {}

  /**
   * The full URL of the DWC CDN, including the version number. This constant combines the base URL
   * with the version from the property in the POM file to form the complete CDN URL.
   */
  public static final String DWC_CDN =
      "https://d3hx2iico687v8.cloudfront.net/${dwc.cdn.version}/dwc-addons.esm.js";
}
