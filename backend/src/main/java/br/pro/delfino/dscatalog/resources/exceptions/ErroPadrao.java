package br.pro.delfino.dscatalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroPadrao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Instant horario;
	private Integer situacao;
	private String erro;
	private String mensagem;
	private String caminho;
}
	
