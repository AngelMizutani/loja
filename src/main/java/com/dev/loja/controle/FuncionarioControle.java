package com.dev.loja.controle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.repositorios.CidadeRepositorio;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.servico.EnviarEmailHtmlServico;
import com.dev.loja.servico.EnviarEmailServico;
import com.dev.loja.servico.EnviarEmailTemplateServico;
import com.dev.loja.util.ValidarCpf;

@Controller
public class FuncionarioControle {

	@Autowired
    private EnviarEmailTemplateServico enviarEmail;
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/administrativo/funcionarios/cadastrar")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/cadastro");
		mv.addObject("funcionario", funcionario);
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		return cadastrar(funcionario.get());
	}
	
	@GetMapping("/administrativo/funcionarios/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		funcionarioRepositorio.delete(funcionario.get());
		return listar();
	}
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result) {

		boolean cpfValido = validarCpf(funcionario.getCpf());
		
		if(result.hasErrors() || (cpfValido == false)) {
			return cadastrar(funcionario);
		}

		//funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
		
		funcionarioRepositorio.saveAndFlush(funcionario);

		enviarCodigo(funcionario.getEmail());

		return cadastrar(new Funcionario());
	}

	public Boolean validarCpf(String cpf) {
		Boolean cpfValido = false;
		if(ValidarCpf.isCPF(cpf)) {
			cpfValido = true;
		}

		return cpfValido;
	}

	public String enviarCodigo (String email){
		Funcionario funcionario = funcionarioRepositorio.findByEmail(email);
        
        if(funcionario != null){
            int codigo = 1000 + ((int) (Math.random() * 1000));
            funcionario.setCodigoRecuperacao(Integer.toString(codigo));
            funcionario.setDataCodigo(new Date());
            funcionarioRepositorio.save(funcionario);

			Map<String, Object> prop = new HashMap<String, Object>();
        	prop.put("codigo", codigo); //usuario.codigo

        	return enviarEmail.enviar(
            	email, 
            	"Cadastro de Senha", 
            	"/email/emailCadastroSenha", 
            	prop);
        }

		return "";

	}

	@GetMapping("/cadastrarSenha")
	public ModelAndView cadastrarSenha(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/email/cadastrarSenha");
		return mv;
	}

	@PostMapping("/cadastrarSenha")
    public String salvarSenha(
        @RequestParam(name = "email") String email,
        @RequestParam(name = "codigo") String codigo,
        @RequestParam(name = "senha") String senha,
        Model model
        ) {
            Funcionario funcionario = funcionarioRepositorio.findByEmailAndCodigoRecuperacao(email, codigo);

            if (funcionario != null){
                Date diff = new Date(new Date().getTime() - funcionario.getDataCodigo().getTime());

                if (diff.getTime() / 1000 < 900) {
                    funcionario.setCodigoRecuperacao(null);
                    funcionario.setDataCodigo(null);
					funcionario.setSenha(new BCryptPasswordEncoder().encode(senha));
                    funcionarioRepositorio.saveAndFlush(funcionario);
                    funcionarioRepositorio.save(funcionario);
                    model.addAttribute("mensagem", "Senha cadastrada com sucesso!");

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
