import { browserSupportsWebAuthn, browserSupportsWebAuthnAutofill, platformAuthenticatorIsAvailable, safeBrowserApiCall } from "../browser-supports";

describe("browser support utility functions", () => {
  describe("safeBrowserApiCall", () => {
    let consoleWarnSpy: jest.SpyInstance;
  
    beforeEach(() => {
      consoleWarnSpy = jest.spyOn(console, "warn").mockImplementation(() => {});
    });
  
    afterEach(() => {
      consoleWarnSpy.mockRestore();
    });
  
    it("should execute the callback function successfully", () => {
      const callback = jest.fn();
      safeBrowserApiCall(callback, "testFunction");
      expect(callback).toHaveBeenCalled();
    });
  
    it("should log a warning if an error occurs during the callback execution", () => {
      const error = new Error("Test error");
      const callback = jest.fn(() => {
        throw error;
      });
      safeBrowserApiCall(callback, "testFunction");
      expect(callback).toHaveBeenCalled();
      expect(consoleWarnSpy).toHaveBeenCalledWith(
        `The browser extension that intercepted this WebAuthn API call incorrectly implemented testFunction. You should report this error to them.\n`,
        error
      );
    });
  
    it("should log the correct warning message with error details", () => {
      const error = new Error("Another test error");
      const callback = jest.fn(() => {
        throw error;
      });
      safeBrowserApiCall(callback, "anotherTestFunction");
      expect(consoleWarnSpy).toHaveBeenCalledWith(
        `The browser extension that intercepted this WebAuthn API call incorrectly implemented anotherTestFunction. You should report this error to them.\n`,
        error
      );
    });
  
    it("should handle callback that does not throw an error", () => {
      const callback = jest.fn();
      safeBrowserApiCall(callback, "nonThrowingFunction");
      expect(callback).toHaveBeenCalled();
      expect(consoleWarnSpy).not.toHaveBeenCalled();
    });
  });

  describe("browserSupportsWebAuthn", () => {
    const originalWindow = { ...window };
  
    afterEach(() => {
      global.window = originalWindow;
    });
  
    it("should return true if window.PublicKeyCredential is defined and is a function", () => {
      Object.defineProperty(global.window, "PublicKeyCredential", { value: jest.fn(), writable: true })
      expect(browserSupportsWebAuthn()).toBe(true);
    });
  
    it("should return false if window.PublicKeyCredential is undefined", () => {
      Object.defineProperty(global.window, "PublicKeyCredential", { value: undefined, writable: true })
      expect(browserSupportsWebAuthn()).toBe(false);
    });
  
    it("should return false if window.PublicKeyCredential is not a function", () => {
      Object.defineProperty(global.window, "PublicKeyCredential", { value: {}, writable: true })
      expect(browserSupportsWebAuthn()).toBe(false);
    });
  });

  describe("browserSupportsWebAuthnAutofill", () => {
    const originalWindow = { ...window };
  
    afterEach(() => {
      global.window = originalWindow;
    });
  
    it("should return true if PublicKeyCredential.isConditionalMediationAvailable resolves to true", async () => {
      Object.defineProperty(window.PublicKeyCredential, "isConditionalMediationAvailable", {
        value: jest.fn(() => Promise.resolve(true)),
        writable: true,
      });
      expect(await browserSupportsWebAuthnAutofill()).toBe(true);
    });
  
    it("should return false if PublicKeyCredential.isConditionalMediationAvailable resolves to false", async () => {
      Object.defineProperty(window.PublicKeyCredential, "isConditionalMediationAvailable", {
        value: jest.fn(() => Promise.resolve(false)),
        writable: true,
      });
      expect(await browserSupportsWebAuthnAutofill()).toBe(false);
    });
  
    it("should return false if PublicKeyCredential.isConditionalMediationAvailable is undefined", async () => {
      Object.defineProperty(window.PublicKeyCredential, "isConditionalMediationAvailable", {
        value: undefined,
        writable: true,
      });
      expect(await browserSupportsWebAuthnAutofill()).toBe(false);
    });
  
    it("should return false if window.PublicKeyCredential is undefined", async () => {
      Object.defineProperty(window, "PublicKeyCredential", {
        value: undefined,
        writable: true,
      });
      expect(await browserSupportsWebAuthnAutofill()).toBe(false);
    });
  });

  describe("platformAuthenticatorIsAvailable", () => {
    const originalWindow = { ...window };
    const isUserVerifyingPlatformAuthenticatorAvailable = jest.fn();

    class MockedPublicKeyCredential {
      static isUserVerifyingPlatformAuthenticatorAvailable = isUserVerifyingPlatformAuthenticatorAvailable;
    }
  
    beforeEach(() => {
      Object.defineProperty(window, "PublicKeyCredential", {
        value: MockedPublicKeyCredential,
        writable: true
      });
    });
  
    afterEach(() => {
      global.window = originalWindow;
    });

    it("should return false if browserSupportsWebAuthn returns false", async () => {
      Object.defineProperty(window, "PublicKeyCredential", { value: undefined })
      expect(await platformAuthenticatorIsAvailable()).toBe(false);
    });
  
    it("should return true if browserSupportsWebAuthn returns true and isUserVerifyingPlatformAuthenticatorAvailable resolves to true", async () => {
      isUserVerifyingPlatformAuthenticatorAvailable.mockReturnValue(true);
      expect(await platformAuthenticatorIsAvailable()).toBe(true);
    });
  
    it("should return false if browserSupportsWebAuthn returns true and isUserVerifyingPlatformAuthenticatorAvailable resolves to false", async () => {
      isUserVerifyingPlatformAuthenticatorAvailable.mockReturnValue(false);
      expect(await platformAuthenticatorIsAvailable()).toBe(false);
    });
  
    it("should return false if window.PublicKeyCredential is undefined", async () => {
      Object.defineProperty(window, "PublicKeyCredential", { value: undefined });
      expect(await platformAuthenticatorIsAvailable()).toBe(false);
    });
  });
})

