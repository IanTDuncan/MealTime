package com.example.mealtime1;

import com.spoonacular.client.model.AddToMealPlanRequest;
import com.spoonacular.client.model.DeleteFromMealPlanRequest;
import com.spoonacular.client.model.GenerateMealPlan200Response;
import com.spoonacular.client.model.GenerateShoppingList200Response;
import com.spoonacular.client.model.GenerateShoppingListRequest;
import com.spoonacular.client.model.GetShoppingList200Response;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MealPlanningService {
    @GET("mealplanner/generate")
    Call<GenerateMealPlan200Response> generateMealPlan(
            @Query("timeFrame") String timeFrame,
            @Query("targetCalories") BigDecimal targetCalories,
            @Query("diet") String diet,
            @Query("exclude") String exclude,
            @Query("apiKey") String apiKey
    );

    @POST("mealplanner/{username}/shopping-list/items")
    Call<GenerateShoppingList200Response> addToShoppingList(
            @Query("username") String username,
            @Query("hash") String hash,
            @Body AddToMealPlanRequest addToMealPlanRequest,
            @Query("apiKey") String apiKey
    );

    @POST("/mealplanner/{username}/shopping-list/{start-date}/{end-date}")
    Call<GenerateShoppingList200Response> generateShoppingList(
            @Query("username") String username,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("hash") String hash,
            @Body GenerateShoppingListRequest generateShoppingList,
            @Query("apiKey") String apiKey
    );

    @DELETE("/mealplanner/{username}/shopping-list/items/{id}")
    Call<Object> deleteFromShoppingList (
            @Query("username") String username,
            @Query("Id") Integer id,
            @Query("hash") String hash,
            @Body DeleteFromMealPlanRequest deleteFromMealPlanRequest,
            @Query("apiKey") String apiKey
    );

    @GET("/mealplanner/{username}/shopping-list")
    Call<GetShoppingList200Response> getShoppingList (
            @Query("username") String username,
            @Query("hash") String hash,
            @Query("apiKey") String apiKey
    );

}
