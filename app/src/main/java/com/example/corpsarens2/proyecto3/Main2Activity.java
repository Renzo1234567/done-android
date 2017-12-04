package com.example.corpsarens2.proyecto3;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView Texto1;
    EditText Nombre;
    EditText  Apellido;
    EditText  Fecha_Nacimiento;
    EditText Repetir_Contraseña;
    EditText  Usuario;
    EditText Contraseña;
    EditText Email;
    Button Registrarse;
    ImageView Icono1;
    ImageView Icono2;
    ImageView Icono3;
    ImageView Icono4;
    ImageView Icono5;
    ImageView Icono6;
    ImageView Icono7;
    int dia,mes,año;
    int año_hoy,dia_hoy,mes_hoy;
    int recuperar_año,recuperar_mes,recuperar_dia;
    Boolean admitir=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Icono6=(ImageView) findViewById(R.id.imageView11) ;
        Icono7=(ImageView) findViewById(R.id.imageView12) ;
        Nombre=(EditText) findViewById(R.id.Introducir_Apellido)   ;
        Apellido= (EditText) findViewById(R.id.Introducir_Nombre) ;
        Texto1=(TextView) findViewById(R.id.Texto1);
        Icono1=(ImageView) findViewById(R.id.imageView6);
        Icono2=(ImageView) findViewById(R.id.imageView7);
        Icono3=(ImageView) findViewById(R.id.imageView8);
        Icono4=(ImageView) findViewById(R.id.imageView9);
        Icono5=(ImageView) findViewById(R.id.imageView10);
        Email=(EditText) findViewById(R.id.Introducir_Email);
        Contraseña=(EditText) findViewById(R.id.Introducir_Contraseña);
        Usuario=(EditText) findViewById(R.id.Introducir_Usuario);
        Fecha_Nacimiento=(EditText)findViewById(R.id.Introducir_Fecha);
        Fecha_Nacimiento.setOnClickListener(this);
        Repetir_Contraseña=(EditText)findViewById(R.id.Introducir_Repetir);
        Registrarse = (Button)findViewById((R.id.Boton_Registrarse));


       Registrarse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validar();
               String username, email, password, nombre, apellido, fechaDeNacimiento, formaDeRegistro;
               nombre = Nombre.getText().toString();
               email = Email.getText().toString();
              password = Contraseña.getText().toString();
               username = Usuario.getText().toString();
               apellido = Apellido.getText().toString();
               fechaDeNacimiento = Fecha_Nacimiento.getText().toString();
               formaDeRegistro ="Android";




               if (admitir == true) {
                   Usuarios usuario = new Usuarios(
                           email,
                           username,
                           password,
                           nombre,
                           apellido,
                           fechaDeNacimiento,
                           formaDeRegistro

                   );
                   sendRequestNetwork(usuario);

               }
           }
       });

    }

    private void sendRequestNetwork(Usuarios usuario) {
        SendNetworkRequest enviar=new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service = retrofit.create(UserClient.class);
        Call<Usuarios> call = service.create(usuario);

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {

                if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Usuario registrado! "+response.body().getMessage(),Toast.LENGTH_LONG).show();
                } else
                {
                    try
                    { JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getApplicationContext(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Algo fallo... "+ e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                    Toast.makeText(Main2Activity.this,"Algo fallo...",Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void validar(){
        admitir=true;
        Nombre.setError(null);
        Apellido.setError(null);
        Usuario.setError(null);
        Email.setError(null);
        Contraseña.setError(null);
        Fecha_Nacimiento.setError(null);
        Repetir_Contraseña.setError(null);
        String email=Email.getText().toString();
        String nombre=Nombre.getText().toString();
        String apellido=Apellido.getText().toString();
        String usuario=Usuario.getText().toString();
        String contraseña=Contraseña.getText().toString();
        String repetir_contraseña=Repetir_Contraseña.getText().toString();
        String fecha_nacimiento=Fecha_Nacimiento.getText().toString();
        int valor;


        if (TextUtils.isEmpty(email)){
           validarvacio(Email);
        }
        if (TextUtils.isEmpty(nombre)){
            validarvacio(Nombre);
        }
        if (TextUtils.isEmpty(apellido)){
            validarvacio(Apellido);
        }
        if (TextUtils.isEmpty(usuario)){
            validarvacio(Usuario);
        }
        if (TextUtils.isEmpty(contraseña)){
            validarvacio(Contraseña);
        }
        if (TextUtils.isEmpty(repetir_contraseña)){
            validarvacio(Repetir_Contraseña);
        }
        if (TextUtils.isEmpty(fecha_nacimiento)){
            validarvacio(Fecha_Nacimiento);
        }
        if(nombre.length()>50 || nombre.length()<1){
            validarlongitud(Nombre);
        }
        if(apellido.length()>50 || apellido.length()<1){
            validarlongitud(Apellido);
        }
        if(usuario.length()>20 || usuario.length()<1){
           Usuario.setError(getString(R.string.error_longitud_usuario));
            Usuario.requestFocus();
            admitir=false;

        }
        if(contraseña.length()>50 ||  contraseña.length()<8){
            Contraseña.setError(getString(R.string.error_longitud_contraseña));
            Contraseña.requestFocus();
            admitir=false;
        }
        if (email.length()>50){
            validarlongitud(Email);
        }
        if (contraseña.equals(repetir_contraseña)==false) {
            Repetir_Contraseña.setError(getString(R.string.error_contraseña));
            Repetir_Contraseña.requestFocus();
            admitir=false;
        }
        int edad1 = año_hoy-recuperar_año;
        if (edad1<18){
            Fecha_Nacimiento.setError(getString(R.string.error_edad));
            Fecha_Nacimiento.requestFocus();
            admitir=false;

        }
        if (edad1==18 && mes_hoy>recuperar_mes){
            admitir=true;
        }

        if(edad1==18 &&mes_hoy<recuperar_mes){
            Fecha_Nacimiento.setError(getString(R.string.error_edad));
            Fecha_Nacimiento.requestFocus();
            admitir=false;
        }
        if (edad1==18 && mes_hoy==recuperar_mes){
            if (dia_hoy>recuperar_dia){
                admitir=true;
            }
            if(dia_hoy<recuperar_dia){
                Fecha_Nacimiento.setError(getString(R.string.error_edad));
                Fecha_Nacimiento.requestFocus();
                admitir=false;
            }
            if(dia_hoy==recuperar_dia){
                admitir=true;
            }
        }


    }

    @Override
    public void onClick(View v) {
        Time today=new Time (Time.getCurrentTimezone());
        today.setToNow();
         dia_hoy=today.monthDay;
         mes_hoy=today.month;
          año_hoy=today.year;
        mes_hoy=mes_hoy+1;
        if (v==Fecha_Nacimiento){
            final Calendar c=Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            año=c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                    mes= mes+1;
                    Fecha_Nacimiento.setText(mes+"/"+dia+"/"+año);
                    recuperar_año=año;
                    recuperar_dia=dia;
                    recuperar_mes=mes;
                }
            }
            ,año,mes,dia);
            datePickerDialog.show();
        }



    }
        public boolean validarvacio(EditText campo){
            campo.setError(getString(R.string.error_campo_obligatorio));
            campo.requestFocus();
            admitir=false;
            return admitir;
        }
        public boolean validarlongitud(EditText campo){
            campo.setError(getString(R.string.error_longitud));
            campo.requestFocus();
            admitir=false;
            return admitir;
        }

}
