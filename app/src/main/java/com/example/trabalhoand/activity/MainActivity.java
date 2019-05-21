package com.example.trabalhoand.activity;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.trabalhoand.R;
import com.example.trabalhoand.adapter.UsuarioAdapter;
import com.example.trabalhoand.config.ConfiguracaoFirebase;
import com.example.trabalhoand.config.RecyclerItemClickListener;
import com.example.trabalhoand.fragment.UsuarioFragment;
import com.example.trabalhoand.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerUsuarios;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios = new ArrayList<>();
    private DatabaseReference firebaseRef;

    private UsuarioFragment usuarioFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseRef =  ConfiguracaoFirebase.getFirebase();
        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);



        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerUsuarios.setHasFixedSize(true);
        usuarioAdapter = new UsuarioAdapter(usuarios);
        recyclerUsuarios.setAdapter(usuarioAdapter);

        recyclerUsuarios.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerUsuarios,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Usuario usuario = usuarios.get(position);
                                UsuarioFragment usuarioFragment = new UsuarioFragment();

                                Bundle bundle = new Bundle();
                                bundle.putString("nome", usuario.getNome());
                                usuarioFragment.setArguments(bundle);



                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.usuarioFrame, usuarioFragment);
                                transaction.commit();


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );



        recuperaUsuario();


        swipe();


    }


    public void recuperaUsuario(){

        final DatabaseReference usuarioRef = firebaseRef
                .child("usuario");


        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuarios.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //String chave = dataSnapshot.getKey();
                    //usuarioRef.child(chave).removeValue();
                    usuarios.add(ds.getValue(Usuario.class));

                }

                usuarioAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });



    }

    public void swipe(){
        final ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);



            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                int position = viewHolder.getAdapterPosition();
                removeUsuario(position);
                usuarios.remove(position);
                usuarioAdapter.notifyItemRemoved(position);
            }

        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView( recyclerUsuarios );
    }

    public void removeUsuario(int position){

        DatabaseReference usuarioRef = firebaseRef;
        usuarioRef.child("usuario")
                .child(usuarios.get(position).getIdUsuario())
                .setValue(null);
    }
}