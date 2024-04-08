package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.Queue;

class PathTest {
    @Test
    void getCanonicalForm() {
        Path path = new Path("FLFFFFFRFFRFFLFFFFFFRFFFFLF");

        assertEquals("F L FFFFF R FF R FF L FFFFFF R FFFF L F", path.getCanonicalForm());
    }

    @Test
    void getFactorizedForm() {
        Path path = new Path("FLFFFFFRFFRFFLFFFFFFRFFFFLF");

        assertEquals("F L 5F R 2F R 2F L 6F R 4F L F", path.getFactorizedForm());
    }

    @Test
    void expandedPath() {
        Path path = new Path("4F 3R L");

        assertEquals("FFFF RRR L", path.getCanonicalForm());
    }


    @Test
    void expandedPath2() {
        Path path = new Path("10F 11R");

        assertEquals("FFFFFFFFFF RRRRRRRRRRR", path.getCanonicalForm());
    }
}

class NewTest {

    @Test
    void testBFS() throws Exception {
        //run BFS for straight line 
        String filePath = "./examples/straight.maz.txt";
        Maze maze = new Maze(filePath);

        //set up solver and path 
        MazeSolver solver = new BFS();
        Path path = solver.solve(maze); 

        //make sure path expanded 
        String canonicalPath = path.getCanonicalForm();

        //checks if path is same as expected 
        assertEquals(canonicalPath,"FFFF");
    }

    @Test
    void testNode() {
        //test node creation 
        Position position = new Position(0, 0);
        Direction direction = Direction.RIGHT;

        Node node = new Node(position, direction);

        //checks if variables I made and variables stored and pulled from node are equal 

        //test direction 
        assertEquals(direction, node.getDirection());

        //test position 
        assertEquals(position, node.getPosition());
        
    }

}
    