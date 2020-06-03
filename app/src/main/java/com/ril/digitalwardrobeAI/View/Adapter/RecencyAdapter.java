package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.DateFormat;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Fragment.WardrobeDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class RecencyAdapter extends RecyclerView.Adapter<RecencyAdapter.MyViewHolder>  implements  WardrobeDialog.AlertInterface {
    TreeMap<Date, ArrayList<WardrobeItemBean>> productMap;
    Context context;
    private WardrobeAdapter dateAdapter;
    private RecyclerView.LayoutManager layoutManager;
    WardrobeDialog wardrobeDialog;

    @Override
    public void closeAlert() {
        wardrobeDialog.dismiss();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView dateText;
        RecyclerView colorItemRecyclerView;

        public MyViewHolder(View v) {
            super(v);
            dateText=v.findViewById(R.id.dateText);
            colorItemRecyclerView=v.findViewById(R.id.colorItemRecyclerView);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();



        }
    }

    // Provide a suitable construct
    // or (depends on the kind of dataset)
    public RecencyAdapter(Context context, TreeMap<Date, ArrayList<WardrobeItemBean>> productMap) {
        this.productMap = productMap;
        this.context=context;
    }
    public RecencyAdapter(){}
    // Create new views (invoked by the layout manager)
    @Override
    public RecencyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);
        RecencyAdapter.MyViewHolder vh = new RecencyAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecencyAdapter.MyViewHolder holder, final int position) {
        ArrayList<Date> dateList=new ArrayList();
        for(Date date: productMap.keySet()){

            dateList.add(date);
        }
        for(int i=0;i<dateList.size();i++) {
                if(i==position)
                {
                    holder.dateText.setText(DateFormat.getFormattedDate(dateList.get(i),"dd MMM"));
                    dateAdapter=new WardrobeAdapter(context,productMap.get(dateList.get(i)));
                   // holder.colorItemRecyclerView.setHasFixedSize(true);
                    holder.colorItemRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                    holder.colorItemRecyclerView.setAdapter(dateAdapter);
                    break;
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productMap.size();
    }


}
