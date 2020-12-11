package com.ca.biere.local.quebec.gestion.ws.controleur;

import com.ca.biere.local.quebec.commons.ws.entite.PrixBiere;
import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.filter.PrixBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.PrixBiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        origins="*"
)
@RestController
@RequestMapping(value = "${application.domain}/prix")
public class PrixBiereControleur {

    @Autowired
    private PrixBiereService service;

    @PostMapping
    public ResponseEntity<Response<PrixBiere>> create(@RequestBody PrixBiere entite) {
        final PrixBiere nouvellePrixBiere = this.service.insert(entite);

        return Response.status(HttpStatus.CREATED.value(), PrixBiere.class)
                .message("Prix de la bière ajouté avec succès!")
                .data(nouvellePrixBiere)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response<PrixBiere>> update(@RequestBody PrixBiere entite, @PathVariable( "id" ) Long id) {

        entite.setIdPrix(id);
        final PrixBiere prixBiere = this.service.update(entite);

        return Response.status(HttpStatus.OK.value(), PrixBiere.class)
                .message("Prix de la bière mis à jour avec succès!")
                .data(prixBiere)
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<PrixBiere>> findById(@PathVariable("id") Long id) {
        PrixBiere result = this.service.findById(id);

        if(Optional.ofNullable(result).isPresent()) {

            return Response.status(HttpStatus.OK.value(), PrixBiere.class)
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
                    .build();

        } else {
            return Response.status(HttpStatus.NOT_FOUND.value(), PrixBiere.class)
                    .message(HttpStatus.NOT_FOUND.getReasonPhrase()).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<PrixBiere>> delete(@PathVariable("id") Long id) {

        this.service.deleteById(id);

        return Response.status(HttpStatus.OK.value(), PrixBiere.class)
                .message("Prix de la bière effacé avec succès!")
                .build();
    }

    @GetMapping
    public ResponseEntity<Response<Page>> listerPrixBiere(
            @RequestParam(name = "filter", required = false, defaultValue = "{}") String filter, Pageable page) {

        PrixBiereFilter prixBiereFilter = JsonUtils.convertFromJson(filter, PrixBiereFilter.class);

        Page<PrixBiere> result = this.service.listerPrix(prixBiereFilter, page);

        return Response.status(HttpStatus.OK.value(), Page.class)
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

}
