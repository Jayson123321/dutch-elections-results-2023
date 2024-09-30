<script>
import FooterComponent from './FooterComponent.vue'
import HeaderComponent from './HeaderComponent.vue'

export default {
  name: "Home",
  components: {
    FooterComponent,
    HeaderComponent
  }
}
</script>

<template>
  <div>
    <HeaderComponent />
    <div class="homepage">
      <h1>Welkom bij Demo-Crazy</h1>
      <p>De plek waar je alles kunt vinden over de politieke partijen in Nederland.</p>

      <div class="flexcontainer">
           <div class="flexbox1">
             <h2>Partijen</h2>
             <p>Ontdek de politieke partijen in Nederland.</p>
           </div>
            <div class="flexbox2">
              <h2>Uitslagen</h2>
              <p>bekijk de uitslagen van de verkiezingen.</p>
            </div>
            <div class="flexbox3">
              <h2>Forum</h2>
              <p>Chat voor politieke meningen.</p>
            </div>
      </div>

    </div>
    <FooterComponent />
  </div>
</template>

<style>
.homepage {
  margin-top: 100px;
  text-align: center;
  align-items: center;
  height: calc(100vh - 200px); /* hoogte van de pagina minus de header en footer */
}

.flexcontainer {
  display: flex;
  justify-content: space-around;
  margin-top: 50px;
  margin-right: 50px;
  align-items: center;
}
</style>