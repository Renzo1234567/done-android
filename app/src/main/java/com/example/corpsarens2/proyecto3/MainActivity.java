package com.example.corpsarens2.proyecto3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String token,nombreusuario;
    Button Entrar;
    EditText  Usuario;
    EditText Contraseña;
    TextView Texto1;
    TextView Texto2;
    TextView Texto3;
    TextView Texto4;
    TextView Texto5;
    Button OlvideContraseña;
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

        OlvideContraseña=(Button) findViewById(R.id.Olvide_button);
        Texto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Texto2= new Intent(MainActivity.this,Main2Activity.class);
                startActivity(Texto2);
            }
        });

        OlvideContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recuperar= new Intent(MainActivity.this,RecuperarPassword.class);
                startActivity(recuperar);
            }
        });

        Entrar.setEnabled(true);
        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username=Usuario.getText().toString();
                String Password=Contraseña.getText().toString();
                UsuariosLogin usuarioslogin = new UsuariosLogin(Username,Password);
                sendRequestNetwork(usuarioslogin,Username);
                Entrar.setEnabled(false);
            }
        });




    }

    private void sendRequestNetwork(final UsuariosLogin usuarioslogin, final String username) {
        String usuario;
        SendNetworkRequest enviar=new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service = retrofit.create(UserClient.class);
        Call<UsuariosLogin> call = service.createLogin(usuarioslogin);
        call.enqueue(new Callback<UsuariosLogin>() {
            @Override
            public void onResponse(Call<UsuariosLogin> call, Response<UsuariosLogin> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Login completado ",Toast.LENGTH_LONG).show();
                            token=response.body().getToken().toString();
                            getnombre(username);
                            getToken(token);
                            Intent Entrar1= new Intent(MainActivity.this,Main3Activity.class);
                            Entrar1.putExtra("token",token);
                            Entrar1.putExtra("Usuario",username);
                            startActivity(Entrar1);

                                Entrar.setEnabled(true);



                } else
                {

                    try
                    {

                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(),jObjError.getString("mensaje"),Toast.LENGTH_LONG).show();
                        Entrar.setEnabled(true);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Error en el Servidor " , Toast.LENGTH_LONG).show();
                        Entrar.setEnabled(true);
                    }
                }

            }

            @Override
            public void onFailure(Call<UsuariosLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error en el servidor",Toast.LENGTH_SHORT).show();
                Entrar.setEnabled(true);

            }
        });

    }
    @Override
    public void onBackPressed() {

    }
    public String getnombre(String username){
        String usuario=username;

        return usuario;
    }
    public String getToken(String token){
       String token1=token;
        return token1;
    }

}
