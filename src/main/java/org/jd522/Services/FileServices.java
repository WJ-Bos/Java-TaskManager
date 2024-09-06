package org.jd522.Services;

import org.jd522.DTOs.TaskDTO;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileServices {

    public static void exportAsCSV(ArrayList<TaskDTO> tasks) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose location to export file to.");

        fileChooser.setSelectedFile(new java.io.File("Tasks.txt"));
        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection == JFileChooser.APPROVE_OPTION){
            Path filepath =fileChooser.getSelectedFile().toPath();
            saveTaskToCSV(filepath, tasks);
        }
    }


    private static void saveTaskToCSV(Path filepath, ArrayList<TaskDTO> tasks) {
        List<String> lines = new ArrayList<>();

        lines.add("ID,Title,Description,Category,Status");

        for (TaskDTO task : tasks){
            lines.add(task.getId()+","+
                    task.getTitle()+","+
                    task.getDescription()+","+
                    task.getCategory().getCategoryValue().name()+","+
                    task.getStatus());
        }
        try{
            Files.write(filepath,lines);
            JOptionPane.showMessageDialog(null, "Tasks exported successfully");
        }catch (IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to export tasks");
        }
    }
}

