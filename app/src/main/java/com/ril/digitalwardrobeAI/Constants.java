package com.ril.digitalwardrobeAI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ril.digitalwardrobeAI.Model.Product;
import com.ril.digitalwardrobeAI.Model.ResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.ScannedProductBean;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.Model.WardrobeItems;
import com.ril.digitalwardrobeAI.View.Adapter.ViewPagerAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;


public class Constants {
    public static String LOGTAG="Digital Wardrobe : ";
    public static HashMap<String,String> hearders;
    public  static String ROOT_URL;
    public  static String ROOT_URL_HOSTED;
    public  static String PORT="8031";
    public  static String WARDROBE_TYPE="wardrobe";
    public static String FILTER="";


    public static ArrayList<Product> complementaryProducts;
    public static  ArrayList<Product> products;
    public static  ArrayList<Product> catalogSimilarProducts;
    public static  ArrayList<Product> catalogComplementaryProducts;

    public static  ArrayList<Product> similarStyleProducts;
    public static ArrayList<Product> similarColorProducts;
    public static ScannedProductBean msdTags;
    public  static RestResponseBean<ResponseBean> restResponse;
    public static TextView[] dots;
    public static ArrayList<Integer> layouts;
    public  static ArrayList<WardrobeItemBean> tops;
    public static ArrayList<WardrobeItemBean> trousers;
    public static ArrayList<WardrobeItemBean> skirts;
    public static ArrayList<WardrobeItemBean> selectedItems;
    public static ArrayList<String> colorBar;
    public  static String IMAGE_URL_WARDROBE;
    public  static String IMAGE_URL_CATALOG;
    public  static String IMAGE_THUMBNAIL_URL_WARDROBE;
    public  static String IMAGE__THUMBNAIL_URL_CATALOG;

    static ViewPagerAdapter viewPagerAdapter;
    public static ArrayList<WardrobeItemBean> wardrobeItems;
    public static ArrayList<WardrobeItems> wardrobeList;
    public static ArrayList<String> imageList;
    public static Bitmap croppedImage,thumbImage;
    public static File pictureFile;
    public static double APP_VERSION=1.3;

    public static void setAdapterViewPager(AutoScrollViewPager viewPager,final Activity activity, final LinearLayout dotsLayout, ArrayList<Product> products,String type){
        layouts=new ArrayList<>();
        for (int i = 0; i<products.size(); i++)
            layouts.add(R.layout.view_pager_layout);
        // adding bottom dots
        addBottomDots(activity,0,dotsLayout);
        viewPagerAdapter = new ViewPagerAdapter(layouts,activity,products,type);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                addBottomDots(activity,position,dotsLayout);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public static void addBottomDots(Activity activity,int currentPage,LinearLayout dotsLayout) {
        dots = new TextView[layouts.size()];
        ArrayList<Integer> colorsActive = new ArrayList<>();
        ArrayList<Integer> colorsInactive = new ArrayList<>();
        for(int i=0;i<layouts.size();i++) {
            colorsActive.add(R.color.orange);
            colorsInactive.add(R.color.grey);
        }
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(activity);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(25);
            dots[i].setTextColor(activity.getResources().getColor(colorsInactive.get(currentPage)));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) dots[currentPage].setTextColor(activity.getResources().getColor(colorsActive.get(currentPage)));
    }
}
