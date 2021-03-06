package com.dev.loja.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="produtos")
public class Produto implements Serializable{
	
	public Produto() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String descricao;
	private Double valorVenda;
	
	@ManyToOne
	private Categoria categoria;

	@ManyToOne
	private Marca marca;
	private Double quantidadeEstoque = 0.0;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "produto")
	private List<Imagem> imagens = new ArrayList<>();

	private String nomeImagem;

	public String getNomeImagem() {
		return nomeImagem;
	}
	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	public Double getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Double quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public List<Imagem> getImagens() {
		return this.imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagem(Imagem imagem){
		this.imagens.add(imagem);
		imagem.setProduto(this);
	}
	
}
