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
    public static Queue<Direction> direction = new LinkedList<Direction>();

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
        currentPos = maze.getStart();      
        goalPos = maze.getEnd();
        
        //start direction - always facing right 
        
        //start point storage 
        explored.add(currentPos);
        queue.offer(currentPos); 
        parentTracker.put(currentPos, null);
        direction.offer(Direction.RIGHT);
        
        while (!(queue.isEmpty())) {
            currentPos = queue.poll();
            dir = direction.poll();

            if (currentPos.equals(goalPos)) {
                return true; 
            }
            //create list of three nodes right left and front of current
            //if never searched and no wall before add to queue, if searched do not add to queue
            ArrayList<Position> nodes = around(maze); //list of nodes around current 

            for (Position node:nodes){
                if(!explored.contains(node)){
                    queue.offer(node);
                    explored.add(node);//tracks all explored nodes 
                    parentTracker.put(node,currentPos); //tracks new node and parent

                }
            }
        }
        return true; 
    }

    public static ArrayList<Position> around(Maze maze){
        //check nodes left and right and if they are not walls, add to queue 
        ArrayList<Position> nodes = new ArrayList<>();

        if(!maze.isWall(currentPos.move(dir))){
            nodes.add(currentPos.move(dir)); 
        }
        if (!maze.isWall(currentPos.move(dir.turnLeft()))){
            nodes.add(currentPos.move(dir)); 
        }

        if (!maze.isWall(currentPos.move(dir.turnRight()))){
            nodes.add(currentPos.move(dir)); 
        }

        return nodes; 
    }

    public Path path() {
        Path path = new Path();

        return path; 
    }

    //create a state class to store direction, position and output
    //OUTPUT: (maybe - like F, L R --> Like to reach from current not to this node do FFR)
    //so all the nodes will states be updated like that in the queues
    //for the final path go through the tree and collect all the paths, then flip the string to get the right order 

}
