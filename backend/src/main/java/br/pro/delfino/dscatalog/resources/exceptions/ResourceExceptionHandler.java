package br.pro.delfino.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@ControllerAdvice
public class ResourceExceptionHandler {
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
	
	@ExceptionHandler(ViolacaoIntegridadeDadosException.class)
	public ResponseEntity<ErroPadrao> violacaoIntegridadeDados(
		ViolacaoIntegridadeDadosException excecao, 
		HttpServletRequest requisicao){
		
		ErroPadrao erro = new ErroPadrao();
		erro.setHorario(Instant.now());
		erro.setSituacao(HttpStatus.BAD_REQUEST.value());
		erro.setErro("Violação de integridade de dados");
		erro.setMensagem(excecao.getMessage());
		erro.setCaminho(requisicao.getRequestURI());
		
		return ResponseEntity.badRequest().body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> argumentoMetodoNaoValido(
		MethodArgumentNotValidException excecao, 
		HttpServletRequest requisicao){
		
		ErroPadrao erro = new ErroPadrao();
		erro.setHorario(Instant.now());
		erro.setSituacao(HttpStatus.UNPROCESSABLE_ENTITY.value());
		erro.setErro("Erro de validação");
		erro.setMensagem(excecao.getMessage());
		erro.setCaminho(requisicao.getRequestURI());
		
		return ResponseEntity.unprocessableEntity().body(erro);
	}
}
