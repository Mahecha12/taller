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

import co.edu.unbosque.taller.dto.ChisteDTO;
import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.exception.ExternalApiException;


public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();


	private static final Gson GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	// ---------------- API de países (Nicolás) ----------------

	private static final HttpClient CLIENTE_HTTP = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static final String URL_PAISES = "https://api.countrystatecity.in/v1/countries";

	private static final String API_KEY = "dfa13167a2555a681a139a0c3be6d1c8bd0ffe8e00407b5e256900000de87a18";

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("//// GET ////");
		String aoe = "https://aoe2api.teamrespawntv.com";
		// System.out.println(doGetAndParse(aoe + "/civilization/1"));

		CivilizationDTO civ = doGetAndConvertToDto(aoe + "/civilization/britons", CivilizationDTO.class);
		System.out.println(civ);

		List<UnitDTO> units = doGetAndConvertToDtoList(aoe + "/units", "units", UnitDTO.class);
		System.out.println(units.size() + " unidades");
	}

	// ---------------- API de chistes (Kevin) ----------------

	public static ChisteDTO doGetAndConvertToDto(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String json = response.body();
		ChisteDTO fact = new GsonBuilder().create().fromJson(json, ChisteDTO.class);
		fact.setStatuscode(response.statusCode());

		return fact;

	}

	// ---------------- API de países (Nicolás) ----------------

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

	// ---------------- API de Age of Empires II (Simón) ----------------

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
		}
		return "Recurso no encontrado: " + url;
	}

}
