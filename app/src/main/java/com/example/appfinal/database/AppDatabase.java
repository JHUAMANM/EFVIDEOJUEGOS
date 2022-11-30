package com.example.appfinal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appfinal.daos.CuentaDao;
import com.example.appfinal.entities.Cuenta;

@Database(entities = {Cuenta.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CuentaDao cuentaDao();

    public static AppDatabase getInstance(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "CUENTA_DB")
                .allowMainThreadQueries()
                .build();
    }


}
