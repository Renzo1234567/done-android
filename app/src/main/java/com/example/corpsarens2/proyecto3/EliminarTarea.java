package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 06/01/2018.
 */

public class EliminarTarea {
    @SerializedName("_id")
    String _id;

    String mensaje;

    public EliminarTarea(String _id) {
        this._id = _id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String get_id() {
        return _id;
    }
}
