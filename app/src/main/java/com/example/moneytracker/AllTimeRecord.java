package com.example.moneytracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllTimeRecord extends AppCompatActivity implements RecyclerViewClickListener{

    Toolbar mToolbar;
    CardView searchToolbar;
    android.support.v7.widget.SearchView mSearchView;
    ImageView backBtn;
    ArrayList<MoneyParameter> List;
    DatabaseHelper helper;
    IncomeExpensesListAdapter adapter;
    RecyclerViewClickListener recyclerViewClickListener;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_time_record);
        getAllWidget();
        setUpToolBar();
        initializeActivity();
        getAllData();
    }

    public void setUpToolBar(){
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Bhargav","All Time Records Activity search icon");
        int id = item.getItemId();
        if(id == R.id.search){
            startItemSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String searchText = s.toLowerCase();
                ArrayList<MoneyParameter> searchResult = new ArrayList<>();
                for(int i=0;i<List.size();i++){
                    String title = List.get(i).Title.toLowerCase();
                    if(title.contains(searchText)){
                        searchResult.add(List.get(i));
                    }

                }
                adapter.updateAdapterData(searchResult);
                return false;
            }
        });
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(searchToolbar.getVisibility() == View.VISIBLE) {
            searchToolbar.setVisibility(View.GONE);
            mToolbar.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }

    public void startItemSearch(){
        mToolbar.setVisibility(View.GONE);
        searchToolbar.setVisibility(View.VISIBLE);
        mSearchView.setFocusable(true);
        mSearchView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    public void getAllWidget(){
        searchToolbar = (CardView)findViewById(R.id.search_view_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.records_activity_toolbar);
        mSearchView = findViewById(R.id.search_view);
        backBtn = (ImageView)findViewById(R.id.back_btn);
        recyclerView = (RecyclerView)findViewById(R.id.list);
    }

    public void initializeActivity(){
        helper = new DatabaseHelper(getApplicationContext());
        List = new ArrayList<>();
        adapter = new IncomeExpensesListAdapter(getApplicationContext(),List,recyclerViewClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
    public void getAllData() {
        Cursor c = helper.getAllData();
        if (c.getCount() == 0)
            Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT).show();
        else {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndex("Id"));
                String title = c.getString(c.getColumnIndex("Title"));
                String description = c.getString(c.getColumnIndex("Description"));
                Double amount = c.getDouble(c.getColumnIndex("Amount"));
                int type = c.getInt(c.getColumnIndex("Type"));
                String date = c.getString(c.getColumnIndex("Date"));
                MoneyParameter moneyParameter = new MoneyParameter(id, title, description, amount, date, type);
                List.add(moneyParameter);
            } while (c.moveToNext());
        }
    }

    @Override
    public void recyclerViewOnClicked(View v, int position, MoneyParameter Data) {

    }

    @Override
    public void recyclerViewOnLongClicked(View v, int position, MoneyParameter Data) {

    }
}
