package co.edu.unbosque.taller.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.service.ExternalHTTPRequestHandler;


@RestController
@RequestMapping("/aoe")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
public class AgeOfEmpiresController {

	public AgeOfEmpiresController() {
	}


	@GetMapping("/civilizations/getall")
	ResponseEntity<List<CivilizationDTO>> getAllCivilizations() {
		List<CivilizationDTO> civs = ExternalHTTPRequestHandler.getAllCivilizations();
		if (civs.isEmpty()) {
			return new ResponseEntity<>(civs, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(civs, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/civilizations/getbyid/{id}")
	ResponseEntity<CivilizationDTO> getCivilizationById(@PathVariable String id) {
		return new ResponseEntity<>(ExternalHTTPRequestHandler.getCivilizationById(id), HttpStatus.ACCEPTED);
	}


	@GetMapping("/units/getall")
	ResponseEntity<List<UnitDTO>> getAllUnits() {
		List<UnitDTO> units = ExternalHTTPRequestHandler.getAllUnits();
		if (units.isEmpty()) {
			return new ResponseEntity<>(units, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(units, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/units/getbyid/{id}")
	ResponseEntity<UnitDTO> getUnitById(@PathVariable String id) {
		return new ResponseEntity<>(ExternalHTTPRequestHandler.getUnitById(id), HttpStatus.ACCEPTED);
	}

}
