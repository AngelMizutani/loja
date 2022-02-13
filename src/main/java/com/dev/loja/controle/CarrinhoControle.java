package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dev.loja.modelos.ItensCompra;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.ProdutoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarrinhoControle {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();

	@Autowired
	private ProdutoRepositorio repositorioProduto;

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {

		ModelAndView mv = new ModelAndView("cliente/carrinho");
		mv.addObject("listaItens", itensCompra);
		return mv;

	}

	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(
		@PathVariable Long id,
		@PathVariable Integer acao) {


		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				if(acao.equals(1)){
					it.setQuantidade(it.getQuantidade() + 1);
				} else if(acao == 0) {
					if(it.getQuantidade() > 0){
						it.setQuantidade(it.getQuantidade() - 1);
					}
					else{
						it.setQuantidade(0);
					}
				}

				break;
			}
		}

		return "redirect:/carrinho";

	}

	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				itensCompra.remove(it);

				break;
			}
		}

		return "redirect:/carrinho";

	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Long id) {
		Optional<Produto> prod = repositorioProduto.findById(id);
		Produto produto = prod.get();

		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				controle = 1;
				break;
			}
		}

		if (controle == 0) {
			ItensCompra item = new ItensCompra();

			item.setProduto(produto);
			item.setValorUnitario(produto.getValorVenda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
			itensCompra.add(item);

		}

		return "redirect:/carrinho";

	}

}
