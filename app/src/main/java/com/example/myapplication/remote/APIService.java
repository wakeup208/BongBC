package com.example.myapplication.remote;

import com.example.myapplication.model.Response;
import com.example.myapplication.model.Test;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface APIService {
    @POST("storage/uploadFile")
    @Multipart
    public Call<String> postImage(@Part MultipartBody.Part file);

    @POST("post")
    @Multipart
    public Call<Test> uploadImagePostman(@Part MultipartBody.Part file);

    @POST("storage/uploadFile")
    @FormUrlEncoded
    public Call<String> postImage1(@Body String s);

    @GET("weather")
    public Call<Response> getWeather(@Query("q") String q, @retrofit2.http.Query("units") String units, @Query("appid") String appId);
}
