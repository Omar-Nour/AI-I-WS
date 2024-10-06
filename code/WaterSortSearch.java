package code;

import java.util.ArrayList;
import java.util.Queue;

class WaterSort extends Problem {


    public WaterSort(ArrayList<State> stateSpace, State initialState, String[] operators) {
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
        String[] lines = currOperator.split("_");
        int theI = Integer.parseInt(lines[1]);
        int theJ = Integer.parseInt(lines[2]);

        if (visitedStates.contains(currState.toString())) {
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

        visitedStates.add(currState.toString());
        // if (visualize) {
        //  System.out.println(currState.toString() + " -> " + currOperator);
        // }

        String[] lines = currOperator.split("_");

        //identify the two bottles
        int bottleFrom = Integer.parseInt(lines[1]);
        int bottleTo = Integer.parseInt(lines[2]);

        // create a copy of the current state (the bottle)
        char[][] bottles = getBottles(currState);
        char[][] newBottles = new char[bottles.length][bottles[0].length];
        for (int i = 0; i < bottles.length; i++) {
            for (int j = 0; j < bottles[i].length; j++) {
                newBottles[i][j] = bottles[i][j];
            }
        }

        // know at which index to pour from and to
        int i = getBottleCapacity(currState) - 1;
        while (newBottles[bottleFrom][i] == 'e') {
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
            while (newBottles[bottleTo][j] != 'e') {
                j++;
            }
        }

        int numOfPourCellsAvailable = getBottleCapacity(currState) - j;

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

        //TODO: cost+1 or cost+actualPourLayers???
        Node newNode = new Node(currentNode, currentNode.depth + 1, newState, currentNode.pathCostFromRoot + 1, 0, currOperator);
        return newNode;
    }

    @Override
    State testAction(Node parentNode, State currState, String currOperator, boolean visualize, int heuristicType) {
        return null;
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

        WaterSort problem = new WaterSort(null, state, operators);
        Node solution = generalSearch(problem, strategy, visualize);

        return "";
    }
}


