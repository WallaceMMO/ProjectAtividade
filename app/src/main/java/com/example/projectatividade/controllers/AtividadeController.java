package com.example.projectatividade.controllers;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.Professor;
import com.example.projectatividade.persistence.AlunoAtividadeDao;
import com.example.projectatividade.persistence.AtividadeDao;
import com.example.projectatividade.persistence.CriterioDao;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AtividadeController {
    private AtividadeDao atividadeDao;
    private CriterioDao criterioDao;
    private AlunoAtividadeDao atividadeAlunoDao;

    public AtividadeController(Context context) {
        this.atividadeDao = new AtividadeDao(context);
        this.criterioDao = new CriterioDao(context);
        this.atividadeAlunoDao = new AlunoAtividadeDao(context);
    }

    public Atividade criarAtividade(Atividade atividade, List<Criterio> criterios, List<Aluno> alunos) {
        Professor professor = SessaoUsuario.getProfessorLogado();
        atividade.setProfessorCriador(professor);
        atividade.setAlunosAssociados(alunos);
        Atividade nova = atividadeDao.criar(atividade);

        for (Criterio c : criterios) {
            c.setAtividade(nova);
            criterioDao.criar(c);
            nova.getCriterios().add(c);
        }

        for(Aluno a: alunos) {
            atividadeAlunoDao.associarAlunoAtividade(atividade.getIdentificador(), a.getIdentificador());
        }

        professor.getAtividadesCriadas().add(nova);

        return nova;
    }

    public List<Atividade> listarAtividadesProfessor(Professor professor) {
        return atividadeDao.listarPorProfessor(professor.getIdentificador());
    }

    public Atividade buscarAtividadePorId(int id) {
        Atividade atividade = atividadeDao.buscarPorId(id);
        if (atividade != null) {
            List<Criterio> criterios = atividadeAlunoDao.listarCriteriosPorAtividade(id);
            atividade.setCriterios(criterios);

            List<Aluno> alunos = atividadeAlunoDao.listarAlunosPorAtividade(id);
            atividade.setAlunosAssociados(alunos);
            Log.d("atividade.setAlunosAssociados(alunos);", String.valueOf(alunos.size()));
            return atividade;
        }
        return null;
    }

    public List<Atividade> listarAtividadesAluno(Aluno aluno) {
        return atividadeAlunoDao.listarAtividadesPorAluno(aluno.getIdentificador());
    }

    public Atividade atualizarAtividade(Atividade atividade) {
        return atividadeDao.atualizar(atividade);
    }
}