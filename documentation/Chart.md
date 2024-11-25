### Chart.js Documentatie

**Beschrijving**: Chart.js is een eenvoudige JavaScript-bibliotheek voor het maken van grafieken en diagrammen. Het biedt een breed scala aan grafiektypen en is gemakkelijk te integreren met andere frameworks zoals Vue.js.

**Waarom gekozen voor Chart.js**:
1. **Eenvoudig te gebruiken**: Chart.js heeft een API die gemakkelijk te begrijpen en te gebruiken is.
2. **Grafieken**: Het ondersteunt verschillende grafiektypen zoals lijndiagrammen, staafdiagrammen, cirkeldiagrammen, radar.
3. **Aanpasbaarheid**: Chart.js biedt veel opties voor het aanpassen van de grafieken, inclusief kleuren, labels.
 
**Belangrijke punten**:
- **Installatie**: Hoe Chart.js te installeren en te integreren in je project.
- **Configuratieopties**: Overzicht van de belangrijkste configuratieopties en hoe deze te gebruiken.
- **Integratie met Vue.js**: Hoe Chart.js te gebruiken in combinatie met Vue.js.

**Installatie**:
Chart.js kan eenvoudig worden geïnstalleerd via npm:
```bash
npm install chart.js
```

**Gebruik**:
voorbeeld hoe Chart.js hebt gebruikt in project:
```javascript
renderChart() {
  if (this.chart) {
    this.chart.destroy();
  }

  const chart = document.getElementById('partyVotesChart').getContext('2d');
  const totalVotes = this.partyVotes.reduce((sum, vote) => sum + vote.validVotes, 0);

  this.chart = new Chart(chart, {
    type: 'bar',
    data: {
      labels: this.partyVotes.map(vote => vote.affiliation.registeredName),
      datasets: [{
        label: 'Stemmen',
        data: this.partyVotes.map(vote => vote.validVotes),
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      },
      plugins: {
        tooltip: {
          callbacks: {
            label: function(context) {
              const value = context.raw;
              const percentage = ((value / totalVotes) * 100).toFixed(2);
              return `${value} stemmen (${percentage}%)`;
            }
          }
        }
      }
    }
  });
}
```