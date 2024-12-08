package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectatividade.models.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDao implements ICRUDDao<Professor> {
    private DBHelper dbHelper;

    public ProfessorDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public Professor criar(Professor obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("nome", obj.getNome());
        long idUsuario = db.insert("tb_usuario", null, valoresUsuario);

        ContentValues valoresProfessor = new ContentValues();
        valoresProfessor.put("id_usuario", idUsuario);
        db.insert("tb_professor", null, valoresProfessor);

        obj.setIdentificador((int) idUsuario);
        db.close();
        return obj;
    }

    @Override
    public Professor atualizar(Professor obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("nome", obj.getNome());
        db.update("tb_usuario", valoresUsuario, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(Professor obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tb_professor", "id_usuario=?", new String[]{String.valueOf(obj.getIdentificador())});
        int linhas = db.delete("tb_usuario", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<Professor> listarTodos() {
        List<Professor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.identificador, u.nome FROM tb_professor p INNER JOIN tb_usuario u ON p.id_usuario = u.identificador", null);
        while (c.moveToNext()) {
            Professor p = new Professor();
            p.setIdentificador(c.getInt(0));
            p.setNome(c.getString(1));
            lista.add(p);
        }
        c.close();
        db.close();
        return lista;
    }

    @Override
    public Professor buscarPorId(int identificador) {
        Professor p = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT u.identificador, u.nome FROM tb_professor p INNER JOIN tb_usuario u ON p.id_usuario = u.identificador WHERE u.identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            p = new Professor();
            p.setIdentificador(c.getInt(0));
            p.setNome(c.getString(1));
        }
        c.close();
        db.close();
        return p;
    }
}