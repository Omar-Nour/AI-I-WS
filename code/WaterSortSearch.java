package code;

import java.security.Key;
import java.util.*;

class WaterSortProblem extends Problem {


    public WaterSortProblem(ArrayList<State> stateSpace, State initialState, String[] operators) {
        super(stateSpace, initialState, operators);

    }

    public int getBottleCapacity(State state) {
        return ((char[][]) state.getDS())[0].length;
    }

    public char[][] getBottles(State state) {
        return (char[][]) state.getDS();
    }

    public boolean bottleIsEmpty(State state, int i) {
        return getBottles(state)[i][0] == 'e';
    }

    public boolean bottleIsFull(State state, int i) {
        return getBottles(state)[i][getBottleCapacity(state) - 1] != 'e';
    }

    public char getTop(State state, int i) {
        //if it returns e then the bottle is empty
        for (int j = getBottleCapacity(state) - 1; j >= 0; j--) {
            if (getBottles(state)[i][j] != 'e') {
                return getBottles(state)[i][j];
            }
        }
        return 'e';
    }

    @Override
    boolean goalTest(State currState) {

        char[][] bottles = getBottles(currState);
        for (int i = 0; i < bottles.length; i++) {
            for (int j = 0; j < bottles[i].length; j++) {
                if (j == 0 && bottles[i][j] == 'e') {
                    continue;
                }

                if (bottles[i][j] != 'e' && bottles[i][j] != bottles[i][0]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    boolean shouldPerformAction(State currState, String currOperator, Node currentNode) {

        // check if in visitedStates
        // check if valid
        // check if parent is not pour(i, j) and current action is pour(j, i)
        if (currentNode.parent == null) // this is root so all operators are fine
            return true;

        String[] lines = currOperator.split("_");
        int theI = Integer.parseInt(lines[1]);
        int theJ = Integer.parseInt(lines[2]);

        State newExpectedState = (State) testAction(currentNode, currState, currOperator, false, 0)[0];

        if (visitedStates.contains(newExpectedState.toString())) {
            return false;
        } else if (bottleIsEmpty(currState, theI) || bottleIsFull(currState, theJ)) {
            return false;
        } else if ((getTop(currState, theI) != getTop(currState, theJ)) && (!bottleIsEmpty(currState, theJ))) {
            return false;

        } else if (currentNode != null) {
            String[] parentOperator = currentNode.operatorApplied.split("_");
            int parentI = Integer.parseInt(parentOperator[1]);
            int parentJ = Integer.parseInt(parentOperator[2]);
            if (parentOperator[0].equals("pour") && theI == parentJ && theJ == parentI) {
                return false;
            }
        }

        return true;
    }

    @Override
    Node perfomAction(Node currentNode, State currState, String currOperator, boolean visualize, int heuristicType) {
        //we already know it is a valid action
        // add to visitedStates
        // print if visualize
        // perform action on current state in a new node and adjust pathcost, adjust nodes expanded, calculate heuristic if (1 or 2), depth, parent operator applied
        // return the new node



        // if (visualize) {
        //  System.out.println(currState.toString() + " -> " + currOperator);
        // }

        Object[] res = testAction(currentNode, currState, currOperator, visualize, heuristicType);

        State newState = (State) res[0];
        visitedStates.add(newState.toString());
        int actualPourLayers = (int) res[1];
        Node newNode = new Node(currentNode, currentNode.depth + 1, newState, currentNode.pathCostFromRoot + actualPourLayers, (heuristicType == 1) ? h1(newState) : h2(newState), currOperator);
        return newNode;
    }

    @Override
    Object[] testAction(Node parentNode, State currState, String currOperator, boolean visualize, int heuristicType) {


        String[] lines = currOperator.split("_");

        //identify the two bottles
        int bottleFrom = Integer.parseInt(lines[1]);
        int bottleTo = Integer.parseInt(lines[2]);

        // create a copy of the current state (the bottle)
        char[][] bottles = getBottles(currState);
        char[][] newBottles = new char[bottles.length][WaterSortState.bottleCapacity];
        for (int i = 0; i < bottles.length; i++) {
            for (int j = 0; j < WaterSortState.bottleCapacity; j++) {
                newBottles[i][j] = bottles[i][j];
            }
        }

        // know at which index to pour from and to
        int i = getBottleCapacity(currState) - 1;
        while (i >= 1 && newBottles[bottleFrom][i] == 'e') {
            i--;
        }

        int numOfLayersToPour = 0;
        char color = newBottles[bottleFrom][i];
        for (int k = i; k >= 0; k--) {
            if (newBottles[bottleFrom][k] == color) {
                numOfLayersToPour++;
            } else {
                break;
            }
        }

        int j = 0;
        if (newBottles[bottleTo][j] != 'e') {
            while (j < WaterSortState.bottleCapacity && newBottles[bottleTo][j] != 'e' ) {
                j++;
            }
        }

        int numOfPourCellsAvailable = getBottleCapacity(currState) - j ;

        int actualPourLayers = Math.min(numOfLayersToPour, numOfPourCellsAvailable);

        //pour the water
        for (int k = 0; k < actualPourLayers; k++) {
            newBottles[bottleTo][j] = newBottles[bottleFrom][i];
            newBottles[bottleFrom][i] = 'e';
            i--;
            j++;
        }

        //create a new state
        State newState = new WaterSortState(newBottles);
        return new Object[]{newState, actualPourLayers};
    }

    @Override
    int h1(State currState) {
        // h1 logik: return the number of non goal bottles
        // source trust me bro
        int nonGoalBottles = 0;
        char[][] bottles = (char[][]) currState.getDS();
        for (int bi = 0; bi < bottles.length; bi++) {
            char[] b = bottles[bi];
            char fColor = '#';
            for (int i = 0; i < b.length; i++) {
                if (b[i] != 'e') {
                    if (fColor == '#') {
                        fColor = b[i];
                    } else if (b[i] != fColor) {
                        nonGoalBottles++;
                        break;
                    }
                }
            }
        }
        return nonGoalBottles;
    }

    @Override
    int h2(State currState) {
        int minPourCountToFix = 0;
        char[][] bottles = (char[][]) currState.getDS();

        for (char[] bottle : bottles) {
            char currColor = 'e';

            for (char layer : bottle) {
                if (layer != 'e' && layer != currColor) {
                    minPourCountToFix++;
                    currColor = layer;
                }
            }
        }

        return minPourCountToFix;
    }
}

public class WaterSortSearch extends GenericSearch {


    public static String solve(String initialState, String strategy, boolean visualize) {
        // create a new waterSort object
        // call generalSearch from GenericSearch
        // return the solution

        WaterSortState state = new WaterSortState(initialState);
        String[] operators = new String[state.numOfBottles * state.numOfBottles - state.numOfBottles];
        int k = 0;
        for (int i = 0; i < state.numOfBottles; i++) {
            for (int j = 0; j < state.numOfBottles; j++) {
                if (i != j) {
                    operators[k] = "pour_" + i + "_" + j;
                    k++;
                }
            }
        }

        WaterSortProblem problem = new WaterSortProblem(null, state, operators);
        Node solution = generalSearch(problem, strategy, visualize);

        if (solution == null) {
            return "NOSOLUTION";
        }

        System.out.println(strategy);
        //System.out.println("sol: " + solution.state);

        explainSolPlan(solution, visualize);

        return genSolutionStrFromNode(solution, problem.nodesExpanded);
    }

    public static String genSolutionStrFromNode(Node sol, int nodesExpanded) {
        String operators = "" + sol.pathCostFromRoot + ";" + nodesExpanded;
        operators = sol.operatorApplied + ";" + operators;
        while (sol.parent != null) {
            sol = sol.parent;
            if (sol.parent != null)
                operators = sol.operatorApplied + "," + operators;
        }
        return operators;
    }

    public static void explainSolPlan(Node sol, boolean visualize) {
        if (visualize) {
            BottleVisualizerGUI.visualizeSolutionPlan(sol);

            Stack<Node> planNodes = new Stack<>();
            Stack<String> oprStack = new Stack<>();
            while (sol != null) {
                planNodes.add(sol);
                if (sol.operatorApplied != null) {
                    oprStack.add(sol.operatorApplied);
                }
                sol = sol.parent;
            }

            while (!planNodes.isEmpty()) {
                Node currentNode = planNodes.pop();
                String currentOpr = (oprStack.isEmpty()) ? null : oprStack.pop();

                // Print State, Depth, action

                System.out.println("Current State: ");
                drawBottles(currentNode.state.toString());
                System.out.println("Depth: " + currentNode.depth);
                System.out.println("Current Operation: " + ((currentOpr== null) ? "None (solution state)" : currentOpr));
                System.out.println("----------------------------------------------------------------");

            }
        }
    }

    private static void drawBottles(String state) {
        String[] bottles = state.split(";");  // Split the state by ';' to get individual bottles

        // Assuming all bottles have the same height (max height)
        int maxHeight = bottles[0].length();

        // Draw bottles from top to bottom (reverse order)
        for (int level = maxHeight - 1; level >= 0; level--) {
            StringBuilder levelS = new StringBuilder(); // Use StringBuilder for efficient string concatenation

            for (String bottle : bottles) {
                // Handle cases where the bottle might not be tall enough
                char color = (level < bottle.length()) ? bottle.charAt(level) : 'e'; // 'e' for empty
                levelS.append(getColoredPart(color)).append(" "); // Add colored part
            }

            // Print the current level of bottles
            System.out.println(levelS.toString()); // Print all bottles at the current level
        }

    }

    // Helper function to represent the color with a visual symbol
    private static String getColoredPart(char color) {
        switch (color) {
            case 'r':
                return "\u001B[91m⬤\u001B[0m";  // Bright Red
            case 'b':
                return "\u001B[94m⬤\u001B[0m";  // Bright Blue
            case 'y':
                return "\u001B[93m⬤\u001B[0m";  // Bright Yellow
            case 'g':
                return "\u001B[92m⬤\u001B[0m";  // Bright Green
            case 'o':
                return "\u001B[38;5;208m⬤\u001B[0m";  // Bright Orange (using extended colors)
            case 'p':
                return "\u001B[35m⬤\u001B[0m";  // Magenta
            case 'e':
                return "\u001B[0m⬤\u001B[0m";  // Default color for empty (no color)
            default:
                return " ";
        }
    }

    public static void main(String[] args) {
        System.out.println(solve("5;4;" + "b,y,r,b;" + "b,y,r,r;" +
                "y,r,b,y;" + "b,y,r,b;" + "b,y,r,b;", "ID", true));
    }
}


