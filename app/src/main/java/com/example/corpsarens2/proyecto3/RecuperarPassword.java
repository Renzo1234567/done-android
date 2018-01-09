package com.example.corpsarens2.proyecto3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecuperarPassword extends AppCompatActivity {
    TextView Email;
    Button boton;
    EditText EditEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        Email=(TextView)findViewById(R.id.recuperar);
        boton=(Button) findViewById(R.id.Boton);
        EditEmail=(EditText) findViewById(R.id.ingresar_email);



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNetworkRequest enviar=new SendNetworkRequest();
                Retrofit retrofit=enviar.Enviar();
               String email=EditEmail.getText().toString();
                UserClient service = retrofit.create(UserClient.class);
                EnviarEmail enviar1=new EnviarEmail(email);
                Call<EnviarEmail> call = service.enviaremail(enviar1);
                call.enqueue(new Callback<EnviarEmail>() {
                    @Override
                    public void onResponse(Call<EnviarEmail> call, Response<EnviarEmail> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Email enviado, recibira un correo para cambiar su contraseña", Toast.LENGTH_LONG).show();
                        } else

                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText((getApplicationContext()), jsonObject.getString("mensaje"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText((getApplicationContext()), "Error en el servidor", Toast.LENGTH_LONG).show();
                            }

                    }

                    @Override
                    public void onFailure(Call<EnviarEmail> call, Throwable t) {
                        Toast.makeText((getApplicationContext()),"Error en el servidor",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
