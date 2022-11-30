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
import com.example.appfinal.DetailsMovimientoActivity;
import com.example.appfinal.R;
import com.example.appfinal.entities.Movimiento;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter {

    List<Movimiento> dataMovimiento;

    public MovimientoAdapter(List<Movimiento> data){
        this.dataMovimiento = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_movimiento, parent, false);
        return new MovimientoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Movimiento movimiento = dataMovimiento.get(position);


        TextView tvTipoMov = holder.itemView.findViewById(R.id.tvTipoMov);
        TextView tvMontoCuenta = holder.itemView.findViewById(R.id.tvMontoCuenta);
        TextView tvUbicacion = holder.itemView.findViewById(R.id.tvLatitud);


        tvTipoMov.setText(String.valueOf(movimiento.tipo));
        tvMontoCuenta.setText(String.valueOf(movimiento.monto));
        tvUbicacion.setText(movimiento.latitud +"  "+ movimiento.longitud);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsMovimientoActivity.class);
                intent.putExtra("MOVIMIENTO", new Gson().toJson(movimiento));

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataMovimiento.size();
    }

    public class MovimientoViewHolder extends RecyclerView.ViewHolder{

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
