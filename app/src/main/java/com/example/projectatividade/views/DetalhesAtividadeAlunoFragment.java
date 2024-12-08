package com.example.projectatividade.views;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.projectatividade.R;
import com.example.projectatividade.controllers.AtividadeController;
import com.example.projectatividade.controllers.SessaoUsuario;
import com.example.projectatividade.controllers.SubmissaoController;
import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.AlunoAtividade;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.NotaCriterio;
import com.example.projectatividade.models.Submissao;
import com.example.projectatividade.persistence.SubmissaoDao;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DetalhesAtividadeAlunoFragment extends Fragment {
    private static final String ARG_ID_ATIVIDADE = "id_atividade";
    private int idAtividade;

    private TextView lblTitulo, lblDescricao, lblDataEntrega, lblCriterios, lblNotas;
    private EditText txtConteudoSubmissao;
    private Button btnEnviarSubmissao;

    private AtividadeController atividadeController;
    private SubmissaoController submissaoController;
    private Atividade atividade;
    private Aluno alunoLogado;
    private Submissao submissaoAluno;

    public static DetalhesAtividadeAlunoFragment novaInstancia(int idAtividade) {
        DetalhesAtividadeAlunoFragment f = new DetalhesAtividadeAlunoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_ATIVIDADE, idAtividade);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idAtividade = getArguments().getInt(ARG_ID_ATIVIDADE, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View raiz = inflater.inflate(R.layout.fragment_detalhes_atividade_aluno, container, false);
        lblTitulo = raiz.findViewById(R.id.lblTituloAtivDetAluno);
        lblDescricao = raiz.findViewById(R.id.lblDescricaoAtivDetAluno);
        lblDataEntrega = raiz.findViewById(R.id.lblDataEntregaAtivDetAluno);
        lblCriterios = raiz.findViewById(R.id.lblCriteriosAtivDetAluno);
        lblNotas = raiz.findViewById(R.id.lblNotasAtivDetAluno);
        txtConteudoSubmissao = raiz.findViewById(R.id.txtConteudoSubmissaoAluno);
        btnEnviarSubmissao = raiz.findViewById(R.id.btnEnviarSubmissaoAluno);
        return raiz;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atividadeController = new AtividadeController(requireContext());
        submissaoController = new SubmissaoController(requireContext());
        alunoLogado = SessaoUsuario.getAlunoLogado();

        if (idAtividade == -1 || alunoLogado == null) {
            Toast.makeText(getContext(), "Dados inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        atividade = atividadeController.buscarAtividadePorId(idAtividade);
        if (atividade == null) {
            Toast.makeText(getContext(), "Atividade não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        lblTitulo.setText("Título: " + atividade.getTitulo());
        lblDescricao.setText("Descrição: " + atividade.getDescricao());
        lblDataEntrega.setText("Data Entrega: " + atividade.getDataEntrega());

        StringBuilder sbCriterios = new StringBuilder();
        for (Criterio c : atividade.getCriterios()) {
            sbCriterios.append(c.getNome()).append(" (Peso: ").append(c.getPeso()).append(")\n");
        }
        lblCriterios.setText("Critérios:\n" + (sbCriterios.length() > 0 ? sbCriterios.toString() : "Nenhum critério"));

        submissaoAluno = submissaoController.buscarSubmissaoPorAtividadeAluno(new AlunoAtividade(alunoLogado, atividade));

        if (submissaoAluno != null) {
            txtConteudoSubmissao.setText(submissaoAluno.getConteudo());
            txtConteudoSubmissao.setEnabled(false);

            btnEnviarSubmissao.setVisibility(View.GONE);

            if (!submissaoAluno.getNotasPorCriterio().isEmpty()) {
                StringBuilder sbNotas = new StringBuilder("Notas Obtidas:\n");
                double soma = 0;
                double somaPesos = 0;
                for (NotaCriterio nc : submissaoAluno.getNotasPorCriterio()) {
                    double notaCrit = nc.getValor() * nc.getCriterio().getPeso();
                    somaPesos += nc.getCriterio().getPeso();
                    sbNotas.append(nc.getCriterio().getNome())
                            .append(": ")
                            .append(nc.getValor())
                            .append(" (Peso: ")
                            .append(nc.getCriterio().getPeso())
                            .append(")\n");
                    soma += notaCrit;
                }
                sbNotas.append("Nota Final: ").append(soma/somaPesos);
                lblNotas.setText(sbNotas.toString());
            } else {
                lblNotas.setText("Ainda não avaliada");
            }
        } else {
            lblNotas.setText("Nenhuma submissão ainda.");
            btnEnviarSubmissao.setOnClickListener(v -> {
                String conteudo = txtConteudoSubmissao.getText().toString().trim();
                if (TextUtils.isEmpty(conteudo)) {
                    txtConteudoSubmissao.setError("Insira o conteúdo da submissão");
                    return;
                }

                Submissao nova = new Submissao();
                nova.setAtividade(atividade);
                nova.setAluno(alunoLogado);
                nova.setConteudo(conteudo);
                nova.setNotasPorCriterio(new ArrayList<>());

                nova = submissaoController.criarSubmissao(nova.getConteudo(), nova.getDataEnvio(), nova.getAtividade(), nova.getAluno()); // criando submissão

                atividade.getSubmissoes().add(nova);
                alunoLogado.getSubmissoes().add(nova);

                Toast.makeText(getContext(), "Submissão enviada com sucesso", Toast.LENGTH_SHORT).show();

                txtConteudoSubmissao.setEnabled(false);
                btnEnviarSubmissao.setVisibility(View.GONE);
                lblNotas.setText("Submissão realizada, aguardando avaliação.");

            });
        }
    }
}