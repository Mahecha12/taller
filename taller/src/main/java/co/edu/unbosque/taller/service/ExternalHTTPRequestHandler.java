package co.edu.unbosque.taller.service;

import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.exception.ExternalApiException;

/**
 * Manejador de peticiones HTTP a APIs externas.
 * <p>
 * Replica la estructura y los nombres de métodos del
 * ExternalHTTPRequestHandler de SpringFirstAppJWT. Los métodos
 * doGetAndConvertToDto y doGetAndConvertToDtoList son genéricos para poder
 * reutilizarlos con las tres APIs del taller.
 * </p>
 */
public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	/**
	 * Gson configurado para mapear los campos snake_case de las APIs (army_type,
	 * hit_points...) a atributos camelCase de los DTO (armyType, hitPoints...).
	 */
	private static final Gson GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("//// GET ////");
		String aoe = "https://aoe2api.teamrespawntv.com";
		// System.out.println(doGetAndParse(aoe + "/civilization/1"));

		CivilizationDTO civ = doGetAndConvertToDto(aoe + "/civilization/britons", CivilizationDTO.class);
		System.out.println(civ);

		List<UnitDTO> units = doGetAndConvertToDtoList(aoe + "/units", "units", UnitDTO.class);
		System.out.println(units.size() + " unidades");
	}

	/**
	 * Hace un GET y convierte la respuesta en una lista de DTO. Soporta las tres
	 * formas en que puede responder una API:
	 * <ul>
	 * <li>Objeto que envuelve la lista: {"units": [ ... ]} (se usa {@code key})</li>
	 * <li>Arreglo directo: [ ... ]</li>
	 * <li>Objeto único: { ... } (se devuelve una lista de un elemento)</li>
	 * </ul>
	 *
	 * @param url   URL del recurso.
	 * @param key   Nombre de la propiedad que envuelve la lista (puede ser null).
	 * @param clazz Clase del DTO destino.
	 * @return Lista de DTO.
	 */
	public static <T> List<T> doGetAndConvertToDtoList(String url, String key, Class<T> clazz) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);
		validarStatusCode(response, url);

		String json = response.body();
		List<T> list = new ArrayList<>();
		try {
			JsonElement element = JsonParser.parseString(json);
			if (element.isJsonObject() && key != null && element.getAsJsonObject().has(key)) {
				element = element.getAsJsonObject().get(key);
			}
			if (element.isJsonArray()) {
				for (JsonElement item : element.getAsJsonArray()) {
					list.add(GSON.fromJson(item, clazz));
				}
			} else if (element.isJsonObject()) {
				list.add(GSON.fromJson(element, clazz));
			}
		} catch (JsonParseException | IllegalStateException e) {
			throw new ExternalApiException(HttpStatus.BAD_GATEWAY, "La API externa devolvió un JSON inválido: " + url);
		}

		return list;

	}

	/**
	 * Hace un GET y convierte la respuesta (un objeto JSON) en un DTO.
	 *
	 * @param url   URL del recurso.
	 * @param clazz Clase del DTO destino.
	 * @return DTO con la información de la respuesta.
	 */
	public static <T> T doGetAndConvertToDto(String url, Class<T> clazz) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);
		validarStatusCode(response, url);

		String json = response.body();
		T dto = null;
		try {
			dto = GSON.fromJson(json, clazz);
		} catch (JsonParseException e) {
			throw new ExternalApiException(HttpStatus.BAD_GATEWAY, "La API externa devolvió un JSON inválido: " + url);
		}

		return dto;

	}

	public static String doGetAndParse(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();

		return prettyPrintUsingGson(uglyJson);
	}

	/**
	 * GET con una cabecera adicional (p. ej. una API key: "X-Apikey").
	 */
	public static String doGetWithHeaders(String url, String headerName, String headerValue) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").setHeader(headerName, headerValue).build();

		HttpResponse<String> response = send(request, url);

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();
		return prettyPrintUsingGson(uglyJson);
	}

	public static String doGet(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);

		System.out.println("status code -> " + response.statusCode());

		String uglyJson = response.body();
		return prettyPrintUsingGson(uglyJson);
	}

	public static String doPost(String url, String json) {
		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);
		System.out.println("status code -> " + response.statusCode());

		return prettyPrintUsingGson(response.body());
	}

	public static String doPut(String url, String json) {

		// add json header
		HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create(url)).setHeader("User-Agent", "Java 11 HttpClient Bot")
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);
		System.out.println("status code -> " + response.statusCode());
		System.out.println(response.body());
		return prettyPrintUsingGson(response.body());
	}

	public static String doDelete(String url) {

		HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(url))
				.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);
		System.out.println("status code -> " + response.statusCode());

		return prettyPrintUsingGson(response.body());
	}

	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;
	}

	/**
	 * Envía la petición. Si no hay conexión (API caída, contenedor apagado...)
	 * lanza una excepción en lugar de devolver null, como en Hermes.
	 */
	private static HttpResponse<String> send(HttpRequest request, String url) {
		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		if (response == null) {
			throw new ExternalApiException(HttpStatus.BAD_GATEWAY, "No se pudo conectar con la API externa: " + url);
		}
		return response;
	}

	/**
	 * Traduce los códigos de error de la API externa: 404 se propaga como 404 y
	 * cualquier otro error como 502 (Bad Gateway).
	 */
	private static void validarStatusCode(HttpResponse<String> response, String url) {
		int status = response.statusCode();
		if (status == 404) {
			throw new ExternalApiException(HttpStatus.NOT_FOUND, extraerMensaje(response.body(), url));
		}
		if (status < 200 || status >= 300) {
			throw new ExternalApiException(HttpStatus.BAD_GATEWAY,
					"La API externa respondió con el código " + status + ": " + url);
		}
	}

	/** Usa el campo "message" o "error" de la API si existe (p. ej. "Unit not found"). */
	private static String extraerMensaje(String body, String url) {
		try {
			JsonElement element = JsonParser.parseString(body);
			if (element.isJsonObject()) {
				JsonObject obj = element.getAsJsonObject();
				if (obj.has("message")) {
					return obj.get("message").getAsString();
				}
				if (obj.has("error")) {
					return obj.get("error").getAsString();
				}
			}
		} catch (JsonParseException | IllegalStateException | UnsupportedOperationException e) {
			// cuerpo no JSON: se usa el mensaje por defecto
		}
		return "Recurso no encontrado: " + url;
	}

}
