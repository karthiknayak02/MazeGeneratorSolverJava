import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;


public class Maze extends JFrame implements ActionListener{

    // layout objects: Views of the board and the label area
    private mazeGenerator mazeBoard;
    private mazeSolver mazeSolve;
    private controlBoard ctr;
    private JPanel boardView, labelView, controlView;
    public static JLabel timerLabel, visitedLabel, statusLabel;
    public static Timer gameTimer;
    private int gameTime = 0;

    public Maze(String labelAccess){ }

    public Maze() {
        // Call the base class constructor
        super("Maze");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Allocate the interface elements
        timerLabel      = new JLabel("Time: 0");
        visitedLabel    = new JLabel("Visited: 0%");
        statusLabel     = new JLabel("Status: Grid generated");

        labelView = new JPanel();
        labelView.setLayout(new GridLayout(1, 3, 0, 0));
        labelView.add(timerLabel);
        labelView.add(visitedLabel);
        labelView.add(statusLabel);

        controlView = new JPanel();
        ctr = new controlBoard(controlView, this);

        boardView = new JPanel();

        ActionListener seconds = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameTime += 1;
                timerLabel.setText("Time: " + gameTime);
            }
        };

        gameTimer = new Timer(1000, seconds);

        mazeBoard = new mazeGenerator();
        mazeSolve = new mazeSolver();

        Container c = getContentPane();
        c.add(boardView, BorderLayout.CENTER);
        c.add(controlView, BorderLayout.EAST);
        c.add(labelView, BorderLayout.SOUTH);

        newMaze();
    }



    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Generate") && !mazeSolve.solverRunning && !mazeBoard.generatorRunning) {

            visitedLabel.setText("Visited: 0%");
            newMaze();

            if (ctr.animation.isSelected()) {
                gameTime = 0;
                gameTimer.restart();
                mazeBoard.generateAnimated();
            }
            else mazeBoard.generate();

        }

        if (e.getActionCommand().equals("Stop")) {

            if (mazeBoard.generatorRunning) {
                mazeBoard.mazeTimer.stop();
                mazeBoard.generatorRunning = false;
                statusLabel.setText("Status: Stopped!");
                gameTimer.stop();
            }

            if (mazeSolve.solverRunning) {
                mazeSolve.solverTimer.stop();
                mazeSolve.solverRunning = false;
                statusLabel.setText("Status: Stopped!");
                gameTimer.stop();
            }
        }

        if (e.getActionCommand().equals("Solve") && !mazeSolve.solverRunning && !mazeBoard.generatorRunning) {

            mazeSolve = new mazeSolver(mazeBoard.cells);

            if (ctr.animation.isSelected()) {
                gameTime = 0;
                gameTimer.restart();
                mazeSolve.solveAnimated();
            }
            else mazeSolve.solve();
            repaint();
            setVisible(true);
        }
    }

    private void newMaze()
    {
        boardView.removeAll();

        Cell.rows = 0;
        Cell.cols = 0;

        mazeBoard = new mazeGenerator(ctr.x, ctr.y);
        mazeBoard.fillBoardView(boardView);
        boardView.setLayout(new GridLayout(ctr.x, ctr.y));


        setSize( getDim(ctr.y) + 190,  getDim(ctr.x) + 16);
        repaint();
        setVisible(true);

    }

    private int getDim(int a){  //approximation to make the maze look appropriate
        if (ctr.x - ctr.y >= 7 || ctr.y - ctr.x >= 7){
            int t = (int) Math.sqrt(ctr.x*ctr.y);

            if (a >= 10 && a < 20) return a*500/(t);
            else if (a >= 20 && a < 30) return a*500/(t);
            else if (a >= 30 && a < 40) return a*500/(t);
            else if (a >= 40 && a <= 50) return a*500/(t);
            return 0;
        }
        else {
            if (a >= 10 && a < 20) return 500;
            else if (a >= 20 && a < 30) return 600;
            else if (a >= 30 && a < 40) return 700;
            else if (a >= 40 && a <= 50) return 800;
            return 0;
        }
    }

    public static void main(String[] args) {
	    // write your code here
        Maze M = new Maze();
    }

}
