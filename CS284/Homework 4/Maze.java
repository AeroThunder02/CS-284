package Maze;
import java.util.*;
/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
    	//If there's no possible path, then return false
    	if ((x > maze.getNCols() - 1) || (x < 0) || (y > maze.getNRows() - 1) || y < 0) {
            return false;    
    	}
    	
    	//If there's zero paths, then return false 
    	else if (maze.getColor(x, y) != GridColors.NON_BACKGROUND) {
            return false;
    	} 
    	
    	//Path found
    	else if ((x == maze.getNCols() - 1) && (y == maze.getNRows() - 1)) {
            maze.recolor(x, y, GridColors.PATH);
            return true;
        }

        maze.recolor(x, y, GridColors.TEMPORARY);
        
        
        if (findMazePath(x - 1, y) || findMazePath(x + 1, y) || findMazePath(x, y - 1) || findMazePath(x, y + 1)) {
            maze.recolor(x, y, GridColors.PATH);
            return true;
        } 
        
        else {
            return false;
        }
    }
    
    private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    	//A remodel of problem 1, but adding to a stack rather than returning true, and throwing excpetions rather than returning false
        if (x > maze.getNCols() - 1 || x < 0 || y > maze.getNRows() - 1 || y < 0) {
        	throw new IllegalArgumentException(
					"Out of Bounds");
        } 
        
        else if (maze.getColor(x, y) != GridColors.NON_BACKGROUND) {
        	throw new IllegalArgumentException(
					"Not part of a path");
        } 
        
        //If it is in bounds
        else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
            trace.push(new PairInt(x, y));    //Pushing point to the stack
            ArrayList<PairInt> path = new ArrayList<>();  //Beginning the path
            path.addAll(trace);    //add everything from trace to path
            result.add(path);     
            maze.recolor(x, y, GridColors.NON_BACKGROUND);  
            trace.pop();
        } 
        
        else {
            maze.recolor(x, y, GridColors.TEMPORARY);
            trace.push(new PairInt(x, y));
            findMazePathStackBased(x + 1, y, result, trace);  //The following are all recursive calls to find a possible path
            findMazePathStackBased(x - 1, y, result, trace);   
            findMazePathStackBased(x, y + 1, result, trace);
            findMazePathStackBased(x, y - 1, result, trace);
            maze.recolor(x, y, GridColors.NON_BACKGROUND);
            trace.pop();     
        }
    }
    
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {

        ArrayList<ArrayList<PairInt>> result = new ArrayList<>(); //make a new array list listing the paths
        Stack<PairInt> trace = new Stack<>();        
        findMazePathStackBased(0, 0, result, trace);
        return result;
        
    }
    
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
       
        ArrayList<ArrayList<PairInt>> paths = findAllMazePaths(x, y);  //A new arraylist full of arraylists that are the different paths
        
        if (paths.size() == 0) {                 //Case for when there are no paths
            return new ArrayList<PairInt>();
        }
        
        
        ArrayList<PairInt> shortestPath = paths.get(0);     //Start from the first path
        
        for (int i = 1; i < paths.size(); i++) {
            if (paths.get(i).size() < shortestPath.size()) {    //if the size of the current shortest path is smaller
                shortestPath = paths.get(i);   //set it to the new smallest path
            }
        }
        return shortestPath;
    }    
    
    
    

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
}
/*</listing>*/
