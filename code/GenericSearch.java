package code;

import java.util.ArrayDeque;
import java.util.Queue;

public class GenericSearch {
    int nodesExpanded;


    public static Node generalSearch(Problem problem, String searchStrategy, boolean visualize) {
        Queue<Node> nodes = new ArrayDeque<>();
        nodes.add(new Node(null, 0, problem.initialState, 0, code.Node.h1(problem.initialState), code.Node.h2(problem.initialState), null, 0));
        while (!nodes.isEmpty()) {
            Node currNode = nodes.poll();
            if (problem.goalTest(currNode.state)) {
                return currNode;
            }
            nodes = qAndExpand(nodes, currNode, searchStrategy, problem.operators, visualize);
        }
        return null;
    }

    public static Queue<Node> qAndExpand(Queue<Node> nodes, Node nodeToExpand, String searchStartegy, String[] operators, boolean visualize) {
        /*
        ∗ BF for breadth-first search,
        ∗ DF for depth-first search,
        ∗ ID for iterative deepening search,
        ∗ UC for uniform cost search,
        ∗ GRi for greedy search, with i ∈ {1, 2} distinguishing the two heuristics.
        ∗ ASi for A∗ search with i ∈ {1, 2} distinguishing the two heuristics.
        */
        switch (searchStartegy) {
            case "BF":
                return bfs(nodes, nodeToExpand, operators, visualize);
            case "DF":
                return dfs(nodes, nodeToExpand, operators, visualize);
            case "ID":
                return itrDeep(nodes, nodeToExpand, operators, visualize);
            case "UC":
                return ucs(nodes, nodeToExpand, operators, visualize);
            case "GR1":
                return greedy(nodes, nodeToExpand, operators, 1, visualize);
            case "GR2":
                return greedy(nodes, nodeToExpand, operators, 2, visualize);
            case "AS1":
                return Astar(nodes, nodeToExpand, operators, 1, visualize);
            case "AS2":
                return Astar(nodes, nodeToExpand, operators, 2, visualize);
            default: return new ArrayDeque<>();
        }
    }

    public static Queue<Node> bfs(Queue<Node> nodes, Node nodeToExpand, String[] operators, boolean visualize) {
        return new ArrayDeque<>();
    }

    public static Queue<Node> dfs(Queue<Node> nodes, Node nodeToExpand, String[] operators, boolean visualize) {
        return new ArrayDeque<>();
    }

    public static Queue<Node> itrDeep(Queue<Node> nodes, Node nodeToExpand, String[] operators, boolean visualize) {
        return new ArrayDeque<>();
    }

    public static Queue<Node> ucs(Queue<Node> nodes, Node nodeToExpand, String[] operators, boolean visualize) {
        return new ArrayDeque<>();
    }

    public static Queue<Node> greedy(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, boolean visualize) {
        return new ArrayDeque<>();
    }

    public static Queue<Node> Astar(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, boolean visualize) {
        return new ArrayDeque<>();
    }



}
