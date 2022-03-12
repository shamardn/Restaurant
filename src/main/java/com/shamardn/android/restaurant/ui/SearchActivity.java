package com.shamardn.android.restaurant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.shamardn.android.restaurant.R;
import com.shamardn.android.restaurant.adapters.FoodAdapter;
import com.shamardn.android.restaurant.data.FoodDomain;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initFoodRecycler();
    }
    private void initFoodRecycler(){
        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);

        ArrayList<FoodDomain> foods = new ArrayList<>();
        foods.add(new FoodDomain("Canalony Sandwich","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img9));
        foods.add(new FoodDomain("Burger","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img10));
        foods.add(new FoodDomain("Sides","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img11));
        foods.add(new FoodDomain("Chicken","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img12));
        foods.add(new FoodDomain("Pizza","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img13));
        foods.add(new FoodDomain("Beverages","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img3));

        FoodAdapter adapter = new FoodAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter.setList(foods);
    }
}