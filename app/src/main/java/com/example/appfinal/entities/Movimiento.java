package com.example.appfinal.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movimientos")
public class Movimiento {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int cuentaId;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "monto")
    public Double monto;
    @ColumnInfo(name = "tipo")
    public int tipo;
    @ColumnInfo(name = "latitud")
    public Double latitud;
    @ColumnInfo(name = "longitud")
    public Double longitud;
    @ColumnInfo(name = "imageURL")
    public String imageURL;
}
