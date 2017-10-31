import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class mazeSolver {
    public static boolean solverRunning;
    public Timer solverTimer;

    private Cell[][] cells = new Cell[0][0];
    private Cell curr, next;
    private int visitedCountSolver = 0, totalCells, rows = 10, cols = 10, speed;
    private boolean unvisitedOpenNeighboursExist = true;


    private controlBoard c = new controlBoard();
    private Maze m = new Maze("just for label access");

    public mazeSolver() {}

    public mazeSolver(Cell[][] maze) {
        cells = maze;
        rows = cells.length;
        cols = cells[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j].visited = false;
                cells[i][j].fillGrey();
            }
        }
    }

    public void solve(){

        //initialize stack
        Stack<Cell> stSolver = new Stack<>();
        cells[rows - 1][cols - 1].fillGreen();
        curr = cells[0][0];
        curr.visited = true;
        visitedCountSolver++;
        totalCells = rows * cols;

        while (curr != cells[rows - 1][cols - 1] && visitedCountSolver < totalCells) {

            //Choose the first right Open Unvisited Neighbours as 'next'
            chooseNext(curr);

            //If the current cell has any neighbours which have not been visited
            if (unvisitedOpenNeighboursExist) {

                //Push the current cell to the stack
                stSolver.add(curr);

                //Fill the chosen cell
                curr.fillPurple();

                //Make the chosen cell the current cell and mark it as visited
                curr = next;

                curr.visited = true;
                visitedCountSolver++;
            }
            else if (stSolver.size() > 0) {
                curr.fillPink();
                curr = stSolver.pop();
            }
            else {
                m.statusLabel.setText("Status: Maze not solved");
                break;
            }

        }
        m.visitedLabel.setText("Visited: " + visitedCountSolver*100/totalCells + "%");
        if (curr == cells[rows - 1][cols - 1]) m.statusLabel.setText("Status: Maze solved");
    }

    public void solveAnimated(){

        m.statusLabel.setText("Status: Solving maze..");
        solverRunning = true;

        //initialize stack
        Stack<Cell> stSolver = new Stack<>();
        curr = cells[0][0];
        curr.visited = true;
        visitedCountSolver++;
        totalCells = rows * cols;

        ActionListener seconds = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                speed = getSpeed(c.speedSlider.getValue());
                solverTimer.setDelay(speed);
                m.visitedLabel.setText("Visited: " + visitedCountSolver*100/totalCells + "%");

                if (curr == cells[rows - 1][cols - 1]) {
                    solverTimer.stop();
                    m.gameTimer.stop();
                    solverRunning = false;
                    m.statusLabel.setText("Status: Maze solved");
                }

                else if (visitedCountSolver < totalCells) {

                    //Choose the first right Open Unvisited Neighbours as 'next'
                    chooseNext(curr);

                    //If the current cell has any neighbours which have not been visited
                    if (unvisitedOpenNeighboursExist) {

                        //Push the current cell to the stack
                        stSolver.add(curr);

                        //Fill the chosen cell
                        curr.fillPurple();

                        //Make the chosen cell the current cell and mark it as visited
                        curr = next;
                        curr.fillGreen();

                        curr.visited = true;
                        visitedCountSolver++;
                    }
                    else if (stSolver.size() > 0) {
                        curr.fillPink();
                        curr = stSolver.pop();
                        curr.fillGreen();
                    }
                    else {
                        solverTimer.stop();
                        m.gameTimer.stop();
                        solverRunning = false;
                        m.statusLabel.setText("Status: Maze not solved");
                    }
                }
            }
        };

        speed = getSpeed(c.speedSlider.getValue());
        solverTimer = new Timer(speed, seconds);
        solverTimer.start();

    }

    public void chooseNext(Cell current){

        unvisitedOpenNeighboursExist = true;
        if (current.sides[3] == 0 && !cells[current.x][current.y + 1].visited) {
                next = cells[current.x][current.y + 1];
                return;
            }

        if (current.sides[2] == 0 && !cells[current.x + 1][current.y].visited) {
                next = cells[current.x + 1][current.y];
                return;
            }

        if (current.sides[1] == 0 && !cells[current.x][current.y-1].visited){
                next = cells[current.x][current.y-1];
                return;
            }


        if (current.sides[0] == 0 && !cells[current.x - 1][current.y].visited) {
                next = cells[current.x - 1][current.y];
                return;
            }

        unvisitedOpenNeighboursExist = false;
        return;
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
