package com.example.corpsarens2.proyecto3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_VerTareas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_VerTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_VerTareas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   private RecyclerView recyclerdatos;
   private RecyclerView.Adapter adapter;

    private List<ListaItems> listaobjeto;
    private LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;
   private ArrayList<ListaItems> data;
   private List<TareaDatos> tarea;
   private List<Listado> listado;
   private Spinner spinner;
   private TextView textView;


    public Fragment_VerTareas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_VerTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_VerTareas newInstance(String param1, String param2) {
        Fragment_VerTareas fragment = new Fragment_VerTareas();
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
        View vista=inflater.inflate(R.layout.fragment_fragment__ver_tareas, container, false);
        final RecyclerView recyclerView=(RecyclerView) vista.findViewById(R.id.recyclerid);
        recyclerView.setHasFixedSize(true);
        listaobjeto=new ArrayList<>();
        listado=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final String token = getArguments().getString("token");
        final String usuario = getArguments().getString("Usuario");
        getuserclient(token,recyclerView,usuario);
        spinner=(Spinner)vista.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String recuperarcategoria=adapterView.getSelectedItem().toString();
                if (recuperarcategoria.equals("Todas")){
                    getuserclient(token,recyclerView,usuario);
                }
                if(recuperarcategoria.equals("Personal")){
                    getcategorias(token,recyclerView,"Personal",usuario);
                }
                if(recuperarcategoria.equals("Estudios")){
                    getcategorias(token,recyclerView,"Estudios",usuario);
                }
                if(recuperarcategoria.equals("Trabajo")){
                    getcategorias(token,recyclerView,"Trabajo",usuario);
                }
                if(recuperarcategoria.equals("Hogar")){
                    getcategorias(token,recyclerView,"Hogar",usuario);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return vista;
    }

    private void getcategorias(final String token, final RecyclerView recyclerView, String categoria, final String usuario) {
        final ProgressDialog progressDialog = new ProgressDialog((getActivity()));
        progressDialog.setMessage("Cargando..");
        progressDialog.show();
        SendNetworkRequest enviar = new SendNetworkRequest();

        Retrofit retrofit = enviar.Enviar();

        UserClient service = retrofit.create(UserClient.class);
        Call<GetCategorias> call=service.getcategoria(token,categoria);
        call.enqueue(new Callback<GetCategorias>() {
            @Override
            public void onResponse(Call<GetCategorias> call, Response<GetCategorias> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                  if (response.body().getMensaje()==null){
                      recyclerView.setVisibility(View.VISIBLE);
                      List<TareaDatos> lista=response.body().getTarea();

                      adapter=new AdapterDatos(lista,getContext(),token,usuario);
                      recyclerView.setAdapter(adapter);
                  }else{
                      recyclerView.setVisibility(View.GONE);

                  }
                }
                else
                {
                    try
                    {

                        JSONArray jObjError = new JSONArray(response.errorBody().string());
                        Toast.makeText(getActivity(),jObjError.getJSONObject(0).getString("mensaje"),Toast.LENGTH_LONG).show();


                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"Error en el servidor " , Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategorias> call, Throwable t) {
                Toast.makeText(getContext(),"Error en el Servidor",Toast.LENGTH_LONG);
            }
        });

    }

    private void getuserclient(final String token, final RecyclerView recyclerView, final String usuario) {
        try{
            final ProgressDialog progressDialog = new ProgressDialog((getActivity()));
            progressDialog.setMessage("Cargando..");
            progressDialog.show();
            SendNetworkRequest enviar = new SendNetworkRequest();

            Retrofit retrofit = enviar.Enviar();

            UserClient service = retrofit.create(UserClient.class);
            Call<ListaItems> call =service.getusers(token);
            call.enqueue(new Callback<ListaItems>() {

                @Override
                public void onResponse(Call<ListaItems> call, Response<ListaItems> response) {

                progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
               List<TareaDatos> lista=response.body().getTarea();
               adapter=new AdapterDatos(lista,getContext(),token,usuario);
               recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<ListaItems> call, Throwable t) {
                    System.out.println("FALLO");
                }
            });
    }catch (Exception e){}}



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
