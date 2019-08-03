
package com.example.moneytracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class DisplayIncomeExpensesData {
    int pos;
    Context mContext;
    DatabaseHelper helper;
    Double MyBalance;
    public DisplayIncomeExpensesData(Context context){
        mContext = context;
    }

    public Double getIncomeExpensesListOfToday(IncomeExpensesDisplayListFragment mFrag) {
        MyBalance=0.0;
        helper = new DatabaseHelper(mContext);
        Cursor c = helper.getAllData();
        ArrayList<MoneyParameter> list = new ArrayList<>();
        if (c.getCount() == 0)
            Toast.makeText(mContext, "No Records Found", Toast.LENGTH_SHORT).show();
        else {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndex("Id"));
                String title = c.getString(c.getColumnIndex("Title"));
                String description = c.getString(c.getColumnIndex("Description"));
                Double amount = c.getDouble(c.getColumnIndex("Amount"));
                int type = c.getInt(c.getColumnIndex("Type"));
                String date = c.getString(c.getColumnIndex("Date"));
                String systemDate = getSystemDate();
                if(systemDate.equals(date)) {
                    MoneyParameter moneyParameter = new MoneyParameter(id,title, description, amount, date, type);
                    MyBalance += amount;
                    list.add(moneyParameter);
                }
            } while (c.moveToNext());
            if(list!=null) {
                mFrag.updateData(list);
            }
        }
        return MyBalance;
    }

    public Double getIncomeExpensesListOfMonth(IncomeExpensesDisplayListFragment mFrag) {
        MyBalance=0.0;
        helper = new DatabaseHelper(mContext);
        Cursor c = helper.getAllData();
        ArrayList<MoneyParameter> list = new ArrayList<>();
        if (c.getCount() == 0)
            Toast.makeText(mContext, "No Records Found", Toast.LENGTH_SHORT).show();
        else {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndex("Id"));
                String title = c.getString(c.getColumnIndex("Title"));
                String description = c.getString(c.getColumnIndex("Description"));
                Double amount = c.getDouble(c.getColumnIndex("Amount"));
                int type = c.getInt(c.getColumnIndex("Type"));
                String date = c.getString(c.getColumnIndex("Date"));
                String systemDate = getSystemDate();
                boolean x = compareMonth(date,systemDate);
                if(x == true) {
                    MoneyParameter moneyParameter = new MoneyParameter(id,title, description, amount, date, type);
                    list.add(moneyParameter);
                    MyBalance += amount;
                }
            } while (c.moveToNext());
            if(list!=null) {
                mFrag.updateData(list);
            }
        }
        return MyBalance;
    }

    public Double getIncomeExpensesListOfWeek(IncomeExpensesDisplayListFragment mFrag) {
        MyBalance=0.0;
        helper = new DatabaseHelper(mContext);
        Cursor c = helper.getAllData();
        ArrayList<MoneyParameter> list = new ArrayList<>();
        if (c.getCount() == 0)
            Toast.makeText(mContext, "No Records Found", Toast.LENGTH_SHORT).show();
        else {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndex("Id"));
                String title = c.getString(c.getColumnIndex("Title"));
                String description = c.getString(c.getColumnIndex("Description"));
                Double amount = c.getDouble(c.getColumnIndex("Amount"));
                int type = c.getInt(c.getColumnIndex("Type"));
                String date = c.getString(c.getColumnIndex("Date"));
                String systemDate = getSystemDate();
                MoneyParameter moneyParameter = new MoneyParameter(id,title, description, amount, date, type);
                MyBalance += amount;
                list.add(moneyParameter);
            } while (c.moveToNext());
            if(list!=null) {
                mFrag.updateData(list);
            }
        }
        return MyBalance;
    }
    private String getSystemDate() {
        final Calendar c = Calendar.getInstance();
        int mDate = c.get(Calendar.DATE);
        int mMonth = c.get(Calendar.MONTH);
        int mYear = c.get(Calendar.YEAR);
        String Date = getDate(mDate,mMonth,mYear);
        return Date;
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
    public boolean compareMonth(String date,String systemDate){
        if(date.charAt(6)!= systemDate.charAt(6))
            return false;
        if(date.charAt(7)!= systemDate.charAt(7))
            return false;
        if(date.charAt(8)!= systemDate.charAt(8))
            return false;
        if(date.charAt(9)!= systemDate.charAt(9))
            return false;
        if(date.charAt(3)!= systemDate.charAt(3))
            return false;
        if(date.charAt(4)!= systemDate.charAt(4))
            return false;
        return true;
    }

    public void showItemOnclick(int position){
        Toast.makeText(mContext,"Position is " + position,Toast.LENGTH_SHORT).show();

    }
}

