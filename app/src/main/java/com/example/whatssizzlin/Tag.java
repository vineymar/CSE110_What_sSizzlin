package com.example.whatssizzlin;

public class Tag {

    public static final int INGREDIENT = 0;
    public static final int CULTURE = 1;
    public static final int CATEGORY = 2;

    private String name;
    private int type;

    public Tag(String name, int type ){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }


}
