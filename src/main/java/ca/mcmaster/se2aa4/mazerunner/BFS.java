package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFS implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Maze maze) {
        Path path = new Path();

        Position currentPos = maze.getStart();
        Direction dir = Direction.RIGHT;

        //anytime iswall is false we can add to graph 

        return path;
    }

    public int graph(Maze maze) {
        return 0; 
    }


}
