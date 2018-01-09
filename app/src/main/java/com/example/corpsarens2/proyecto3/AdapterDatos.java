package com.example.corpsarens2.proyecto3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
   private String usuario;

    public AdapterDatos(List<TareaDatos> listdatos, Context context,String token,String usuario) {
        this.listdatos = listdatos;
            this.context=context;
            this.token=token;
            this.usuario=usuario;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list,parent,false);

        return new ViewHolderDatos(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolderDatos holder, final int position) {

        final TareaDatos listaItems = listdatos.get(position);
        final String titulo = listaItems.getTitulo();
        final String id=listaItems.getId().toString();
        holder.titulo.setText(listaItems.getTitulo());
        holder.descripcion.setText(listaItems.getDescripcion());
        holder.fecha.setText(listaItems.getFechaParaSerCompletada());
        if (listaItems.getFechaParaSerCompletada()!=null){
            holder.fecha.setText(listaItems.getFechaParaSerCompletada().substring(0,10));
        }
        if (listaItems.getCompletado()==true) {
            holder.check.setChecked(true);
            holder.check.setEnabled(false);
            holder.edit.setEnabled(false);

        }
        final TareaDatos data = listdatos.get(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.delete.setEnabled(false);
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
                                            if(response.isSuccessful()){
                                            Toast.makeText(context,"Tarea Eliminada",Toast.LENGTH_SHORT).show();
                                            removeitem(position,data);
                                            holder.delete.setEnabled(true);

                                        }else
                                            {

                                                try {
                                                    JSONObject jsonObject=new JSONObject(response.errorBody().toString());
                                                    Toast.makeText(context,jsonObject.getString("mensaje"), Toast.LENGTH_LONG);
                                                    holder.delete.setEnabled(true);
                                                } catch (JSONException e) {
                                                    Toast.makeText(context,"Error en respuesta del servidor",Toast.LENGTH_SHORT).show();
                                                    holder.delete.setEnabled(true);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<EliminarTarea> call, Throwable t) {
                                            Toast.makeText(context,"Error en el servidor",Toast.LENGTH_SHORT).show();
                                            holder.delete.setEnabled(true);
                                        }
                                    });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                holder.delete.setEnabled(true);
                            }
                        })

                       .setOnCancelListener(new DialogInterface.OnCancelListener() {
                           @Override
                           public void onCancel(DialogInterface dialogInterface) {
                               holder.delete.setEnabled(true);
                           }
                       })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recuperartitulo=listaItems.getTitulo();
                String recuperarfecha=listaItems.getFechaParaSerCompletada();
                String recuperardescripcion=listaItems.getDescripcion();
                String recuperarcategoria=listaItems.getCategoria();
                String recuperarid=listaItems.getId();
                Intent intent = new Intent(view.getContext(),editar_Tarea.class);
                intent.putExtra("token",token);
                intent.putExtra("Usuario",usuario);
                intent.putExtra("titulo",recuperartitulo);
                intent.putExtra("fechaParaSerCompletada",recuperarfecha);
                intent.putExtra("categoria",recuperarcategoria);
                intent.putExtra("descripcion",recuperardescripcion);
                intent.putExtra("id",recuperarid);
                view.getContext().startActivity(intent);

            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.check.isChecked()){
                    final TareaCompletada tareaCompletada=new TareaCompletada(true);
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(context);
                    builder.setTitle("Marcar como completado?")
                            .setMessage("Esta seguro que quiere marcar esta tarea como completada?, no podra modificarla despues")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SendNetworkRequest enviar=new SendNetworkRequest();
                                    Retrofit retrofit=enviar.Enviar();
                                    UserClient service= retrofit.create(UserClient.class);
                                    Call<TareaCompletada> call=service.completarTarea(id,tareaCompletada,token);
                                    call.enqueue(new Callback<TareaCompletada>() {
                                        @Override
                                        public void onResponse(Call<TareaCompletada> call, Response<TareaCompletada> response) {
                                            holder.check.setEnabled(false);
                                            holder.edit.setEnabled(false);
                                        }

                                        @Override
                                        public void onFailure(Call<TareaCompletada> call, Throwable t) {

                                        }
                                    });
                                holder.check.setEnabled(false);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                holder.check.setChecked(false);
                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    holder.check.setChecked(false);
                                }
                            })
                            .setIcon(R.drawable.check)
                            .show();
                }
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
        TextView titulo,fecha,descripcion,_id,sercompletada;
        ImageView delete,edit;
        CheckBox check;
        CardView card;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.titulo);
            fecha=(TextView) itemView.findViewById(R.id.fecha);
            descripcion=(TextView) itemView.findViewById(R.id.descripcion);
            delete=(ImageView)itemView.findViewById(R.id.imagedelete);
            edit=(ImageView)itemView.findViewById(R.id.imageedit);
            check=(CheckBox)itemView.findViewById(R.id.checkBox);
            card=(CardView)itemView.findViewById((R.id.Card));
            sercompletada=(TextView) itemView.findViewById(R.id.texto10);
        }

    }
}
