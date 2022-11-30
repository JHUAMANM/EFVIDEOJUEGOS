package com.example.appfinal.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appfinal.entities.Cuenta;

import java.util.List;

@Dao
public interface CuentaDao {

    @Query("SELECT * FROM cuenta")
    List<Cuenta> getAll();

    @Query("SELECT * FROM cuenta WHERE id = :id")
    Cuenta find(int id);

    @Insert
    void create(Cuenta cuenta);

    @Update
    void update(Cuenta cuenta);

    @Delete
    void delete(Cuenta cuenta);
}
