package co.edu.unbosque.taller.service;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.edu.unbosque.taller.dto.PaisDTO;

@Service
public class PaisService {

	private final Gson gson = new Gson();

	public List<PaisDTO> listarPaises() {
		String json = ExternalHTTPRequestHandler.obtenerJsonPaises();
		Type listType = new TypeToken<List<PaisDTO>>() {
		}.getType();
		return gson.fromJson(json, listType);
	}
}
