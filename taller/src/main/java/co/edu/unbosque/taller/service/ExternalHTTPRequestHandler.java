package co.edu.unbosque.taller.service;

import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import co.edu.unbosque.taller.dto.ChisteDTO;
import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.PaisDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.exception.ExternalApiException;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	private static final Gson GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();


	public static final String URL_PAISES = "https://api.countrystatecity.in/v1/countries";

	public static final String URL_CHISTES = "https://v2.jokeapi.dev/joke/Any?lang=es";

	public static final String URL_AOE = "https://aoe2api.teamrespawntv.com";

	private static final String API_KEY = "dfa13167a2555a681a139a0c3be6d1c8bd0ffe8e00407b5e256900000de87a18";

	public static List<PaisDTO> obtenerPaises() {
		String json = obtenerJsonPaises(URL_PAISES);
		Type listType = new TypeToken<List<PaisDTO>>() {
		}.getType();
		return new Gson().fromJson(json, listType);
	}

	public static PaisDTO obtenerPaisPorId(String id) {
		String json = obtenerJsonPaises(URL_PAISES + "/" + id);
		return new Gson().fromJson(json, PaisDTO.class);
	}

	public static ChisteDTO obtenerChisteAleatorio() {
		return doGetAndConvertToDto(URL_CHISTES);
	}

	public static List<CivilizationDTO> getAllCivilizations() {
		return doGetAndConvertToDtoList(URL_AOE + "/civilizations", "civilizations", CivilizationDTO.class);
	}

	public static CivilizationDTO getCivilizationById(String id) {
		return doGetAndConvertToDto(URL_AOE + "/civilization/" + id, CivilizationDTO.class);
	}

	public static List<UnitDTO> getAllUnits() {
		return doGetAndConvertToDtoList(URL_AOE + "/units", "units", UnitDTO.class);
	}

	public static UnitDTO getUnitById(String id) {
		return doGetAndConvertToDto(URL_AOE + "/unit/" + id, UnitDTO.class);
	}


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

	public static String obtenerJsonPaises(String url) {
		HttpRequest peticion = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Accept", "application/json").header("X-CSCAPI-KEY", API_KEY).build();
		try {
			HttpResponse<String> respuesta = HTTP_CLIENT.send(peticion, HttpResponse.BodyHandlers.ofString());

			if (respuesta.statusCode() == 404) {
				throw new ExternalApiException(HttpStatus.NOT_FOUND, "País no encontrado: " + url);
			}
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

	public static <T> List<T> doGetAndConvertToDtoList(String url, String key, Class<T> clazz) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = send(request, url);

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

		String json = response.body();
		T dto = null;
		try {
			dto = GSON.fromJson(json, clazz);
		} catch (JsonParseException e) {
			throw new ExternalApiException(HttpStatus.BAD_GATEWAY, "La API externa devolvió un JSON inválido: " + url);
		}

		return dto;

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

}
