package org.jd522.screens;

import org.jd522.Classes.Category;
import org.jd522.Constants.ColorConstants;
import org.jd522.Services.DataServices;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class AddTaskScreen extends JFrame {

    private String url = "jdbc:sqlite:src/main/java/org/jd522/db/Tasks.db";
    private Connection connection = null;

    public AddTaskScreen() {
        super("Add Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode(ColorConstants.DARK_GREY));

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

        //Adding components to the screen when Instance is Created
        addTaskGui addTaskScreen = new addTaskGui();

    }

    private class addTaskGui {

        public addTaskGui() {
            AddComponentsToScreen();
        }

        private void AddComponentsToScreen() {
            JLabel title = new JLabel("<HTML><U>Add a Task</U></HTML>");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Verdana", Font.BOLD, 24));
            title.setSize(250,30);
            title.setLocation(400,150);
            add(title);

            JTextPane taskTitle = new JTextPane();
            taskTitle.setSize(400,30);
            taskTitle.setLocation(60,220);
            taskTitle.setText("Task Title");
            taskTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
            taskTitle.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            add(taskTitle);

            JTextPane taskDescription = new JTextPane();
            taskDescription.setSize(400,30);
            taskDescription.setLocation(480,220);
            taskDescription.setText("Task Description");
            taskDescription.setFont(new Font("Verdana", Font.PLAIN, 18));
            taskDescription.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            add(taskDescription);

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

            JButton addTask = new JButton("Add Task");
            addTask.setSize(220,50);
            addTask.setLocation(360,340);
            addTask.setFont(new Font("Verdana", Font.BOLD, 16));
            addTask.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            addTask.setBackground(Color.decode(ColorConstants.AQUA));
            addTask.setForeground(Color.WHITE);
            add(addTask);

            addTask.addActionListener(e ->{
                DataServices.addTaskToDatabase(connection,
                        taskTitle.getText(),
                        taskDescription.getText(),
                        taskCategory.getSelectedItem().toString());

                dispose();
                new MainScreen().setVisible(true);

            });
        }
    }
}
