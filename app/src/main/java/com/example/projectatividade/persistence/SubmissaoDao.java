package com.example.projectatividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projectatividade.models.Atividade;
import com.example.projectatividade.models.Submissao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SubmissaoDao implements ICRUDDao<Submissao> {
    private DBHelper dbHelper;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SubmissaoDao(Context contexto) {
        dbHelper = new DBHelper(contexto);
    }

    @Override
    public Submissao criar(Submissao obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("conteudo", obj.getConteudo());
        valores.put("data_envio", obj.getDataEnvio() != null ? obj.getDataEnvio().format(dtf) : null);
        valores.put("id_atividade", obj.getAtividade() != null ? obj.getAtividade().getIdentificador() : null);
        valores.put("id_aluno", obj.getAluno() != null ? obj.getAluno().getIdentificador() : null);

        long id = db.insert("tb_submissao", null, valores);
        obj.setIdentificador((int) id);
        db.close();
        return obj;
    }

    @Override
    public Submissao atualizar(Submissao obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("conteudo", obj.getConteudo());
        valores.put("data_envio", obj.getDataEnvio() != null ? obj.getDataEnvio().format(dtf) : null);
        valores.put("id_atividade", obj.getAtividade() != null ? obj.getAtividade().getIdentificador() : null);
        valores.put("id_aluno", obj.getAluno() != null ? obj.getAluno().getIdentificador() : null);

        db.update("tb_submissao", valores, "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return obj;
    }

    @Override
    public boolean deletar(Submissao obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int linhas = db.delete("tb_submissao", "identificador=?", new String[]{String.valueOf(obj.getIdentificador())});
        db.close();
        return linhas > 0;
    }

    @Override
    public List<Submissao> listarTodos() {
        List<Submissao> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT identificador, conteudo, data_envio, id_atividade, id_aluno FROM tb_submissao", null);
        while (c.moveToNext()) {
            Submissao s = new Submissao();
            s.setIdentificador(c.getInt(0));
            s.setConteudo(c.getString(1));
            String dataEnvioStr = c.getString(2);
            if (dataEnvioStr != null) s.setDataEnvio(LocalDate.parse(dataEnvioStr, dtf));
            int idAtividade = c.getInt(3);
            int idAluno = c.getInt(4);
            lista.add(s);
        }
        c.close();
        db.close();
        return lista;
    }

    @Override
    public Submissao buscarPorId(int identificador) {
        Submissao s = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT s.identificador, s.conteudo, s.data_envio, s.id_atividade, a.titulo, a.descricao, a.data_criacao, a.data_entrega FROM tb_submissao s INNER JOIN tb_atividade a ON a.identificador = id_atividade WHERE s.identificador=?", new String[]{String.valueOf(identificador)});
        if (c.moveToFirst()) {
            s = new Submissao();
            s.setIdentificador(c.getInt(0));
            s.setConteudo(c.getString(1));
            String dataEnvioStr = c.getString(2);
            if (dataEnvioStr != null) s.setDataEnvio(LocalDate.parse(dataEnvioStr, dtf));
            s.setAtividade(new Atividade(c.getInt(3), c.getString(4), c.getString(5), LocalDate.parse(c.getString(6), dtf), LocalDate.parse(c.getString(7), dtf)));
        }
        c.close();
        db.close();
        return s;
    }

    public Submissao buscarPorIdAtividadeAluno(int id_atividade, int id_aluno) {
        Submissao s = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT s.identificador, s.conteudo, s.data_envio, s.id_atividade, a.titulo, a.descricao, a.data_criacao, a.data_entrega FROM tb_submissao s INNER JOIN tb_atividade a ON a.identificador = id_atividade WHERE id_atividade=? AND id_aluno=?", new String[]{String.valueOf(id_atividade), String.valueOf(id_aluno)});
        if (c.moveToFirst()) {
            s = new Submissao();
            s.setIdentificador(c.getInt(0));
            s.setConteudo(c.getString(1));
            String dataEnvioStr = c.getString(2);
            if (dataEnvioStr != null) s.setDataEnvio(LocalDate.parse(dataEnvioStr, dtf));
            s.setAtividade(new Atividade(c.getInt(3), c.getString(4), c.getString(5), LocalDate.parse(c.getString(6), dtf), LocalDate.parse(c.getString(7), dtf)));
        }
        c.close();
        db.close();
        return s;
    }
}