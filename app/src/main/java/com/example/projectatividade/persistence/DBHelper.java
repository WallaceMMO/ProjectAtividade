package com.example.projectatividade.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "sistema_atividades.db";
    public static final int VERSAO = 1;

    public DBHelper(Context contexto) {
        super(contexto, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_usuario (identificador INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)");
        db.execSQL("CREATE TABLE tb_professor (id_usuario INTEGER PRIMARY KEY, FOREIGN KEY(id_usuario) REFERENCES tb_usuario(identificador))");
        db.execSQL("CREATE TABLE tb_aluno (id_usuario INTEGER PRIMARY KEY, FOREIGN KEY(id_usuario) REFERENCES tb_usuario(identificador))");
        db.execSQL("CREATE TABLE tb_atividade (identificador INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descricao TEXT, data_criacao TEXT, data_entrega TEXT, id_professor INTEGER, FOREIGN KEY(id_professor) REFERENCES tb_professor(id_usuario))");
        db.execSQL("CREATE TABLE tb_criterio (identificador INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, peso REAL, id_atividade INTEGER, FOREIGN KEY(id_atividade) REFERENCES tb_atividade(identificador))");
        db.execSQL("CREATE TABLE tb_submissao (identificador INTEGER PRIMARY KEY AUTOINCREMENT, conteudo TEXT, data_envio TEXT, id_atividade INTEGER, id_aluno INTEGER, FOREIGN KEY(id_atividade) REFERENCES tb_atividade(identificador), FOREIGN KEY(id_aluno) REFERENCES tb_aluno(id_usuario))");
        db.execSQL("CREATE TABLE tb_nota_criterio (identificador INTEGER PRIMARY KEY AUTOINCREMENT, valor REAL, id_submissao INTEGER, id_criterio INTEGER, FOREIGN KEY(id_submissao) REFERENCES tb_submissao(identificador), FOREIGN KEY(id_criterio) REFERENCES tb_criterio(identificador))");
        db.execSQL("CREATE TABLE tb_atividade_aluno (id_atividade INTEGER, id_aluno INTEGER, PRIMARY KEY (id_atividade, id_aluno),FOREIGN KEY(id_atividade) REFERENCES tb_atividade(identificador) ON DELETE CASCADE,FOREIGN KEY(id_aluno) REFERENCES tb_aluno(identificador) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS tb_usuario");
            db.execSQL("DROP TABLE IF EXISTS tb_professor");
            db.execSQL("DROP TABLE IF EXISTS tb_aluno");
            db.execSQL("DROP TABLE IF EXISTS tb_atividade");
            db.execSQL("DROP TABLE IF EXISTS tb_criterio");
            db.execSQL("DROP TABLE IF EXISTS tb_submissao");
            db.execSQL("DROP TABLE IF EXISTS tb_nota_criterio");
            db.execSQL("DROP TABLE IF EXISTS tb_atividade_aluno");
            onCreate(db);
            Log.d("MeuSQLiteOpenHelper", "Banco de dados atualizado para a versão " + newVersion);
        } catch (Exception e) {
            Log.e("MeuSQLiteOpenHelper", "Erro ao atualizar banco de dados: " + e.getMessage());
        }
    }
}
