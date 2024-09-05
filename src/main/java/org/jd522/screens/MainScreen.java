package org.jd522.screens;

import org.jd522.Classes.Category;
import org.jd522.Constants.ColorConstants;
import org.jd522.DTOs.TaskDTO;
import org.jd522.Services.DataServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class MainScreen extends JFrame {
    private String url = "jdbc:sqlite:src/main/java/org/jd522/db/Tasks.db";
    private Connection connection = null;

    public MainScreen() {
        super("Main Screen");
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

        ArrayList<TaskDTO> currentTasks = DataServices.fetchAllTasks(connection);

        //Method to Add Components to the Screen By Calling Inner Class
        MainGui mainScreen = new MainGui(connection,currentTasks);
    }

    //Inner Class to Handle Gui Components
    private class MainGui{

        //Adding the Components when new Gui instance is created
        public MainGui(Connection connection, ArrayList<TaskDTO> currentTasks){
            AddComponentsToScreen(connection,currentTasks);
        }

        private void AddComponentsToScreen(Connection connection, ArrayList<TaskDTO> currentTasks) {
            JLabel title = new JLabel("Task Management");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Verdana", Font.BOLD, 22));
            title.setSize(250,30);
            title.setLocation(400,30);
            add(title);

            JTextPane searchBar = new JTextPane();
            searchBar.setSize(400,30);
            searchBar.setLocation(60,80);
            searchBar.setText("Search");
            searchBar.setFont(new Font("Verdana", Font.PLAIN, 18));
            searchBar.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            add(searchBar);

            JButton searchButton = new JButton("Search");
            searchButton.setSize(100,30);
            searchButton.setLocation(480,80);
            searchButton.setFont(new Font("Verdana", Font.BOLD, 16));
            searchButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            searchButton.setBackground(Color.decode(ColorConstants.ORANGE));
            searchButton.setForeground(Color.WHITE);
            add(searchButton);

            searchButton.addActionListener(e -> {
                String searchText = searchBar.getText();
                TaskDTO fetchedTask = DataServices.searchTaskByName(connection, searchText);
                if(fetchedTask != null){
                    SingleTaskScreen singleTaskScreen = new SingleTaskScreen(fetchedTask);
                    singleTaskScreen.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Task Not Found");
                }
            });


            createJTable(currentTasks);

            JButton updateTaskButton = new JButton("Update Task");
            updateTaskButton.setSize(200,50);
            updateTaskButton.setLocation(100, 460);
            updateTaskButton.setFont(new Font("Verdana", Font.BOLD, 16));
            updateTaskButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            updateTaskButton.setBackground(Color.decode(ColorConstants.ORANGE));
            updateTaskButton.setForeground(Color.WHITE);
            add(updateTaskButton);

            JButton addTaskButton = new JButton("Add Task");
            addTaskButton.setSize(200,50);
            addTaskButton.setLocation(330, 460);
            addTaskButton.setFont(new Font("Verdana", Font.BOLD, 16));
            addTaskButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            addTaskButton.setBackground(Color.decode(ColorConstants.GREEN));
            addTaskButton.setForeground(Color.WHITE);
            add(addTaskButton);

            addTaskButton.addActionListener(e -> {
                AddTaskScreen addTaskScreen = new AddTaskScreen();
                addTaskScreen.setVisible(true);
                dispose();
            });

            JLabel filterLabel = new JLabel("Search By Category");
            filterLabel.setForeground(Color.WHITE);
            filterLabel.setFont(new Font("Verdana", Font.BOLD, 16));
            filterLabel.setSize(200,50);
            filterLabel.setLocation(710, 120);
            add(filterLabel);

            JComboBox<String> comboBox = new JComboBox<>();
            comboBox.setSize(230,50);
            comboBox.setLocation(680, 180);
            comboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
            comboBox.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            comboBox.setBackground(Color.decode(ColorConstants.GREEN_GREY));
            comboBox.setForeground(Color.WHITE);
            add(comboBox);

            //getting the Categories from the Category enum and adding them to the ComboBox
            for(Category.CategoryValue category : Category.CategoryValue.values()) {
                comboBox.addItem(category.toString());
            }

            JButton filterButton = new JButton("Search Category");
            filterButton.setSize(200,50);
            filterButton.setLocation(695, 290);
            filterButton.setFont(new Font("Verdana", Font.BOLD, 16));
            filterButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            filterButton.setBackground(Color.decode(ColorConstants.ORANGE));
            filterButton.setForeground(Color.WHITE);
            add(filterButton);

            JButton exportButton = new JButton("Export CSV");
            exportButton.setSize(200,50);
            exportButton.setLocation(695, 350);
            exportButton.setFont(new Font("Verdana", Font.BOLD, 16));
            exportButton.setBorder(BorderFactory.createLineBorder(Color.decode(ColorConstants.BLACK)));
            exportButton.setBackground(Color.decode(ColorConstants.LIGHT_PURPLE));
            exportButton.setForeground(Color.WHITE);
            add(exportButton);
        }

        private void createJTable(ArrayList<TaskDTO> tasks) {
            String[] columnNames = {"ID","Title", "Description", "Category", "Status"};
            DefaultTableModel taskTableModel = new DefaultTableModel(columnNames, 0){

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for(TaskDTO task : tasks) {
                Object[] rowData ={
                    task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getCategory().getCategoryValue().name(),
                        task.getStatus()
                };
                taskTableModel.addRow(rowData);
            }

            JTable taskTable = new JTable(taskTableModel);
            JScrollPane scrollPane = new JScrollPane(taskTable);
            scrollPane.setLocation(60, 130);
            scrollPane.setSize(520, 300);
            add(scrollPane);
        }
    }
}
