package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Criterio;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CriterioDao implements ICRUDDao<Criterio> {
    private DBHelper dbHelper;

    public CriterioDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public Criterio criar(Criterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", obj.getNome());
        valores.put("peso", obj.getPeso());
        valores.put("id_atividade", obj.getAtividade() != null ? obj.getAtividade().getIdentificador() : null);

        long id = db.insert("tb_criterio", null, valores);
        obj.setIdentificador((int) id);
        db.close();
        return obj;
    }

    @Override
    public Criterio atualizar(Criterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", obj.getNome());
        valores.put("peso", obj.getPeso());
        valores.put("id_atividade", obj.getAtividade() != null ? obj.getAtividade().getIdentificador() : null);

        db.update("tb_criterio", valores, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(Criterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int linhas = db.delete("tb_criterio", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<Criterio> listarTodos() {
        List<Criterio> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, nome, peso, id_atividade FROM tb_criterio", null);
        while (c.moveToNext()) {
            Criterio crt = new Criterio();
            crt.setIdentificador(c.getInt(0));
            crt.setNome(c.getString(1));
            crt.setPeso(c.getDouble(2));
            int idAtividade = c.getInt(3);
            lista.add(crt);
        }
        c.close();
        db.close();
        return lista;
    }

    @Override
    public Criterio buscarPorId(int identificador) {
        Criterio crt = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, nome, peso, id_atividade FROM tb_criterio WHERE identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            crt = new Criterio();
            crt.setIdentificador(c.getInt(0));
            crt.setNome(c.getString(1));
            crt.setPeso(c.getDouble(2));
            int idAtividade = c.getInt(3);
        }
        c.close();
        db.close();
        return crt;
    }


    public List<Criterio> listarPorAtividade(int atividadeId) {
        List<Criterio> criterios = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_criterio WHERE id_atividade = ?", new String[]{String.valueOf(atividadeId)});

        if (cursor.moveToFirst()) {
            do {
                Criterio criterio = new Criterio();
                criterio.setIdentificador(cursor.getInt(cursor.getColumnIndexOrThrow("identificador")));
                criterio.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                criterio.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow("peso")));

                criterios.add(criterio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return criterios;
    }

    public List<Criterio> listarPorAtividadeId(int atividadeId) {
        List<Criterio> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Criterio WHERE atividade_id = ?", new String[]{String.valueOf(atividadeId)});

        if (cursor.moveToFirst()) {
            do {
                Criterio criterio = new Criterio();
                criterio.setIdentificador(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                criterio.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                criterio.setPeso(cursor.getDouble(cursor.getColumnIndexOrThrow("peso")));

                lista.add(criterio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }
}