package com.ril.digitalwardrobeAI.Network;

import com.ril.digitalwardrobeAI.Model.AddToWardrobeBean;
import com.ril.digitalwardrobeAI.Model.MissingItems;
import com.ril.digitalwardrobeAI.Model.PredictingPrice;
import com.ril.digitalwardrobeAI.Model.ResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBean;
import com.ril.digitalwardrobeAI.Model.RestResponseBeanCart;
import com.ril.digitalwardrobeAI.Model.Task;
import com.ril.digitalwardrobeAI.Model.VersionResponse;
import com.ril.digitalwardrobeAI.Model.WardrobeBean;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by 772250 on 12/20/2017.
 */

public interface UserServices {

    @Multipart
    @POST("/dw/upload/analyze")
    Call<RestResponseBean<ResponseBean>> analyzeImage(@HeaderMap HashMap<String, String> headers, @Part MultipartBody.Part fileBody);

    //get all wadrobe items
    @GET("dw/wardrobe/list")
    Call<RestResponseBean<WardrobeBean>> getWardrobeItems(@HeaderMap HashMap<String, String> headers);  //to be followed

    //get all wadrobe items
    @POST("dw/wardrobe/add")
    Call<RestResponseBean> addToWardrobe(@HeaderMap HashMap<String, String> headers, @Body AddToWardrobeBean addToWardrobeBean);

    //get app version
    @GET("/fileserver/appversion/listall")
    Call<VersionResponse> getAppVersion();

    //get all wadrobe items
    @GET("dw/popular-product/selling")
    Call<RestResponseBean<MissingItems>> getMissing(@HeaderMap HashMap<String, String> headers);  //to be followed

    //get all wadrobe items
    @GET("dw/popular-product/buying")
    Call<RestResponseBean<MissingItems>> getBuying(@HeaderMap HashMap<String, String> headers);  //to be followed

    @GET("dw/cart/showCart")
    Call<RestResponseBeanCart> getCartIds(@HeaderMap HashMap<String, String> headers);

    @GET("dw/popular-product")
    Call<RestResponseBean<MissingItems>> getCartDetails(@HeaderMap HashMap<String, String> headers,@Query( "nameID" ) String[] nameID);

    @POST("dw/cart/add/nameid/{nameid}")
    Call<RestResponseBeanCart> addCartDetails(@HeaderMap HashMap<String, String> headers,@Path( "nameid" ) String nameid);

    @POST("dw/cart/remove/nameid/{nameid}")
    Call<RestResponseBeanCart> removeCartDetails(@HeaderMap HashMap<String, String> headers,@Path( "nameid" ) String nameid);

    @POST("dw/wardrobe/pricing")
    Call<PredictingPrice> getPricingFactors(@Body Task task);

}
