package com.example.moneytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_name = "MyDatabase";
    public static final String Table_name = "MoneyTracker";
    public static final String Id = "Id";
    public static final String Title = "Title";
    public static final String Description = "Description";
    public static final String Amount = "Amount";
    public static final String Date = "Date";
    public static final String Type = "Type";

    public DatabaseHelper(Context context){
        super(context,Database_name,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_name + " ( " + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Title + " TEXT NOT NULL, " +
                Description + " TEXT, " + Amount + " REAL NOT NULL, " + Date + " TEXT, " + Type + " INTEGER" + "); ";
        db.execSQL(query);

    }


    public boolean insert(String title,String description,Double amount,String date,int type){
        SQLiteDatabase db = this.getWritableDatabase();
        Double final_amount = type*amount;
        ContentValues c = new ContentValues();
        c.put(this.Title,title);
        c.put(this.Description,description);
        c.put(this.Amount,final_amount);
        c.put(this.Type,type);
        c.put(this.Date,date);
        long x = db.insert(Table_name,null,c);
        if(x == -1)
            return false;
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Table_name;
        Cursor c = db.query(Table_name,new String[]{Id,Title,Description,Amount,Date,Type},null,null
                ,null,null,null, null);
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + Table_name;
        db.execSQL(query);
        onCreate(db);

    }

    public void updateData(int id,String title,String description,String date,Double amount,int type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Title,title);
        cv.put(Description,description);
        cv.put(Date,date);
        cv.put(Amount,amount);
        cv.put(Type,type);
        cv.put(Id,id);
        db.update(Table_name,cv,"Id = " + id,null);
        Log.d("Bhargav","Update");
    }

    public void deleteInformation(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_name,"Id = " + id,null);
    }
}
