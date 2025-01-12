# Fullstack Java Project

## Sander Van Overloop (3AONC)

## Folder structure

- Readme.md
- _architecture_: this folder contains documentation regarding the architecture of your system.
- `docker-compose.yml` : to start the backend (starts all microservices)
- _backend-java_: contains microservices written in java
- _demo-artifacts_: contains images, files, etc that are useful for demo purposes.
- _frontend-web_: contains the Angular webclient

Each folder contains its own specific `.gitignore` file.  
**:warning: complete these files asap, so you don't litter your repository with binary build artifacts!**

## How to setup and run this application
In the backend run the compose.yaml
Then start the microservices in order (ConfigService,DiscoveryService,GateWayService) after these u can start Review,Post and commentService in a random order
Frontend can be started using docker run -d -p 80:80 javafullstack-app
:heavy_check_mark:_(COMMENT) Add setup instructions and provide some direction to run the whole  application: frontend to backend._
