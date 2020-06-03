package com.ril.digitalwardrobeAI.View.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.OnSwipeTouchListener;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Fragment.WardrobeFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class WardrobeActivity extends AppCompatActivity {
    ConstraintLayout mainLayout;
    ImageView scanCamera;
    ImageView missing;
    private static final String TAG="Wardrobex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        mainLayout=findViewById(R.id.mainLayout);
        missing=findViewById(R.id.missingItem);
        missing.setVisibility(View.VISIBLE);
        scanCamera=findViewById(R.id.scanCamera);


        WardrobeFragment wardrobeFragment=new WardrobeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_wardrobe,wardrobeFragment).commit();
        scanCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WardrobeActivity.this, BuySellActivity.class);
                startActivity(intent);
                finish();
            }
        });


        missing.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMissing();
            }
        } );


        mainLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

            public void onSwipeLeft(){
                Intent intent=new Intent(getApplicationContext(), BuySellActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.left_swipe);
                finish();

            }



            public boolean onTouch(View v, MotionEvent event) {
                missing.setVisibility(View.GONE);
                return gestureDetector.onTouchEvent(event);
            }
        });


    }

    public void  getMissing(){
        Util.getUserService().getMissing(hearders).enqueue( new Callback<RestResponseBean<MissingItems>>() {
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
                        Intent i=new Intent( WardrobeActivity.this,MissingActivity.class );
                        Gson gson = new Gson();
                        String json = gson.toJson( allMissing );
                        i.putExtra( "jsonObject", json );
                        startActivity(i);
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

}
