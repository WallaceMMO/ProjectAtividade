package com.example.projectatividade.models;

public abstract class Usuario {
    private int identificador;
    private String nome;

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

    @Override
    public String toString() {
        return "Usuario{" +
                "identificador=" + identificador +
                ", nome='" + nome + '\'' +
                '}';
    }
}