package com.ril.digitalwardrobeAI.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ril.digitalwardrobeAI.OnSwipeTouchListener;
import com.ril.digitalwardrobeAI.R;
import com.ril.digitalwardrobeAI.View.Fragment.MainFragment;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout mainLayoutLeft,mainLayoutRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayoutLeft=findViewById(R.id.mainLayoutLeft);
        mainLayoutRight=findViewById(R.id.mainLayoutRight);
        MainFragment mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,mainFragment,"Main").commit();


        mainLayoutLeft.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeLeft() {
                Intent intent=new Intent(getApplicationContext(), BuySellActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.left_swipe);


            }
            public void onSwipeBottom() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        mainLayoutRight.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                Intent intent=new Intent(getApplicationContext(), WardrobeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.right_swipe);

            }
            public void onSwipeBottom() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });



}

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("Similar") != null||getSupportFragmentManager().findFragmentByTag("Compli") != null) {
            Log.d(LOGTAG," INSIDE BACK");
           // getSupportFragmentManager().findFragmentByTag("Main");

            MainFragment mainFragment=new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,mainFragment).commit();
        } else {
            super.onBackPressed();
            Intent intent=new Intent(this, BuySellActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
