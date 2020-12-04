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

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.BiereService;

@CrossOrigin(
	allowedHeaders = "*",
	methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
	origins="*"
)
@RestController
@RequestMapping(value = "${application.domain}")
public class BiereControleur {

	@Autowired
	private BiereService service;
	
	@PostMapping
	public ResponseEntity<Response<Biere>> create(@RequestBody Biere entite) {
		final Biere nouvelleBiere = this.service.insert(entite);
		
		return Response.status(HttpStatus.CREATED.value(), Biere.class)
				.message("Biere ajouté avec succès!")
				.data(nouvelleBiere)
				.build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<Biere>> update(@RequestBody Biere entite, @PathVariable("id") Long id) {
		
		entite.setIdBiere(id);
		final Biere biere = this.service.update(entite);
		
		return Response.status(HttpStatus.OK.value(), Biere.class)
				.message("Biere mis à jour avec succès!")
				.data(biere)
				.build();
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<Biere>> findById(@PathVariable("id") Long id) {
		final Biere result = this.service.findById(id);
		
		if(Optional.ofNullable(result).isPresent()) {
			
			return Response.status(HttpStatus.OK.value(), Biere.class)
					.message(HttpStatus.OK.getReasonPhrase())
					.data(result)
					.build();
			
		} else {
			return Response.status(HttpStatus.NOT_FOUND.value(), Biere.class)
					.message(HttpStatus.NOT_FOUND.getReasonPhrase()).build();
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Biere>> delete(@PathVariable("id") Long id) {
		
		this.service.deleteById(id);
		
		return Response.status(HttpStatus.OK.value(), Biere.class)
				.message("Biere supprimé avec succès!")
				.build();
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity<Response<Page>> listerBiere(
			@RequestParam(name = "filter", required = false, defaultValue = "{}") String filter, Pageable page) {
		
		BiereFilter biereFilter = JsonUtils.convertFromJson(filter, BiereFilter.class);
		
		Page<Biere> result = this.service.listerBiere(biereFilter, page);
		
		return Response.status(HttpStatus.OK.value(), Page.class)
				.message(HttpStatus.OK.getReasonPhrase())
				.data(result)
				.build();
	}
}
