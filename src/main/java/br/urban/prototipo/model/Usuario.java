package br.urban.prototipo.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "login", nullable = false)
	private String login;

	@ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name="usuario_papel", joinColumns={@JoinColumn(name="usuario_id")}, inverseJoinColumns={@JoinColumn(name="papel_id")})
	private Set<Papel> papeis;
	
	public Usuario(String login) {
		this.login = login;
		this.papeis = new HashSet<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<Papel> getPapeis() {
		return Collections.unmodifiableSet(papeis);
	}
	
	public int getQuantidadePapeis() {
		return papeis.size();
	}
	
	public boolean possui(Papel papel) {
		return this.papeis.contains(papel);
	}

	public void adiciona(Papel papel) {
		this.papeis.add(papel);
	}
	
	public void remove(Papel papel) {
		this.papeis.remove(papel);
	}
	
	public boolean isNew() {
		return this.id == null;
	}
	
}
