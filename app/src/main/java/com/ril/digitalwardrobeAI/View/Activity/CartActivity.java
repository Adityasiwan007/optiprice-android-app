package com.ril.digitalwardrobeAI.View.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Adapter.CartAdapter;
import com.ril.digitalwardrobeAI.View.Helper.RecyclerItemTouchHelper;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class CartActivity extends AppCompatActivity implements com.ril.digitalwardrobeAI.View.Helper.RecyclerItemTouchHelperListener {
    private static DecimalFormat df = new DecimalFormat("0.00");
    public String[] cartIDs;
    public MissingItembean detetedItem;
    private ConstraintLayout cs;
    public ArrayList<MissingItembean> cart_item = new ArrayList<MissingItembean>();
    private ArrayList<MissingItembean> currentSelectedItems = new ArrayList<>();
    ImageView back,payment;
    String jsonString,send_back_missing;
    ConstraintLayout organge;
    TextView num,total_sum,selected,add_count;
    String num_items;
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra("cart_product");
        send_back_missing=intent.getStringExtra( "missing" );
        //cart_item= new Gson().fromJson( jsonString,new TypeToken<ArrayList<MissingItembean>>(){}.getType() );
        back=(ImageView)findViewById( R.id.missingback ) ;
        num=(TextView)findViewById( R.id.num);
        add_count=(TextView)findViewById( R.id.add_count );
        organge=(ConstraintLayout) findViewById( R.id.count_layout );
        total_sum=(TextView)findViewById( R.id.total );
        cs=(ConstraintLayout)findViewById( R.id.constraint_layout );
        selected=(TextView)findViewById( R.id.count_price );
        payment=(ImageView)findViewById( R.id.proceed );
        prevCartId();
//        if(cart_item.size()==0)
//        {
//            Intent i=new Intent(CartActivity.this,MissingActivity.class);
//            i.putExtra( "jsonObject",send_back_missing );
//            startActivity( i );
//        }
        //for going back
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("custom-message");
//                intent.putExtra("unit",cart_item);
//                LocalBroadcastManager.getInstance(CartActivity.this).sendBroadcast(intent);
                Intent i=new Intent(CartActivity.this,MissingActivity.class);
                i.putExtra( "jsonObject",send_back_missing );
                startActivity( i );
            }
        } );

        // for going for payment
        payment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSelectedItems.size()==0)
                {
                    Toast.makeText(CartActivity.this, "Please select any product.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent1 = new Intent( CartActivity.this, PaymentActivity.class );
                    intent1.putExtra( "missing", send_back_missing );
                    intent1.putExtra( "cart_item", jsonString );
                    Gson gson = new Gson();
                    String payment = gson.toJson( currentSelectedItems );
                    intent1.putExtra( "payment", payment );
                    startActivity( intent1 );
                }
            }
        } );

