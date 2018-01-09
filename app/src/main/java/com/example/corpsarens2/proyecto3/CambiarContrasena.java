package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 08/01/2018.
 */

public class CambiarContrasena {
    @SerializedName("passwordViejo")
    String contraseña_nueva;
    @SerializedName("password")
    String contraseña_vieja;

    public CambiarContrasena(String contraseña_nueva, String contraseña_vieja) {
        this.contraseña_nueva = contraseña_nueva;
        this.contraseña_vieja = contraseña_vieja;
    }

    public String getContraseña_nueva() {
        return contraseña_nueva;
    }

    public String getContraseña_vieja() {
        return contraseña_vieja;
    }
}
