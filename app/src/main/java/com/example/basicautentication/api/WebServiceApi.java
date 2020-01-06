package com.example.basicautentication.api;

import com.example.basicautentication.Model.Curso;
import com.example.basicautentication.Model.Profesor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WebServiceApi {

    @GET("/todos_profesores_public")
    Call<List<Profesor>> listAllProfesorPublic();

    @GET("/todos_profesores_admin")
    Call<List<Profesor>> listAllProfesorAdmin();

    @GET("/todos_profesores_user")
    Call<List<Profesor>> listAllProfesorUser();

    @GET("/todos_profesores_admin")
    Call<List<Profesor>> listAllProfesorAdmin(@Header("Authorization")String authHeader);

    @GET("/todos_profesores_user")
    Call<List<Profesor>> listAllProfesorUser(@Header("Authorization")String authHeader);


}

