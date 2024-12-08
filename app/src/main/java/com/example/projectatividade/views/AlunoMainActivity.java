package com.example.projectatividade.views;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectatividade.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AlunoMainActivity extends AppCompatActivity {

    private Button btnListarAtividadesAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_main);

        btnListarAtividadesAluno = findViewById(R.id.btnListarAtividadesAluno);
        btnListarAtividadesAluno.setOnClickListener(v -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_aluno, new ListarAtividadesAlunoFragment());
            ft.addToBackStack(null);
            ft.commit();
        });
    }
}