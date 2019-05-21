package com.example.trabalhoand.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhoand.config.ApiService;
import com.example.trabalhoand.config.UsuarioDes;
import com.example.trabalhoand.model.Usuario;
import com.example.trabalhoand.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private EditText edtNome, edtCep;
    private Button btnVerLista, btnCamera, btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        inicializarComponentes();

        Gson g = new GsonBuilder().registerTypeAdapter(Usuario.class, new UsuarioDes()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        final ApiService service = retrofit.create(ApiService.class);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtCep.getText().toString().isEmpty()){
                    Call<Usuario> callUsuarioCep = service.getCEP(edtCep.getText().toString());

                    callUsuarioCep.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(HomeActivity.this, "Erro ao procurar CEP", Toast.LENGTH_SHORT).show();
                            } else {
                                Usuario usuario = response.body();
                                usuario.setNome(edtNome.getText().toString());
                                usuario.salvar();
                                edtNome.setText("");
                                edtCep.setText("");

                                iniciaActivity();

                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {

                        }
                    });
                }


            }
        });


        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FotoActivity.class);
                startActivity(intent);
            }
        });



    }

    public void iniciaActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void inicializarComponentes(){

        edtNome = findViewById(R.id.edtNome);
        edtCep = findViewById(R.id.edtCep);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVerLista = findViewById(R.id.btnVerLista);
        btnCamera = findViewById(R.id.btnCamera);
    }
}
