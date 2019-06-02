package com.example.whatssizzlin;

public abstract class Tag {

    public static final int INGREDIENT = 0;
    public static final int CULTURE = 1;
    public static final int CATEGORY = 2;

    private String name;
    private int tagId;

    public Tag(String name, int tagId){
        this.name = name;
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public abstract int getType();

    public int getTagId(){ return tagId; }

    public abstract int getTagColor();



}
