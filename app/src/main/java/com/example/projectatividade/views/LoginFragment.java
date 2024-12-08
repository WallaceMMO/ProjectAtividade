package com.example.projectatividade.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectatividade.R;
import com.example.projectatividade.controllers.SessaoUsuario;
import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.Professor;
import com.example.projectatividade.persistence.AlunoDao;
import com.example.projectatividade.persistence.ICRUDDao;
import com.example.projectatividade.persistence.ProfessorDao;

import java.util.List;

public class LoginFragment extends Fragment {
    private EditText txtNome;
    private RadioGroup radioGroupTipo;
    private RadioButton radioProfessor, radioAluno;
    private Button btnLogin;
    private ICRUDDao<Professor> professorDao;
    private ICRUDDao<Aluno> alunoDao;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        professorDao = new ProfessorDao(requireContext());
        alunoDao = new AlunoDao(requireContext());

        btnLogin.setOnClickListener(v -> {
            String nome = txtNome.getText().toString();
            if (radioProfessor.isChecked()) {
                List<Professor> listaProf = professorDao.listarTodos();
                boolean encontrado = false;
                for (Professor p : listaProf) {
                    if (p.getNome().equalsIgnoreCase(nome)) {
                        SessaoUsuario.setProfessorLogado(p);
                        encontrado = true;
                        Intent intent = new Intent(getActivity(), ProfessorMainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                if(!encontrado) {
                    Toast.makeText(getContext(), "Professor não encontrado", Toast.LENGTH_SHORT).show();
                }
            } else if (radioAluno.isChecked()) {
                List<Aluno> listaAluno = alunoDao.listarTodos();
                boolean encontrado = false;
                for (Aluno a : listaAluno) {
                    if (a.getNome().equalsIgnoreCase(nome)) {
                        SessaoUsuario.setAlunoLogado(a);
                        encontrado = true;
                        Intent intent = new Intent(getActivity(), AlunoMainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                if(!encontrado) {
                    Toast.makeText(getContext(), "Aluno não encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                txtNome.setError("Selecione um tipo de usuário");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View raiz = inflater.inflate(R.layout.fragment_login, container, false);
        txtNome = raiz.findViewById(R.id.txtNome);
        radioGroupTipo = raiz.findViewById(R.id.radioGroupTipo);
        radioProfessor = raiz.findViewById(R.id.radioProfessor);
        radioAluno = raiz.findViewById(R.id.radioAluno);
        btnLogin = raiz.findViewById(R.id.btnLogin);

        return raiz;
    }
}