package br.pro.delfino.dscatalog.services.exceptions;

public class ViolacaoIntegridadeDadosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ViolacaoIntegridadeDadosException(String mensagem) {
		super(mensagem);
	}
}
