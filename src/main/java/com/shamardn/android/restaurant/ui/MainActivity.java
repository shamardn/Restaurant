package com.shamardn.android.restaurant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.shamardn.android.restaurant.R;
import com.shamardn.android.restaurant.adapters.CategoryAdapter;
import com.shamardn.android.restaurant.adapters.FoodAdapter;
import com.shamardn.android.restaurant.data.CategoryDomain;
import com.shamardn.android.restaurant.data.FoodDomain;
import com.shamardn.android.restaurant.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_view);

        openNavigationDrawer();
        handelingNavigationMenu();
        handelingMainMenu();
        initCategoryRecycler();
        initFoodRecycler();

    }
    private void initFoodRecycler(){
        RecyclerView recyclerView = findViewById(R.id.popular_recyclerView);

        ArrayList<FoodDomain> foods = new ArrayList<>();
        foods.add(new FoodDomain("Canalony Sandwich","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img9));
        foods.add(new FoodDomain("Burger","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img10));
        foods.add(new FoodDomain("Sides","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img11));
        foods.add(new FoodDomain("Chicken","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img12));
        foods.add(new FoodDomain("Pizza","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img13));
        foods.add(new FoodDomain("Beverages","Cheese - Tomato - cucmber - breed - beens ",25,R.drawable.img3));

        FoodAdapter adapter = new FoodAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter.setList(foods);
    }
   private void initCategoryRecycler(){
       RecyclerView recyclerView = findViewById(R.id.cat_recyclerView);

       ArrayList<CategoryDomain> categories = new ArrayList<>();
       categories.add(new CategoryDomain("Sandwich",R.drawable.img9));
       categories.add(new CategoryDomain("Burger",R.drawable.img10));
       categories.add(new CategoryDomain("Sides",R.drawable.img11));
       categories.add(new CategoryDomain("Chicken",R.drawable.img12));
       categories.add(new CategoryDomain("Pizza",R.drawable.img13));
       categories.add(new CategoryDomain("Beverages",R.drawable.img3));

       CategoryAdapter adapter = new CategoryAdapter();

       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       adapter.setList(categories);
    }
    private void openNavigationDrawer(){
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void handelingNavigationMenu(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.item2:
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.item3:
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.item4:
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void handelingMainMenu(){
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_search:
                        startActivity(new Intent(MainActivity.this,SearchActivity.class));
                        return true;
                }
                return false;
            }
        });
    }


}