package me.kakao.pay.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import me.kakao.pay.common.vo.ErrorResponse;
import me.kakao.pay.common.vo.NotValiedResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FailedCreateTokenException.class)
	protected ResponseEntity<ErrorResponse> handleDuplicatedTokenException(FailedCreateTokenException e) {
		ErrorResponse reponse = new ErrorResponse();
		reponse.setCode("E0001");
		reponse.setMessage(e.getMessage());
		return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		NotValiedResponse reponse = new NotValiedResponse();
		reponse.setCode("B0001");
		reponse.setMessage("Not valid parameters' values.");
		List<String> errors = new ArrayList<>();

		BindingResult bindingResult = e.getBindingResult();
		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append("[");
			builder.append(fieldError.getField());
			builder.append("] ");
			builder.append(fieldError.getDefaultMessage());
			builder.append(" {");
			builder.append(fieldError.getRejectedValue());
			builder.append("}");
			errors.add(builder.toString());
			builder.setLength(0);
		}
		reponse.setDetails(errors);

		return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);
	}
}
