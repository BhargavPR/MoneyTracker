package com.example.moneytracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchList extends AppCompatActivity implements RecyclerViewClickListener{

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
        setContentView(R.layout.activity_search_list);
        getAllWidget();
        initializeActivity();
        getAllData();


    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    public void getAllWidget(){
        mSearchView = findViewById(R.id.search_view);
        backBtn = (ImageView)findViewById(R.id.back_btn);
        recyclerView = (RecyclerView)findViewById(R.id.list);
    }

    public void initializeActivity(){
        helper = new DatabaseHelper(getApplicationContext());
        List = new ArrayList<>();
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
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
