package com.example.corpsarens2.proyecto3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConfiguracion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConfiguracion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConfiguracion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView texto1,texto2,texto;
    EditText edit_nueva,edit_vieja;
    Button enviar2;

    private OnFragmentInteractionListener mListener;

    public FragmentConfiguracion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConfiguracion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConfiguracion newInstance(String param1, String param2) {
        FragmentConfiguracion fragment = new FragmentConfiguracion();
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
       View vista= inflater.inflate(R.layout.fragment_fragment_configuracion, container, false);
        final String token=getArguments().getString("Token");

        texto1=(TextView) vista.findViewById(R.id.contraseña_actual);
        texto2=(TextView) vista.findViewById(R.id.nuevaContraseña);
        edit_vieja=(EditText) vista.findViewById(R.id.editcontraseña);
        edit_nueva=(EditText) vista.findViewById(R.id.editnuevacontraseña);
        enviar2=(Button) vista.findViewById(R.id.Boton_Nueva_Contraseña);
        texto=(TextView) vista.findViewById((R.id.Texto));
        enviar2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                enviar2.setEnabled(false);
                String nueva=edit_vieja.getText().toString();
                String vieja=edit_nueva.getText().toString();
                CambiarContrasena cambiarContraseña=new CambiarContrasena(nueva,vieja);

                SendNetworkRequest enviar= new SendNetworkRequest();
                Retrofit retrofit=enviar.Enviar();
                UserClient service=retrofit.create(UserClient.class);
                Call<CambiarContrasena> call=service.cambiarcontraseña(token,cambiarContraseña);
                call.enqueue(new Callback<CambiarContrasena>() {
                    @Override
                    public void onResponse(Call<CambiarContrasena> call, Response<CambiarContrasena> response) {
                       enviar2.setEnabled(true);
                       if (response.isSuccessful()){
                           Toast.makeText(getActivity(),"Contraseña cambiada",Toast.LENGTH_LONG).show();
                       }else
                           try{
                           JSONObject jsonObject=new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(),jsonObject.getString("mensaje"),Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"Error en el servidor " , Toast.LENGTH_LONG).show();

                    }

                    }

                    @Override
                    public void onFailure(Call<CambiarContrasena> call, Throwable t) {
                        enviar2.setEnabled(true);
                        Toast.makeText(getActivity(),"Error en el servidor " , Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        return vista;
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
