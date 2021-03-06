package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.dev.loja.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    
    @Query("from Cliente where email=?1")
    public List<Cliente> buscarClienteEmail(String email);
}
