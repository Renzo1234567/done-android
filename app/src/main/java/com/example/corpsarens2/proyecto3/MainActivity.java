package com.example.corpsarens2.proyecto3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button Entrar;
    EditText  Usuario;
    EditText Contraseña;
    TextView Texto1;
    TextView Texto2;
    TextView Texto3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Entrar = (Button) findViewById(R.id.Login_Usuario);
        Usuario = (EditText) findViewById(R.id.Nombre_Usuario);
        Contraseña = (EditText) findViewById(R.id.Contraseña_Usuario);
        Texto1 = (TextView) findViewById(R.id.Texto_1);
        Texto2 = (TextView) findViewById(R.id.Texto_2);
        Texto3 = (TextView) findViewById(R.id.Texto_3);
        Texto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Texto2= new Intent(MainActivity.this,Main2Activity.class);
                startActivity(Texto2);
            }
        });
        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username=Usuario.getText().toString();
                String Password=Contraseña.getText().toString();
                UsuariosLogin usuarioslogin = new UsuariosLogin(Username,Password);
                sendRequestNetwork(usuarioslogin);
            }
        });




    }

    private void sendRequestNetwork(UsuariosLogin usuarioslogin) {
        SendNetworkRequest enviar=new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service = retrofit.create(UserClient.class);
        Call<UsuariosLogin> call = service.createLogin(usuarioslogin);
        call.enqueue(new Callback<UsuariosLogin>() {
            @Override
            public void onResponse(Call<UsuariosLogin> call, Response<UsuariosLogin> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Login completado ",Toast.LENGTH_LONG).show();
                            Intent Entrar= new Intent(MainActivity.this,Main3Activity.class);
                            startActivity(Entrar);


                } else
                {

                    try
                    { JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(),jObjError.getString("mensaje"),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Algo fallo... " , Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UsuariosLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Algo fallo...",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
