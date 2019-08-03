package com.example.moneytracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;

import static com.example.moneytracker.DatabaseHelper.Description;

public class Information extends AppCompatActivity {

    TextView mIncomeBox, mExpenseBox;
    TextView mDatePicker;
    android.support.v7.widget.Toolbar mToolBar;
    EditText mTitle,mAmount;
    Button mCancel,mSave;
    ImageView mCalendar;
    Spinner mCategory;
    int flag;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getAllWidget();
        setUpToolBar();
        flag=1;
    }

    private String getSystemDate() {
        final Calendar c = Calendar.getInstance();
        int mDate = c.get(Calendar.DATE);
        int mMonth = c.get(Calendar.MONTH);
        int mYear = c.get(Calendar.YEAR);
        String Date = getDate(mDate,mMonth,mYear);
        return Date;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String current_date = getSystemDate();
        mDatePicker.setText(current_date);
        if(flag==1){
            mIncomeBox.setBackgroundResource(R.drawable.income_text_background);
            mIncomeBox.setTextColor(Color.parseColor("#FFFFFF"));
            mExpenseBox.setTextColor(Color.parseColor("#212121"));
            setUpCategorySpinner(1);
        }
        else{
            mExpenseBox.setBackgroundResource(R.drawable.expense_text_background);
            mIncomeBox.setTextColor(Color.parseColor("#212121"));
            mExpenseBox.setTextColor(Color.parseColor("#FFFFFF"));
            setUpCategorySpinner(-1);
        }

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUpCategorySpinner(1);
        mIncomeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                mIncomeBox.setBackgroundResource(R.drawable.income_text_background);
                mExpenseBox.setBackgroundResource(0);
                mIncomeBox.setTextColor(Color.parseColor("#FFFFFF"));
                mExpenseBox.setTextColor(Color.parseColor("#212121"));
                setUpCategorySpinner(1);
            }
        });
        mExpenseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=-1;
                mIncomeBox.setBackgroundResource(0);
                mExpenseBox.setBackgroundResource(R.drawable.expense_text_background);
                mIncomeBox.setTextColor(Color.parseColor("#212121"));
                mExpenseBox.setTextColor(Color.parseColor("#FFFFFF"));
                setUpCategorySpinner(-1);
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = mTitle.getText().toString();
                String Category = mCategory.getSelectedItem().toString();
                String moneyAmount = mAmount.getText().toString();
                String Date = mDatePicker.getText().toString();
                int Type = flag;
                boolean check = validate(Title,moneyAmount);
                if(check == true) {
                    Double Amount = Double.parseDouble(moneyAmount);
                    Amount = Amount + 0.000;
                    Amount = round(Amount,2);
                    Log.d("Bhargav","Amount is " + moneyAmount);
                    boolean x = helper.insert(Title, Category, Amount, Date, Type);
                    finish();
                }
            }
        });
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mDate=0,mMonth=0,mYear=0,mMinute=0,mHour=0;
                String showDateTime="";
                ShowDateDialog(mDate,mMonth,mYear,showDateTime);
            }
        });

    }

    public void getAllWidget() {
        mIncomeBox = (TextView) findViewById(R.id.Income_box);
        mExpenseBox = (TextView) findViewById(R.id.Expense_box);
        mTitle = (EditText)findViewById(R.id.Title);
        mCategory = (Spinner)findViewById(R.id.category_spinner);
        mDatePicker = (TextView)findViewById(R.id.Date);
        mAmount = (EditText)findViewById(R.id.Amount);
        mSave = (Button)findViewById(R.id.Save);
        mCalendar = (ImageView)findViewById(R.id.Calendar);
        helper = new DatabaseHelper(Information.this);
        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.Information_activity_toolbar);
    }
    public  boolean validate(String Title,String Amount){
        if(Title.isEmpty()&&Amount.isEmpty()){
            Toast.makeText(Information.this,"Please Fill All Details",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Title.isEmpty()){
            Toast.makeText(Information.this,"Title is Empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Amount.isEmpty()){
            Toast.makeText(Information.this,"Please fill amount details",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Amount.equals(".")){
            Toast.makeText(Information.this,"Amount is Invalid",Toast.LENGTH_SHORT).show();
            return false;
        }
        int digits_of_amount=Amount.length();
        for(int i=0;i<Amount.length();i++){
            if(Amount.charAt(i)=='.'){
                digits_of_amount=i;
                break;
            }
        }
        if(digits_of_amount>8){
            Toast.makeText(Information.this,"You don't need this App. You are billionaire!!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void ShowDateDialog(int mDate, int mMonth, int mYear, final String showDateTime) {
        final Calendar c = Calendar.getInstance();
        mDate = c.get(Calendar.DATE);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);
        final int Date, Month, Year;
        int flag = 1;
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = getDate(dayOfMonth,month,year);
                mDatePicker.setText(Date);
            }
        }, mYear, mMonth, mDate);
        datePickerDialog.show();

    }

    public String getDate(int dayOfMonth,int month,int year){
        String temp = "";
        if (dayOfMonth < 10)
            temp = temp + '0' + dayOfMonth;
        else
            temp = temp + dayOfMonth;
        temp = temp + '/';
        if (month < 9)
            temp = temp + '0' + (month + 1);
        else
            temp = temp + (month + 1);
        temp = temp + '/';
        temp = temp + year;
        return temp;
    }
    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void setUpToolBar(){
        mToolBar.setTitle("Income/Expenses Details");
        mToolBar.setNavigationIcon(R.drawable.back_icon_24dp);
    }

    public void setUpCategorySpinner(int value){
        if(value == -1) {
            mCategory.setPrompt("Select the Category1");
            ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.expenses_category,android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCategory.setAdapter(categoryAdapter);
        }
        else{

            @SuppressLint("ResourceType")
            ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.income_category,android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCategory.setAdapter(categoryAdapter);
        }
    }
}
