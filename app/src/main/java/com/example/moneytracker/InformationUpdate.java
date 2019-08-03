package com.example.moneytracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class InformationUpdate extends AppCompatActivity {

    Toolbar mToolbar;
    TextView detailsTitle,detailsDate,detailsAmount,detailsType,detailsCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_update);
        getAllWidget();
        setUpToolbar();
        Intent intent = getIntent();
        Bundle itemDetails = intent.getExtras();
        bindInformation(itemDetails);
    }



    public void bindInformation(Bundle itemDetails){

        detailsCategory.setText(itemDetails.getString("Description"));
        detailsTitle.setText(itemDetails.getString("Title"));
        detailsDate.setText(itemDetails.getString("Date"));
        int type = itemDetails.getInt("Type");
        detailsAmount.setText(Double.toString(itemDetails.getDouble("Amount")*type));
        if(type == 1)
            detailsType.setText("Income");
        else
            detailsType.setText("Expenses");

    }
    public void setUpToolbar(){
        mToolbar.setTitle("Details");
        mToolbar.setNavigationIcon(R.drawable.back_icon_24dp);
    }
    public void getAllWidget(){
        mToolbar = (Toolbar)findViewById(R.id.information_update_activity_toolbar);
        detailsTitle = (TextView)findViewById(R.id.details_title);
        detailsDate = (TextView)findViewById(R.id.details_date);
        detailsAmount = (TextView)findViewById(R.id.details_amount);
        detailsType = (TextView)findViewById(R.id.details_type);
        detailsCategory = (TextView)findViewById(R.id.details_category);
    }

}

