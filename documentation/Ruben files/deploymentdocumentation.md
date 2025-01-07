# Deployment Documentatie

## Inleiding

Deze documentatie beschrijft het proces van het deployen van de frontend en backend van onze applicatie met behulp van Docker en Render. Het deployment proces is uitgevoerd in de `deployment` branch.

## Voorbereiding

1. **Docker installeren**: Zorg ervoor dat Docker is geïnstalleerd op je machine. Volg de installatie-instructies op de [officiële Docker website](https://www.docker.com/get-started).
2. **Render account aanmaken**: Maak een account aan op [Render](https://render.com) en configureer je project.

## Frontend Deployment

1. **Frontend bouwen**: Navigeer naar de frontend directory en bouw de frontend applicatie.
    ```sh
    cd frontend
    npm install
    npm run build
    ```
2. **Docker image maken voor de frontend**:
    ```sh
    docker build -t frontend-image .
    ```
3. **Docker image pushen naar Docker Hub**:
    ```sh
    docker tag frontend-image your-dockerhub-username/frontend-image
    docker push your-dockerhub-username/frontend-image
    ```
4. **Frontend deployen op Render**: Maak een nieuwe web service aan op Render en gebruik de gepushte Docker image.

## Backend Deployment

1. **Backend bouwen**: Navigeer naar de backend directory en bouw de backend applicatie.
    ```sh
    cd backend
    mvn clean install
    ```
2. **Docker image maken voor de backend**:
    ```sh
    docker build -t backend-image .
    ```
3. **Docker image pushen naar Docker Hub**:
    ```sh
    docker tag backend-image your-dockerhub-username/backend-image
    docker push your-dockerhub-username/backend-image
    ```
4. **Backend deployen op Render**: Maak een nieuwe web service aan op Render en gebruik de gepushte Docker image.

## Link Aanpassen

De link naar de gedeployde applicatie kan verschillen per teamlid. Zorg ervoor dat je de juiste link gebruikt wanneer je de applicatie benadert.

## Hulp en Ondersteuning

Het deployment proces is uitgevoerd met behulp van de handleiding in het online leergebied (DLO) en met de hulp van Jayson.

## Conclusie

Door de bovenstaande stappen te volgen, kun je de frontend en backend van de applicatie succesvol deployen met Docker en Render. Pas de links aan waar nodig om ervoor te zorgen dat de applicatie correct werkt.