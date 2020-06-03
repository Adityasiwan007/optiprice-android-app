package com.ril.digitalwardrobeAI.View.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ril.digitalwardrobeAI.AsyncUpdateApp;
import com.ril.digitalwardrobeAI.DBHelper;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.VersionResponse;
import com.ril.digitalwardrobeAI.Model.WardrobeBean;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.PreferenceManager;
import com.ril.digitalwardrobeAI.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.APP_VERSION;
import static com.ril.digitalwardrobeAI.Constants.IMAGE_THUMBNAIL_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_CATALOG;
import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.IMAGE__THUMBNAIL_URL_CATALOG;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.PORT;
import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;
import static com.ril.digitalwardrobeAI.Constants.colorBar;
import static com.ril.digitalwardrobeAI.Constants.hearders;
import static com.ril.digitalwardrobeAI.Constants.imageList;
import static com.ril.digitalwardrobeAI.Constants.wardrobeItems;
import static com.ril.digitalwardrobeAI.Constants.wardrobeList;

public class LoginActivity extends AppCompatActivity {
    EditText ipText,userName;
    PreferenceManager preferenceManager;
    Button btn_submit;
    public static DBHelper dbHelper;
    ProgressDialog progressDialog;
    int PERMISSION_ALL = 1;
    double LATEST_APP_VERSION=1.3;
    public static int height=0,width=0;
    private static final String TAG="Login";
    TextView title;

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ipText=findViewById(R.id.edit_text_ip);
        btn_submit=findViewById(R.id.btn_submit);
        title=findViewById( R.id.textView4 );
        Shader shader = new LinearGradient(0,0,0,title.getLineHeight(),
                Color.rgb( 82, 246, 128 ), Color.rgb( 30, 140, 236), Shader.TileMode.REPEAT);
        title.getPaint().setShader(shader);

//        Shader shader1 = new LinearGradient(0,0,0,btn_submit.getLineHeight(),
//                Color.rgb( 82, 246, 128 ), Color.rgb( 30, 140, 236), Shader.TileMode.REPEAT);
//        btn_submit.getPaint().setShader(shader1);

        preferenceManager=new PreferenceManager(this);
        progressDialog=new ProgressDialog(this);
        hearders=new HashMap<>();
        ipText.setText(preferenceManager.getIp(PreferenceManager.IP));
        userName=findViewById(R.id.userName);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        if(preferenceManager.getUserName(PreferenceManager.USER_NAME).length()!=0) {
            userName.setText(preferenceManager.getUserName(PreferenceManager.USER_NAME));
            hearders.put("userName",preferenceManager.getUserName(PreferenceManager.USER_NAME));
        }

        dbHelper = new DBHelper(this);
        imageList = dbHelper.getImages();
        wardrobeList=dbHelper.getWardrobe();
        
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if(isNetworkConnected()) {
            //get all wardrobe items
            //getAppVersion();

        }
        else
        {
            Toast.makeText(LoginActivity.this,"No internet connection.",Toast.LENGTH_SHORT).show();

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LATEST_APP_VERSION == APP_VERSION) {
                    if (userName.getText().toString().length() == 0)
                        Toast.makeText(LoginActivity.this, " Enter Username ", Toast.LENGTH_SHORT).show();
                    else if (ipText.getText().toString().length() == 0)
                        Toast.makeText(LoginActivity.this, " Enter IP ", Toast.LENGTH_SHORT).show();
                    else {
                        preferenceManager.setUserName(PreferenceManager.USER_NAME, userName.getText().toString());
                        hearders.put("userName", preferenceManager.getUserName(PreferenceManager.USER_NAME));
                        preferenceManager.setUserName(PreferenceManager.USER_NAME, userName.getText().toString());
                        ROOT_URL ="http://192.168.1.144:" + PORT + "/";
                        preferenceManager.setIp(PreferenceManager.IP, ipText.getText().toString());
                        IMAGE_URL_WARDROBE = ROOT_URL + "raw_images/";
                        IMAGE_URL_CATALOG = ROOT_URL + "catalog/";
                        IMAGE_THUMBNAIL_URL_WARDROBE = ROOT_URL + "thumbnails/";
                        IMAGE__THUMBNAIL_URL_CATALOG = ROOT_URL + "thumbnails/";


                        if (isNetworkConnected()) {
                            //get all wardrobe items
                            getWardrobeItems();    //Use this for Selling & Buying Option page
                            //getMissing();    //use thi to get directly to selling page

                        } else {
                            Toast.makeText(LoginActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "You can't continue with this verison of app. Please install the new version of digital wardrobe", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }

            }
        }
        return true;
    }
    
