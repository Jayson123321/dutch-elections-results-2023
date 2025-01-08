<template>
  <div>
    <HeaderComponent />
    <div class="educatieve-pagina">
      <h1>Uitleg over Verkiezingen</h1>
      <p class="intro-text">
        Welkom op de pagina over de verkiezingen van Nederland! Hier kun je alles leren over hoe verkiezingen werken, hoe politieke partijen zetels winnen in de Tweede Kamer en wat je kunt doen met de informatie op deze website.
      </p>

      <section class="content-section">
        <h2>Wat zijn verkiezingen?</h2>
        <p>
          Verkiezingen zijn momenten waarop burgers kunnen kiezen wie hen gaat vertegenwoordigen in de politiek. In Nederland hebben we verschillende verkiezingen, maar de belangrijkste is de <strong>Tweede Kamerverkiezing</strong>. Dit is de verkiezing waarbij we stemmen op politieke partijen en kandidaten die in de Tweede Kamer gaan zitten. De Tweede Kamer controleert de regering en maakt wetten.
        </p>
        <p>
          Tijdens de verkiezingen kiest Nederland dus <strong>politieke partijen</strong> en <strong>kandidaten</strong>. Elke partij heeft een lijst van mensen die ze naar de Tweede Kamer willen sturen. Elke kiezer mag kiezen op welke partij en kandidaat hij of zij wil stemmen. Het aantal zetels dat een partij wint, hangt af van het aantal stemmen dat ze krijgen.
        </p>
      </section>

      <section class="content-section">
        <h2>Hoe werken de uitslagen?</h2>
        <p>
          Na de verkiezingen wordt er gekeken hoeveel stemmen elke partij heeft gekregen. De stemmen worden omgezet in <strong>zetels</strong> in de Tweede Kamer. Nederland gebruikt een systeem dat de <strong>systematische evenredige vertegenwoordiging</strong> heet. Dit betekent dat als een partij veel stemmen krijgt, ze ook veel zetels krijgt. Als een partij minder stemmen krijgt, krijgt die partij minder zetels.
        </p>
        <p>
          De zetels worden verdeeld volgens een <strong>d'Hondt-systeem</strong>, waarbij de stemmen van de partijen worden gedeeld door een reeks getallen. Hoe meer stemmen een partij heeft, hoe meer kans die partij heeft om zetels te winnen.
        </p>
      </section>

      <section class="content-section">
        <h2>Hoe kun je de verkiezingsuitslagen analyseren?</h2>
        <p>Als je de uitslagen bekijkt, kun je letten op de volgende dingen:</p>
        <ul>
          <li><strong>Welke partij heeft de meeste zetels?</strong> Dit betekent dat zij waarschijnlijk de grootste invloed hebben in de politiek.</li>
          <li><strong>Welke kandidaten hebben de meeste stemmen gekregen?</strong> Dit zijn de mensen die namens de partij in de Tweede Kamer zullen zitten.</li>
          <li><strong>Wat is het belangrijkste thema in de verkiezingen?</strong> De onderwerpen waar de meeste partijen over praten, zijn vaak belangrijke thema's voor de verkiezingen.</li>
        </ul>
        <p>
          Het is ook belangrijk om te weten dat de verkiezingsuitslagen niet altijd meteen de volgende dag duidelijk zijn. Het kan even duren voordat alle stemmen geteld zijn, vooral bij stemmen die per post worden ingeleverd.
        </p>
      </section>

      <section class="content-section">
        <h2>Wat kun je doen op deze website?</h2>
        <p>Op deze website kun je veel meer leren over de verkiezingen en hoe ze werken:</p>
        <ul>
          <li><strong>Quiz:</strong> Test je kennis over de verkiezingen! De quiz helpt je om te begrijpen hoe verkiezingen in Nederland werken en wie er allemaal meedoen. Door de quiz te maken, krijg je een beter idee van hoe de politiek in Nederland werkt en kun je zien hoeveel je al weet.</li>
          <li><strong>PowerPoint:</strong> Je kunt een PowerPoint-presentatie downloaden die alles uitlegt over de verkiezingen. Deze is handig voor school of als je gewoon meer wilt leren. De PowerPoint bevat duidelijke uitleg over de verkiezingssystemen, de partijen, en de geschiedenis van verkiezingen in Nederland.</li>
        </ul>
      </section>

      <section class="content-section">
        <h2>Wat kun je verder leren?</h2>
        <p>Op de website kun je meer lezen over:</p>
        <ul>
          <li>De verschillende <strong>politieke partijen</strong> in Nederland en wat ze belangrijk vinden.</li>
          <li>De <strong>verkiezingsprocedures</strong>, zoals het stemmen en het tellen van stemmen.</li>
          <li>Hoe politieke partijen hun <strong>verkiezingscampagnes</strong> voeren om kiezers te overtuigen.</li>
        </ul>
        <p>
          Als je meer wilt leren, kun je ook de andere pagina's op de website bekijken, zoals de uitleg over politieke partijen en de geschiedenis van de verkiezingen in Nederland.
        </p>
      </section>

      <section class="content-section summary-section">
        <h2>Samenvatting</h2>
        <p>
          Verkiezingen zijn belangrijk, want ze bepalen wie er in de regering zit en wie de wet mag maken. De resultaten van de verkiezingen laten zien welke partijen het meeste steun hebben van de kiezers. Hier op de website kun je alles leren over hoe verkiezingen werken en hoe je de uitslagen kunt begrijpen. Vergeet niet om de quiz te maken en de PowerPoint te downloaden om je kennis te testen!
        </p>
      </section>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import '/src/assets/base.css';

export default {
  name: "verkiezingsuitleg",
  components: {
    FooterComponent,
    HeaderComponent,
  },
};
</script>

<style scoped>
.educatieve-pagina {
  margin-top: 100px;
  padding: 20px;
  min-height: calc(100vh - 200px);
  font-family: 'Arial', sans-serif;
  text-align: center;
}

h1 {
  color: #ff6600;
  font-size: 36px;
  margin-bottom: 20px;
  font-weight: bold;
}

h2 {
  color: #ff6600;
  font-size: 28px;
  margin-top: 30px;
  font-weight: bold;
}

p {
  font-size: 16px;
  line-height: 1.8;
}

ul {
  list-style-type: disc;
  margin-left: 20px;
  text-align: left;
  font-size: 16px;
}

ul li {
  margin-bottom: 10px;
}

button {
  background-color: #ff6600;
  padding: 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  width: 100%;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #cc5200;
  transform: translateY(-2px);
}

.content-section {
  padding: 20px;
  border-radius: 12px;
  margin-bottom: 30px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.summary-section {
  background-color: #ff6600;
  padding: 20px;
  border-radius: 12px;
}

footer {
  margin-top: 50px;
}
</style>
