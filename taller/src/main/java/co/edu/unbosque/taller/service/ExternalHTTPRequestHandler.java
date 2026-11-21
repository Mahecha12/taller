package co.edu.unbosque.taller.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class ExternalHTTPRequestHandler {

	private static final HttpClient CLIENTE_HTTP = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static final String URL_PAISES = "https://api.countrystatecity.in/v1/countries";

	private static final String API_KEY = System.getenv("COUNTRYSTATECITY_API_KEY");

	public static String obtenerJsonPaises() {
		if (API_KEY == null || API_KEY.isBlank()) {
			throw new IllegalStateException("Falta configurar la variable de entorno COUNTRYSTATECITY_API_KEY");
		}

		HttpRequest peticion = HttpRequest.newBuilder().GET().uri(URI.create(URL_PAISES))
				.header("Accept", "application/json").header("X-CSCAPI-KEY", API_KEY).build();

		try {
			HttpResponse<String> respuesta = CLIENTE_HTTP.send(peticion, HttpResponse.BodyHandlers.ofString());

			if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
				throw new RuntimeException("Error al consultar la API. Código HTTP: " + respuesta.statusCode()
						+ " - Respuesta: " + respuesta.body());
			}

			return respuesta.body();
		} catch (IOException e) {
			throw new RuntimeException("No se pudo conectar con la API de países", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("La petición hacia la API fue interrumpida", e);
		}
	}

	
}
