<template>
  <header class="header">
    <div class="header-container">
      <div class="logo">
      </div>
      <nav class="nav">
        <div class="header-section links">
          <ul>
            <li><router-link to="/">Home</router-link></li>
            <li><router-link to="/about">Over Ons</router-link></li>
            <li><router-link to="/contact">Contact</router-link></li>
          </ul>
        </div>
      </nav>

      <div class="search-container">
        <input type="text" class="search-input" placeholder="Zoeken...">
        <button class="search-button">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24">
            <path fill="currentColor" d="M15.5 14h-.79l-.28-.27a6.471 6.471 0 001.48-5.34C15.24 5.64 13.3 3.7 10.88 3.7s-4.36 1.94-4.36 4.36S8.45 12.42 10.88 12.42c1.61 0 3.07-.61 4.13-1.64l.27.28v.79l4.25 4.25c.39.39.39 1.02 0 1.41-.39.39-1.02.39-1.41 0l-4.25-4.25zm-4.62 0C9.01 14 7.7 12.69 7.7 11s1.31-3 2.88-3 2.88 1.31 2.88 3-1.31 3-2.88 3z"/>
          </svg>
        </button>
      </div>
      <div class="account">
        <router-link to="/account"><i class="fas fa-user"></i></router-link>
      </div>
    </div>
    <svg xmlns="http://www.w3.org/2000/svg" width="20px" height="17px" viewBox="0 0 48 48" id="b" @click="toggleDarkMode" class="toggle-dark-mode-image">
      <defs>
      </defs>
      <path class="c" d="m32.8,29.3c-8.9-.8-16.2-7.8-17.5-16.6-.3-1.8-.3-3.7,0-5.4.2-1.4-1.4-2.3-2.5-1.6C6.3,9.7,2.1,16.9,2.5,25c.5,10.7,9,19.5,19.7,20.4,10.6.9,19.8-6,22.5-15.6.4-1.4-1-2.6-2.3-2-2.9,1.3-6.1,1.8-9.6,1.5Z"/>
    </svg>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
  data() {
    return {
      isDarkMode: false
    };
  },
  methods: {
    toggleDarkMode() {
      this.isDarkMode = !this.isDarkMode;
      if (this.isDarkMode) {
        document.documentElement.setAttribute('data-theme', 'dark');
      } else {
        document.documentElement.setAttribute('data-theme', 'light');
      }
    }
  }
}
</script>

<style>
.header {
  padding: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  top: 0;
  z-index: 1000;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 80%;
  max-width: 1200px;
}

.logo img {
  height: 40px;
}

.nav ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

.nav ul li {
  margin: 0 15px;
}

.nav ul li a {
  text-decoration: none;
  font-size: 18px;
  transition: color 0.3s;

}

.search-container {
  display: flex;
  align-items: center;
}

.search-input {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  outline: none;
}

.search-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  margin-left: 8px;
}

.search-button svg {
  fill: currentColor;
  width: 24px;
  height: 24px;
}

.account a {
  font-size: 24px;
  text-decoration: none;
}

.toggle-dark-mode-image {
  cursor: pointer;
}

</style>