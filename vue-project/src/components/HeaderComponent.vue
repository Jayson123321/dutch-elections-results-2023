<script>
export default {
  name: "HeaderComponent",
  data() {
    return {
      sidebarVisible: false
    };
  },
  methods: {
    toggleSidebar() {
      this.sidebarVisible = !this.sidebarVisible;
    }
  }
}
</script>

<template>
  <div>
    <div class="topnav">
      <div class="left-container">
        <div class="stripes" @click="toggleSidebar">&#9776;</div>
        <div class="title">Demo-Crazy</div>
      </div>
      <div class="search-container">
        <input type="text" class="search-input" placeholder="Search...">
        <button class="search-button">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
            <path fill="currentColor" d="M15.5 14h-.79l-.28-.27a6.471 6.471 0 001.48-5.34C15.24 5.64 13.3 3.7 10.88 3.7s-4.36 1.94-4.36 4.36S8.45 12.42 10.88 12.42c1.61 0 3.07-.61 4.13-1.64l.27.28v.79l4.25 4.25c.39.39.39 1.02 0 1.41-.39.39-1.02.39-1.41 0l-4.25-4.25zm-4.62 0C9.01 14 7.7 12.69 7.7 11s1.31-3 2.88-3 2.88 1.31 2.88 3-1.31 3-2.88 3z"/>
          </svg>
        </button>
      </div>
      <a href="#" class="account-icon"><i class="fas fa-user"></i></a>
    </div>
    <div :class="['sidebar', { 'sidebar-visible': sidebarVisible }]">
      <router-link to="/">Home</router-link>
      <router-link to="/partijenpagina">Partijen</router-link>
      <router-link to="/uitslagen">Uitslagen</router-link>
      <router-link to="/forum">Forum</router-link>
    </div>
  </div>
</template>

<style>
.sidebar {
  max-height: 0; /* Max hoogte is 0 wanneer niet zichtbaar */
  position: fixed; /* Vast bovenin het scherm */
  z-index: 1;
  top: 85px;
  left: 0;
  width: 250px;
  background-color: #78b178; /* Kleur van de sidebar */
  overflow: hidden; /* Verbergt de inhoud wanneer de hoogte 0 is */
  transition: max-height 0.5s ease; /* Soepele overgang voor max-height */
  padding: 0 20px; /* Padding om wat ruimte binnenin te creëren */
}

/* Sidebar zichtbaar maken */
.sidebar-visible {
  max-height: 500px; /* Max hoogte wanneer de sidebar geopend is */
  width: 250px;
}

/* Stijlen voor de links in de sidebar */
.sidebar a {
  display: block;
  padding: 12px 0;
  text-decoration: none;
  font-size: 18px;
  color: white;
  transition: color 0.3s;
}

.sidebar a:hover {
  color: #f1f1f1;
}

/* Top navigatiebalk */
.topnav {
  background-color: #78b178;
  width: 100%;
  margin: 0;
  padding: 10px 0;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.left-container {
  display: flex;
  align-items: center;
}

.stripes {
  color: white;
  font-size: 24px;
  padding: 14px 16px;
  cursor: pointer;
}

.title {
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 22px;
}

.search-container {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  max-width: 500px;
  width: 100%;
}

.search-input {
  width: 100%;
  padding: 12px 16px;
  padding-right: 50px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 50px;
  outline: none;
  transition: border 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

.search-input:focus {
  border: 2px solid #6200ea;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
}

.search-button {
  position: absolute;
  right: 10px;
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: #333;
  padding: 8px;
  transition: color 0.3s ease;
}

.search-button:hover {
  color: #6200ea;
}

.search-button svg {
  fill: currentColor;
  width: 24px;
  height: 24px;
}

.account-icon {
  color: white;
  font-size: 24px;
  padding: 14px 16px;
  text-decoration: none;
}


</style>