package com.example.appfinal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinal.DetailsCuentaActivity;
import com.example.appfinal.FormCuentaActivity;
import com.example.appfinal.R;
import com.example.appfinal.entities.Cuenta;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter {

    List<Cuenta> cuentaData;


    public CuentaAdapter(List<Cuenta> data){

        this.cuentaData = data;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_cuenta, parent, false);
        return new CuentaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Cuenta cuenta = cuentaData.get(position);

        TextView tvNombre = holder.itemView.findViewById(R.id.tvNombre);




        tvNombre.setText(cuenta.nombre);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsCuentaActivity.class);
                intent.putExtra("CUENTA_DATA", new Gson().toJson(cuenta));

                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cuentaData.size();
    }

    public  class CuentaViewHolder extends RecyclerView.ViewHolder{

        public CuentaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
