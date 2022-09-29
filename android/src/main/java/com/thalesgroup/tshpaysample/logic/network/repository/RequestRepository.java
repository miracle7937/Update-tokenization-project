package com.thalesgroup.tshpaysample.logic.network.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestRepository {
    public static final String BASE_URL = "https://webservicestest.zenithbank.com:8443/";

   public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
