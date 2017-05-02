package urban.sandbox.jpa.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "login", nullable = false)
	private String login;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "usuario_papel", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "papel_id") })
	private Set<Papel> papeis;
		
	public Usuario() {
		
	}

	public Usuario(String login) {
		this.login = login;
		this.papeis = new HashSet<>();
	}
	
	private Usuario(UsuarioBuilder builder) {
		this.login = builder.login;
		this.papeis = builder.papeis;
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
		if(!possui(papel)) {
			this.papeis.add(papel);
		}
	}

	public void remove(Papel papel) {
		if(possui(papel)) {
			this.papeis.remove(papel);
		}
	}

	public boolean isNew() {
		return this.id == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((papeis == null) ? 0 : papeis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (papeis == null) {
			if (other.papeis != null)
				return false;
		} else if (!papeis.equals(other.papeis))
			return false;
		return true;
	}
	
	public static class UsuarioBuilder {

		private String login;
		
		private Set<Papel> papeis;
		
		public UsuarioBuilder() {
			this.login = null;
			this.papeis = new HashSet<>();
		}
		
		public UsuarioBuilder comLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UsuarioBuilder adicionaPapel(Papel papel) {
			this.papeis.add(papel);
			return this;
		}
			
		public Usuario build() {
			return new Usuario(this);
		}
	}

}
