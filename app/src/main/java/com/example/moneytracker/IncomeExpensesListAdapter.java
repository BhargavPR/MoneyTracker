package com.example.moneytracker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class IncomeExpensesListAdapter extends Adapter<IncomeExpensesListAdapter.ViewHolder> {

    private ArrayList<MoneyParameter> list;
    private Context mcontext;
    IncomeExpensesListAdapter(Context context, ArrayList<MoneyParameter> v){
        mcontext = context;
        list = v;
    }
    @NonNull
    @Override
    public IncomeExpensesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.income_expenses_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeExpensesListAdapter.ViewHolder viewHolder, int i) {
        String title = list.get(i).Title;
        String Date = list.get(i).Date;
        Double Amount = list.get(i).Amount;
        String amount = Double.toString(Amount);
        int Type = list.get(i).Type;
        viewHolder.mTitle.setText(title);
        viewHolder.mDate.setText(Date);
        viewHolder.mAmount.setText(amount);
        if(Type == 1)
            viewHolder.mAmount.setTextColor(Color.parseColor("#5BC5A7"));
        else
            viewHolder.mAmount.setTextColor(Color.parseColor("#E16835"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle,mDate,mAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = (TextView)itemView.findViewById(R.id.IvTitle);
            mDate = (TextView)itemView.findViewById(R.id.IvDate);
            mAmount = (TextView)itemView.findViewById(R.id.IvAmount);
        }
    }
    public void updateAdapterData(ArrayList<MoneyParameter> mData){
        list.clear();
        list=mData;
        notifyDataSetChanged();
    }

}
