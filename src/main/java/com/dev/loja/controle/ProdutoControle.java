package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.Line;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Categoria;
import com.dev.loja.modelos.Marca;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.CategoriaRepositorio;
import com.dev.loja.repositorios.MarcaRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {
	
	private static String caminhoImagens = "C:\\Users\\angel\\OneDrive\\Documentos\\Engenharia de Software\\3o. Ano\\Programação WEB\\imagens_loja\\";
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	@Autowired
	private MarcaRepositorio marcaRepositorio;
	
	@GetMapping("/administrativo/entrada/produtos/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		mv.addObject("listaCategorias", categoriaRepositorio.findAll());
		mv.addObject("listaMarcas", marcaRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/entrada/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}

	@PostMapping("/administrativo/produtos/buscarDescricao")
	public ModelAndView buscarPorDescricao(@RequestParam("buscar_descricao") String descricao){
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findByDescricaoContaining(descricao));
		return mv;
	}

	@PostMapping("/administrativo/produtos/buscarCategoria")
	public ModelAndView buscarPorCategoria(@RequestParam("buscar_categoria") String categoria){
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		Optional<Categoria> opCategoria = categoriaRepositorio.findByNomeContaining(categoria);
		mv.addObject("listaProdutos", produtoRepositorio.findByCategoria(opCategoria));
		return mv;
	}

	@PostMapping("/administrativo/produtos/buscarMarca")
	public ModelAndView buscarPorMarca(@RequestParam("buscar_marca") String marca){
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		Optional<Marca> opMarca = marcaRepositorio.findByNomeContaining(marca);
		mv.addObject("listaProdutos", produtoRepositorio.findByMarca(opMarca));
		return mv;
	}
	
	@GetMapping("/administrativo/entrada/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return cadastrar(produto.get());
	}
	
	@GetMapping("/administrativo/entrada/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return listar();
	}
	
	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length()>0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}
	
	@PostMapping("/administrativo/entrada/produtos/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo) {
		if(result.hasErrors()) {
			return cadastrar(produto);
		}
		
		produtoRepositorio.saveAndFlush(produto);
		
		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				
				produto.setNomeImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				produtoRepositorio.saveAndFlush(produto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return cadastrar(new Produto());
	}

}
