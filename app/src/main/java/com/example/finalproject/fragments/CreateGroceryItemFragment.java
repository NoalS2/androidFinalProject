package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.viewmodels.GroceryItemViewModel;

public class CreateGroceryItemFragment extends Fragment {
    public CreateGroceryItemFragment() {
        super(R.layout.create_grocery_item_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.addButton).setOnClickListener((v) -> {
            Spinner spinner = view.findViewById(R.id.spinner);
            EditText itemName = view.findViewById(R.id.itemEditText);
            GroceryItemViewModel viewModel = new ViewModelProvider(getActivity()).get(GroceryItemViewModel.class);
            viewModel.saveGroceryItem(spinner.getSelectedItem().toString(), itemName.getText().toString());

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container, GroceryListFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
