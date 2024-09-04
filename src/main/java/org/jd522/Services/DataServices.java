package org.jd522.Services;

import org.jd522.Constants.Categories;
import org.jd522.Constants.TaskStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataServices {

    /**
     * @Author: WJ-Bos
     * @param url
     * @param connection
     *      This method will create a table if it doesn't exist
     *     This Method creates a Simple Schema for a Task
     *     Using Auto Increment for the ID
     * */
    public void createTableIfNotExist(String url, Connection connection){
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String createStatement = "CREATE TABLE IF NOT EXISTS tasks " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "task_title VARCHAR(255), " +
                    "task_description VARCHAR(255), " +
                    "task_category VARCHAR(50), " +
                    "task_status VARCHAR(255))";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void seedData(String url, Connection connection) {
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String insertStatement = "INSERT INTO tasks (task_title, task_description, task_category, task_status) " +
                    "VALUES ('Task 1', 'Task 1 description', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')";

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url
     * @param connection
     * @return
     *
     * This method will check if the table is empty
     * It does this by checking if there are any rows in the table
     */

    private boolean isTableEmpty(String url, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT COUNT(*) FROM tasks";
            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if table is empty: " + e.getMessage(), e);
        }
        return false;
    }
}
