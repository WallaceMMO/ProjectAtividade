package com.example.projectatividade.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Submissao {
    private int identificador;
    private String conteudo;
    private LocalDate dataEnvio;
    private Atividade atividade;
    private Aluno aluno;
    private List<NotaCriterio> notasPorCriterio = new ArrayList<>();

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<NotaCriterio> getNotasPorCriterio() {
        return notasPorCriterio;
    }

    public void setNotasPorCriterio(List<NotaCriterio> notasPorCriterio) {
        this.notasPorCriterio = notasPorCriterio;
    }

    @Override
    public String toString() {
        return "Submissao{" +
                "identificador=" + identificador +
                ", conteudo='" + conteudo + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", atividade=" + atividade +
                ", aluno=" + aluno +
                ", notasPorCriterio=" + notasPorCriterio +
                '}';
    }
}