    public void  getWardrobeItems(){
        progressDialog.setMessage("Loading");
        progressDialog.show();
        btn_submit.setEnabled(false);
        Util.getUserService().getWardrobeItems(hearders).enqueue(new Callback<RestResponseBean<WardrobeBean>>() {
            @Override
            public void onResponse(Call<RestResponseBean<WardrobeBean>> call, Response<RestResponseBean<WardrobeBean>> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+getLocalClassName(), "--------Getting wardrobe items--------");
                    RestResponseBean<WardrobeBean> restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        ArrayList<WardrobeItemBean> allWardrobeItems=new ArrayList<>();
                        allWardrobeItems = restResponse.getObject().getWardrobeItems();
                        Log.d(LOGTAG+getLocalClassName(), "--------Fetched all items from wadrobe--------"+ allWardrobeItems.size());
                        wardrobeItems=new ArrayList<>();
                        colorBar=restResponse.getObject().getSortedColors();
                        for(int i=0;i<allWardrobeItems.size();i++){
                            if((allWardrobeItems.get(i).getTags().getCategory().equals("Tops"))||(allWardrobeItems.get(i).getTags().getCategory().equals("Skirts"))||(allWardrobeItems.get(i).getTags().getCategory().equals("Pants")))
                                wardrobeItems.add(allWardrobeItems.get(i));
                        }
                   /*     if(imageList.size()<wardrobeItems.size()) {
                            Log.d(LOGTAG+getLocalClassName(),"---------Go to wardrobe screen-----");
                            Intent intent = new Intent(LoginActivity.this, LoadingWardrobeActivity.class);
                            startActivity(intent);
                            finish();
                        }*/
                            Log.d(LOGTAG+getLocalClassName(),"---------Go to Camera screen-----");
                            Intent intent = new Intent(LoginActivity.this, BuySellActivity.class);
                            startActivity(intent);
                            //finish();

                    } else Toast.makeText(getApplicationContext(), "Please try again. nn", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    btn_submit.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid login.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    btn_submit.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<RestResponseBean<WardrobeBean>> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOGTAG+getLocalClassName(), t.getMessage());
                Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                btn_submit.setEnabled(true);
            }
        });
    }

   void getAppVersion(){
       ROOT_URL= ipText.getText().toString().equals("ril")
               ?"http://192.168.43.171:8099"
               :"http://"+ipText.getText().toString()+":"+PORT+"/";
       Log.d(LOGTAG,"Current app version is "+APP_VERSION);
       Util.getUserService().getAppVersion().enqueue(new Callback<VersionResponse>() {
           @Override
           public void onResponse(Call<VersionResponse> call, Response<VersionResponse> response) {
               if (response.isSuccessful()) {
                   Log.d(LOGTAG+getLocalClassName(), "--------Getting version--------");
                   VersionResponse versionResponse = response.body();
                   if (versionResponse.isSuccess()) {

                       Log.d(LOGTAG+getLocalClassName(), "--------Fetched version details--------" + versionResponse.getApplications().get(0).getAppFile());
                       LATEST_APP_VERSION= Double.parseDouble(versionResponse.getApplications().get(0).getVersion());
                       //if current installed app version is less than available app version
                       if(APP_VERSION<LATEST_APP_VERSION){
                           Log.d(LOGTAG+getLocalClassName(),"App update required - New version available " + versionResponse.getApplications().get(0).getVersion());
                           Toast.makeText(getApplicationContext(),"Please install new version of digital wardrobe ",Toast.LENGTH_SHORT).show();
                           String url = versionResponse.getApplications().get(0).getAppFile();
                           new AsyncUpdateApp(getApplicationContext()).execute(ROOT_URL+url);

                       }

                       else
                           Log.d(LOGTAG+getLocalClassName(),"App is up to date");


                   }
                   else Toast.makeText(getApplicationContext(), "Server error, Please try again. ", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
                   btn_submit.setEnabled(true);
               } else {
                   Toast.makeText(getApplicationContext(), "Server error, Please try again.", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
                   btn_submit.setEnabled(true);
               }
           }
           @Override
           public void onFailure(Call<VersionResponse> call, Throwable t) {
               Log.d(LOGTAG+getLocalClassName(), t.getMessage());
               Toast.makeText(getApplicationContext(),"Unable to reach server.",Toast.LENGTH_SHORT).show();
               progressDialog.dismiss();
               btn_submit.setEnabled(true);
           }
       });

   }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
                        Intent i=new Intent( LoginActivity.this,MissingActivity.class );
                        Gson gson = new Gson();
                        String json = gson.toJson( allMissing );
                        i.putExtra( "jsonObject", json );
                        i.putExtra( "status","noAction" );
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