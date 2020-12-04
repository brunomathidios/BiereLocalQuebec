package com.ca.biere.local.quebec.gestion.ws.controleur;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.TypeBiereService;

@CrossOrigin(
	allowedHeaders = "*",
	methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
	origins="*"
)
@RestController
@RequestMapping(value = "${application.domain}/types")
public class TypeBiereControleur {
	
	@Autowired
	private TypeBiereService service;

	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity<Response<Page>> listerTypesBiere(
			@RequestParam(name = "filter", required = false, defaultValue = "{}") String filter, Pageable page) {
		
		TypeBiereFilter typeBiereFilter = JsonUtils.convertFromJson(filter, TypeBiereFilter.class);
		
		Page<TypeBiere> result = this.service.listerTypeBiere(typeBiereFilter, page);
		
		return Response.status(HttpStatus.OK.value(), Page.class)
				.message(HttpStatus.OK.getReasonPhrase())
				.data(result)
				.build();
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<TypeBiere>> findById(@PathVariable("id") Long id) {
		TypeBiere result = this.service.findById(id);
		
		if(Optional.ofNullable(result).isPresent()) {
			
			return Response.status(HttpStatus.OK.value(), TypeBiere.class)
					.message(HttpStatus.OK.getReasonPhrase())
					.data(result)
					.build();
			
		} else {
			return Response.status(HttpStatus.NOT_FOUND.value(), TypeBiere.class)
					.message(HttpStatus.NOT_FOUND.getReasonPhrase()).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Response<TypeBiere>> create(@RequestBody TypeBiere entite) {
		final TypeBiere nouvelleTypeBiere = this.service.insert(entite);
		
		return Response.status(HttpStatus.CREATED.value(), TypeBiere.class)
				.message("Type Biere ajouté avec succès!")
				.data(nouvelleTypeBiere)
				.build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<TypeBiere>> delete(@PathVariable("id") Long id) {
		
		this.service.deleteById(id);
		
		return Response.status(HttpStatus.OK.value(), TypeBiere.class)
				.message("Type Biere effacé avec succès!")
				.build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<TypeBiere>> update(@RequestBody TypeBiere entite, @PathVariable( "id" ) Long id) {
		
		entite.setIdTypeBiere(id);
		final TypeBiere nouvelleTypeBiere = this.service.update(entite);
		
		return Response.status(HttpStatus.OK.value(), TypeBiere.class)
				.message("Type Biere mis à jour avec succès!")
				.data(nouvelleTypeBiere)
				.build();
	}
	
}
