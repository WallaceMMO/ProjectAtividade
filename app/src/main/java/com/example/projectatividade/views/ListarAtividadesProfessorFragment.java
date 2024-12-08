package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
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
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Professor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListarAtividadesProfessorFragment extends Fragment implements AtividadeAdapter.OnAtividadeClickListener {

    private RecyclerView recyclerView;
    private AtividadeController atividadeController;
    private AtividadeAdapter adapter;
    private Professor professorLogado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View raiz = inflater.inflate(R.layout.fragment_listar_atividades_professor, container, false);
        recyclerView = raiz.findViewById(R.id.recyclerAtividadesProfessor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return raiz;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atividadeController = new AtividadeController(requireContext());

        professorLogado = SessaoUsuario.getProfessorLogado();

        if (professorLogado != null) {
            List<Atividade> atividades = atividadeController.listarAtividadesProfessor(professorLogado);

            if (atividades.isEmpty()) {
                Toast.makeText(getContext(), "Nenhuma atividade encontrada.", Toast.LENGTH_SHORT).show();
            }

            adapter = new AtividadeAdapter(atividades, this);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Professor não está logado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAtividadeClick(Atividade atividade) {
        DetalhesAtividadeProfessorFragment fragment = DetalhesAtividadeProfessorFragment.novaInstancia(atividade.getIdentificador());
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container_professor, fragment)
                .addToBackStack(null)
                .commit();
    }
}