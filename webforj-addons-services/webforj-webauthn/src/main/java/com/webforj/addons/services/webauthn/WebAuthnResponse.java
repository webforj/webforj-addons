package com.webforj.addons.services.webauthn;

import com.google.gson.JsonElement;

/**
 * Represents the response envelope returned by the TypeScript WebAuthn client. Every client call
 * resolves with this structure.
 *
 * <p>This is a package-private deserialization target and is not part of the public API.
 */
class WebAuthnResponse {

  private boolean success;
  private JsonElement data;
  private ErrorPayload error;

  boolean isSuccess() {
    return success;
  }

  JsonElement getData() {
    return data;
  }

  ErrorPayload getError() {
    return error;
  }

  /**
   * The structured error details included in a failed response envelope.
   */
  static class ErrorPayload {
    private String code;
    private String message;
    private String name;

    String getCode() {
      return code;
    }

    String getMessage() {
      return message != null ? message : "Unknown error";
    }

    String getName() {
      return name;
    }
  }
}
