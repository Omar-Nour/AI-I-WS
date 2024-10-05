package code;

public class Node {
    Node parent;
    int depth;
    int pathCostFromRoot;
    int heuristicToGoal1;
    int heuristicToGoal2;
    int nodesExpanded;
    State state;
    String operatorApplied;

    public Node(Node parent, int depth, State state, int pathCostFromRoot, int heuristicToGoal1, int heuristicToGoal2, String operatorApplied, int nodesExpanded) {
        this.parent = parent;
        this.depth = depth;
        this.pathCostFromRoot = pathCostFromRoot;
        this.heuristicToGoal1 = heuristicToGoal1;
        this.heuristicToGoal2 = heuristicToGoal2;
        this.state = state;
        this.operatorApplied = operatorApplied;
        this.nodesExpanded = nodesExpanded;
    }

    public static int h1(State currState) {
        return 0;
    }

    public static int h2(State currState) {
        return 0;
    }
}
