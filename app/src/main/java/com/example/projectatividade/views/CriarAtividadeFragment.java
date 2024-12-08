package com.example.projectatividade.views;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.projectatividade.controllers.SessaoUsuario;
import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.persistence.AlunoDao;
import com.example.projectatividade.persistence.ICRUDDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CriarAtividadeFragment extends Fragment {
    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtCriterioNome;
    private EditText txtCriterioPeso;
    private EditText txtDataCriacao;
    private EditText txtDataEntrega;
    private Button btnAdicionarCriterio;
    private Button btnSalvarAtividade;

    private LinearLayout layoutAlunos;
    private LinearLayout layoutCriteriosAdicionados;

    private List<Aluno> todosAlunos;
    private List<Aluno> alunosSelecionados = new ArrayList<>();
    private List<Criterio> criterios = new ArrayList<>();

    private AtividadeController atividadeController;
    private ICRUDDao<Aluno> alunoDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View raiz = inflater.inflate(R.layout.fragment_criar_atividade, container, false);

        txtTitulo = raiz.findViewById(R.id.txtTitulo);
        txtDescricao = raiz.findViewById(R.id.txtDescricao);
        txtDataCriacao = raiz.findViewById(R.id.txtDataCriacao);
        txtDataEntrega = raiz.findViewById(R.id.txtDataEntrega);
        txtCriterioNome = raiz.findViewById(R.id.txtCriterioNome);
        txtCriterioPeso = raiz.findViewById(R.id.txtCriterioPeso);

        btnAdicionarCriterio = raiz.findViewById(R.id.btnAdicionarCriterio);
        btnSalvarAtividade = raiz.findViewById(R.id.btnSalvarAtividade);
        layoutAlunos = raiz.findViewById(R.id.layoutAlunos);
        layoutCriteriosAdicionados = raiz.findViewById(R.id.layoutCriteriosAdicionados);

        return raiz;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atividadeController = new AtividadeController(requireContext());
        alunoDao = new AlunoDao(requireContext());

        LocalDate hoje = LocalDate.now();
        txtDataCriacao.setText(hoje.toString());

        todosAlunos = alunoDao.listarTodos();
        for (Aluno a : todosAlunos) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(a.getNome());
            cb.setTag(a);
            layoutAlunos.addView(cb);
        }

        txtDataEntrega.setOnClickListener(v -> mostrarDatePicker(txtDataEntrega));

        btnAdicionarCriterio.setOnClickListener(view1 -> {
            String nome = txtCriterioNome.getText().toString().trim();
            String pesoStr = txtCriterioPeso.getText().toString().trim();
            if (!nome.isEmpty() && !pesoStr.isEmpty()) {
                try {
                    double peso = Double.parseDouble(pesoStr);
                    Criterio c = new Criterio();
                    c.setNome(nome);
                    c.setPeso(peso);
                    criterios.add(c);
                    txtCriterioNome.setText("");
                    txtCriterioPeso.setText("");

                    TextView txtCrit = new TextView(getContext());
                    txtCrit.setText(c.getNome() + " (Peso: " + c.getPeso() + ")");
                    layoutCriteriosAdicionados.addView(txtCrit);

                    Toast.makeText(getContext(), "Critério adicionado", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Peso inválido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Preencha todos os campos do critério", Toast.LENGTH_SHORT).show();
            }
        });

        btnSalvarAtividade.setOnClickListener(view12 -> {
            String titulo = txtTitulo.getText().toString().trim();
            String descricao = txtDescricao.getText().toString().trim();
            String dataCriacaoStr = txtDataCriacao.getText().toString().trim();
            String dataEntregaStr = txtDataEntrega.getText().toString().trim();

            if (titulo.isEmpty() || descricao.isEmpty() || dataCriacaoStr.isEmpty() || dataEntregaStr.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os campos da atividade", Toast.LENGTH_SHORT).show();
                return;
            }

            LocalDate dataC = LocalDate.now();
            LocalDate dataE;
            try {
                dataE = LocalDate.parse(dataEntregaStr);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Formato de data de entrega inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            alunosSelecionados.clear();
            for (int i = 0; i < layoutAlunos.getChildCount(); i++) {
                View vAl = layoutAlunos.getChildAt(i);
                if (vAl instanceof CheckBox) {
                    CheckBox cb = (CheckBox) vAl;
                    if (cb.isChecked()) {
                        Aluno al = (Aluno) cb.getTag();
                        alunosSelecionados.add(al);
                    }
                }
            }

            if (alunosSelecionados.isEmpty()) {
                Toast.makeText(getContext(), "Selecione pelo menos um aluno", Toast.LENGTH_SHORT).show();
                return;
            }

            if (criterios.isEmpty()) {
                Toast.makeText(getContext(), "Adicione pelo menos um critério", Toast.LENGTH_SHORT).show();
                return;
            }

            Atividade atividade = new Atividade();
            atividade.setTitulo(titulo);
            atividade.setDescricao(descricao);
            atividade.setDataCriacao(dataC);
            atividade.setDataEntrega(dataE);

            atividadeController.criarAtividade(atividade, criterios, alunosSelecionados);
            Toast.makeText(getContext(), "Atividade criada com sucesso", Toast.LENGTH_SHORT).show();

            txtTitulo.setText("");
            txtDescricao.setText("");
            txtDataCriacao.setText(LocalDate.now().toString());
            txtDataEntrega.setText("");
            criterios.clear();
            layoutCriteriosAdicionados.removeAllViews();
            for (int i = 0; i < layoutAlunos.getChildCount(); i++) {
                View vAl = layoutAlunos.getChildAt(i);
                if (vAl instanceof CheckBox) {
                    ((CheckBox) vAl).setChecked(false);
                }
            }
        });
    }

    private void mostrarDatePicker(EditText campoData) {
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (datePicker, anoSel, mesSel, diaSel) -> {
                    LocalDate data = LocalDate.of(anoSel, mesSel+1, diaSel);
                    campoData.setText(data.toString());
                }, ano, mes, dia);
        datePickerDialog.show();
    }
}