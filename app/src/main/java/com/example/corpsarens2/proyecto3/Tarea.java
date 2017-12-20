package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;



public class Tarea {
    @SerializedName("titulo")
    String titulo;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("fechaParaSerCompletada")
    String fechaParaSerCompletada;

    String mensaje;

    public Tarea(String titulo,String descripcion,String fechaParaSerCompletada){
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.fechaParaSerCompletada=fechaParaSerCompletada;
    }
    public String getMessage(){
        return mensaje;
    }
}
