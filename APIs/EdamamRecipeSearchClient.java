package com.example.mealtime1.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamRecipeSearchClient {
    @GET("/api/recipes/v2")
    Call<SearchRecipes> searchRecipes(@Query("type")String type,
                                      @Query("q")String query,
                                      @Query("app_id")String RecipeID,
                                      @Query("app_key")String RecipeKey);
}
