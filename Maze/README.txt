PURPOSE:
The purpose of this project is to create the Maze Dot Com game per specifications of the two Co-CEO's.

Common/ includes common pieces needed for the basic building blocks of the games structure.
This includes board.java which includes our implementation of shifting rows, reachability, and placing a spare tile into the board.

Common also includes state.java, which encapsulates our referee gamestate interpretation.

Strategies such as Euclidean and Riemann have been implemented in Common.

-- Within Common, our components include an interface and implementation for a Tile, a Position, a Maze Board, a referee gamestate, and a referee's concept of a player..
    --We also have certain enums such as Directions, which should be self explanatory.

Players/ Contains player information such as the player protocol interface and a simple implementation

Referee/ contains referee specific information such as the referee protocol and a game implementation.

Bin/ is the binary folder, which allows testing and running of the .java files, and so is necessary.

test/ is where the junit test jar and hamcrest core jar (which allows the junit tests to run) are. They are also necessary for our testing. This folder also includes the GSON jar, which our test harness and future testing to be done using it's methods and functions.

out/ is a recently added folder which contains class files and binaries, we plan to deprecate testing through the binary and start testing through this folder since it allows use of packages, etc.

HOW TO RUN TESTS FOR EACH MILESTONE:

UNIT TESTS:
All Milestones: 
-To run the tests for all milestones, run ./xtest in this directory with no arguments.
This should run all unit tests and produce feedback on the amount of tests passed/failed.

Milestone 2: board.PP
- To run the tests for this milestone, run "./xtest 1" from the command line. This should run all tests for the board and provide which tests passed/failed.

Milestone 3: state.PP
- To run the tests for this milestone, run "./xtest 2"  from the command line. This should run all tests for the state and provide which tests passed/failed.

Milestone 4: strategies
- To run the tests for this milestone, run "./xtest 3"  from the command line. This should run all tests for strategies and provide which tests passed/failed.

Milestone 5: strategies
- To run the tests for this milestone, run "./xtest 4"  from the command line. This should run all tests for protocols of the player and referee and provide which tests passed/failed.


TEST HARNESSES:
Board Harness:
Navigate into the 3/ folder outside this project directory and run ./xboard.
To utilise it in a manageable way, pipe it some input from a file, such as the examples given:
./xboard < ./Tests/0-in.json
./xboard < ./Tests/1-in.json
./xboard < ./Tests/2-in.json

State Harness:
Navigate into the 4/ folder outside this project directory and run ./xstate.
To utilise it in a manageable way, pipe it some input from a file, such as the examples given:
./xstate < ./Tests/0-in.json
./xstate < ./Tests/1-in.json
./xstate < ./Tests/2-in.json
./xstate < ./Tests/3-in.json
./xstate < ./Tests/4-in.json

Strategy Harness:
Navigate into the 5/ folder outside this project directory and run ./xchoice.
To utilise it in a manageable way, pipe it some input from a file, such as the examples given:
./xchoice < ./Tests/0-in.json
./xchoice < ./Tests/1-in.json
./xchoice < ./Tests/2-in.json
./xchoice < ./Tests/3-in.json
./xchoice < ./Tests/4-in.json

Protocol Harnesses:
Navigate into the 6/ folder outside this project directory and run ./xgames.
To utilise it in a manageable way, pipe it some input from a file, such as the examples given:
./xgames < ./Tests/0-in.json
./xgames < ./Tests/1-in.json
./xgames < ./Tests/2-in.json

You may also want to use the GUI xgames : ./xgames-with-observer
To utilise it in a manageable way, pipe it some input from a file, such as the examples given:
./xgames-with-observer < ./Tests/0-in.json
./xgames-with-observer < ./Tests/1-in.json
./xgames-with-observer < ./Tests/2-in.json

Press the bottom next button to proceed, the top to save current to a file : Tested on .txt files


