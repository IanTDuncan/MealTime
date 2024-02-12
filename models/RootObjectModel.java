package com.example.mealtime1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootObjectModel {
    @SerializedName("recipe")
    @Expose
    private RecipeModel recipeModel;

    public RootObjectModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    public RecipeModel getRecipeModel() {
        return recipeModel;
    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }
}
