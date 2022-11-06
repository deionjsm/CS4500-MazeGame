# **Memorandum**
TO:           Professor Ben Lerner

FROM:     David Mitre, Deion Smith

DATE:       September 29th, 2022

SUBJECT: First Three Sprints

For the first sprint we will establish a design pattern, how the components fit in with this pattern, and some simple functionality. Our initial plan to accomplish this is by retrofitting our existing homework so that they fit our design pattern and better suit the components. Once we have a basic outline, we will then begin actually implementing the components. For this sprint we will focus on the data definitions (a sufficient model), and some basic functionality of the referee interface because it interacts with all other components, which makes sense as referees generally control real life sports as well. We would like to, at the very least, have this interface done in logical terms.
      
For the second sprint, after implementing referee so that we may set up a board and have simple game functionality, we will work on the player-referee interface and try to make sure that this interface is able to work with other players outside of our own. This interface will allow for the communication between the player and referee: how moves will be made by the player and the current state of the board after each move received from the referee.

For sprint 3, we will finally implement a player that is able to play through our game by connecting through the player-referee interface. This player will be able to identify the current state of the board and make moves accordingly. 

This plan for the first three sprints is very generalized, and subject to change.
