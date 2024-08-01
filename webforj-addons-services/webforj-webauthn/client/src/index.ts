export { register } from './methods/register';
export { authenticate } from './methods/authenticate';
export { webAuthnAbort } from './utils/webauthn-abort';
export {
  browserSupportsWebAuthn,
  browserSupportsWebAuthnAutofill,
  platformAuthenticatorIsAvailable,
} from './utils/browser-supports';
