package com.ril.digitalwardrobeAI.View.Fragment;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.R;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.selectedItems;
import static com.ril.digitalwardrobeAI.Constants.skirts;
import static com.ril.digitalwardrobeAI.Constants.tops;
import static com.ril.digitalwardrobeAI.Constants.trousers;
import static com.ril.digitalwardrobeAI.Constants.wardrobeItems;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {
    ConstraintLayout trouserSection,topSection,skirtSection;
    TextView skirtCount,trouserCount,topCount,itemCount;
    public static String selectedType;
    ImageView missing;

    public WardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wardrboe, container, false);
        trouserSection=view.findViewById(R.id.trouserSection);
        topSection=view.findViewById(R.id.topSection);
        skirtSection=view.findViewById(R.id.skirtSection);
        skirtCount=view.findViewById(R.id.skirtCount);
        topCount=view.findViewById(R.id.topCount);
        trouserCount=view.findViewById(R.id.trouserCount);
        itemCount=view.findViewById(R.id.itemCount);
        missing=(ImageView)getActivity().findViewById( R.id.missingItem );
        missing.setVisibility( View.VISIBLE );
        loadWardrobe();

        ConstraintLayout mainLayout=getActivity().findViewById(R.id.mainLayout);
        LinearLayout swipeRight=getActivity().findViewById(R.id.swipeRight);
        ImageView bar=getActivity().findViewById(R.id.bar);
        mainLayout.setVisibility(View.VISIBLE);
        bar.setVisibility(View.VISIBLE);
        swipeRight.setVisibility(View.VISIBLE);


        skirtSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skirts!=null) {
                    missing.setVisibility( View.GONE );
                    selectedItems = skirts;
                    selectedType="Skirts";
                    WardrobeNewFragment WardrobeNewFragment = new WardrobeNewFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_wardrobe, WardrobeNewFragment,"view").addToBackStack(null).commit();
                }

            }
        });
        trouserSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trousers!=null){
                    missing.setVisibility( View.GONE );
                selectedItems = trousers;
                    selectedType="Trousers";
                WardrobeNewFragment WardrobeNewFragment = new WardrobeNewFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_wardrobe, WardrobeNewFragment).addToBackStack(null).commit();
            }

            }
        });
        topSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tops!=null) {
                    missing.setVisibility( View.GONE );
                    selectedItems = tops;
                    selectedType="Tops";
                    WardrobeNewFragment WardrobeNewFragment = new WardrobeNewFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_wardrobe, WardrobeNewFragment).addToBackStack(null).commit();
                }

            }
        });
        return view;
    }
    void loadWardrobe(){
        tops=new ArrayList<>();
        skirts=new ArrayList<>();
        trousers=new ArrayList<>();
        itemCount.setText("3 categories, "+String.valueOf(wardrobeItems.size())+ "  Items");
        for(int i=0;i<wardrobeItems.size();i++) {
            if(wardrobeItems.get(i).getTags().getCategory().equals("Tops"))
                tops.add(wardrobeItems.get(i));
            else if((wardrobeItems.get(i).getTags().getCategory().equals("Skirts")))
                skirts.add(wardrobeItems.get(i));
            else if(wardrobeItems.get(i).getTags().getCategory().equals("Pants"))
                trousers.add(wardrobeItems.get(i));

        }

        if(tops!=null){
            topCount.setText(String.valueOf(tops.size()));
        }
        if(skirts!=null){
            skirtCount.setText(String.valueOf(skirts.size()));
        }
        if(trouserCount!=null){
            trouserCount.setText(String.valueOf(trousers.size()));
        }
    }


}
