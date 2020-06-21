package com.ril.digitalwardrobeAI.View.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.hearders;

public class PaymentActivity extends AppCompatActivity {
    String card_type="non";
    String card_type_final="none";
    private static DecimalFormat df = new DecimalFormat("0.00");
    String jsonString,cart,missing,backPage,payBack;
    ArrayList<MissingItembean> payment_item;
    Dialog pop,pop1;

    ImageView back,bluecard,redcard,blackcard,proceed,confirm;
    TextView price_summary,price_confirm,card_name,trans_type,cart_mainTxt,cart_subTxt,total_amount,show_card_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payment );
        Intent intent = getIntent();
        jsonString = intent.getStringExtra("payment");
        cart=intent.getStringExtra( "cart_item" ); //to be send back
        missing=intent.getStringExtra( "missing" ); //to be send back
        backPage=intent.getStringExtra( "backPage" ); //to be send back
        payBack=intent.getStringExtra( "payBack" ); //to be send back

        payment_item= new Gson().fromJson( jsonString,new TypeToken<ArrayList<MissingItembean>>(){}.getType() );

        back=(ImageView)findViewById( R.id.cartback );
        bluecard=(ImageView)findViewById( R.id.blueCard );
        blackcard=(ImageView)findViewById( R.id.blackCard );
        redcard=(ImageView)findViewById( R.id.redCard );
        proceed=(ImageView)findViewById( R.id.proceed );
        cart_mainTxt=(TextView)findViewById( R.id.maintext_cart );
        total_amount=(TextView)findViewById( R.id.total_amount );
        cart_subTxt=(TextView)findViewById( R.id.subtext_cart );
        show_card_name=(TextView)findViewById( R.id.selected_card_name ) ;

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backPage.equals("grid") || payBack.equals("cart"))
                {
                    Intent intent1=new Intent( PaymentActivity.this,CartActivity.class );
                    intent1.putExtra("cart_product",cart);
                    intent1.putExtra( "missing",missing );
                    intent1.putExtra("backPage",backPage);
                    startActivity( intent1 );
                }
                else
                {
                    Intent intent1=new Intent( PaymentActivity.this,BuyMarket.class );
                    intent1.putExtra( "missing",missing );
                    intent1.putExtra("selected",backPage);
                    startActivity( intent1 );
                }
            }
        } );

        bluecard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluecard.getScaleX()==1.0f) {
                    show_card_name.setText( "Master card" );
                    card_type="blue";
                    ObjectAnimator blue_scaleDownX = ObjectAnimator.ofFloat( bluecard, "scaleX", 1.2f );
                    ObjectAnimator blue_scaleDownY = ObjectAnimator.ofFloat( bluecard, "scaleY", 1.3f );
                    blue_scaleDownX.setDuration( 1000 );
                    blue_scaleDownY.setDuration( 1000 );
                    AnimatorSet scaleDown = new AnimatorSet();
                    scaleDown.play( blue_scaleDownX ).with( blue_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator red_scaleDownX = ObjectAnimator.ofFloat( redcard, "scaleX", 1.0f );
                    ObjectAnimator red_scaleDownY = ObjectAnimator.ofFloat( redcard, "scaleY", 1.0f );
                    red_scaleDownX.setDuration( 1000 );
                    red_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( red_scaleDownX ).with( red_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator black_scaleDownX = ObjectAnimator.ofFloat( blackcard, "scaleX", 1.0f );
                    ObjectAnimator black_scaleDownY = ObjectAnimator.ofFloat( blackcard, "scaleY", 1.0f );
                    black_scaleDownX.setDuration( 1000 );
                    black_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( black_scaleDownX ).with( black_scaleDownY );
                    scaleDown.start();


                }
                else
                {
                    setNormal();
                }
            }
        } );
        blackcard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blackcard.getScaleX()==1.0f) {
                    card_type="black";
                    show_card_name.setText( "RHB Premier" );
                    ObjectAnimator blue_scaleDownX = ObjectAnimator.ofFloat( bluecard, "scaleX", 1.0f );
                    ObjectAnimator blue_scaleDownY = ObjectAnimator.ofFloat( bluecard, "scaleY", 1.0f );
                    blue_scaleDownX.setDuration( 1000 );
                    blue_scaleDownY.setDuration( 1000 );
                    AnimatorSet scaleDown = new AnimatorSet();
                    scaleDown.play( blue_scaleDownX ).with( blue_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator red_scaleDownX = ObjectAnimator.ofFloat( redcard, "scaleX", 1.0f );
                    ObjectAnimator red_scaleDownY = ObjectAnimator.ofFloat( redcard, "scaleY", 1.0f );
                    red_scaleDownX.setDuration( 1000 );
                    red_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( red_scaleDownX ).with( red_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator black_scaleDownX = ObjectAnimator.ofFloat( blackcard, "scaleX", 1.2f );
                    ObjectAnimator black_scaleDownY = ObjectAnimator.ofFloat( blackcard, "scaleY", 1.3f );
                    black_scaleDownX.setDuration( 1000 );
                    black_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( black_scaleDownX ).with( black_scaleDownY );
                    scaleDown.start();


                }
                else
                {
                    setNormal();
                }

            }
        } );
        redcard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(redcard.getScaleX()==1.0f) {
                    card_type="red";
                    show_card_name.setText( "Australia post" );
                    ObjectAnimator blue_scaleDownX = ObjectAnimator.ofFloat( bluecard, "scaleX", 1.0f );
                    ObjectAnimator blue_scaleDownY = ObjectAnimator.ofFloat( bluecard, "scaleY", 1.0f );
                    blue_scaleDownX.setDuration( 1000 );
                    blue_scaleDownY.setDuration( 1000 );
                    AnimatorSet scaleDown = new AnimatorSet();
                    scaleDown.play( blue_scaleDownX ).with( blue_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator red_scaleDownX = ObjectAnimator.ofFloat( redcard, "scaleX", 1.2f );
                    ObjectAnimator red_scaleDownY = ObjectAnimator.ofFloat( redcard, "scaleY", 1.3f );
                    red_scaleDownX.setDuration( 1000 );
                    red_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( red_scaleDownX ).with( red_scaleDownY );
                    scaleDown.start();

                    ObjectAnimator black_scaleDownX = ObjectAnimator.ofFloat( blackcard, "scaleX", 1.0f );
                    ObjectAnimator black_scaleDownY = ObjectAnimator.ofFloat( blackcard, "scaleY", 1.0f );
                    black_scaleDownX.setDuration( 1000 );
                    black_scaleDownY.setDuration( 1000 );
                    scaleDown = new AnimatorSet();
                    scaleDown.play( black_scaleDownX ).with( black_scaleDownY );
                    scaleDown.start();


                }
                else
                {
                    setNormal();
                }

            }
        } );

        proceed.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card_type.equals( "non" ))
                {
                    Toast.makeText(PaymentActivity.this, "Please select the card.", Toast.LENGTH_SHORT).show();
                }
                else if(!card_type_final.equals("none"))
                {
                    Toast.makeText(PaymentActivity.this, "Payment already done", Toast.LENGTH_SHORT).show();
                }
                else {
                pop = new Dialog( PaymentActivity.this );
                pop.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                pop.setContentView( R.layout.payment_conformation_popup );
                Window window = pop.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                pop.show();

                confirm = (ImageView) pop.findViewById( R.id.confirm );
                price_confirm = (TextView) pop.findViewById( R.id.cost_confirm );
                card_name=(TextView)pop.findViewById( R.id.card_name );
                trans_type=(TextView)pop.findViewById( R.id.card_method ) ;

                if(card_type.equals( "blue" ))
                  {
                        card_name.setText( "Master card 5412 5412 XXXX XXXX" );
                        trans_type.setText( "Credit Card Transaction" );
                  }
                if(card_type.equals( "black" ))
                  {
                        card_name.setText( "RHB Premier 8888 8888 XXXX XXXX" );
                        trans_type.setText( "Visa Card Transaction" );
                  }
                if(card_type.equals( "red" ))
                  {
                        card_name.setText( "Australia post 6012 3412 XXXX XXXX" );
                        trans_type.setText( "Credit Card Transaction" );
                  }

                price_confirm.setText( getFinalCost( payment_item ) );

                confirm.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pop.dismiss();
                        pop1 = new Dialog( PaymentActivity.this );
                        pop1.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                        pop1.setContentView( R.layout.payment_summary_pop );
                        Window window = pop1.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.BOTTOM;
                        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        pop1.show();
                        price_summary = (TextView) pop1.findViewById( R.id.cost );
                        price_summary.setText( getFinalCost( payment_item ) );
                        card_type_final=card_type;
                        for(int i=0;i<payment_item.size();i++)
                        {
                            removeFromServer( payment_item.get(i).getRawImages().get( 0 ) );
                        }
                    }
                } );
              }//else ends
            }//onclick ends
        } );//listener ends

        if(payment_item.size()==1)
        {
            cart_mainTxt.setText( "Total " + payment_item.size() + " Item     : " + getCost( payment_item ) );
        }
        else
            {
            cart_mainTxt.setText( "Total " + payment_item.size() + " Items    : " + getCost( payment_item ) );
        }

        String[] cata=new String[payment_item.size()];
        for(int i=0;i<payment_item.size();i++)
        {
            System.out.println("test1 : "+ payment_item.get(i).getPrice() );
            cata[i]=payment_item.get(i).getTags().getCategory();
        }
        cata = new HashSet<String>(Arrays.asList(cata)).toArray(new String[0]);
        cart_subTxt.setText( toCSV( cata ) );

        total_amount.setText( getFinalCost( payment_item ) );

    }

    public static String toCSV(String[] array) {
        String result = "";

        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();

            for (String s : array) {
                sb.append(s).append(",");
            }

            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

    public void setNormal()
    {
        card_type="non";
        show_card_name.setText( "" );
        ObjectAnimator blue_scaleDownX = ObjectAnimator.ofFloat( bluecard, "scaleX", 1.0f );
        ObjectAnimator blue_scaleDownY = ObjectAnimator.ofFloat( bluecard, "scaleY", 1.0f );
        blue_scaleDownX.setDuration( 1000 );
        blue_scaleDownY.setDuration( 1000 );
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play( blue_scaleDownX ).with( blue_scaleDownY );
        scaleDown.start();

        ObjectAnimator red_scaleDownX = ObjectAnimator.ofFloat( redcard, "scaleX", 1.0f );
        ObjectAnimator red_scaleDownY = ObjectAnimator.ofFloat( redcard, "scaleY", 1.0f );
        red_scaleDownX.setDuration( 1000 );
        red_scaleDownY.setDuration( 1000 );
        scaleDown = new AnimatorSet();
        scaleDown.play( red_scaleDownX ).with( red_scaleDownY );
        scaleDown.start();

        ObjectAnimator black_scaleDownX = ObjectAnimator.ofFloat( blackcard, "scaleX", 1.0f );
        ObjectAnimator black_scaleDownY = ObjectAnimator.ofFloat( blackcard, "scaleY", 1.0f );
        black_scaleDownX.setDuration( 1000 );
        black_scaleDownY.setDuration( 1000 );
        scaleDown = new AnimatorSet();
        scaleDown.play( black_scaleDownX ).with( black_scaleDownY );
        scaleDown.start();
    }
    public String getFinalCost(ArrayList<MissingItembean> payment_item)
    {
        double c=0;
        int i;
        for(i=0;i<payment_item.size();i++)
        {
            c=c+payment_item.get(i).getPrice();
        }
        c=c+7+3;
        String final_cost="$"+df.format( c );
        return final_cost;
    }
    public String getCost(ArrayList<MissingItembean> payment_item)
    {
        double c=0;
        int i;
        for(i=0;i<payment_item.size();i++)
        {
            c=c+payment_item.get(i).getPrice();
        }
        String final_cost="$"+df.format( c );
        return final_cost;
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
    @Override
    public void onBackPressed() {
        if(backPage.equals("grid") || payBack.equals("cart"))
        {
            Intent intent1=new Intent( PaymentActivity.this,CartActivity.class );
            intent1.putExtra("cart_product",cart);
            intent1.putExtra( "missing",missing );
            intent1.putExtra("backPage",backPage);
            startActivity( intent1 );
        }
        else
        {
            Intent intent1=new Intent( PaymentActivity.this,BuyMarket.class );
            intent1.putExtra( "missing",missing );
            intent1.putExtra("selected",backPage);
            startActivity( intent1 );
        }
        super.onBackPressed();
    }

}
