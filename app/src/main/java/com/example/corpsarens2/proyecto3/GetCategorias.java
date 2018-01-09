package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Corp Sarens2 on 08/01/2018.
 */

public class GetCategorias {
    @SerializedName("tarea")
    @Expose
    private List<TareaDatos> tarea = null;
    String mensaje;

    public GetCategorias(List<TareaDatos> tarea) {
        super();
        this.tarea = tarea;
    }

    public List<TareaDatos> getTarea() {

        return tarea;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setTarea(List<TareaDatos> tarea) {
        this.tarea = tarea;
    }
}
