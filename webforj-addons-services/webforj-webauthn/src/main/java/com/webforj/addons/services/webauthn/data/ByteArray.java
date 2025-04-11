package com.webforj.addons.services.webauthn.data;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Represents an immutable byte array with support for encoding/decoding to/from various encodings.
 * This class provides methods to create ByteArray instances from byte arrays and Base64Url strings.
 * It also supports concatenation of ByteArray instances and provides methods to retrieve the byte
 * contents in different encoding formats.
 *
 * @author @ElyasSalar
 * @since 1.00
 */
public final class ByteArray {
  /** Default size for the generateRandom method. */
  private static final int DEFAULT_SIZE = 32;

  /** The Base64 encoder for encoding byte arrays to Base64 strings. */
  private static final Base64.Encoder base64Encoder = Base64.getEncoder();

  /** The Base64 decoder for decoding Base64 strings to byte arrays. */
  private static final Base64.Decoder base64Decoder = Base64.getDecoder();

  /** The Base64Url encoder for encoding byte arrays to Base64Url strings without padding. */
  private static final Base64.Encoder base64UrlEncoder = Base64.getUrlEncoder().withoutPadding();

  /** The Base64Url decoder for decoding Base64Url strings to byte arrays. */
  private static final Base64.Decoder base64UrlDecoder = Base64.getUrlDecoder();

  /** The raw byte contents of the ByteArray instance. */
  private final byte[] bytes;

  /** The content bytes encoded as Base64Url data, without padding. */
  private final String base64url;

  /**
   * Constructs a new ByteArray instance by copying the contents of the given byte array.
   *
   * @param bytes The byte array to copy.
   */
  public ByteArray(byte[] bytes) {
    this.bytes = Arrays.copyOf(bytes, bytes.length);
    this.base64url = base64UrlEncoder.encodeToString(this.bytes);
  }

  /**
   * Constructs a new ByteArray instance by decoding the provided Base64Url string. This constructor
   * decodes the Base64Url string to a byte array using the Base64Url decoder and initializes the
   * ByteArray instance with the decoded byte array.
   *
   * @param base64url The Base64Url string to decode and initialize the ByteArray.
   */
  private ByteArray(String base64url) {
    this.bytes = base64UrlDecoder.decode(base64url);
    this.base64url = base64url;
  }

  /**
   * Generates a random ByteArray with a default size. This method creates a new ByteArray instance
   * with random byte contents of a default size. The default size is 32 bytes.
   *
   * @return A new ByteArray instance with random byte contents of default size.
   */
  public static ByteArray generateRandom() {
    return generateRandom(DEFAULT_SIZE);
  }

  /**
   * Generates a random ByteArray of the specified size. This method creates a new ByteArray
   * instance with random byte contents of the specified size. The size parameter is limited to a
   * maximum value of 1024.
   *
   * @param size The size of the ByteArray to generate.
   * @return A new ByteArray instance with random byte contents.
   */
  public static ByteArray generateRandom(int size) {
    int inRangeSize = Math.min(size, 1024);
    byte[] randomBytes = new byte[inRangeSize];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(randomBytes);
    return new ByteArray(randomBytes);
  }

  /**
   * Constructs a new ByteArray instance by decoding the provided Base64 string. This method decodes
   * the Base64 string to a byte array using the Base64 decoder and creates a new ByteArray instance
   * with the decoded byte array.
   *
   * @param base64 The Base64 string to decode and create the ByteArray.
   * @return A new ByteArray instance representing the decoded byte array.
   */
  public static ByteArray fromBase64(final String base64) {
    byte[] decodedBytes = base64Decoder.decode(base64);
    return new ByteArray(decodedBytes);
  }

  /**
   * Constructs a new ByteArray instance by decoding the given Base64Url string. This method decodes
   * the Base64Url string and creates a ByteArray instance from the resulting byte array. If the
   * provided Base64Url string is invalid, a Base64UrlException is thrown.
   *
   * @param base64url The Base64Url encoded string to decode.
   * @return The constructed ByteArray instance.
   */
  public static ByteArray fromBase64Url(final String base64url) {
    return new ByteArray(base64url.split("=")[0]);
  }

  /**
   * Concatenates this ByteArray with another ByteArray. This method creates a new ByteArray
   * instance containing a copy of this instance followed by a copy of the provided ByteArray.
   *
   * @param tail The ByteArray to concatenate with.
   * @return A new ByteArray containing a copy of this instance followed by a copy of the provided
   *     ByteArray.
   */
  public ByteArray concat(final ByteArray tail) {
    byte[] concatenatedBytes = new byte[this.bytes.length + tail.bytes.length];
    System.arraycopy(this.bytes, 0, concatenatedBytes, 0, this.bytes.length);
    System.arraycopy(tail.bytes, 0, concatenatedBytes, this.bytes.length, tail.bytes.length);
    return new ByteArray(concatenatedBytes);
  }

  /**
   * Converts the decoded binary data to a UTF-8 encoded string.
   *
   * <p>This method takes a byte array containing decoded binary data and converts it to a UTF-8
   * encoded string. It creates a new string by decoding the byte array using the UTF-8 character
   * encoding.
   *
   * @param decodedBytes The byte array containing the decoded binary data. It represents the binary
   *     data that will be converted to a UTF-8 encoded string.
   * @return A UTF-8 encoded string representing the decoded binary data. It returns a new string
   *     containing the UTF-8 encoded representation of the decoded binary data.
   * @throws NullPointerException If the decodedBytes parameter is null. It is thrown if the
   *     decodedBytes parameter is null, indicating that no binary data is provided for conversion.
   */
  public static String convertToUTF8String(byte[] decodedBytes) {
    return new String(decodedBytes, StandardCharsets.UTF_8);
  }

  /**
   * Checks if this ByteArray is empty.
   *
   * @return {@code true} if this ByteArray is empty, {@code false} otherwise.
   */
  public boolean isEmpty() {
    return this.bytes.length == 0;
  }

  /**
   * Gets the size of this ByteArray. The size is the number of bytes in the ByteArray.
   *
   * @return The size of this ByteArray.
   */
  public int size() {
    return this.bytes.length;
  }

  /**
   * Gets a copy of the raw byte contents of this ByteArray. This method returns a new byte array
   * containing a copy of the raw byte contents of this ByteArray instance.
   *
   * @return A copy of the raw byte contents of this ByteArray.
   */
  public byte[] getBytes() {
    return Arrays.copyOf(this.bytes, this.bytes.length);
  }

  /**
   * Gets the content bytes encoded as classic Base64 data. This method returns the content bytes of
   * this ByteArray encoded as classic Base64 data.
   *
   * @return The content bytes encoded as classic Base64 data.
   */
  public String getBase64() {
    return base64Encoder.encodeToString(this.bytes);
  }

  /**
   * Gets the content bytes encoded as Base64Url data, without padding. This method returns the
   * content bytes of this ByteArray encoded as Base64Url data, without padding.
   *
   * @return The content bytes encoded as Base64Url data.
   */
  public String getBase64Url() {
    return this.base64url;
  }
}
