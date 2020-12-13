package com.ca.biere.local.quebec.gestion.ws.controleur;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.BiereService;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Response<Biere>> create(@RequestPart("biere") String entite,
												  @RequestPart(name = "photo", required = false) MultipartFile photo) throws IOException {

		Biere biere = JsonUtils.convertFromJson(entite, Biere.class);

		if(photo != null) {
			biere.setPhoto(photo.getBytes());
		}
		final Biere nouvelleBiere = this.service.insert(biere);
		
		return Response.status(HttpStatus.CREATED.value(), Biere.class)
				.message("Biere ajouté avec succès!")
				.data(nouvelleBiere)
				.build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<Biere>> update(@RequestPart("biere") String entite,
                                                  @RequestPart(name = "photo", required = false) MultipartFile photo,
                                                  @PathVariable("id") Long id) throws IOException  {

        Biere biere = JsonUtils.convertFromJson(entite, Biere.class);

        if(photo != null) {
            biere.setPhoto(photo.getBytes());
        }

        biere.setIdBiere(id);
		final Biere biereMisAJour = this.service.update(biere);
		
		return Response.status(HttpStatus.OK.value(), Biere.class)
				.message("Biere mis à jour avec succès!")
				.data(biereMisAJour)
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

		result.getContent().stream().forEach(biere -> biere.setPhoto(null));
		
		return Response.status(HttpStatus.OK.value(), Page.class)
				.message(HttpStatus.OK.getReasonPhrase())
				.data(result)
				.build();
	}

	@GetMapping(path = "/nom")
	public ResponseEntity<Response<Biere>> listerBiereByNom(@RequestParam("nom") String nom) {
		List<Biere> result = this.service.listerBiereByNom(nom);

		return Response.status(HttpStatus.OK.value(), Biere.class)
				.data(result)
				.build();
	}
}
