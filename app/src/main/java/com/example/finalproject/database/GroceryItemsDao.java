package com.example.finalproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.models.GroceryItem;

import java.util.List;

@Dao
public interface GroceryItemsDao {
    @Insert
    public long insert(GroceryItem item);

    @Query("SELECT * FROM groceryitem")
    public List<GroceryItem> getAll();

    @Update
    public void update(GroceryItem item);

    @Delete
    public void delete(GroceryItem item);
}
