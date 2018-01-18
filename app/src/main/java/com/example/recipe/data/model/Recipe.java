package com.example.recipe.data.model;

public class Recipe {

    private Integer mId;
    private String name, description;

    public Recipe() {
    }

    public Recipe(int mId, String name, String description) {
        this.mId = mId;
        this.name = name;
        this.description = description;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
