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
<b>Then</b> I will be asked to submit my userID and color Pieces being X or O<br> <br>

<img src="https://i.imgur.com/tmlak84.png" alt ="AC2">

<br>

<h3>AC3: Connect to PlayerTwo</h3>
<b>Given</b> I am PlayerOne  <br>
<b>When</b> playerTwo successfully joins the server <br>
<b>Then</b> they will be asked to submit their userID and told their Piece is either X or O <br>
<b>And</b> I will be told it is my turn to chose a column from 1-9 <br>
<br>
<img src="https://i.imgur.com/adYJTeZ.png" alt ="AC3">
<br>

<h3>AC4: Play Game and Provide Winner</h3>
<b>Given</b> I am PlayerOne  <br>
<b>When</b> I take turns between PlayerOne to put in a piece between 1-9<br>
<b> And</b> there is five pieces in a consecutive row (horizontally, diagonally or vertically)<br>
<b>Then</b> the consolue will display the winner and their color (either X or O) <br>
<br>
<img src="https://i.imgur.com/KnpIeB0.png" alt ="AC4">


