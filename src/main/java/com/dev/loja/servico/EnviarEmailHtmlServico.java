package com.dev.loja.servico;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailHtmlServico {

    @Autowired
    private JavaMailSender mailSender;
    
    public String enviar(String destinatario, String assunto, String mensagemCorpo){
        try{
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mensagem = new MimeMessageHelper(mail);
            mensagem.setFrom("testeemailpw21@gmail.com");
            mensagem.setSubject(assunto);
            mensagem.setTo(destinatario);
            mensagem.setText(mensagemCorpo, true);
    
            mailSender.send(mail);

            return "Email enviado com sucesso!";

        } catch (Exception e){
            e.printStackTrace();
            return "Erro!";
        }

    }
    
}