//        sum( cart_item );  //for the price sum
//        setItemCount( cart_item );
//
//        try {
//            initRecyclerView();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
    public void onBackPressed() {
        Intent i=new Intent(CartActivity.this,MissingActivity.class);
        i.putExtra( "jsonObject",send_back_missing );
        startActivity( i );
        super.onBackPressed();
    }
    private  void sum(ArrayList<MissingItembean> pro)
    {

        int i;
        double total=0;

        //price
        for(i=0;i<pro.size();i++)
        {
            System.out.println( "Price index: "+pro.get(i).getPrice() );
            total=total+pro.get(i).getPrice();
        }

        String total_disp="$"+df.format( total );
        total_sum.setText( total_disp );


        //count above price
        if(pro.size()<=1)
        {
            selected.setText( "Total "+pro.size()+" Item" );
        }
        else {
            selected.setText( "Total " + pro.size() + " Items" );
        }
    }
    public void setItemCount(ArrayList<MissingItembean> pro)
    {
        //count
        if(pro.size()==1)
        {
            num_items= pro.size()+" Item";
        }
        else {
            num_items = pro.size() + " Items";
        }
        num.setText( num_items );


        //cart img
        int x=pro.size();
        if(x<=0)
        {
            organge.setVisibility( ConstraintLayout.INVISIBLE );
        }
        else
        {
            organge.setVisibility( ConstraintLayout.VISIBLE );
        }
        String count=Integer.toString( x );
        add_count.setText( count );
    }
    private void initRecyclerView() throws JSONException {

        LinearLayoutManager layoutManager=new LinearLayoutManager( this,LinearLayoutManager.VERTICAL,false );
        RecyclerView recyclerView=findViewById(R.id.product_recycler_cart);
        recyclerView.setLayoutManager( layoutManager );
        Gson gson = new Gson();
        jsonString = gson.toJson( cart_item );
        adapter = new CartAdapter(this, jsonString,new CartAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(MissingItembean item) {
                currentSelectedItems.add(item);
                sum( currentSelectedItems );
            }

            @Override
            public void onItemUncheck(MissingItembean item) {
                currentSelectedItems.remove(item);
                sum( currentSelectedItems );

            }
        });
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack=new RecyclerItemTouchHelper( 0,ItemTouchHelper.LEFT,this );
        new ItemTouchHelper(itemTouchHelperCallBack ).attachToRecyclerView( recyclerView );


    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if(viewHolder instanceof CartAdapter.ViewHolder)
        {
            String name=cart_item.get(viewHolder.getAdapterPosition()).getRawImages().get( 0 );
            detetedItem=cart_item.get(viewHolder.getAdapterPosition());
            final int deleteIndex=viewHolder.getAdapterPosition();

            adapter.removeItem(viewHolder.getAdapterPosition());
            cart_item.remove( deleteIndex );

            if(cart_item.size()==0)
            {
                Intent i=new Intent(CartActivity.this,MissingActivity.class);
                i.putExtra( "jsonObject",send_back_missing );
                startActivity( i );
            }
            removeFromServer(name);
            setItemCount( cart_item );


//            Snackbar snackbar = Snackbar
//                    .make(cs, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    adapter.restoreItem(detetedItem,deleteIndex);
//                    cart_item.add(cart_item.size(),detetedItem);
//                    setItemCount(cart_item);
//
//                }
//            });
//            snackbar.setActionTextColor( Color.YELLOW);
//            snackbar.show();
        }
    }
    public void removeFromServer(String id)
    {
        Util.getUserService().removeCartDetails(hearders,id).enqueue( new Callback<RestResponseBeanCart>(){
            @Override
            public void onResponse(Call<RestResponseBeanCart> call, Response<RestResponseBeanCart> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+getLocalClassName(), "--------Getting cart items--------");
                    RestResponseBeanCart restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        String[] cartIDs;
                        cartIDs=restResponse.getCart();
                        //prevCartPro(cartIDs);
                        Log.d(LOGTAG+getLocalClassName(), "Length of the cart decreased to: "+ cartIDs.length);
                        for(int i=0;i<cartIDs.length;i++)
                        {
                            System.out.println( cartIDs[i] );
                        }

                    } else Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBeanCart> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void prevCartId()
    {
        Util.getUserService().getCartIds(hearders).enqueue( new Callback<RestResponseBeanCart>(){
            @Override
            public void onResponse(Call<RestResponseBeanCart> call, Response<RestResponseBeanCart> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+getLocalClassName(), "--------Getting cart items--------");
                    RestResponseBeanCart restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        cartIDs=restResponse.getCart();
                        prevCartPro(cartIDs);
                        Log.d(LOGTAG+getLocalClassName(), "Length of the cart: "+ cartIDs.length);

                    } else Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBeanCart> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void prevCartPro(String[] id)
    {
        Util.getUserService().getCartDetails(hearders,id).enqueue( new Callback<RestResponseBean<MissingItems>>() {
            @Override
            public void onResponse(Call<RestResponseBean<MissingItems>> call, Response<RestResponseBean<MissingItems>> response) {
                Log.d( LOGTAG,"response"+response.isSuccessful() );
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+getLocalClassName(), "--------Getting Missing items--------");
                    RestResponseBean<MissingItems> restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        ArrayList<MissingItembean> allMissing=new ArrayList<>();
//                        allMissing = restResponse.getObject().getMissingDetails();
                        allMissing=restResponse.getObject().getMissingDetails();
                        Log.d(LOGTAG+getLocalClassName(), "--------Fetched all Missing items from wadrobe--------"+ id.length);

                        for(int i = 0; i< allMissing.size(); i++)
                        {
                            Log.d(LOGTAG,"onResponse: \n"+
                                    "Name: "+ allMissing.get( i ).getTags().getTitle()+"\n"+
                                    "Price: "+ allMissing.get( i ).getRawImages().get(0)+"\n"+
                                    "_______________________________________________\n\n");
                        }
                        for(int i=0;i<id.length;i++)
                        {
                            for(int j=0;j<allMissing.size();j++)
                            {
                                if(id[i].equals( allMissing.get(j).getRawImages().get(0)))
                                {
                                    cart_item.add( allMissing.get(j) );
                                }
                            }
                        }

                        if(cart_item.size()==0)
                        {
                            Intent i=new Intent(CartActivity.this,MissingActivity.class);
                            i.putExtra( "jsonObject",send_back_missing );
                            startActivity( i );
                        }
                        else
                        {
                            sum( cart_item );  //for the price sum
                            setItemCount( cart_item );

                            try {
                                initRecyclerView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } else Toast.makeText(getApplicationContext(), "Please try again. nn", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBean<MissingItems>> call, Throwable t) {
                t.printStackTrace();
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
