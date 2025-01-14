<template>
  <header class="header">
    <div class="header-container">
      <div class="logo">
      </div>
      <nav :class="{ 'is-active': isMenuOpen }" class="nav">
        <ul>
          <li><router-link to="/">{{ $t('common.home') }}</router-link></li>
          <li><router-link to="/choose-results">{{ $t('common.results') }}</router-link></li>
          <li><router-link to="/partijenpagina">{{ $t('common.parties') }}</router-link></li>
          <li><router-link to="/login">{{ $t('common.login') }}</router-link></li>
          <li><router-link to="/Educativepage">{{ $t('Educativepage') }}</router-link></li>
          <li><router-link to="/forum">{{ $t('Forums') }}</router-link></li>
          <li><router-link to="/userForums">{{ ('My forums') }}</router-link></li>

        </ul>
      </nav>

      <div class="account">
        <router-link to="/account"><i class="fas fa-user"></i></router-link>
      </div>
      <label class="switch">
        <input type="checkbox" @change="toggleDarkMode" :checked="isDarkMode">
        <span class="slider round"></span>
      </label>
    </div>
  </header>
</template>

<script>
import {useI18n} from "vue-i18n";

export default {
  name: "HeaderComponent",
  setup() {
    const { locale } = useI18n();

    const toggleLocale = () => {
      locale.value = locale.value === 'en' ? 'nl' : 'en';
    };

    return {
      toggleLocale
    };
  },
  data() {
    return {
      isDarkMode: false,
      isMenuOpen: false
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
    },
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    }
  }
}
</script>

<style>
.header {
  background-color: var(--header-background-color);
  padding: 10px 0;
  width: 100%;
  top: 0;
  z-index: 1000;
}

.header-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 100%;
  flex-wrap: wrap;
}

.logo img {
  height: 40px;
}

.hamburger {
  display: none;
  flex-direction: column;
  justify-content: space-around;
  width: 30px;
  height: 21px;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  z-index: 10;
}

.hamburger .bar {
  width: 100%;
  height: 3px;
  background-color: #000;
  border-radius: 10px;
  transition: all 0.3s linear;
}

.nav {
  display: flex;
  align-items: center;
}

.nav.is-active {
  display: block;
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
  margin-top: 5px;
  background-color: #f1f1f1;
  border-radius: 20px;
  padding: 3px 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.search-input {
  padding: 5px 10px;
  border: none;
  border-radius: 20px;
  outline: none;
  flex-grow: 1;
  font-size: 14px;
  background-color: transparent;
}

.search-button {
  background-color: #007bff;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  padding: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

.search-button svg {
  fill: #ffffff;
  width: 20px;
  height: 20px;
}

.search-button:hover {
  background-color: #0056b3;
}

.account a {
  font-size: 24px;
  text-decoration: none;
}

.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
  margin-top: 10px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #e4e4e4;
  transition: .4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: #ffffff;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: black;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

@media (max-width: 768px) {
  .header-container {
    flex-direction: column;
    align-items: center;
  }

  .hamburger {
    display: flex;
  }

  .nav {
    display: none;
    flex-direction: column;
    align-items: center;
    width: 100%;
  }

  .nav ul {
    flex-direction: column;
    align-items: center;
  }

  .nav ul li {
    margin: 10px 0;
  }

  .search-container {
    margin-top: 20px;
  }

  .switch {
    margin-top: 20px;
  }
}
</style>