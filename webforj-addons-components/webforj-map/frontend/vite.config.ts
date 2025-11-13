import { defineConfig } from 'vite'

export default defineConfig({
  build: {
    lib: {
      entry: 'src/w-map.ts',
      name: 'WMapElement',
      fileName: 'w-map',
      formats: ['es']
    },
    outDir: 'dist/static',
    minify: true,
    sourcemap: false,
    rollupOptions: {
      output: {
        entryFileNames: 'w-map.js',
        assetFileNames: '[name][extname]'
      }
    }
  },
  resolve: {
    alias: {
      '@': '/src'
    }
  }
})