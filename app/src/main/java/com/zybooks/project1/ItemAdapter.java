package com.zybooks.project1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText("Quantity: " + item.getQuantity());

        // Handle delete button click
        holder.deleteButton.setOnClickListener(v -> {
            // Delete the item from the database
            InventoryDatabase db = new InventoryDatabase(v.getContext());
            db.deleteItem(item.getName());

            // Remove the item from the list and notify the adapter
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        });

        // Check if inventory is zero and handle the SMS functionality
        if (item.getQuantity() == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#880808")); // Custom dark red color
            holder.itemName.setTextColor(Color.WHITE); // Change text color to white
            holder.itemQuantity.setTextColor(Color.WHITE); // Change text color to white

            // Start SMS permission activity to notify about the out-of-stock item
            Intent smsIntent = new Intent(holder.itemView.getContext(), SmsPermissionActivity.class);
            smsIntent.putExtra("itemName", item.getName()); // Pass item name for SMS
            holder.itemView.getContext().startActivity(smsIntent);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Reset color if not zero
            holder.itemName.setTextColor(Color.BLACK); // Change text color to black
            holder.itemQuantity.setTextColor(Color.BLACK); // Change text color to black
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQuantity;
        Button deleteButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
