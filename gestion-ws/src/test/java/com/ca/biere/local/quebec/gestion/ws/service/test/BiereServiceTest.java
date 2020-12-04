package com.ca.biere.local.quebec.gestion.ws.service.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.BiereService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class BiereServiceTest {

	@Autowired
	private BiereService service;
	
	@Autowired
	private Environment environment;
	
	@Test
	public void insertEntiteAvecIdDoitRetournerException() {
		
		SaveEntiteException saveException = 
				Assert.assertThrows(SaveEntiteException.class, 
				() -> {
					
					Biere entite = new Biere();
					entite.setIdBiere(1L);
					this.service.insert(entite);
				});
		
		String erreur = this.environment.getRequiredProperty("message.error.methode.insert");
		
		Assert.assertEquals(erreur, saveException.getError());
	}
	
	@Test
	public void insertEntiteSansChampsRempliDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					this.service.insert(new Biere()); 
				});
		
		Assert.assertEquals(6, validationException.getErrors().size());
	}
	
	@Test
	public void insertEntiteNomPlus150CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setNom("nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand");
					entite.setOrigine("teste");
					entite.setTauxAlcool(new BigDecimal(5.5));
					entite.setIbu(45);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setIdTypeBiere(2L);
					this.service.insert(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'nom' doit avoir au maximum 150 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test
	public void insertEntiteOriginePlus200CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setNom("test nom");
					entite.setOrigine("teste nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand");
					entite.setTauxAlcool(new BigDecimal(5.5));
					entite.setIbu(45);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setIdTypeBiere(2L);
					this.service.insert(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'origine' doit avoir au maximum 200 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test 
	public void insertEntiteDescriptionPlus4000CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setNom("test nom");
					entite.setOrigine("teste nom tres grand");
					entite.setTauxAlcool(new BigDecimal(5.51));
					entite.setIbu(45);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setDescription("description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande teste");
					entite.setIdTypeBiere(2L);
					this.service.insert(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'description' doit avoir au maximum 4000 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test
	public void misAJourEntiteSansIdDoitRetournerException() {
		
		SaveEntiteException saveException = 
				Assert.assertThrows(SaveEntiteException.class, 
				() -> {
					this.service.update(new Biere());
				});
		
		String erreur = this.environment.getRequiredProperty("message.error.methode.update");
		
		Assert.assertEquals(erreur, saveException.getError());
	}
	
	@Test
	public void misAJourEntiteAvecIdInexistentDoitRetournerException() {
		ResourceNotFoundException exception = 
				Assert.assertThrows(ResourceNotFoundException.class, 
				() -> {
					Biere biere = new Biere(); 
					biere.setIdBiere(999999L);
					this.service.update(biere);
				});
		
		String msg = "Entité " + 999999L + " du type Biere n'a pas été trouvée";
		Assert.assertEquals(msg, exception.getErrors().get(0));
	}
	
	@Test
	public void misAJourEntiteChampsSansRempliDoitRetournerException() {
		ValidationException exception = 
				Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere biere = new Biere();
					biere.setIdBiere(1L);
					this.service.update(biere);
				});
		
		Assert.assertEquals(6, exception.getErrors().size());
	}
	
	@Test
	public void misAJourEntiteNomPlus150CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setIdBiere(1L);
					entite.setNom("nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand");
					entite.setOrigine("teste");
					entite.setTauxAlcool(new BigDecimal(5.5));
					entite.setIbu(45);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setIdTypeBiere(2L);
					this.service.update(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'nom' doit avoir au maximum 150 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test
	public void misAJourEntiteOriginePlus200CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setIdBiere(1L);
					entite.setNom("test nom");
					entite.setOrigine("teste nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand nome tres grand nom tres grand");
					entite.setTauxAlcool(new BigDecimal(5.5));
					entite.setIbu(45);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setIdTypeBiere(2L);
					this.service.update(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'origine' doit avoir au maximum 200 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test
	public void misAJourEntiteDescriptionPlus4000CaracteresDoitRetournerException() {
		
		ValidationException validationException = Assert.assertThrows(ValidationException.class, 
				() -> {
					Biere entite = new Biere();
					entite.setIdBiere(1L);
					entite.setNom("test nom");
					entite.setOrigine("teste nom tres grand");
					entite.setTauxAlcool(new BigDecimal(5.5));
					entite.setIbu(120);
					entite.setAmertume(EnumAmertume.MOYENNE);
					entite.setDescription("description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande description d'une biere vraiment grande teste");
					entite.setIdTypeBiere(2L);
					this.service.update(entite);
				});
		
		Assert.assertEquals(1, validationException.getErrors().size());
		Assert.assertEquals("Le champ 'description' doit avoir au maximum 4000 caractères", 
				validationException.getErrors().get(0));
	}
	
	@Test
	public void existsBiereByIdTypeBiere10DoitRetornerTrue() {
		Long idTypeBiere = 10L;
		
		Boolean resultat = this.service.existsBiereByIdTypeBiere(idTypeBiere);
		
		Assert.assertNotNull(resultat);
		Assert.assertTrue(resultat.booleanValue());
	}
	
	@Test
	public void existsBiereByIdTypeBiere99999DoitRetornerFalse() {
		Long idTypeBiere = 99999L;
		
		Boolean resultat = this.service.existsBiereByIdTypeBiere(idTypeBiere);
		
		Assert.assertNotNull(resultat);
		Assert.assertFalse(resultat.booleanValue());
	}
	
	@Test
	public void supprimerEntiteInexistentDoitRetournerException() {
		Long idBiereInexistent = 99999L;
		
		ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class, 
				() -> {
					this.service.deleteById(idBiereInexistent);
				});
		
		Assert.assertFalse(exception.getErrors().isEmpty());
		Assert.assertTrue(exception.getErrors().size() == 1);
		Assert.assertEquals("Entité " + idBiereInexistent + " du type Biere n'a pas été trouvée", 
				exception.getErrors().get(0));
	}
	
	@Test
	public void filtrer10EnregistrementsDoitRetourner8() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Biere> result = this.service.listerBiere(new BiereFilter(), page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 8);
	}
	
	@Test
	public void filtrerParNom() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setNom("ipa");
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 2);
	}
	
	@Test
	public void filtrerParOrigine() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setOrigine("canada");
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 4);
	}
	
	@Test
	public void filtrerParTauxAlcoolGreaterThan6Pourcent() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setTauxAlcoolStart(new BigDecimal(6));
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 4);
	}
	
	@Test
	public void filtrerParTauxAlcoolLessThan5Pourcent() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setTauxAlcoolEnd(new BigDecimal(5));
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 3);
	}
	
	@Test
	public void filtrerParTauxAlcoolEntre5Et7Pourcent() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setTauxAlcoolStart(new BigDecimal(5));
		filter.setTauxAlcoolEnd(new BigDecimal(7));
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 4);
	}
	
	@Test
	public void filtrerParIbuGreaterThan40() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setIbuStart(40);
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 3);
	}
	
	@Test
	public void filtrerParIbuLessThan30() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setIbuEnd(30);
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 3);
	}
	
	@Test
	public void filtrerParIbuEntre35Et50() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setIbuStart(35);
		filter.setIbuEnd(50);
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 3);
	}
	
	@Test
	public void filtrerParAmertume() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setAmertume(EnumAmertume.MOYENNE);
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 3);
	}
	
	@Test
	public void filtrerParDescription() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setDescription("the best");
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 4);
	}
	
	@Test
	public void filtrerParTypeBiere() {
		PageRequest page = PageRequest.of(0, 5);
		
		BiereFilter filter = new BiereFilter();
		filter.setIdTypeBiere(2L);
		
		Page<Biere> result = this.service.listerBiere(filter, page);
		
		Assert.assertNotNull(result);
		Assert.assertFalse(result.getContent().isEmpty());
		Assert.assertTrue(result.getNumberOfElements() == 2);
	}
}
