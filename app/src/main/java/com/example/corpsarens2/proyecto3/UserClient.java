package com.example.corpsarens2.proyecto3;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.PATCH;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface UserClient {

    @GET("tareas")
    Call<ListaItems>getusers(
            @Header("X-auth") String token);


    @POST("usuarios")
    Call<Usuarios> create(@Body Usuarios usuario);


    @POST("usuarios/login")
    Call<UsuariosLogin> createLogin(@Body UsuariosLogin usuarioslogin);


    @POST("tareas")
    Call<Tarea> createTarea(
            @Header("X-auth") String token,
            @Body Tarea tarea);

    @DELETE("tareas/{id}")
    Call<EliminarTarea> eliminarTarea(@Path("id") String id,@Header("X-auth") String token);

    @POST("tareas")
    Call<Tareasinfecha>createTareaSinFecha(
            @Header("X-auth") String token,
            @Body Tareasinfecha tarea);

    @PATCH("tareas/{id}")
    Call<TareaCompletada> completarTarea(@Path("id") String id,@Body TareaCompletada tareaCompletada,@Header("X-auth") String token);

    @POST("usuarios/changepass")
    Call<EnviarEmail>enviaremail(@Body EnviarEmail enviarEmail);

    @GET("tareas/{categoria}")
    Call<GetCategorias> getcategoria(@Header("X-auth") String token,@Path("categoria") String categoria);

    @PATCH("usuarios/me/pass")
    Call<CambiarContrasena> cambiarcontraseña(@Header("X-auth") String token, @Body CambiarContrasena cambiarcontraseña );

    @PATCH("usuario/modificar")
    Call<Tareasinfecha> modificartareasinfecha(
            @Header("X-auth") String token,
            @Body Tareasinfecha tarea);
    @PATCH("usuario/modificar")
    Call<Tarea> modificartareaconfecha(
            @Header("X-auth") String token,
            @Body Tarea tarea);
}
