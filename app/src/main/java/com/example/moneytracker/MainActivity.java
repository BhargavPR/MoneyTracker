package com.example.moneytracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moneytracker.IncomeExpensesDisplayListFragment;
import com.example.moneytracker.R;
import com.example.moneytracker.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView navigationDrawerIcon;
    FloatingActionButton addIncomeExpensesBtn;
    DatabaseHelper helper;
    IncomeExpensesDisplayListFragment fragToday,fragWeek,fragMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllWidget();
        setUpTabMenu();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Bhargav","Current tab is " + position);
                displayData(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Override
    protected void onResume() {
        navigationDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        addIncomeExpensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Information.class);
                startActivity(intent);
            }
        });
        super.onResume();
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void setUpTabMenu(){

        ArrayList<MoneyParameter> IncomeExpensesList = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        IncomeExpensesDisplayListFragment fragToday = new IncomeExpensesDisplayListFragment(getApplicationContext(),IncomeExpensesList);
        IncomeExpensesDisplayListFragment fragWeek = new IncomeExpensesDisplayListFragment(getApplicationContext(),IncomeExpensesList);
        IncomeExpensesDisplayListFragment fragMonth = new IncomeExpensesDisplayListFragment(getApplicationContext(),IncomeExpensesList);
        viewPagerAdapter.AddFragment(fragToday,"Today");
        viewPagerAdapter.AddFragment(fragWeek,"Week");
        viewPagerAdapter.AddFragment(fragMonth,"Month");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void displayData(int position) {
        Cursor c = helper.getAllData();
        ArrayList<MoneyParameter> list = new ArrayList<>();
        if (c.getCount() == 0)
            Toast.makeText(MainActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
        else {
            c.moveToFirst();
            do {
                String title = c.getString(c.getColumnIndex("Title"));
                String description = c.getString(c.getColumnIndex("Description"));
                Double amount = c.getDouble(c.getColumnIndex("Amount"));
                int type = c.getInt(c.getColumnIndex("Type"));
                String date = c.getString(c.getColumnIndex("Date"));
                MoneyParameter moneyParameter = new MoneyParameter(title, description, amount, date, type);
                list.add(moneyParameter);

            } while (c.moveToNext());
            fragToday.updateData(list);
        }
    }
    public void getAllWidget(){
        helper = new DatabaseHelper(getApplicationContext());
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        toolbar = (Toolbar)findViewById(R.id.main_activity_toolbar);
        navigationDrawerIcon = (ImageView)findViewById(R.id.navigation_drawer_icon);
        drawer = (DrawerLayout)findViewById(R.id.main_activity_drawer_layout);
        addIncomeExpensesBtn = (FloatingActionButton)findViewById(R.id.add_income_expenses_btn);
    }
}
