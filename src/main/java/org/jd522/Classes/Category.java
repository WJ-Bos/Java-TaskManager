package org.jd522.Classes;

public class Category {

    private CategoryValue categoryValue;

    public Category(CategoryValue categoryValue){
        this.categoryValue = categoryValue;
    }

    public CategoryValue getCategoryValue(){
        return categoryValue;
    }


    public enum CategoryValue{
        WORK("Work"),
        PERSONAL("Personal"),
        LEARNING("Learning"),
        FITNESS("Fitness"),
        OTHER("Other");

        private final String label;

        CategoryValue(String label){
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
    }

}
