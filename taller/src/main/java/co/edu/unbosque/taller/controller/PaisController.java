package co.edu.unbosque.taller.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.PaisDTO;
import co.edu.unbosque.taller.service.PaisService;

@RestController
@RequestMapping("/paises")
@CrossOrigin(origins = "*")
public class PaisController {

	private final PaisService paisService;

	public PaisController(PaisService paisService) {
		this.paisService = paisService;
	}

	@GetMapping
	public ResponseEntity<List<PaisDTO>> listarPaises() {
		List<PaisDTO> paises = paisService.listarPaises();
		return ResponseEntity.ok(paises);
	}
}
