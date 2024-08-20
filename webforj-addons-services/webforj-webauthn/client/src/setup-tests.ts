/**
 * JSDom doesn't seem to support `credentials`, so let's define them here so we can mock their
 * implementations in specific tests.
 */
Object.defineProperty(globalThis.window.navigator, 'credentials', {
  writable: true,
  value: {
    create: jest.fn(),
    get: jest.fn(),
  },
});

/**
 * Allow for setting values to `window.location.hostname`
 */
Object.defineProperty(window, 'location', {
  writable: true,
  value: {
    hostname: '',
  },
});