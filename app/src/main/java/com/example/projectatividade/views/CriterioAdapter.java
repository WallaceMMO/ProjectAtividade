package com.example.projectatividade.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectatividade.R;
import com.example.projectatividade.models.Criterio;

import java.util.List;

public class CriterioAdapter extends RecyclerView.Adapter<CriterioAdapter.ViewHolder> {

    private List<Criterio> listaCriterios;

    public CriterioAdapter(List<Criterio> listaCriterios) {
        this.listaCriterios = listaCriterios;
    }

    @NonNull
    @Override
    public CriterioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_criterio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CriterioAdapter.ViewHolder holder, int position) {
        Criterio c = listaCriterios.get(position);
        holder.lblNomeCriterio.setText(c.getNome());
        holder.lblPesoCriterio.setText("Peso: " + c.getPeso());
    }

    @Override
    public int getItemCount() {
        return listaCriterios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblNomeCriterio, lblPesoCriterio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNomeCriterio = itemView.findViewById(R.id.lblNomeCriterio);
            lblPesoCriterio = itemView.findViewById(R.id.lblPesoCriterio);
        }
    }
}