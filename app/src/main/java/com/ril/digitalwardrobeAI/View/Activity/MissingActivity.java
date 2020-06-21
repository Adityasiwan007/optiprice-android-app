package com.ril.digitalwardrobeAI.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Adapter.MissingAdapter;
import com.ril.digitalwardrobeAI.View.Adapter.MissingGridAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class MissingActivity extends AppCompatActivity{

    ConstraintLayout organge;
    String sell_id;
    int i,status;
    public static ArrayList<String> sold=new ArrayList<String>();
    public static ArrayList<String> sold1=new ArrayList<String>();
    String jsonString;
    private TextView number;
    private ImageView back,cart;
    public GridView grid;
    ArrayList<String> colorList;
    public String[] cartIDs;
    LinkedHashMap<String, ArrayList<MissingItembean>> productMap;
    private static final String TAG="MissingScreen";
    private MissingItembean selected_pro;
    public ArrayList<MissingItembean> missingPro;
    //public ArrayList<String> cartIDs=new ArrayList<String>();
    //String[] cartIDs;
    public ArrayList<MissingItembean> cart_item = new ArrayList<MissingItembean>();
    TextView add_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_missing );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra("jsonObject");
        sell_id=intent.getStringExtra( "status" );
        add_count=findViewById( R.id.pro_text );
        if(!sell_id.equals( "noAction" ))
        {
            sold.add( sell_id );
        }
        sold1 = removeDuplicates(sold);
        System.out.println( "AdityaS1 "+sold );
        System.out.println( "AdityaS2 "+sold1 );
        //gridView=findViewById( R.id.grid_view );
//        cart=(ImageView)findViewById( R.id.cart );
//        back=(ImageView)findViewById( R.id.missingback);
//        number=(TextView)findViewById( R.id.num );
//        add_count=(TextView)findViewById( R.id.add_count );
//        organge=(ConstraintLayout) findViewById( R.id.count_layout );
          grid=findViewById(R.id.grid_view);
//        back.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(MissingActivity.this,WardrobeActivity.class);
//                startActivity( i );
//            }
//        } );


        //prevCartId();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));
//        cart.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(cart_item==null)
//                {
//                    Toast.makeText(getApplicationContext(),"No any item added to cart.",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Intent i = new Intent( MissingActivity.this, CartActivity.class );
//                    Gson gson = new Gson();
//                    String cart_pro=gson.toJson(cart_item);
//                    i.putExtra( "cart_product", cart_pro );
//                    i.putExtra( "missing",jsonString );
//                    startActivity( i );
//                }
//
//            }
//        } );
//
        try {
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



private void initRecyclerView() throws JSONException {
    missingPro= new Gson().fromJson( jsonString,new TypeToken<ArrayList<MissingItembean>>(){}.getType() );
//    number=findViewById( R.id.num );
    add_count.setText("Total "+missingPro.size()+" Items");

//    LinearLayoutManager layoutManager=new LinearLayoutManager( MissingActivity.this );
//    RecyclerView recyclerView=findViewById(R.id.product_recycler);
//    recyclerView.setLayoutManager( layoutManager );
//    MissingAdapter adapter=new MissingAdapter( this,jsonString);
//    recyclerView.setAdapter(adapter);

//    System.out.println( "Aditya "+missingPro.size() );
        MissingGridAdapter adapter=new MissingGridAdapter(this,missingPro,sold1);
        grid.setAdapter( adapter );


    grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selected_pro=missingPro.get( position );
            status=0;
            for(i=0;i<sold1.size();i++)
            {
                if(selected_pro.getRawImages().get(0).equals(sold1.get(i)))
                {
                    Toast.makeText(getApplicationContext(),"Item already in sale",Toast.LENGTH_SHORT).show();
                    status=1;
                    break;
                }
            }
            if(status==0)
            {
                Intent i = new Intent(MissingActivity.this, SellMainAI.class);
                Gson gson = new Gson();
                String select = gson.toJson(selected_pro);
                i.putExtra("selected", select);
                startActivity(i);
            }
        }
    } );
}


//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String broadPro = (String) intent.getSerializableExtra("unit");
//            cart_item= overAllPro( broadPro );
//            cart.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(cart_item.size()==0)
//                    {
//                        Toast.makeText(getApplicationContext(),"No any item added to cart.",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Intent i = new Intent( MissingActivity.this, CartActivity.class );
//                        Gson gson = new Gson();
//                        String cart_pro=gson.toJson(cart_item);
//                        i.putExtra( "cart_product", cart_pro );
//                        i.putExtra( "missing",jsonString );
//                        startActivity( i );
//                    }
//
//                }
//            } );
//
//        }
//    };

