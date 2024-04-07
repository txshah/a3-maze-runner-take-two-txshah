package ca.mcmaster.se2aa4.mazerunner;

public class Node {
//node class so I can store both position and current direction 
    private Position position;
    private Direction direction;
    //private Path path; 

    //create node 
    public Node(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        //this.path = path;
    }

    //return position of node 
    public Position getPosition() {
        return position;
    }

    //return direction of node 
    public Direction getDirection() { 
        return direction;
    }
    
    /*public Path getPath() {//path from previous node to new node
        return path;
    }*/
}
