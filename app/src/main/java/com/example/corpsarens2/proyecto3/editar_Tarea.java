package com.example.corpsarens2.proyecto3;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class editar_Tarea extends AppCompatActivity {
    TextView Titulo;
    int dia,mes,año;
    int año_hoy,dia_hoy,mes_hoy;
    int recuperar_año,recuperar_mes,recuperar_dia;
    EditText Edit_Titulo;
    TextView Descripcion;
    EditText Edit_Descripcion;
    TextView Fecha,Tarea;
    EditText Edit_Fecha;
    Button Crear_Tarea;
    Spinner spiner;
    String recuperarcategoria;
    ImageButton delete;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__tarea);
        spiner=(Spinner) findViewById(R.id.spinner) ;
        Titulo=(TextView)findViewById(R.id.Titulo);
        Descripcion=(TextView)findViewById(R.id.Descripcion);
        Fecha=(TextView)findViewById(R.id.Edit_Fecha);
        Edit_Descripcion=(EditText)findViewById(R.id.Edit_Descripcion);
        Edit_Titulo=(EditText)findViewById(R.id.Edit_Titulo);
        Edit_Fecha=(EditText)findViewById(R.id.Edit_Fecha);
        delete=(ImageButton)findViewById(R.id.deletefecha);
        Crear_Tarea=(Button)findViewById(R.id.Crear_Tarea);
        Tarea=(TextView) findViewById(R.id.Editando);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Fecha.setText("");
            }
        });

       token=getIntent().getExtras().getString("token");

        String titulo=getIntent().getExtras().getString("titulo");
        String descripcion=getIntent().getExtras().getString("descripcion");
        String fechaParaSerCompletada=getIntent().getExtras().getString("fechaParaSerCompletada");
        String categoria=getIntent().getExtras().getString("categoria");
        Edit_Descripcion.setText(descripcion);
        if (fechaParaSerCompletada!=null) {
            String año1 = fechaParaSerCompletada.substring(0, 4);
            String mes1 = fechaParaSerCompletada.substring(6, 7);
            String dia1 = fechaParaSerCompletada.substring(9, 10);
            Edit_Fecha.setText(mes1 + "/" + dia1 + "/" + año1);
        }
        Edit_Titulo.setText(titulo);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recuperarcategoria=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Edit_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time today=new Time (Time.getCurrentTimezone());
                today.setToNow();
                dia_hoy=today.monthDay;
                mes_hoy=today.month;
                año_hoy=today.year;
                mes_hoy=mes_hoy+1;
                if (view==Edit_Fecha){
                    final Calendar c=Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    año=c.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog= new DatePickerDialog(editar_Tarea.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                            mes= mes+1;
                            Edit_Fecha.setText(mes+"/"+dia+"/"+año);
                            recuperar_año=año;
                            recuperar_dia=dia;
                            recuperar_mes=mes;
                        }
                    }
                            ,año,mes,dia);
                    datePickerDialog.show();
                }
            }
        });

        Crear_Tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crear_Tarea.setEnabled(false);
                String titulo,fecha,descripcion;

                titulo= Edit_Titulo.getText().toString();
                fecha= Fecha.getText().toString();
                descripcion= Edit_Descripcion.getText().toString();
                if (fecha.equals("")) {
                    Tareasinfecha creartareasinfecha=new Tareasinfecha(titulo,descripcion,recuperarcategoria);
                    sendRequestNetworksintarea(creartareasinfecha);
                }else{
                    Tarea creatarea = new Tarea(titulo, descripcion, fecha, recuperarcategoria);
                    sendRequestNetwork(creatarea);

                }
                String nombreusuario=getIntent().getExtras().getString("Usuario");
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("Usuario",nombreusuario);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

    }

    private void sendRequestNetwork(Tarea creatarea) {
        SendNetworkRequest enviar=new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service = retrofit.create(UserClient.class);
        Call<Tarea> call = service.modificartareaconfecha(token,creatarea);

        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {

                if (response.isSuccessful()) {
                    String nombreusuario=getIntent().getExtras().getString("Usuario");
                    Toast.makeText(getApplicationContext(),"Tarea Editada!",Toast.LENGTH_LONG).show();
                    Crear_Tarea.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                    intent.putExtra("Usuario",nombreusuario);
                    intent.putExtra("token",token);
                    startActivity(intent);

                } else
                {
                    try
                    { JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getApplicationContext(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Error en el Servidor "+ e.getMessage() , Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error en el servidor..",Toast.LENGTH_SHORT).show();
                Crear_Tarea.setEnabled(true);
            }
        });

    }

    private void sendRequestNetworksintarea(Tareasinfecha creartareasinfecha) {
        SendNetworkRequest enviar= new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service=retrofit.create(UserClient.class);
        Call<Tareasinfecha> call=service.modificartareasinfecha(token,creartareasinfecha);
        call.enqueue(new Callback<Tareasinfecha>() {
            @Override
            public void onResponse(Call<Tareasinfecha> call, Response<Tareasinfecha> response) {
                if (response.isSuccessful()) {
                    String nombreusuario=getIntent().getExtras().getString("Usuario");
                    Toast.makeText(getApplicationContext(),"Tarea Modificada",Toast.LENGTH_LONG).show();
                    Crear_Tarea.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                    intent.putExtra("Usuario",nombreusuario);
                    intent.putExtra("token",token);
                    startActivity(intent);

                } else
                {
                    try
                    { JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getApplicationContext(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Error en el Servidor "+ e.getMessage() , Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    }
                }
            }


            @Override
            public void onFailure(Call<Tareasinfecha> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error en el servidor..",Toast.LENGTH_SHORT).show();
                Crear_Tarea.setEnabled(true);
            }
        });

    }
}
