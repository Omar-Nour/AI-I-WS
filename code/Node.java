package code;

public class Node {
    Node parent;
    int depth;
    int pathCostFromRoot;
    int heuristicToGoal;
    State state;
    String operatorApplied;

    public Node(Node parent, int depth, State state, int pathCostFromRoot, int heuristicToGoal, String operatorApplied) {
        this.parent = parent;
        this.depth = depth;
        this.pathCostFromRoot = pathCostFromRoot;
        this.heuristicToGoal = heuristicToGoal;
        this.state = state;
        this.operatorApplied = operatorApplied;
    }

    public static int h1(State currState) {
        return 0;
    }

    public static int h2(State currState) {
        return 0;
    }
}
