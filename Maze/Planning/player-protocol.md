# **Memorandum**
TO: Professor Ben Lerner
FROM: David Mitre, Deion Smith
DATE: October 20th, 2022
SUBJECT: Protocol of Player and Player Referee Interaction
# Initializing the Game
The Player will need to share information about itself in order for the Referee to be able to fully initialize the game. This information is the fields contained in the Player class which will be its…
- …current position represented as an IMazePosition
- …home position represented as an IMazePosition
- …its avatar represented as a Color
- …its age represented by an integer
```Referee Calls: void SendIntroInformation();```
# Referee Shares GameState with Player During Their Turn
At the beginning of a Players turn the Referee will share a viewable only game state with the active Player which has…
- …the game board itself with accompanying tiles and treasures and the spare as an IBoardModel
- …the home and current positions of all Players as IMazePositions
- …the goal position of the active Player as IMazePositions
- … the last action taken because it is not allowed to undo the previous move
```Referee Calls: void SendActivePlayerInformation();```
# Player Requests a Move During Their Turn
The only thing a player needs to do during its turn is a way to communicate its desired move to the referee the move will include…
- …a direction in which to slide a row or column represented by a Direction enum
- …an integer that represents a valid row or column to slide
- …an integer that represents the number of degrees we would like to rotate the spare tile
- …a position represented by a IMazePosition that will move the player down an acceptable path
```player Calls: boolean requestMove(IMove move); //blocks until response```
# Referee Actions taken After Move Request
After taking in what we assume to be a valid move from the Player the Referee will then perform the following actions…
- …rotate the spare using a method from the gamestate 
```boolean rotateSpareTile(int degreesCW);```
- …slide the indicated row or column in the specified direction and then directly inserting the rotated spare tile and update every players position depending on if they were affected by the slide, using a series of methods
```shiftAndInsert(int indexOfRowOrCol, BoardOpt rORc, Direction direction);```
- …shift a Player to the specified position updating the Players current position
```boolean moveActiveTo(int rowNumber, int colNumber)```
- …check if the Players new current position is its goal tile
```boolean isActiveOnGoal()```
- ...return a response with success, and a shutdown if the game ends 
```void respondActivePlayer()```
- ..and share gamestate with next player..