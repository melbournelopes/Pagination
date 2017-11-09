package com.demo.coupondemo.interfaces;

import com.demo.coupondemo.models.ResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebApiInterface {
    @GET("task?")
    Call<ResponseModel> getData(@Query("page") String pageNo);

}