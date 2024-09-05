package org.jd522.screens;

import org.jd522.Constants.ColorConstants;
import org.jd522.DTOs.TaskDTO;

import javax.swing.*;
import java.awt.*;

public class SingleTaskScreen extends JFrame {

    public SingleTaskScreen(TaskDTO task) {
        super("Single Task Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(400, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode(ColorConstants.DARK_GREY));

        SingleTaskGui singleTaskScreen = new SingleTaskGui(task);
    }

    private class SingleTaskGui{


        public SingleTaskGui(TaskDTO task) {
            createSingleTaskGui(task);
        }

        public void createSingleTaskGui(TaskDTO task) {
            JLabel title = new JLabel(task.getTitle());
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Verdana", Font.BOLD, 22));
            title.setSize(250,30);
            title.setLocation(400,30);
            add(title);

            JTextField description = new JTextField(task.getDescription());
            description.setSize(300,30);
            description.setLocation(60,80);
            add(description);

            JLabel category = new JLabel(task.getCategory().getCategoryValue().name());
            category.setForeground(Color.WHITE);
            category.setFont(new Font("Verdana", Font.BOLD, 18));
            category.setSize(250,30);
            category.setLocation(400,80);
            add(category);

            JLabel status = new JLabel(task.getStatus().toString());
            status.setForeground(Color.WHITE);
            status.setFont(new Font("Verdana", Font.BOLD, 18));
            status.setSize(250,30);
            status.setLocation(400,120);
            add(status);

            JButton backButton = new JButton("Back");
            backButton.setSize(100,50);
            backButton.setLocation(100, 460);
            backButton.setFont(new Font("Verdana", Font.BOLD, 16));
            backButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            backButton.setBackground(Color.decode(ColorConstants.OFF_RED));
            backButton.setForeground(Color.WHITE);
            add(backButton);

            backButton.addActionListener(e -> {
                dispose();
                new MainScreen();
            });
        }
    }
}
