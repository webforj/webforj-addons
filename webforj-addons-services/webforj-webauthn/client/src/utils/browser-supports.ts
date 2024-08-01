/**
 * Safely executes a callback function for browser APIs that may not be widely supported.
 * If an error occurs during execution, it logs a warning message with the error details.
 * 
 * @param callback The callback function to execute.
 * @param functionName The name of the API function being called (for error reporting).
 */
export const safeBrowserApiCall = (
  callback: () => any,
  functionName: string
): void => {
  try {
    callback();
  } catch (err) {
    console.warn(
      `The browser extension that intercepted this WebAuthn API call incorrectly implemented ${functionName}. You should report this error to them.\n`,
      err,
    );
  }
}

/**
 * Checks whether the current browser environment supports the Web Authentication API (WebAuthn).
 * This function verifies if the necessary objects and methods for WebAuthn are available in the window object.
 * 
 * @returns {boolean} True if the browser supports WebAuthn, otherwise false.
 */
export function browserSupportsWebAuthn(): boolean {
  return (
    window?.PublicKeyCredential !== undefined &&
    typeof window?.PublicKeyCredential === 'function'
  );
}

/**
 * Checks whether the current browser environment supports the Web Authentication API (WebAuthn) Autofill feature.
 * This function verifies if the necessary objects and methods for WebAuthn Autofill are available in the window object.
 * 
 * @returns {Promise<boolean>} A promise that resolves to true if the browser supports WebAuthn Autofill, otherwise false.
 */
export function browserSupportsWebAuthnAutofill(): Promise<boolean> {

  if (PublicKeyCredential?.isConditionalMediationAvailable === undefined) {
    return Promise.resolve(false);
  }

  return PublicKeyCredential.isConditionalMediationAvailable();
}

/**
 * Determines whether the current browser environment can communicate with a user-verifying platform authenticator,
 * such as Touch ID, Android fingerprint scanner, or Windows Hello.
 * 
 * Note: This method does not provide information about the specific platform authenticator being used.
 */
export function platformAuthenticatorIsAvailable(): Promise<boolean> {
  if (!browserSupportsWebAuthn()) {
    return Promise.resolve(false);
  }

  return PublicKeyCredential.isUserVerifyingPlatformAuthenticatorAvailable();
}