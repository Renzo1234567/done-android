package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Corp Sarens2 on 07/01/2018.
 */

public class EnviarEmail {
    @SerializedName("email")
    String email;
    String mensaje;
    public EnviarEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getMensaje() {
        return mensaje;
    }
}
