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
        //Path path = new Path();
        //anytime iswall is false we can add to graph 
        logger.info("in BFS"); 

        search(maze);
        return path(maze);
    }

    public static boolean search(Maze maze) {
        //current positions and goal position 
        logger.info("in search"); 
        currentPos = maze.getStart();      
        goalPos = maze.getEnd();
        
        logger.info(currentPos); 
        logger.info(goalPos); 
        logger.info(dir); 

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
                logger.info("found goal 1");
                return true; 
            }

            ArrayList<Node> nodes = around(maze); //list of nodes around current 

            for (Node node:nodes){//loop through all nodes around 
                if(!explored.contains(node)){//if not already explored 
                    logger.info("adding nodes");
                    queue.offer(node);//add to queue 
                    explored.add(node);//tracks in explored 
                    parentTracker.put(node.getPosition(),currentPos); //tracks new node and parent
                    
                    logger.info(node.getPosition());
                    logger.info(currentPos);
                    
                    if (node.getPosition().equals(goalPos)){//checks if goal position reached with new addition 
                        logger.info("found goal");
                        return true;
                    }
                }
            }
        }
        logger.info("queue empty");
        return true; 
    }

    public static ArrayList<Node> around(Maze maze){
        //check nodes left and right and if they are not walls, add to queue 
        logger.info("checking surrondings"); 
        ArrayList<Node> nodes = new ArrayList<>();

        if(!maze.isWall(currentPos.move(dir))){
            logger.info("forward"); 
            nodes.add(new Node(currentPos.move(dir), dir)); 
            
            logger.info("node check 1");
            logger.info(currentPos.move(dir)); 
            logger.info(dir); 
        }
        if (!maze.isWall(currentPos.move(dir.turnLeft()))){
            logger.info("left"); 
            nodes.add(new Node(currentPos.move(dir.turnLeft()), dir.turnLeft())); 
            
            logger.info("node check 2");
            logger.info(currentPos.move(dir.turnLeft())); 
            logger.info(dir.turnLeft()); 
        }

        if (!maze.isWall(currentPos.move(dir.turnRight()))){
            logger.info("right"); 
            nodes.add(new Node(currentPos.move(dir.turnRight()), dir.turnRight())); 

            logger.info("node check 3");
            logger.info(currentPos.move(dir.turnRight())); 
            logger.info(dir.turnRight()); 
        }

        return nodes; 
    }

    public Path path(Maze maze) {
        logger.info("getting path");
        Path path = new Path();
        startPos = maze.getStart();   
        Direction currentDir = Direction.RIGHT; 

        Stack<Position> stack= new Stack<>();  
        Position current = goalPos; 

        while (current != startPos){//or until null? 
            logger.info("creating stack");
            stack.push(current); //push goal node, or any current node into stack 
            logger.info(current);
            current = parentTracker.get(current); //update current to be parent of current node, so at start, parent of goal node
            if(current == startPos){
                logger.info("break");
                break;
            }
            logger.info(current);
            //eventually will get to start node
        }
        //stack.push(current);//current should be startPos right now 
        logger.info("stack made");
        //loop through explored and check stack items with explored nodes 
        while (!(stack.isEmpty())){
            logger.info("in while");
            for (Node node:explored){
                logger.info("checking nodes LOLZ");
                Position bestPath = stack.peek();
                Position exploredNodes = node.getPosition(); 

                logger.info(bestPath);
                logger.info(exploredNodes);

                if(bestPath.equals(exploredNodes)){
                    logger.info("match");
                    Direction next = node.getDirection(); 
                    logger.info(next);

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
        logger.info("path finished");
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
