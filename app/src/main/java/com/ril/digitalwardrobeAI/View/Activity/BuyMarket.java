package com.ril.digitalwardrobeAI.View.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    Context context;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final String TAG="BuyMarketAI";
    public ArrayList<MissingItembean> currentSelectedItems = new ArrayList<MissingItembean>();
    ImageView post,person,drope,image,back,cart,dots,cross;
    TextView casual,heading,org_price,review_txt,reviewer,add_cart_count,main_size_text;
    MissingItembean selected_pro;
    ImageView star1,star2,star3,star4,star5;
    ImageView[] stars_id = new ImageView[5];
    String selectedString,cartString,send_back_missing;
    ConstraintLayout buy;
    String[] cartIDs;
    Dialog pop;
    EditText msg;
    int i,flag=0;
    ConstraintLayout organge,demo_off,review_off,msg_off,wallet_off,demo_field,review_field,msg_field,wallet_field;
    TextView max,min,title_pop,price,wear,plus,minus,TS_rare_text,pop_casual,TS_new_text,TS_drop_text,Condition_excellent_text,Size_l_text,Size_m_text,Size_s_text,Condition_brand_text,Condition_minor_text,Sale_yes_text,Sale_no_text,Usage_rare_text,Usage_mod_text,Usage_fre_text;
    ConstraintLayout proceed,send_layout,TS_rare,TS_new,TS_drop,Condition_excellent,Condition_brand,Condition_minor,Sale_yes,Sale_no,Usage_rare,Usage_mod,Usage_fre,Size_s,Size_l,Size_m,Main_s,Main_m,Main_l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_buy_market );
        Intent intent = getIntent();
        selectedString = intent.getStringExtra("selected");
        send_back_missing=intent.getStringExtra( "missing" );
        selected_pro= new Gson().fromJson( selectedString,new TypeToken<MissingItembean>(){}.getType() );

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
        buy=findViewById(R.id.buyNow);
        add_cart_count=(TextView)findViewById( R.id.add_count );
        organge=(ConstraintLayout) findViewById( R.id.count_layout );
        cart=findViewById(R.id.cart);
        demo_off=findViewById(R.id.demo_off);
        review_off=findViewById(R.id.review_off);
        msg_off=findViewById(R.id.msg_off);
        wallet_off=findViewById(R.id.pay_off);
        demo_field=findViewById(R.id.demo_field);
        review_field=findViewById(R.id.review_field);
        msg_field=findViewById(R.id.msg_field);
        wallet_field=findViewById(R.id.wallet_field);
        dots=findViewById(R.id.dots);
        main_size_text=findViewById(R.id.main_size_text);
        proceed=findViewById(R.id.proceed_layout);
        send_layout=findViewById(R.id.send_layout);
        msg=findViewById(R.id.msg_dialog);

        prevCartId();
        send_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.getText().clear();
                Toast.makeText(BuyMarket.this, "Message sent to seller "+selected_pro.getSeller(), Toast.LENGTH_SHORT).show();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent( BuyMarket.this, PaymentActivity.class );
                intent1.putExtra( "missing", send_back_missing );
                intent1.putExtra( "cart_item", send_back_missing );
                currentSelectedItems.add(selected_pro);
                Gson gson = new Gson();
                String payment = gson.toJson( currentSelectedItems );
                intent1.putExtra( "payment", payment );
                intent1.putExtra("backPage",selectedString);
                intent1.putExtra("payBack","none");
                startActivity( intent1 );
            }
        });

        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDisplay(selected_pro);
            }
        });

        demo_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo_field.setVisibility(View.VISIBLE);
                review_field.setVisibility(View.GONE);
                msg_field.setVisibility(View.GONE);
                wallet_field.setVisibility(View.GONE);
            }
        });

        review_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo_field.setVisibility(View.GONE);
                review_field.setVisibility(View.VISIBLE);
                msg_field.setVisibility(View.GONE);
                wallet_field.setVisibility(View.GONE);
            }
        });

        msg_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo_field.setVisibility(View.GONE);
                review_field.setVisibility(View.GONE);
                msg_field.setVisibility(View.VISIBLE);
                wallet_field.setVisibility(View.GONE);
            }
        });

        wallet_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo_field.setVisibility(View.GONE);
                review_field.setVisibility(View.GONE);
                msg_field.setVisibility(View.GONE);
                wallet_field.setVisibility(View.VISIBLE);
            }
        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                for(i=0;i<cartIDs.length;i++)
                {
                    if(selected_pro.getRawImages().get(0).equals(cartIDs[i]))
                    {
                        Toast.makeText(getApplicationContext(),"Product already in cart.",Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;
                    }
                }
                if(flag==0)
                {
                    addToServer( selected_pro.getRawImages().get(0) );
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartIDs.length<=0)
                {
                    Toast.makeText(getApplicationContext(),"No any item added to cart.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    prevCartPro(cartIDs);
                }
            }
        } );

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
        main_size_text.setText(selected_pro.getSize());
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
    public void onBackPressed()
    {
        getBuying( "noAction" );
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
                        overAllPro( cartIDs.length );
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
    public void addToServer(String id)
    {
        Util.getUserService().addCartDetails(hearders,id).enqueue( new Callback<RestResponseBeanCart>(){
            @Override
            public void onResponse(Call<RestResponseBeanCart> call, Response<RestResponseBeanCart> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+context, "--------Getting cart items--------");
                    RestResponseBeanCart restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        Toast.makeText(getApplicationContext(),"Product added to cart",Toast.LENGTH_SHORT).show();
                        cartIDs=restResponse.getCart();
                        overAllPro(cartIDs.length);
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
    public void overAllPro(int count)
    {
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


        int x=count;
        if(x<=0)
        {
            organge.setVisibility( ConstraintLayout.INVISIBLE );
        }
        else
        {
            organge.setVisibility( ConstraintLayout.VISIBLE );
        }
        String count1=Integer.toString( x );
        add_cart_count.setText( count1 );
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
                            Log.d(TAG,"onResponse: \n"+
                                    "Name: "+ allMissing.get( i ).getTags().getTitle()+"\n"+
                                    "Price: "+ allMissing.get( i ).getRawImages().get(0)+"\n"+
                                    "_______________________________________________\n\n");
                        }
                        Gson gson = new Gson();
                        String cart_pro=gson.toJson(allMissing);
                        Intent i = new Intent( BuyMarket.this, CartActivity.class );
                        i.putExtra( "cart_product", cart_pro );
                        i.putExtra( "missing",send_back_missing );
                        Gson gson1 = new Gson();
                        String backPage = gson1.toJson( currentSelectedItems );
                        i.putExtra("backPage",selectedString);
                        startActivity( i );

//                        for(int i=0;i<id.length;i++) {
//                            for (int j = 0; j < allMissing.size(); j++) {
//                                if (id[i].equals(allMissing.get(j).getRawImages().get(0))) {
//                                    cart_item.add(allMissing.get(j));
//                                }
//                            }
//                        }

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
    public void popupDisplay(MissingItembean selected_pro)
    {
        pop=new Dialog( BuyMarket.this );
        pop.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        pop.setContentView( R.layout.buying_tags_popup);
        title_pop=pop.findViewById( R.id.pop_title );
        cross=pop.findViewById( R.id.pop_cross);
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
        pop_casual=pop.findViewById( R.id.pop_type );
        Size_s=pop.findViewById( R.id.size_tag1 );
        Size_s_text=pop.findViewById( R.id.size_tag1_text );
        Size_m=pop.findViewById( R.id.size_tag2 );
        Size_m_text=pop.findViewById( R.id.size_tag2_text );
        Size_l=pop.findViewById( R.id.size_tag3 );
        Size_l_text=pop.findViewById( R.id.size_tag3_text );



        pop.show();

        title_pop.setText( selected_pro.getManual_desc() );
        pop_casual.setText( selected_pro.getOccasion() );
        System.out.println("Aditya: "+selected_pro.getSize()+" "+selected_pro.getCondition()+" "+selected_pro.getGrade()+" "+selected_pro.getF_scale());
        if(selected_pro.getSize().equals("S"))
        {
                Size_s.setBackgroundResource( R.drawable.tags_back_blue );
                Size_s_text.setTextColor( getResources().getColor( R.color.white ));
                Size_m.setBackgroundResource( R.drawable.tags_back );
                Size_m_text.setTextColor( getResources().getColor( R.color.subtext ));
                Size_l.setBackgroundResource( R.drawable.tags_back );
                Size_l_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if (selected_pro.getSize().equals("M"))
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

        if(selected_pro.getGrade().equals("None"))
        {
            TS_new.setBackgroundResource( R.drawable.tags_back_blue );
            TS_new_text.setTextColor( getResources().getColor( R.color.white ));
            TS_rare.setBackgroundResource( R.drawable.tags_back );
            TS_rare_text.setTextColor( getResources().getColor( R.color.subtext ));
            TS_drop.setBackgroundResource( R.drawable.tags_back );
            TS_drop_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
        else if (selected_pro.getGrade().equals("rare find"))
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

        if(selected_pro.getCondition().equals("Excellent"))
        {
            Condition_excellent.setBackgroundResource( R.drawable.tags_back_blue );
            Condition_excellent_text.setTextColor( getResources().getColor( R.color.white ));
            Condition_brand.setBackgroundResource( R.drawable.tags_back );
            Condition_brand_text.setTextColor( getResources().getColor( R.color.subtext ));
            Condition_minor.setBackgroundResource( R.drawable.tags_back );
            Condition_minor_text.setTextColor( getResources().getColor( R.color.subtext ));
        }
       else if (selected_pro.getCondition().equals("Brand New"))
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

       if(selected_pro.getF_scale().equals("Yes"))
       {
           Sale_yes.setBackgroundResource( R.drawable.tags_back_blue );
           Sale_yes_text.setTextColor( getResources().getColor( R.color.white ));
           Sale_no.setBackgroundResource( R.drawable.tags_back );
           Sale_no_text.setTextColor( getResources().getColor( R.color.subtext ));
       }
        else
       {
           Sale_no.setBackgroundResource( R.drawable.tags_back_blue );
           Sale_no_text.setTextColor( getResources().getColor( R.color.white ));
           Sale_yes.setBackgroundResource( R.drawable.tags_back );
           Sale_yes_text.setTextColor( getResources().getColor( R.color.subtext ));
       }

        cross.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        } );

    }
}
