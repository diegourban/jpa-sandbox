package br.com.urban.sandbox.jpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Version;

@NamedEntityGraphs({
		@NamedEntityGraph(name = "produtoComCategoria", attributeNodes = { @NamedAttributeNode("categorias") }) })
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private String linkDaFoto;

	@Column
	private String descricao;

	private double preco;

	@ManyToMany
	private List<Categoria> categorias = new ArrayList<>();

	@ManyToOne
	private Loja loja;
	
	@Version
	private Integer versao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void adicionarCategorias(Categoria... categorias) {
		for (Categoria categoria : categorias) {
			this.categorias.add(categoria);
		}
	}

	public String getLinkDaFoto() {
		return linkDaFoto;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setLinkDaFoto(String linkDaFoto) {
		this.linkDaFoto = linkDaFoto;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Loja getLoja() {
		return loja;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Integer getVersao() {
		return versao;
	}
	
	public void setVersao(Integer versao) {
		this.versao = versao;
	}

}
