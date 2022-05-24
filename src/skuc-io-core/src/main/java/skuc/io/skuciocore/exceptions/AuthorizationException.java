package skuc.io.skuciocore.exceptions;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends HandleableException {
	
	private static final long serialVersionUID = 1L;

	public AuthorizationException() {
		super(HttpStatus.UNAUTHORIZED);
	}

	public AuthorizationException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}
	
	public AuthorizationException(String message, Object payload) {
		super(message, payload, HttpStatus.UNAUTHORIZED);
	}
}