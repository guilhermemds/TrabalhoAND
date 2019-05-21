package com.example.trabalhoand.config;

import com.example.trabalhoand.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://viacep.com.br/ws/";

    @GET("{CEP}/json/")
    Call<Usuario> getCEP(@Path("CEP") String cep);

}
