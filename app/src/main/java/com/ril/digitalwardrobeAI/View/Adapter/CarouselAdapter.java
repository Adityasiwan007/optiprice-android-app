package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.gtomato.android.ui.widget.CarouselView;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;

public  class CarouselAdapter extends CarouselView.Adapter<CarouselAdapter.MyViewHolder>  {
        ArrayList<WardrobeItemBean> products;
        Context context;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        public void setProducts(ArrayList<WardrobeItemBean> products) {
        this.products = products;
        }

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    // each data item is just a string in this case
    PhotoView product_image;

    public MyViewHolder(View v) {
        super(v);
        product_image=v.findViewById(R.id.product_image);

    }

    @Override
    public void onClick(View v) {

    }





}



    // Provide a suitable constructor (depends on the kind of dataset)
    public CarouselAdapter(Context context, ArrayList<WardrobeItemBean> products) {
        this.products = products;
        this.context=context;
    }
    public CarouselAdapter(){}
    // Create new views (invoked by the layout manager)
    @Override
    public CarouselAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_carowsel, parent, false);
        CarouselAdapter.MyViewHolder vh = new CarouselAdapter.MyViewHolder(v);

        return vh;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final CarouselAdapter.MyViewHolder holder, final int position) {
        final String imageName=products.get(position).getRawImages().get(0);
        Picasso.get().load(IMAGE_URL_WARDROBE + imageName).resize(500, 500).centerInside().into(holder.product_image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }



}
