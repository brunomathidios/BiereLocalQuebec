package com.ca.biere.local.quebec.gestion.ws.controleur;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ca.biere.local.quebec.commons.ws.dto.AmertumeDTO;
import com.ca.biere.local.quebec.commons.ws.dto.DTOEnum;
import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;

@CrossOrigin(
	allowedHeaders = "*",
	methods = {RequestMethod.GET},
	origins="*"
)
@RestController
@RequestMapping(value = "${application.domain}/amertumes")
public class AmertumeControleur {
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity<Response<DTOEnum>> getAmertumes() {
		
		List<AmertumeDTO> result = 
				Stream.of(EnumAmertume.values()).map(EnumAmertume::getAmertumeDTO)
				.collect(Collectors.toList());
		
		DTOEnum<AmertumeDTO> dto = new DTOEnum<AmertumeDTO>(result);
		
		return Response.status(HttpStatus.OK.value(), DTOEnum.class)
				.message(HttpStatus.OK.getReasonPhrase())
				.data(dto)
				.build();
	}

}
