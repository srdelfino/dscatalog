package br.pro.delfino.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ErroDeValidacao extends ErroPadrao {
	private static final long serialVersionUID = 1L;

	@Getter
	private List<MensagemDeCampo> erros = new ArrayList<>();
	
	public void adicionarErro(String nomeDoCampo, String mensagem) {
		MensagemDeCampo erro = new MensagemDeCampo(nomeDoCampo, mensagem);
		erros.add(erro);
	}
}
