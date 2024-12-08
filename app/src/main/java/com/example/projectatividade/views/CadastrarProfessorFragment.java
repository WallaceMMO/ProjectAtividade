package com.example.projectatividade.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.projectatividade.R;
import com.example.projectatividade.models.Professor;
import com.example.projectatividade.persistence.ICRUDDao;
import com.example.projectatividade.persistence.ProfessorDao;

public class CadastrarProfessorFragment extends Fragment {
    private EditText txtNome;
    private Button btnSalvar;
    private ICRUDDao<Professor> professorDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        professorDao = new ProfessorDao(getContext());
        View raiz = inflater.inflate(R.layout.fragment_cadastrar_professor, container, false);
        txtNome = raiz.findViewById(R.id.txtNome);
        btnSalvar = raiz.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> {
            Professor p = new Professor();
            p.setNome(txtNome.getText().toString());
            professorDao.criar(p);
            txtNome.setText("");
        });
        return raiz;
    }
}