package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Aluno;
import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Criterio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AlunoAtividadeDao {
    private DBHelper dbHelper;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public AlunoAtividadeDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    public void associarAlunoAtividade(int idAtividade, int idAluno) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id_atividade", idAtividade);
        valores.put("id_aluno", idAluno);
        db.insert("tb_atividade_aluno", null, valores);
        db.close();
    }

    public List<Aluno> listarAlunosPorAtividade(int idAtividade) {
        List<Aluno> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.identificador, u.nome FROM tb_atividade_aluno aa INNER JOIN tb_aluno a ON aa.id_aluno = a.id_usuario INNER JOIN tb_usuario u ON a.id_usuario = u.identificador WHERE aa.id_atividade=?", new String[]{String.valueOf(idAtividade)});
        while (c.moveToNext()) {
            Aluno a = new Aluno();
            a.setIdentificador(c.getInt(0));
            a.setNome(c.getString(1));
            lista.add(a);
        }
        c.close();
        db.close();
        return lista;
    }

    public List<Atividade> listarAtividadesPorAluno(int identificador) {
        List<Atividade> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT a.identificador, a.titulo, a.descricao, a.data_criacao, a.data_entrega, a.id_professor FROM tb_atividade_aluno aa INNER JOIN tb_atividade a ON aa.id_atividade = a.identificador WHERE aa.id_aluno=?", new String[]{String.valueOf(identificador)});
        while (c.moveToNext()) {
            Atividade a = new Atividade();
            a.setIdentificador(c.getInt(0));
            a.setTitulo(c.getString(1));
            a.setDescricao(c.getString(2));
            String dataCriacaoStr = c.getString(3);
            String dataEntregaStr = c.getString(4);
            if (dataCriacaoStr != null) a.setDataCriacao(LocalDate.parse(dataCriacaoStr, dtf));
            if (dataEntregaStr != null) a.setDataEntrega(LocalDate.parse(dataEntregaStr, dtf));

            int idProfessor = c.getInt(5);

            lista.add(a);
        }
        c.close();
        db.close();
        return lista;
    }


    public List<Criterio> listarCriteriosPorAtividade(int idAtividade) {
        List<Criterio> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, nome, peso FROM tb_criterio WHERE id_atividade=?", new String[]{String.valueOf(idAtividade)});
        while (c.moveToNext()) {
            Criterio crt = new Criterio();
            crt.setIdentificador(c.getInt(0));
            crt.setNome(c.getString(1));
            crt.setPeso(c.getDouble(2));
            lista.add(crt);
        }
        c.close();
        db.close();
        return lista;
    }
}