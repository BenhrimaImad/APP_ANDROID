package com.example.myapplication.remote;

import com.example.myapplication.model.Classe;
import com.example.myapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {


    // User *****************************************

    @GET("user/")
    Call<List<User>> getUsers();

    @GET("user/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("user/email/{email}")
    Call<User> getUser(@Path("email") String email);

    @POST("user/")
    Call<User> addUser(@Body User user);

    @PUT("user/")
    Call<User> updateUser(@Body User user);

    @DELETE("user/{id}")
    Call<User> deleteUser(@Path("id") int id);

    // Classe ******************************************

    @GET("classe/")
    Call<List<Classe>> getClasses();

    @GET("classe/{id}")
    Call<Classe> getClasse();

    @POST("classe/")
    Call<Classe> addClasse(@Body Classe user);

    @PUT("classe/")
    Call<Classe> updateClasse(@Body Classe user);

    @DELETE("classe/{id}")
    Call<Classe> deleteClasse(@Path("id") int id);
}
