package co.edu.unbosque.taller.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.edu.unbosque.taller.dto.CivilizationDTO;
import co.edu.unbosque.taller.dto.UnitDTO;
import co.edu.unbosque.taller.exception.ExternalApiException;

@Service
public class AgeOfEmpiresService {

	private static final String BASE_URL = "https://aoe2api.teamrespawntv.com";

	public AgeOfEmpiresService() {
	}

	public List<CivilizationDTO> getAllCivilizations() {
		return ExternalHTTPRequestHandler.doGetAndConvertToDtoList(BASE_URL + "/civilizations", "civilizations",
				CivilizationDTO.class);
	}

	public CivilizationDTO getCivilizationById(String id) {
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(BASE_URL + "/civilization/" + validarId(id),
				CivilizationDTO.class);
	}

	public List<UnitDTO> getAllUnits() {
		return ExternalHTTPRequestHandler.doGetAndConvertToDtoList(BASE_URL + "/units", "units", UnitDTO.class);
	}

	public UnitDTO getUnitById(String id) {
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(BASE_URL + "/unit/" + validarId(id), UnitDTO.class);
	}

	/**
	 * La API acepta un número (1, 34) o un nombre con guiones bajos
	 * (britons, teutonic_knight). Un id con espacios u otros caracteres haría
	 * fallar URI.create con un error 500, así que se responde 400.
	 */
	private String validarId(String id) {
		if (id == null || !id.matches("[A-Za-z0-9_-]+")) {
			throw new ExternalApiException(HttpStatus.BAD_REQUEST,
					"El id debe ser un número o un nombre con letras, guiones o guiones bajos.");
		}
		return id;
	}

}
