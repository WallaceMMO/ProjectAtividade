package com.example.projectatividade.models;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Atividade {
    private int identificador;
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private Professor professorCriador;
    private List<Criterio> criterios = new ArrayList<>();
    private List<Submissao> submissoes = new ArrayList<>();
    private List<Aluno> alunosAssociados = new ArrayList<>();

    public Atividade(){}
    public Atividade(int identificador, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataEntrega) {
        this.identificador = identificador;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataEntrega = dataEntrega;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Atividade b = (Atividade)obj;
        return this.identificador == b.getIdentificador();
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Professor getProfessorCriador() {
        return professorCriador;
    }

    public void setProfessorCriador(Professor professorCriador) {
        this.professorCriador = professorCriador;
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<Criterio> criterios) {
        this.criterios = criterios;
    }

    public List<Submissao> getSubmissoes() {
        return submissoes;
    }

    public void setSubmissoes(List<Submissao> submissoes) {
        this.submissoes = submissoes;
    }

    public List<Aluno> getAlunosAssociados() {
        return alunosAssociados;
    }

    public void setAlunosAssociados(List<Aluno> alunosAssociados) {
        this.alunosAssociados = alunosAssociados;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "identificador=" + identificador +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataEntrega=" + dataEntrega +
                ", professorCriador=" + professorCriador +
                ", criterios=" + criterios +
                ", submissoes=" + submissoes +
                ", alunosAssociados=" + alunosAssociados +
                '}';
    }
}