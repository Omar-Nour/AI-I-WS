package code;

public class Node {
    Node parent;
    int depth;
    int pathCostFromRoot;
    int heuristicToGoal1;
    int heuristicToGoal2;
    State state;
    String operatorApplied;

    public Node(Node parent, int depth, State state, int pathCostFromRoot, int heuristicToGoal1, int heuristicToGoal2, String operatorApplied) {
        this.parent = parent;
        this.depth = depth;
        this.pathCostFromRoot = pathCostFromRoot;
        this.heuristicToGoal1 = heuristicToGoal1;
        this.heuristicToGoal2 = heuristicToGoal2;
        this.state = state;
        this.operatorApplied = operatorApplied;
    }
}
