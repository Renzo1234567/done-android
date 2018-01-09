package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 07/01/2018.
 */

public class TareaCompletada {
    @SerializedName("completado")
    Boolean completado;

    public TareaCompletada(Boolean completado) {
        this.completado = completado;
    }

    public Boolean getCompletado() {
        return completado;
    }
}
