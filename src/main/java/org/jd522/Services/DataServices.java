package org.jd522.Services;

import org.jd522.Constants.Categories;
import org.jd522.Constants.TaskStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataServices {

    /**
     * @param url
     * @param connection This method will create a table if it doesn't exist
     *                   This Method creates a Simple Schema for a Task
     *                   Using Auto Increment for the ID
     * @Author: WJ-Bos
     */
    public static void createTableIfNotExist(String url, Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String createStatement = "CREATE TABLE IF NOT EXISTS tasks " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "task_title VARCHAR(255), " +
                    "task_description VARCHAR(255), " +
                    "task_category VARCHAR(50), " +
                    "task_status VARCHAR(255))";
            statement.executeUpdate(createStatement);
            statement.close();


            //Seed the Tasks Table if the Table is empty
            if (isTableEmpty(url, connection)) {
                seedData(url, connection);
            }
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void  seedData(String url, Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String insertStatement;

            insertStatement = "INSERT INTO tasks (task_title, task_description, task_category, task_status) VALUES " +
                    "('Laundry', 'Wash, dry, fold, and put away clothes', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Vacuum', 'Clean the floors', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Dinner', 'Make dinner', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Workout', 'Go for a run and do some strength exercises', '" + Categories.FITNESS.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Learn Java', 'Watch tutorials and practice programming', '" + Categories.LEARNING.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Read a book', 'Read a book about programming', '" + Categories.LEARNING.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Fix the bike', 'Fix the bike and take it for a ride', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Paint the room', 'Paint the bedroom', '" + Categories.WORK.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Clean the house', 'Clean the entire house', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Buy groceries', 'Buy groceries for the week', '" + Categories.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')";
            statement.executeUpdate(insertStatement);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url
     * @param connection
     * @return This method will check if the table is empty
     * It does this by checking if there are any rows in the table
     */

    private static boolean isTableEmpty(String url, Connection connection) {
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
