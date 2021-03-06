package urban.sandbox.jpa.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "Papel")
public class Papel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@ManyToMany(mappedBy = "papeis")
	private Set<Usuario> usuarios;
	
	public Papel() {
		
	}

	public Papel(String descricao) {
		this.descricao = descricao;
	}

	private Papel(PapelBuilder builder) {
		this.descricao = builder.descricao;
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

	public Set<Usuario> getUsuarios() {
		return Collections.unmodifiableSet(usuarios);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
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
		Papel other = (Papel) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

	public static class PapelBuilder {

		private String descricao;

		public PapelBuilder() {
			this.descricao = null;
		}

		public PapelBuilder comDescricao(String descricao) {
			this.descricao = descricao;
			return this;
		}

		public Papel build() {
			return new Papel(this);
		}

	}

}
