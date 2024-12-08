package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListarAtividadesAlunoFragment extends Fragment implements AtividadeAdapterAluno.OnAtividadeAlunoClickListener {

    private RecyclerView recyclerView;
    private AtividadeController atividadeController;
    private Aluno alunoLogado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View raiz = inflater.inflate(R.layout.fragment_listar_atividades_aluno, container, false);
        recyclerView = raiz.findViewById(R.id.recyclerAtividadesAluno);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return raiz;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atividadeController = new AtividadeController(requireContext());
        alunoLogado = SessaoUsuario.getAlunoLogado();

        if (alunoLogado == null) {
            Toast.makeText(getContext(), "Aluno não está logado.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Atividade> atividades = atividadeController.listarAtividadesAluno(alunoLogado);

        if (atividades.isEmpty()) {
            Toast.makeText(getContext(), "Nenhuma atividade encontrada.", Toast.LENGTH_SHORT).show();
        }

        AtividadeAdapterAluno adapter = new AtividadeAdapterAluno(atividades, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAtividadeAlunoClick(Atividade atividade) {
        DetalhesAtividadeAlunoFragment fragment = DetalhesAtividadeAlunoFragment.novaInstancia(atividade.getIdentificador());
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_aluno, fragment)
                .addToBackStack(null)
                .commit();
    }
}