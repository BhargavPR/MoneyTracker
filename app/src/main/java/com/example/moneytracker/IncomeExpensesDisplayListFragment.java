package com.example.moneytracker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeExpensesDisplayListFragment extends Fragment {


    ArrayList<MoneyParameter> List;
    Context mContext;
    RecyclerView recyclerView;
    IncomeExpensesListAdapter adapter;
    public IncomeExpensesDisplayListFragment(){
    };
    @SuppressLint("ValidFragment")
    public IncomeExpensesDisplayListFragment(Context context,ArrayList<MoneyParameter> list){
        List = list;
        mContext = context;
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_income_expenses_list_display,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new IncomeExpensesListAdapter(mContext,List);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    public void updateData(ArrayList<MoneyParameter> list){
        List.clear();
        List=list;
        adapter.updateAdapterData(List);
    }


}
