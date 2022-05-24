package skuc.io.skuciocore.exceptions;

import org.springframework.http.HttpStatus;

public class MissingEntityException extends HandleableException {
	
	private static final long serialVersionUID = 1L;

	public MissingEntityException() {
		super(HttpStatus.NOT_FOUND);
	}

	public MissingEntityException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}
	
	public MissingEntityException(String message, Object payload) {
		super(message, payload, HttpStatus.NOT_FOUND);
	}
}