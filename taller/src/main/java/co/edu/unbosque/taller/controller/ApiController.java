package co.edu.unbosque.taller.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.ChisteDTO;
import co.edu.unbosque.taller.service.ExternalHTTPRequestHandler;

@RestController
@RequestMapping("/api/chistes")
@CrossOrigin(origins = "*")
public class ApiController {
	@GetMapping("/aleatorio")
	public ResponseEntity<ChisteDTO> obtenerChiste() {
		String url = "https://v2.jokeapi.dev/joke/Any?lang=es";
		
		ChisteDTO chiste = ExternalHTTPRequestHandler.doGetAndConvertToDto(url);
		
		return ResponseEntity.status(chiste.getStatuscode()).body(chiste);
	}

}
