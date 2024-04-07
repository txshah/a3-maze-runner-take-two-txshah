package ca.mcmaster.se2aa4.mazerunner;

public class Node {

    private Position position;
    private Direction direction;
    //private Path path; 
    public Object getDirection;

    public Node(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        //this.path = path;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
    
    /*public Path getPath() {//path from previous node to new node
        return path;
    }*/
}
