# MazeGeneratorSolverJava
Java stand-alone application that generates and solves mazes

# Features
The program runs in two primary modes: maze generation and computer-automated maze solving.

![Grid](/Images/1.png)

  ## 1. Maze Generation
The program generates a maze using a randomized depth-first search algorithm. The user can visualize the maze construction based on user-selected parameters (resolution in `rows` and `columns`, `speed`).  <br/>
The result – the randomly generated maze

![Generating](/Images/3.png)

  ## 2. Automated Depth-First Search Solver
The program solves the maze using a depth-first search algorithm and a stack. The solution can be visualized as the solver runs. The speed of the visualization of the solver’s path through the maze must be controllable by the user. <br/>
The cells that are on the active path in one color, backtracked cells (visited but determined to be dead ends) in a
second color, and unvisited cells in the default color. 

![Solving](/Images/4.png)

  ## 3. The Graphical User Interface
The GUI has the following functionality for the user: <br/>
`Generate`      : Generate a new maze based on current parameters <br/>
`Solve`         : Start the automated solver <br/>
`Stop`          : Stop the automated generator/solver <br/>
`Speed`         : Change speed of the visualization of maze generation and solver <br/>
`Rows/columns`  : Set maze resolution (10x10 to 50x50; rows/cols independent) <br/>

   ### Other features
`Time`            : Display a running time it takes to generate/solve at a given speed <br/>
`Percent visited` : Displays a running percentage of number of cells visited <br/>
`Status`          : Display a running status of the grid <br/>

![Generated & Solved](/Images/2.png)

# How to compile 
Download and open directory in terminal <br/>
`javac Maze.java	controlBoard.java	mazeGenerator.java	mazeSolver.javaCell.java`
-  <br/> OR <br/>
`javac *.java`

# How to run
`java Maze`







