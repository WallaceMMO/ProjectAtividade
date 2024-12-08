package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectatividade.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfessorMainActivity extends AppCompatActivity {
    private Button btnCriarAtividade;
    private Button btnListarAtividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main);

        btnCriarAtividade = findViewById(R.id.btnCriarAtividade);
        btnListarAtividades = findViewById(R.id.btnListarAtividades);

        btnCriarAtividade.setOnClickListener(v -> {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_professor, new CriarAtividadeFragment());
                ft.commit();
        });

        btnListarAtividades.setOnClickListener(v -> {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_professor, new ListarAtividadesProfessorFragment());
                ft.commit();
        });
    }
}