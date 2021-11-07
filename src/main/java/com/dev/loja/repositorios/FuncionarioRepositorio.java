package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
    Funcionario findByEmail(String email);
    Funcionario findByEmailAndCodigoRecuperacao(String email, String codigoRecuperacao);
}
