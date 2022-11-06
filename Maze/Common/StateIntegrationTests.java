import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class StateIntegrationTests {

    public static void main(String[] args) throws IOException {



        Gson json = new Gson(); //will read 1 json at a time



        InputStreamReader in = new InputStreamReader(System.in);
        JsonReader inread = new JsonReader(in);
        inread.setLenient(true);
        //if has none, will add empty list to gson - printing "[]"
        //begin read


        // read in json values
        TestState s = json.fromJson(inread, TestState.class);
        int idx = json.fromJson(inread, Integer.class);
        Directions d = json.fromJson(inread, Directions.class);
        int deg = json.fromJson(inread, Integer.class);


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
        IBoardModel testableBoard = new Board(7, 7, positions, std);


        // create testable state
        List<IRefPlayer> players = new ArrayList<IRefPlayer>();
        for (int i = 0; i < s.plmt.length; i++) {
            IRefPlayer testablePlayer = new SimpleRefPlayer(s.plmt[i].current.row, s.plmt[i].current.column, 0,0,
                    s.plmt[i].home.row, s.plmt[i].home.column, 7, 7);
            players.add(testablePlayer);
        }

        IRefGameState testableState = new State(testableBoard, 7, 7,
                players, 0);

        testableState.rotateSpareTile(deg);
        testableState.shiftAndInsert(idx, getBoardOpt(d) ,getDirectionAcceptable(d));






        List<Coordinate> outputCoords = new ArrayList<>();
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {

                if (testableState.isReachableByActivePlayer(row, col)) {
                    Coordinate tempCoord = new Coordinate(col,row);//STATIC TODO LOOK OUT
                    outputCoords.add(tempCoord);
                }
            }
        }


        String output = json.toJson(outputCoords);

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
            case "│":
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
