package com.dev.loja.servico;

import java.util.Optional;

import com.dev.loja.modelos.Categoria;
import com.dev.loja.modelos.Marca;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.ProdutoRepositorio;
import com.dev.loja.util.Paged;
import com.dev.loja.util.Paging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServico {
    
    private final ProdutoRepositorio produtoRepositorio;

    @Autowired
    public ProdutoServico(ProdutoRepositorio produtoRepositorio){
        this.produtoRepositorio = produtoRepositorio;
    }

    public Paged<Produto> getPage(int pageNumber, int size){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Produto> prodPage = produtoRepositorio.findAll(request);

        return new Paged<>(prodPage, Paging.of(prodPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Produto> getPageDescricao(int pageNumber, int size, String descricao){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Produto> prodPage = produtoRepositorio.findByDescricaoContaining(request, descricao);

        return new Paged<>(prodPage, Paging.of(prodPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Produto> getPageCategoria(int pageNumber, int size, Optional<Categoria> categoria){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Produto> prodPage = produtoRepositorio.findByCategoria(request, categoria);

        return new Paged<>(prodPage, Paging.of(prodPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Produto> getPageMarca(int pageNumber, int size, Optional<Marca> marca){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Produto> prodPage = produtoRepositorio.findByMarca(request, marca);

        return new Paged<>(prodPage, Paging.of(prodPage.getTotalPages(), pageNumber, size));
    }
}
