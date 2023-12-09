package com.mycompany.n.queens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Eng Ossama Samir
 */

public class NQueens {
    public static int n;
    public static Solver solve;
    public static int currentBoardIndex;
    public static JFrame boardsFrame;
    public static JPanel cardPanel;

    public static void main(String[] args) {
        n = 6;
        solve = new Solver(n);

        JFrame mainFrame = new JFrame("N Queens Solver 👑");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        JButton chooseNButton = new JButton("Enter N");
        chooseNButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(mainFrame, "Enter the value of N (board size):");
                try {
                    int newN = Integer.parseInt(input);
                    if (newN > 0) {
                        n = newN;
                        solve = new Solver(n); // Create a new Solver with the new value of N
                        displayBoards();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Please enter a valid positive number.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number.");
                }
            }
        });

        mainFrame.add(chooseNButton, BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setSize(400, 100);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }

    private static void displayBoards() {
        currentBoardIndex = 1;
        if (boardsFrame != null) {
            boardsFrame.dispose();
        }
        boardsFrame = new JFrame("N Queens Solver 👑");
        boardsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cardPanel = new JPanel(new CardLayout());

        for (int i = 1; i <= 4; i++) {
            JPanel panelWithLabel = createBoardWithLabel(n, i);
            panelWithLabel.setBackground(Color.GRAY);
            Border newBorder = BorderFactory.createLineBorder(Color.YELLOW, 2);
            panelWithLabel.setBorder(newBorder);
            cardPanel.add(panelWithLabel, "board" + i);
        }

        boardsFrame.add(cardPanel);

        JPanel navigationPanel = new JPanel(new FlowLayout());

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentBoardIndex > 1) {
                    currentBoardIndex--;
                    CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                    cardLayout.show(cardPanel, "board" + currentBoardIndex);
                }
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentBoardIndex < 4) {
                    currentBoardIndex++;
                    CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                    cardLayout.show(cardPanel, "board" + currentBoardIndex);
                }
            }
        });

        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);

        boardsFrame.add(navigationPanel, BorderLayout.SOUTH);
        boardsFrame.pack();
        boardsFrame.setSize(800, 600);
        boardsFrame.setResizable(true);
        boardsFrame.setVisible(true);
    }

    private static JPanel createBoardWithLabel(int n, int boardNumber) {
        JPanel panelWithLabel = new JPanel(new BorderLayout());
        JPanel board = new JPanel(new GridLayout(n, n));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int[][] currentBoard = solve.getBoard(boardNumber);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JButton button = new JButton();
                if (currentBoard[i][j] == 1) {
                    if ((i + j) % 2 == 0) {
                        button.setBackground(Color.WHITE);
                    } else {
                        button.setBackground(Color.BLACK);
                    }
                    button.setText("Q");
                    button.setFont(new Font("Arial", Font.BOLD, 55));
                     button.setForeground(Color.YELLOW);
                } else {
                    button.setText("-");
                    if ((i + j) % 2 == 0) {
                        button.setBackground(Color.WHITE);
                    } else {
                        button.setBackground(Color.BLACK);
                    }
                }
                board.add(button);
            }
        }

        JLabel label = new JLabel("Thread #" + boardNumber, SwingConstants.CENTER);
        label.setForeground(Color.GREEN);
        label.setFont(new Font("Arial", Font.BOLD, 55));
        panelWithLabel.add(label, BorderLayout.NORTH);
        panelWithLabel.add(board, BorderLayout.CENTER);
        return panelWithLabel;
    }
}





