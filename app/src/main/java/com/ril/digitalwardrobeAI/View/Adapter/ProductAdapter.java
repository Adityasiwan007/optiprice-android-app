package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ril.digitalwardrobeAI.Model.Product;
import com.ril.digitalwardrobeAI.ProductInterface;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_THUMBNAIL_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.IMAGE__THUMBNAIL_URL_CATALOG;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>  {
    ArrayList<Product> products;
    Context context;
    String type;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static ProductInterface pi;


    public ProductInterface getPi() {
        return pi;
    }
    public void setPi(ProductInterface pi) {
        this.pi = pi;
    }
    public void setProducts(ArrayList<Product> products,String type) {
        this.products = products;
        this.type=type;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        ImageView product_image;

        public MyViewHolder(View v) {
            super(v);
            product_image=v.findViewById(R.id.product_image);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            pi.getPositon(position);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context=context;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String imageName=products.get(position).getRawImages().get(0);
        Log.d(LOGTAG," IMAGE CLICKED "+ IMAGE__THUMBNAIL_URL_CATALOG+imageName);
        if(type.equals("wardrobe"))Picasso.get().load(IMAGE_THUMBNAIL_URL_WARDROBE + imageName).into(holder.product_image);
        else Picasso.get().load(IMAGE__THUMBNAIL_URL_CATALOG + imageName).into(holder.product_image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }



}
