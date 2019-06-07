package com.example.whatssizzlin;

public class CultureTag extends Tag {
    public CultureTag(String name, int tagId) {
        super(name, tagId);
    }

    @Override
    public int getType() {
        return CULTURE;
    }

    @Override
    public int getTagColor() {
        return R.color.colorCultureTag;
    }
}
