package com.example.corpsarens2.proyecto3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.IDNA;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Corp Sarens2 on 04/01/2018.
 */

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

   private List<TareaDatos> listdatos;
   private Context context;
   private String token;

    public AdapterDatos(List<TareaDatos> listdatos, Context context,String token) {
        this.listdatos = listdatos;
            this.context=context;
            this.token=token;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list,parent,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, final int position) {

        TareaDatos listaItems = listdatos.get(position);
        final String titulo = listaItems.getTitulo();
        final String id=listaItems.getId().toString();
        holder.titulo.setText(listaItems.getTitulo());
        holder.descripcion.setText(listaItems.getDescripcion());
        holder.fecha.setText(listaItems.getFechaParaSerCompletada());
        final TareaDatos data = listdatos.get(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Eliminar Tarea")
                        .setMessage("Esta seguro que quiere eliminar esta tarea?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                SendNetworkRequest enviar=new SendNetworkRequest();
                                Retrofit retrofit=enviar.Enviar();
                                UserClient service = retrofit.create(UserClient.class);
                                Call<EliminarTarea> call = service.eliminarTarea(id,token);
                                    call.enqueue(new Callback<EliminarTarea>() {
                                        @Override
                                        public void onResponse(Call<EliminarTarea> call, Response<EliminarTarea> response) {
                                            Toast.makeText(context,"Tarea Eliminada",Toast.LENGTH_SHORT).show();
                                            removeitem(position,data);
                                        }

                                        @Override
                                        public void onFailure(Call<EliminarTarea> call, Throwable t) {
                                            Toast.makeText(context,"Error en el servidor",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    private void removeitem(int position,TareaDatos data){
        int posicion=listdatos.indexOf(data);
        listdatos.remove(posicion);
        notifyItemRemoved(posicion);
    }
    @Override
    public int getItemCount() {
        return listdatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView titulo,fecha,descripcion,_id;
        ImageView delete;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.titulo);
            fecha=(TextView) itemView.findViewById(R.id.fecha);
            descripcion=(TextView) itemView.findViewById(R.id.descripcion);
            delete=(ImageView)itemView.findViewById(R.id.imagedelete);
        }

    }
}
