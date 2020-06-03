package com.ril.digitalwardrobeAI.View.Fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Adapter.TabAdapter;

import static com.ril.digitalwardrobeAI.Constants.selectedItems;
import static com.ril.digitalwardrobeAI.View.Fragment.WardrobeFragment.selectedType;


public class WardrobeNewFragment extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView itemType,itemCount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_wardrobe_new, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout)  view.findViewById(R.id.tabLayout);
        itemCount=view.findViewById(R.id.itemCount);
        itemType=view.findViewById(R.id.itemType);
        itemCount.setText(String.valueOf(selectedItems.size()));
        itemType.setText(selectedType);
        adapter = new TabAdapter(getFragmentManager());
        adapter.addFragment(new ColorWardrobeFragment(), "Color");
        adapter.addFragment(new RecencyWardrobeFragment(), "Recency");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        ConstraintLayout mainLayout=getActivity().findViewById(R.id.mainLayout);
        LinearLayout swipeRight=getActivity().findViewById(R.id.swipeRight);
        ImageView bar=getActivity().findViewById(R.id.bar);
        mainLayout.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);
        swipeRight.setVisibility(View.GONE);
        return  view;
    }
}
