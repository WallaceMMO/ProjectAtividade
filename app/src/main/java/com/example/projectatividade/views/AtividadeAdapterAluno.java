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

public class AtividadeAdapterAluno extends RecyclerView.Adapter<AtividadeAdapterAluno.ViewHolder> {

    private List<Atividade> listaAtividades;
    private OnAtividadeAlunoClickListener listener;

    public interface OnAtividadeAlunoClickListener {
        void onAtividadeAlunoClick(Atividade atividade);
    }

    public AtividadeAdapterAluno(List<Atividade> listaAtividades, OnAtividadeAlunoClickListener listener) {
        this.listaAtividades = listaAtividades;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AtividadeAdapterAluno.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atividade_aluno, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeAdapterAluno.ViewHolder holder, int position) {
        Atividade atividade = listaAtividades.get(position);
        holder.lblTituloAtividade.setText(atividade.getTitulo());
        holder.itemView.setOnClickListener(view -> listener.onAtividadeAlunoClick(atividade));
    }

    @Override
    public int getItemCount() {
        return listaAtividades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblTituloAtividade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTituloAtividade = itemView.findViewById(R.id.lblTituloAtividadeAlunoItem);
        }
    }
}