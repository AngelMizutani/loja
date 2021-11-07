package com.dev.loja.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class EnviarEmailServico {

    @Autowired
    private JavaMailSender mailSender;
    
    public String enviar(String destinatario, String assunto, String mensagemCorpo){
        try{
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom("testeemailpw21@gmail.com");
            mensagem.setSubject(assunto);
            mensagem.setTo(destinatario);
            mensagem.setText(mensagemCorpo);
    
            mailSender.send(mensagem);

            return "Email enviado com sucesso!";

        } catch (Exception e){
            e.printStackTrace();
            return "Erro!";
        }

    }
    
}
