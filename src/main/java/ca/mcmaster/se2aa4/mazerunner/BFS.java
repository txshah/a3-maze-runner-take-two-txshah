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

    public static Queue<Node> queue = new LinkedList<Node>();
    public static ArrayList<Node> explored = new ArrayList<Node>(); 
   //public static Queue<Direction> direction = new LinkedList<Direction>();

    public static Map<Position, Position> parentTracker = new HashMap<>(); //dictionary tracking new nodes and the parent

    @Override
    public Path solve(Maze maze) {
        search(maze);
        return path(maze);
    }

    public static boolean search(Maze maze) {
        //current positions and goal position 
        currentPos = maze.getStart();      
        goalPos = maze.getEnd();
        
        //start point storage 
        explored.add(new Node(currentPos, dir));
        queue.offer(new Node(currentPos, dir)); 
        parentTracker.put(currentPos, null);
        
        while (!(queue.isEmpty())) {
            Node currentNode = queue.poll();
            dir = currentNode.getDirection();
            currentPos = currentNode.getPosition();

            ArrayList<Node> nodes = around(maze); //list of nodes around current 

            for (Node node:nodes){//loop through all nodes around 
                if(!explored.contains(node)){//if not already explored 

                    queue.offer(node);//add to queue 
                    explored.add(node);//tracks in explored 
                    parentTracker.put(node.getPosition(),currentPos); //tracks new node and parent
                    
                    if (node.getPosition().equals(goalPos)){//checks if goal position reached with new addition 
                        return true;//exits loop if at goal position 
                    }
                }
            }
        }
        return true; 
    }

    public static ArrayList<Node> around(Maze maze){
        //check nodes front left and right and if they are not walls, add to list 
        ArrayList<Node> nodes = new ArrayList<>();

        if(!maze.isWall(currentPos.move(dir))){
            nodes.add(new Node(currentPos.move(dir), dir)); 
        }
        if (!maze.isWall(currentPos.move(dir.turnLeft()))){
            nodes.add(new Node(currentPos.move(dir.turnLeft()), dir.turnLeft()));      
        }
        if (!maze.isWall(currentPos.move(dir.turnRight()))){
            nodes.add(new Node(currentPos.move(dir.turnRight()), dir.turnRight())); 
        }

        return nodes; 
    }

    public Path path(Maze maze) {
        //set up path and other variables 
        Path path = new Path();
        startPos = maze.getStart();   
        Direction currentDir = Direction.RIGHT; 

        Stack<Position> stack= new Stack<>();  
        Position current = goalPos; 

        while (current != startPos){//or until null? 
            stack.push(current); //push goal node, or any current node into stack 
            current = parentTracker.get(current); //update current to be parent of current node, so at start, parent of goal node
            
            if(current == startPos){//break to make sure loop does not run forever 
                break;
            }
        }

        //loop through explored and check stack items with explored nodes 
        while (!(stack.isEmpty())){
            for (Node node:explored){
                Position bestPath = stack.peek();
                Position exploredNodes = node.getPosition(); 

                if(bestPath.equals(exploredNodes)){
                    Direction next = node.getDirection(); 

                    if(next.equals(currentDir)){
                        path.addStep('F');

                    }else if (next.equals(currentDir.turnRight())){
                        path.addStep('R');
                        path.addStep('F');
                        currentDir = next;

                    }else if (next.equals(currentDir.turnLeft())){
                        path.addStep('L');
                        path.addStep('F');
                        currentDir = next; 

                    }else if (next.equals(currentDir.turnRight().turnRight())){
                        //technically should never happen since the BFS will not lead us to a dead end 
                        path.addStep('R');
                        path.addStep('R');
                        currentDir = next; 
                    }

                    stack.pop();
                    break;
                }
            }
        }
        return path; 
    }
}

