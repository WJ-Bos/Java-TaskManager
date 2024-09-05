package org.jd522.DTOs;


import org.jd522.Classes.Category;

/**
 * @Author: WJ-Bos
 * Basic DTO (Data Transfer Object) class for a single Task
 * this is needed because we don't want to expose the database table
 * and we use this to map the data from the database to the DTO
 * the DTO is then used to pass the Data to the JTable
 */


public class TaskDTO {

    private int id;
    private String title;
    private String description;
    private Category category;
    private String status;


    public TaskDTO(int id, String title, String description, Category category, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
