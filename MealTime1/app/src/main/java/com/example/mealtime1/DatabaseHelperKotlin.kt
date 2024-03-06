package com.example.mealtime1
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MealTimeDB"
        const val DATABASE_VERSION = 1
    }

    // User Table
// healthConcerns contains allergies, abnormalities, health problems...
    private val createTableUser = """
    CREATE TABLE User (
        userID INTEGER PRIMARY KEY AUTOINCREMENT,
        username TEXT NOT NULL,
        password TEXT NOT NULL,
        email TEXT NOT NULL,
        age INTEGER,
        gender TEXT,
        height REAL,
        weight REAL, 
        activityLevel TEXT,
        healthConcerns TEXT NOT NULL
    );
"""

    // Ingredients Table - store all the nutrition facts?
    private val createTableIngredients = """
    CREATE TABLE Ingredients (
        ingredientID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        calories REAL NOT NULL,
        protein REAL NOT NULL,
        carbs REAL NOT NULL,
        fats REAL NOT NULL,
        category_id INTEGER,
        FOREIGN KEY (category_id) REFERENCES FoodCategories(categoryId)
    );
"""

    // GroceryList Table
    private val createTableGroceryList = """
    CREATE TABLE GroceryList (
        groceryListId INTEGER PRIMARY KEY AUTOINCREMENT,
        userId INTEGER,
        ingredientId INTEGER,
        quantity INTEGER,
        FOREIGN KEY (userId) REFERENCES User(userId),
        FOREIGN KEY (ingredientId) REFERENCES Ingredients(ingredientId)
    );
"""

    // MealRecipe Table
    private val createTableMealRecipe = """
    CREATE TABLE MealRecipe (
        mealId INTEGER PRIMARY KEY AUTOINCREMENT,
        userId INTEGER,  
        name TEXT,
        description TEXT,
        calories REAL,
        protein REAL,
        carbs REAL,
        fats REAL,
        difficultyLevel TEXT,
        healthConcerns TEXT,
        quantity TEXT,
        instructions TEXT,
        FOREIGN KEY (userId) REFERENCES User(userId)
    );
"""

    // MealIngredient Table
    private val createTableMealIngredient = """
CREATE TABLE MealIngredient (
    mealId INTEGER,
    ingredientId INTEGER,
    PRIMARY KEY (mealId, ingredientId),
    FOREIGN KEY (mealId) REFERENCES MealRecipe(mealId),
    FOREIGN KEY (ingredientId) REFERENCES Ingredients(ingredientId)
);
"""

    // DailyIntake Table
    private val createTableDailyIntake = """
    CREATE TABLE DailyIntake (
        dailyIntakeId INTEGER PRIMARY KEY AUTOINCREMENT,
        userId INTEGER NOT NULL,
        mealId INTEGER NOT NULL,
        totalCalories REAL,
        totalProtein REAL,
        totalCarbs REAL,
        totalFats REAL,
        dayTime DATE NOT NULL,
        FOREIGN KEY (userId) REFERENCES User(userId),
        FOREIGN KEY (mealId) REFERENCES Meal(mealId)
    );
"""

    // LogInHistory Table
    private val createTableLogInHistory = """
    CREATE TABLE LogInHistory (
        logInId INTEGER PRIMARY KEY AUTOINCREMENT,
        userId INTEGER NOT NULL,
        logInTime DATE NOT NULL,
        FOREIGN KEY (userId) REFERENCES User(userId)
    );
"""

    // FoodCategories Table
    private val createTableFoodCategories = """
    CREATE TABLE FoodCategories (
        categoryId INTEGER PRIMARY KEY,
        categoryName TEXT
    );
"""

    // UserPreferences Table - do we want to keep it?
    private val createTableUserPreferences = """
    CREATE TABLE UserPreferences (
        preferenceId INTEGER PRIMARY KEY AUTOINCREMENT,
        userId INTEGER,
        preferredCalories REAL,
        preferredProtein REAL,
        preferredCarbs REAL,
        preferredFats REAL,
        FOREIGN KEY (userId) REFERENCES User(userId)
    );
"""


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTableIngredients)
        db.execSQL(createTableGroceryList)
        db.execSQL(createTableDailyIntake)
        db.execSQL(createTableLogInHistory)
        db.execSQL(createTableFoodCategories)
        db.execSQL(createTableUserPreferences)
        db.execSQL(createTableMealRecipe)
        db.execSQL(createTableMealIngredient)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS User;")
        db.execSQL("DROP TABLE IF EXISTS Ingredients;")
        db.execSQL("DROP TABLE IF EXISTS GroceryList;")
        db.execSQL("DROP TABLE IF EXISTS DailyIntake;")
        db.execSQL("DROP TABLE IF EXISTS LogInHistory;")
        db.execSQL("DROP TABLE IF EXISTS FoodCategories;")
        db.execSQL("DROP TABLE IF EXISTS UserPreferences;")
        db.execSQL("DROP TABLE IF EXISTS MealRecipe;")
        db.execSQL("DROP TABLE IF EXISTS MealIngredient")
        onCreate(db)
    }
}