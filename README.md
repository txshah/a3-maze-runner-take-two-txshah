# Assignment A3 - Maze Runner Take Two 

* **Student**: [TVESHA SHAH](shaht28@mcmaster.ca)
* **Program**: B. Eng. In Software Engineering
* **Course code**: SFWRENG 2AA4
* **Course Title**: Software Design I - Introduction to Software Development
* Term: *Level II - Winter 2024*

CREDITS TO ALEXANDRE LACHANCE FOR THE STARTER CODE<br />
ALL WORK BY STUDENT TO IMPLEMENT A GRAPH ALGORITHM EXPANDED ON INITIAL MAZE SOLVER<br />

Additional resources for graph implementation: 
- 2C03 Course slides - information on graph algos (Professor Jelle Hellings)
- General Understanding of BFS and it's practical implementation 
    - TheHappieCat (https://www.youtube.com/watch?v=WvR9voi0y2I&t=339s&ab_channel=TheHappieCat)
    - GeeksForGeeks (https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/)
- Node Classes and how to use (how to store multiple properties in a node): 
    - Codecademy (https://www.codecademy.com/learn/getting-started-with-data-structures-java/modules/nodes-java/cheatsheet)
    - JavatPoint (https://www.javatpoint.com/java-program-to-create-a-singly-linked-list-of-n-nodes-and-count-the-number-of-nodes)   

## Business Logic Specification

This program explores a maze, finding a path from an entry point to an exit one.

- The maze is stored in a text file, with `#` representing walls and `␣` (_empty space_) representing passages.
- You’ll find examples of such mazes in the [`examples`](./examples) directory.
    - You can also use the [Maze Generator](https://github.com/ace-lectures/maze-gen) to generate others.
- The Maze is surrounded by walls on its four borders, except for its entry/exit points.
    - Entry and exit points are always located on the East and West border.
    - The maze is not directed. As such, exit and entry can be interchanged.
- At the beginning of the exploration, we're located on the entry tile, facing the opposite side (e.g., if entering by
  the eastern entry, you're facing West).
- The program generates a sequence of instructions to reach the opposite exit (i.e., a "path"):
    - `F` means 'move forward' according to your current direction
    - `R` means 'turn right' (does not move, just change direction), and `L` means ‘turn left’.
- A canonical path contains only `F`, `R` and `L` symbols
- A factorized path squashes together similar instructions (i.e., `FFF` = `3F`, `LL` = `2L`).
- Spaces are ignored in the instruction sequence (only for readability: `FFLFF` = `FF L FF`)
- The program takes as input a maze and print the path on the standard output.
    - For this assignment, the path does not have to be the shortest one.
- The program can take a path as input and verify if it's a legit one.

## How to run this software?

To build the program, simply package it with Maven:

```
mosser@azrael A1-Template % mvn -q clean package 
```

### Initial version (starter code)

The starter code taken from: https://github.com/2AA4-W24/a1-solution 
credits to Alexandre Lachance and Sebastien Mosser

When called on a non-existing file or a correct file without -i . it prints an error message
```
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % java -jar target/mazerunner.jar ./examples/small.maz.txt 
[INFO ] Main ** Starting Maze Runner
MazeSolver failed.  Reason: Missing required option: i
[ERROR] Main MazeSolver failed.  Reason: Missing required option: i
[ERROR] Main PATH NOT COMPUTED
[INFO ] Main End of MazeRunner
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah %

```
```
[INFO ] Main ** Starting Maze Runner
[INFO ] Main ./examples/small.maz.txtd
MazeSolver failed.  Reason: ./examples/small.maz.txtd (No such file or directory)
[ERROR] Main MazeSolver failed.  Reason: ./examples/small.maz.txtd (No such file or directory)
[ERROR] Main PATH NOT COMPUTED
[INFO ] Main End of MazeRunner
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % 
```
#### Initial Command line arguments
- `-i MAZE_FILE`: specifies the filename to be used;
- `-p PATH_SEQUENCE`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze

If you are also delivering the bonus, your program will react to a third flag:

- `-method {tremaux, righthand}`: specifies which path computation method to use. (default is right hand)

#### Examples

When no logs are activated, the programs only print the computed path on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt
4F
mosser@azrael A1-Template %
```

If a given path is correct, the program prints the message `correct path` on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 4F
correct path
mosser@azrael A1-Template %
```

If a given path is incorrect, the program prints the message `incorrect path` on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 3F
inccorrect path
mosser@azrael A1-Template %
```

### Delivered version
#### Command line arguments

Still contains initial command line arguments :

- `-i MAZE_FILE`: specifies the filename to be used;
- `-p PATH_SEQUENCE`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze

- `-method {tremaux, righthand}`: specifies which path computation method to use. (NO DEFAULT - MUST CHOOSE METHOD)

Additional Command line arguments:
- `-method {bfs}`: additional bfs graph search algorithm added 
- `-baseline {tremaux, righthand, bfs}`: when doing both -method and -baseline (any order) compares how the -method is more improved than the baseline 

#### Examples

When no logs are activated, the programs only print the computed path on the standard output. Currently in files logs are activated. 

Finding the shortest path with BFS 

```
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % java -jar target/mazerunner.jar -i ./examples/large.maz.txt -method bfs              
15F R 2F L 8F R 2F L 4F R 2F L 4F R 4F L 6F R 2F L 2F R 2F L 2F R 2F L 2F R 2F L 2F R 2F L 2F R 6F L 2F L 2F R F
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % 
```

Using baseline 

```
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % java -jar target/mazerunner.jar -i ./examples/large.maz.txt -method bfs -baseline righthand
Time taken to go load the maze: 0.78ms
Time taken to explore the maze with righthand is:48.87ms
Time taken to explore the maze with bfs is:6.65ms
Speedup = baseline/method = 4494.0/144.0= 31.21
bfs is 31.21 times faster
tveshashah@MacBook-Pro a3-maze-runner-take-two-txshah % 
```

If a given path is correct, the program prints the message `correct path` on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 4F
correct path
mosser@azrael A1-Template %
```

If a given path is incorrect, the program prints the message `incorrect path` on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 3F
inccorrect path
mosser@azrael A1-Template %
```

