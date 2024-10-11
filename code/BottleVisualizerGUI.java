package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class BottleVisualizerGUI extends JPanel {

    private char[][] bottles;  // 2D array to store the bottle colors
    private String operation;
    private int depth;

    // Constructor that takes a state representation as a string
    public BottleVisualizerGUI(String stateRepresentation, String operation, int depth) {
        this.bottles = parseState(stateRepresentation);  // Parse the state string
        this.operation = operation;
        this.depth = depth;
    }

    // Method to parse the state string into a 2D char array
    private char[][] parseState(String stateRepresentation) {
        String[] bottleStrings = stateRepresentation.split(";");
        char[][] bottles = new char[bottleStrings.length][];

        for (int i = 0; i < bottleStrings.length; i++) {
            bottles[i] = bottleStrings[i].replace(",", "").toCharArray();  // Convert to char array
        }

        return bottles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBottles(g, bottles);
    }

    // Function to draw bottles using Swing graphics
    private void drawBottles(Graphics g, char[][] bottles) {
        if (bottles == null || bottles.length == 0) {
            g.drawString("No Solution Found", 50, 100); // In case the state is invalid
            return;
        }

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Bottle State", 175, 50);

        int bottleWidth = 50;
        int bottleHeight = 200;
        int bottleSpacing = 20;
        int maxHeight = bottles[0].length;  // Assuming all bottles have the same height

        // Calculate starting position to center bottles
        int totalWidth = bottles.length * (bottleWidth + bottleSpacing) - bottleSpacing;
        int startX = (getWidth() - totalWidth) / 2; // Center bottles horizontally
        int baseY = 300; // Y position for the bottom of the bottles

        for (int i = 0; i < bottles.length; i++) {
            char[] bottle = bottles[i];
            int x = startX + i * (bottleWidth + bottleSpacing);  // Horizontal position of the bottle

            // Draw each color in the bottle from bottom to top
            for (int j = 0; j < maxHeight; j++) {
                int y = baseY - (j + 1) * (bottleHeight / maxHeight);  // Calculate vertical position
                char color = (j < bottle.length) ? bottle[j] : 'e'; // Use 'e' for empty
                g.setColor(getColor(color));
                g.fillRect(x, y, bottleWidth, bottleHeight / maxHeight);  // Draw the color block
                g.setColor(Color.BLACK);
                g.drawRect(x, y, bottleWidth, bottleHeight / maxHeight);  // Draw the border
            }

            // Draw index below the bottle
            g.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for bottle index
            g.drawString(String.valueOf(i), x + bottleWidth / 2 - 5, baseY + 15); // Center the index text
        }

        // Draw additional information (operation and depth)
        g.setColor(Color.BLACK);
         // Set font for operation and depth
        g.drawString("Next Operation: " + (operation == null ? "None (solution state)" : operation), 50, 370);
        g.drawString("Depth: " + depth, 50, 400);
    }

    // Helper function to return the color based on the character
    private Color getColor(char color) {
        switch (color) {
            case 'r':
                return Color.RED;
            case 'b':
                return Color.BLUE;
            case 'y':
                return Color.YELLOW;
            case 'e':
                return Color.WHITE;  // Empty
            default:
                return Color.GRAY;  // Default color for unknown states
        }
    }

    // Class to manage the entire solution plan visualization with GUI
    public static class BottleVisualizerManager {
        private ArrayList<Node> planNodes;
        private ArrayList<String> oprStack;
        private int currentIndex;

        private JFrame frame;
        private BottleVisualizerGUI bottlePanel;
        private JButton prevButton;
        private JButton nextButton;

        public BottleVisualizerManager(ArrayList<Node> planNodes, ArrayList<String> oprStack) {
            this.planNodes = planNodes;
            this.oprStack = oprStack;
            this.currentIndex = 0;

            // Check if planNodes is empty
            if (planNodes.isEmpty()) {
                System.out.println("Error: No solution nodes to display.");
                JOptionPane.showMessageDialog(null, "No solution found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;  // Exit if there's nothing to visualize
            }

            // Set up the main GUI frame
            frame = new JFrame("Bottle Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450, 500);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            // Initialize buttons first before calling updateBottlePanel
            prevButton = new JButton("Previous");
            nextButton = new JButton("Next");

            prevButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPreviousState();
                }
            });

            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showNextState();
                }
            });

            // Create navigation button panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);

            // Add the button panel to the frame
            frame.add(buttonPanel, BorderLayout.SOUTH);

            // Now update the bottle panel
            updateBottlePanel();

            // Show the frame
            frame.setVisible(true);
        }

        // Update the panel to reflect the current state
        private void updateBottlePanel() {
            if (bottlePanel != null) {
                frame.remove(bottlePanel);
            }

            Node currentNode = planNodes.get(currentIndex);
            String currentOpr = (currentIndex < oprStack.size()) ? oprStack.get(currentIndex) : null;

            bottlePanel = new BottleVisualizerGUI(currentNode.state.toString(), currentOpr, currentNode.depth); // Use state as string
            frame.add(bottlePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            // Disable/enable buttons based on the current index
            prevButton.setEnabled(currentIndex > 0);
            nextButton.setEnabled(currentIndex < planNodes.size() - 1);
        }

        // Show the previous state
        private void showPreviousState() {
            if (currentIndex > 0) {
                currentIndex--;
                updateBottlePanel();
            }
        }

        // Show the next state
        private void showNextState() {
            if (currentIndex < planNodes.size() - 1) {
                currentIndex++;
                updateBottlePanel();
            }
        }
    }

    // Method to visualize the solution plan using GUI
    public static void visualizeSolutionPlan(Node sol) {
        if (sol == null) {
            System.out.println("Error: Solution node is null.");
            JOptionPane.showMessageDialog(null, "No solution found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Stack<Node> nodeStack = new Stack<>();
        Stack<String> operationStack = new Stack<>();

        // Traverse the solution path and store states and operations
        while (sol != null) {
            nodeStack.add(sol);
            if (sol.operatorApplied != null) {
                operationStack.add(sol.operatorApplied);
            }
            sol = sol.parent;
        }

        // Reverse stacks into lists to allow bidirectional navigation
        ArrayList<Node> planNodes = new ArrayList<>();
        ArrayList<String> oprStack = new ArrayList<>();

        while (!nodeStack.isEmpty()) {
            planNodes.add(nodeStack.pop());
        }
        while (!operationStack.isEmpty()) {
            oprStack.add(operationStack.pop());
        }

        // Create and manage the GUI
        new BottleVisualizerManager(planNodes, oprStack);
    }

    // Example usage
    public static void main(String[] args) {

    }
}
