package skuc.io.skucioapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import skuc.io.skuciocore.exceptions.ErrorObject;
import skuc.io.skuciocore.exceptions.HandleableException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {HandleableException.class})
	public ResponseEntity<ErrorObject> handleException(HandleableException exception) {
		var errorObject = new ErrorObject(exception.getMessage(), exception.getPayload(), exception.getCode());
		
	return new ResponseEntity<>(errorObject, exception.getCode());
	}
}