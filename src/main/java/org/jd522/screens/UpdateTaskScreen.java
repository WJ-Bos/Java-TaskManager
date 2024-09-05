package org.jd522.screens;

import org.jd522.Classes.Category;
import org.jd522.Constants.ColorConstants;
import org.jd522.Constants.TaskStatus;
import org.jd522.DTOs.TaskDTO;
import org.jd522.Services.DataServices;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class UpdateTaskScreen extends JFrame {

    private String url = "jdbc:sqlite:src/main/java/org/jd522/db/Tasks.db";
    private Connection connection = null;

    public UpdateTaskScreen(TaskDTO task) {

        super("Update Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode(ColorConstants.DARK_GREY));

//--------------------------------------------------------------------------------------------------

        //Connecting to SQLite
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to SQLite database");
            //Seeding and Table Creation
            DataServices.createTableIfNotExist(url, connection);
        }catch (Exception e){
            System.out.println("Could Not establish connection");
        }

//--------------------------------------------------------------------------------------------------

        UpdateTaskGui updateTaskGui = new UpdateTaskGui(task);

    }

    private class UpdateTaskGui {

        public UpdateTaskGui(TaskDTO task) {
            AddComponentsToScreen(task);
        }

        private void AddComponentsToScreen(TaskDTO task) {
            JLabel title = new JLabel("<HTML><U>Update Task</U></HTML>");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Verdana", Font.BOLD, 24));
            title.setSize(250,30);
            title.setLocation(400,150);
            add(title);

            JTextPane taskTitle = new JTextPane();
            taskTitle.setSize(400,30);
            taskTitle.setLocation(60,220);
            taskTitle.setText(task.getTitle());
            taskTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
            taskTitle.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            add(taskTitle);

            JTextPane taskDescription = new JTextPane();
            taskDescription.setSize(400,30);
            taskDescription.setLocation(480,220);
            taskDescription.setText(task.getDescription());
            taskDescription.setFont(new Font("Verdana", Font.PLAIN, 18));
            taskDescription.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            add(taskDescription);

//--------------------------------------------------------------------------------------------------

            JComboBox<String> taskCategory = new JComboBox<>();
            taskCategory.setSize(300,30);
            taskCategory.setLocation(320,280);
            taskCategory.setFont(new Font("Verdana", Font.PLAIN, 16));
            taskCategory.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            taskCategory.setBackground(Color.decode(ColorConstants.GREEN_GREY));
            taskCategory.setForeground(Color.WHITE);
            add(taskCategory);

            for(Category.CategoryValue category : Category.CategoryValue.values()) {
                taskCategory.addItem(category.toString());
            }

//--------------------------------------------------------------------------------------------------

            JComboBox<String> taskStatus = new JComboBox<>();
            taskStatus.setSize(300,30);
            taskStatus.setLocation(320,320);
            taskStatus.setFont(new Font("Verdana", Font.PLAIN, 16));
            taskStatus.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            taskStatus.setBackground(Color.decode(ColorConstants.GREEN_GREY));
            taskStatus.setForeground(Color.WHITE);
            add(taskStatus);

            for(TaskStatus status : TaskStatus.values()) {
                taskStatus.addItem(status.toString());
            }

//--------------------------------------------------------------------------------------------------

            JButton saveTask = new JButton("Save Task");
            saveTask.setSize(220,50);
            saveTask.setLocation(360,370);
            saveTask.setFont(new Font("Verdana", Font.BOLD, 16));
            saveTask.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            saveTask.setBackground(Color.decode(ColorConstants.GREEN_GREY));
            saveTask.setForeground(Color.WHITE);
            add(saveTask);

            saveTask.addActionListener(e -> {
                //TODO: Create a method to update the task in the database
            });


            JButton backButton = new JButton("Back");
            backButton.setSize(220,50);
            backButton.setLocation(360,440);
            backButton.setFont(new Font("Verdana", Font.BOLD, 16));
            backButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            backButton.setBackground(Color.decode(ColorConstants.OFF_RED));
            backButton.setForeground(Color.WHITE);
            add(backButton);

            backButton.addActionListener(e ->{
                new MainScreen().setVisible(true);
                dispose();
            });


        }
    }
}
