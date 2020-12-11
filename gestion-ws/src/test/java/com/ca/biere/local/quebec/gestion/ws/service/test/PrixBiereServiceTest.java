package com.ca.biere.local.quebec.gestion.ws.service.test;

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.entite.PrixBiere;
import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;
import com.ca.biere.local.quebec.gestion.ws.filter.PrixBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.service.PrixBiereService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class PrixBiereServiceTest {

    @Autowired
    private PrixBiereService service;

    @Autowired
    private Environment environment;

    /** les test pour insertion **/
    @Test
    public void insertEntiteAvecIdDoitRetournerException() {
        SaveEntiteException exception = Assert.assertThrows(SaveEntiteException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdPrix(1L);
                    this.service.insert(entite);
                });

        String message = this.environment.getRequiredProperty("message.error.methode.insert");
        Assert.assertEquals(message, exception.getError());
    }

    @Test
    public void insertEntiteSansPrixDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdBiere(1L);
                    this.service.insert(entite);
                });

        Assert.assertEquals("Le champ 'prix' est obligatoire", exception.getErrors().get(0));
    }

    @Test
    public void insertEntitePrixPlus1000CarateresDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019");
                    entite.setIdBiere(1L);
                    this.service.insert(entite);
                });

        Assert.assertEquals("Le champ 'prix' doit avoir au maximum 1000 caractères", exception.getErrors().get(0));
    }

    @Test
    public void insertEntiteSansIdBiereDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
                    this.service.insert(entite);
                });

        Assert.assertEquals("Le champ 'bière' est obligatoire", exception.getErrors().get(0));
    }

    @Test
    public void insertEntiteAvecIdBiereInexistentDoitRetournerException() {
        Long idBiereInexistent = 99999L;
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
                    entite.setIdBiere(idBiereInexistent);
                    this.service.insert(entite);
                });
        String message = "Entité " + idBiereInexistent + " du type Bière n'a pas été trouvée";
        Assert.assertEquals(message, exception.getErrors().get(0));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertEntiteAvecPrixEnDoubleDoirRetournerException() {
        PrixBiere entite = new PrixBiere();
        entite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
        entite.setIdBiere(1L);
        this.service.insert(entite);

        PrixBiere nouvelleEntite = new PrixBiere();
        nouvelleEntite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
        nouvelleEntite.setIdBiere(1L);
        this.service.insert(nouvelleEntite);
    }
    /** fin **/

    /** les test pour mis a jour **/

    @Test
    public void misAJourEntiteSansIdDoitRetournerException() {
        SaveEntiteException exception = Assert.assertThrows(SaveEntiteException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setPrix("Médaille d'or 2020");
                    this.service.update(entite);
                });

        String message = this.environment.getRequiredProperty("message.error.methode.update");
        Assert.assertEquals(message, exception.getError());
    }

    @Test
    public void misAJourEntiteSansPrixDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdPrix(1L);
                    entite.setIdBiere(1L);
                    this.service.update(entite);
                });

        Assert.assertEquals("Le champ 'prix' est obligatoire", exception.getErrors().get(0));
    }

    @Test
    public void misAJourEntitePrixPlus1000CarateresDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdPrix(1L);
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019");
                    entite.setIdBiere(1L);
                    this.service.update(entite);
                });

        Assert.assertEquals("Le champ 'prix' doit avoir au maximum 1000 caractères", exception.getErrors().get(0));
    }

    @Test
    public void misAJourEntiteSansIdBiereDoitRetournerException() {
        ValidationException exception = Assert.assertThrows(ValidationException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdPrix(1L);
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
                    this.service.update(entite);
                });

        Assert.assertEquals("Le champ 'bière' est obligatoire", exception.getErrors().get(0));
    }

    @Test
    public void misAJourEntiteAvecIdBiereInexistentDoitRetournerException() {
        Long idBiereInexistent = 99999L;
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class,
                () -> {
                    PrixBiere entite = new PrixBiere();
                    entite.setIdPrix(1L);
                    entite.setPrix("médaille d'or de la meilleure bière du Canada 2019");
                    entite.setIdBiere(idBiereInexistent);
                    this.service.update(entite);
                });
        String message = "Entité " + idBiereInexistent + " du type Bière n'a pas été trouvée";
        Assert.assertEquals(message, exception.getErrors().get(0));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void misAJourEntiteAvecPrixEnDoubleDoirRetournerException() {
        PrixBiere entite = new PrixBiere();
        entite.setPrix("médaille d'argent Canada 2019");
        entite.setIdBiere(1L);
        PrixBiere nouvelleEntite = this.service.insert(entite);

        nouvelleEntite.setPrix("Médaille dor 2019");
        this.service.update(nouvelleEntite);
    }

    @Test
    public void findEntiteAvecIdInexistentDoitRetournerNull() {
        Assert.assertNull(this.service.findById(9999L));
    }

    @Test
    public void supprimerEntiteAvecIdNonExistentDoitRetournerException() {
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class,
                () -> {
                    this.service.deleteById(99999L);
                });
        String message = "Entité " + 99999L + " du type Prix n'a pas été trouvée";
        Assert.assertEquals(message, exception.getErrors().get(0));
    }

    @Test
    public void listerToutesLesEntites() {
        PrixBiereFilter filter = new PrixBiereFilter();
        Pageable page = PageRequest.of(0, 5);

        Page<PrixBiere> result = this.service.listerPrix(filter, page);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.getContent().isEmpty());
        Assert.assertTrue(result.getContent().size() == 5);
        Assert.assertTrue(result.getTotalElements() == 11);
    }

    @Test
    public void listerLesEntitesByIdBiere() {
        PrixBiereFilter filter = new PrixBiereFilter();
        filter.setIdBiere(3L);

        Pageable page = PageRequest.of(0, 5);

        Page<PrixBiere> result = this.service.listerPrix(filter, page);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.getContent().isEmpty());
        Assert.assertTrue(result.getContent().size() == 3);
    }

    @Test
    public void listerLesEntitesByPrixBiere() {
        PrixBiereFilter filter = new PrixBiereFilter();
        filter.setPrix("médaille");

        Pageable page = PageRequest.of(0, 5);

        Page<PrixBiere> result = this.service.listerPrix(filter, page);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.getContent().isEmpty());
        Assert.assertTrue(result.getContent().size() == 5);
        Assert.assertTrue(result.getTotalElements() == 9);
    }
}
