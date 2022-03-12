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
import com.shamardn.android.restaurant.data.CategoryDomain;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {
    ArrayList<CategoryDomain> categories = new ArrayList<>();

    public void setList(ArrayList<CategoryDomain> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout, parent, false);

        return new CatViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CategoryDomain currentEntry = categories.get(position);

        holder.tv_categoryName.setText(currentEntry.getCategoryName());

        Glide.with(holder.itemView.getContext()).load(currentEntry.getCategoryImage()).into(holder.img_category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_categoryName;
        private ImageView img_category;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_categoryName = itemView.findViewById(R.id.cat_tv_name);
            img_category = itemView.findViewById(R.id.cat_img);
        }
    }
}
