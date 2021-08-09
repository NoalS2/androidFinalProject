package com.example.finalproject.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GroceryItem {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public boolean purchased;
}
