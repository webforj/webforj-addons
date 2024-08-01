import typescript from '@rollup/plugin-typescript';
import nodeResolve from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';

export default [
  {
    input: 'src/index.ts',
    output: {
      dir: 'dist',
      format: 'umd',
      name: 'dwcWebAuthn',
      entryFileNames: 'webauthn.js',
      plugins: [terser()],
    },
    plugins: [
      typescript({ tsconfig: './tsconfig.es5.json' }),
      nodeResolve(),
    ],
  },
];
