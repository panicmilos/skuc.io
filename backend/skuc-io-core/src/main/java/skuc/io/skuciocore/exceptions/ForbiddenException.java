package skuc.io.skuciocore.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HandleableException {
	
	private static final long serialVersionUID = 1L;

	public ForbiddenException() {
		super(HttpStatus.FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(message, HttpStatus.FORBIDDEN);
	}
	
	public ForbiddenException(String message, Object payload) {
		super(message, payload, HttpStatus.FORBIDDEN);
	}
}