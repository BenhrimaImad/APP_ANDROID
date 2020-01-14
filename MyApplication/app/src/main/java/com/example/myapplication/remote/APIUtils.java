package com.example.myapplication.remote;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "https://pacific-springs-31866.herokuapp.com/";

    public static UserService getUserService(){


        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }

}