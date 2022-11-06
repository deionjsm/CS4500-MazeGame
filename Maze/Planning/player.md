# **Memorandum**

TO: Professor Ben Lerner

FROM: David Mitre, Deion Smith

DATE: October 13th, 2022

SUBJECT: Player Data and Interface

**Data Needed by Player to Complete Actions…**

- …a data representation of the board, filtered, such that only its tiles and the spare tile are visible, all of which the player can only VIEW (field of Player)
- …their own and other players home tiles and current positions, represented by an (non-referential) IMazePosition in our current implementation, and a List of positions for the other players' homes.
- …only their own goal tile, represented by an IMazePosition (protected field of Player)
- …to know whether or not it is their own turn, represented by a boolean that, when true, will allow the Player to request a move, with every Players boolean initialized to false to start the game (protected field of Player) 
- …which rows and columns are valid to shift, represented by an array (private field of Player)
- …age of Player represented as an integer (protected field of Player)
 
**Fields and Related Methods:**
IFilteredBoard playersBoard;
->JSON? playersBoard.view()
IMaze Position homePosition
List<IMazePosition> enemyHomes
IMaze Position currentPosition
List<IMazePosition> enemyPositions
IMaze Position goalPosition
boolean myTurn
int[] ValidIndicies
int age
 
**Interface of the Player, which extends the future Player-Referee interface…**

- …a method that allows the Player to request 'a move' to the Referee.
- A move will consume all three parts of a turn, a shift, a rotate, and a placement of a spare: which as a whole need a Direction, a row/column number depending on the Direction, and a IMazePosition, which will change the boolean field to false if move is found to be valid by referee, returns true otherwise (validity checked by referee) 

**Fields and Related Methods:**
boolean requestMove(Turn turn);
-> Where Turn is a datatype as described above.