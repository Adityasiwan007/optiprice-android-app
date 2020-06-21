package com.ril.digitalwardrobeAI.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ril.digitalwardrobeAI.CameraPreview;
import com.ril.digitalwardrobeAI.Model.MissingItembean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.ResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.OnSwipeTouchListener;
import com.ril.digitalwardrobeAI.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.FILTER;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.WARDROBE_TYPE;
import static com.ril.digitalwardrobeAI.Constants.catalogComplementaryProducts;
import static com.ril.digitalwardrobeAI.Constants.catalogSimilarProducts;
import static com.ril.digitalwardrobeAI.Constants.complementaryProducts;
import static com.ril.digitalwardrobeAI.Constants.pictureFile;
import static com.ril.digitalwardrobeAI.Constants.croppedImage;
import static com.ril.digitalwardrobeAI.Constants.hearders;
import static com.ril.digitalwardrobeAI.Constants.msdTags;
import static com.ril.digitalwardrobeAI.Constants.products;
import static com.ril.digitalwardrobeAI.Constants.restResponse;
import static com.ril.digitalwardrobeAI.Constants.similarColorProducts;
import static com.ril.digitalwardrobeAI.Constants.similarStyleProducts;


public class BuySellActivity extends AppCompatActivity {
    private Camera mCamera;
    private static final String TAG="BuySellActivity";
    private CameraPreview mCameraPreview;
    RelativeLayout progressBar;
    ImageButton captureButton;
    ConstraintLayout mainLayout;
    ImageView buy,sell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell );
        mainLayout=findViewById(R.id.mainLayout);
//        progressBar=findViewById(R.id.progressBar);
//        //initializeCamera();
//        captureButton = findViewById(R.id.button_capture);

        buy=findViewById( R.id.buy );
        sell=findViewById( R.id.sell );
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMissing();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BuyMainAI.class);
                startActivity(intent);
                finish();
            }
        });

//        mainLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
//            public void onSwipeTop() {
//            }
//            public void onSwipeRight() {
//                Intent intent=new Intent(getApplicationContext(), WardrobeActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0,R.anim.right_swipe);
//                finish();
//            }
//
//            public void onSwipeBottom() {
//            }
//
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        });


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
                        Intent i=new Intent( BuySellActivity.this,MissingActivity.class );
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
    public void onBackPressed() {
        Intent intent1 = new Intent( BuySellActivity.this, LoginActivity.class );
        //Intent intent1=new Intent( BuyingGridActivity.this,LoginActivity.class );
        startActivity( intent1 );
        super.onBackPressed();
    }

