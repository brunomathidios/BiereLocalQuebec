package com.ca.biere.local.quebec.gestion.ws.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.TypeBiereService;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class TypeBiereServiceTest {
	
	private static final String NOM_INSERT_BIERE_TEST = "Bière Test Nom";
	
	@Autowired
	private TypeBiereService service;
	
	@Test(expected = SaveEntiteException.class)
	public void insertEntiteAvecIdDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setIdTypeBiere(1L);
		
		this.service.insert(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void insertEntiteSansNomDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setDescription("test");
		
		this.service.insert(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void insertEntiteAvecNomPlus200CaracteresDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setNom("teste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste teste");
		entite.setDescription("test");
		
		this.service.insert(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void insertEntiteAvecDescriptionPlus4000CaracteresDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setNom("nom test");
		entite.setDescription("description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres ");
		
		this.service.insert(entite);
	}
	
	@Test(expected = SaveEntiteException.class)
	public void updateEntiteSansIdDoitRetournerException() {
		this.service.update(new TypeBiere());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void updateEntiteAvecIdInexistentDoitRetournerException() {
		TypeBiere entite = new TypeBiere();
		
		entite.setIdTypeBiere(999991L);
		entite.setNom("nom test");
		entite.setDescription("description test");
		
		this.service.update(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void updateEntiteSansNomDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setIdTypeBiere(1L);
		entite.setDescription("test");
		
		this.service.update(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void updateEntiteAvecNomPlus200CaracteresDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setIdTypeBiere(1L);
		entite.setNom("teste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste teste");
		
		this.service.update(entite);
	}
	
	@Test(expected = ValidationException.class)
	public void updateEntiteAvecDescriptionPlus4000CaracteresDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setIdTypeBiere(1L);
		entite.setNom("nom test");
		entite.setDescription("description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres ");
		
		this.service.update(entite);
	}
	
	@Test
	public void updateEntite() {
		TypeBiere entite = this.service.findById(1L);
		
		String nouvelleDescription = "nouvelle description";
		entite.setDescription(nouvelleDescription);
		
		entite = this.service.update(entite);
		
		Assert.assertEquals(nouvelleDescription, entite.getDescription());
		Assert.assertNotNull("Date mis a jour doit être rempli", entite.getDateMisAJour());
		
	}
	
	@Test
	public void findByIdExitant() {
		Assert.assertNotNull(this.service.findById(1L));
	}
	
	@Test
	public void findByIdInexitant() {
		Assert.assertNull(this.service.findById(99999L));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void effacerEntiteIdNonExistent() { 
		this.service.deleteById(999999L);
	}
	
	/** não pode inserir e nem atualizar um registro com nome duplicado **/
	
	@Test(expected = DataIntegrityViolationException.class)
	public void insertEntiteAvecNomExistentDoitRetournerException() {
		TypeBiere entite = new TypeBiere();
		entite.setNom(NOM_INSERT_BIERE_TEST);
		entite.setDescription("test description");
		
		this.service.insert(entite);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void misAJourEntiteAvecNomExistentDoitRetournerException() {
		
		TypeBiere entite = new TypeBiere();
		entite.setIdTypeBiere(2L);
		entite.setNom(NOM_INSERT_BIERE_TEST);
		entite.setDescription("test description");
		
		this.service.update(entite);
	}
	
	@Test
	public void rechercheAvecFilterNomDoitRetournerPageAvecContent() {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setNom("Ipa");
		
		Pageable page = PageRequest.of(0, 5);
		
		Page<TypeBiere> result = this.service.listerTypeBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getContent().size() >= 1);
	}
	
	@Test
	public void rechercheAvecFilterDescriptionDoitRetournerPageAvecContent() {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setDescription("belle");
		
		Pageable page = PageRequest.of(1, 5);
		
		Page<TypeBiere> result = this.service.listerTypeBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getContent().size() >= 1); 
	}
	
	@Test
	public void rechercheAvecFilterNomEDescriptionDoitRetournerPageAvecContent() {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setNom("stout");
		filter.setDescription("belle");
		
		Pageable page = PageRequest.of(0, 5);
		
		Page<TypeBiere> result = this.service.listerTypeBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getContent().size() >= 1);
	}
	
	@Test
	public void rechercheSansFilterDoitRetournerPageAvecContent() {
		Page<TypeBiere> result = this.service.listerTypeBiere(new TypeBiereFilter(), PageRequest.of(0, 5));
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getContent().size() == 5);
	}
	
	@Test
	public void supprimerTypeBiereAssocieeAvecDautresBieresDoitRetornerValidationException() {
		ValidationException exception = Assert.assertThrows(ValidationException.class, 
				() -> {
					Long idTypeBierePossedeBieres = 10L;
					this.service.deleteById(idTypeBierePossedeBieres);
				});
		
		String message = "Interdit de supprimer cet enregistrement, "
				+ "il possède d'autres bières associées à lui.";
		
		Assert.assertEquals(message, exception.getErrors().get(0));
	}
	
	@Test
	public void listerTypeBiereByNomIpaDoitRetourner3Enregistrements() {
		List<TypeBiere> result = this.service.listerTypeBiereByNom("ipa");

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(3, result.size());
	}
}
