package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
