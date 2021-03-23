<h3>User Story</h3>
<b>As a</b> client of the server on port 8080 <br>
<b>I want</b> to play connectFive on a server with another client on port 8080 <br>
<b>So that</b> I can have some fun! <br>

<h3>AC1: PlayerOne Joins Game</h3>
<b>Given</b> I am PlayerOne  <br>
<b>When</b> I enter run the jar file in the cmd line  <br>
java -jar client-0.0.1-SNAPSHOT-jar-with-dependencies.jar <br>
<b>Then</b> I will be connected to the server <br>

<h3>AC2: Enter a Username and Piece </h3>
<b>Given</b> I am PlayerOne  <br>
<b>When</b> successfully join the server <br>
<b>Then</b> I will be asked to submit my userID and color Pieces being X or O<br>

<img src="https://i.imgur.com/tmlak84.png" alt ="AC2">
![image](https://user-images.githubusercontent.com/79998654/112230753-4f7de600-8c2d-11eb-8a41-e099ab078e59.png)
