package co.edu.unbosque.taller.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.ChisteDTO;
import co.edu.unbosque.taller.service.ExternalHTTPRequestHandler;

@RestController
@RequestMapping("/chistes")
@CrossOrigin(origins = "*")
public class ChistesController {
	
	
	@GetMapping("/aleatorio")
	public ResponseEntity<ChisteDTO> obtenerChiste() {
		ChisteDTO chiste = ExternalHTTPRequestHandler.obtenerChisteAleatorio();

		return ResponseEntity.status(chiste.getStatuscode()).body(chiste);
	}

}
