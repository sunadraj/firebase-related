package com.example.sunadtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunadtask.viewholder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class MainActivity extends AppCompatActivity {

    Context context;
    ImageView lines;
    DrawerLayout drawerLayout;
    EditText textediting;
    ActionBarDrawerToggle toggle;
    private RecyclerView viewPager2, recyclerView;
    DatabaseReference databaseReference;
    NavigationView navigationView;

    FirebaseRecyclerOptions<CategoryItem> items;
    FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        lines = view.findViewById(R.id.lines);
        TextView name = view.findViewById(R.id.name);
        textediting=findViewById(R.id.textediting);

        textediting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textediting.setFocusable(true);
                textediting.setFocusableInTouchMode(true);
                textediting.requestFocus();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });
        viewPager2 = findViewById(R.id.viewpager);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.nav_view);

        recyclerView.setHasFixedSize(true);
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("CategoryBackground");
        } catch (Exception e) {
            e.getMessage();

        }
        items = new FirebaseRecyclerOptions.Builder<CategoryItem>().setQuery(databaseReference, CategoryItem.class).build();

        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(items) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull CategoryItem categoryItem) {

                Picasso.get().load(categoryItem.getImageLink()).into(categoryViewHolder.i1, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "couldn't load image", Toast.LENGTH_SHORT).show();
                    }
                });

                categoryViewHolder.t1.setText(categoryItem.getName());
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
                CategoryViewHolder holder = new CategoryViewHolder(view);
                return holder;
            }
        };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        viewPager2.setLayoutManager(layoutManager);

        List<Slider> sliders = new ArrayList<>();
        sliders.add(new Slider(R.drawable.image1));
        sliders.add(new Slider(R.drawable.image2));
        sliders.add(new Slider(R.drawable.image3));

        viewPager2.setAdapter(new SliderAdapter(sliders, viewPager2));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        try {
            adapter.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);

        lines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        Fragment fragment = null;
                        switch (id) {
                            case R.id.search:
                                //         fragment=new SearchFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.basket:
                                //       fragment=new BasketFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.favorite:
                                //     fragment=new FavFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.promo_code:
                                //   fragment=new PromoCodeFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.orders:
                                //   fragment=new OrderFragment();
                                loadFragment(fragment);
                                break;
                            default:
                                return true;
                        }
                        return true;
                    }
                });
            }

        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
       }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }

    }
