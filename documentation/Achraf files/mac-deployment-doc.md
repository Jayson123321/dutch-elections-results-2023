# Deployment documentatie Mac

Deze beginnersvriendelijke versie van de deployment documentatie is gemaakt voor mensen die voor het eerst aan de slag gaan met deployen. Daarnaast is dit ook handig voor de mensen die deployen op een Mac. Ik heb dit gemaakt omdat ik tegen wat dingen aanliep en andere teamgenoten wil helpen.

## Belangrijk om te begrijpen
### Wat is Render?
Render is een cloudplatform waarmee je makkelijk websites, APi's en databases kunt hosten. Dit is wat onze groep gebruikt binnen dit project. Render automatiseert veel processen voor ons die technisch zijn waardoor wij snel en makkelijk onze project live kunnen krijgen zonder dat het te ingewikkeld wordt.
### Wat is Docker?
Docker is een programma waarmee we onze project kunnen maken, uitsturen en draaien. Dit doen we in containers, dit zijn hele kleine omgevingen die alles hebben wat onze project nodig heeft om te werken. Dit is handig want dan kan onze project draaien op elke omgeving.
### Wat is een image?
In docker gebruiken we images, dit is de basis bestand voor de containers die we gebruiken. Dit is waar alle code, logicatie, libaries etc in zitten voor de containers. Een image is een vast bestand waarmee we de containers kunnen maken in docker.


## Before we start
- Download docker desktop
- Maak een account aan op Docker hub
- Maak een account aan op Render (Werkt beter als je een account maakt met HvA gitlab)
- Maak een deployment branche die up to date is


## De frontend
Als alles gelukt is gaan we als eerst beginnen met het deployen van de frontend. Navigeer naar de frontend in je IntelliJ terminal.
 ```sh
    cd frontend
   ```
Dit doe je door de cd command en dan tab in te drukken totdat je de juiste mapje ziet die je nodig hebt, in dit geval is dat de frontend mapje.
<br> <br>
Als je in de juiste mapje zit dan kan je beginnen met het opzetten van de deployment:
 ```sh
    npm install
   ```
Daarna:
 ```sh
    npm run build
   ```
Als dat gelukt is dan gaan we aan de slag met het maken van je eerste image, de frontend image. Dit gaan we bouwen door de volgende command:
 ```sh
    docker build -t frontend-image .
   ```
De volgende stap is dan deze frontend image te pushen naar je docker hub maar voordat we dat doen gaan we het eerst taggen:
```sh
    docker tag frontend-image gebruikersnaam/frontend-image
   ```
Nu kan je pushen:
```sh
    docker push gebruikersnaam/frontend-image
   ```
Hierna kan je beginnen met maken van een nieuwe web service op Render voor deze gepushte image.
1. Ga naar Render op je browser en navigeer naar je dashboard
2. Rechts boven klik je een knop met "Add new"
3. Daar selecteer je de optie "Web service"
4. Van de 3 opties die je krijgt, kies je voor de optie "Existing image"
5. Voor de image URL navigeer je naar je docker desktop, zoek je de naam die je hebt gegeven voor de frontend image. Als je erop klikt zie je boven de naam met ":latest" erachter. Die kopieer je en plak je het in de image URL sectie op Render.
6. Selecteer je credential (docker username) en klik op connect
7. Verander de region naar een region dat dichtbij is (in ons geval frankfurt) en selecteer ook de gratis hosting optie.
8. En nu kan je gaan builden, terwijl dat bezig is kan je aan de slag gaan met de backend

## De backend
Om de backend te deployen gebruiken we net andere commands in de terminal maar we doen daarna precies het zelfde in Docker en Render.
<br> <br>
Nu kan je naar de backend navigeren in je IntelliJ terminal. Met deze command ga je terug uit de frontend
```sh
    cd ..
   ```
Navigeer op de zelfde manier die je voor de frontend hebt gebruikt, nu naar de backend
 ```sh
    cd backend
   ```
Inplaats van npm run gaan we nu werken met Maven. Daarbij hoort deze command
  ```sh
    mvn clean install
 ```
Bouw weer een image maar dan voor de backend
 ```sh
    docker build -t backend-image .
   ```
Tag en push nu ook de backend
```sh
    docker tag backend-image gebruikersnaam/backend-image
    docker push gebruikersnaam/backend-image
 ```
Nu kan je de stappen herhalen die we hebben uitgevoerd op Docker en Render.

## APIconfig.java & config.ts
Stel de juiste url's in zodat je goed kunt verbinden tussen de back en frontend.

## Problemen?
Voor problemen kan je altijd mij aanspreken in de klas en we gaan er samen doorheen :). Ik heb er zelf veel meegemaakt tijdens het deployen voor de eerste keer. Dit is compleet normaal en zodra je het 1x hebt gedaan wordt het alleen maar makkelijker de keren daarop.