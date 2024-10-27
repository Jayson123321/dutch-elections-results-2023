<template>
  <header class="header">
    <div class="header-container">
      <div class="logo">
      </div>
      <nav class="nav">
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/services">Diensten</router-link></li>
          <li><router-link to="/about">Over Ons</router-link></li>
          <li><router-link to="/contact">Contact</router-link></li>
        </ul>
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
  </header>
</template>

<script>
export default {
  name: "HeaderComponent"
}
</script>

<style>
.header {
  background-color: #333;
  color: white;
  padding: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: fixed;
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
  color: white;
  text-decoration: none;
  font-size: 18px;
  transition: color 0.3s;
}

.nav ul li a:hover {
  color: #78b178;
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
  color: white;
  margin-left: 8px;
}

.search-button svg {
  fill: currentColor;
  width: 24px;
  height: 24px;
}

.account a {
  color: white;
  font-size: 24px;
  text-decoration: none;
}
</style>