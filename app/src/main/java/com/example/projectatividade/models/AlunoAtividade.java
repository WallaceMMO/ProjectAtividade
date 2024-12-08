package com.example.projectatividade.models;

public class AlunoAtividade {
    Aluno aluno;
    Atividade atividade;

    public AlunoAtividade(Aluno aluno, Atividade atividade) {
        this.aluno = aluno;
        this.atividade = atividade;
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

    @Override
    public String toString() {
        return "AlunoAtividade{" +
                "aluno=" + aluno +
                ", Atividade=" + atividade +
                '}';
    }
}
