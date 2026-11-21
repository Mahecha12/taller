package co.edu.unbosque.taller.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * Excepción lanzada cuando una API externa no responde o responde con un
 * código de error. Spring la traduce automáticamente al código HTTP indicado.
 */
public class ExternalApiException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public ExternalApiException(HttpStatusCode status, String reason) {
		super(status, reason);
	}

}
