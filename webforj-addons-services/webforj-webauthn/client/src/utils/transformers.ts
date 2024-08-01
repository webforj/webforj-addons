import { PublicKeyCredentialDescriptorJSON } from "../types";
import { AUTHENTICATOR_ATTACHMENTS } from "../constants";
import { base64URLStringToBuffer } from "../utils/communications";

/**
 * Converts a string representation of authenticator attachment to its corresponding enum value.
 * 
 * This function converts a string representation of an authenticator attachment to its corresponding
 * enum value defined in the AuthenticatorAttachment enum. If the input string is not a valid authenticator
 * attachment or null, it returns undefined.
 * 
 * @param {string | null} attachment - The string representation of authenticator attachment.
 * @returns {AuthenticatorAttachment | undefined} The corresponding enum value of authenticator attachment, or undefined if the input is null or invalid.
 * 
 * @example
 * // Example usage:
 * const attachment1 = 'platform';
 * const attachment2 = 'cross-platform';
 * const attachment3 = null;
 * console.log(toAuthenticatorAttachment(attachment1)); // Output: AuthenticatorAttachment.Platform
 * console.log(toAuthenticatorAttachment(attachment2)); // Output: AuthenticatorAttachment.CrossPlatform
 * console.log(toAuthenticatorAttachment(attachment3)); // Output: undefined
 * 
 * @see {@link AuthenticatorAttachment} for the enum definition of authenticator attachment values.
 */
export function toAuthenticatorAttachment(
  attachment: string | null,
): AuthenticatorAttachment | undefined {
  if (!attachment) {
    return;
  }

  if (AUTHENTICATOR_ATTACHMENTS.indexOf(attachment as AuthenticatorAttachment) < 0) {
    return;
  }

  return attachment as AuthenticatorAttachment;
}

/**
 * Transforms a PublicKeyCredentialDescriptorJSON object to a PublicKeyCredentialDescriptor object.
 * 
 * @param {PublicKeyCredentialDescriptorJSON} descriptor - The PublicKeyCredentialDescriptorJSON object to convert.
 * @returns {PublicKeyCredentialDescriptor} The converted PublicKeyCredentialDescriptor object.
 */
export function toPublicKeyCredentialDescriptor(
  descriptor: PublicKeyCredentialDescriptorJSON,
): PublicKeyCredentialDescriptor {
  const { id } = descriptor;

  return {
    ...descriptor,
    id: base64URLStringToBuffer(id),
    /**
     * `descriptor.transports` is an array of our `AuthenticatorTransportFuture` that includes newer
     * transports that TypeScript's DOM lib is ignorant of. Convince TS that our list of transports
     * are fine to pass to WebAuthn since browsers will recognize the new value.
     */
    transports: descriptor.transports as AuthenticatorTransport[],
  };
}