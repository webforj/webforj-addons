import { isValidDomain } from "../validation";

describe("validations", () => {
  describe('isValidDomain', () => {
    it('returns true for localhost', () => {
      expect(isValidDomain('localhost')).toBeTruthy();
    });

    it('returns true for valid domain names', () => {
      expect(isValidDomain('example.com')).toBeTruthy();
      expect(isValidDomain('subdomain.example.com')).toBeTruthy();
      expect(isValidDomain('test.co.uk')).toBeTruthy();
      expect(isValidDomain('123domain.com')).toBeTruthy();
      expect(isValidDomain('localhost')).toBeTruthy();
    });

    it('returns false for invalid domain names', () => {
      expect(isValidDomain('')).toBeFalsy();
      expect(isValidDomain('local host')).toBeFalsy();
      expect(isValidDomain('example')).toBeFalsy();
      expect(isValidDomain('.example.com')).toBeFalsy();
      expect(isValidDomain('example..com')).toBeFalsy();
      expect(isValidDomain('example-.com')).toBeFalsy();
      expect(isValidDomain('example.com.')).toBeFalsy();
      expect(isValidDomain('example.')).toBeFalsy();
      expect(isValidDomain('example_.com')).toBeFalsy();
    });

    it('handles non-string inputs gracefully', () => {
      expect(isValidDomain(undefined as any)).toBeFalsy();
      expect(isValidDomain(null as any)).toBeFalsy();
      expect(isValidDomain(123 as any)).toBeFalsy();
      expect(isValidDomain({} as any)).toBeFalsy();
      expect(isValidDomain([] as any)).toBeFalsy();
    });
  });
})