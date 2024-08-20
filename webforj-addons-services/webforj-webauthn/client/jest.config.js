/**
 * @type {import("jest").Config}
 */
export default {
  preset: 'ts-jest',
  collectCoverageFrom: ['<rootDir>/src/**/*.{js,ts}'],
  coverageDirectory: 'coverage',
  testEnvironment: '<rootDir>/jest-environment.js',
  setupFilesAfterEnv: ['<rootDir>/src/setup-tests.ts'],
};