package com.example.projectatividade.models;

public class NotaCriterio {
    private int identificador;
    private double valor;
    private Submissao submissao;
    private Criterio criterio;

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Submissao getSubmissao() {
        return submissao;
    }

    public void setSubmissao(Submissao submissao) {
        this.submissao = submissao;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    @Override
    public String toString() {
        return "NotaCriterio{" +
                "identificador=" + identificador +
                ", valor=" + valor +
                ", submissao=" + submissao +
                ", criterio=" + criterio +
                '}';
    }
}