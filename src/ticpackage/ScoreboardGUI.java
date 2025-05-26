package ticpackage;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ScoreboardGUI extends JFrame {

    private final MainMenu MainMenu;

    public ScoreboardGUI(MainMenu mainMenu) {
        this.MainMenu = mainMenu;

        setTitle("Scoreboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try {
            ResultSet rs = DatabaseHelper.getAllPlayers();
            while (rs.next()) {
                area.append(rs.getString("username") + ": " + rs.getInt("wins") + " wins\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        // 🔘 Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton renameButton = new JButton("Change Name");
        JButton deleteButton = new JButton("Delete Player");
        JButton backButton = new JButton("Back to Menu");

        // ✏️ Change name
        renameButton.addActionListener(e -> {
            String oldName = JOptionPane.showInputDialog("Enter current name:");
            String newName = JOptionPane.showInputDialog("Enter new name:");
            if (oldName != null && newName != null && !oldName.isEmpty() && !newName.isEmpty()) {
                DatabaseHelper.updateUsername(oldName, newName);
                JOptionPane.showMessageDialog(this, "Name updated.");
                dispose();
                new ScoreboardGUI(mainMenu).setVisible(true); // refresh
            }
        });

        // ❌ Delete player
        deleteButton.addActionListener(e -> {
            String nameToDelete = JOptionPane.showInputDialog("Enter name to delete:");
            if (nameToDelete != null && !nameToDelete.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + nameToDelete + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    DatabaseHelper.deletePlayer(nameToDelete);
                    JOptionPane.showMessageDialog(this, "Player deleted.");
                    dispose();
                    new ScoreboardGUI(mainMenu).setVisible(true); // refresh
                }
            }
        });

        // 🔙 Back
        backButton.addActionListener(e -> {
            dispose();
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });

        // Add all buttons
        buttonPanel.add(renameButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
