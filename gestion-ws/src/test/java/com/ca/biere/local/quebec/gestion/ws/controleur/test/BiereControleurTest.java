package com.ca.biere.local.quebec.gestion.ws.controleur.test;

import java.math.BigDecimal;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.controleur.base.test.ControleurBaseTest;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;

@RunWith(SpringRunner.class)
@SpringBootTest 
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BiereControleurTest extends ControleurBaseTest {

	@Value("${application.domain}")
	private String uriControleur; 
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Environment environment;
	
	@Test
	public void insertEntiteAvecIdDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"		\"idBiere\": 5000,\n" + 
				"		\"nom\": \"nom biere\",\n" + 
				"	    \"description\": \"description biere\"\n" + 
				"	}"; 
		
		String msg = this.environment.getRequiredProperty("message.error.methode.insert");
	
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo(msg)));
	}
	
	@Test
	public void insertEntiteSansNomDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"origine\": \"origine test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("nom")));
	}
	
	@Test
	public void insertEntiteSansOrigineDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("origine")));
	}
	
	@Test
	public void insertEntiteSansTauxAlcoolDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("taxu d'alcool")));
	}
	
	@Test
	public void insertEntiteSansIbuDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("ibu")));
	}
	
	@Test
	public void insertEntiteSansAmertumeDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 35,\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("amertume")));
	}
	
	@Test
	public void insertEntiteSansIdTypeBiereDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 35,\n" + 
				"	    \"amertume\": \"MOYENNE\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("type de bière")));
	}
	
	@Test
	public void insertEntiteSansAucunChampRempliDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": null,\n" + 
				"	    \"origine\": null,\n" + 
				"	    \"tauxAlcool\": null,\n" + 
				"	    \"ibu\": null,\n" + 
				"	    \"amertume\": null,\n" +
				"	    \"idTypeBiere\": null\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(6)));
	}
	
	@Test
	public void insertEntiteAvecSuccess() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"origine test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.equalTo("Biere ajouté avec succès!")));
	}
	
	@Test
	public void misAJourEntiteSansIdDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"origine test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + null)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void misAJourEntiteSansNomDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"origine\": \"origine test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("nom")));
	}
	
	@Test
	public void misAJourEntiteSansOrigineDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("origine")));
	}
	
	@Test
	public void misAJourEntiteSansTauxAlcoolDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("taxu d'alcool")));
	}
	
	@Test
	public void misAJourEntiteSansIbuDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("ibu")));
	}
	
	@Test
	public void misAJourEntiteSansAmertumeDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 35,\n" + 
				"	    \"idTypeBiere\": 1\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("amertume")));
	}
	
	@Test
	public void misAJourEntiteSansIdTypeBiereDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nom test\",\n" + 
				"	    \"origine\": \"brésil\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 35,\n" + 
				"	    \"amertume\": \"MOYENNE\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("type de bière")));
	}
	
	@Test
	public void misAJourEntiteSansAucunChampRempliDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": null,\n" + 
				"	    \"origine\": null,\n" + 
				"	    \"tauxAlcool\": null,\n" + 
				"	    \"ibu\": null,\n" + 
				"	    \"amertume\": null,\n" +
				"	    \"idTypeBiere\": null\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(6)));
	}
	
	@Test
	public void misAJourEntiteAvecSuccess() throws Exception {
		String entite = 
				"	{\n" + 
				"	    \"nom\": \"nouvelle nom test\",\n" + 
				"	    \"origine\": \"origine test\",\n" + 
				"	    \"tauxAlcool\": 5.60,\n" + 
				"	    \"ibu\": 20,\n" + 
				"	    \"amertume\": \"MOYENNE\",\n" + 
				"	    \"idTypeBiere\": 1,\n" +
				"		\"dateCreation\": \"2020-11-13T22:57:42.345+00:00\"\n" +
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.equalTo("Biere mis à jour avec succès!")));
	}
	
	@Test
	public void getEntiteIdInexistentDoitRetournerException() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur + "/" + 99999L)
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.equalTo("Not Found")));
	}
	
	@Test
	public void getEntiteAvecSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur + "/" + 1L)
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].idBiere", Is.is(1)));
	}
	
	@Test
	public void supprimerEntiteIdInexistentDoitRetournerException() throws Exception {
		Long idBiereInexistent = 99999L;
		String erreurMessage = "Entité " + idBiereInexistent + " du type Biere n'a pas été trouvée";
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.delete(this.uriControleur + "/" + idBiereInexistent)
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo(erreurMessage)));
	}
	
	@Test
	public void listerParNom() throws Exception {
		BiereFilter filter = new BiereFilter();
		filter.setNom("stout");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("page", "0")
			.param("size", "5")
			.param("filter", JsonUtils.convertToJson(filter))
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(2)));
	}
	
	@Test
	public void listerParTauxAlcoolEntre5_5Et8Pourcent() throws Exception {
		BiereFilter filter = new BiereFilter();
		filter.setTauxAlcoolStart(new BigDecimal(5.5));
		filter.setTauxAlcoolEnd(new BigDecimal(8.0));
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("page", "0")
			.param("size", "5")
			.param("filter", JsonUtils.convertToJson(filter))
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(4)));
	}
	
	@Test
	public void listerParIBUEntre35Et50Pourcent() throws Exception {
		BiereFilter filter = new BiereFilter();
		filter.setIbuStart(35);
		filter.setIbuEnd(50);
		
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
	public void listerParAmertume() throws Exception {
		BiereFilter filter = new BiereFilter();
		filter.setAmertume(EnumAmertume.FAIBLE);
		
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
	
}
