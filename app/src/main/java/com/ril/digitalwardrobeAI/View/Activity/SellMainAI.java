package com.ril.digitalwardrobeAI.View.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ril.digitalwardrobeAI.Constants;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.PredictingPrice;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.Task;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class SellMainAI extends AppCompatActivity {
    private static DecimalFormat df = new DecimalFormat("0.00");
    String pop_condition="Excellent";
    String pop_usage="Moderate";
    String pop_size="M";
    int pop_fs=1;
    Double second_cost=0.0;
    String pop_hot= "None";
    String jsonString;
    ImageView back;
    private static final String TAG="SellMailAI";
    Dialog pop;
    public int count=0;
    MissingItembean selected_pro;
    ImageView image;
    ImageView dots,person,drope,post,cross,cancel,done;
    private SeekBar seekBar;
    int inc=0,MAX=200;
    Timer timer;
    CardView card1;
    GifImageView load;
    TextView  leftText,rightText,heading,casual,pop_casual;
    boolean gotServerResponse=false;
    TextView max,min,title_pop,price,wear,plus,minus,TS_rare_text,TS_new_text,TS_drop_text,Condition_excellent_text,Size_l_text,Size_m_text,Size_s_text,Condition_brand_text,Condition_minor_text,Sale_yes_text,Sale_no_text,org_price,Usage_rare_text,Usage_mod_text,Usage_fre_text;
    ConstraintLayout sell,TS_rare,TS_new,TS_drop,Condition_excellent,Condition_brand,Condition_minor,Sale_yes,Sale_no,Usage_rare,Usage_mod,Usage_fre,Size_s,Size_l,Size_m,Main_s,Main_m,Main_l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sell_main_ai );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra("selected");
        selected_pro= new Gson().fromJson( jsonString,new TypeToken<MissingItembean>(){}.getType() );
        image=(ImageView)findViewById( R.id.pro_img );
        dots=findViewById( R.id.dots );
        back=findViewById( R.id.buyback );
        person=findViewById( R.id.person );
        drope=findViewById( R.id.drope );
        post=findViewById( R.id.post );
        price=findViewById( R.id.ai_price );
        org_price=findViewById( R.id.original_price );
        leftText=findViewById( R.id.min_sick );
        rightText=findViewById( R.id.max_sick );
        heading=findViewById( R.id.pro_name );
        seekBar=findViewById( R.id.seekBar );
        casual=findViewById( R.id.lastTag );
        Main_s=findViewById( R.id.main_s );
        Main_m=findViewById( R.id.main_m );
        Main_l=findViewById( R.id.main_l );
        sell=findViewById( R.id.orange_layout );
        card1=findViewById(R.id.card1);
        load=findViewById(R.id.load_gif);


        seekBar.setMax(200);

        seekbarAnimation();


        Main_s.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_size="S";
                Main_s.setBackgroundResource( R.drawable.orange_circle );
                Main_m.setBackgroundResource( R.drawable.grey_circle );
                Main_l.setBackgroundResource( R.drawable.grey_circle );
            }
        } );
        Main_m.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_size="M";
                Main_m.setBackgroundResource( R.drawable.orange_circle );
                Main_s.setBackgroundResource( R.drawable.grey_circle );
                Main_l.setBackgroundResource( R.drawable.grey_circle );
            }
        } );
        Main_l.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_size="L";
                Main_l.setBackgroundResource( R.drawable.orange_circle );
                Main_m.setBackgroundResource( R.drawable.grey_circle );
                Main_s.setBackgroundResource( R.drawable.grey_circle );
            }
        } );
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

        casual.setText( selected_pro.getOccasion() );
        heading.setText( selected_pro.getManual_desc() );
        org_price.setText( "$"+df.format( selected_pro.getPrice() ));
        final String imageName=selected_pro.getRawImages().get(0);
        String img_url=ROOT_URL+"sell/"+imageName;
        Picasso.get().load( img_url).into(image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMissing("noAction");
            }
        });

        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop=new Dialog( SellMainAI.this );
                pop.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                pop.setContentView( R.layout.pricing_tags_popup );
                title_pop=pop.findViewById( R.id.pop_title );
                cross=pop.findViewById( R.id.pop_cross);
