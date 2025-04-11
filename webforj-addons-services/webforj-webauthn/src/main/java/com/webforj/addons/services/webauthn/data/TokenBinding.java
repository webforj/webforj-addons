package com.webforj.addons.services.webauthn.data;

/**
 * Represents a Token Binding, which is a cryptographic protocol for creating long-lived, uniquely
 * identifiable TLS bindings spanning multiple TLS sessions and connections. Token Bindings prevent
 * token export and replay attacks by cryptographically binding security tokens to the TLS layer.
 *
 * @see <a href= "https://www.w3.org/TR/webauthn-3/#dictdef-tokenbinding">Token Binding Protocol</a>
 * @author @ElyasSalar
 * @since 1.00
 */
public record TokenBinding(String id, TokenBindingStatus status) {}
