import { WebAuthnAbort } from "../webauthn-abort";

describe("WebAuthn abort", () => {
  describe('WebAuthnAbort', () => {
    let service: WebAuthnAbort;

    beforeEach(() => {
      service = new WebAuthnAbort();
    });

    afterEach(() => {
      service = undefined as any;
    });

    it('creates a new AbortController and returns its signal', () => {
      const signal = service.createAbortSignal();

      expect(signal).toBeInstanceOf(AbortSignal);
      expect(service['controller']).toBeInstanceOf(AbortController);
    });

    it('aborts existing WebAuthn operation when creating a new one', () => {
      service.createAbortSignal();
      const abortSpy = jest.spyOn(service['controller']!, 'abort');

      const newSignal = service.createAbortSignal();

      expect(abortSpy).toHaveBeenCalledTimes(1);
      expect(abortSpy).toHaveBeenCalledWith(expect.any(Error));
      expect(newSignal).toBeInstanceOf(AbortSignal);
      expect(service['controller']).toBeInstanceOf(AbortController);
    });

    it('cancels an ongoing WebAuthn operation', () => {
      service.createAbortSignal();
      const abortSpy = jest.spyOn(service['controller']!, 'abort');

      service.cancelCeremony();

      expect(abortSpy).toHaveBeenCalledTimes(1);
      expect(abortSpy).toHaveBeenCalledWith(expect.any(Error));
      expect(service['controller']).toBeUndefined();
    });

    it('handles cancelling when no operation is active', () => {
      const abortSpy = jest.spyOn(AbortController.prototype, 'abort');

      service.cancelCeremony();

      expect(abortSpy).not.toHaveBeenCalled();
      expect(service['controller']).toBeUndefined();
    });
  });
})