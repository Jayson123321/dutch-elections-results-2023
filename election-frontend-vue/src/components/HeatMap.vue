<template>
  <div id="map" style="height: 1000px;"></div>
</template>

<script>
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

export default {
  name: 'HeatMap',
  mounted() {
    const map = L.map('map').setView([52.3676, 4.9041], 7);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    fetch('/src/assets/gemeenten.xml')
        .then(response => response.text())
        .then(str => new window.DOMParser().parseFromString(str, 'text/xml'))
        .then(data => {
          const gemeenten = data.getElementsByTagName('gemeente');
          for (let i = 0; i < gemeenten.length; i++) {
            const gemeente = gemeenten[i];
            const name = gemeente.getElementsByTagName('name')[0].textContent;
            const coordinates = gemeente.getElementsByTagName('coordinate');
            const latLngs = [];
            for (let j = 0; j < coordinates.length; j++) {
              const [lng, lat] = coordinates[j].textContent.split(',').map(Number);
              latLngs.push([lat, lng]);
            }
            const polygon = L.polygon(latLngs, {
              color: 'blue',
              weight: 2,
              fillOpacity: 0.5
            }).addTo(map);
            polygon.bindPopup(name);
          }
        })
        .catch(error => console.error('Error loading XML:', error));
  }
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%;
}
</style>