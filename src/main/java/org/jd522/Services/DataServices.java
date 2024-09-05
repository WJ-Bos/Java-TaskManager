package org.jd522.Services;

import org.jd522.Classes.Category;
import org.jd522.Constants.TaskStatus;
import org.jd522.DTOs.TaskDTO;

import java.sql.*;
import java.util.ArrayList;

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
                    "('Laundry', 'Wash, dry, fold, and put away clothes', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Vacuum', 'Clean the floors', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Dinner', 'Make dinner', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Workout', 'Go for a run and do some strength exercises', '" + Category.CategoryValue.FITNESS.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Learn Java', 'Watch tutorials and practice programming', '" + Category.CategoryValue.LEARNING.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Read a book', 'Read a book about programming', '" + Category.CategoryValue.LEARNING.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Fix the bike', 'Fix the bike and take it for a ride', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Paint the room', 'Paint the bedroom', '" + Category.CategoryValue.WORK.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Clean the house', 'Clean the entire house', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')," +
                    "('Buy groceries', 'Buy groceries for the week', '" + Category.CategoryValue.PERSONAL.name() + "', '" + TaskStatus.NOT_STARTED.name() + "')";
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


    /**
     * @param connection
     * @return
     * Fetches all tasks from the Database
     */

    public static ArrayList<TaskDTO> fetchAllTasks(Connection connection) {

        ArrayList<TaskDTO> tasks = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            String selectStatement = "SELECT * FROM tasks";
            ResultSet resultSet = statement.executeQuery(selectStatement);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String categoryString = resultSet.getString("task_category");
                String status = resultSet.getString("task_status");

                Category.CategoryValue categoryValue = Category.CategoryValue.valueOf(categoryString.toUpperCase());
                Category category = new Category(categoryValue);

                TaskDTO task = new TaskDTO(id, title, description, category, status);
                tasks.add(task);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }


    /**
     * @param connection
     * @param title
     * @param description
     * @param category
     *
     * Adds a Task to the Database
     */

    public static void addTaskToDatabase(Connection connection, String title, String description, String category) {
        try (Statement statement = connection.createStatement()) {
            String insertStatement = "INSERT INTO tasks (task_title, task_description, task_category, task_status) VALUES " +
                    "('" + title + "', '" + description + "', '" + category + "', '" + TaskStatus.NOT_STARTED.name() + "')";
            statement.executeUpdate(insertStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param connection
     * @param title
     * @return
     *
     * Searches for a Task in the Database using the Task name
     */

    public static TaskDTO searchTaskByName(Connection connection, String title){
        String selectStatement = "SELECT * FROM tasks WHERE task_title = ? ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)){
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String taskTitle = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String category = resultSet.getString("task_category");
                String status = resultSet.getString("task_status");

                //getting the Category Value from the String
                Category.CategoryValue categoryValue = Category.CategoryValue.valueOf(category);

                return new TaskDTO(id, taskTitle, description, new Category(categoryValue), status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //returning Null if there is No task found
        return null;
    }

    public static TaskDTO searchTaskById(Connection connection, int id){
        String selectStatement = "SELECT * FROM tasks WHERE id = ? ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String taskTitle = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String category = resultSet.getString("task_category");
                String status = resultSet.getString("task_status");

                //getting the Category Value from the String
                Category.CategoryValue categoryValue = Category.CategoryValue.valueOf(category);

                return new TaskDTO(id, taskTitle, description, new Category(categoryValue), status);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
