package com.ril.digitalwardrobeAI.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ril.digitalwardrobeAI.PreferenceManager;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.imageList;
import static com.ril.digitalwardrobeAI.Constants.wardrobeItems;
import static com.ril.digitalwardrobeAI.Constants.wardrobeList;
import static com.ril.digitalwardrobeAI.View.Activity.LoginActivity.dbHelper;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LoadingWardrobeActivity extends AppCompatActivity {
    Queue<HashMap<String,String>> imageDownloadQueue = new ConcurrentLinkedDeque<>();
    Picasso picasso;
    ArrayList<Target> targets = new ArrayList<>();
    TextView countText;
    PreferenceManager preferenceManager;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_wardrobe);
        countText=findViewById(R.id.countText);
        picasso = Picasso.get();
        preferenceManager=new PreferenceManager(this);
        imageList = dbHelper.getImages();
        wardrobeList = dbHelper.getWardrobe();
        //  reloadWardrobe();
        
        //download image to app cache if not present in cache
        for(int i=0;i<(wardrobeItems.size());i++){
            String imageName=wardrobeItems.get(i).getRawImages().get(0);
            if(!imageList.contains(imageName)){
                HashMap<String, String> item = new HashMap();
                item.put(imageName, wardrobeItems.get(i).getTags().getCategory());
                imageDownloadQueue.add(item);
            }
        }
        for(int i=0;i<15;i++){
            HashMap<String,String> item = imageDownloadQueue.poll();
            if(item!=null)downloadImage(item);
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void downloadImage(HashMap<String,String> item){
        String imageName = null;
        for (HashMap.Entry<String, String> e : item.entrySet()) {
            imageName = e.getKey();
        }
        Target target =getTarget(this,imageName,item.get(imageName), new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                HashMap<String,String> item = imageDownloadQueue.poll();
                if(item!=null){
                    int count = wardrobeItems.size()-imageDownloadQueue.size();
                    countText.setText(imageList.size()+"/"+wardrobeItems.size()+ " loaded ");
                    downloadImage(item);
                }else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // all the images are downloaded
                            // TODO: start camera activityWARDROBE COUNTP
                        }
                    },15000);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
        targets.add(target);
        picasso.load(IMAGE_URL_WARDROBE +imageName).into(target);
    }

    public Target getTarget(final Activity activity, final String imageName, final String type, final com.squareup.picasso.Callback callback){

        Target target=new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File myDir = new File(getCacheDir()+ "/dw/");
                            if (!myDir.exists()) {
                                myDir.mkdirs();
                            }
                            myDir = new File(myDir, imageName);
                            FileOutputStream fos=new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                            dbHelper.insertImageUrl(imageName,type);
                            imageList=dbHelper.getImages();
                            wardrobeList=dbHelper.getWardrobe();
                            fos.flush();
                            fos.close();
                            if(imageList.size()>(wardrobeItems.size()*0.8))
                            {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Intent intent = new Intent(getApplicationContext(), BuySellActivity.class);
                                            activity.startActivity(intent);
                                            targets.clear(); // remove all reference to targets, so gc can take em
                                            finish();

                                        } catch (Exception e) {
                                            Log.d(LOGTAG + getLocalClassName(), e.toString());
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            // some action
                            e.printStackTrace();
                        }finally {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess();
                                }
                            });
                        }
                    }}).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
               Log.e(LOGTAG+getLocalClassName(),imageName+" image donwload failed");
               try{}
               catch (Exception ex){
                   Log.e(LOGTAG,ex.getMessage());
               }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        return target;
    }

    void reloadWardrobe(){
        if(getCacheDir()!=null) {
            Log.d(LOGTAG+getLocalClassName(), "Delete cache");
            deleteCache(getApplicationContext());
            dbHelper.delete();
            imageList = dbHelper.getImages();
            wardrobeList = dbHelper.getWardrobe();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGTAG+getLocalClassName(),"RESUME");
        imageList = dbHelper.getImages();
        wardrobeList = dbHelper.getWardrobe();


    }

}
