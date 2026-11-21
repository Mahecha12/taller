package co.edu.unbosque.taller.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


import com.google.gson.GsonBuilder;

import co.edu.unbosque.taller.dto.ChisteDTO;



public class ExternalHTTPRequestHandler {
	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	
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

}
