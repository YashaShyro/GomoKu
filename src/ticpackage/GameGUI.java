package ticpackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JPanel {

  
private static final int BOARD_SIZE = 15;
private static final int PANEL_WIDTH = 600;
private static final int PANEL_HEIGHT = 600;
private static final int TILE_SIZE = PANEL_WIDTH / BOARD_SIZE;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE]; // 0 = empty, 1 = black, 2 = white
    private int currentPlayer = 1; // black starts

    private final Image background;
    private final JLabel turnLabel;
    private final String player1;
    private final String player2;
    
    
    public GameGUI(String player1, String player2, JLabel turnLabel) {
        this.player1 = player1;
        this.player2 = player2;
        this.turnLabel = turnLabel;
        updateTurnLabel();

        background = new ImageIcon("C:\\Users\\MICHAEL\\Documents\\NetBeansProjects\\Tictac2\\assets\\board.jpg").getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / TILE_SIZE;
                int col = e.getX() / TILE_SIZE;

                if (row < BOARD_SIZE && col < BOARD_SIZE && board[row][col] == 0) {
                    board[row][col] = currentPlayer;
                    repaint();

                    if (checkWin(row, col)) {
                        String winner = (currentPlayer == 1) ? player1 : player2;
                        JOptionPane.showMessageDialog(null, winner + " wins!");
                        DatabaseHelper.addWin(winner);  // Log win
                        resetBoard();
                    } else {
                        currentPlayer = (currentPlayer == 1) ? 2 : 1;
                        updateTurnLabel();
                    }
                }
            }
        });
    }

    private void updateTurnLabel() {
        String name = (currentPlayer == 1) ? player1 : player2;
        turnLabel.setText("Turn: " + name);
    }

    private boolean checkWin(int row, int col) {
        int player = board[row][col];
        int[][] directions = {
                {0, 1}, {1, 0}, {1, 1}, {1, -1}
        };

        for (int[] d : directions) {
            int count = 1;
            for (int step = 1; step < 5; step++) {
                int r = row + d[0] * step;
                int c = col + d[1] * step;
                if (r < 0 || r >= BOARD_SIZE || c < 0 || c >= BOARD_SIZE || board[r][c] != player) break;
                count++;
            }
            for (int step = 1; step < 5; step++) {
                int r = row - d[0] * step;
                int c = col - d[1] * step;
                if (r < 0 || r >= BOARD_SIZE || c < 0 || c >= BOARD_SIZE || board[r][c] != player) break;
                count++;
            }
            if (count >= 5) return true;
        }

        return false;
    }

    private void resetBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 1;
        updateTurnLabel();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE, this);
        // Remove or comment out this block inside paintComponent:
g.setColor(Color.BLACK);
for (int i = 0; i <= BOARD_SIZE; i++) {
    g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, PANEL_HEIGHT); // vertical lines
    g.drawLine(0, i * TILE_SIZE, PANEL_WIDTH, i * TILE_SIZE); // horizontal lines
}

setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        g.setColor(Color.BLACK);
        for (int i = 0; i < BOARD_SIZE; i++) {
            g.drawLine(0, i * TILE_SIZE, TILE_SIZE * (BOARD_SIZE - 1), i * TILE_SIZE);
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, TILE_SIZE * (BOARD_SIZE - 1));
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(col * TILE_SIZE + 5, row * TILE_SIZE + 5, 30, 30);
                } else if (board[row][col] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(col * TILE_SIZE + 5, row * TILE_SIZE + 5, 30, 30);
                }
            }
        }
    }

    // Show GUI (called after login)
    public static void createAndShowGUI(String player1, String player2) {
    JFrame frame = new JFrame("TicTacToe2");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel turnLabel = new JLabel("Turn: ");
    turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
    turnLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

    GameGUI gamePanel = new GameGUI(player1, player2, turnLabel);
    frame.setLayout(new BorderLayout());

    // Create a panel to hold turn label and back button side by side
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(turnLabel, BorderLayout.CENTER);

    JButton backButton = new JButton("Back to Main Menu");
    topPanel.add(backButton, BorderLayout.EAST);

    backButton.addActionListener(e -> {
        frame.dispose(); // Close game window
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    });

    frame.add(topPanel, BorderLayout.NORTH);
    frame.add(gamePanel, BorderLayout.CENTER);

    frame.setSize(BOARD_SIZE * TILE_SIZE + 15, BOARD_SIZE * TILE_SIZE + 80);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

    }
    
