package com.m8.whatssizzlin.Model;

public class Cookbook {
    private String name, description, ingredients, step, comment, phoneNumber;

    public Cookbook() {
    }

    public Cookbook(String name, String description, String ingredients, String step, String comment, String phoneNumber) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.step = step;
        this.comment = comment;
        this.phoneNumber = phoneNumber;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
