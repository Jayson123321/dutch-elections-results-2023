<script>
export default {
  name: "HeaderComponent"
}
</script>

<template>
  <div class="topnav">
    <div class="stripes">&#9776;</div>
    <div class="title">Demo-Crazy</div>
    <button class="partijenbtn">Partijen</button>
    <div class="search-container">
      <input type="text" class="search-bar" placeholder="Search...">
      <button class="search-button">Go</button>
    </div>
    <a href="#" class="account-icon"><i class="fas fa-user"></i></a>
  </div>
</template>

<style>
.topnav {
  background-color: #78b178;
  overflow: hidden;
  width: 100%;
  margin: 0;
  padding: 10px 0;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
}

.stripes {
  float: left;
  color: white;
  font-size: 24px;
  padding: 14px 16px;
  cursor: pointer;
}

.title {
  float: left;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 22px;
}



.search-container {
  display: flex;
  align-items: center;
  flex-grow: 1;
  justify-content: center;
  margin-right: 300px;
}

.search-bar {
  width: 300px;
  padding: 8px 12px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 50px;
  outline: none;
  transition: all 0.3s ease;
}

.search-bar:hover,
.search-bar:focus {
  border-color: #6c63ff;
  box-shadow: 0 4px 8px rgba(108, 99, 255, 0.2);
}

.search-button {
  background-color: #6c63ff;
  border: none;
  color: white;
  padding: 8px 12px;
  margin-left: -40px;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.search-button:hover {
  background-color: #574bdb;
}

.partijenbtn {
  float: left;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 22px;
  background-color: #78b178;
  border: none;
}

.account-icon {
  color: white;
  font-size: 24px;
  padding: 14px 16px;
  text-decoration: none;
}
</style>