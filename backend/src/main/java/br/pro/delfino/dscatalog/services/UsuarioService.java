package br.pro.delfino.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pro.delfino.dscatalog.dto.UsuarioDTO;
import br.pro.delfino.dscatalog.dto.UsuarioEdicaoDTO;
import br.pro.delfino.dscatalog.dto.UsuarioInsercaoDTO;
import br.pro.delfino.dscatalog.entities.Perfil;
import br.pro.delfino.dscatalog.entities.Usuario;
import br.pro.delfino.dscatalog.repositories.PerfilRepository;
import br.pro.delfino.dscatalog.repositories.UsuarioRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	@Autowired
	private PerfilRepository perfilRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder codificador;

	@Transactional(readOnly = true)
	public List<UsuarioDTO> buscarTudo() {
		List<Usuario> lista = usuarioRepositorio.findAll();

		List<UsuarioDTO> listaDTO = lista.stream().map(entidade -> new UsuarioDTO(entidade))
				.collect(Collectors.toList());

		return listaDTO;
	}
	
	@Transactional(readOnly = true)
	public Page<UsuarioDTO> buscarTudo(Pageable paginacao) {
		Page<Usuario> lista = usuarioRepositorio.findAll(paginacao);
		
		Page<UsuarioDTO> listaDTO = lista
			.map(entidade -> new UsuarioDTO(entidade));
			
		return listaDTO;
	}

	@Transactional(readOnly = true)
	public UsuarioDTO buscarPorId(Long id) {
		Optional<Usuario> opcional = usuarioRepositorio.findById(id);

		Usuario entidade = opcional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));

		UsuarioDTO dto = new UsuarioDTO(entidade);
		return dto;
	}

	@Transactional
	public UsuarioDTO inserir(UsuarioInsercaoDTO dto) {
		Usuario entidade = new Usuario();

		converterDTOParaEntidade(dto, entidade);
		entidade.setSenha(codificador.encode(dto.getSenha()));
		
		usuarioRepositorio.save(entidade);

		UsuarioDTO dtoRetorno = new UsuarioDTO(entidade);
		return dtoRetorno;
	}

	@Transactional
	public UsuarioDTO editar(Long id, UsuarioEdicaoDTO dto) {
		try {
			Usuario entidade = usuarioRepositorio.getOne(id);
			converterDTOParaEntidade(dto, entidade);

			usuarioRepositorio.save(entidade);

			UsuarioDTO dtoRetorno = new UsuarioDTO (entidade);
			return dtoRetorno;
		} catch (EntityNotFoundException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		}
	}

	@Transactional
	public void excluir(Long id) {
		try {
			usuarioRepositorio.deleteById(id);
			usuarioRepositorio.flush();
		} catch (EmptyResultDataAccessException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		} catch (DataIntegrityViolationException excecao) {
			throw new ViolacaoIntegridadeDadosException("Violação da integridade referencial");
		}
	}
	
	private void converterDTOParaEntidade(UsuarioDTO dto, Usuario entidade) {
		entidade.setEmail(dto.getEmail());
		entidade.setNome(dto.getNome());
		entidade.setSobrenome(dto.getSobrenome());
		
		entidade.getPerfis().clear();
		
		dto.getPerfis().forEach(perfilDTO -> {
			Perfil perfilEntidade = perfilRepositorio.getOne(perfilDTO.getId());
			entidade.getPerfis().add(perfilEntidade);
		});
	}
}
