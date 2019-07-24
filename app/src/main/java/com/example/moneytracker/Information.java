package com.example.moneytracker;

import android.app.DatePickerDialog;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Information extends AppCompatActivity {

    TextView mIncomeBox, mExpenseBox;
    TextView mDatePicker;
    EditText mTitle,mDescription,mAmount;
    Button mCancel,mSave;
    ImageView mCalendar;
    int flag;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getAllElements();
        flag=1;
        String current_date = getSystemDate();
        mDatePicker.setText(current_date);
        mIncomeBox.setBackgroundResource(R.drawable.income_text_background);
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
        mIncomeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                mIncomeBox.setBackgroundResource(R.drawable.income_text_background);
                mExpenseBox.setBackgroundResource(0);
            }
        });
        mExpenseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=-1;
                mIncomeBox.setBackgroundResource(0);
                mExpenseBox.setBackgroundResource(R.drawable.expense_text_background);
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = mTitle.getText().toString();
                String Description = mDescription.getText().toString();
                String moneyAmount = mAmount.getText().toString();
                String Date = mDatePicker.getText().toString();
                int Type = flag;
                boolean check = validate(Title,moneyAmount);
                if(check == true) {
                    Double Amount = Double.parseDouble(moneyAmount);
                    boolean x = helper.insert(Title, Description, Amount, Date, Type);
                    finish();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void getAllElements() {
        mIncomeBox = (TextView) findViewById(R.id.Income_box);
        mExpenseBox = (TextView) findViewById(R.id.Expense_box);
        mTitle = (EditText)findViewById(R.id.Title);
        mDescription = (EditText)findViewById(R.id.Description);
        mDatePicker = (TextView)findViewById(R.id.Date);
        mAmount = (EditText)findViewById(R.id.Amount);
        mSave = (Button)findViewById(R.id.Save);
        mCancel = (Button)findViewById(R.id.Cancel);
        mCalendar = (ImageView)findViewById(R.id.Calendar);
        helper = new DatabaseHelper(Information.this);
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
}
