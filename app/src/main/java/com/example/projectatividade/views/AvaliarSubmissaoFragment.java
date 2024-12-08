package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.projectatividade.R;
import com.example.projectatividade.controllers.SubmissaoController;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.NotaCriterio;
import com.example.projectatividade.models.Submissao;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AvaliarSubmissaoFragment extends Fragment {
    private static final String ARG_ID_SUBMISSAO = "id_submissao";
    private int idSubmissao;

    private TextView lblConteudoSub;
    private LinearLayout layoutCriterios;
    private Button btnSalvarNotas;

    private SubmissaoController submissaoController;
    private Submissao submissao;
    private List<EditText> listaNotasEdits = new ArrayList<>();
    private List<Criterio> criterios = new ArrayList<>();

    private List<Criterio> criteriosEditaveis = new ArrayList<>();

    public static AvaliarSubmissaoFragment novaInstancia(int idSubmissao) {
        AvaliarSubmissaoFragment f = new AvaliarSubmissaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_SUBMISSAO, idSubmissao);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idSubmissao = getArguments().getInt(ARG_ID_SUBMISSAO, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View raiz = inflater.inflate(R.layout.fragment_avaliar_submissao, container, false);
        lblConteudoSub = raiz.findViewById(R.id.lblConteudoSub);
        layoutCriterios = raiz.findViewById(R.id.layoutCriterios);
        btnSalvarNotas = raiz.findViewById(R.id.btnSalvarNotas);
        return raiz;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submissaoController = new SubmissaoController(requireContext());

        if (idSubmissao == -1) {
            Toast.makeText(getContext(), "Submissão inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        submissao = submissaoController.buscarSubmissaoPorId(idSubmissao);
        if (submissao == null) {
            Toast.makeText(getContext(), "Submissão não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        if (submissao.getConteudo() == null || submissao.getConteudo().isEmpty()) {
            Toast.makeText(getContext(), "Esta submissão não possui conteúdo", Toast.LENGTH_SHORT).show();
            return;
        }

        lblConteudoSub.setText("Conteúdo da Submissão:\n" + submissao.getConteudo());

        Atividade atividade = submissao.getAtividade();
        if (atividade == null) {
            Toast.makeText(getContext(), "Atividade não encontrada para esta submissão", Toast.LENGTH_SHORT).show();
            return;
        }

        criterios = atividade.getCriterios();
        if (criterios.isEmpty()) {
            Toast.makeText(getContext(), "Não há critérios definidos para esta atividade", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Criterio c : criterios) {
            TextView lbl = new TextView(getContext());
            lbl.setText(c.getNome() + " (Peso: " + c.getPeso() + ")");
            layoutCriterios.addView(lbl);

            EditText txtNota = new EditText(getContext());
            txtNota.setHint("Nota para " + c.getNome());
            txtNota.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            NotaCriterio notaExistente = null;
            for (NotaCriterio nc : submissao.getNotasPorCriterio()) {
                if (nc.getCriterio().getIdentificador() == c.getIdentificador()) {
                    notaExistente = nc;
                    break;
                }
            }

            if (notaExistente != null) {
                txtNota.setText(String.valueOf(notaExistente.getValor()));
                txtNota.setEnabled(false);
            } else {
                criteriosEditaveis.add(c);
            }

            layoutCriterios.addView(txtNota);
            listaNotasEdits.add(txtNota);
        }

        btnSalvarNotas.setOnClickListener(v -> {
            List<NotaCriterio> novasNotas = new ArrayList<>();

            for (int i = 0; i < criterios.size(); i++) {
                Criterio crit = criterios.get(i);
                EditText campoNota = listaNotasEdits.get(i);

                if (!campoNota.isEnabled()) {
                    continue;
                }

                String notaStr = campoNota.getText().toString().trim();
                double valor = 0;
                if (!notaStr.isEmpty()) {
                    try {
                        valor = Double.parseDouble(notaStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Nota inválida para o critério " + crit.getNome(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                NotaCriterio nc = new NotaCriterio();
                nc.setCriterio(crit);
                nc.setValor(valor);
                novasNotas.add(nc);
            }

            if (!novasNotas.isEmpty()) {
                List<NotaCriterio> todasNotas = new ArrayList<>(submissao.getNotasPorCriterio());
                todasNotas.addAll(novasNotas);
                submissaoController.salvarNotas(submissao, todasNotas);
                Toast.makeText(getContext(), "Notas salvas com sucesso", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Não há novas notas para salvar", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            }

        });
    }
}