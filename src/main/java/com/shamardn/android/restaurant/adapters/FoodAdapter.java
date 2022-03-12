package com.shamardn.android.restaurant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shamardn.android.restaurant.R;
import com.shamardn.android.restaurant.data.FoodDomain;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    ArrayList<FoodDomain> foods = new ArrayList<>();

    public void setList(ArrayList<FoodDomain> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout, parent, false);

        return new FoodViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodDomain currentEntry = foods.get(position);
        holder.foodTitle.setText(currentEntry.getTitle());
        holder.foodDetails.setText(currentEntry.getDetails());
        holder.foodFees.setText(String.valueOf(currentEntry.getFees()));

        Glide.with(holder.itemView.getContext()).load(currentEntry.getImg()).into(holder.foodImg);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView foodTitle, foodDetails, foodFees;
        private ImageView foodImg;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.item_tv_title);
            foodDetails = itemView.findViewById(R.id.item_tv_details);
            foodFees = itemView.findViewById(R.id.item_tv_fees);
            foodImg = itemView.findViewById(R.id.item_iv_img);
        }
    }
}
