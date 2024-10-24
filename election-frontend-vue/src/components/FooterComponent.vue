<script>
export default {
  name: "" +
      "FooterComponent"
}
</script>

<template>
  <div class="footer">
    <div class="footer-section">
      <a href="#">Over ons</a>
    </div>
    <div class="footer-section">
      <h3>Contact</h3>
      <p>Email: demo-crazy@gmail.com</p>
      <p>Telefoon: 06-12345678</p>
    </div>
    <div class="footer-section">
      <h3>Volg Ons</h3>
      <a href="#"><i class="fab fa-facebook"></i></a>
      <a href="#"><i class="fab fa-twitter"></i></a>
      <a href="#"><i class="fab fa-instagram"></i></a>
    </div>


  </div>
</template>

<style>
.footer {
  background-color: #78b178;
  color: white;
  padding: 20px 0;
  display: flex;
  justify-content: space-around;
  align-items: center;
  position: fixed;
  bottom: 0;
  width: 100%;
  left: 0;
  height: 70px;
}

.footer-section {
  text-align: center;
  flex: 1;
}

.footer-section h3 {
  margin-bottom: 10px;
}

.footer-section p,
.footer-section a {
  color: white;
  text-decoration: none;
  margin: 5px 0;
}

.footer-section a {
  font-size: 24px;
  margin: 0 10px;
}

.footer-section a:hover {
  color: #78b178;
}
.footer-section a {
  font-size: 24px;
  margin: 0 10px;
  text-decoration: none;
  border-bottom: 1px solid white; /* Lijntje onder de tekst */
}

</style>