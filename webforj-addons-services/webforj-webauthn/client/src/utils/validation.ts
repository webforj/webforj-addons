import { DOMAIN_NAME_REGEX } from "../constants/regex";

/**
 * Checks if a given hostname is a valid domain name.
 * 
 * This function validates whether a provided hostname is a valid domain name.
 * It returns true if the hostname matches a valid domain name pattern, including 
 * 'localhost', otherwise returns false.
 * 
 * @param {string} hostname - The hostname to validate.
 * @returns {boolean} True if the hostname is a valid domain name, otherwise false.
 * 
 * @example
 * // Example usage:
 * const hostname1 = 'example.com';
 * const hostname2 = 'localhost';
 * console.log(isValidDomain(hostname1)); // Output: true
 * console.log(isValidDomain(hostname2)); // Output: true
 */
export function isValidDomain(hostname: string): boolean {
  return (
    hostname === 'localhost' || DOMAIN_NAME_REGEX.test(hostname)
  );
}
