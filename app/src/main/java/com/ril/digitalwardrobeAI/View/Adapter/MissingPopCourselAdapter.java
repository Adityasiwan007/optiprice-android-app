package com.ril.digitalwardrobeAI.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.gtomato.android.ui.widget.CarouselView;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public  class MissingPopCourselAdapter extends CarouselView.Adapter<MissingPopCourselAdapter.MyViewHolder>  {
    ArrayList<MissingItembean> products;
    Context context;
    private int c=1;
    Dialog pop;
    PhotoView product_image;
    TextView title,cat,price,size;
    ImageView cart,pic,star1,star2,star3,star4,star5;
    ImageView[] stars_id = new ImageView[5];
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    final ArrayList<MissingItembean> cartpro = new ArrayList<MissingItembean>();

    public void setProducts(ArrayList<MissingItembean> products) {
        this.products = products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        public MyViewHolder(View v) {
            super(v);
            title=(TextView)v.findViewById( R.id.title_pop );
            cat=(TextView)v.findViewById( R.id.cata_pop );
            cart=(ImageView)v.findViewById( R.id.cart_pop );
            pic=(ImageView)v.findViewById( R.id.imageView12 ) ;
            price=(TextView)v.findViewById( R.id.price_txt );
            star1=(ImageView)v.findViewById( R.id.s1 );
            star2=(ImageView)v.findViewById( R.id.s2 );
            star3=(ImageView)v.findViewById( R.id.s3 );
            star4=(ImageView)v.findViewById( R.id.s4 );
            star5=(ImageView)v.findViewById( R.id.s5 );
            size=(TextView)v.findViewById( R.id.add_size_m );
            stars_id[0]=star1;stars_id[1]=star2;stars_id[2]=star3;stars_id[3]=star4;stars_id[4]=star5;
        }

        @Override
        public void onClick(View v) {

        }





    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MissingPopCourselAdapter(Context context, ArrayList<MissingItembean> products) {
        this.products = products;
        this.context=context;
    }
    public MissingPopCourselAdapter(){}
    // Create new views (invoked by the layout manager)
    @Override
    public MissingPopCourselAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.missing_popup, parent, false);
        MissingPopCourselAdapter.MyViewHolder vh = new MissingPopCourselAdapter.MyViewHolder(v);

        return vh;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MissingPopCourselAdapter.MyViewHolder holder, final int position) {
//        String img_url=ROOT_URL+"popular/"+products.get(position).getRawImages().get(0);
//        Picasso.get().load( Constants.IMAGE_THUMBNAIL_URL_WARDROBE + products.get(position).getRawImages().get(0)).into(holder.product_image);
//                  pop=new Dialog( context );
//                  pop.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );

                  String price_value="$ "+products.get(position).getPrice();
                  price.setText(price_value );
                  size.setText( products.get(position).getSize() );
                  title.setText( products.get(position).getManual_desc());
                  cat.setText(products.get(position).getOccasion());
                  String img_url=ROOT_URL+"popular/"+products.get(position).getRawImages().get(0);
                  Picasso.get().load( img_url).into(pic);

                  //Set the start rating
                  double rate=products.get(position).getRating();
                  int int_rate=(int)rate;
                  int i;

                  for(i=0;i<int_rate;i++)
                  {
                      stars_id[i].setImageResource( R.drawable.stared );
                  }
                  if((rate-int_rate)!=0)
                  {
                      stars_id[int_rate].setImageResource( R.drawable.halfstar );
                  }

                  cart.setOnClickListener( new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                         // added.setText( c );
                          //cartpro.add(products.get(position));
                          Intent intent = new Intent("custom-message");
                          addToServer(products.get(position).getRawImages().get(0));
                          intent.putExtra("unit",products.get(position).getRawImages().get(0));
                          LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                          Toast.makeText( context,"Item added to cart", Toast.LENGTH_SHORT).show();
                      }
                  } );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addToServer(String id)
    {
        Util.getUserService().addCartDetails(hearders,id).enqueue( new Callback<RestResponseBeanCart>(){
            @Override
            public void onResponse(Call<RestResponseBeanCart> call, Response<RestResponseBeanCart> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+context, "--------Getting cart items--------");
                    RestResponseBeanCart restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        String[] cartIDs;
                        cartIDs=restResponse.getCart();
                        //prevCartPro(cartIDs);
                        Log.d(LOGTAG+context, "Length of the cart: "+ cartIDs.length);
                        for(int i=0;i<cartIDs.length;i++)
                        {
                            System.out.println( cartIDs[i] );
                        }

                    } else Toast.makeText(context, "Please try again.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBeanCart> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+context, t.getMessage());
                Toast.makeText(context,"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
