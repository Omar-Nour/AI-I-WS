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
        this.visitedStates.add(initialState.toString());
        this.nodesExpanded = 0;
    }

    abstract int h1(State currState);

    abstract int h2(State currState);


    abstract boolean goalTest(State currState) ;
    abstract boolean shouldPerformAction(State currState, String currOperator, Node currentNode);
    abstract Node perfomAction (Node currentNode, State currState, String currOperator, boolean visualize, int heuristicType);
    abstract Object[] testAction(Node currentNode, State currState, String currOperator, boolean visualize, int heuristicType);
}