//    public static Camera getCameraInstance() {
//        Camera camera = null;
//        try {
//            camera = Camera.open();
//            Camera.Parameters params = camera.getParameters();
//            if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            } else {
//                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//            }
//            camera.setParameters(params);
//
//        } catch (Exception e) {
//            // cannot get camera or does not exist
//        }
//        return camera;
//    }

    /*Camera.PictureCallback mPicture = (data, camera) -> {

        try {
            //crop image
            camera.startPreview();
            getCroppedImage(data);
            //analyze image and identify type,color etc
            analyzeImage(pictureFile);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
    };
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(getCacheDir(),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void analyzeImage(File uploadedFile){
        RequestBody fileReqBody=RequestBody.create(MediaType.parse("image/*"),uploadedFile);
        MultipartBody.Part part=MultipartBody.Part.createFormData("imgasset",uploadedFile.getName(),fileReqBody);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"),"image-type");
        captureButton.setEnabled(false);
        Util.getUserService().analyzeImage(hearders,part).enqueue(new Callback<RestResponseBean<ResponseBean>>() {
            @Override
            public void onResponse(Call<RestResponseBean<ResponseBean>> call, Response<RestResponseBean<ResponseBean>> response) {
                if(response.isSuccessful())
                {
                    Log.d(LOGTAG+"Camera Fragment " , "--------Analizing Image--------");
                    restResponse= response.body();
                    if(restResponse.isSuccess())
                    {
                        final ResponseBean responseBean=restResponse.getObject();
                        Log.d(LOGTAG+"Camera Fragment " , "--------Analizing Success--------");
                        //GET COMPLIMENTARY PRODUCTS
                        complementaryProducts=new ArrayList<>();
                        for(int i=0;responseBean.getComplementaryProduct()!=null&&i<responseBean.getComplementaryProduct().size();i++){
                            complementaryProducts.add(responseBean.getComplementaryProduct().get(i));
                        }
                        //GET SIMILAR PRODUCTS
                        products =new ArrayList<>();
                        for( int i=0;responseBean.getSimilarProduct()!=null&&i<responseBean.getSimilarProduct().size();i++) {
                            products.add(responseBean.getSimilarProduct().get(i));
                        }
                        Log.d(LOGTAG, String.valueOf(restResponse.getObject().getSimilarProduct().size()));

                        for(int i=0;i<restResponse.getObject().getSortedColors().size();i++)
                            Log.d(LOGTAG,restResponse.getObject().getSortedColors().get(i));
                        //get catalog similar products
                        catalogSimilarProducts=new ArrayList<>();
                        for( int i=0;responseBean.getCatalogSimilarProducts()!=null&&i<responseBean.getCatalogSimilarProducts().size();i++) {
                            catalogSimilarProducts.add(responseBean.getCatalogSimilarProducts().get(i));
                        }
                        Log.d(LOGTAG," SIZE OF CATALOG similar " +catalogSimilarProducts.size());

                        //get catalog comp products
                        catalogComplementaryProducts=new ArrayList<>();
                        for( int i=0;responseBean.getCatalogComplementaryProducts()!=null&&i<responseBean.getCatalogComplementaryProducts().size();i++) {
                            catalogComplementaryProducts.add(responseBean.getCatalogComplementaryProducts().get(i));
                        }
                        Log.d(LOGTAG," SIZE OF CATALOG COMP " +catalogComplementaryProducts.size());
                        //GET SIMILAR STYLE PRODUCTS
                        similarStyleProducts=new ArrayList<>();
                        for(int i=0;responseBean.getSimilarStyleProduct()!=null&&i<responseBean.getSimilarStyleProduct().size();i++){
                            similarStyleProducts.add(responseBean.getSimilarStyleProduct().get(i));
                        }
                        //GET SIMILAR COLOR PRODUCTS
                        similarColorProducts=new ArrayList<>();
                        for(int i=0;responseBean.getSimilarColorProduct()!=null&&i<responseBean.getSimilarColorProduct().size();i++){
                            similarColorProducts.add(responseBean.getSimilarColorProduct().get(i));
                        }
                        //GET UPLOADED PRODUCT DETAILS
                        if(responseBean.getMsdTags()!=null) {
                            msdTags = responseBean.getMsdTags();

                            if(msdTags.getCategory().equals("Tops")||msdTags.getCategory().equals("Skirts")||msdTags.getCategory().equals("Pants")){
                                if(restResponse.getObject().getMsdTags().getCategory().equals("Tops"))
                                    FILTER="Skirts";

                                else if(restResponse.getObject().getMsdTags().getCategory().equals("Skirts")||restResponse.getObject().getMsdTags().getCategory().equals("Pants"))
                                    FILTER="Tops";
                                WARDROBE_TYPE="wardrobe";
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"Please try again.No similar item found for the " + msdTags.getCategory(),Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                            captureButton.setEnabled(true);
                            mCamera.startPreview();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please try again."+restResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        mCamera.startPreview();
                        captureButton.setEnabled(true);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    mCamera.startPreview();
                    captureButton.setEnabled(true);

                }
            }
            @Override
            public void onFailure(Call<RestResponseBean<ResponseBean>> call, Throwable t) {
                Log.d(LOGTAG+"Camera Fragment " , "onFailure: "+"Message Sent Failed "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Check your internet connection.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                mCamera.startPreview();
                captureButton.setEnabled(true);


            }
        });
    }

    void initializeCamera(){
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(getApplicationContext(), mCamera, this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
    }

    void getCroppedImage(byte[] data) throws IOException {
        pictureFile = getOutputMediaFile();
        Log.d(LOGTAG,pictureFile.getPath());
        if (pictureFile == null) {
            return;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        croppedImage = BitmapFactory.decodeByteArray(data , 0, data.length);
        //thumbImage=croppedImage;
        //thumbImage = Bitmap.createBitmap(croppedImage,(croppedImage.getWidth()/5),(croppedImage.getHeight())/5,(int)(croppedImage.getWidth()/1.7),(int)(croppedImage.getHeight()/1.5));//ARGB_8888 is a good quality configuration
        //thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);//100 is the best quality possibe
        //croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);//100 is the best quality possibe
        FileOutputStream fos = new FileOutputStream(pictureFile);
        fos.write(data);
        fos.close();
        croppedImage=modifyOrientation(croppedImage,pictureFile.getAbsolutePath());
        pictureFile = new Compressor(getApplicationContext()).compressToFile(pictureFile);


    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

   @Override
    protected void onResume() {
        super.onResume();


        initializeCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }*/
}
