package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appfinal.adapter.CuentaAdapter;
import com.example.appfinal.adapter.MovimientoAdapter;
import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.entities.Movimiento;
import com.example.appfinal.service.CuentaService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListMovimientoActivity extends AppCompatActivity {

    private RecyclerView rvMovimiento;
    private Button btnGoMovimiento;
    Cuenta cuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movimiento);

        rvMovimiento = findViewById(R.id.rvMovimiento);

        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("CUENTA_DATA");

        Log.i("MAIN_APP", new Gson().toJson(cuentaJson));

        if(cuentaJson != null){

            cuenta = new Gson().fromJson(cuentaJson, Cuenta.class);

        }
        else return;



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6359bece38725a1746b71b5e.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CuentaService service = retrofit.create(CuentaService.class);
       service.obtenerMovimiento(cuenta.id).enqueue(new Callback<List<Movimiento>>() {
           @Override
           public void onResponse(Call<List<Movimiento>> call, Response<List<Movimiento>> response) {
               List<Movimiento> datos = response.body();
               rvMovimiento.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
               rvMovimiento.setAdapter(new MovimientoAdapter(datos));


               Log.i("MAIN_APP","Response: "+response.body().size());
               Log.i("MAIN_APP", new Gson().toJson(datos).toString());

               Toast.makeText(getApplicationContext(), "datos correctamente...", Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onFailure(Call<List<Movimiento>> call, Throwable t) {

           }
       });

    }
}