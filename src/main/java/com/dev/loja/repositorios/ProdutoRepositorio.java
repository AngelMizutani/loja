package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.dev.loja.modelos.Categoria;
import com.dev.loja.modelos.Marca;
import com.dev.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    List<Produto> findByDescricaoContaining(String descricao);
    List<Produto> findByCategoria(Optional<Categoria> categoria);  
    List<Produto> findByMarca(Optional<Marca> marca); 
}
