package com.example.appfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appfinal.entities.Cuenta;
import com.example.appfinal.entities.ImageResponse;
import com.example.appfinal.entities.Imagen;
import com.example.appfinal.entities.Movimiento;
import com.example.appfinal.factories.RetrofitFactory;
import com.example.appfinal.service.CuentaService;
import com.example.appfinal.service.ImageService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovimientoCuentaActivity extends AppCompatActivity {

    Movimiento movimiento = new Movimiento();

    private final static int CAMERA_REQUEST = 1000;

    private Spinner spTipoCuenta;
    private Button btnPhoto;
    private Button btnGallery;

    private ImageView ivPhoto;

    public Double latitud;
    public Double longitud;

    private EditText etLatitud;
    private EditText etLongitud;
    private EditText etMontoMov;
    private EditText etMotivoMov;
    private Button btnUbicacion;
    private Button btnSaveMovimiento;
    private EditText etUrlImagen;

    Cuenta datosCuenta;

    String link = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_cuenta);

        spTipoCuenta = findViewById(R.id.spTiposCuenta);
        btnPhoto = findViewById(R.id.btnPhoto);
        btnGallery = findViewById(R.id.btnGallery);
        ivPhoto = findViewById(R.id.ivPhoto);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        etMontoMov = findViewById(R.id.etMontoMov);
        etMotivoMov = findViewById(R.id.etMotivoMov);
        etUrlImagen = findViewById(R.id.etUrlImagen);


        btnUbicacion = findViewById(R.id.btnUbicacion);
        btnSaveMovimiento = findViewById(R.id.btnSaveMovimiento);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_tipos, android.R.layout.simple_spinner_dropdown_item);

        spTipoCuenta.setAdapter(adapter);


        Intent intent = getIntent();
        String cuentaJson = intent.getStringExtra("CUENTA_ID");

        Log.i("MAIN_APP", new Gson().toJson(cuentaJson));





        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MovimientoCuentaActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    locationStart();
                }
            }
        });



        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }
                else{
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                }


            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    abrirCamara();
                }
                else{
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, 100);
                }

            }
        });

        btnSaveMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(cuentaJson != null){
                    datosCuenta = new Gson().fromJson(cuentaJson, Cuenta.class);
                    if(spTipoCuenta.getSelectedItem() == "INGRESO"){
                        movimiento.tipo = 1;
                    }
                    else{
                        movimiento.tipo = 2;
                    }


                    Double montoTotal = Double.valueOf(etMontoMov.getText().toString()) ;

                    if(montoTotal <= 0){
                        Toast.makeText(getApplicationContext(), "Monto incorrecto...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        movimiento.monto = montoTotal;
                        movimiento.description = etMotivoMov.getText().toString();
                        movimiento.latitud = latitud;
                        movimiento.longitud = longitud;
                        movimiento.imageURL = etUrlImagen.getText().toString();
                        movimiento.cuentaId = datosCuenta.id;



                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://6359bece38725a1746b71b5e.mockapi.io/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CuentaService service = retrofit.create(CuentaService.class);
                        service.crearMovimiento(datosCuenta.id, movimiento).enqueue(new Callback<Movimiento>() {
                            @Override
                            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {

                                if(response.isSuccessful()){

                                    Log.i("MAIN_APP","Response: "+response.code());


                                    Toast.makeText(getApplicationContext(), "Se creo los datos correctamente...", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), DetailsCuentaActivity.class);
                                    startActivity(intent);

                                }

                                Log.i("MAIN_APP","Response: "+response.code());


                                Toast.makeText(getApplicationContext(), "Se creo los datos correctamente...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), DetailsCuentaActivity.class);
                                startActivity(intent);


                            }

                            @Override
                            public void onFailure(Call<Movimiento> call, Throwable t) {

                            }
                        });
                    }


                }
                else return;
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);

    }

    private void abrirCamara() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhoto.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit retrofit = new RetrofitFactory(this)
                    .build("https://api.imgur.com/", "Client-ID 8bcc638875f89d9");

            ImageService imageService = retrofit.create(ImageService.class);
            ImageResponse image = new ImageResponse();
            image.image = imgBase64;


            imageService.sendImage(image).enqueue(new Callback<Imagen>() {
                @Override
                public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                    Imagen img = response.body();

                    etUrlImagen.setText(img.data.link);

                    //link = img.data.link;

                    GuardarPokemon();

                }

                @Override
                public void onFailure(Call<Imagen> call, Throwable t) {

                }
            });
        }

        if(requestCode == 1001) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap imageBitmap = BitmapFactory.decodeFile(picturePath);
            ivPhoto.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit retrofit = new RetrofitFactory(this)
                    .build("https://api.imgur.com/", "Client-ID 8bcc638875f89d9");

            ImageService imageService = retrofit.create(ImageService.class);
            ImageResponse image = new ImageResponse();
            image.image = imgBase64;


            imageService.sendImage(image).enqueue(new Callback<Imagen>() {
                @Override
                public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                    Imagen img = response.body();

                    //etPokemonPosterURL.setText(img.data.link);

                    GuardarPokemon();

                }

                @Override
                public void onFailure(Call<Imagen> call, Throwable t) {

                }
            });
        }
    }

    private void GuardarPokemon() {


        Retrofit r2 = new RetrofitFactory(MovimientoCuentaActivity.this)
                .build("https://api.imgur.com/", "Client-ID 8bcc638875f89d9");

        CuentaService s = r2.create(CuentaService.class);

        s.create(new Cuenta()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

/* Aqui empieza la Clase Localizacion */
public class Localizacion implements LocationListener {

    MovimientoCuentaActivity movimientoCuentaActivity;
    public MovimientoCuentaActivity getMainActivity() {
        return movimientoCuentaActivity;
    }

    public void setMainActivity(MovimientoCuentaActivity mainActivity) {
        this.movimientoCuentaActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // GPS recibe nuevas coordenadas por el cambio de ubicación

        loc.getLatitude();
        loc.getLongitude();
        latitud = loc.getLatitude();
        longitud = loc.getLongitude();
        etLatitud.setText("Latitud: " + latitud);
        etLongitud.setText("Longitud: " + longitud);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //GPS es desactivado
        Toast.makeText(getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        //GPS es activado
        Toast.makeText(getApplicationContext(), "GPS Activado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
}
}
