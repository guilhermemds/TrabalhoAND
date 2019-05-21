package com.example.trabalhoand.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trabalhoand.R;
import com.example.trabalhoand.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.MyViewHolder> {

    private List<Usuario> usuarios;



    public UsuarioAdapter(List<Usuario> usuarios){
        this.usuarios = usuarios;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_usuario, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final Usuario usuario = usuarios.get(i);

        holder.nome.setText(usuario.getNome());
        holder.cep.setText(usuario.getCep());
        holder.bairro.setText(usuario.getBairro());
        holder.rua.setText(usuario.getLogradouro());
        holder.localidade.setText(usuario.getLocalidade());

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView cep;
        TextView bairro;
        TextView rua;
        TextView localidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomeFgt);
            cep = itemView.findViewById(R.id.txtCep);
            bairro = itemView.findViewById(R.id.txtBairro);
            rua = itemView.findViewById(R.id.txtRua);
            localidade = itemView.findViewById(R.id.txtLocalidade);


        }
    }
}
