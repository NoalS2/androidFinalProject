package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.GroceryListAdapter;
import com.example.finalproject.R;
import com.example.finalproject.models.GroceryItem;
import com.example.finalproject.viewmodels.GroceryItemViewModel;

public class GroceryListFragment extends Fragment {
    public GroceryListFragment() { super(R.layout.grocery_list_fragment); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GroceryItemViewModel viewModel = new ViewModelProvider(getActivity()).get(GroceryItemViewModel.class);
        ObservableArrayList groceryItems = viewModel.getItems();

        GroceryListAdapter adapter = new GroceryListAdapter(groceryItems);
        groceryItems.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemMoved(fromPosition, toPosition);
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                });
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.floatingActionButton).setOnClickListener((v) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container, CreateGroceryItemFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.finishedButton).setOnClickListener((v) -> {
            for (int i = 0; i < groceryItems.size(); i++) {
                View itemView = recyclerView.getChildAt(i);
                CheckBox checkBox = itemView.findViewById(R.id.checkBox);
                if (checkBox.isChecked()) {
                    viewModel.updatePurchased((GroceryItem) groceryItems.get(i));
                }
                viewModel.deletePurchased();
            }
        });
    }
}
