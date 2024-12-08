package com.example.projectatividade.controllers;

import android.content.Context;

import com.example.projectatividade.models.Professor;
import com.example.projectatividade.persistence.ProfessorDao;

import java.util.List;

public class ProfessorController {
    private ProfessorDao professorDao;

    public ProfessorController(Context contexto) {
        professorDao = new ProfessorDao(contexto);
    }

    public Professor criarProfessor(String nome) {
        Professor p = new Professor();
        p.setNome(nome);
        return professorDao.criar(p);
    }

    public Professor atualizarProfessor(Professor p) {
        return professorDao.atualizar(p);
    }

    public boolean deletarProfessor(Professor p) {
        return professorDao.deletar(p);
    }

    public List<Professor> listarProfessores() {
        return professorDao.listarTodos();
    }

    public Professor buscarProfessorPorId(int id) {
        return professorDao.buscarPorId(id);
    }

    public Professor loginProfessor(String nome) {
        List<Professor> lista = listarProfessores();
        for (Professor p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }
}