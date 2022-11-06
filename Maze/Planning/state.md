# **Memorandum**
TO:           Professor Ben Lerner

FROM:     David Mitre, Deion Smith

DATE:       October 5th, 2022

SUBJECT: Game State Wishlist

1. In order to represent the game state, the referee will require the following (not in any distinct order)…
2. The home and goal tiles of players, home represented as a public variable that anyone can view, and goal being a protected variable only being visible to the referee and the player it belongs too
3. Age of players, as a private variable that the referee will use in order to allow player to make moves in the correct order
4. General rules for moving, implemented as methods that validate a move 
5. Current state of the board, which will be used by the rules in order to validate that a move is valid
6. Keep track of whether or not a player has passed their goal tile yet with a boolean, allowing a player to then win by going back to their home state
7. Ability to update the board and display it for the players or communicate to players that their move is invalid
8. Receive players actions as some input, that is only viewable to the referee
9. Termination state, once the board reaches a state where a player wins the referee will safely end it and display as such

