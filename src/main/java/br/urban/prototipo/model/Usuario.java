package br.urban.prototipo.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "login")
	private String login;

	@ManyToMany
    @JoinTable(name="usuario_papel", joinColumns={@JoinColumn(name="usuario_id")}, inverseJoinColumns={@JoinColumn(name="papel_id")})
	private Set<Papel> papeis;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuario_id")
	private Papel papel;

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
		return papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		this.papeis = papeis;
	}
	
	public Papel getPapel() {
		return papel;
	}
	
	public void setPapel(Papel papel) {
		this.papel = papel;
	}

}
