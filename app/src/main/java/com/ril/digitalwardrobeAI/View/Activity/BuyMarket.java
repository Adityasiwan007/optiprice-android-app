package com.ril.digitalwardrobeAI.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class BuyMarket extends AppCompatActivity {
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final String TAG="BuyMarketAI";
    ImageView post,person,drope,image,back;
    TextView casual,heading,org_price,review_txt,reviewer;
    MissingItembean selected_pro;
    ImageView star1,star2,star3,star4,star5;
    ImageView[] stars_id = new ImageView[5];
    String jsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_buy_market );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra("selected");
        selected_pro= new Gson().fromJson( jsonString,new TypeToken<MissingItembean>(){}.getType() );

        person=findViewById( R.id.person );
        drope=findViewById( R.id.drope );
        post=findViewById( R.id.post );
        image=(ImageView)findViewById( R.id.pic );
        heading=findViewById( R.id.pro_name );
        casual=findViewById( R.id.casual );
        org_price=findViewById( R.id.price_txt );
        back=findViewById( R.id.buyback );
        review_txt=findViewById( R.id.review_text );
        reviewer=findViewById( R.id.reviewer );
        star1=(ImageView)findViewById( R.id.s1 );
        star2=(ImageView)findViewById( R.id.s2 );
        star3=(ImageView)findViewById( R.id.s3 );
        star4=(ImageView)findViewById( R.id.s4 );
        star5=(ImageView)findViewById( R.id.s5 );
        stars_id[0]=star1;stars_id[1]=star2;stars_id[2]=star3;stars_id[3]=star4;stars_id[4]=star5;


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBuying( "noAction" );
            }
        });
        double rate=selected_pro.getRating();
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
        reviewer.setText( selected_pro.getSeller() );
        review_txt.setText(selected_pro.getReview());
        casual.setText( selected_pro.getOccasion());
        heading.setText( selected_pro.getManual_desc() );
        org_price.setText( "$"+df.format( selected_pro.getPrice() ));
        final String imageName=selected_pro.getRawImages().get(0);
        String img_url=ROOT_URL+"buy/"+imageName;
        Picasso.get().load( img_url).into(image);

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person.setImageResource( R.drawable.in_person_on );
                drope.setImageResource( R.drawable.drope_off );
                post.setImageResource( R.drawable.post_off );
            }
        });
        drope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setImageResource( R.drawable.in_person_off );
                drope.setImageResource( R.drawable.drope_on );
                post.setImageResource( R.drawable.post_off );
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setImageResource( R.drawable.in_person_off );
                drope.setImageResource( R.drawable.drope_off );
                post.setImageResource( R.drawable.post_on );
            }
        });

    }
    public void  getBuying(String value){
        Util.getUserService().getBuying(hearders).enqueue( new Callback<RestResponseBean<MissingItems>>() {
            @Override
            public void onResponse(Call<RestResponseBean<MissingItems>> call, Response<RestResponseBean<MissingItems>> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+getLocalClassName(), "--------Getting Missing items--------");
                    RestResponseBean<MissingItems> restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        ArrayList<MissingItembean> allMissing=new ArrayList<>();
//                        allMissing = restResponse.getObject().getMissingDetails();
                        allMissing=restResponse.getObject().getMissingDetails();
                        Log.d(LOGTAG+getLocalClassName(), "--------Fetched all Missing items from wadrobe--------"+ allMissing.size());

                        for(int i = 0; i< allMissing.size(); i++)
                        {
                            Log.d(TAG,"onResponse: \n"+
                                    "Name: "+ allMissing.get( i ).getTags().getTitle()+"\n"+
                                    "Price: "+ allMissing.get( i ).getRawImages().get(0)+"\n"+
                                    "_______________________________________________\n\n");
                        }
                        Log.d(LOGTAG+getLocalClassName(),"---------Go to MissingItem screen-----");
                        if(!value.equals( "noAction" ))
                        {
                            Toast.makeText(getApplicationContext(), "Item on Sale", Toast.LENGTH_SHORT).show();
                        }
                        Intent i=new Intent( BuyMarket.this,BuyingGridActivity.class );
                        Gson gson = new Gson();
                        String json = gson.toJson( allMissing );
                        i.putExtra( "jsonObject", json );
                        i.putExtra( "status",value );
                        startActivity(i);
                        finish();
                    } else Toast.makeText(getApplicationContext(), "Please try again. nn", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBean<MissingItems>> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        getBuying( "noAction" );
    }
}
