package com.example.projectatividade.models;

public class Criterio {
    private int identificador;
    private String nome;
    private double peso;
    private Atividade atividade;

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    @Override
    public String toString() {
        return "Criterio{" +
                "identificador=" + identificador +
                ", nome='" + nome + '\'' +
                ", peso=" + peso +
                ", atividade=" + atividade +
                '}';
    }
}