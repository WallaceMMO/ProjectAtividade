package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Atividade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AtividadeDao implements ICRUDDao<Atividade> {
    private DBHelper dbHelper;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AtividadeDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public Atividade criar(Atividade obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", obj.getTitulo());
        valores.put("descricao", obj.getDescricao());
        valores.put("data_criacao", obj.getDataCriacao() != null ? obj.getDataCriacao().format(dtf) : null);
        valores.put("data_entrega", obj.getDataEntrega() != null ? obj.getDataEntrega().format(dtf) : null);
        valores.put("id_professor", obj.getProfessorCriador() != null ? obj.getProfessorCriador().getIdentificador() : null);

        long id = db.insert("tb_atividade", null, valores);
        obj.setIdentificador((int) id);
        db.close();
        
        return obj;
    }

    @Override
    public Atividade atualizar(Atividade obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", obj.getTitulo());
        valores.put("descricao", obj.getDescricao());
        valores.put("data_criacao", obj.getDataCriacao() != null ? obj.getDataCriacao().format(dtf) : null);
        valores.put("data_entrega", obj.getDataEntrega() != null ? obj.getDataEntrega().format(dtf) : null);
        valores.put("id_professor", obj.getProfessorCriador() != null ? obj.getProfessorCriador().getIdentificador() : null);

        db.update("tb_atividade", valores, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(Atividade obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int linhas = db.delete("tb_atividade", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<Atividade> listarTodos() {
        List<Atividade> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, titulo, descricao, data_criacao, data_entrega, id_professor FROM tb_atividade", null);

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

    @Override
    public Atividade buscarPorId(int identificador) {
        Atividade a = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, titulo, descricao, data_criacao, data_entrega, id_professor FROM tb_atividade WHERE identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            a = new Atividade();
            a.setIdentificador(c.getInt(0));
            a.setTitulo(c.getString(1));
            a.setDescricao(c.getString(2));
            String dataCriacaoStr = c.getString(3);
            String dataEntregaStr = c.getString(4);
            if (dataCriacaoStr != null) a.setDataCriacao(LocalDate.parse(dataCriacaoStr, dtf));
            if (dataEntregaStr != null) a.setDataEntrega(LocalDate.parse(dataEntregaStr, dtf));

            int idProfessor = c.getInt(5);
        }
        c.close();
        db.close();
        return a;
    }

    public List<Atividade> listarPorProfessor(int identificador) {
        List<Atividade> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, titulo, descricao, data_criacao, data_entrega, id_professor FROM tb_atividade WHERE id_professor = ?", new String[]{String.valueOf(identificador)});

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
}