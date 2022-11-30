package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.entities.Movimiento;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsMovimientoActivity extends AppCompatActivity {

    public Movimiento movimiento;

    private TextView tvDetalleMotivo, tvDetalleMonto, tvTipoMovimiento;
    private Button btnMaps;
    private ImageView ivPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movimiento);


        tvDetalleMotivo = findViewById(R.id.tvDetalleMotivo);
        tvDetalleMonto = findViewById(R.id.tvDetalleMonto);
        tvTipoMovimiento = findViewById(R.id.tvTipoMovimiento);
        ivPhoto = findViewById(R.id.ivDetallePhoto);
        btnMaps = findViewById(R.id.btnMaps);




        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("MOVIMIENTO_DATA", new Gson().toJson(movimiento));
                startActivity(intent);
            }
        });



        Intent intent = getIntent();
        String moviJson = intent.getStringExtra("MOVIMIENTO");

        Log.i("MAIN_APP", new Gson().toJson(moviJson));

        if(moviJson != null){
            movimiento = new Gson().fromJson(moviJson, Movimiento.class);

            tvTipoMovimiento.setText(String.valueOf(movimiento.tipo));
            tvDetalleMonto.setText(String.valueOf(movimiento.monto));
            tvDetalleMotivo.setText(movimiento.description);
            Picasso.get().load(movimiento.imageURL).into(ivPhoto);

        }
        else return;

    }
}