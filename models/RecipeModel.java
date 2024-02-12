package com.example.mealtime1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeModel {
    private String label;
    private String image;
    private String source;
    private float yield;
    private float calories;
    private float totalWeight;
    @SerializedName("images")
    @Expose()
    private RootObjectModel rootObjectModel;

    public RecipeModel() {
        new RecipeModel(label, image, source, yield, calories, totalWeight, rootObjectModel);
    }

    private RecipeModel(String label, String image, String source, float yield, float calories, float totalWeight, RootObjectModel rootObjectModel) {
        this.label = label;
        this.image = image;
        this.source = source;
        this.yield = yield;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.rootObjectModel = rootObjectModel;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public float getYield() {
        return yield;
    }

    public float getCalories() {
        return calories;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public RootObjectModel getRootObjectModel() {
        return rootObjectModel;
    }
}
