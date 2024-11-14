# Architectuurcomponenten

## API Gateway
De API Gateway is het toegangspunt voor alle inkomende verzoeken van de frontend. Deze gateway is verantwoordelijk voor het routeren van de verzoeken naar de juiste microservices.

## Microservices

### PostService
- Verantwoordelijk voor het beheer van posts.
- Slaat de postgegevens op in een MySQL-database.

### ReviewService
- Beheert de reviewprocessen van posts.
- Maakt gebruik van een eigen MySQL-database voor opslag van reviewgegevens.

### CommentService
- Verantwoordelijk voor het beheren van reacties op posts.
- Slaat reactiegegevens op in een aparte MySQL-database.

## Communicatie tussen Microservices

### OpenFeign
Voor synchrone communicatie tussen de microservices:
- **ReviewService ↔ CommentService**: ReviewService haalt reacties op bij een afgekeurde review, zodat de redacteur weet welke wijzigingen nodig zijn.
- **PostService ↔ CommentService**: Directe communicatie om reacties bij een post te plaatsen.

### Message Bus
Voor asynchrone communicatie tussen de microservices:
- **PostService ↔ ReviewService**: Het reviewproces kan enige tijd duren, dus verloopt deze communicatie asynchroon via de Message Bus.

## Service Discovery

### Discovery Service
Maakt gebruik van een service discovery-patroon zodat microservices elkaar kunnen vinden zonder vaste configuraties.

## Configuratiebeheer

### Config Service
Beheert alle configuratie-instellingen van de microservices, zoals de configuratiebestanden `post-service.yml`, `review-service.yml`, en `comment-service.yml`. Hiermee kunnen de configuraties centraal worden beheerd.

## Meldingen

### Notification Service
Stuurt meldingen naar de redacteur wanneer een post wordt goedgekeurd of afgekeurd, zodat hij of zij direct op de hoogte is van de statuswijzigingen.
