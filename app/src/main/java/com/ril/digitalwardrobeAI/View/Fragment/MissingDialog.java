package com.ril.digitalwardrobeAI.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.gtomato.android.ui.transformer.CoverFlowViewTransformer;
import com.gtomato.android.ui.widget.CarouselView;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Activity.LoginActivity;
import com.ril.digitalwardrobeAI.View.Adapter.MissingPopCourselAdapter;

import java.util.ArrayList;
import java.util.Collections;


public class MissingDialog extends Dialog   {
    CarouselView carouselView;
    Integer position;
    ArrayList<MissingItembean> productList;
    ImageView close;
    Context context;
    AlertInterface alertInterface;
    public void setAlertInt(AlertInterface alertInterface){
        this.alertInterface=alertInterface;
    }
    public MissingDialog(Context context, ArrayList<MissingItembean> productList,Integer position) {
        super(context);
        this.productList=productList;
        this.position=position;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_wardrobe_carousel);
        carouselView=findViewById(R.id.carouselView);
        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        // Gets linearlayout
// Gets the layout params that will allow you to resize the layout
        ConstraintLayout container=findViewById(R.id.container);
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.height = LoginActivity.height-LoginActivity.height/4;
        params.width = LoginActivity.width;
        container.setLayoutParams(params);

        carouselView.setTransformer(new CoverFlowViewTransformer());
        // if(productList.size()// Changes the height and width to the specified *pixels*>1)Collections.swap(productList,0,position);

        ArrayList<MissingItembean> list=new ArrayList<>();
        if(productList.size()>1) {

            for(int i=0;i<productList.size();i++){
                list.add(productList.get(i));
            }
            if(position!=0)
                Collections.swap(list, position, 1);
        }
        else list=productList;
        carouselView.setAdapter(new MissingPopCourselAdapter(context,list));
        if(position==0)
            carouselView.smoothScrollToPosition(0);

        if(productList.size()>1&&position!=0)
            carouselView.smoothScrollToPosition(1);
        carouselView.setVisibility(View.VISIBLE);
    }


    public interface AlertInterface{

        public void closeAlert();
    }
}
