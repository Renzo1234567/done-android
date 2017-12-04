package com.example.corpsarens2.proyecto3;

import com.google.gson.annotations.SerializedName;

public class UsuariosLogin {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public UsuariosLogin(String username, String password){
        this.username=username;
        this.password=password;
    }
}
