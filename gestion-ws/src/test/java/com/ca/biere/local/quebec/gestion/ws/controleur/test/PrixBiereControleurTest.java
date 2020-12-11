package com.ca.biere.local.quebec.gestion.ws.controleur.test;

import com.ca.biere.local.quebec.commons.ws.entite.PrixBiere;
import com.ca.biere.local.quebec.commons.ws.enums.EnumConstraintViolation;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.controleur.base.test.ControleurBaseTest;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;
import com.ca.biere.local.quebec.gestion.ws.filter.PrixBiereFilter;
import com.fasterxml.jackson.databind.JsonNode;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PrixBiereControleurTest extends ControleurBaseTest {

    @Value("${application.domain}/prix")
    private String uriControleur;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Environment environment;

    /** insert **/
    @Test
    public void insertNouvelleEntiteAvecIdRempliDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"idPrix\": 9999,\n" +
                "		\"prix\": \"nom test\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        String msg = this.environment.getRequiredProperty("message.error.methode.insert");

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase(msg)));
    }

    @Test
    public void insertNouvelleEntiteSansPrixRempliDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo("Le champ 'prix' est obligatoire")));
    }

    @Test
    public void insertNouvelleEntiteSansIdBiereDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"Médaille d'or 2020\"\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo("Le champ 'bière' est obligatoire")));
    }

    @Test
    public void insertNouvelleEntiteAvecPrixPlus1000CaracteresDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]",
                        Matchers.equalTo("Le champ 'prix' doit avoir au maximum 1000 caractères")));
    }

    @Test
    public void insertNouvelleEntiteAvecPrixEnDoubleDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"Best beer du Brésil\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]",
                        Matchers.equalTo(EnumConstraintViolation.PRIX_BIERE_UNIQUE.getMessage())));
    }

    @Test
    public void insertNouvelleEntiteAvecIdBiereInexistentDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"Best beer du Brésil\",\n" +
                "	    \"idBiere\": 999999\n" +
                "	}";

        String message = "Entité 999999 du type Bière n'a pas été trouvée";

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo(message)));
    }

    /** mis à jour **/
    @Test
    public void misAJourEntiteSansIdDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"nom test\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + null)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void misAJourEntiteSansPrixRempliDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo("Le champ 'prix' est obligatoire")));
    }

    @Test
    public void misAJourEntiteSansIdBiereDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"Médaille d'or 2020\"\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo("Le champ 'bière' est obligatoire")));
    }

    @Test
    public void misAJourEntiteAvecPrixPlus1000CaracteresDoitRetournerException() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019médaille d'or de la meilleure bière du Canada 2019 médaille d'or de la meilleure bière du Canada 2019\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]",
                        Matchers.equalTo("Le champ 'prix' doit avoir au maximum 1000 caractères")));
    }

    @Test
    public void misAJourEntiteAvecPrixEnDoubleDoitRetournerException() throws Exception {
        Long idPrixBiereAjoute = this.getIdPrixBiereByNouvellePrixAvecIdBiere5();

        String entite =
                "	{\n" +
                "		\"prix\": \"Best beer du Brésil\",\n" +
                "	    \"idBiere\": 5,\n" +
                "		\"dateCreation\": \"2020-11-13T22:57:42.345+00:00\"\n" +
                "	}";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + idPrixBiereAjoute)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]",
                        Matchers.equalTo(EnumConstraintViolation.PRIX_BIERE_UNIQUE.getMessage())));
    }

    private Long getIdPrixBiereByNouvellePrixAvecIdBiere5() throws Exception {
        String entite =
                "	{\n" +
                "		\"prix\": \"Nouvelle Best beer du Brésil\",\n" +
                "	    \"idBiere\": 5\n" +
                "	}";

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post(this.uriControleur)
                .header("Content-Type", this.contentType)
                .content(entite))
                .andReturn();

        JsonNode node = JsonUtils.convertFromJsonToJsonNode(result.getResponse().getContentAsString());

        PrixBiere prixBiereAjoute = JsonUtils
                .converterJsonNodeToObject(node.get("data").iterator().next(), PrixBiere.class);

        return prixBiereAjoute.getIdPrix();
    }

    @Test
    public void misAJourEntiteAvecIdBiereInexistentDoitRetournerException() throws Exception {
        String entiteAvecId =
                "	{\n" +
                "		\"prix\": \"Best beer du Brésil\",\n" +
                "	    \"idBiere\": 999999\n" +
                "	}";

        String message = "Entité 999999 du type Bière n'a pas été trouvée";

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1)
                .header("Content-Type", this.contentType)
                .content(entiteAvecId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo(message)));
    }
    /** fin **/

    @Test
    public void supprimerEntiteAvecIdNonExistentDoitRetournerException() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(this.uriControleur + "/9999999")
                .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("9999999")));
    }

    @Test
    public void findById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(this.uriControleur + "/1")
                .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].idPrix", Is.is(1)));
    }

    @Test
    public void findByIdInexistentDoitRetournerNotFound() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(this.uriControleur + "/999999")
                .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").isNotEmpty());
    }

    @Test
    public void listerParPrixMédaillePage2() throws Exception {
        PrixBiereFilter filter = new PrixBiereFilter();
        filter.setPrix("médaille");

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(this.uriControleur)
                        .param("page", "1")
                        .param("size", "5")
                        .param("filter", JsonUtils.convertToJson(filter))
                        .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(4)));
    }

    @Test
    public void listerParIdBiere2() throws Exception {
        PrixBiereFilter filter = new PrixBiereFilter();
        filter.setIdBiere(2L);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(this.uriControleur)
                        .param("page", "0")
                        .param("size", "5")
                        .param("filter", JsonUtils.convertToJson(filter))
                        .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(3)));
    }

    @Test
    public void listerParPrixEtIdBiere() throws Exception {
        PrixBiereFilter filter = new PrixBiereFilter();
        filter.setPrix("brésil");
        filter.setIdBiere(5L);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(this.uriControleur)
                        .param("page", "0")
                        .param("size", "5")
                        .param("filter", JsonUtils.convertToJson(filter))
                        .header("Content-Type", this.contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(1)));
    }
}
