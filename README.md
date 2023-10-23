How to deploy Splendor Server& Client + LobbyService

Prerequisistes : 
    Install maven
    Install Docker Desktop app
    Have this repo cloned and up to date

Power-up the lobby service : 
    1. cd into the docker/BoardGamePlatform folder
    2. Make sure the Docker app is running
    3. execute command : docker-compose up

Run the server : 
    1. cd into the server folder
    2. execute command : mvn clean package spring-boot:run

Install the card pictures (Client) : 
    1. Download Assets.zip from here : https://drive.google.com/drive/folders/1SzUSTTGr-QDUaywgOf02euxN3p1NurJY
    2. Unzip it
    3. Put the Assets folder in client/src/main/ressources

Run the client :
    1. cd into the client folder
    2. execture command : mvn javafx:run

Enjoy! :)
