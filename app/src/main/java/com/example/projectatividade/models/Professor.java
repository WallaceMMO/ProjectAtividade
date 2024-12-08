package com.example.projectatividade.models;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private List<Atividade> atividadesCriadas = new ArrayList<>();

    public List<Atividade> getAtividadesCriadas() {
        return atividadesCriadas;
    }

    public void setAtividadesCriadas(List<Atividade> atividadesCriadas) {
        this.atividadesCriadas = atividadesCriadas;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "identificador=" + getIdentificador() +
                ", nome='" + getNome() + '\'' +
                ", atividadesCriadas=" + atividadesCriadas +
                '}';
    }
}