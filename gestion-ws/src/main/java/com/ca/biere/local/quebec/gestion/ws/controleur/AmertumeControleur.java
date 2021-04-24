package com.ca.biere.local.quebec.gestion.ws.controleur;

import com.ca.biere.local.quebec.commons.ws.dto.AmertumeDTO;
import com.ca.biere.local.quebec.commons.ws.dto.DTOEnum;
import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "${application.domain}/amertumes")
public class AmertumeControleur {
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity<Response<DTOEnum>> getAmertumes() {
		
		List<AmertumeDTO> result = 
				Stream.of(EnumAmertume.values()).map(EnumAmertume::getAmertumeDTO)
				.collect(Collectors.toList());
		
		DTOEnum<AmertumeDTO> dto = new DTOEnum<>(result);
		
		return Response.status(HttpStatus.OK.value(), DTOEnum.class)
				.message(HttpStatus.OK.getReasonPhrase())
				.data(dto)
				.build();
	}

}
