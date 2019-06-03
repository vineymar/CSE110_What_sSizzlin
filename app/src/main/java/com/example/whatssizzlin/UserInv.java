package com.example.whatssizzlin;

import com.example.whatssizzlin.IngredientTag;
import com.example.whatssizzlin.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class UserInv {
    private UserSingleton user;
    private List<IngredientTag> inventory;
    public UserInv(UserSingleton user){
        this.user = user;
        populateInventoryFromDatabase();
    }

    public void addToInventory(IngredientTag tag){
        inventory.add(tag);
        pushInventoryToDatabase();
    }

    public List<IngredientTag> getInventory() {
        return inventory;
    }

    public void setInventory(List<IngredientTag> inventory) {
        this.inventory = inventory;
        pushInventoryToDatabase();
    }

    private void populateInventoryFromDatabase(){
        if(user.getId() == null) inventory =  new ArrayList<IngredientTag>();
        //TODO implement database reading inventory stuff
        inventory = new ArrayList<IngredientTag>();
    }

    private void pushInventoryToDatabase(){
        //TODO implement database writing inventory stuff
        return;
    }
}
