
package com.example.moneytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.IncomeExpensesDisplayListFragment;
import com.example.moneytracker.R;
import com.example.moneytracker.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {

    Toolbar toolbar;
    Toolbar mDeleteItemToolbar;
    TextView mBalance;
    DrawerLayout drawer;
    TabLayout tabLayout;
    ViewPager viewPager;
    NavigationView navigationView;
    ImageView navigationDrawerIcon;
    FloatingActionButton addIncomeExpensesBtn;
    DatabaseHelper helper;
    DisplayIncomeExpensesData printer;
    IncomeExpensesDisplayListFragment fragToday, fragWeek, fragMonth;
    int sortingTypeFlag;
    int sortingType, mItemCount;
    boolean mLongClickEnable;
    int currentTab;
    ArrayList<MoneyParameter> selectedItem;
    ArrayList<View> selectedItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllWidget();
        setUpTabMenu();
        setUpToolbar();
        resetRecyclerView();
        sortingType = 1;
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        resetRecyclerView();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset >= 0.001)
                    resetRecyclerView();
                if (positionOffset == 0.0) {
                    Log.d("Bhargav1", "value of " + positionOffset);
                    displayData(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        mDeleteItemToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRecyclerView();
            }
        });
        mDeleteItemToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete_multiple_item:
                        Log.d("Bhargav", "Delete icon clicked");
                        deleteDataAtMultipleRow();
                        return true;
                    default:
                        return false;
                }
            }
        });
        addIncomeExpensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Information.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.sort:
                        Log.d("Bhargav", "Sort icon clicked");
                        sortingIconClickListener();
                        return true;
                    case R.id.search:
                        startSearchActivity();
                    default:
                        return false;
                }
            }
        });
        super.onResume();
    }

    public void deleteDataAtMultipleRow() {
        for (int i = 0; i < selectedItem.size(); i++) {
            helper.deleteInformation(selectedItem.get(i).Id);
        }
        resetRecyclerView();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            if (mDeleteItemToolbar.getVisibility() == View.VISIBLE)
                resetRecyclerView();
            else
                super.onBackPressed();
        }
    }


    public void setUpTabMenu() {

        ArrayList<MoneyParameter> IncomeExpensesList = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragToday = new IncomeExpensesDisplayListFragment(getApplicationContext(), IncomeExpensesList, this);
        fragWeek = new IncomeExpensesDisplayListFragment(getApplicationContext(), IncomeExpensesList, this);
        fragMonth = new IncomeExpensesDisplayListFragment(getApplicationContext(), IncomeExpensesList, this);
        viewPagerAdapter.AddFragment(fragToday, "Today");
        viewPagerAdapter.AddFragment(fragWeek, "Week");
        viewPagerAdapter.AddFragment(fragMonth, "Month");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        //select the 0th index tab
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
    }

    public void displayData(int position) {
        Double MyBalance = 0.00;
        if (position == 0)
            MyBalance = printer.getIncomeExpensesListOfToday(fragToday);
        else {
            if (position == 2)
                MyBalance = printer.getIncomeExpensesListOfMonth(fragMonth);
            else
                MyBalance = printer.getIncomeExpensesListOfWeek(fragWeek);
        }
        MyBalance = round(MyBalance, 2);
        String MyBalanceToText = Double.toString(MyBalance);
        if (MyBalanceToText.charAt(MyBalanceToText.length() - 2) == '.') {
            MyBalanceToText = MyBalanceToText + "0";
        }
        mBalance.setText(MyBalanceToText);

    }

    public void setUpToolbar() {
        toolbar.setTitle("Home");
        toolbar.setNavigationIcon(R.drawable.navigation_drawer_icon);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        mDeleteItemToolbar.inflateMenu(R.menu.delete_item_toolbar_menu);
        mDeleteItemToolbar.setNavigationIcon(R.drawable.back_icon_24dp);
    }

    public void sortingIconClickListener() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.sorting_type_selection_dialog, null);
        alertDialog.setView(view);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.sorting_type);
        alertDialog.show();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("Bhargav", "You Clicked " + i);
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void getAllWidget() {
        mDeleteItemToolbar = (Toolbar) findViewById(R.id.delete_item_toolbar);
        printer = new DisplayIncomeExpensesData(getApplicationContext());
        helper = new DatabaseHelper(getApplicationContext());
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        addIncomeExpensesBtn = (FloatingActionButton) findViewById(R.id.add_income_expenses_btn);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mBalance = (TextView) findViewById(R.id.total_balance);
        sortingType = 0;
        selectedItem = new ArrayList<>();
        selectedItemView = new ArrayList<>();
        currentTab = 0;
    }

    public void resetRecyclerView() {
        mDeleteItemToolbar.setVisibility(View.GONE);
        mItemCount = 0;
        mLongClickEnable = false;
        for (int i = 0; i < selectedItem.size(); i++) {
            MoneyParameter x = selectedItem.get(i);
            Log.d("Bhargav", x.Title);
            x.setSelected(false);
            View v = selectedItemView.get(i);
        }
        selectedItem.clear();
        selectedItemView.clear();
    }


    @Override
    public void recyclerViewOnClicked(View v, int position, MoneyParameter mData) {
        if (mLongClickEnable == true && mItemCount != 0) {
            if (mData.isSelected() == false) {
                mData.setSelected(true);
                mItemCount++;
                selectedItem.add(mData);
                selectedItemView.add(v);
            } else {
                mData.setSelected(false);
                mItemCount--;
                for (int i = 0; i < selectedItem.size(); i++) {
                    if (mData == selectedItem.get(i)) {
                        selectedItemView.remove(i);
                        selectedItem.remove(i);
                    }
                }
                Log.d("Bhargav", "Selected items " + mItemCount);
            }
            setUpDeleteItemToolbar();
        } else {
            Bundle itemDetails = new Bundle();
            itemDetails.putInt("Id", mData.getId());
            itemDetails.putString("Title", mData.getTitle());
            itemDetails.putString("Description", mData.getDescription());
            itemDetails.putString("Date", mData.Date);
            itemDetails.putDouble("Amount", Math.abs(mData.getAmount()));
            itemDetails.putInt("Type", mData.getType());
            Intent intent = new Intent(MainActivity.this, InformationUpdate.class);
            intent.putExtras(itemDetails);
            startActivity(intent);
        }

    }

    @Override
    public void recyclerViewOnLongClicked(View v, int position, MoneyParameter mData) {
        mLongClickEnable = true;
        mItemCount++;
        Log.d("Bhargav", "Selected items " + mItemCount);
        setUpDeleteItemToolbar();
        selectedItem.add(mData);
        selectedItemView.add(v);
        mData.setSelected(true);
    }

    public void setUpDeleteItemToolbar() {
        if (mItemCount > 0) {
            mDeleteItemToolbar.setVisibility(View.VISIBLE);
            String itemCount = Integer.toString(mItemCount);
            mDeleteItemToolbar.setTitle(itemCount);
        } else {
            resetRecyclerView();
        }
    }

    public void startSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchList.class);
        startActivity(intent);
    }

    public void startNewActivity(int flag) {
        if (flag == 1) {
            Intent intent = new Intent(MainActivity.this, AllTimeRecord.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("Bhargav123", "Inside navigation item selected");
        int id = menuItem.getItemId();
        switch (id){
            case R.id.history:
                startNewActivity(1);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
