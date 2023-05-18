package com.example.myapplication.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    companion object{
//        fun getClient(): Retrofit {
//            var retrofit : Retrofit? = null
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
//            retrofit = Retrofit.Builder()
//                    .baseUrl("https://api.thingspeak.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build()
//            return retrofit
//        }
//    }
    public static Retrofit getClient(){
        Retrofit retrofit;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }
}
