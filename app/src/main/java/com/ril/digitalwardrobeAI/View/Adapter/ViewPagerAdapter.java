package com.ril.digitalwardrobeAI.View.Adapter;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.Model.Product;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_CATALOG;
import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;


public class ViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    ArrayList<Integer> layouts;
    ArrayList<Product> products;
    Activity activity;
    TextView txt_title,itemCount;
    ImageView prod_image;
    String type;

    public ViewPagerAdapter(ArrayList<Integer> layouts, Activity activity, ArrayList<Product> products,String type) {
        this.activity=activity;
        this.layouts=layouts;
        this.products=products;
        this.type=type;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts.get(position), container, false);
        txt_title=view.findViewById(R.id.txt_title);
        prod_image=view.findViewById(R.id.product_image);
        itemCount=view.findViewById(R.id.itemCount);
        String title = products.get(position).getTags().getTitle().substring(0, 1).toUpperCase() + products.get(position).getTags().getTitle().substring(1);

        txt_title.setText(title.toUpperCase());
        itemCount.setText(position+1+"/"+String.valueOf(products.size()));
        String imageName=products.get(position).getRawImages().get(0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        if(type.equals("wardrobe"))Picasso.get().load(IMAGE_URL_WARDROBE + imageName).resize(500, 500).centerInside().into(prod_image);
        else Picasso.get().load(IMAGE_URL_CATALOG + imageName).resize(500, 500).centerInside().into(prod_image);
        container.addView(view);

        return view;
    }



    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }



}