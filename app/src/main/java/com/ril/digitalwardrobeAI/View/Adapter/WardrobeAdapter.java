package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ril.digitalwardrobeAI.Constants;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Activity.CartActivity;
import com.ril.digitalwardrobeAI.View.Activity.MissingActivity;
import com.ril.digitalwardrobeAI.View.Activity.SellMainAI;
import com.ril.digitalwardrobeAI.View.Fragment.WardrobeDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;

public class WardrobeAdapter extends RecyclerView.Adapter<WardrobeAdapter.MyViewHolder>  implements  WardrobeDialog.AlertInterface {
    ArrayList<WardrobeItemBean> productList;
    WardrobeItemBean selected_pro;
    Context context;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    WardrobeDialog wardrobeDialog;

    @Override
    public void closeAlert() {
        wardrobeDialog.dismiss();
    }

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
            Log.d(LOGTAG,Constants.IMAGE_THUMBNAIL_URL_WARDROBE +productList.get(position).getRawImages().get(0));
            if(productList.size()>1) {
               // ArrayList<WardrobeItemBean> list=productList;
               //Collections.swap(list, position, 1);
            }

//            selected_pro=productList.get( position );
//            Intent i = new Intent( context, SellMainAI.class );
//            Gson gson = new Gson();
//            String select=gson.toJson(selected_pro);
//            i.putExtra( "selected", select );
//            context.startActivity(i);

//            wardrobeDialog=new WardrobeDialog(context,productList,position);
//          //  wardrobeDialog
//            wardrobeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            wardrobeDialog.show();


        }
    }

    // Provide a suitable construct
    // or (depends on the kind of dataset)
    public WardrobeAdapter(Context context, ArrayList<WardrobeItemBean> productList) {
        this.productList = productList;
        this.context=context;
    }
    public WardrobeAdapter(){}
    // Create new views (invoked by the layout manager)
    @Override
    public WardrobeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wardrobe, parent, false);
        WardrobeAdapter.MyViewHolder vh = new WardrobeAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final WardrobeAdapter.MyViewHolder holder, final int position) {
       /* for(int i=0;i<productList.size();i++) {
            Log.d(LOGTAG, " date " + productList.get(i).getAddedOn());
            Log.d(LOGTAG, " image " + productList.get(i).getRawImages().get(0));
        }
*/
        final String imageName=productList.get(position).getRawImages().get(0);
        Picasso.get().load(Constants.IMAGE_THUMBNAIL_URL_WARDROBE + imageName).into(holder.product_image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productList.size();
    }


}
