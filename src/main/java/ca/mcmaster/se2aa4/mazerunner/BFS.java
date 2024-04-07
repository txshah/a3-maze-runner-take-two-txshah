package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFS implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();

    public static Position currentPos;
    public static Position goalPos; 
    public static Direction dir;

    public static Queue<Position> queue = new LinkedList<Position>();
    public static ArrayList<Position> explored = new ArrayList<>(); 

    public static Map<Position, Position> parentTracker = new HashMap<>(); 

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

        explored.add(currentPos);
        queue.offer(currentPos); 
        
        while (!(queue.isEmpty())) {
            currentPos = queue.poll();

            if (currentPos.equals(goalPos)) {
                return true; 
            }
            add(maze); //adds nodes to queue

            for (Position node:queue){
                if(!explored.contains(node)){
                    explored.add(node);//tracks all explored nodes 

                    parentTracker.put(node,currentPos); //tracks new node and parent

                }
            }



        }
        return true; 
    }

    public static void add(Maze maze){
        //check nodes left and right and if they are not walls, add to queue 
        
        if(!maze.isWall(currentPos.move(dir))){
            queue.offer(currentPos.move(dir)); 
        }

        if (!maze.isWall(currentPos.move(dir.turnLeft()))){
            queue.offer(currentPos.move(dir)); 
        }

        if (!maze.isWall(currentPos.move(dir.turnRight()))){
            queue.offer(currentPos.move(dir)); 
        }

    }

    public Path path() {
        Path path = new Path();

        return path; 
    }


}
