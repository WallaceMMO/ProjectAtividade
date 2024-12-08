package com.example.projectatividade.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.projectatividade.R;
import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.persistence.AlunoDao;
import com.example.projectatividade.persistence.ICRUDDao;

public class CadastrarAlunoFragment extends Fragment {
    private EditText txtNome;
    private Button btnSalvar;
    private ICRUDDao<Aluno> alunoDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alunoDao = new AlunoDao(getContext());
        View raiz = inflater.inflate(R.layout.fragment_cadastrar_aluno, container, false);
        txtNome = raiz.findViewById(R.id.txtNome);
        btnSalvar = raiz.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno a = new Aluno();
                a.setNome(txtNome.getText().toString());
                alunoDao.criar(a);
                txtNome.setText("");
            }
        });
        return raiz;
    }
}