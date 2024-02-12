package com.example.mealtime1.APIs;

import com.example.mealtime1.models.RootObjectModel;
import com.google.gson.annotations.SerializedName;

public class SearchRecipes {
    private int from;
    private int to;
    private int count;
    @SerializedName("hits")
    private RootObjectModel[] foodRecipes;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }

    public RootObjectModel[] getFoodRecipes() {
        return foodRecipes;
    }

    public String toString() {
        return "Recipes{" + "Recipes=\t" + foodRecipes + "from = \t" + from
                + "to=\t" + to
                + "count=\t" + count + "" + "}";
    }
}
