package com.example.trabalhoand.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trabalhoand.R;
import com.example.trabalhoand.adapter.UsuarioAdapter;
import com.example.trabalhoand.config.ConfiguracaoFirebase;
import com.example.trabalhoand.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment {

    TextView txtFragment;
    List<Usuario> usuarios;
    UsuarioAdapter usuarioAdapter;
    String nome;
    DatabaseReference firebaseRef;





    public UsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        firebaseRef =  ConfiguracaoFirebase.getFirebase();

        txtFragment = view.findViewById(R.id.txtFragment);



        Bundle bundle = getArguments();
        String nome = bundle.getString("nome");

        txtFragment.setText(nome);


        return view;
    }


}
