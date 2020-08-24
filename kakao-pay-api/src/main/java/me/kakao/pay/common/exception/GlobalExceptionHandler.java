package me.kakao.pay.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import me.kakao.pay.common.vo.ErrorResponse;
import me.kakao.pay.common.vo.NotValiedResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		logger.error(e);

		ErrorResponse response = new ErrorResponse();
		response.setCode("E9999");
		response.setMessage("This is undefined error, please contact to us.");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

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
	
	@ExceptionHandler(CannotGetJdbcConnectionException.class)
	protected ResponseEntity<ErrorResponse> handlerCannotGetJdbcConnectionException(CannotGetJdbcConnectionException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("E0004");
		response.setMessage("DB Connection Error.");
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

	@ExceptionHandler(ExpiredTimeLuckException.class)
	protected ResponseEntity<ErrorResponse> handlerExpiredLuckException(ExpiredTimeLuckException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0004");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FullGrabException.class)
	protected ResponseEntity<ErrorResponse> handlerFullGrabException(FullGrabException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0005");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<ErrorResponse> handlerInvalidLuckException(InvalidTokenException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0006");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotValidMemberException.class)
	protected ResponseEntity<ErrorResponse> handlerNotValidMemberException(NotValidMemberException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0007");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ForbiddenSearchException.class)
	protected ResponseEntity<ErrorResponse> handlerForbiddenSearchException(ForbiddenSearchException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0008");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredDateLuckException.class)
	protected ResponseEntity<ErrorResponse> handlerExpiredDateLuckException(ExpiredDateLuckException e) {
		ErrorResponse response = new ErrorResponse();
		response.setCode("B0009");
		response.setMessage(e.getLocalizedMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
