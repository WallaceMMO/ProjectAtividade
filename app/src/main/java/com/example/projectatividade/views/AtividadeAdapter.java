package com.example.projectatividade.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectatividade.R;
import com.example.projectatividade.models.Atividade;

import java.util.List;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder> {

    private List<Atividade> listaAtividades;
    private OnAtividadeClickListener listener;

    public interface OnAtividadeClickListener {
        void onAtividadeClick(Atividade atividade);
    }

    public AtividadeAdapter(List<Atividade> listaAtividades, OnAtividadeClickListener listener) {
        this.listaAtividades = listaAtividades;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atividade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeAdapter.ViewHolder holder, int position) {
        Atividade atividade = listaAtividades.get(position);
        holder.lblTituloAtividade.setText(atividade.getTitulo());
        holder.lblDescricaoAtividade.setText(atividade.getDescricao());

        holder.itemView.setOnClickListener(v -> listener.onAtividadeClick(atividade));
    }

    @Override
    public int getItemCount() {
        return listaAtividades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblTituloAtividade;
        TextView lblDescricaoAtividade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTituloAtividade = itemView.findViewById(R.id.lblTituloAtividadeItem);
            lblDescricaoAtividade = itemView.findViewById(R.id.lblDescricaoAtividadeItem);
        }
    }
}