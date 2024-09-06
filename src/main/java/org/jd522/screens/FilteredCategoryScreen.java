package org.jd522.screens;

import org.jd522.Constants.ColorConstants;
import org.jd522.DTOs.TaskDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FilteredCategoryScreen extends JFrame {

    public FilteredCategoryScreen(ArrayList<TaskDTO>filteredTasks) {
        super("Filtered Category Screen");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setSize(500, 400);
        getContentPane().setBackground(Color.decode(ColorConstants.DARK_GREY));

        DefaultTableModel filteredTableModel = new DefaultTableModel();
        filteredTableModel.addColumn("ID");
        filteredTableModel.addColumn("Title");
        filteredTableModel.addColumn("Description");
        filteredTableModel.addColumn("Category");
        filteredTableModel.addColumn("Status");

       for(TaskDTO task : filteredTasks) {
           filteredTableModel.addRow(new Object[]{
                   task.getId(),
                   task.getTitle(),
                   task.getDescription(),
                   task.getCategory().getCategoryValue().name(),
                   task.getStatus()
           });
       }

       JTable filteredTable = new JTable(filteredTableModel);
       filteredTable.setForeground(Color.BLACK);
       JScrollPane scrollPane = new JScrollPane(filteredTable);
       scrollPane.setBackground(Color.decode(ColorConstants.DARK_GREY));
       add(scrollPane, BorderLayout.CENTER);

       JButton backButton = new JButton("Back");
       backButton.setFont(new Font("Verdana", Font.BOLD, 16));
       backButton.setPreferredSize(new Dimension(100, 50));
       backButton.setBackground(Color.decode(ColorConstants.OFF_RED));
       backButton.setForeground(Color.WHITE);
       backButton.addActionListener(e -> {
           dispose();
       });

       JPanel buttonPanel = new JPanel();
       buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       buttonPanel.add(backButton);
       buttonPanel.setBackground(Color.decode(ColorConstants.DARK_GREY));
       add(buttonPanel, BorderLayout.SOUTH);
    }
}
