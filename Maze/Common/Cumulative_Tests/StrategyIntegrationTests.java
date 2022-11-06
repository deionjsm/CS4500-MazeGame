package Cumulative_Tests;

import Maze_Board.Board;
import Maze_Positions.*;
import Maze_Tiles.*;
import Enum_Constants.*;
import Enum_Constants.Direction;
import Maze_Board.*;
import Players.*;
import Players.*;
import GameState.*;
import Filtered_Info.IFilteredInfo;
import Filtered_Info.SimpleFilteredInfo;
import Player_Strategies.EuclidStrategy;
import Player_Move.IMove;
import Player_Strategies.IStrategy;
import Player_Strategies.RiemannStrategy;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class StrategyIntegrationTests {

    public static void main(String[] args) throws IOException {



        Gson json = new Gson(); //will read 1 json at a time



        InputStreamReader in = new InputStreamReader(System.in);
        JsonReader inread = new JsonReader(in);
        inread.setLenient(true);
        //if has none, will add empty list to gson - printing "[]"
        //begin read


        // read in json values
        String strat = json.fromJson(inread, String.class);
        IStrategy strategy;
        TestState s = json.fromJson(inread, TestState.class);
        Coordinate goal = json.fromJson(inread, Coordinate.class);

        if (strat.equals("Euclid")) {
            strategy = new EuclidStrategy();
        }
        else {
            strategy = new RiemannStrategy();
        }


        List<List <IMazePosition>> positions = new ArrayList<>();
        // initialize list of maze positions with a tile
        for (int row = 0; row < 7; row++) {
            ArrayList <IMazePosition> temp = new ArrayList<>();
            for (int col1 = 0; col1 < 7; col1++) {

                IMazePosition tempPosition = new MazePositionSimple(row,col1,
                        getTileAcceptable(s.board.connectors.get(row).get(col1)));

                temp.add(tempPosition);
            }

            positions.add(temp);
        }


        // create testable board
        Queue<ITile> std = new ArrayDeque<>();
        std.add(getTileAcceptable(s.spare.tilekey));
        //System.out.println(std.peek().getRotationCW());
        //System.out.println(std.peek().getConnector());
        IBoardModel testableBoard = new Board(7, 7, positions, std, true);


        // create testable state
        List<IRefPlayer> players = new ArrayList<IRefPlayer>();
        for (int i = 0; i < s.plmt.length; i++) {
            IRefPlayer testablePlayer = new SimpleRefPlayer(s.plmt[i].current.row, s.plmt[i].current.column, 0,0,
                    s.plmt[i].home.row, s.plmt[i].home.column, 7, 7);
            players.add(testablePlayer);
        }

        IRefGameState testableState = new State(testableBoard, 7, 7,
                players, 0);
        //TODO DO NOT PASS IN ACTUAL BOARD HERE
        IFilteredInfo info = new SimpleFilteredInfo(testableBoard,
                std.peek(),
                new MazePositionSimple(s.plmt[0].current.row, s.plmt[0].current.column),
                new MazePositionSimple(goal.row, goal.column),
                new MazePositionSimple(s.plmt[0].home.row, s.plmt[0].home.column));

        IMove pickedMove = strategy.getDecision(info);
        if (pickedMove.getSkip()) {
            System.out.println("PASS"); //DONE
            return;
        }
        //now else:

        //testableState.rotateSpareTile();
        //testableState.shiftAndInsert(idx, getBoardOpt(d) ,getDirectionAcceptable(d));


        ArrayList<Object> outputPre = new ArrayList<>();
        outputPre.add(pickedMove.getShiftIndex());
        Direction dir = pickedMove.getShiftDirection();

        switch (dir) {
            case Left:
                outputPre.add("LEFT");
                break;
            case Right:
                outputPre.add("RIGHT");
                break;
            case Up:
                outputPre.add("UP");
                break;
            case Down:
                outputPre.add("DOWN");
                break;
            default:
                throw new IllegalArgumentException("BAD DIRECTION GIVEN");


        }


        //COUNTER CLOCKWISE ZZZZ
        int rotCW = pickedMove.getRotation();

        int rotCCW = 0;
        switch (rotCW) {
            case 0:
                break;
            case 90:
                if (getTileAcceptable(s.spare.tilekey).getConnector() == ConnectorShape.UpLine) {
                    rotCCW = 90;
                    break;
                }
                rotCCW = 270;
                break;
            case 180:
                rotCCW = 180;
                break;
            case 270:
                rotCCW = 90;
                break;
            default:
                break;

        }

        outputPre.add(rotCCW);
        outputPre.add(new Coordinate(pickedMove.getMoveAvatarPos().getYCoordinate(),
                pickedMove.getMoveAvatarPos().getXCoordinate()));





        String output = json.toJson(outputPre);

        System.out.println(output);

    }



    public static BoardOpt getBoardOpt(Directions dir) {
        if(dir.equals(Directions.UP) || dir.equals(Directions.DOWN)) {
            return BoardOpt.Col;
        } else {
            return BoardOpt.Row;
        }
    }

    public static Direction getDirectionAcceptable(Directions dir) {
        if(dir.equals(Directions.UP)) {
            return Direction.Up;
        } else if(dir.equals(Directions.DOWN)) {
            return Direction.Down;
        } else if(dir.equals(Directions.LEFT)) {
            return Direction.Left;
        } else {
            return Direction.Right;
        }
    }


    public static ITile getTileAcceptable(String connector) {
        ITile temp;
        switch (connector) {
            case "│": // |
                return new MazeTileSimple(ConnectorShape.UpLine);

            case "─":
                temp = new MazeTileSimple(ConnectorShape.UpLine);
                temp.rotateConnector(90);
                return temp;
            case "┐":
                temp = new MazeTileSimple(ConnectorShape.UpRight);
                temp.rotateConnector(180);
                return temp;
            case "└":
                temp = new MazeTileSimple(ConnectorShape.UpRight);

                return temp;
            case "┌":
                temp = new MazeTileSimple(ConnectorShape.UpRight);
                temp.rotateConnector(90);
                return temp;


            case "┘":
                temp = new MazeTileSimple(ConnectorShape.UpRight);
                temp.rotateConnector(270);
                return temp;

            case "┬":
                temp = new MazeTileSimple(ConnectorShape.Tee);
                return temp;


            case "├":
                temp = new MazeTileSimple(ConnectorShape.Tee);
                temp.rotateConnector(270);
                return temp;

            case "┴":
                temp = new MazeTileSimple(ConnectorShape.Tee);
                temp.rotateConnector(180);
                return temp;


            case "┤":
                temp = new MazeTileSimple(ConnectorShape.Tee);
                temp.rotateConnector(90);
                return temp;

            case "┼":
                temp = new MazeTileSimple(ConnectorShape.Cross);

                return temp;
            default:
                throw new IllegalStateException(connector);

        }
    }




    private static class Cell {
        String c;

        public Cell (String connector) {
            this.c = connector;
        }
        //│, ─, ┐, └, ┌, ┘, ┬, ├, ┴, ┤, ┼
        public ITile getTileAcceptable(String connector) {
            ITile temp;
            switch (connector) {
                case "|":
                    return new MazeTileSimple(ConnectorShape.UpLine);

                case "─":
                    temp = new MazeTileSimple(ConnectorShape.UpLine);
                    temp.rotateConnector(90);
                    return temp;
                case "┐":
                    temp = new MazeTileSimple(ConnectorShape.UpRight);
                    temp.rotateConnector(180);
                    return temp;
                case "└":
                    temp = new MazeTileSimple(ConnectorShape.UpRight);

                    return temp;
                case "┌":
                    temp = new MazeTileSimple(ConnectorShape.UpRight);
                    temp.rotateConnector(90);
                    return temp;


                case "┘":
                    temp = new MazeTileSimple(ConnectorShape.UpRight);
                    temp.rotateConnector(270);
                    return temp;

                case "┬":
                    temp = new MazeTileSimple(ConnectorShape.Tee);
                    return temp;


                case "├":
                    temp = new MazeTileSimple(ConnectorShape.Tee);
                    temp.rotateConnector(270);
                    return temp;

                case "┴":
                    temp = new MazeTileSimple(ConnectorShape.Tee);
                    temp.rotateConnector(180);
                    return temp;


                case "┤":
                    temp = new MazeTileSimple(ConnectorShape.Tee);
                    temp.rotateConnector(90);
                    return temp;

                case "┼":
                    temp = new MazeTileSimple(ConnectorShape.Cross);

                    return temp;
                default:
                    throw new IllegalStateException();

            }
        }


    }

    private static class Gem {
        String s;
    }

    private static class board {
        ArrayList<ArrayList <String>> connectors;
        ArrayList<ArrayList <ArrayList <String>>> treasures;

        public board(ArrayList<ArrayList <String>> connectors, ArrayList<ArrayList <ArrayList <String>>> treasures) {
            this.connectors = connectors;
            this.treasures = treasures;
        }


    }

    private enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static class Coordinate {

        @SerializedName(value = "column#")
        int column;
        @SerializedName(value = "row#")
        int row;


        public Coordinate (int col, int row) {
            this.row= row;
            this.column = col;
        }

    }

    private static class Tile {
        String tilekey;
        Gem oneimage;
        Gem twoimage;

        public Tile(String tk, Gem img1, Gem img2) {
            this.tilekey = tk;
            this.oneimage = img1;
            this.twoimage = img2;
        }
    }


    private static class Player {
        Coordinate current;
        Coordinate home;
        String color;

        public Player(Coordinate curr, Coordinate home, String col) {
            this.current = curr;
            this.home = home;
            this.color = col;
        }
    }

    private static class Action {
        int index;
        Direction direction;

        Object[] act = new Object[2];

        public Action(int idx, Direction dir) {
            this.index = idx;
            this.direction = dir;
            act[0] = index;
            act[1] = direction;
        }
    }

    private class TestState {
        board board;
        Tile spare;
       Player[] plmt;
        String[] last;

        public TestState(board brd, Tile spr, Player[] pl, String[] last) {
            if (pl.length == 0) {
                throw new RuntimeException("List of players can not be empty");
            }
            this.board = brd;
            this.spare = spr;
            this.plmt = pl;
            this.last = last;
        }
    }

    public static class Degrees {
        Integer[] validDegrees = {0, 90, 180, 270};
        int deg = 0;

        public Degrees(int d) {
            for(Integer i : validDegrees) {
                if (d == i) {
                    this.deg = d;
                }
            }
        }
    }

    public static class Index {

        Integer[] validIndicies = {2, 4, 6};

        int index;
        public Index(int idx) {
            for(Integer i : validIndicies) {
                if (idx == i) {
                    this.index = idx;
                }
            }
        }
    }

}
