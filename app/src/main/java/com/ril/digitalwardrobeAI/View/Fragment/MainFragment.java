package com.ril.digitalwardrobeAI.View.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ril.digitalwardrobeAI.Model.AddToWardrobeBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.WardrobeBean;
import com.ril.digitalwardrobeAI.Model.WardrobeItemBean;
import com.ril.digitalwardrobeAI.Network.Util;
import com.ril.digitalwardrobeAI.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ril.digitalwardrobeAI.Constants.IMAGE_URL_WARDROBE;
import static com.ril.digitalwardrobeAI.Constants.LOGTAG;
import static com.ril.digitalwardrobeAI.Constants.hearders;
import static com.ril.digitalwardrobeAI.Constants.imageList;
import static com.ril.digitalwardrobeAI.Constants.restResponse;
import static com.ril.digitalwardrobeAI.Constants.wardrobeItems;
import static com.ril.digitalwardrobeAI.Constants.wardrobeList;
import static com.ril.digitalwardrobeAI.View.Activity.LoginActivity.dbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ImageView addToWardrobe,compliItems,similarItems;
    ProgressDialog progressDialog;
    ArrayList<Target> targets = new ArrayList<>();
    String imageName,type;


    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        addToWardrobe=view.findViewById(R.id.addToWardrobe);
        similarItems=view.findViewById(R.id.similarItems);
        compliItems=view.findViewById(R.id.compliItems);
        progressDialog=new ProgressDialog(getContext());
        setData();
        ColorFragment colorFragment=new ColorFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,colorFragment).commit();



        addToWardrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.alert_add_message)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add item to wardrobe
                                addToWardrobe(dialog);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });



        similarItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                similarItems.setImageResource(R.drawable.similar_selected);
                compliItems.setImageResource(R.drawable.compli);
                SimilarFragment similarFragment=new SimilarFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,similarFragment,"Similar").commit();

            }
        });
        compliItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //products= complementaryProducts;
                compliItems.setImageResource(R.drawable.compl_selected);
                similarItems.setImageResource(R.drawable.similar);
                //mAdapter.setProducts(products);
                //mAdapter.notifyDataSetChanged();
                ComplimentaryFragment complimentaryFragment=new ComplimentaryFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,complimentaryFragment,"Compli").commit();
            }
        });

        return view;
    }

    void addToWardrobe(final DialogInterface dialog){
        AddToWardrobeBean addToWardrobeBean=new AddToWardrobeBean(restResponse.getObject().getStaticAssetId());
        Log.d(LOGTAG+"Main Fragment " ,addToWardrobeBean.getStaticAssetId());
        Util.getUserService().addToWardrobe(hearders,addToWardrobeBean).enqueue(new Callback<RestResponseBean>() {
            @Override
            public void onResponse(Call<RestResponseBean> call, Response<RestResponseBean> response) {
                if(response.isSuccessful())
                {
                    getWardrobeItems();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(), "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBean> call, Throwable t) {
                Log.d(LOGTAG+"Main Fragment " , t.getMessage());
                Toast.makeText(getContext(),"Check your internet connection.",Toast.LENGTH_SHORT).show();

            }
        });
    }
  /*  public static Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

*/
public void setFragemnt(){

    setData();

}
    public void  getWardrobeItems(){
        progressDialog.setMessage("Adding item to wardrobe");
        progressDialog.show();
        Util.getUserService().getWardrobeItems(hearders).enqueue(new Callback<RestResponseBean<WardrobeBean>>() {
            @Override
            public void onResponse(Call<RestResponseBean<WardrobeBean>> call, Response<RestResponseBean<WardrobeBean>> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGTAG+"Main Fragment " , "--------Getting wardrobe items--------");
                    RestResponseBean<WardrobeBean> restResponse = response.body();
                    if (restResponse.isSuccess()) {
                        Log.d(LOGTAG+"Main Fragment " , "--------Fetched wardrobe items--------");
                        ArrayList<WardrobeItemBean> allWardrobeItems=new ArrayList<>();
                        allWardrobeItems = restResponse.getObject().getWardrobeItems();
                        wardrobeItems=new ArrayList<>();
                        for(int i=0;i<allWardrobeItems.size();i++){
                            if((allWardrobeItems.get(i).getTags().getCategory().equals("Tops"))||(allWardrobeItems.get(i).getTags().getCategory().equals("Skirts"))||allWardrobeItems.get(i).getTags().getCategory().equals("Pants")) {
                                wardrobeItems.add(allWardrobeItems.get(i));
                            }
                        }
                        imageList=dbHelper.getImages();
                        for(WardrobeItemBean wardrobeItemBean: wardrobeItems)
                        {
                            if(!imageList.contains(wardrobeItemBean.getRawImages().get(0))){
                                imageName =wardrobeItemBean.getRawImages().get(0);
                                type=wardrobeItemBean.getTags().getCategory();
                                break;
                            }
                        }
                        if(imageName!=null) {
                            Log.d(LOGTAG+"Main Fragment " , "--------saving inage added to local -------- "+imageName );
                            HashMap<String, String> item = new HashMap();
                            item.put(imageName, type);
                            downloadImage(item);


                        }


                    } else {
                        Toast.makeText(getContext(), "Please try again.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }

                } else {
                    Toast.makeText(getContext(), "Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<RestResponseBean<WardrobeBean>> call, Throwable t) {
                Log.d(LOGTAG+"Main Fragment " , "onFailure: "+"Message Sent Failed "+t.getMessage());
                Toast.makeText(getContext(),"Check your internet connection.",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    public Target getTarget(final String imageName, final String type){
        Target target=new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File myDir = new File(getContext().getCacheDir()+ "/dw/");
                            if (!myDir.exists()) {
                                myDir.mkdirs();
                            }
                            myDir = new File(myDir, imageName);
                            FileOutputStream fos = new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                            dbHelper.insertImageUrl(imageName, type);
                            wardrobeList = dbHelper.getWardrobe();
                            imageList = dbHelper.getImages();
                            Log.d(LOGTAG+"Main Fragment " , imageName + " saved to local " + " image list size " + imageList.size());

                            fos.flush();
                            fos.close();
                        } catch (Exception e) {
                        }
                    }}).start();

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        return target;
    }



    public void downloadImage(HashMap<String,String> item){
        Picasso picasso = Picasso.get();
        String imageName = null;
        for (HashMap.Entry<String, String> e : item.entrySet()) {
            imageName = e.getKey();
        }
        Target target =getTarget(imageName,item.get(imageName));
        targets.add(target);
        Toast.makeText(getContext(),"Item added to wardrobe",Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        picasso.load(IMAGE_URL_WARDROBE +imageName)
                .into(target);
    }
    void setData()
    {
        similarItems.setImageResource(R.drawable.similar);
        compliItems.setImageResource(R.drawable.compli);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
