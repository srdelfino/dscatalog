package br.pro.delfino.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;

@ControllerAdvice
public class RosourceExceptionHandler {
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(
		EntidadeNaoEncontradaException excecao, 
		HttpServletRequest requisicao){
		
		ErroPadrao erro = new ErroPadrao();
		erro.setHorario(Instant.now());
		erro.setSituacao(HttpStatus.NOT_FOUND.value());
		erro.setErro("Recurso não encontrado");
		erro.setMensagem(excecao.getMessage());
		erro.setCaminho(requisicao.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
}
