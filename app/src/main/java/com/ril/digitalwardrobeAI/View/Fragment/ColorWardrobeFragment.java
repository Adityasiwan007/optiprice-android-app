package com.ril.digitalwardrobeAI.View.Fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Adapter.ColorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.colorBar;
import static com.ril.digitalwardrobeAI.Constants.selectedItems;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorWardrobeFragment extends Fragment {
     LinearLayout colorBarLayout;
    public ColorAdapter colorAdapter;

    ArrayList<String> colorList;
     public  static int y=0;
     LinkedHashMap<String, ArrayList<WardrobeItemBean>> productMap;
     ArrayList<String> colorPositions=new ArrayList<>();
    HashSet<String> hashSet = new HashSet<String>();

    RecyclerView colorRecyclerView;
    public ColorWardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_color_wardrobe, container, false);
       // gridView=view.findViewById(R.id.gridView);
        colorBarLayout=view.findViewById(R.id.colorBar);
        colorRecyclerView=view.findViewById(R.id.colorRecyclerView);
        colorList=new ArrayList<>();
        productMap = new LinkedHashMap<>();
        //get map of color and corresponding products for that color
        getProductForWardrobe();

        colorAdapter=new ColorAdapter(getContext(),productMap);
        // colorRecyclerView.setHasFixedSize(true);
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        colorRecyclerView.setAdapter(colorAdapter);
        colorRecyclerView.setAdapter(colorAdapter);

        for(String color : productMap.keySet()){

            colorPositions.add(color);
        }

        //create recycler view with that color
        return view;
    }



    public  HashMap getProductForWardrobe(){
        productMap=new LinkedHashMap<>();
        for(int j=0;j<colorBar.size();j++) {
            for (int i = 0; i < selectedItems.size(); i++) {
                if (colorBar.get(j).equals(selectedItems.get(i).getTags().getBaseColorHex())) {
                    if (!productMap.containsKey(selectedItems.get(i).getTags().getBaseColorHex())) {
                        ArrayList<WardrobeItemBean> list = new ArrayList<WardrobeItemBean>();
                        list.add(selectedItems.get(i));
                        productMap.put(selectedItems.get(i).getTags().getBaseColorHex(), list);
                    } else {
                        productMap.get(selectedItems.get(i).getTags().getBaseColorHex()).add(selectedItems.get(i));
                    }

                }
            }
        }
        setcolorBarLayout();
        return productMap;
    }
    int i=0;
    void setcolorBarLayout() {
        colorBarLayout.setWeightSum(productMap.keySet().size());
        for (String key : productMap.keySet()) {
            LinearLayout layout = new LinearLayout(getContext());
            Log.d(LOGTAG, "colors : " + key);
            ViewTreeObserver vto = colorBarLayout.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        colorBarLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        colorBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    int height = colorBarLayout.getMeasuredHeight();
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / productMap.size()));

                }
            });

            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackgroundColor(Color.parseColor(key));
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int color = ((ColorDrawable) layout.getBackground()).getColor();
                    for(int i=0;i<colorPositions.size();i++){
                        int colorRec =0;
                            colorRec =Color.parseColor(colorPositions.get(i));
                        Log.d(LOGTAG, " COLOR REC " + colorRec + " color "+ color );
                       if(colorRec==color) {
                           colorRecyclerView.scrollToPosition(i);
                           break;
                       }
                    }

                }
            });
            colorBarLayout.addView(layout);
            i=i+1;
        }
    }

}
