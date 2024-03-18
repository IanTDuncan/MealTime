package com.example.mealtime1;

import com.spoonacular.client.model.GenerateMealPlan200Response;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MealPlanningService {
    @GET("mealplanner/generate")
    Call<GenerateMealPlan200Response> generateMealPlan(
            @Query("timeFrame") String timeFrame,
            @Query("targetCalories") BigDecimal targetCalories,
            @Query("diet") String diet,
            @Query("exclude") String exclude,
            @Query("apiKey") String apiKey
    );
}
