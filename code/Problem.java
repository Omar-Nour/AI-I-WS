package code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Problem {
    ArrayList<State> stateSpace;
    State initialState;
    String[] operators;
    Queue<Node> queue;

    public Problem(ArrayList<State> stateSpace, State initialState, String[] operators, Queue<Node> queue) {
        this.stateSpace = stateSpace;
        this.initialState = initialState;
        this.operators = operators;
        this.queue = queue;
    }

    public boolean goalTest(State currState) {
        return false;
    }
}
