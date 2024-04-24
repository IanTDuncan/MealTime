package com.example.mealtime1.DatabaseFiles

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.math.BigDecimal
import com.example.mealtime1.IngredientCost
import com.example.mealtime1.Meal



class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context = context

    companion object {
        const val DATABASE_NAME = "MealTimeDB"
        const val DATABASE_VERSION = 1
    }

    // Device Table
    private val createTableDevice = """
    CREATE TABLE Device (
        deviceID TEXT PRIMARY KEY
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
    private val createTableIngredient  = """
    CREATE TABLE Ingredient (
        mealID INTEGER,
        IngredientName TEXT,
        PricePerUnit REAL,
        FOREIGN KEY (mealID) REFERENCES Results (mealID)
    );
    """

    fun getMealIds(): IntArray {
        val mealIds = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT mealID FROM Results"
        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mealId = cursor.getInt(cursor.getColumnIndex("mealID"))
                mealIds.add(mealId)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mealIds.toIntArray()
    }


    fun hasResults(): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM Results LIMIT 1"
        val cursor = db.rawQuery(query, null)
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

    fun insertIngredient(mealId: Int, ingredientName: String, pricePerUnit: android.icu.math.BigDecimal) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("mealID", mealId)
            put("IngredientName", ingredientName)
            put("PricePerUnit", pricePerUnit.toDouble())
        }
        db.insert("Ingredient", null, values)
    }

    fun getIngredientsForMeals(mealIds: IntArray): List<IngredientCost> {
        val ingredients = mutableListOf<IngredientCost>()
        val db = this.readableDatabase
        mealIds.forEach { mealId ->
            val query = "SELECT * FROM Ingredient WHERE mealID = ?"
            val cursor: Cursor = db.rawQuery(query, arrayOf(mealId.toString()))
            if (cursor.moveToFirst()) {
                do {
                    val ingredientName = cursor.getString(cursor.getColumnIndex("IngredientName"))
                    val pricePerUnit = cursor.getDouble(cursor.getColumnIndex("PricePerUnit"))
                    val ingredientCost = IngredientCost(ingredientName, BigDecimal(pricePerUnit), mealId)

                    // Retrieve meal name associated with the meal ID
                    val mealName = getSavedMealName(mealId)
                    ingredientCost.mealName = mealName

                    ingredients.add(ingredientCost)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return ingredients
    }


    fun getSavedMealName(mealId: Int): String? {
        val db = this.readableDatabase
        val query = "SELECT mealName FROM Results WHERE mealID = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(mealId.toString()))
        var mealName: String? = null
        if (cursor.moveToFirst()) {
            mealName = cursor.getString(cursor.getColumnIndex("mealName"))
        }
        cursor.close()
        return mealName
    }


    fun deleteAllMeals() {
        val db = this.writableDatabase
        db.delete("Results", null, null)
    }

    fun deleteIngredient(mealId: Int, ingredientName: String) {
        val db = this.writableDatabase
        db.delete("Ingredient", "mealID=? AND IngredientName=?", arrayOf(mealId.toString(), ingredientName))
    }

    fun deleteAllIngredients() {
        val db = this.writableDatabase
        db.delete("Ingredient", null, null)
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableDevice)
        db.execSQL(createTableResults)
        db.execSQL(createTableIngredient)

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
        db.execSQL("DROP TABLE IF EXISTS Ingredient;")
        onCreate(db)
    }
}