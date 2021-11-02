package com.dev.loja.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cidade;

@Controller
public class LoginControle {
	
	@GetMapping("/login")
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/login");
		return mv;
	}

}
