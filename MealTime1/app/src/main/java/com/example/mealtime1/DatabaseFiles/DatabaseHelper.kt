package com.example.mealtime1.DatabaseFiles

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mealtime1.Meal


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        const val DATABASE_NAME = "MealTimeDB"
        const val DATABASE_VERSION = 1
    }

    // User Table
    private val createTableUser = """
    CREATE TABLE User (
        userID INTEGER PRIMARY KEY AUTOINCREMENT
    );
    """

    // Device Table
    private val createTableDevice = """
    CREATE TABLE Device (
        deviceID TEXT PRIMARY KEY
    );
    """

    // MealPlan Table
    private val createTableMealPlan  = """
    CREATE TABLE MealPlan  (
        mealPlanID INTEGER PRIMARY KEY AUTOINCREMENT,
        userID INTEGER,
        mealID INTEGER, 
        FOREIGN KEY (userID) REFERENCES User(userID),
        FOREIGN KEY (mealID) REFERENCES Results(mealID)
    );
    """

    // Results Table
    private val createTableResults = """
    CREATE TABLE Results (
        mealID INTEGER PRIMARY KEY AUTOINCREMENT,
        mealName TEXT,
        mealPic BLOB,
        mealNutritionLabel BLOB
    );
    """

    // ShoppingList Table
    private val createTableShoppingList  = """
    CREATE TABLE ShoppingList (
        listID INTEGER PRIMARY KEY AUTOINCREMENT,
        mealID INTEGER,
        mealPlanID INTEGER,
        IngredientName TEXT,
        Quantity INTEGER,
        Unit TEXT,
        PricePerUnit INTEGER,
        FOREIGN KEY (mealID) REFERENCES Results (mealID),
        FOREIGN KEY (mealPlanID) REFERENCES MealPlan(mealPlanID)
    );
    """


    fun hasResults(deviceId: String?): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM Results INNER JOIN MealPlan ON Results.mealID = MealPlan.mealID WHERE MealPlan.userID = (SELECT userID FROM Device WHERE deviceID = ?)"
        val cursor = db.rawQuery(query, arrayOf(deviceId))
        val hasResults = cursor.count > 0
        cursor.close()
        return hasResults
    }

    fun getSavedMeals(): List<Meal> {
        val meals = mutableListOf<Meal>()
        val db = this.readableDatabase
        val query = "SELECT * FROM Results"
        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mealName = cursor.getString(cursor.getColumnIndex("mealName"))
                val mealPic = cursor.getString(cursor.getColumnIndex("mealPic"))
                val mealID = cursor.getInt(cursor.getColumnIndex("mealID"))
                meals.add(Meal(mealName, mealPic, mealID))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return meals
    }

    fun insertMeal(meal: Meal) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("mealName", meal.name)
            put("mealPic", meal.imageUrl)
            put("mealID", meal.id)
        }
        db.insert("Results", null, values)
    }


    fun insertNutritionLabel(mealId: Int, nutritionLabel: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("mealNutritionLabel", nutritionLabel)
        }
        db.update(
            "Results",
            values,
            "mealID=?",
            arrayOf(mealId.toString())
        )
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTableDevice)
        db.execSQL(createTableMealPlan)
        db.execSQL(createTableResults)
        db.execSQL(createTableShoppingList)

        // Store the device ID when the database is first created
        val deviceId = DeviceIdManager.getDeviceId(context)
        val values = ContentValues().apply {
            put("deviceID", deviceId)
        }
        db.insert("Device", null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS User;")
        db.execSQL("DROP TABLE IF EXISTS Device;")
        db.execSQL("DROP TABLE IF EXISTS MealPlan;")
        db.execSQL("DROP TABLE IF EXISTS Results;")
        db.execSQL("DROP TABLE IF EXISTS ShoppingList;")
        onCreate(db)
    }
}