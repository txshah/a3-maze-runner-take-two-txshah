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
    public static Direction dir = Direction.RIGHT; //starting direction, always right 

    public static Queue<Node> queue = new LinkedList<Node>(); //maintains queue of nodes to check until goal node is found 
    public static ArrayList<Node> explored = new ArrayList<Node>();  //holds all explored nodes to prevent repeats 

    public static Map<Position, Position> parentTracker = new HashMap<>(); //dictionary tracking new nodes and the parent

    @Override
    public Path solve(Maze maze) {
        //path method - as required by interface 
        search(maze);//search to get to goal node 
        return path(maze);//path to return output 
    }

    private static boolean search(Maze maze) {
        //current positions and goal position 
        currentPos = maze.getStart();      
        goalPos = maze.getEnd();
        
        //start point storage 
        explored.add(new Node(currentPos, dir));//store start node as explored 
        queue.offer(new Node(currentPos, dir)); //store start node in queue 
        parentTracker.put(currentPos, null); //add start node with no parent 
        
        while (!(queue.isEmpty())) { //start BFS implementation, run loop as long a queue is not empty 
            Node currentNode = queue.poll();//remove first item from queue 
            dir = currentNode.getDirection(); //get direction from node 
            currentPos = currentNode.getPosition(); //get position from node 

            ArrayList<Node> nodes = around(maze); //list of nodes around current 

            for (Node node:nodes){//loop through all nodes around 
                if(!explored.contains(node)){//if not already explored 

                    queue.offer(node);//add to queue 
                    explored.add(node);//tracks in explored 
                    parentTracker.put(node.getPosition(),currentPos); //tracks new node and parent
                    
                    if (node.getPosition().equals(goalPos)){ //checks if goal position reached with new addition 
                        return true; //exits loop if at goal position 
                    }
                }
            }
        }
        return false; 
    }

    private static ArrayList<Node> around(Maze maze){
        //check nodes front left and right and if they are not walls, add to list 
        ArrayList<Node> nodes = new ArrayList<>();

        if(!maze.isWall(currentPos.move(dir))){//check forward 
            nodes.add(new Node(currentPos.move(dir), dir)); 
        }
        if (!maze.isWall(currentPos.move(dir.turnRight()))){ //check right 
            nodes.add(new Node(currentPos.move(dir.turnRight()), dir.turnRight())); 
        }
        if (!maze.isWall(currentPos.move(dir.turnLeft()))){ //check left 
            nodes.add(new Node(currentPos.move(dir.turnLeft()), dir.turnLeft()));      
        }
        return nodes; 
    }

    private Path path(Maze maze) {
        //set up path and other variables 
        Path path = new Path();
        startPos = maze.getStart();   
        Direction currentDir = Direction.RIGHT; //set current direction 

        //stack ensures that even though we are backtracking from goalpoint we still read the values from the start
        Stack<Position> stack= new Stack<>(); //set up stack to hold nodes to traverse (start from end and go to start)
        Position current = goalPos; 

        while (!current.equals(startPos)){//make sure current node is not start (since working backwards)
            stack.push(current); //push current node into stack (starts with goal/final node)
            current = parentTracker.get(current); //update current to be parent of current node (at start, parent of goal node)
            if(current == startPos){
                break;
            }
        }

        while (!stack.isEmpty()){//run until entire stack empty 
            for (Node node:explored){ //loop through explored and check stack items with explored nodes 
                Position bestPath = stack.peek();//store first item in stack (after start node - since already there don't need to travel there)
                Position exploredNodes = node.getPosition(); //store position of node

                if(bestPath.equals(exploredNodes)){//check if node and stack value match 
                    Direction next = node.getDirection(); //get direction and based on direction add to path 

                    if(next.equals(currentDir)){//if same as current direction go forward 
                        path.addStep('F');

                    }else if (next.equals(currentDir.turnRight())){
                        //if same as current's right go right and forward 
                        path.addStep('R');
                        path.addStep('F');
                        currentDir = next;//update current 

                    }else if (next.equals(currentDir.turnLeft())){
                        //if same as current's left, go left and forward 
                        path.addStep('L');
                        path.addStep('F');
                        currentDir = next; //update current 

                    }
                    stack.pop();//since node and stack value match, pop the value out 
                    break;//break loop 
                }
            }
        }
        return path; //return final output 
    }
    
}

