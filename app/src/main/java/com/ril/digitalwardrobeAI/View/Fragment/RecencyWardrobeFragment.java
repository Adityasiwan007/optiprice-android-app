package com.ril.digitalwardrobeAI.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Adapter.RecencyAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

import static com.ril.digitalwardrobeAI.Constants.selectedItems;


public class RecencyWardrobeFragment extends Fragment {
    public RecencyAdapter recencyAdapter;
    ArrayList<String> colorList;
    TreeMap<Date, ArrayList<WardrobeItemBean>> productMap;
    RecyclerView colorRecyclerView;
    public RecencyWardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recency_wardrobe, container, false);
        // gridView=view.findViewById(R.id.gridView);
        colorRecyclerView=view.findViewById(R.id.colorRecyclerView);
        colorList=new ArrayList<>();
        productMap = new TreeMap<>();
        //get map of color and corresponding products for that color
        getProductForWardrobe();
        recencyAdapter=new RecencyAdapter(getContext(),productMap);
       // colorRecyclerView.setHasFixedSize(true);
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        colorRecyclerView.setAdapter(recencyAdapter);
        colorRecyclerView.setAdapter(recencyAdapter);
        //create recycler view with that color
        return view;
    }


    public void getProductForWardrobe(){
        productMap = new TreeMap<Date, ArrayList<WardrobeItemBean>>(Collections.reverseOrder());

        for (int i = 0; i < selectedItems.size(); i++) {
             if(!productMap.containsKey(selectedItems.get(i).getAddedOn())){
                        ArrayList<WardrobeItemBean> list = new ArrayList<WardrobeItemBean>();
                        list.add(selectedItems.get(i));
                        productMap.put(selectedItems.get(i).getAddedOn(), list);
                    }else{
                        productMap.get( selectedItems.get(i).getAddedOn()).add(selectedItems.get(i));
                    }
        }

    }

}
