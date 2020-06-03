package com.ril.digitalwardrobeAI.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ril.digitalwardrobeAI.Constants.ROOT_URL;


/**
 * Created by 772250 on 12/20/2017.
 */

public class Util {
    public static String AccessToken = " ";
    public static String empId;
    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build();
    }

    public static UserServices getUserService() {

        return getRetrofitInstance().create(UserServices.class);
    }

}
