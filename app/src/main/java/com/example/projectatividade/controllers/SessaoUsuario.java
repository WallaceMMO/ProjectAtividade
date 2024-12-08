package com.example.projectatividade.controllers;

import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.Professor;

public class SessaoUsuario {
    private static Professor professorLogado;
    private static Aluno alunoLogado;

    public static Professor getProfessorLogado() {
        return professorLogado;
    }

    public static void setProfessorLogado(Professor professor) {
        professorLogado = professor;
    }

    public static Aluno getAlunoLogado() {
        return alunoLogado;
    }

    public static void setAlunoLogado(Aluno aluno) {
        alunoLogado = aluno;
    }
}