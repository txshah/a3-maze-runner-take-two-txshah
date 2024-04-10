package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            logger.info(filePath);
            
            long start = System.nanoTime();//getting time to create maze
            Maze maze = new Maze(filePath);
            long end = System.nanoTime();//getting time to create maze

            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else if ((cmd.getOptionValue("method") != null) && (cmd.getOptionValue("baseline") == null)){
                //if user does -method (and does not do -baseline) they can choose either bfs, righthand or tremaux
                String method = cmd.getOptionValue("method");

                Path path = solveMaze(method, maze);
                System.out.println(path.getFactorizedForm());

            }else if ((cmd.getOptionValue("baseline") != null) && (cmd.getOptionValue("method") != null)){

                String baselineCmd = cmd.getOptionValue("baseline");//get baseline value
                String methodCmd = cmd.getOptionValue("method");//get method value 

                long mazeTime = end - start; //call time taken to get maze implementation 

                //output maze creation time 
                System.out.println("Time taken to go load the maze: " + String.format("%.2f", mazeTime/1_000_000.0) + "ms"); 

                double path1 = calculation(baselineCmd, maze); //choosen baseline
                double path2 = calculation(methodCmd, maze); //compare method 

                //print speedup 
                System.out.println("Speedup = baseline/method = " + path1+"/"+path2 + "= " + String.format("%.2f", path1/path2));
                
                //difference b/w baseline and method chosen (how much faster is the method)
                System.out.println(methodCmd +" is " + String.format("%.2f", path1/path2) + " times faster");
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = null;
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
                break;
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
                break;
            }//added BFS implementation 
            case "bfs" -> {
                logger.debug("BFS algorithm chosen.");
                solver = new BFS();//create new solver of BFS chosen 
                break;
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");
        return solver.solve(maze);
    }

    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline", true, "Specify which method to use as baseline"));


        return options;
    }

    private static int calculation(String method, Maze maze) throws Exception{
        //get number of moves based on chose method (baseline or method)
        int commands = 0; 

        long start = System.nanoTime();//start time 
        Path path = solveMaze(method, maze);
        String output = path.getCanonicalForm();
        logger.info(output);
        long end = System.nanoTime();

        long mazeTime = end - start; 
        //output time taken per method 
        System.out.println("Time taken to explore the maze with " + method +" is:" + String.format("%.2f", mazeTime/1_000_000.0) + "ms"); 

        for(int i = 0; i<output.length(); i++){
            if (output.charAt(i) != ' '){
                commands+=1;
            }
        }
        
        //return number of moves in path to get to end 
        return commands; 
    }
}
