package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Fragment.WardrobeDialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder>  implements  WardrobeDialog.AlertInterface {
    LinkedHashMap<String, ArrayList<WardrobeItemBean>> productMap;
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
        LinearLayout colorIdentifier;
        RecyclerView colorItemRecyclerView;

        public MyViewHolder(View v) {
            super(v);
            colorIdentifier=v.findViewById(R.id.colorIdentifier);
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
    public ColorAdapter(Context context, LinkedHashMap<String, ArrayList<WardrobeItemBean>> productMap) {
        this.productMap = productMap;
        this.context=context;
    }
    public ColorAdapter(){}
    // Create new views (invoked by the layout manager)
    @Override
    public ColorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color, parent, false);
        ColorAdapter.MyViewHolder vh = new ColorAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ColorAdapter.MyViewHolder holder, final int position) {
        ArrayList<String> colorList=new ArrayList();
        for(String color: productMap.keySet()){

            colorList.add(color);
        }
        for(int i=0;i<colorList.size();i++) {
            if(i==position)
            {
                holder.colorIdentifier.getBackground().setColorFilter(Color.parseColor(colorList.get(i)), PorterDuff.Mode.SRC_ATOP);
                dateAdapter=new WardrobeAdapter(context,productMap.get(colorList.get(i)));
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
