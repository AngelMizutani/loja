package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Marca;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {

}
