package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectatividade.models.Criterio;
import com.example.projectatividade.models.NotaCriterio;

import java.util.ArrayList;
import java.util.List;

public class NotaCriterioDao implements ICRUDDao<NotaCriterio> {
    private DBHelper dbHelper;

    public NotaCriterioDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public NotaCriterio criar(NotaCriterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("valor", obj.getValor());
        valores.put("id_submissao", obj.getSubmissao() != null ? obj.getSubmissao().getIdentificador() : null);
        valores.put("id_criterio", obj.getCriterio() != null ? obj.getCriterio().getIdentificador() : null);

        long id = db.insert("tb_nota_criterio", null, valores);
        obj.setIdentificador((int) id);
        db.close();
        return obj;
    }

    @Override
    public NotaCriterio atualizar(NotaCriterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("valor", obj.getValor());
        valores.put("id_submissao", obj.getSubmissao() != null ? obj.getSubmissao().getIdentificador() : null);
        valores.put("id_criterio", obj.getCriterio() != null ? obj.getCriterio().getIdentificador() : null);

        db.update("tb_nota_criterio", valores, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(NotaCriterio obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int linhas = db.delete("tb_nota_criterio", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<NotaCriterio> listarTodos() {
        List<NotaCriterio> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, valor, id_submissao, id_criterio FROM tb_nota_criterio", null);
        while (c.moveToNext()) {
            NotaCriterio nc = new NotaCriterio();
            nc.setIdentificador(c.getInt(0));
            nc.setValor(c.getDouble(1));
            int idSubmissao = c.getInt(2);
            int idCriterio = c.getInt(3);

            lista.add(nc);
        }
        c.close();
        db.close();
        return lista;
    }

    public List<NotaCriterio> listarPorSubmissao(int id) {
        List<NotaCriterio> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT nc.identificador, nc.valor, nc.id_submissao, nc.id_criterio, c.nome, c.peso FROM tb_nota_criterio nc INNER JOIN tb_criterio c ON c.identificador = nc.id_criterio WHERE id_submissao = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            NotaCriterio nc = new NotaCriterio();
            nc.setIdentificador(c.getInt(0));
            nc.setValor(c.getDouble(1));
            Criterio criterio = new Criterio();
            criterio.setIdentificador(c.getInt(3));
            criterio.setNome(c.getString(4));
            criterio.setPeso(c.getDouble(5));
            nc.setCriterio(criterio);

            lista.add(nc);
        }
        c.close();
        db.close();
        return lista;
    }
    @Override
    public NotaCriterio buscarPorId(int identificador) {
        NotaCriterio nc = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, valor, id_submissao, id_criterio FROM tb_nota_criterio WHERE identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            nc = new NotaCriterio();
            nc.setIdentificador(c.getInt(0));
            nc.setValor(c.getDouble(1));
            int idSubmissao = c.getInt(2);
            int idCriterio = c.getInt(3);
        }
        c.close();
        db.close();
        return nc;
    }
}