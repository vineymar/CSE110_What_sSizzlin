package com.example.whatssizzlin;

public class IngredientTag extends Tag {
    public IngredientTag(String name, int tagId) {
        super(name, tagId);
    }

    @Override
    public int getType() {
        return INGREDIENT;
    }

    @Override
    public int getTagColor() {
        return R.color.colorIngredientTag;
    }
}
