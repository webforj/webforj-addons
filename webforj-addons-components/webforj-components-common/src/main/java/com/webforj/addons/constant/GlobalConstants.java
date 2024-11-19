package com.webforj.addons.constant;

/**
 * This class contains global constants used throughout the components module.
 */
public class GlobalConstants {

	/**
	 * The version of the DWC client components CDN.
	 */
	public static final String DWC_CDN_VERSION = "1.1.0";

	/**
	 * The full URL of the DWC CDN, including the version number. This constant
	 * combines the base URL with the {@link GlobalConstants#DWC_CDN_VERSION} to
	 * form the complete CDN URL.
	 */
	public static final String DWC_CDN = "https://d3hx2iico687v8.cloudfront.net/" + DWC_CDN_VERSION
			+ "/dwc-addons.esm.js";
}
