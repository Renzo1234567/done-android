package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 05/01/2018.
 */

public class TareaDatos {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fechaParaSerCompletada")
    @Expose
    private String fechaParaSerCompletada;
    @SerializedName("_creador")
    @Expose
    private String creador;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("fechaDeRegistro")
    @Expose
    private String fechaDeRegistro;
    @SerializedName("completado")
    @Expose
    private Boolean completado;
    @SerializedName("categoria")
    @Expose
    private String categoria;
    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param id
     * @param fechaDeRegistro
     * @param v
     * @param titulo
     * @param completado
     * @param creador
     * @param descripcion
     * @param fechaParaSerCompletada
     */
    public TareaDatos(String id, String titulo, String descripcion, String fechaParaSerCompletada,String categoria, String creador, Integer v, String fechaDeRegistro, Boolean completado) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaParaSerCompletada = fechaParaSerCompletada;
        this.creador = creador;
        this.v = v;
        this.fechaDeRegistro = fechaDeRegistro;
        this.completado = completado;
        this.categoria=categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaParaSerCompletada() {
        return fechaParaSerCompletada;
    }

    public void setFechaParaSerCompletada(String fechaParaSerCompletada) {
        this.fechaParaSerCompletada = fechaParaSerCompletada;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public void setFechaDeRegistro(String fechaDeRegistro) {
        this.fechaDeRegistro = fechaDeRegistro;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public String getCategoria() {
        return categoria;
    }
}
