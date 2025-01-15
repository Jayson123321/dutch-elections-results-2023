<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-section about">
      </div>
      <div class="footer-section links">
        <h3>Links</h3>
        <ul>
          <li><a href="#">Over Ons</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
      </div>
      <div class="footer-section contact">
        <h3>Contact</h3>
        <p>Email: info@democrazy.com</p>
      </div>
      <div class="footer-section social">
        <h3>Volg Ons</h3>
        <a href="#"><i class="fab fa-facebook-f"></i></a>
        <a href="#"><i class="fab fa-twitter"></i></a>
        <a href="#"><i class="fab fa-instagram"></i></a>
        <a href="#"><i class="fab fa-linkedin-in"></i></a>
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
  padding: 40px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
  bottom: 0;
}

.footer-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  width: 80%;
  max-width: 1200px;
}

.footer-section {
  padding: 0 20px;
}

.footer-section h3 {
  margin-bottom: 20px;
  font-size: 1.5em;
}

.footer-section p,
.footer-section a {
  text-decoration: none;
  margin: 5px 0;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section ul li {
  margin: 10px 0;
}

.footer-section ul li a {
  text-decoration: none;
}

.footer-section .social a {
  font-size: 24px;
  margin: 0 10px;
  text-decoration: none;
}

</style>