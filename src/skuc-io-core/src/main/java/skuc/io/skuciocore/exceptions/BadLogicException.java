package skuc.io.skuciocore.exceptions;

import org.springframework.http.HttpStatus;

public class BadLogicException extends HandleableException {
	
	private static final long serialVersionUID = 1L;

	public BadLogicException() {
		super(HttpStatus.BAD_REQUEST);
	}

	public BadLogicException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}
	
	public BadLogicException(String message, Object payload) {
		super(message, payload, HttpStatus.BAD_REQUEST);
	}
}