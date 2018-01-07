package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;



public class Tarea {
    @SerializedName("titulo")
    String titulo;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("fechaParaSerCompletada")
    String fechaParaSerCompletada;
    @SerializedName("categoria")
    String categoria;

    String mensaje;

    public Tarea(String titulo,String descripcion,String fechaParaSerCompletada,String categoria){
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.fechaParaSerCompletada=fechaParaSerCompletada;
        this.categoria=categoria;
    }
    public String getMessage(){
        return mensaje;
    }
}
