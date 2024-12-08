package com.example.projectatividade.views;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectatividade.R;
import com.example.projectatividade.controllers.SubmissaoController;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Submissao;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SubmissaoAdapter extends RecyclerView.Adapter<SubmissaoAdapter.ViewHolder> {

    private List<Submissao> listaSubmissoes;
    private OnSubmissaoClickListener listener;
    private SubmissaoController submissaoController;

    public interface OnSubmissaoClickListener {
        void onSubmissaoClick(Submissao submissao);
    }

    public SubmissaoAdapter(List<Submissao> listaSubmissoes, OnSubmissaoClickListener listener, SubmissaoController submissaoController) {
        this.listaSubmissoes = listaSubmissoes;
        this.listener = listener;
        this.submissaoController = submissaoController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submissao, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Submissao s = listaSubmissoes.get(position);

        Log.d("Testando2", s == null ? "nada" : s.toString());
        holder.lblNomeAluno.setText("Aluno: " + s.getAluno().getNome());

        boolean entregue = s.getConteudo() != null && !s.getConteudo().isEmpty();
        holder.lblStatusEntrega.setText("Entregue: " + (entregue ? "Sim" : "Não"));

        Log.d("Testando4", String.valueOf(s.getNotasPorCriterio().isEmpty()));
        double notaFinal = s.getNotasPorCriterio().isEmpty() ? 0.0 : submissaoController.calcularPontuacaoFinal(s);
        holder.lblNotaFinal.setText("Nota Final: " + (notaFinal > 0.0 ? notaFinal : "N/A"));

        holder.itemView.setOnClickListener(v -> {
            if (entregue) {
                listener.onSubmissaoClick(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSubmissoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblNomeAluno, lblStatusEntrega, lblNotaFinal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNomeAluno = itemView.findViewById(R.id.lblNomeAlunoSub);
            lblStatusEntrega = itemView.findViewById(R.id.lblStatusEntregaSub);
            lblNotaFinal = itemView.findViewById(R.id.lblNotaFinalSub);
        }
    }
}