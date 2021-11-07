package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.dev.loja.modelos.Imagem;

public interface ImagemRepositorio extends JpaRepository<Imagem, Long> {
    List<Imagem> findByProduto(Long id_produto);
}
