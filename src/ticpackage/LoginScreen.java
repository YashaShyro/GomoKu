package ticpackage;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen(MainMenu mainMenu) {
        setTitle("Player Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel label1 = new JLabel("Player 1 (Black):");
        JTextField player1Field = new JTextField();

        JLabel label2 = new JLabel("Player 2 (White):");
        JTextField player2Field = new JTextField();

        JButton startButton = new JButton("Start Game");

        add(label1); add(player1Field);
        add(label2); add(player2Field);
        add(new JLabel()); // spacer
        add(startButton);

        startButton.addActionListener(e -> {
            String p1 = player1Field.getText().trim();
            String p2 = player2Field.getText().trim();

            if (p1.isEmpty() || p2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both names.");
                return;
            }

            // Optionally insert to database if not exists
            DatabaseHelper.insertIfNotExists(p1);
            DatabaseHelper.insertIfNotExists(p2);

            dispose();
            GameGUI.createAndShowGUI(p1, p2); // Pass names to game
        });
    }
}
