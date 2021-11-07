package com.dev.loja.controle;

import java.util.Date;

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.servico.EnviarEmailServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecuperarSenhaControle {
    
    @Autowired
    private EnviarEmailServico enviarEmail;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @GetMapping("/recuperarSenha")
    public String recuperarSenha(Model model){
        return "email/recuperarSenha";
    }

    @PostMapping("/solicitarCodigo")
    public String solicitarCodigo(@RequestParam(name = "email") String email, Model model){
        Funcionario funcionario = funcionarioRepositorio.findByEmail(email);
        
        if(funcionario != null){
            int codigo = 1000 + ((int) (Math.random() * 1000));
            funcionario.setCodigoRecuperacao(Integer.toString(codigo));
            funcionario.setDataCodigo(new Date());
            funcionarioRepositorio.save(funcionario);

            enviarEmail.enviar(
                email, 
                "Recuperação de Senha", 
                "O seu código de recuperação de senha é: " + funcionario.getCodigoRecuperacao());
            
            model.addAttribute("mensagem", "O código foi encaminhado para o seu email");

            return "email/alterarSenha";
        }

        model.addAttribute("mensagem", "Email não encontrado");

        return "email/mensagem";
    }

    @PostMapping("/alterarSenha")
    public String alterarSenha(
        @RequestParam(name = "email") String email,
        @RequestParam(name = "codigoRecuperacao") String codigoRecuperacao,
        @RequestParam(name = "senha") String senha,
        Model model
        ) {
            Funcionario funcionario = funcionarioRepositorio.findByEmailAndCodigoRecuperacao(email, codigoRecuperacao);

            if (funcionario != null){
                Date diff = new Date(new Date().getTime() - funcionario.getDataCodigo().getTime());

                if (diff.getTime() / 1000 < 900) {
                    funcionario.setCodigoRecuperacao(null);
                    funcionario.setDataCodigo(null);
                    funcionarioRepositorio.save(funcionario);
                    model.addAttribute("mensagem", "Senha alterada com sucesso!");

                    return "email/mensagem";
                } else {
                    model.addAttribute("mensagem", "Código expirado, solicite novamente");
                    return "email/mensagem";
                }
            }

            model.addAttribute("mensagem", "Email e/ou código não encontrado");

            return "email/mensagem";
        }
}
