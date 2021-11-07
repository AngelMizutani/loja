package com.dev.loja.controle;

import java.util.HashMap;
import java.util.Map;

import com.dev.loja.servico.EnviarEmailHtmlServico;
import com.dev.loja.servico.EnviarEmailServico;
import com.dev.loja.servico.EnviarEmailTemplateServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvioEmailControle {

    @Autowired
    private EnviarEmailServico enviarEmail;

    @Autowired
    private EnviarEmailHtmlServico enviarEmailHtml;

    @Autowired
    private EnviarEmailTemplateServico enviarEmailTemplateServico;

    @GetMapping("/enviar-email")
    public String enviar(){
        return enviarEmail.enviar("angelicamizutani@gmail.com", "Nova senha", "Olá! \n\n Sua nova senha é 123456");

    }

    @GetMapping("/enviar-html")
    public String enviarHtml(){
        StringBuilder builder = new StringBuilder();
        builder.append("Olá <b>Angélica!</b> <br/>");
        builder.append("Sua nova senha é <b>123456<b><br/>");
        return enviarEmailHtml.enviar("angelicamizutani@gmail.com", "Nova senha", builder.toString());

    }

    @GetMapping("/enviar-email-template")
    public String enviarEmailTemplate(){
        Map<String, Object> prop = new HashMap<String, Object>();
        prop.put("nome", "Angelica"); //usuario.nome
        prop.put("codigo", "123456"); //usuario.senha

        return enviarEmailTemplateServico.enviar(
            "angelicamizutani@gmail.com", 
            "mais um email", 
            "emailRecuperacaoSenha", 
            prop);
    }
    
}
