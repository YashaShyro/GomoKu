package ticpackage;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
         DatabaseHelper.createTable();
        setTitle("Tictac2");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Tictac2", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start Game");
        JButton scoreboardButton = new JButton("Scoreboard");

        buttonPanel.add(startButton);
        buttonPanel.add(scoreboardButton);

        add(buttonPanel, BorderLayout.CENTER);

     
        // Start Game action
startButton.addActionListener(e -> {
    dispose(); // close main menu
    new LoginScreen(this).setVisible(true); // open login GUI first
});


        // Scoreboard action
        scoreboardButton.addActionListener(e -> {
            dispose(); // close main menu
            ScoreboardGUI scoreboard = new ScoreboardGUI(this);
            scoreboard.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}



    

