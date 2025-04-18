import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vuetify from 'vite-plugin-vuetify';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vuetify({ autoImport: true }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5175 // Stel de gewenste poort in (5175)
  },
  optimizeDeps: {
    include: ['vuetify'],
  },
  build: {
    rollupOptions: {
      external: ['vuetify/components', 'vuetify/directives'],
    },
  },
});