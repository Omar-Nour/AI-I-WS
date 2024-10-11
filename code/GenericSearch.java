package code;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class GenericSearch {

    public static Node generalSearch(Problem problem, String searchStrategy, boolean visualize) {

        Node initialNode = new Node(null, 0, problem.initialState, 0, 99999, null);

        if (searchStrategy.equals("ID")) {
            return itrDeep(initialNode, problem.operators, problem, visualize);
        } else {
            Queue<Node> nodes = new ArrayDeque<>();
            nodes.add(initialNode);
            while (!nodes.isEmpty()) {
                Node currNode = nodes.poll();
                if (problem.goalTest(currNode.state)) {
                    return currNode;
                }
                nodes = qAndExpand(nodes, currNode, searchStrategy, problem.operators, problem, visualize);
            }
        }

        return null;
    }

    public static Queue<Node> qAndExpand(Queue<Node> nodes, Node nodeToExpand, String searchStartegy, String[] operators, Problem problem, boolean visualize) {
        /*
        ∗ BF for breadth-first search,
        ∗ DF for depth-first search,
        ∗ UC for uniform cost search,
        ∗ GRi for greedy search, with i ∈ {1, 2} distinguishing the two heuristics.
        ∗ ASi for A∗ search with i ∈ {1, 2} distinguishing the two heuristics.
        */
        problem.nodesExpanded++;

        switch (searchStartegy) {
            case "BF":
                return bfs(nodes, nodeToExpand, operators, problem, visualize);
            case "DF":
                return dfs(nodes, nodeToExpand, operators, problem, visualize);
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
        // return modified nodes

        for (String opr : operators) {
            if (problem.shouldPerformAction(nodeToExpand.state, opr, nodeToExpand)) {
                System.out.println("should do " +  opr);
                Node newNode = problem.perfomAction(nodeToExpand, nodeToExpand.state, opr, visualize, 0);
                nodes.add(newNode);
            }
        }
        System.out.println("iter------------");

        return nodes;
    }

    public static Queue<Node> dfs(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        // return modified nodes

        return new ArrayDeque<>();
    }

    public static Node itrDeep(Node initialNode, String[] operators, Problem problem, boolean visualize) {
        // HUUUH
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy

        Queue<Node> nodes = new ArrayDeque<>();
        nodes.add(initialNode);

        int maxDepth = 0;
        while (true) {

            if (nodes.isEmpty()) {
                nodes.add(initialNode);
                maxDepth++;

                //reset visited states
                problem.visitedStates = new HashSet<String>();
                problem.visitedStates.add(initialNode.state.toString());
            }


            Node currNode = nodes.poll();
            if (problem.goalTest(currNode.state)) {
                return currNode;
            }

            if (currNode.depth < maxDepth) {
                problem.nodesExpanded++;
                for (String opr : operators) {
                    if (problem.shouldPerformAction(currNode.state, opr, currNode)) {
                        Node newNode = problem.perfomAction(currNode, currNode.state, opr, visualize, 0);
                        nodes = enqueueAtFront(nodes, newNode);
                    }
                }
            }

        }
    }

    public static Queue<Node> ucs(Queue<Node> nodes, Node nodeToExpand, String[] operators, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, 0);
        // insert each node in "nodes" according to search strategy
        // return modified nodes

        for (String opr : operators) {
            if (problem.shouldPerformAction(nodeToExpand.state, opr, nodeToExpand)) {
                System.out.println("should do " +  opr);
                Node newNode = problem.perfomAction(nodeToExpand, nodeToExpand.state, opr, visualize, 0);
                insertSorted(nodes, newNode, true, false);
            }
        }
        System.out.println("iter------------");

        return nodes;
    }

    public static Queue<Node> greedy(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, heuristicNum);
        // insert each node in "nodes" according to search strategy
        for (String opr : operators) {
            if (problem.shouldPerformAction(nodeToExpand.state, opr, nodeToExpand)) {
                System.out.println("should do " +  opr);
                Node newNode = problem.perfomAction(nodeToExpand, nodeToExpand.state, opr, visualize, heuristicNum);
                insertSorted(nodes, newNode, false, true);
            }
        }
        System.out.println("iter------------");

        return nodes;
    }

    public static Queue<Node> Astar(Queue<Node> nodes, Node nodeToExpand, String[] operators, int heuristicNum, Problem problem, boolean visualize) {
        // DO NOT REMOVE, ONLY ADD NODES
        // loop on operators
        // before doing operator on "nodeToExpand" check with problem.shouldPerformAction(State, operator)
        // create nodes after acceptance check by calling problem.performAction(nodeToExpand, nodeToExpand.state, currOperator,  visualize, heuristicNum);
        // insert each node in "nodes" according to search strategy
        for (String opr : operators) {
            if (problem.shouldPerformAction(nodeToExpand.state, opr, nodeToExpand)) {
                System.out.println("should do " +  opr);
                Node newNode = problem.perfomAction(nodeToExpand, nodeToExpand.state, opr, visualize, heuristicNum);
                insertSorted(nodes, newNode, true, true);
            }
        }
        System.out.println("iter------------");
        return nodes;
    }

    public static void insertSorted(Queue<Node> nodes, Node n, boolean gn, boolean hn) {
        int count = nodes.size();
        int costCalculationQueueNode = 0;
        int costCalculationInsertNode = 0;
        // gn : cost from root only
        // hn : cost to goal

        boolean inserted = false;
        while (count-- > 0) {
            costCalculationQueueNode = gn ? nodes.peek().pathCostFromRoot + ((hn) ? nodes.peek().heuristicToGoal : 0) : nodes.peek().heuristicToGoal;
            costCalculationInsertNode = gn ? n.pathCostFromRoot + ((hn) ? n.heuristicToGoal : 0) : n.heuristicToGoal;
            if (!inserted && costCalculationQueueNode >= costCalculationInsertNode) {
                nodes.add(n);
                nodes.add(nodes.poll());
                inserted = true;
            } else {
                nodes.add(nodes.poll());
            }
        }

        if (!inserted) {
            nodes.add(n);
        }


        System.out.println(nodes);
    }

    public static Queue<Node> enqueueAtFront(Queue<Node> nodes, Node n) {
        Queue<Node> temp = new ArrayDeque<>();
        temp.add(n);
        while (!nodes.isEmpty()) {
            temp.add(nodes.poll());
        }

        System.out.println(temp);
        return temp;

    }



}
