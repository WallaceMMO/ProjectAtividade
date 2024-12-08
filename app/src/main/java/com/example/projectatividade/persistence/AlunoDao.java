package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectatividade.models.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements ICRUDDao<Aluno> {
    private DBHelper dbHelper;

    public AlunoDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public Aluno criar(Aluno obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("nome", obj.getNome());
        long idUsuario = db.insert("tb_usuario", null, valoresUsuario);

        ContentValues valoresAluno = new ContentValues();
        valoresAluno.put("id_usuario", idUsuario);
        db.insert("tb_aluno", null, valoresAluno);

        obj.setIdentificador((int) idUsuario);
        db.close();
        return obj;
    }

    @Override
    public Aluno atualizar(Aluno obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("nome", obj.getNome());
        db.update("tb_usuario", valoresUsuario, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(Aluno obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tb_aluno", "id_usuario=?", new String[]{String.valueOf(obj.getIdentificador())});
        int linhas = db.delete("tb_usuario", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.identificador, u.nome FROM tb_aluno a INNER JOIN tb_usuario u ON a.id_usuario = u.identificador", null);
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

    @Override
    public Aluno buscarPorId(int identificador) {
        Aluno a = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.identificador, u.nome FROM tb_aluno a INNER JOIN tb_usuario u ON a.id_usuario = u.identificador WHERE u.identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            a = new Aluno();
            a.setIdentificador(c.getInt(0));
            a.setNome(c.getString(1));
        }
        c.close();
        db.close();
        return a;
    }

    public List<Aluno> listarPorAtividade(int atividadeId) {
        List<Aluno> alunos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT Aluno.id, Aluno.nome FROM Aluno " +
                "INNER JOIN AtividadeAluno ON Aluno.id = AtividadeAluno.aluno_id " +
                "WHERE AtividadeAluno.atividade_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(atividadeId)});

        if (cursor.moveToFirst()) {
            do {
                Aluno aluno = new Aluno();
                aluno.setIdentificador(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                aluno.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                alunos.add(aluno);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return alunos;
    }
}