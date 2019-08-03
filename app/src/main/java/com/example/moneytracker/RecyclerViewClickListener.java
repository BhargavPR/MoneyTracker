package com.example.moneytracker;

import android.view.View;

public interface RecyclerViewClickListener {
    void recyclerViewOnClicked(View v,int position, MoneyParameter Data);
    void recyclerViewOnLongClicked(View v,int position, MoneyParameter Data);
}
