/******************************************************************************
 *  Compilation:  javac PuzzleChecker.java
 *  Execution:    java PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;

public class PuzzleChecker {
// delay in miliseconds (controls animation speed)
    private static final int DELAY = 100;
    
    private Board board;

    // draw n-by-n percolation system
    public static void draw(int[][] board, int n, PuzzleChecker checker) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05, n + 0.05);
        StdDraw.setYscale(-0.2, n + 0.05);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, 0);

        // draw n-by-n grid     
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] != 0) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); 
                }               
                else
                    StdDraw.setPenColor(StdDraw.BLACK);
                
                StdDraw.filledSquare(col + 0.5, n - row - 0.5, 0.45);
                StdDraw.setFont(new Font("OpenSans", Font.BOLD, 16));
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(col + 0.5, n - row - 0.5, String.valueOf(board[row][col]));     
            }
        }
        
        if(checker.board != null) {
            // write status text
            StdDraw.setFont(new Font("OpenSans", Font.PLAIN, 12));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(0.25*n, -0.025*n, "hamming: " + String.valueOf(checker.board.hamming()));      
            StdDraw.text(0.65*n, -0.025*n, "manhatten: " + String.valueOf(checker.board.manhattan()));      
        }
        

    }
    public static void main(String[] args) {
            PuzzleChecker checker = new PuzzleChecker();
            // turn on animation mode
            StdDraw.enableDoubleBuffering();
            // read in the board specified in the filename
            In in = new In("files\\puzzle23.txt");
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            draw(tiles, n, checker);
            StdDraw.show();
            StdDraw.pause(DELAY);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                    draw(tiles, n, checker);
                    StdDraw.show();
                    StdDraw.pause(DELAY);
                }
            }  
            
            // solve the slider puzzle
            checker.board = new Board(tiles);        
            assert(checker.board.dimension() == n);
            
            int hamming = checker.board.hamming();
            int manhattan = checker.board.manhattan();
            boolean isGoal = checker.board.isGoal();
            Board twin = checker.board.twin();
            
            Solver solver = new Solver(checker.board);
            solver.isSolvable();
            
            draw(tiles, n, checker);
            StdDraw.show();
            StdDraw.pause(DELAY);
    }
}