//    public ArrayList<MissingItembean> overAllPro(String newPro)
//    {
//        if(!newPro.equals( "xyz" ))  //work only when new product is clicked
//        {
//            //addToServer(newPro);
//        }
//
//        int i;
//        for(i=0;i<missingPro.size();i++)
//        {
//            if(missingPro.get(i).getRawImages().get(0).equals( newPro ))
//            {
//                cart_item.add( missingPro.get(i) );
//            }
//        }
//
//
//        int x=cart_item.size();
//        if(x<=0)
//        {
//            organge.setVisibility( ConstraintLayout.INVISIBLE );
//        }
//        else
//        {
//            organge.setVisibility( ConstraintLayout.VISIBLE );
//        }
//        String count=Integer.toString( x );
//        add_count.setText( count );
//     return cart_item;
//    }

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

                        //prevCartPro(cartIDs);
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
//    public void prevCartPro(String[] id)
//    {
//        Util.getUserService().getCartDetails(hearders,id).enqueue( new Callback<RestResponseBean<MissingItems>>() {
//            @Override
//            public void onResponse(Call<RestResponseBean<MissingItems>> call, Response<RestResponseBean<MissingItems>> response) {
//                Log.d( LOGTAG,"response"+response.isSuccessful() );
//                if (response.isSuccessful()) {
//                    Log.d(LOGTAG+getLocalClassName(), "--------Getting Missing items--------");
//                    RestResponseBean<MissingItems> restResponse = response.body();
//                    if (restResponse.isSuccess()) {
//                        ArrayList<MissingItembean> allMissing=new ArrayList<>();
////                        allMissing = restResponse.getObject().getMissingDetails();
//                        allMissing=restResponse.getObject().getMissingDetails();
//                        Log.d(LOGTAG+getLocalClassName(), "--------Fetched all Missing items from wadrobe--------"+ id.length);
//
//                        for(int i = 0; i< allMissing.size(); i++)
//                        {
//                            Log.d(TAG,"onResponse: \n"+
//                                    "Name: "+ allMissing.get( i ).getTags().getTitle()+"\n"+
//                                    "Price: "+ allMissing.get( i ).getRawImages().get(0)+"\n"+
//                                    "_______________________________________________\n\n");
//                        }
//                        for(int i=0;i<id.length;i++)
//                        {
//                            for(int j=0;j<allMissing.size();j++)
//                            {
//                                if(id[i].equals( allMissing.get(j).getRawImages().get(0)))
//                                {
//                                    cart_item.add( allMissing.get(j) );
//                                }
//                            }
//                        }
//                        overAllPro( "xyz" );  //not calling this fuction in the above double for loop because some of the Previous ID might not be there in the recent missing items.
//
//                    } else Toast.makeText(getApplicationContext(), "Please try again. nn", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<RestResponseBean<MissingItems>> call, Throwable t) {
//                t.printStackTrace();
//                t.printStackTrace();
//                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
//                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public void addToServer(String id)
//    {
//        Util.getUserService().addCartDetails(hearders,id).enqueue( new Callback<RestResponseBeanCart>(){
//            @Override
//            public void onResponse(Call<RestResponseBeanCart> call, Response<RestResponseBeanCart> response) {
//                if (response.isSuccessful()) {
//                    Log.d(LOGTAG+getLocalClassName(), "--------Getting cart items--------");
//                    RestResponseBeanCart restResponse = response.body();
//                    if (restResponse.isSuccess()) {
//                        String[] cartIDs;
//                        cartIDs=restResponse.getCart();
//                        //prevCartPro(cartIDs);
//                        Log.d(LOGTAG+getLocalClassName(), "Length of the cart: "+ cartIDs.length);
//                        for(int i=0;i<cartIDs.length;i++)
//                        {
//                            System.out.println( cartIDs[i] );
//                        }
//
//                    } else Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<RestResponseBeanCart> call, Throwable t) {
//                t.printStackTrace();
//                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
//                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    @Override
    public void onBackPressed() {
        Intent intent1=new Intent( MissingActivity.this,BuySellActivity.class );
        //Intent intent1=new Intent( MissingActivity.this,LoginActivity.class );
        startActivity( intent1 );
        super.onBackPressed();
    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}
