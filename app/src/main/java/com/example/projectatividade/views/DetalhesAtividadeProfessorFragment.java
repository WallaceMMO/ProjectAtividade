package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectatividade.R;
import com.example.projectatividade.controllers.AtividadeController;
import com.example.projectatividade.controllers.SubmissaoController;
import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.AlunoAtividade;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.Submissao;
import com.example.projectatividade.persistence.AtividadeDao;
import com.example.projectatividade.persistence.ICRUDDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DetalhesAtividadeProfessorFragment extends Fragment implements SubmissaoAdapter.OnSubmissaoClickListener {

    private static final String ARG_ID_ATIVIDADE = "id_atividade";
    private int idAtividade;

    private TextView txtTituloAtivDet;
    private TextView txtDescricaoAtivDet;
    private TextView txtDataCriacaoAtivDet;
    private TextView txtDataEntregaAtivDet;
    private RecyclerView recyclerCriteriosAtivDet;
    private RecyclerView recyclerSubmissoesAtivDet;

    private AtividadeController atividadeController;
    private SubmissaoController submissaoController;
    private Atividade atividade;

    private CriterioAdapter criterioAdapter;
    private SubmissaoAdapter submissaoAdapter;

    public DetalhesAtividadeProfessorFragment() {

    }

    public static DetalhesAtividadeProfessorFragment novaInstancia(int idAtividade) {
        DetalhesAtividadeProfessorFragment fragment = new DetalhesAtividadeProfessorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_ATIVIDADE, idAtividade);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idAtividade = getArguments().getInt(ARG_ID_ATIVIDADE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View raiz = inflater.inflate(R.layout.fragment_detalhes_atividade_professor, container, false);
        txtTituloAtivDet = raiz.findViewById(R.id.txtTituloAtivDet);
        txtDescricaoAtivDet = raiz.findViewById(R.id.txtDescricaoAtivDet);
        txtDataCriacaoAtivDet = raiz.findViewById(R.id.txtDataCriacaoAtivDet);
        txtDataEntregaAtivDet = raiz.findViewById(R.id.txtDataEntregaAtivDet);
        recyclerCriteriosAtivDet = raiz.findViewById(R.id.recyclerCriteriosAtivDet);
        recyclerSubmissoesAtivDet = raiz.findViewById(R.id.recyclerSubmissoesAtivDet);
        return raiz;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atividadeController = new AtividadeController(requireContext());
        submissaoController = new SubmissaoController(requireContext());

        atividade = atividadeController.buscarAtividadePorId(idAtividade);
        if (atividade == null) {
            Toast.makeText(getContext(), "Atividade não encontrada.", Toast.LENGTH_SHORT).show();
            return;
        }

        txtTituloAtivDet.setText(atividade.getTitulo());
        txtDescricaoAtivDet.setText(atividade.getDescricao());
        txtDataCriacaoAtivDet.setText(atividade.getDataCriacao().toString());
        txtDataEntregaAtivDet.setText(atividade.getDataEntrega().toString());

        recyclerCriteriosAtivDet.setLayoutManager(new LinearLayoutManager(getContext()));
        criterioAdapter = new CriterioAdapter(atividade.getCriterios());
        recyclerCriteriosAtivDet.setAdapter(criterioAdapter);

        recyclerSubmissoesAtivDet.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Submissao> listaSubmissoes = prepararListaSubmissoes(atividade);

        Collections.sort(listaSubmissoes, new Comparator<Submissao>() {
            @Override
            public int compare(Submissao s1, Submissao s2) {
                if(s2.getConteudo().isEmpty()) return -1;
                if(s1.getConteudo().isEmpty()) return 1;

                double nota1 = submissaoController.calcularPontuacaoFinal(s1);
                double nota2 = submissaoController.calcularPontuacaoFinal(s2);
                return Double.compare(nota2, nota1);
            }
        });

        submissaoAdapter = new SubmissaoAdapter(listaSubmissoes, this, submissaoController);
        recyclerSubmissoesAtivDet.setAdapter(submissaoAdapter);
    }

    private List<Submissao> prepararListaSubmissoes(Atividade atividade) {
        List<Submissao> listaSubmissoes = new ArrayList<>();

        for (Aluno aluno : atividade.getAlunosAssociados()) {
            Submissao submissaoEncontrada = submissaoController.buscarSubmissaoPorAtividadeAluno(new AlunoAtividade(aluno, atividade));

            if (submissaoEncontrada == null) {
                submissaoEncontrada = new Submissao();
                submissaoEncontrada.setConteudo("");
                submissaoEncontrada.setNotasPorCriterio(new ArrayList<>());
            }
            submissaoEncontrada.setAluno(aluno);
            submissaoEncontrada.setAtividade(atividade);

            listaSubmissoes.add(submissaoEncontrada);
        }

        return listaSubmissoes;
    }

    @Override
    public void onSubmissaoClick(Submissao submissao) {
        if (submissao.getConteudo() != null && !submissao.getConteudo().isEmpty()) {
            AvaliarSubmissaoFragment f = AvaliarSubmissaoFragment.novaInstancia(submissao.getIdentificador());
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container_professor, f)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Esta submissão não foi entregue", Toast.LENGTH_SHORT).show();
        }
    }
}