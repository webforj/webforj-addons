import { base64URLStringToBuffer, bufferToBase64URLString, bufferToUTF8String, utf8StringToBuffer } from "../communications";

describe("communications", () => {
  describe("bufferToBase64URLString", () => {
    it("should convert an empty buffer to an empty string", () => {
      const buffer = new ArrayBuffer(0);
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("");
    });

    it("should convert a single byte buffer to the correct base64url string", () => {
      const buffer = new Uint8Array([255]).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("_w");
    });

    it("should convert a multi-byte buffer to the correct base64url string", () => {
      const buffer = new Uint8Array([72, 101, 108, 108, 111]).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("SGVsbG8");
    });

    it("should correctly replace '+' with '-' in the base64url string", () => {
      const buffer = new Uint8Array([250, 250, 250]).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("-vr6");
    });

    it("should correctly replace '/' with '_' in the base64url string", () => {
      const buffer = new Uint8Array([255, 255, 255]).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("____");
    });

    it("should correctly remove '=' padding in the base64url string", () => {
      const buffer = new Uint8Array([104, 101, 108, 108]).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBe("aGVsbA");
    });

    it("should handle large buffers correctly", () => {
      const buffer = new Uint8Array(1024).buffer;
      const result = bufferToBase64URLString(buffer);
      expect(result).toBeDefined();
      expect(typeof result).toBe("string");
      expect(result.length).toBeGreaterThan(0);
    });

    it("should handle special characters correctly", () => {
      const specialChars = new Uint8Array([33, 34, 35, 36, 37, 38, 39, 40, 41, 42]).buffer;
      const result = bufferToBase64URLString(specialChars);
      expect(result).toBe("ISIjJCUmJygpKg");
    });
  });

  describe("base64URLStringToBuffer", () => {
    it("converts a valid Base64URL string to an ArrayBuffer", () => {
      const base64URLString = "SGVsbG8gV29ybGQh";
      const buffer = base64URLStringToBuffer(base64URLString);
  
      expect(buffer.byteLength).toBe(12);
      const uint8Array = new Uint8Array(buffer);
      expect(String.fromCharCode.apply(null, uint8Array)).toBe("Hello World!");
    });
  
    it("handles Base64URL strings with padding correctly", () => {
      const base64URLString = "SGVsbG8gV29ybGQh";
      const buffer = base64URLStringToBuffer(base64URLString);
  
      expect(buffer.byteLength).toBe(12);
      const uint8Array = new Uint8Array(buffer);
      expect(String.fromCharCode.apply(null, uint8Array)).toBe("Hello World!");
    });
  
    it("handles Base64URL strings without padding correctly", () => {
      const base64URLString = "SGVsbG8gV29ybGQ";
      const buffer = base64URLStringToBuffer(base64URLString);
  
      const uint8Array = new Uint8Array(buffer);
      expect(String.fromCharCode.apply(null, uint8Array)).toBe("Hello World");
    });
  
    it("returns an empty buffer for an empty input", () => {
      const base64URLString = "";
      const buffer = base64URLStringToBuffer(base64URLString);
  
      expect(buffer.byteLength).toBe(0);
    });
  });

  describe("bufferToUTF8String", () => {
    it("converts an ArrayBuffer with UTF-8 encoded data to a string", () => {
      const buffer = new ArrayBuffer(4);
      const view = new Uint8Array(buffer);
      view[0] = 72;
      view[1] = 101;
      view[2] = 108;
      view[3] = 108;
      const utf8String = bufferToUTF8String(buffer);
  
      expect(utf8String).toBe("Hell");
    });
  
    it("handles empty ArrayBuffer", () => {
      const buffer = new ArrayBuffer(0);
      const utf8String = bufferToUTF8String(buffer);
  
      expect(utf8String).toBe("");
    });
  
    it("converts a longer UTF-8 sequence correctly", () => {
      const buffer = new Uint8Array([240, 159, 140, 141, 240, 159, 140, 142]).buffer;
      const utf8String = bufferToUTF8String(buffer);
  
      expect(utf8String).toBe("🌍🌎");
    });
  });

  describe("utf8StringToBuffer", () => {
    it("converts a simple ASCII string to an ArrayBuffer", () => {
      const value = "Hello";
      const buffer = utf8StringToBuffer(value);
      const decodedString = new TextDecoder("utf-8").decode(buffer);
  
      expect(decodedString).toBe(value);
    });
  
    it("converts a string with non-ASCII characters to an ArrayBuffer", () => {
      const value = "ü€";
      const buffer = utf8StringToBuffer(value);
      const decodedString = new TextDecoder("utf-8").decode(buffer);
  
      expect(decodedString).toBe(value);
    });
  
    it("handles an empty string", () => {
      const value = "";
      const buffer = utf8StringToBuffer(value);
      const decodedString = new TextDecoder("utf-8").decode(buffer);
  
      expect(decodedString).toBe(value);
    });
  
    it("converts a string with emoji to an ArrayBuffer", () => {
      const value = "🌍🌎";
      const buffer = utf8StringToBuffer(value);
      const decodedString = new TextDecoder("utf-8").decode(buffer);
  
      expect(decodedString).toBe(value);
    });
  });
});
