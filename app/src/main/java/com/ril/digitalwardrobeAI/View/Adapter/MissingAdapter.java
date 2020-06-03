//package com.ril.digitalwardrobe.View.Adapter;
//import com.bumptech.glide.Glide;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.ril.digitalwardrobe.Model.Product;
//import com.ril.digitalwardrobe.Model.WardrobeItemBean;
//import com.ril.digitalwardrobe.R;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import static com.ril.digitalwardrobe.Constants.ROOT_URL;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//
//public class MissingAdapter extends RecyclerView.Adapter<MissingAdapter.ViewHolder> {
//    public  String cat;
//    public String img_url;
//    public String price;
//    private static final String TAG="MissingViewAdapter";
//    private String products_string = null;
//    private ArrayList<WardrobeItemBean> missingPro;
//    private Context mContext;
//
//    public MissingAdapter(Context mContext, String missingPro) throws JSONException {
//        this.products_string = missingPro;
//        this.mContext = mContext;
//        this.missingPro= new Gson().fromJson( missingPro,new TypeToken<ArrayList<WardrobeItemBean>>(){}.getType() );
//
//    }
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
//        Log.d(TAG,"onCreateViewHolder: called");
//
//        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.product_card ,parent,false);
//
//        return new ViewHolder( view );
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        Log.d(TAG,"onBindViewHolder: called");
//        img_url=ROOT_URL+"popular/"+missingPro.get(position).getRawImages().get(0);
//        Glide.with( mContext )
//                .asBitmap()
//                .load(img_url )
//                .into(holder.pro_image);
//        holder.pro_name.setText(missingPro.get(position).getTags().getTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return missingPro.size();
//    }
//
//
//
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        CardView card_view;
//        ImageView pro_image;
//        TextView pro_name;
//
//        public ViewHolder(View itemView)
//        {
//            super(itemView);
//            pro_image=itemView.findViewById(R.id.pro_pic);
//            pro_name=itemView.findViewById(R.id.pro_text);
//            card_view = itemView.findViewById(R.id.card);
//
//        }
//
//    }
//}


package com.ril.digitalwardrobeAI.View.Adapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gtomato.android.ui.widget.CarouselView;
import com.ril.digitalwardrobeAI.Addedcart;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Activity.SellMainAI;
import com.ril.digitalwardrobeAI.View.Fragment.MissingDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class MissingAdapter extends RecyclerView.Adapter<MissingAdapter.ViewHolder>{
    public  String cat;
    public String img_url;
    public String price;
    private static final String TAG="MissingViewAdapter";
    private String products_string = null;
    private ArrayList<MissingItembean> missingPro;
    private MissingItembean selected_pro;
    
    Dialog pop;
    Addedcart Addedcart;
    private int c=1;
    CarouselView carouselView;
    private Context mContext;
    private TextView number;
    private String[] cata= {"Tops","Trousers","Skirts"};
    private int i;
    public GridView grid;
    ImageView close;
    public MissingAdapter(Context mContext, String missingPro) throws JSONException {
        this.products_string = missingPro;
        this.mContext = mContext;
        this.missingPro= new Gson().fromJson( missingPro,new TypeToken<ArrayList<MissingItembean>>(){}.getType() );
        this.Addedcart=Addedcart;
    }


//    MissingGridAdapter adapter=new MissingGridAdapter( MissingActivity.this,jsonString);
//    gridView.setAdapter( adapter );

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        Log.d(TAG,"onCreateViewHolder: called");

        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.missing_grid ,parent,false);
        return new ViewHolder( view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ArrayList<MissingItembean> gridPro = new ArrayList<MissingItembean>();
        Log.d(TAG,"onBindViewHolder: called");
//        img_url=ROOT_URL+"popular/"+missingPro.get(position).getRawImages().get(0);
//        Glide.with( mContext )
//                .asBitmap()
//                .load(img_url )
//                .into(holder.pro_image);
//        holder.pro_name.setText(missingPro.get(position).getTags().getTitle());

        for(i=0;i<missingPro.size();i++)
        {
            if(missingPro.get(i).getTags().getCategory().equals( cata[position] ))
            {
                gridPro.add( missingPro.get(i) );
            }
        }

//        if(gridPro.size()!=0)
//        {
//            holder.pro_name.setText(cata[position]);
//            MissingGridAdapter adapter=new MissingGridAdapter(mContext,gridPro);
//            grid.setAdapter( adapter );
//        }

//        gridPro.clear();
        grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_pro=missingPro.get( position );
                Intent i = new Intent( mContext, SellMainAI.class );
                Gson gson = new Gson();
                String select=gson.toJson(selected_pro);
                i.putExtra( "selected", select );
                mContext.startActivity(i);

            }
        } );


    }

    @Override
    public int getItemCount() {
        return cata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pro_name;
        public ViewHolder(View itemView)
        {
            super(itemView);
            grid=itemView.findViewById(R.id.grid_view);
            pro_name=itemView.findViewById(R.id.pro_text);

        }

    }
}




