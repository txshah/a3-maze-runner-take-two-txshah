package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFS implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();

    public static Position currentPos;
    public static Position goalPos; 
    public static Position startPos; 
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
        return path(maze);
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

    public Path path(Maze maze) {
        Path path = new Path();
        startPos = maze.getStart();   
        Direction currentDir = Direction.RIGHT; 

        Stack<Position> stack= new Stack<>();  
        
        Position current = goalPos; 

        while (current != startPos){//or until null? 
            stack.push(current); //push goal node, or any current node into stack 
            current = parentTracker.get(current); //update current to be parent of current node, so at start, parent of goal node
            //eventually will get to start node
        }
        //stack.push(current);//current should be startPos right now 

        //loop through explored and check stack items with explored nodes 
        while (!stack.isEmpty()){
            for (Node node:explored){
                if(stack.pop().equals(node.getPosition())){
                    if(node.getDirection(). equals(currentDir)){
                        path.addStep('F');
                    }else if (node.getDirection.equals(currentDir.turnRight())){
                        path.addStep('R');
                        path.addStep('F');
                        currentDir = node.getDirection(); 
                    }else if (node.getDirection.equals(currentDir.turnLeft())){
                        path.addStep('L');
                        path.addStep('F');
                        currentDir = node.getDirection(); 
                    }else if (node.getDirection.equals(currentDir.turnRight().turnRight())){
                        //technically should never happen since the BFS will not lead us to a dead end 
                        path.addStep('R');
                        path.addStep('R');
                        currentDir = node.getDirection(); 
                    }
                    break;
                }
            }
        }
        return path; 
    }
}

            //loop through explored nodes
            //direction and direction old 
            //add to direction
            //check if old directiom matches new 
            //if not check turn 

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
