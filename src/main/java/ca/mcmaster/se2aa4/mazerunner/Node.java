package ca.mcmaster.se2aa4.mazerunner;

public class Node {
//node class so I can store both position and current direction 
    private Position position;
    private Direction direction;
    //private Path path; 

    public Node(Position position, Direction direction) {//create node 
        this.position = position;
        this.direction = direction;
        //this.path = path;
    }

    public Position getPosition() {//return position of node 
        return position;
    }

    public Direction getDirection() {//return direction of node 
        return direction;
    }
    
    /*public Path getPath() {//path from previous node to new node
        return path;
    }*/
}