//                plus=pop.findViewById( R.id.plus );
//                minus=pop.findViewById( R.id.minus );
//                wear=pop.findViewById( R.id.num );
                cancel=pop.findViewById( R.id.cancel );
                done=pop.findViewById( R.id.done );
                TS_new=pop.findViewById( R.id.trend_tag2 );
                TS_new_text=pop.findViewById( R.id.trend_tag2_text );
                TS_rare=pop.findViewById( R.id.trend_tag3 );
                TS_rare_text=pop.findViewById( R.id.trend_tag3_text );
                TS_drop=pop.findViewById( R.id.trend_tag4 );
                TS_drop_text=pop.findViewById( R.id.trend_tag4_text );
                Condition_excellent=pop.findViewById( R.id.condition_tag1 );
                Condition_excellent_text=pop.findViewById( R.id.condition_tag1_text );
                Condition_brand=pop.findViewById( R.id.condition_tag2 );
                Condition_brand_text=pop.findViewById( R.id.condition_tag2_text );
                Condition_minor=pop.findViewById( R.id.condition_tag3 );
                Condition_minor_text=pop.findViewById( R.id.condition_tag3_text );
                Sale_yes=pop.findViewById( R.id.sale_tag1 );
                Sale_yes_text=pop.findViewById( R.id.sale_tag1_text );
                Sale_no=pop.findViewById( R.id.sale_tag2 );
                Sale_no_text=pop.findViewById( R.id.sale_tag2_text );
                Usage_rare=pop.findViewById( R.id.usage_tag1 );
                Usage_rare_text=pop.findViewById( R.id.usage_tag1_text );
                Usage_mod=pop.findViewById( R.id.usage_tag2 );
                Usage_mod_text=pop.findViewById( R.id.usage_tag2_text );
                Usage_fre=pop.findViewById( R.id.usage_tag3 );
                Usage_fre_text=pop.findViewById( R.id.usage_tag3_text );
                pop_casual=pop.findViewById( R.id.pop_type );
                Size_s=pop.findViewById( R.id.size_tag1 );
                Size_s_text=pop.findViewById( R.id.size_tag1_text );
                Size_m=pop.findViewById( R.id.size_tag2 );
                Size_m_text=pop.findViewById( R.id.size_tag2_text );
                Size_l=pop.findViewById( R.id.size_tag3 );
                Size_l_text=pop.findViewById( R.id.size_tag3_text );



                pop.show();

                String count1=Integer.toString( count );
