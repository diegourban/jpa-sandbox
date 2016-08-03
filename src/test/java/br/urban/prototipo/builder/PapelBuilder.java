package br.urban.prototipo.builder;

import br.urban.prototipo.model.Papel;

public class PapelBuilder {
	
	private Papel papel;
	
	public PapelBuilder() {
		this.papel = new Papel(null);
	}
	
	public PapelBuilder comDescricao(String descricao) {
		this.papel.setDescricao(descricao);
		return this;
	}
	
	public Papel build() {
		return this.papel;
	}

}
