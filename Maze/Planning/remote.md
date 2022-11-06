# **Memorandum**
TO: Professor Ben Lerner
FROM: David Mitre, Deion Smith
DATE: November 3rd, 2022
SUBJECT: Remote Interaction
# **New RemoteProxy Class…**
- We will create a RemoteProxy class which will extend our existing Player class that we completed in milestone 5. This new class holds the functionality required for a client to interact with our Referee. The reason for this class is to ‘hide’ the original Player class and control access to it.
- We will create a TCP connection using a similar method to the one we used in assignment E with some added functionality. Including placing a time limit on players so they do not take too long to answer and disrupt the game. 
- We will create an interface that allows for cohesive use of the player by prompting the player for information when necessary as well as sending the player all necessary information.
# **Protocol…**
# Gathering Players:
- A player will connect to the referee via TCP connection and be prompted for a name. The player will type in their name which will be sent to our remote proxy class. The RemoteProxy will gather the players only ensuring they have valid names. Then they will be sent to the Referee as a list IPlayerProtocols.
# Launching a Game
- After receiving the list of IPlayerProtocols, the Referee will then request a board of a specified size from each player, they will then propose a board and the Referee will choose randomly. The chosen board will then be used to create a gamestate for the referee, and a filtered state for the players. From here we can use our runGame function and each player will be set up and take turns accordingly.
# Report Result:
- At the end of a game, we will display the reason the game ended as well as each player’s individual result of whether they won or lost. 
