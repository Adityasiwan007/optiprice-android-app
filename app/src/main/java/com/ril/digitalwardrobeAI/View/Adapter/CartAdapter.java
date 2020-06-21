package com.ril.digitalwardrobeAI.View.Adapter;

import android.content.Context;

import com.ril.digitalwardrobeAI.Constants;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private final OnItemCheckListener onItemClick;

    public interface OnItemCheckListener {
        void onItemCheck(MissingItembean item);
        void onItemUncheck(MissingItembean item);
    }
    private OnItemCheckListener onItemCheckListener;

    TextView name,desc,price,seller;
    ImageView pic;
    public String img_url;
    private static DecimalFormat df = new DecimalFormat("0.00");
    public String price_text;
    private static final String TAG="CartAdapter";
    private String products_string = null;
    private ArrayList<MissingItembean> products;
    private Context mContext;
    public CartAdapter(Context mContext,String products,OnItemCheckListener onItemCheckListener) throws JSONException {
        this.products_string = products;
        this.mContext = mContext;
        this.products= new Gson().fromJson( products,new TypeToken<ArrayList<MissingItembean>>(){}.getType() );
        this.onItemClick = onItemCheckListener;
    }



    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        Log.d(TAG,"onCreateViewHolder: called");

        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.cart_recycler_layout ,parent,false);

        return new ViewHolder( view );
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MissingItembean currentItem=products.get(position);
        Log.d(TAG,"onBindViewHolder: called");
        price_text="$"+df.format(products.get(position).getPrice());
        price.setText( price_text );
        img_url=ROOT_URL+"popular/"+products.get(position).getRawImages().get(0);
        Picasso.get().load( Constants.IMAGE_THUMBNAIL_URL_WARDROBE + products.get(position).getRawImages().get(0)).into(pic);
        name.setText( products.get(position).getManual_desc());
        desc.setText(products.get(position).getOccasion());
        seller.setText(products.get(position).getSeller());




        ((ViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewHolder) holder).checkbox.setChecked(
                        !((ViewHolder) holder).checkbox.isChecked());
                if (((ViewHolder) holder).checkbox.isChecked()) {
                    onItemClick.onItemCheck(currentItem);
                } else {
                    onItemClick.onItemUncheck(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public void removeItem(int index)
    {
        products.remove( index );
        notifyItemRemoved( index );
//        notifyDataSetChanged();
//        notifyItemRangeChanged(index, products.size());
    }
    public void restoreItem(MissingItembean item, int index)
    {
        products.add(index,item);
        //notifyItemInserted( index );
        notifyDataSetChanged();
//        notifyItemRangeChanged(index, products.size());
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public View viewForeground,viewBackground;
        CheckBox checkbox;
        //CardView viewForeground,viewBackground;
        public ViewHolder(View itemView)
        {
            super(itemView);
            checkbox=itemView.findViewById( R.id.checkBox );
            checkbox.setClickable(false);
            name=itemView.findViewById( R.id.product_text );
            desc=itemView.findViewById( R.id.product_desc );
            price=itemView.findViewById( R.id.price );
            pic=itemView.findViewById( R.id.pro_pic );
            viewBackground=itemView.findViewById( R.id.card_back );
            viewForeground=itemView.findViewById( R.id.card );
            seller=itemView.findViewById(R.id.product_seller);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

}
