package co.edu.unbosque.taller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.service.AgeOfEmpiresService;

/**
 * Endpoints que exponen al front la información de la API de Age of Empires
 * II. Los {id} aceptan un número (1, 34) o un nombre (britons, teutonic_knight).
 */
@RestController
@RequestMapping("/aoe")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
public class AgeOfEmpiresController {

	@Autowired
	private AgeOfEmpiresService aoeServ;

	public AgeOfEmpiresController() {
	}


	@GetMapping("/civilizations/getall")
	ResponseEntity<List<CivilizationDTO>> getAllCivilizations() {
		List<CivilizationDTO> civs = aoeServ.getAllCivilizations();
		if (civs.isEmpty()) {
			return new ResponseEntity<>(civs, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(civs, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/civilizations/getbyid/{id}")
	ResponseEntity<CivilizationDTO> getCivilizationById(@PathVariable String id) {
		return new ResponseEntity<>(aoeServ.getCivilizationById(id), HttpStatus.ACCEPTED);
	}


	@GetMapping("/units/getall")
	ResponseEntity<List<UnitDTO>> getAllUnits() {
		List<UnitDTO> units = aoeServ.getAllUnits();
		if (units.isEmpty()) {
			return new ResponseEntity<>(units, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(units, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/units/getbyid/{id}")
	ResponseEntity<UnitDTO> getUnitById(@PathVariable String id) {
		return new ResponseEntity<>(aoeServ.getUnitById(id), HttpStatus.ACCEPTED);
	}

}
