package com.example.finalproject.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.models.GroceryItem;

import java.util.ArrayList;
import java.util.Iterator;

public class GroceryItemViewModel extends AndroidViewModel {
    private AppDatabase database;
    private ObservableArrayList<GroceryItem> items = new ObservableArrayList<>();

    public GroceryItemViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, AppDatabase.class, "grocerydb").build();

        new Thread(() -> {
            ArrayList<GroceryItem> groceryItems = (ArrayList<GroceryItem>) database.getGroceryItemsDao().getAll();
            items.addAll(groceryItems);
        }).start();
    }

    public ObservableArrayList<GroceryItem> getItems() {
        return items;
    }

    public void deleteGroceryItem(GroceryItem item) {
        new Thread(() -> {
            database.getGroceryItemsDao().delete(item);

            items.remove(item);
        }).start();
    }

    public void saveGroceryItem(String category, String name) {
        new Thread(() -> {
            GroceryItem newItem = new GroceryItem();

            newItem.category = category;
            newItem.name = name;
            newItem.purchased = false;

            newItem.id = database.getGroceryItemsDao().insert(newItem);

            items.add(newItem);
        }).start();
    }

    public void updatePurchased(GroceryItem item) {
        new Thread(() -> {
            item.purchased = true;
            database.getGroceryItemsDao().update(item);
        }).start();
    }

    public void deletePurchased() {
        new Thread(() -> {

            Iterator<GroceryItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                GroceryItem current = iterator.next();
                if (current.purchased) {
                    database.getGroceryItemsDao().delete(current);

                    iterator.remove();
                }
            }
        }).start();
    }
}
