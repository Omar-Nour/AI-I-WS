package code;

import java.util.ArrayDeque;
import java.util.Queue;

public class GenericSearch {

    public static Node generalSearch(Problem problem, String searchStrategy, boolean visualize) {
        Queue<Node> nodes = new ArrayDeque<>();
        nodes.add(new Node(null, 0, problem.initialState, 0, problem.h1(problem.initialState), null));
        while (!nodes.isEmpty()) {
            Node currNode = nodes.poll();
            if (problem.goalTest(currNode.state)) {
                return currNode;
            }
            nodes = qAndExpand(nodes, currNode, searchStrategy, problem.operators, problem, visualize);
        }
        return null;
    }

    public static Queue<Node> qAndExpand(Queue<Node> nodes, Node nodeToExpand, String searchStartegy, String[] operators, Problem problem, boolean visualize) {
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
                return bfs(nodes, nodeToExpand, operators, problem, visualize);
            case "DF":
                return dfs(nodes, nodeToExpand, operators, problem, visualize);
            case "ID":
                return itrDeep(nodes, nodeToExpand, operators, problem, visualize);
            case "UC":
                return ucs(nodes, nodeToExpand, operators, problem, visualize);
            case "GR1":
                return greedy(nodes, nodeToExpand, operators, 1, problem, visualize);
            case "GR2":
                return greedy(nodes, nodeToExpand, operators, 2, problem, visualize);
            case "AS1":
                return Astar(nodes, nodeToExpand, operators, 1, problem, visualize);
            case "AS2":
                return Astar(nodes, nodeToExpand, operators, 2, problem, visualize);
            default: return new ArrayDeque<>();
        }
    }

    public static Queue<Node> bfs(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }

    public static Queue<Node> dfs(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }

    public static Queue<Node> itrDeep(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // HUUUH
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }

    public static Queue<Node> ucs(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }

    public static Queue<Node> greedy(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, heuristicNum);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }

    public static Queue<Node> Astar(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, heuristicNum);
        // insert each node in "nodes" according to search strategy
        return new ArrayDeque<>();
    }



}
