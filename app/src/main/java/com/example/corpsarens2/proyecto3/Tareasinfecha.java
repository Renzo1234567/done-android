package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 07/01/2018.
 */

public class Tareasinfecha {
    @SerializedName("titulo")
    String titulo;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("categoria")
    String categoria;

    String mensaje;

    public Tareasinfecha(String titulo,String descripcion,String categoria){
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.categoria=categoria;
    }
    public String getMessage(){
        return mensaje;
    }
}

