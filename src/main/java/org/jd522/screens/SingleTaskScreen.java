package org.jd522.screens;

import org.jd522.Constants.ColorConstants;
import org.jd522.DTOs.TaskDTO;

import javax.swing.*;
import java.awt.*;

public class SingleTaskScreen extends JFrame {

    public SingleTaskScreen(TaskDTO task) {
        super("Single Task Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode(ColorConstants.DARK_GREY));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Title
        JLabel title = new JLabel(task.getTitle());
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        // Description
        JTextArea description = new JTextArea(task.getDescription());
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBackground(Color.DARK_GRAY);
        description.setForeground(Color.WHITE);
        description.setFont(new Font("Verdana", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(description);
        scrollPane.setPreferredSize(new Dimension(400, 60));
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);

        // Category
        JLabel category = new JLabel(task.getCategory().getCategoryValue().name());
        category.setForeground(Color.WHITE);
        category.setFont(new Font("Verdana", Font.BOLD, 18));
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(category, gbc);

        // Status
        JLabel status = new JLabel(task.getStatus());
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Verdana", Font.BOLD, 18));
        gbc.gridy = 3;
        add(status, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Verdana", Font.BOLD, 16));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setBackground(Color.decode(ColorConstants.OFF_RED));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(150, 50));
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(backButton, gbc);

        backButton.addActionListener(e -> {
            dispose();
            new MainScreen(); // Adjust to your actual main screen class
        });

        setVisible(true);
    }
}
