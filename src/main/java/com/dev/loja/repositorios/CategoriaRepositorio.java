package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.dev.loja.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNomeContaining(String categoria);
}
