
package com.example.moneytracker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeExpensesDisplayListFragment extends Fragment {


    ArrayList<MoneyParameter> List;
    Context mContext;
    RecyclerView recyclerView;
    IncomeExpensesListAdapter adapter;
    public RecyclerViewClickListener recyclerViewClickListener;
    public IncomeExpensesDisplayListFragment(){
    };
    @SuppressLint("ValidFragment")
    public IncomeExpensesDisplayListFragment(Context context,ArrayList<MoneyParameter> list, RecyclerViewClickListener recyclerViewClickListener){
        List = list;
        mContext = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_income_expenses_list_display,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new IncomeExpensesListAdapter(mContext,List,recyclerViewClickListener);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        return view;
    }

    public void updateData(ArrayList<MoneyParameter> list){
        if(list!= null) {
            List = list;
            adapter.updateAdapterData(List);
            Log.d("Bhargav","updateData Method is called");
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onResume() {
        super.onResume();

    }


}

