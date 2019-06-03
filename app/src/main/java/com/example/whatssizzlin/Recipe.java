package com.example.whatssizzlin;


import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Recipe  {
    public String author;
    public String description;
    public List<String> difficulty = new ArrayList<>();
    public List<String> ingredients = new ArrayList<>();
    public List<String> method = new ArrayList<>();
    public String name;
    public Map<String, String> nutrition;
    public String servings;
    public String img_url;
    public List<Map<String, Map<String, String>>> time = new ArrayList<>();
    public String id;



    public Recipe(String author,
                  String description,
                  List<String> difficulty,
                  List<String> ingredients,
                  List<String> method,
                  String name,
                  Map<String, String> nutrition,
                  String servings,
                  List<Map<String, Map<String, String>>> time){

        this.author = author;
        this.description = description;
        this.difficulty = new ArrayList<String>();
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.method = method;
        this.name = name;
        this.nutrition = nutrition;
        this.servings = servings;
        this.time = time;

    }
    public Recipe(){

    };

}

