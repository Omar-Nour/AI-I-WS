package code;

import java.util.*;

abstract class Problem {
    ArrayList<State> stateSpace;
    State initialState;
    String[] operators;
    Queue<Node> queue;
    HashSet<String> visitedStates;
    int nodesExpanded;


    public Problem(ArrayList<State> stateSpace, State initialState, String[] operators) {
        this.stateSpace = stateSpace;
        this.initialState = initialState;
        this.operators = operators;
        this.queue = new ArrayDeque<>();
        this.visitedStates = new HashSet<>();
        this.nodesExpanded = 0;
    }


    abstract boolean goalTest(State currState) ;
    abstract boolean shouldPerformAction(State currState, String currOperator, Node currentNode);
    abstract Node perfomAction (Node currentNode, State currState, String currOperator, boolean visualize, int heuristicType);
    abstract State testAction(Node currentNode, State currState, String currOperator, boolean visualize, int heuristicType);
}
