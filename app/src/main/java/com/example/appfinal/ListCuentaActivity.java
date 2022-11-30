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
import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.service.CuentaService;
import com.example.appfinal.service.ImageService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListCuentaActivity extends AppCompatActivity {

    private RecyclerView rvCuentas;
    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cuenta);

        rvCuentas = findViewById(R.id.rvCuentas);
        btnCrear = findViewById(R.id.btnCrear);


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FormCuentaActivity.class);
                startActivity(intent);

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6359bece38725a1746b71b5e.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CuentaService service = retrofit.create(CuentaService.class);
        service.listCuentas().enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                List<Cuenta> data = response.body();
                rvCuentas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvCuentas.setAdapter(new CuentaAdapter(response.body()));
                rvCuentas.setAdapter(new CuentaAdapter(data));

                Log.i("MAIN_APP","Response: "+response.body().size());
                Log.i("MAIN_APP", new Gson().toJson(data).toString());

                Toast.makeText(getApplicationContext(), "Se listo los datos correctamente...", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {

                Log.i("MAIN_APP", "Error al cargar");
                Toast.makeText(getApplicationContext(), "No se pudo listar los datos...", Toast.LENGTH_SHORT).show();

            }
        });

    }
}