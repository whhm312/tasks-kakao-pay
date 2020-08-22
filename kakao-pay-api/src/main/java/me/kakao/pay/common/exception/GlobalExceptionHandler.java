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

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicatedTokenException.class)
	protected ResponseEntity<ErrorResponse> handleResourceNoContentException(DuplicatedTokenException e) {
		ErrorResponse reponse = new ErrorResponse();
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());
		return new ResponseEntity<>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorResponse reponse = new ErrorResponse();
		List<String> errors = new ArrayList<>();

		BindingResult bindingResult = e.getBindingResult();
		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append("[");
			builder.append(fieldError.getField());
			builder.append("] ");
			builder.append(fieldError.getDefaultMessage());
			errors.add(builder.toString());
			builder.setLength(0);
		}
		reponse.setErrors(errors);

		return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);
	}
}
