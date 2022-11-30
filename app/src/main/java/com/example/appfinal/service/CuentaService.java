package com.example.appfinal.service;

import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.entities.Movimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CuentaService {

    //Listar
    @GET("cuenta")
    Call<List<Cuenta>> listCuentas();

    //Guardar
    @POST("cuenta")
    Call<Void> create(@Body Cuenta cuenta);

    //Actualizar
    @PUT("cuenta/{idCuenta}")
    Call<Void> update(@Body Cuenta cuenta, @Path("idPokemon") int id);

    //Eliminar
    @DELETE("cuenta/{idCuenta}")
    Call<Void> delete(@Path("idCuenta") int id);

    @GET("/cuenta/{id}/movimiento")
    Call<List<Movimiento>> obtenerMovimiento(@Path("id") int id);

    @POST("/cuenta/{id}/movimiento")
    Call<Movimiento> crearMovimiento(@Path("id") int id, @Body Movimiento movimientos);
}