//                wear.setText( count1 );

                title_pop.setText( selected_pro.getManual_desc() );
                pop_casual.setText( selected_pro.getOccasion() );

                popup_button(pop_hot,pop_condition,pop_fs,pop_usage,pop_size);



                Size_s.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_size="S";
                        Main_s.setBackgroundResource( R.drawable.orange_circle );
                        Main_m.setBackgroundResource( R.drawable.grey_circle );
                        Main_l.setBackgroundResource( R.drawable.grey_circle );
                        Size_s.setBackgroundResource( R.drawable.tags_back_blue );
                        Size_s_text.setTextColor( getResources().getColor( R.color.white ));
                        Size_m.setBackgroundResource( R.drawable.tags_back );
                        Size_m_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Size_l.setBackgroundResource( R.drawable.tags_back );
                        Size_l_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Size_m.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_size="M";
                        Main_s.setBackgroundResource( R.drawable.grey_circle );
                        Main_m.setBackgroundResource( R.drawable.orange_circle );
                        Main_l.setBackgroundResource( R.drawable.grey_circle );
                        Size_m.setBackgroundResource( R.drawable.tags_back_blue );
                        Size_m_text.setTextColor( getResources().getColor( R.color.white ));
                        Size_s.setBackgroundResource( R.drawable.tags_back );
                        Size_s_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Size_l.setBackgroundResource( R.drawable.tags_back );
                        Size_l_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Size_l.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_size="L";
                        Main_s.setBackgroundResource( R.drawable.grey_circle );
                        Main_m.setBackgroundResource( R.drawable.grey_circle );
                        Main_l.setBackgroundResource( R.drawable.orange_circle );
                        Size_l.setBackgroundResource( R.drawable.tags_back_blue );
                        Size_l_text.setTextColor( getResources().getColor( R.color.white ));
                        Size_s.setBackgroundResource( R.drawable.tags_back );
                        Size_s_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Size_m.setBackgroundResource( R.drawable.tags_back );
                        Size_m_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                TS_new.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_hot="None";
                        TS_new.setBackgroundResource( R.drawable.tags_back_blue );
                        TS_new_text.setTextColor( getResources().getColor( R.color.white ));
                        TS_rare.setBackgroundResource( R.drawable.tags_back );
                        TS_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
                        TS_drop.setBackgroundResource( R.drawable.tags_back );
                        TS_drop_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                TS_rare.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_hot="rare find";
                        TS_rare.setBackgroundResource( R.drawable.tags_back_blue );
                        TS_rare_text.setTextColor( getResources().getColor( R.color.white ));
                        TS_drop.setBackgroundResource( R.drawable.tags_back );
                        TS_drop_text.setTextColor( getResources().getColor( R.color.subtext ));
                        TS_new.setBackgroundResource( R.drawable.tags_back );
                        TS_new_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                TS_drop.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_hot="price drop";
                        TS_drop.setBackgroundResource( R.drawable.tags_back_blue );
                        TS_drop_text.setTextColor( getResources().getColor( R.color.white ));
                        TS_rare.setBackgroundResource( R.drawable.tags_back );
                        TS_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
                        TS_new.setBackgroundResource( R.drawable.tags_back );
                        TS_new_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Condition_excellent.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_condition="Excellent";
                        Condition_excellent.setBackgroundResource( R.drawable.tags_back_blue );
                        Condition_excellent_text.setTextColor( getResources().getColor( R.color.white ));
                        Condition_brand.setBackgroundResource( R.drawable.tags_back );
                        Condition_brand_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Condition_minor.setBackgroundResource( R.drawable.tags_back );
                        Condition_minor_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Condition_brand.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_condition="Brand New";
                        Condition_brand.setBackgroundResource( R.drawable.tags_back_blue );
                        Condition_brand_text.setTextColor( getResources().getColor( R.color.white ));
                        Condition_excellent.setBackgroundResource( R.drawable.tags_back );
                        Condition_excellent_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Condition_minor.setBackgroundResource( R.drawable.tags_back );
                        Condition_minor_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Condition_minor.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_condition="Minor Fades";
                        Condition_minor.setBackgroundResource( R.drawable.tags_back_blue );
                        Condition_minor_text.setTextColor( getResources().getColor( R.color.white ));
                        Condition_excellent.setBackgroundResource( R.drawable.tags_back );
                        Condition_excellent_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Condition_brand.setBackgroundResource( R.drawable.tags_back );
                        Condition_brand_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Sale_yes.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_fs=1;
                        Sale_yes.setBackgroundResource( R.drawable.tags_back_blue );
                        Sale_yes_text.setTextColor( getResources().getColor( R.color.white ));
                        Sale_no.setBackgroundResource( R.drawable.tags_back );
                        Sale_no_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Sale_no.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_fs=0;
                        Sale_no.setBackgroundResource( R.drawable.tags_back_blue );
                        Sale_no_text.setTextColor( getResources().getColor( R.color.white ));
                        Sale_yes.setBackgroundResource( R.drawable.tags_back );
                        Sale_yes_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Usage_rare.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_usage="Rare";
                        Usage_rare.setBackgroundResource( R.drawable.tags_back_blue );
                        Usage_rare_text.setTextColor( getResources().getColor( R.color.white ));
                        Usage_mod.setBackgroundResource( R.drawable.tags_back );
                        Usage_mod_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Usage_fre.setBackgroundResource( R.drawable.tags_back );
                        Usage_fre_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Usage_mod.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_usage="Moderate";
                        Usage_mod.setBackgroundResource( R.drawable.tags_back_blue );
                        Usage_mod_text.setTextColor( getResources().getColor( R.color.white ));
                        Usage_rare.setBackgroundResource( R.drawable.tags_back );
                        Usage_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Usage_fre.setBackgroundResource( R.drawable.tags_back );
                        Usage_fre_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
                Usage_fre.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_usage="Frequently";
                        Usage_fre.setBackgroundResource( R.drawable.tags_back_blue );
                        Usage_fre_text.setTextColor( getResources().getColor( R.color.white ));
                        Usage_rare.setBackgroundResource( R.drawable.tags_back );
                        Usage_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
                        Usage_mod.setBackgroundResource( R.drawable.tags_back );
                        Usage_mod_text.setTextColor( getResources().getColor( R.color.subtext ));
                    }
                } );
