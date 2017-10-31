import javax.swing.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.*;
import javax.swing.Timer;

public class mazeGenerator {
    public static boolean generatorRunning;
    public Timer mazeTimer;
    public Cell[][] cells = new Cell[0][0];

    private Cell curr, next;
    private int visitedCount = 0, totalCells, rows = 10, cols = 10, wallSide, speed;

    private controlBoard c = new controlBoard();
    private Maze m = new Maze("just for label access");

    public mazeGenerator() {}

    public mazeGenerator(int x, int y) {

        rows = x;
        cols = y;
        cells = new Cell[rows][cols];
        totalCells = rows*cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = new Cell(i, j);
                cells[i][j] = c;
            }
        }
    }

    public void generate(){
        //initialize stack
        Stack<Cell> st = new Stack<>();

        // Make the initial cell the current cell and mark current cell as visited
        curr = cells[0][0];
        curr.visited = true;
        visitedCount++;

        //While there are unvisited cells
        while (visitedCount < totalCells){

            // get a list of unvisited neighbours
            ArrayList<Cell> unvistedN = getNeighbours(curr);

            //If the current cell has any neighbours which have not been visited
            if (unvistedN.size() > 0){

                //Choose randomly one of the unvisited neighbours
                Collections.shuffle(unvistedN);
                next = unvistedN.get(0);

                //Push the current cell to the stack
                st.add(curr);

                //Remove the wall between the current cell and the chosen cell
                wallSide = getWallSide(curr, next);;
                curr.removeWall(wallSide);

                wallSide = nextWallSide(wallSide);
                next.removeWall(wallSide);

                //Make the chosen cell the current cell and mark it as visited
                curr.fillGrey();
                curr = next;
                curr.fillOrange();

                curr.visited = true;
                visitedCount++;
            }
            else if (st.size()>0){
                //Pop a cell from the stack and make it the current cell
                curr.fillGrey();
                curr = st.pop();
                curr.fillOrange();
            }
        }

        curr.fillGrey();
        m.statusLabel.setText("Status: Maze generated");
        m.timerLabel.setText("Time: 0");

        ActionListener seconds = new ActionListener() {
            public void actionPerformed(ActionEvent e) {}};
        mazeTimer = new Timer(0, seconds);
        mazeTimer.start();
        mazeTimer.stop();
    }

    public void generateAnimated(){
        m.statusLabel.setText("Status: Generating Maze..");
        generatorRunning = true;

        //initialize stack
        Stack<Cell> st = new Stack<>();

        // Make the initial cell the current cell and mark current cell as visited
        curr = cells[0][0];
        curr.visited = true;
        visitedCount++;

        ActionListener seconds = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                speed = getSpeed(c.speedSlider.getValue());
                mazeTimer.setDelay(speed);

                if (visitedCount < totalCells){
                    // get a list of unvisited neighbours
                    ArrayList<Cell> unvistedN = getNeighbours(curr);

                    //If the current cell has any neighbours which have not been visited
                    if (unvistedN.size() > 0){

                        //Choose randomly one of the unvisited neighbours
                        Collections.shuffle(unvistedN);
                        next = unvistedN.get(0);

                        //Push the current cell to the stack
                        st.add(curr);

                        //Remove the wall between the current cell and the chosen cell
                        wallSide = getWallSide(curr, next);;
                        curr.removeWall(wallSide);

                        wallSide = nextWallSide(wallSide);
                        next.removeWall(wallSide);

                        //Make the chosen cell the current cell and mark it as visited
                        curr.fillGrey();
                        curr = next;
                        curr.fillOrange();

                        curr.visited = true;
                        visitedCount++;
                    }
                    else if (st.size()>0){
                        //Pop a cell from the stack and make it the current cell
                        curr.fillGrey();
                        curr = st.pop();
                        curr.fillOrange();
                    }
                }

                else {
                    curr.fillGrey();
                    mazeTimer.stop();
                    m.gameTimer.stop();
                    generatorRunning = false;
                    m.statusLabel.setText("Status: Maze generated");
                }
            }
        };

        speed = getSpeed(c.speedSlider.getValue());
        mazeTimer = new Timer(speed, seconds);
        mazeTimer.start();
    }

    public void fillBoardView(JPanel view)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                view.add(cells[i][j]);
            }
        }
    }

    private ArrayList<Cell> getNeighbours(Cell curr){
        ArrayList<Cell> neighbours = new ArrayList<>();

        int x = curr.x;
        int y = curr.y;

        if (y + 1 >= 0 && y + 1 < cols && cells[x][y+1].visited == false) neighbours.add(cells[x][y + 1]);

        if (x + 1 >= 0 && x + 1 < rows && cells[x+1][y].visited == false) neighbours.add(cells[x + 1][y]);

        if (y - 1 >= 0 && cells[x][y-1].visited == false) neighbours.add(cells[x][y - 1]);

        if (x - 1 >= 0 && cells[x-1][y].visited == false) neighbours.add(cells[x - 1][y]);

        return neighbours;
    }


    private int getWallSide(Cell a, Cell b){
        int diffX = a.x - b.x;
        int diffY = a.y - b.y;

        /*  Diff table
              0  -1     right  3
             -1   0     down   2
              0   1     left   1
              1   0     up     0
        */

        if (diffX ==  0 && diffY == -1) return 3;
        if (diffX == -1 && diffY ==  0) return 2;
        if (diffX ==  0 && diffY ==  1) return 1;
        if (diffX ==  1 && diffY ==  0) return 0;

        return -1;
    }

    private int nextWallSide(int w){
        if (w==0) return 2;
        if (w==1) return 3;
        if (w==2) return 0;
        if (w==3) return 1;
        return -1;
    }


    private int getSpeed(int slider){
        double t = rows*cols;

        if (t >= 100 && t < 400)            t = 10;
        else if (t >= 400 && t < 900)       t = 7.5;
        else if (t >= 900 && t < 1600)      t = 2.5;
        else if (t >= 1600 && t <= 2500)    t = 1;

        return (int) t*slider;
    }
}


