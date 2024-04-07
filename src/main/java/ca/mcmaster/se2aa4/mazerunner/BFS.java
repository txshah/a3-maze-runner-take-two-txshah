package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFS implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Maze maze) {
        //Path path = new Path();
        //anytime iswall is false we can add to graph 

        search(maze);
        return path();
    }

    public static boolean search(Maze maze) {
        //current positions and goal position 
        Position currentPos = maze.getStart();      
        Position goalPos = maze.getEnd();
        
        //start direction - always facing right 
        Direction dir = Direction.RIGHT;

        Queue<Position> queue = new LinkedList<Position>();
        ArrayList<Position> explored = new ArrayList<>(); 

        queue.offer(currentPos); 
        
        while (!(queue.isEmpty())) {
            currentPos = queue.poll();

            if (currentPos.equals(goalPos)) {
                return true; 
            }
            
        }
        return true; 
    }

    public Path path() {
        Path path = new Path();

        return path; 
    }


}
