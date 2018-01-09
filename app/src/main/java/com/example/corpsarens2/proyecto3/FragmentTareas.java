package com.example.corpsarens2.proyecto3;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTareas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTareas extends Fragment {
    int dia,mes,año;
    int año_hoy,dia_hoy,mes_hoy;
    int recuperar_año,recuperar_mes,recuperar_dia;
    View rootView;
    TextView Titulo;
    EditText Edit_Titulo;
    TextView Descripcion;
    EditText Edit_Descripcion;
    TextView Fecha;
    EditText Edit_Fecha;
    Button Crear_Tarea;
    Spinner spiner;
    String recuperarcategoria;
    ImageButton delete;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentTareas() {
        // Required empty public co

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTareas newInstance(String param1, String param2) {

        FragmentTareas fragment = new FragmentTareas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView=inflater.inflate(R.layout.fragment_fragment_tareas, container, false);
       spiner=(Spinner)rootView.findViewById(R.id.spinner) ;
       Titulo=(TextView)rootView.findViewById(R.id.Titulo);
       Descripcion=(TextView)rootView.findViewById(R.id.Descripcion);
       Fecha=(TextView)rootView.findViewById(R.id.Edit_Fecha);
       Edit_Descripcion=(EditText)rootView.findViewById(R.id.Edit_Descripcion);
       Edit_Titulo=(EditText)rootView.findViewById(R.id.Edit_Titulo);
       Edit_Fecha=(EditText)rootView.findViewById(R.id.Edit_Fecha);
       delete=(ImageButton)rootView.findViewById(R.id.deletefecha);
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Edit_Fecha.setText("");
           }
       });
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
                    DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

       Crear_Tarea=(Button)rootView.findViewById((R.id.Crear_Tarea));

            Crear_Tarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    Crear_Tarea.setEnabled(false);
                        }
                }
            });



        return rootView;

    }

    private void sendRequestNetworksintarea(Tareasinfecha tareasinfecha) {
        String token=getArguments().getString("Token");
        SendNetworkRequest enviar= new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service=retrofit.create(UserClient.class);
        Call<Tareasinfecha> call=service.createTareaSinFecha(token,tareasinfecha);
        call.enqueue(new Callback<Tareasinfecha>() {
            @Override
            public void onResponse(Call<Tareasinfecha> call, Response<Tareasinfecha> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(),"Tarea Creada! ",Toast.LENGTH_LONG).show();
                    Crear_Tarea.setEnabled(true);

                } else
                {
                    try
                    { JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getActivity().getApplicationContext(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Error en el Servidor "+ e.getMessage() , Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    }
                }
            }


            @Override
            public void onFailure(Call<Tareasinfecha> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),"Error en el servidor..",Toast.LENGTH_SHORT).show();
                Crear_Tarea.setEnabled(true);
            }
        });
    }


    private void sendRequestNetwork(Tarea creartarea) {
        String token=getArguments().getString("Token");

        SendNetworkRequest enviar=new SendNetworkRequest();
        Retrofit retrofit=enviar.Enviar();
        UserClient service = retrofit.create(UserClient.class);
        Call<Tarea> call = service.createTarea(token,creartarea);

        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(),"Tarea Creada! ",Toast.LENGTH_LONG).show();
                    Crear_Tarea.setEnabled(true);

                } else
                {
                    try
                    { JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getActivity().getApplicationContext(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Error en el Servidor "+ e.getMessage() , Toast.LENGTH_LONG).show();
                        Crear_Tarea.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),"Error en el servidor..",Toast.LENGTH_SHORT).show();
                Crear_Tarea.setEnabled(true);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
