package co.edu.unbosque.taller.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.PaisDTO;
import co.edu.unbosque.taller.service.ExternalHTTPRequestHandler;

@RestController
@RequestMapping("/paises")
@CrossOrigin(origins = "*")
public class PaisController {

	@GetMapping("/getall")
	public ResponseEntity<List<PaisDTO>> listarPaises() {
		List<PaisDTO> paises = ExternalHTTPRequestHandler.obtenerPaises();
		return ResponseEntity.ok(paises);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<PaisDTO> obtenerPaisPorId(@PathVariable String id) {
		PaisDTO pais = ExternalHTTPRequestHandler.obtenerPaisPorId(id);
		return ResponseEntity.ok(pais);
	}
}
