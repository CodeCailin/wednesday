 The server application holds the state and business logic of the game.
 
 The server:
1. Receives the movements from the players
2. Decides whether a player has won
3. Decides if the game is over 
4. Saves the state of the game
5. Who’s turn it is, will be returned to the client upon request. 

The server uses HTTP for communication to clients.
The server, upon start, waits for the two players to connect.
 If one of the players disconnects, the game is over.

Instructions on how to compile the code:
1. cd connectfivegame\server
2. mvn -DskipTests clean package
3. cd /target (server\target\server-0.0.1-SNAPSHOT.jar)
4. java -jar server-0.0.1-SNAPSHOT.jar


Note : 
The server is running when the following message is displayed on the console : 
2021-03-20 15:30:01.317  INFO 11164 --- [           main] com.server.MainApplication               : Started MainApplication in 4.692 seconds (JVM running for 5.415)