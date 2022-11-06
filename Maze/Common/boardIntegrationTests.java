import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class boardIntegrationTests {

    public static void main(String[] args) throws IOException {



        Gson json = new Gson(); //will read 1 json at a time



        InputStreamReader in = new InputStreamReader(System.in);
        JsonReader inread = new JsonReader(in);
        inread.setLenient(true);
        //if has none, will add empty list to gson - printing "[]"
        //begin read

        board b = json.fromJson(inread, board.class);
        Coordinate c = json.fromJson(inread, Coordinate.class);
        List<List <IMazePosition>> positions = new ArrayList<>();

        for (int row = 0; row < 7; row++) {
           ArrayList <IMazePosition> temp = new ArrayList<>();
           for (int col1 = 0; col1 < 7; col1++) {


               IMazePosition tempPosition = new MazePositionSimple(row,col1,
                       getTileAcceptable(b.connectors.get(row).get(col1)));


               temp.add(tempPosition);
           }
           positions.add(temp);
        }

        Queue<ITile> std = new ArrayDeque<>();
        std.add(new MazeTileSimple(ConnectorShape.Tee));
        IBoardModel testableBoard = new Board(7,7,positions, std);



        List<Coordinate> outputCoords= new ArrayList<>();
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {

                if (testableBoard.isReachableFrom(c.row,c.column,row,col)) {
                    Coordinate tempCoord = new Coordinate(col,row);//STATIC TODO LOOK OUT
                    outputCoords.add(tempCoord);

                }
            }

        }


        String output = json.toJson(outputCoords);

        System.out.println(output);

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




    private class Cell {
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

    private class Gem {
        String s;
    }

    private class board {
        ArrayList<ArrayList <String>> connectors;
        ArrayList<ArrayList <ArrayList <String>>> treasures;

        public board(ArrayList<ArrayList <String>> connectors, ArrayList<ArrayList <ArrayList <String>>> treasures) {
            this.connectors = connectors;
            this.treasures = treasures;
        }

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

}
