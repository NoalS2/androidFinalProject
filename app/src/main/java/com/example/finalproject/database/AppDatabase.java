package com.example.finalproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.finalproject.models.GroceryItem;

@Database(entities = {GroceryItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroceryItemsDao getGroceryItemsDao();
}
