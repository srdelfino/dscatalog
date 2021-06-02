package br.pro.delfino.dscatalog.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Usuario implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String sobrenome;
	
	@Column(unique = true)
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String senha;
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(
		name = "usuario_perfil",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "perfil_id")
	)
	@Getter
	private Set<Perfil> perfis = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> autorizacoes = 
			perfis
				.stream()
				.map(perfil -> new SimpleGrantedAuthority(perfil.getNome())).collect(Collectors.toList());
		return autorizacoes;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
