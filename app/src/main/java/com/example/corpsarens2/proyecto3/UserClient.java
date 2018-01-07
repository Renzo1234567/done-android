package com.example.corpsarens2.proyecto3;

        import java.util.List;
        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.Headers;
        import retrofit2.http.POST;
        import retrofit2.http.Path;
        import retrofit2.http.GET;
        import retrofit2.http.Query;

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


}
