<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-section about">
        <h3>Over Ons</h3>
        <p>Wij zijn een organisatie die zich inzet voor democratische waarden en transparantie.</p>
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
        <p>Telefoon: +31 123 456 789</p>
      </div>
      <div class="footer-section social">
        <h3>Volg Ons</h3>
        <div class="social-icons">
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-twitter"></i></a>
          <a href="#"><i class="fab fa-instagram"></i></a>
          <a href="#"><i class="fab fa-linkedin-in"></i></a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent"
}
</script>

<style scoped>
.footer {
  padding: 40px 0;
  background-color: var(--color-background);
  color: var(--color-text);
  text-align: center;
}

.footer-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.footer-section {
  flex: 1;
  min-width: 200px;
  margin: 20px;
}

.footer-section h3 {
  margin-bottom: 20px;
  font-size: 1.5em;
  color: var(--color-heading);
}

.footer-section p,
.footer-section a {
  text-decoration: none;
  margin: 5px 0;
  color: var(--color-text);
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
  color: var(--color-text);
}

.social-icons {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.social-icons a {
  font-size: 32px;
  color: var(--color-text);
  transition: color 0.3s;
}

.social-icons a:hover {
  color: var(--link-color);
}
</style>