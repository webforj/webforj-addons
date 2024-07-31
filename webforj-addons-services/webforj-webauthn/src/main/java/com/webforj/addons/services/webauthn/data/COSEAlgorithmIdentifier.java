package com.webforj.addons.services.webauthn.data;

/**
 * Enumeration representing cryptographic algorithm identifiers used in COSE
 * (CBOR Object Signing and Encryption) messages. These identifiers correspond
 * to values registered in the IANA COSE Algorithms registry. The enum provides
 * a convenient way to reference these algorithms in WebAuthn and other
 * contexts.
 *
 * @see <a href=
 *      "https://www.w3.org/TR/webauthn-3#typedefdef-cosealgorithmidentifier">
 *      §5.10.5. Cryptographic Algorithm Identifier (typedef
 *      COSEAlgorithmIdentifier)</a>
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public enum COSEAlgorithmIdentifier {
	/**
	 * EdDSA algorithm identifier (-8). EdDSA (Edwards-curve Digital Signature
	 * Algorithm) is a digital signature scheme using twisted Edwards curves. It
	 * provides high security with relatively fast signature generation and
	 * verification.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc9053.html"> RFC 8037: CFRG
	 *      Elliptic Curve Diffie-Hellman (ECDH) and Signatures in JSON Object
	 *      Signing and Encryption (JOSE)</a>
	 */
	EdDSA(-8, "EDDSA"),

	/**
	 * ES256 algorithm identifier (-7). ES256 (Elliptic Curve Digital Signature
	 * Algorithm with SHA-256) is an ECDSA (Elliptic Curve Digital Signature
	 * Algorithm) signature algorithm using the NIST P-256 curve and SHA-256 hash
	 * function. It is commonly used for signing JSON Web Tokens (JWTs) and other
	 * cryptographic operations.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc9053.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	ES256(-7, "SHA256withECDSA"),

	/**
	 * ES384 algorithm identifier (-35). ES384 (Elliptic Curve Digital Signature
	 * Algorithm with SHA-384) is an ECDSA signature algorithm using the NIST P-384
	 * curve and SHA-384 hash function. It offers higher security with larger key
	 * sizes compared to ES256.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc9053.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	ES384(-35, "SHA384withECDSA"),

	/**
	 * ES512 algorithm identifier (-36). ES512 (Elliptic Curve Digital Signature
	 * Algorithm with SHA-512) is an ECDSA signature algorithm using the NIST P-521
	 * curve and SHA-512 hash function. It provides the highest level of security
	 * among the ES algorithms but requires more computational resources.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc9053.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	ES512(-36, "SHA512withECDSA"),

	/**
	 * RS256 algorithm identifier (-257). RS256 (RSA Signature with SHA-256) is an
	 * RSA-based signature algorithm using the SHA-256 hash function. It is widely
	 * used for signing JWTs and other digital signatures in various cryptographic
	 * protocols.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc8812.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	RS256(-257, "SHA256withRSA"),

	/**
	 * RS384 algorithm identifier (-258). RS384 (RSA Signature with SHA-384) is an
	 * RSA-based signature algorithm using the SHA-384 hash function. It offers
	 * higher security compared to RS256 due to the larger hash size.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc8812.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	RS384(-258, "SHA384withRSA"),

	/**
	 * RS512 algorithm identifier (-259). RS512 (RSA Signature with SHA-512) is an
	 * RSA-based signature algorithm using the SHA-512 hash function. It provides
	 * the highest level of security among the RS algorithms but requires larger key
	 * sizes and more computational resources.
	 *
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc8812.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	RS512(-259, "SHA512withRSA"),

	/**
	 * RS1 algorithm identifier (-65535). RS1 (RSA Signature with SHA-1) is an
	 * RSA-based signature algorithm using the SHA-1 hash function. It is considered
	 * deprecated and insecure due to vulnerabilities in the SHA-1 hash function.
	 *
	 * @deprecated This algorithm is deprecated and should not be used in new
	 *             implementations due to security concerns related to SHA-1
	 *             vulnerabilities.
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc8812.html">RFC 7518: JSON Web
	 *      Algorithms (JWA)</a>
	 */
	@Deprecated
	RS1(-65535, "SHA1withRSA");

	private final int value;

	private final String javaAlgorithmName;

	COSEAlgorithmIdentifier(int value, String javaAlgorithmName) {
		this.value = value;
		this.javaAlgorithmName = javaAlgorithmName;
	}

	/**
	 * Get the integer value representing the COSE algorithm identifier.
	 *
	 * @return The integer value of the COSE algorithm identifier.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Get the corresponding Java cryptographic algorithm name for the COSE
	 * algorithm identifier.
	 *
	 * @return The Java cryptographic algorithm name.
	 */
	public String getJavaAlgorithmName() {
		return javaAlgorithmName;
	}

	/**
	 * Returns the COSEAlgorithmIdentifier enum constant corresponding to the
	 * provided value.
	 *
	 * @param value The integer value of the COSE algorithm identifier.
	 * @return The COSEAlgorithmIdentifier enum constant.
	 * @throws IllegalArgumentException If the provided value does not match any
	 *             known algorithm identifier.
	 */
	public static COSEAlgorithmIdentifier fromValue(int value) {
		for (COSEAlgorithmIdentifier algorithm : COSEAlgorithmIdentifier.values()) {
			if (algorithm.value == value) {
				return algorithm;
			}
		}
		throw new IllegalArgumentException("Unknown COSE algorithm identifier: " + value);
	}
}