//                minus.setOnClickListener( new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(count>0)
//                        {
//                            count--;
//                            String count1=Integer.toString( count );
//                            wear.setText( count1 );
//                        }
//                    }
//                } );
//                plus.setOnClickListener( new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                            count++;
//                            String count1=Integer.toString( count );
//                            wear.setText( count1 );
//                    }
//                } );


                cross.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                } );
                cancel.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                } );
                done.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call the api here
                        String path="../Node_server/server/public/sell/"+selected_pro.getRawImages().get(0);
                        Double marketPrice=selected_pro.getPrice();
                        card1.setVisibility(View.INVISIBLE);
                        load.setVisibility(View.VISIBLE);
                        jsonParse_pop(path,pop_condition,marketPrice,pop_fs,pop_hot);
                        pop.dismiss();
                    }
                } );
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(second_cost==0.0)
                {
                    Toast.makeText(getApplicationContext(), "Wait for the New Price", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getMissing( selected_pro.getRawImages().get( 0 ) );
                }

            }
        });

        String path="../Node_server/server/public/sell/"+selected_pro.getRawImages().get(0);
        String condition="Excellent";
        Double marketPrice=selected_pro.getPrice();
        jsonParse(path,condition,marketPrice,1,"None");

    }

    private void jsonParse(String path,String condition, Double marketPrice,int fs, String hot)
    {

        Task task=new Task( path,condition,marketPrice,fs,hot);
        Util.getUserService().getPricingFactors(task).enqueue( new Callback<PredictingPrice>() {
            @Override
            public void onResponse(Call<PredictingPrice> call, Response<PredictingPrice> response) {
                if (response.isSuccessful()) {
                    PredictingPrice restResponse = response.body();
                    //for seekbar
                    second_cost=restResponse.getPricingJson();
                    gotServerResponse=true;
                    //setSeekbar(roundToTwoDecimal(second_cost,2));
                    String pp="$"+restResponse.getPricingJson();
                    //price.setText( pp );
//                    Toast.makeText(getApplicationContext(), "Selling Price: "+pp, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PredictingPrice> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void jsonParse_pop(String path,String condition, Double marketPrice,int fs, String hot)
    {

        Task task=new Task( path,condition,marketPrice,fs,hot);
        Util.getUserService().getPricingFactors(task).enqueue( new Callback<PredictingPrice>() {
            @Override
            public void onResponse(Call<PredictingPrice> call, Response<PredictingPrice> response) {
                if (response.isSuccessful()) {
                    PredictingPrice restResponse = response.body();
                    card1.setVisibility(View.VISIBLE);
                    load.setVisibility(View.INVISIBLE);
                    //for seekbar
                    second_cost=restResponse.getPricingJson();
                    gotServerResponse=true;
                    setSeekbar(roundToTwoDecimal(second_cost,2));
                    String pp="$"+df.format( restResponse.getPricingJson() );
                    //price.setText( pp );
//                    Toast.makeText(getApplicationContext(), "Selling Price: "+pp, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PredictingPrice> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void  getMissing(String value){
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
                        if(!value.equals( "noAction" ) && second_cost!=0.0)
                        {
                            Toast.makeText(getApplicationContext(), "Item on Sale", Toast.LENGTH_SHORT).show();
                        }
                        Intent i=new Intent( SellMainAI.this,MissingActivity.class );
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

    public void popup_button(String hot, String condition, int fs, String usage, String Size)
    {

        if(Size.equals( "S" ))
        {
            Size_s.setBackgroundResource( R.drawable.tags_back_blue );
            Size_s_text.setTextColor( getResources().getColor( R.color.white ));
            Size_m.setBackgroundResource( R.drawable.tags_back );
            Size_m_text.setTextColor( getResources().getColor( R.color.subtext ));
            Size_l.setBackgroundResource( R.drawable.tags_back );
            Size_l_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if(Size.equals( "M" ))
        {
            Size_m.setBackgroundResource( R.drawable.tags_back_blue );
            Size_m_text.setTextColor( getResources().getColor( R.color.white ));
            Size_s.setBackgroundResource( R.drawable.tags_back );
            Size_s_text.setTextColor( getResources().getColor( R.color.subtext ));
            Size_l.setBackgroundResource( R.drawable.tags_back );
            Size_l_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else
        {
            Size_l.setBackgroundResource( R.drawable.tags_back_blue );
            Size_l_text.setTextColor( getResources().getColor( R.color.white ));
            Size_s.setBackgroundResource( R.drawable.tags_back );
            Size_s_text.setTextColor( getResources().getColor( R.color.subtext ));
            Size_m.setBackgroundResource( R.drawable.tags_back );
            Size_m_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        if(fs==0)
        {
            Sale_no.setBackgroundResource( R.drawable.tags_back_blue );
            Sale_no_text.setTextColor( getResources().getColor( R.color.white ));
            Sale_yes.setBackgroundResource( R.drawable.tags_back );
            Sale_yes_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else
        {
            Sale_yes.setBackgroundResource( R.drawable.tags_back_blue );
            Sale_yes_text.setTextColor( getResources().getColor( R.color.white ));
            Sale_no.setBackgroundResource( R.drawable.tags_back );
            Sale_no_text.setTextColor( getResources().getColor( R.color.subtext ));
        }


        if(condition.equals( "Excellent" ))
        {
            Condition_excellent.setBackgroundResource( R.drawable.tags_back_blue );
            Condition_excellent_text.setTextColor( getResources().getColor( R.color.white ));
            Condition_brand.setBackgroundResource( R.drawable.tags_back );
            Condition_brand_text.setTextColor( getResources().getColor( R.color.subtext ));
            Condition_minor.setBackgroundResource( R.drawable.tags_back );
            Condition_minor_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if(condition.equals( "Brand New" ))
        {
            Condition_brand.setBackgroundResource( R.drawable.tags_back_blue );
            Condition_brand_text.setTextColor( getResources().getColor( R.color.white ));
            Condition_excellent.setBackgroundResource( R.drawable.tags_back );
            Condition_excellent_text.setTextColor( getResources().getColor( R.color.subtext ));
            Condition_minor.setBackgroundResource( R.drawable.tags_back );
            Condition_minor_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else
        {
            Condition_minor.setBackgroundResource( R.drawable.tags_back_blue );
            Condition_minor_text.setTextColor( getResources().getColor( R.color.white ));
            Condition_excellent.setBackgroundResource( R.drawable.tags_back );
            Condition_excellent_text.setTextColor( getResources().getColor( R.color.subtext ));
            Condition_brand.setBackgroundResource( R.drawable.tags_back );
            Condition_brand_text.setTextColor( getResources().getColor( R.color.subtext ));
        }


         if(hot.equals( "None" ))
        {
            TS_new.setBackgroundResource( R.drawable.tags_back_blue );
            TS_new_text.setTextColor( getResources().getColor( R.color.white ));
            TS_rare.setBackgroundResource( R.drawable.tags_back );
            TS_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
            TS_drop.setBackgroundResource( R.drawable.tags_back );
            TS_drop_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if(hot.equals( "rare find" ))
        {
            TS_rare.setBackgroundResource( R.drawable.tags_back_blue );
            TS_rare_text.setTextColor( getResources().getColor( R.color.white ));
            TS_drop.setBackgroundResource( R.drawable.tags_back );
            TS_drop_text.setTextColor( getResources().getColor( R.color.subtext ));
            TS_new.setBackgroundResource( R.drawable.tags_back );
            TS_new_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else
        {
            TS_drop.setBackgroundResource( R.drawable.tags_back_blue );
            TS_drop_text.setTextColor( getResources().getColor( R.color.white ));
            TS_rare.setBackgroundResource( R.drawable.tags_back );
            TS_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
            TS_new.setBackgroundResource( R.drawable.tags_back );
            TS_new_text.setTextColor( getResources().getColor( R.color.subtext ));
        }

        if(usage.equals( "Rare" ))
        {
            Usage_rare.setBackgroundResource( R.drawable.tags_back_blue );
            Usage_rare_text.setTextColor( getResources().getColor( R.color.white ));
            Usage_mod.setBackgroundResource( R.drawable.tags_back );
            Usage_mod_text.setTextColor( getResources().getColor( R.color.subtext ));
            Usage_fre.setBackgroundResource( R.drawable.tags_back );
            Usage_fre_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if (usage.equals( "Moderate" ))
        {
            Usage_mod.setBackgroundResource( R.drawable.tags_back_blue );
            Usage_mod_text.setTextColor( getResources().getColor( R.color.white ));
            Usage_rare.setBackgroundResource( R.drawable.tags_back );
            Usage_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
            Usage_fre.setBackgroundResource( R.drawable.tags_back );
            Usage_fre_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else
        {
            Usage_fre.setBackgroundResource( R.drawable.tags_back_blue );
            Usage_fre_text.setTextColor( getResources().getColor( R.color.white ));
            Usage_rare.setBackgroundResource( R.drawable.tags_back );
            Usage_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
            Usage_mod.setBackgroundResource( R.drawable.tags_back );
            Usage_mod_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
    }

    public void seekbarAnimation()
    {
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                Log.d("Seek Bar "  ,"inc :  " +inc);
                if(inc>=0&&inc<MAX ) {
                    seekBar.setProgress(inc);
                }
                else  if(inc>=MAX&&inc<(MAX*2) ) {
                    int j=(MAX*2)-inc;
                    seekBar.setProgress(j);
                }
                else if(inc>=(MAX*2)&&inc<(MAX*3)) {
                    int j=inc-(MAX*2);
                    if(j<MAX/2)
                        seekBar.setProgress(j);
                }
                else if(inc>=(MAX*3)&&gotServerResponse) {//gotServer response is set to true when response is received from server
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //replace value with the PRICE value from server
                            setSeekbar(roundToTwoDecimal(second_cost,2)); }
                    });
                    timer.cancel();
                }
                inc=inc+1;
            }
        },0,10);
    }
    private void setSeekbar(double PRICE) {
        final double min=PRICE-3.00;
        double max=PRICE+3.00;
        seekBar.setMax((int) (max-min));
        sell.setBackgroundResource( R.drawable.square_sell_active);
        Toast.makeText(getApplicationContext(), "Selling Price: $"+df.format(roundToTwoDecimal( PRICE,2 )), Toast.LENGTH_SHORT).show();
        price.setText("$"+df.format(roundToTwoDecimal( PRICE,2 )));
        leftText.setText("$"+df.format( roundToTwoDecimal( min,2 ) ));
        rightText.setText("$"+df.format( roundToTwoDecimal( max,2 ) ));
//        progressText.setText(String.valueOf(PRICE));
        seekBar.setProgress((int)(max-min)/2);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                double seekValue=progress+min;
                   price.setText("$"+df.format(roundToTwoDecimal( seekValue,2 )));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public static double roundToTwoDecimal(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    @Override
    public void onBackPressed() {
        getMissing("noAction");
    }
}
