package com.example.corpsarens2.proyecto3;
import com.google.gson.annotations.SerializedName;
public class Usuarios {

    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("nombre")
    String nombre;
    @SerializedName("apellido")
    String apellido;
    @SerializedName("fechaDeNacimiento")
    String fechaDeNacimiento;
    @SerializedName("formaDeRegistro")
    String formaDeRegistro;

     String mensaje;


    public Usuarios(String email, String username, String password, String nombre, String apellido, String fechaDeNacimiento, String formaDeRegistro){
        this.username=username;
        this.email=email;
        this.password=password;
        this.nombre = nombre;
        this.apellido= apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.formaDeRegistro = formaDeRegistro;
    }

    public String getMessage(){
        return mensaje;
    }


}
