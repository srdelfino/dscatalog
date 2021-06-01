package br.pro.delfino.dscatalog.resources.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MensagemDeCampo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeDoCampo;
	private String mensagem;
}
