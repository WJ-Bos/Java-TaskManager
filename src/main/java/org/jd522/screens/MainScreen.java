package org.jd522.screens;

import org.jd522.Constants.ColorConstants;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen() {
        super("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode(ColorConstants.OFF_WHITE));
        
        AddComponentsToScreen();
    }

    private void AddComponentsToScreen() {
        JLabel title = new JLabel("Task Management");
        title.setForeground(Color.decode(ColorConstants.BLACK));
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setSize(200,30);
        title.setLocation(400,30);
        add(title);
    }
}
