package com.example.moneytracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class IncomeExpensesListAdapter extends Adapter<IncomeExpensesListAdapter.ViewHolder> {

    DisplayIncomeExpensesData printer;
    private ArrayList<MoneyParameter> list;
    private Context mContext;
    private boolean mIsLongClickEnable;
    private int mItemCount;
    private RecyclerViewClickListener recyclerViewClickListener;
    IncomeExpensesListAdapter(Context context, ArrayList<MoneyParameter> v, RecyclerViewClickListener recyclerViewClickListener1){
        mContext = context;
        list = v;
        recyclerViewClickListener=recyclerViewClickListener1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.income_expenses_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final MoneyParameter mData = list.get(i);
        final int id = mData.getId();
        String title = mData.getTitle();
        String Date = mData.getDate();
        Double Amount = mData.getAmount();
        String amount = Double.toString(Amount);
        if(amount.charAt(amount.length()-2)=='.'){
            amount = amount + "0";
        }
        String CategoryImage = mData.getDescription();
        int Type = mData.getType();
        viewHolder.mTitle.setText(title);
        viewHolder.mDate.setText(Date);
        viewHolder.mAmount.setText(amount);
        setCategoryImage(CategoryImage,viewHolder);
        if(Type == 1)
            viewHolder.mAmount.setTextColor(Color.parseColor("#4CAF50"));
        else
            viewHolder.mAmount.setTextColor(Color.parseColor("#E16835"));
        //setBackground(mData,viewHolder);

        viewHolder.mItemview.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                recyclerViewClickListener.recyclerViewOnClicked(view,i,mData);
            }
        });
        viewHolder.mItemview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewClickListener.recyclerViewOnLongClicked(view,i,mData);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mItemview;
        TextView mTitle,mDate,mAmount;
        ImageView mCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemview = itemView;
            mTitle = (TextView)itemView.findViewById(R.id.IvTitle);
            mDate = (TextView)itemView.findViewById(R.id.IvDate);
            mAmount = (TextView)itemView.findViewById(R.id.IvAmount);
            mCategory = (ImageView)itemView.findViewById(R.id.IvDescriptionImage);
        }
    }
    public void updateAdapterData(ArrayList<MoneyParameter> mData){
        mIsLongClickEnable=false;
        mItemCount=0;
        list=mData;
        notifyDataSetChanged();
    }


    public void setBackground(MoneyParameter mData, ViewHolder viewHolder){
        if(mData.isSelected() == true){
            viewHolder.mItemview.setBackgroundResource(R.drawable.selected_itemlist_background);
        }
        else{
            viewHolder.mItemview.setBackgroundResource(R.drawable.unselected_itemlist_background);
        }

    }

    public void setCategoryImage(String category,ViewHolder viewHolder){
        Log.d("Bhargav1","Category is "+ category);
        switch (category){
            case "Food":
                viewHolder.mCategory.setImageResource(R.drawable.category_food);
                break;
            case "Transpotation":
                viewHolder.mCategory.setImageResource(R.drawable.category_transpotation);
                break;
            case "Home":
                viewHolder.mCategory.setImageResource(R.drawable.category_home);
                break;
            case "Cab":
                viewHolder.mCategory.setImageResource(R.drawable.category_cab);
                break;
            case "Entertainment":
                viewHolder.mCategory.setImageResource(R.drawable.category_entertainment);
                break;
            case "Shopping":
                viewHolder.mCategory.setImageResource(R.drawable.category_shopping);
                break;
            case "Clothing":
                viewHolder.mCategory.setImageResource(R.drawable.category_clothing);
                break;
            case "Health":
                viewHolder.mCategory.setImageResource(R.drawable.category_health);
                break;
            case "Sport":
                viewHolder.mCategory.setImageResource(R.drawable.category_sport);
                break;
            case "Electronics":
                viewHolder.mCategory.setImageResource(R.drawable.category_electronics);
                break;
            case "Vegetables":
                viewHolder.mCategory.setImageResource(R.drawable.category_vegetables);
                break;
            case "Gifts":
                viewHolder.mCategory.setImageResource(R.drawable.category_gitf);
                break;
            case "Travel":
                viewHolder.mCategory.setImageResource(R.drawable.category_travel);
                break;
            case "Education":
                viewHolder.mCategory.setImageResource(R.drawable.category_education);
                break;
            case "Fruits":
                viewHolder.mCategory.setImageResource(R.drawable.category_fruits);
                break;
        }
    }

}

