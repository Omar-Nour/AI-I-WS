package code;

import java.util.ArrayList;
import java.util.Queue;

public class WaterSortSearch extends GenericSearch {
    public class waterSort extends Problem {
        public waterSort(ArrayList<State> stateSpace, State initialState, String[] operators, Queue<Node> queue) {
            super(stateSpace, initialState, operators, queue);
        }
        @Override
        boolean goalTest(State currState) {
            return false;
        }
        @Override
        boolean shouldPerformAction(State currState, String currOperator) {
            // check if in visitedStates
            // check if valid
            // check if parent is not pour(i, j) and current action is pour(j, i)

            return false;
        }

        @Override
        Node perfomAction(Node parentNode, State currState, String currOperator, boolean visualize, int heuristicType) {
            // add to visitedStates
            // print if visualize
            // perform action on current state in a new node and adjust pathcost, adjust nodes expanded, calculate heuristic if (1 or 2), depth, parent operator applied
            return null;
        }
    }

    public static String solve(String initialState, String strategy, boolean visualize) {
        return "";
    }
}


