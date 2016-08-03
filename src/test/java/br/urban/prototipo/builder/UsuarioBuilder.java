package br.urban.prototipo.builder;

import br.urban.prototipo.model.Papel;
import br.urban.prototipo.model.Usuario;

public class UsuarioBuilder {

	private Usuario usuario;
	
	public UsuarioBuilder() {
		this.usuario = new Usuario(null);
	}
	
	public UsuarioBuilder comLogin(String login) {
		this.usuario.setLogin(login);
		return this;
	}
	
	public UsuarioBuilder adicionaPapel(Papel papel) {
		this.usuario.adiciona(papel);
		return this;
	}
		
	public Usuario build() {
		return this.usuario;
	}
}
