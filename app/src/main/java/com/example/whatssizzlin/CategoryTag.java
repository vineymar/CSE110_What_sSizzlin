package com.example.whatssizzlin;

public class CategoryTag extends Tag {
    public CategoryTag(String name, int tagId) {
        super(name, tagId);
    }

    @Override
    public int getType() {
        return CATEGORY;
    }

    @Override
    public int getTagColor() {
        return R.color.colorCategoryTag;
    }
}
