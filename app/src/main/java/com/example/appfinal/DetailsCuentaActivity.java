package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class DetailsCuentaActivity extends AppCompatActivity {

    Double cuentaSaldo =0d;
    private TextView tvNomCuenta, tvSaldo;
    private Button btnRegMovimiento;
    private Button btnVerMovimiento;
    private Button btnSinc;
    int i = 0;

    Cuenta cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cuenta);

        tvNomCuenta = findViewById(R.id.tvNomCuenta);

        btnRegMovimiento = findViewById(R.id.btnRegMovimiento);
        btnVerMovimiento = findViewById(R.id.btnVerMovimiento);
        tvSaldo = findViewById(R.id.tvSaldo);
        btnSinc = findViewById(R.id.btnSinc);







        btnRegMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovimientoCuentaActivity.class);
                intent.putExtra("CUENTA_ID", new Gson().toJson(cuenta));
                startActivity(intent);
            }
        });

        btnVerMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListMovimientoActivity.class);
                intent.putExtra("CUENTA_DATA", new Gson().toJson(cuenta));
                startActivity(intent);
            }
        });



        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("CUENTA_DATA");

        Log.i("MAIN_APP", new Gson().toJson(cuentaJson));

        if(cuentaJson != null){
            cuenta = new Gson().fromJson(cuentaJson, Cuenta.class);
            tvNomCuenta.setText(cuenta.nombre);


        }
        else return;



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6359bece38725a1746b71b5e.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CuentaService services = retrofit.create(CuentaService.class);
        services.obtenerMovimiento(cuenta.id).enqueue(new Callback<List<Movimiento>>() {
            @Override
            public void onResponse(Call<List<Movimiento>> call, Response<List<Movimiento>> response) {
                List<Movimiento> data = response.body();
                Log.i("MAIN_APP", "Response: "+response.body().size());
                Log.i("MAIN_APP", new Gson().toJson(data));

                for(i = 0; i < data.size(); i++){

                    String monto = String.valueOf(data.get(i).tipo);

                    Log.i("MAIN_APP", monto);

                    if(monto.equals("1")) {
                        cuentaSaldo += data.get(i).monto;
                    }
                    if(monto.equals("2")){
                        cuentaSaldo -= data.get(i).monto;
                    }
                }

                Log.i("MAIN_APP", new Gson().toJson(cuentaSaldo));

                tvSaldo.setText("S/. "+cuentaSaldo);
            }

            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se cargaron las cuentas", Toast.LENGTH_SHORT).show();
            }
        });


    }
}