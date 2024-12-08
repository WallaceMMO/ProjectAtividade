package com.example.projectatividade.controllers;

import android.content.Context;

import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.persistence.AlunoDao;

import java.util.List;

public class AlunoController {
    private AlunoDao alunoDao;

    public AlunoController(Context contexto) {
        alunoDao = new AlunoDao(contexto);
    }

    public Aluno criarAluno(String nome) {
        Aluno a = new Aluno();
        a.setNome(nome);
        return alunoDao.criar(a);
    }

    public Aluno atualizarAluno(Aluno a) {
        return alunoDao.atualizar(a);
    }

    public boolean deletarAluno(Aluno a) {
        return alunoDao.deletar(a);
    }

    public List<Aluno> listarAlunos() {
        return alunoDao.listarTodos();
    }

    public Aluno buscarAlunoPorId(int id) {
        return alunoDao.buscarPorId(id);
    }

    public Aluno loginAluno(String nome) {
        List<Aluno> lista = listarAlunos();
        for (Aluno a : lista) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }
}