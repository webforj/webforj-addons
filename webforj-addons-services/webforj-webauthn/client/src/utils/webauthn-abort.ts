export class WebAuthnAbort {
  private controller: AbortController | undefined;

  /**
   * Creates a new AbortController and returns its signal, aborting any existing WebAuthn operation.
   * Call this method before starting a new WebAuthn operation.
   * @returns The signal of the new AbortController.
   */
  createAbortSignal() {
    if (this.controller) {
      const abortError = new Error(
        'Cancelling existing WebAuthn operation for new one',
      );
      abortError.name = 'AbortError';
      this.controller.abort(abortError);
    }

    this.controller = new AbortController();
    return this.controller.signal;
  }

  /**
   * Cancels any active WebAuthn operation.
   * Call this method to manually cancel an ongoing WebAuthn operation.
   */
  cancelCeremony() {
    if (this.controller) {
      const abortError = new Error(
        'Manually cancelling existing WebAuthn operation',
      );
      abortError.name = 'AbortError';
      this.controller.abort(abortError);
      this.controller = undefined;
    }
  }
}

/**
 * A service singleton to help ensure that only a single WebAuthn operation is active at a time.
 * Intended for use in projects that need to control the behavior of their UX in response to events.
 */
export const webAuthnAbort = new WebAuthnAbort();
