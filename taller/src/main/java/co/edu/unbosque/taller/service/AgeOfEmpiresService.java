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
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(BASE_URL + "/civilization/" + id,
				CivilizationDTO.class);
	}

	public List<UnitDTO> getAllUnits() {
		return ExternalHTTPRequestHandler.doGetAndConvertToDtoList(BASE_URL + "/units", "units", UnitDTO.class);
	}

	public UnitDTO getUnitById(String id) {
		return ExternalHTTPRequestHandler.doGetAndConvertToDto(BASE_URL + "/unit/" + id, UnitDTO.class);
	}

	
	

}
