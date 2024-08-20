/**
 * Converts an ArrayBuffer to a Base64URL string.
 * 
 * @param {ArrayBuffer} buffer - The ArrayBuffer to convert to Base64URL.
 * @returns {string} The Base64URL string representation of the ArrayBuffer.
 * 
 * @example
 * // Example usage:
 * const buffer = new ArrayBuffer(16);
 * const base64URLString = bufferToBase64URLString(buffer);
 * console.log(base64URLString); // Output: "AAAAAAAAAAAAAA"
 */
export function bufferToBase64URLString(buffer: ArrayBuffer): string {
  const bytes = new Uint8Array(buffer);
  let str = '';

  for (const charCode of bytes) {
    str += String.fromCharCode(charCode);
  }

  const base64String = btoa(str);

  return base64String.replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
}

/**
 * Converts a Base64URL string to an ArrayBuffer.
 * 
 * @param {string} base64URLString - The Base64URL string to convert to ArrayBuffer.
 * @returns {ArrayBuffer} The ArrayBuffer representation of the Base64URL string.
 * 
 * @example
 * // Example usage:
 * const base64URLString = 'SGVsbG8gV29ybGQh';
 * const buffer = base64URLStringToBuffer(base64URLString);
 * console.log(buffer); // Output: ArrayBuffer {...}
 */
export function base64URLStringToBuffer(base64URLString: string): ArrayBuffer {
  const base64 = base64URLString.replace(/-/g, '+').replace(/_/g, '/');

  const padLength = (4 - (base64.length % 4)) % 4;
  const padded = base64.padEnd(base64.length + padLength, '=');

  const binary = atob(padded);

  const buffer = new ArrayBuffer(binary.length);
  const bytes = new Uint8Array(buffer);

  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i);
  }

  return buffer;
}

/**
 * Converts an ArrayBuffer containing UTF-8 encoded data to a UTF-8 string.
 * 
 * @param {ArrayBuffer} value - The ArrayBuffer containing UTF-8 encoded data.
 * @returns {string} The UTF-8 string representation of the ArrayBuffer.
 * 
 * @example
 * // Example usage:
 * const buffer = new ArrayBuffer(4);
 * const view = new Uint8Array(buffer);
 * view[0] = 72; // 'H'
 * view[1] = 101; // 'e'
 * view[2] = 108; // 'l'
 * view[3] = 108; // 'l'
 * const utf8String = bufferToUTF8String(buffer);
 * console.log(utf8String); // Output: "Hell"
 */
export function bufferToUTF8String(value: ArrayBuffer): string {
  return new TextDecoder('utf-8').decode(value);
}

/**
 * Converts a UTF-8 string to an ArrayBuffer containing UTF-8 encoded data.
 * 
 * @param {string} value - The UTF-8 string to encode into an ArrayBuffer.
 * @returns {ArrayBuffer} The ArrayBuffer containing UTF-8 encoded data.
 * 
 * @example
 * // Example usage:
 * const utf8String = 'Hello';
 * const buffer = utf8StringToBuffer(utf8String);
 * console.log(buffer); // Output: ArrayBuffer {...}
 */
export function utf8StringToBuffer(value: string): ArrayBuffer {
  return new TextEncoder().encode(value);
}
