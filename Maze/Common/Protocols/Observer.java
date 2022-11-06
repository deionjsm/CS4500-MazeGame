package Protocols;

import Cumulative_Tests.ProtocolIntegrationTests;
import GameState.IRefGameState;
import GameState.State;
import Maze_Board.IBoardModel;
import Maze_Tiles.ITile;
import Players.IRefPlayer;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * This class observes a game, that generates JSON representations of the game as it progresses.
 * It has the capability to represent the state with gui and json.
 * todo CURRENTLY does nothing with the info of game over, can add a final state to queue that draws game over?
 */
public class Observer implements IObserver, ActionListener {


    //NULL STATE REPRESENTS END OF GAME
    Queue<IRefGameState> states = new ArrayDeque<>();

    //if game is over, no more expected gamestates
    private boolean isGameOver = false;

    //creates an observer with an initial state
    public Observer(IRefGameState init) {
        this.states.add(init);
    }

    //BEWARE UNINIT OBSERVER - NULL STATES
    public Observer() {

    }

    //launches gui
    public void startGUI() throws IOException {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        GridLayout gl = new GridLayout(2,1);
        BorderLayout bl = new BorderLayout();
        frame.setLayout(bl);

        JButton button = new JButton("SAVE STATE AS JSON IN FILE");

        button.addActionListener(this);

        JButton nextButton = new JButton("PROCEED TO NEXT STATE IF EXISTS");

        Drawer drawer = new Drawer();

        panel.add(button);


        frame.add(drawer, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.PAGE_START);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //changes current state by popping, must ALSO refresh gui
                nextState();
                drawer.repaint();

            }});
        frame.add(nextButton, BorderLayout.PAGE_END);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI FOR MAZE OBSERVER");
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600,600);
        frame.setVisible(true);
    }


    private class Drawer extends Canvas {
        public void paint(Graphics g) {
            IRefGameState currentState = states.peek();
            if (currentState == null || currentState.getBoardCopy() == null) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 50));
                g.drawString("GAME OVER", 100, 100);
                return;
            }
            IBoardModel currentBoard = currentState.getBoardCopy();
            g.setColor(Color.BLACK);
            //g.drawRect(0,0,50,50);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.setColor(Color.BLUE);

            //┘, ┐, └, ┌
            //For loop thru - print all
            for (int h = 0; h < currentBoard.getHeight(); h++) {

                for (int l = 0; l < currentBoard.getWidth(); l++) {

                    g.setColor(Color.BLACK);
                    g.drawRect((50 * l), (50 * h), 50, 50);
                    g.setColor(Color.BLUE);
                    g.drawString(currentBoard.getConnectorAt(h, l), (50 * l)+20, (50 * h)+25);

                }
            }
            List<IRefPlayer> plys = currentState.getPlayers();
            for (IRefPlayer player : plys) {
                g.setColor(player.getPlayerColor());
                g.drawOval(player.getCurrentColumn()*50 + 5 + plys.indexOf(player),
                        player.getCurrentRow()*50 + 5 + plys.indexOf(player), 9,9);
            }

        }
    }

    /**
     * This provides the JSON OUTPUT capabilities, to a file.
     */
    public void saveCurrentStateInFile(File f) throws IOException {
        //take current state and turn it to json
        FileWriter fw = new FileWriter(f);
        fw.write(currentStateAsJSON());
        fw.flush();
        fw.close();

    }

    private String currentStateAsJSON() {
        if (this.states == null || this.states.peek() == null) {
            return "GAME OVER";
        }
        Gson gson = new Gson();
        String outString = "INCOMPLETE";
        IRefGameState currentState = this.states.peek();
        IBoardModel currentBoard = currentState.getBoardCopy();

        ArrayList<ArrayList <String>> connectors = new ArrayList<>();
        ArrayList<ArrayList < ArrayList <String>>> treasures = new ArrayList<>();
        //pairs of strings per tile
        for (int row = 0; row < currentBoard.getHeight(); row++) {
            ArrayList<String> tempConn = new ArrayList<>();
            ArrayList<ArrayList<String >> tempGems = new ArrayList<>();
            for (int col = 0; col < currentBoard.getWidth(); col++) {
                //need to get board elements
                tempConn.add(currentBoard.getConnectorAt(row, col));
                ArrayList <String> pair = new ArrayList<>();
                //TODO ADD GET GEMS FOR BOARD
                pair.add("TEMPGEM");
                pair.add("TEMPGEM");
                tempGems.add(pair);
                //todo do the treasures too
            }
            connectors.add(tempConn);
            treasures.add(tempGems);
        }

        board b = new board(connectors, treasures);
        Tile t = new Tile(currentState.getSpareCopy().getString(), "TEMPGEM", "TEMPGEM");
        //for each ref player, -> make a rl
        RefereePlayer[] pl = new RefereePlayer[1];
        String[] l = null;
        TestState ts = new TestState(b, t, pl, l);
        outString = gson.toJson(ts, TestState.class);

        return outString;
    }



    /**
     * Progresses state if there is at least 1 other state in queue.
     */
    public void nextState() {
        if (states.size() >= 2){ //HAS NEXT
            IRefGameState temp = states.poll();

            //NEW PEEK IS OUR CURRENT STATE
        }

        if (isGameOver) {
            states.poll(); //REMOVES EVEN
        }
        //NOW NEXT STATE IS READY -- SOME SORT OF UPDATE ON GUI
    }




    @Override
    public void receiveState(IRefGameState s) {
        //adds state to queue of states
        if (isGameOver) { //do not accept anymore states!!
            return;
        }
        states.add(s);
    }

    @Override
    public void gameOver() {
        this.isGameOver = true;
        //NO LONGER ACCEPT STATES
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();
            //System.out.println(file);
            try {
                saveCurrentStateInFile(file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //This is where a real application would open the file.
        }
    }


    //PRIVATE CLASSES USED FOR JSON

    private class TestState {
        board board;
        Tile spare;
        RefereePlayer[] plmt;
        String[] last;



        public TestState(board brd, Tile spr, RefereePlayer[] pl, String[] last) {
            if (pl.length == 0) {
                throw new RuntimeException("List of players can not be empty");
            }
            this.board = brd;
            this.spare = spr;
            this.plmt = pl;
            this.last = last;
        }
    }

    private static class Tile {
        String tilekey;
        String oneimage;
        String twoimage;

        public Tile(String tk, String img1, String img2) {
            this.tilekey = tk;
            this.oneimage = img1;
            this.twoimage = img2;
        }
    }

    private static class board {
        ArrayList<ArrayList<String>> connectors;
        ArrayList<ArrayList <ArrayList <String>>> treasures;

        public board(ArrayList<ArrayList <String>> connectors, ArrayList<ArrayList <ArrayList <String>>> treasures) {
            this.connectors = connectors;
            this.treasures = treasures;
        }


    }

    private static class RefereePlayer {
        Coordinate current;
        Coordinate home;

        @SerializedName(value = "goto")
        Coordinate goTo;
        String color;

        public RefereePlayer(Coordinate c, Coordinate h, Coordinate g, String col) {
            this.current = c;
            this.home = h;
            this.goTo = g;
            this.color = col;
        }
    }



}
