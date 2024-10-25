package com.zybooks.project1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.EditText;

public class DatabaseViewActivity extends AppCompatActivity {

    private InventoryDatabase inventoryDatabase;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database
        inventoryDatabase = new InventoryDatabase(this);

        // Fetch data from the database
        itemList = getInventoryItems();

        // Set up the adapter with the database data
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        // Add button functionality for adding a new item
        EditText editTextItemName = findViewById(R.id.addItemName);
        EditText editTextItemQuantity = findViewById(R.id.addItemQuantity);
        Button buttonAddItem = findViewById(R.id.addDataButton);

        buttonAddItem.setOnClickListener(v -> {
            String itemName = editTextItemName.getText().toString();
            int itemQuantity = Integer.parseInt(editTextItemQuantity.getText().toString());

            // Insert the new item into the database
            inventoryDatabase.insertItem(itemName, itemQuantity);

            // Update the itemList and refresh the RecyclerView
            itemList.clear();
            itemList.addAll(getInventoryItems());
            itemAdapter.notifyDataSetChanged();

            // Clear the input fields after adding the item
            editTextItemName.setText("");
            editTextItemQuantity.setText("");
        });
    }

    // Method to fetch inventory data from the database
    private List<Item> getInventoryItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = inventoryDatabase.getReadableDatabase();

        String[] projection = {
                InventoryDatabase.InventoryData.COLUMN_ITEM_NAME,
                InventoryDatabase.InventoryData.COLUMN_ITEM_QUANTITY
        };

        Cursor cursor = db.query(
                InventoryDatabase.InventoryData.TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryDatabase.InventoryData.COLUMN_ITEM_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryDatabase.InventoryData.COLUMN_ITEM_QUANTITY));

            items.add(new Item(name, quantity));
        }
        cursor.close();
        return items;
    }
}