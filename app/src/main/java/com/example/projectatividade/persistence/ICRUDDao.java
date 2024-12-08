package com.example.projectatividade.persistence;

import java.util.List;

public interface ICRUDDao<T> {
    T criar(T obj);
    T atualizar(T obj);
    boolean deletar(T obj);
    List<T> listarTodos();
    T buscarPorId(int identificador);
}