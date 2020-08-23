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
		ErrorResponse response = new ErrorResponse();
		response.setCode("E0001");
		response.setMessage(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FailedInsertLuckException.class)
	protected ResponseEntity<ErrorResponse> handlerFailedInsertLuckException(FailedInsertLuckException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("E0002");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FailedInsertLuckDetailException.class)
	protected ResponseEntity<ErrorResponse> handlerFailedInsertLuckDetailException(FailedInsertLuckDetailException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("E0003");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<NotValiedResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		NotValiedResponse response = new NotValiedResponse();
		response.setCode("B0001");
		response.setMessage("Not valid parameters' values.");
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
		response.setDetails(errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AlreadyGrabUserException.class)
	protected ResponseEntity<ErrorResponse> handlerAlreadyGrabUserException(AlreadyGrabUserException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0002");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BlesserNotAllowGrabException.class)
	protected ResponseEntity<ErrorResponse> handlerBlesserNotAllowGrabException(BlesserNotAllowGrabException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0003");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredLuckException.class)
	protected ResponseEntity<ErrorResponse> handlerExpiredLuckException(ExpiredLuckException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0004");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FailedCreateTokenException.class)
	protected ResponseEntity<ErrorResponse> handlerFailedCreateTokenException(FailedCreateTokenException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0005");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FullGrabException.class)
	protected ResponseEntity<ErrorResponse> handlerFullGrabException(FullGrabException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0006");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidLuckException.class)
	protected ResponseEntity<ErrorResponse> handlerInvalidLuckException(InvalidLuckException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0007");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotValidMemberException.class)
	protected ResponseEntity<ErrorResponse> handlerNotValidMemberException(NotValidMemberException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0008");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
