import { toAuthenticatorAttachment, toPublicKeyCredentialDescriptor } from '../transformers';
import { PublicKeyCredentialDescriptorJSON } from '../../types';
import { base64URLStringToBuffer } from '../communications';

jest.mock('../communications', () => ({
  base64URLStringToBuffer: jest.fn((str: string) => Buffer.from(str, 'base64')),
}));


describe("Transformers", () => {
  describe('toAuthenticatorAttachment', () => {
    it('should return undefined for null input', () => {
      const result = toAuthenticatorAttachment(null);
      expect(result).toBeUndefined();
    });
  
    it('should return undefined for an invalid string', () => {
      const result = toAuthenticatorAttachment('invalid');
      expect(result).toBeUndefined();
    });
  
    it('should return the correct enum value for "platform"', () => {
      const result = toAuthenticatorAttachment('platform');
      expect(result).toBe('platform');
    });
  
    it('should return the correct enum value for "cross-platform"', () => {
      const result = toAuthenticatorAttachment('cross-platform');
      expect(result).toBe('cross-platform');
    });
  
    it('should return undefined for an empty string', () => {
      const result = toAuthenticatorAttachment('');
      expect(result).toBeUndefined();
    });
  });
  
  describe('toPublicKeyCredentialDescriptor', () => {
    it('should transform a PublicKeyCredentialDescriptorJSON object to a PublicKeyCredentialDescriptor object', () => {
      const descriptorJSON: PublicKeyCredentialDescriptorJSON = {
        type: 'public-key',
        id: 'aGVsbG8=', // 'hello' in base64
        transports: ['usb', 'nfc'],
      };
  
      const expectedDescriptor: PublicKeyCredentialDescriptor = {
        type: 'public-key',
        id: Buffer.from('hello'),
        transports: ['usb', 'nfc'],
      };
  
      const result = toPublicKeyCredentialDescriptor(descriptorJSON);
  
      expect(result).toEqual(expectedDescriptor);
      expect(base64URLStringToBuffer).toHaveBeenCalledWith(descriptorJSON.id);
    });
  
    it('should handle descriptors without transports', () => {
      const descriptorJSON: PublicKeyCredentialDescriptorJSON = {
        type: 'public-key',
        id: 'aGVsbG8=', // 'hello' in base64
      };
  
      const expectedDescriptor: PublicKeyCredentialDescriptor = {
        type: 'public-key',
        id: Buffer.from('hello'),
        transports: undefined,
      };
  
      const result = toPublicKeyCredentialDescriptor(descriptorJSON);
  
      expect(result).toEqual(expectedDescriptor);
      expect(base64URLStringToBuffer).toHaveBeenCalledWith(descriptorJSON.id);
    });
  });
})
