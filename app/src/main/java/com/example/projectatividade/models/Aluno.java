package com.example.projectatividade.models;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private List<Submissao> submissoes = new ArrayList<>();

    public List<Submissao> getSubmissoes() {
        return submissoes;
    }

    public void setSubmissoes(List<Submissao> submissoes) {
        this.submissoes = submissoes;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
