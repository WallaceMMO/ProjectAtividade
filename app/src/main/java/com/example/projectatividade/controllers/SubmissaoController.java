package com.example.projectatividade.controllers;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.AlunoAtividade;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.NotaCriterio;
import com.example.projectatividade.models.Submissao;
import com.example.projectatividade.persistence.AlunoAtividadeDao;
import com.example.projectatividade.persistence.CriterioDao;
import com.example.projectatividade.persistence.NotaCriterioDao;
import com.example.projectatividade.persistence.SubmissaoDao;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SubmissaoController {
    private SubmissaoDao submissaoDao;
    private NotaCriterioDao notaCriterioDao;
    private CriterioDao criterioDao;
    private AlunoAtividadeDao atividadeAlunoDao;

    public SubmissaoController(Context contexto) {
        submissaoDao = new SubmissaoDao(contexto);
        notaCriterioDao = new NotaCriterioDao(contexto);
        criterioDao = new CriterioDao(contexto);
    }

    public Submissao criarSubmissao(String conteudo, LocalDate dataEnvio, Atividade atividade, Aluno aluno) {
        Submissao s = new Submissao();
        s.setConteudo(conteudo);
        s.setDataEnvio(dataEnvio);
        s.setAtividade(atividade);
        s.setAluno(aluno);
        return submissaoDao.criar(s);
    }

    public Submissao atualizarSubmissao(Submissao s) {
        return submissaoDao.atualizar(s);
    }

    public boolean deletarSubmissao(Submissao s) {
        return submissaoDao.deletar(s);
    }

    public List<Submissao> listarSubmissoes() {
        return submissaoDao.listarTodos();
    }

    public Submissao buscarSubmissaoPorId(int id) {
        Submissao s = submissaoDao.buscarPorId(id);

        List<Criterio> list = criterioDao.listarPorAtividade(s.getAtividade().getIdentificador());
        s.getAtividade().setCriterios(list);

        List<NotaCriterio> listNotas = notaCriterioDao.listarPorSubmissao(s.getIdentificador());
        s.setNotasPorCriterio(listNotas);

        return s;
    }

    public void salvarNotas(Submissao submissao, List<NotaCriterio> notas) {
        submissao.getNotasPorCriterio().clear();

        for (NotaCriterio n : notas) {
            n.setSubmissao(submissao);
            notaCriterioDao.criar(n);
            submissao.getNotasPorCriterio().add(n);
        }
    }

    public NotaCriterio avaliarCriterio(Submissao s, Criterio c, double valor) {
        NotaCriterio nc = new NotaCriterio();
        nc.setSubmissao(s);
        nc.setCriterio(c);
        nc.setValor(valor);
        return notaCriterioDao.criar(nc);
    }

    public List<NotaCriterio> listarNotasCriterio() {
        return notaCriterioDao.listarTodos();
    }

    public double calcularPontuacaoFinal(Submissao submissao) {
        double soma = 0;
        double somaPeso = 0;
        for (NotaCriterio nc : submissao.getNotasPorCriterio()) {
            Criterio c = nc.getCriterio();
            soma += nc.getValor() * c.getPeso();
            somaPeso +=  c.getPeso();
        }
        return soma/somaPeso;
    }

    public Submissao buscarSubmissaoPorAtividadeAluno(AlunoAtividade atividadeAluno ) {
        Submissao s = submissaoDao.buscarPorIdAtividadeAluno(atividadeAluno.getAtividade().getIdentificador(), atividadeAluno.getAluno().getIdentificador());
        if(s == null) return null;

        List<NotaCriterio> listNotas = notaCriterioDao.listarPorSubmissao(s.getIdentificador());
        s.setNotasPorCriterio(listNotas);

        /*List<Criterio> list = criterioDao.listarPorAtividade(s.getAtividade().getIdentificador());

        for(NotaCriterio nc: listNotas) {
            for(Criterio c: list) {
                if(nc.getCriterio().getIdentificador() == c.getIdentificador()) {
                    nc
                }
            }
        }*/
        return s;
    }
}