# Checkers Game
<img align="right" width="86" height="86" src="../main/images/CheckersGameIcon.png" >

Game of CheckersTable revised for university project by Luca Rubino and Renato Esposito 
<br>(<a href="https://www.uniparthenope.it/ugov/degreecourse/43357"><i><b>PROGRAMMING III AND PROGRAMMING LABORATORY III</i></b></a> - course of the <b><i><a href="https://www.uniparthenope.it/">Parthenope University of Naples</a></i></strong>).
<h3>Game Rules: </h3>

Each player has N pieces where N is a number that varies according to size of the playing field that is chosen by the player before starting one match.
The following pieces are present on the playing field:
<ul>
 <li><b>Pawn:</b> classic piece of the game of checkers that represents that she can move into diagonal one square at a time and only forward. When a Pawn reaches one of the boxes in the last row and is promoted by becoming a Checkers. There Pawn can eat the opponent's pieces that are forward on the square diagonal next to theirs and that they have the next free square.</li>
 
<li><b>Checkers:</b> classic piece of the checkers game that represents the upgrade of the pawn.
It can move in multiple directions one step at a time.
He can eat any other piece.</li>
          
<li><b>Wizard (Revised Mode only):</b> Can move in multiple directions at one step time. If he eats a piece he resurrects a Checkers instead of the eaten piece. If it is eaten by an opponent's piece, the opponent's piece becomes a Checkers. A wizard cannot become a Checkers and cannot eat another Wizard.                         
</li>
</ul> 

When a player eats an opponent's piece he scores points based on the piece eaten, in particular:
<ul>
<li><b>Pawn:</b> 1 point.</li>
<li><b>Wizard:</b> 3 points.</li></li>
<li><b>Checkers:</b> 5 points.</li>
</ul>
The game ends when one of the following situations occurs:
<ol>
A player runs out of all his pieces.
A player leaves the game.
The timer expires and in this case the winner is determined on the basis of the score higher. If the players have the same score then the game is considered "draw".
</ol>

A piece must eat when it can eat to be able to move.<br>
Player1 always makes the first move.
<h3><p align="center">OUTPUT EXAMPLE (Green: Player1, Red: Player2)</p></h3>
<p align="center"><img  src="../main/images/example.png" style="display: block; margin-left: auto; margin-right: auto; width: 50%;"></p>
