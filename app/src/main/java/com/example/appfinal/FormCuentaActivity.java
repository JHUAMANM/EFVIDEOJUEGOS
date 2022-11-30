package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfinal.database.AppDatabase;
import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.service.CuentaService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormCuentaActivity extends AppCompatActivity {

    Cuenta cuenta = new Cuenta();
    private EditText etNombreCuenta;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cuenta);

        etNombreCuenta = findViewById(R.id.etNombreCuenta);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cuenta.nombre = etNombreCuenta.getText().toString();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://6359bece38725a1746b71b5e.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CuentaService service = retrofit.create(CuentaService.class);

                service.create(cuenta).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {


                        if(response.isSuccessful()){
                            AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                            db.cuentaDao().create(cuenta);

                            List<Cuenta> listaPokemones = db.cuentaDao().getAll();

                            Log.i("MAIN_APP", new Gson().toJson(listaPokemones));

                            Log.i("MAIN_APP","Response: "+response.code());


                            Toast.makeText(getApplicationContext(), "Se creo los datos correctamente...", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), ListCuentaActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(FormCuentaActivity.this, "Error en el servidor...", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });



    }
}