<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-section about">
        <h3>Over Ons</h3>
        <p>Wij zijn een toonaangevend bedrijf in de sector, toegewijd aan het leveren van de beste diensten aan onze klanten.</p>
      </div>
      <div class="footer-section links">
        <h3>Navigatie</h3>
        <ul>
          <li><a href="#">Home</a></li>
          <li><a href="#">Diensten</a></li>
          <li><a href="#">Over Ons</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
      </div>
      <div class="footer-section contact">
        <h3>Contact</h3>
        <p>Email: info@bedrijf.com</p>
        <p>Telefoon: 06-12345678</p>
      </div>
      <div class="footer-section social">
        <h3>Volg Ons</h3>
        <a href="#"><i class="fab fa-facebook"></i></a>
        <a href="#"><i class="fab fa-twitter"></i></a>
        <a href="#"><i class="fab fa-instagram"></i></a>
        <a href="#"><i class="fab fa-linkedin"></i></a>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent"
}
</script>

<style>
.footer {
  background-color: #333;
  color: white;
  padding: 40px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
  bottom: 0;
}

.footer-container {
  display: flex;
  justify-content: space-between;
  width: 80%;
  max-width: 1200px;
}

.footer-section {
  flex: 1;
  padding: 0 20px;
}

.footer-section h3 {
  margin-bottom: 20px;
}

.footer-section p,
.footer-section a {
  color: white;
  text-decoration: none;
  margin: 5px 0;
}

.footer-section a:hover {
  color: #78b178;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section ul li {
  margin: 10px 0;
}

.footer-section ul li a {
  color: white;
  text-decoration: none;
}

.footer-section ul li a:hover {
  color: #78b178;
}

.footer-section .social a {
  font-size: 24px;
  margin: 0 10px;
  text-decoration: none;
}

.footer-section .social a:hover {
  color: #78b178;
}
</style>