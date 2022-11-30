package com.example.appfinal.service;


import com.example.appfinal.entities.ImageResponse;
import com.example.appfinal.entities.Imagen;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageService {

    //Guardar
    @POST("3/image")
    Call<Imagen> sendImage(@Body ImageResponse image);

}
