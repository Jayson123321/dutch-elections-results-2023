<template>
  <div id="map" style="height: 600px;"></div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import L from 'leaflet';

export default defineComponent({
  name: 'Heatmap',
  setup() {
    onMounted(() => {
      const map = L.map('map').setView([52.1326, 5.2913], 7);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(map);

      const heatmapData = [
        [52.370216, 4.895168, 0.5], // Amsterdam
      ];

      L.heatLayer(heatmapData, {
        radius: 25,
        blur: 15,
        maxZoom: 17,
      }).addTo(map);
    });
  }
});
</script>

<style scoped>
#map {
  height: 600px;
}
</style>