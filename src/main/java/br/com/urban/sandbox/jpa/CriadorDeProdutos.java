package br.com.urban.sandbox.jpa;

import javax.persistence.EntityManager;

import br.com.urban.sandbox.jpa.model.Categoria;
import br.com.urban.sandbox.jpa.model.Loja;
import br.com.urban.sandbox.jpa.model.Produto;

public class CriadorDeProdutos {

	public static void criarProdutos(EntityManager em) {
		Loja casaDoCodigo = new Loja();
		casaDoCodigo.setNome("Casa do Código");

		em.persist(casaDoCodigo);

		Loja musicalAlegre = new Loja();
		musicalAlegre.setNome("Musical Alegre");

		em.persist(musicalAlegre);

		Categoria tecnologia = new Categoria("Tecnologia");
		em.persist(tecnologia);

		Categoria musica = new Categoria("Música");
		em.persist(musica);

		Produto cursoDeViolao = new Produto();
		cursoDeViolao.setId(1);
		cursoDeViolao.setNome("Curso de Violão");
		cursoDeViolao.setLoja(musicalAlegre);
		cursoDeViolao.setPreco(49.0);
		cursoDeViolao.setDescricao("Estude com os melhores professores e aprenda no seu ritmo, sem sair de casa.");
		cursoDeViolao.adicionarCategorias(tecnologia, musica);
		cursoDeViolao.setLinkDaFoto(
				"https://pbs.twimg.com/profile_images/378800000825434860/12136ee913ed4f44860914d44650144e.png");

		em.persist(cursoDeViolao);

		Produto livroDeArquitetura = new Produto();
		livroDeArquitetura.setNome("Introduçao a Arquitetura Java e Design de projetos com Java");
		livroDeArquitetura.setLoja(casaDoCodigo);
		livroDeArquitetura.setPreco(30.0);
		livroDeArquitetura.adicionarCategorias(tecnologia);
		livroDeArquitetura.setDescricao("Neste livro, os autores, conhecidos especialistas da...");

		livroDeArquitetura.setLinkDaFoto("http://www.arquiteturajava.com.br/img/capa-livro.png");

		em.persist(livroDeArquitetura);

		Produto livroDeSpring = new Produto();
		livroDeSpring.setNome("Vire o jogo com Spring Framework");
		livroDeSpring.setLoja(casaDoCodigo);
		livroDeSpring.setPreco(30.0);
		livroDeSpring.adicionarCategorias(tecnologia);
		livroDeSpring.setDescricao("Criado para simplificar o desenvolvimento de aplicações Java...");

		livroDeSpring.setLinkDaFoto(
				"http://cdn.shopify.com/s/files/1/0155/7645/products/spring-framework-featured_large.png?v=1411567960");

		em.persist(livroDeSpring);

		Produto violao = new Produto();
		violao.setNome("Violão");
		violao.setLoja(musicalAlegre);
		violao.setDescricao("Excelente violão");
		violao.setPreco(500.0);
		violao.adicionarCategorias(musica);
		violao.setLinkDaFoto("http://www.marillac.g12.br/imgs/atividade%20complementar/violao.jpg");

		em.persist(violao);

		Produto flauta = new Produto();
		flauta.setNome("Flauta Doce");
		flauta.setLoja(musicalAlegre);
		flauta.setDescricao("Flauta doce");
		flauta.setPreco(300.0);
		flauta.adicionarCategorias(musica);
		flauta.setLinkDaFoto("http://i.mlcdn.com.br/1500x1500/flauta-doce-germanicayamaha-yrs-23g-204013000.jpg");

		em.persist(flauta);
	}

}
