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

    public static Queue<Node> queue = new LinkedList<Node>();
    public static ArrayList<Node> explored = new ArrayList<Node>(); 
   //public static Queue<Direction> direction = new LinkedList<Direction>();

    public static Map<Position, Position> parentTracker = new HashMap<>(); //dictionary tracking new nodes and the parent

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
        Direction dir = Direction.RIGHT; //starting direction, always right 
        
        //start point storage 
        explored.add(new Node(currentPos, dir));
        queue.offer(new Node(currentPos, dir)); 
        parentTracker.put(currentPos, null);
        //direction.offer(Direction.RIGHT);
        
        while (!(queue.isEmpty())) {
            Node currentNode = queue.poll();
            dir = currentNode.getDirection();
            currentPos = currentNode.getPosition();

            if (currentPos.equals(goalPos)) {//exit start node and goal node same 
                return true; 
            }

            ArrayList<Node> nodes = around(maze); //list of nodes around current 

            for (Node node:nodes){//loop through all nodes around 
                if(!explored.contains(node)){//if not already explored 
                    queue.offer(node);//add to queue 
                    explored.add(node);//tracks in explored 
                    parentTracker.put(node.getPosition(),currentPos); //tracks new node and parent

                    if (node.getPosition() == goalPos){//checks if goal position reached with new addition 
                        return true;
                    }
                }
            }
        }
        return true; 
    }

    public static ArrayList<Node> around(Maze maze){
        //check nodes left and right and if they are not walls, add to queue 
        ArrayList<Node> nodes = new ArrayList<>();

        if(!maze.isWall(currentPos.move(dir))){
            nodes.add(new Node(currentPos.move(dir), dir)); 
        }
        if (!maze.isWall(currentPos.move(dir.turnLeft()))){
            nodes.add(new Node(currentPos.move(dir), dir.turnLeft())); 
        }

        if (!maze.isWall(currentPos.move(dir.turnRight()))){
            nodes.add(new Node(currentPos.move(dir), dir.turnRight())); 
        }

        return nodes; 
    }

    public Path path() {
        Path path = new Path();
        return path; 
    }
}

        //go through parent tracker tree from goalnode position to start node and get order of nodes (from start to end)
        //from this list loop through the explored nodes from the beggining, set a local direction variable 
        //intia;l direction always right 
        //if the direction is same as previous/local direction variable add F to the string
        //if different add LF or RF (based on the direction change - if it's going right and then it goes up that means LF)
        //return path 
        
 //create a state class to store direction, position and output
    //so explored and queue will be position, direction
    //parentTracker would be position new, position parent, output/path (to get from parent to new) 

    //or just change this for explored and queue - like add path for them
    //then for output

    //OUTPUT: (maybe - like F, L R --> Like to reach from current not to this node do FFR)
    //so all the nodes will states be updated like that in the queues
    //for the final path go through the tree and collect all the paths, then flip the string to get the right order 
