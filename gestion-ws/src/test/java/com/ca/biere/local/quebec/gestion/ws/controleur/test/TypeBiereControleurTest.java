package com.ca.biere.local.quebec.gestion.ws.controleur.test;

import java.io.UnsupportedEncodingException;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.After;
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

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.utils.JsonUtils;
import com.ca.biere.local.quebec.gestion.ws.controleur.base.test.ControleurBaseTest;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest 
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TypeBiereControleurTest extends ControleurBaseTest {
	
	@Value("${application.domain}/types")
	private String uriControleur; 
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Environment environment;
	
	private static final String NOM_BIERE_TEST = "Bière Test Nom";
	
	private boolean doitEffacerEntiteAjoute = false;
	private Long idTypeBiereAjoute = 0L;

	@Test
	public void lister() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("page", "0")
			.param("size", "5")
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(5)));
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
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].idTypeBiere", Is.is(1)));
	}
	
	@Test
	public void findByIdInexistent() throws Exception {
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
	public void insertNouvelleEntiteAvecIdRempliDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"		\"idTypeBiere\": 5000,\n" + 
				"		\"nom\": \"nom test\",\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		String msg = this.environment.getRequiredProperty("message.error.methode.insert");
	
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase(msg)));
	}
	
	@Test
	public void insertNouvelleEntiteSansNomDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("nom")));
	}
	
	@Test
	public void insertNouvelleEntiteAvecNomPlus200CaracteresDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"		\"nom\": \"teste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste teste\",\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("nom")));
	}
	
	@Test
	public void insertNouvelleEntiteAvecDescriptionPlus4000CaracteresDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"		\"nom\": \"nom test\",\n" + 
				"	    \"description\": \"description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres \"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("description")));
	}
	
	@Test
	public void insertNouvelleEntiteAvecNomPlus200CaracterEtDescriptionPlus4000CaracteresDoitRetournerException() throws Exception {
		String entiteAvecId = 
				"	{\n" + 
				"		\"nom\": \"teste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste testeteste teste teste\",\n" + 
				"	    \"description\": \"description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres description test 4000 caracteres \"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entiteAvecId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("caractères")))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[1]", Matchers.containsStringIgnoringCase("caractères")));
	}
	
	@Test
	public void effaceEntiteIdNonExistentDoitRetournerException() throws Exception {
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
	
	public TypeBiere getTypeBiereAjoute() throws Exception, UnsupportedEncodingException {
		String entite = 
				"	{\n" + 
				"		\"nom\": \"" + NOM_BIERE_TEST + "\",\n" + 
				"	    \"description\": \"description type de bière test\"\n" + 
				"	}"; 
		
		MvcResult result = this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", 
					Matchers.containsStringIgnoringCase("ajouté")))
			.andReturn();
		
		JsonNode node = JsonUtils.convertFromJsonToJsonNode(result.getResponse().getContentAsString());
		
		TypeBiere typeBiereAjoute = JsonUtils
				.converterJsonNodeToObject(node.get("data").iterator().next(), TypeBiere.class);
		
		doitEffacerEntiteAjoute = true;
		idTypeBiereAjoute = typeBiereAjoute.getIdTypeBiere();
		
		return typeBiereAjoute;
	}
	
	@Test
	public void misAJourEntiteSansIdDoitRetournerException() throws Exception {
		String entiteSansId = 
				"	{\n" + 
				"		\"nom\": \"nom test\",\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + null)
			.header("Content-Type", this.contentType)
			.content(entiteSansId))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void misAJourEntiteIdInexistentDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"		\"nom\": \"nom test\",\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 99999L)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", 
					Matchers.containsStringIgnoringCase("n'a pas été trouvée")));
	}
	
	/** não pode nem inserir e nem atualizar entidade com nome duplicado **/
	
	@Test
	public void insertEntiteNomInexistentDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"		\"nom\": \"Rousse\",\n" + 
				"	    \"description\": \"description test\"\n" + 
				"	}"; 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post(this.uriControleur)
			.header("Content-Type", this.contentType)
			.content(entite))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", 
					Matchers.containsStringIgnoringCase("déjà existe")));
	}
	
	@Test
	public void misAJourEntiteAvecNomExistentDoitRetournerException() throws Exception {
		String entite = 
				"	{\n" + 
				"		\"nom\": \"" + NOM_BIERE_TEST + "\",\n" + 
				"	    \"description\": \"description test\",\n" + 
				"		\"dateCreation\": \"2020-11-13T22:57:42.345+00:00\"\n" +
				"	}";
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put(this.uriControleur + "/" + 2L)
			.header("Content-Type", this.contentType)
			.content(entite)) 
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", 
					Matchers.containsStringIgnoringCase("déjà existe")));
	}
	
	@Test
	public void rechercheAvecFilterNomDoitRetournerDesResultats() throws Exception {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setNom("stout"); 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("filter", JsonUtils.convertToJson(filter))
			.header("Content-Type", this.contentType)) 
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].pageable").exists());
	}
	
	@Test
	public void rechercheAvecFilterDescriptionDoitRetournerDesResultats() throws Exception {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setDescription("belle");
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("filter", JsonUtils.convertToJson(filter))
			.header("Content-Type", this.contentType)) 
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].pageable").exists());
	}
	
	@Test
	public void rechercheAvecFilterNomeEDescriptionDoitRetournerDesResultats() throws Exception {
		TypeBiereFilter filter = new TypeBiereFilter();
		filter.setNom("dry");
		filter.setDescription("stout"); 
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.param("filter", JsonUtils.convertToJson(filter))
			.header("Content-Type", this.contentType)) 
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].pageable").exists());
	}
	
	@Test
	public void supprimerEntiteAvecBieresAssocieesDoitRetournerException() throws Exception {
		Long idTypeBierePossedeBieres = 10L;
		
		String erreurMessage = "Interdit de supprimer cet enregistrement, "
				+ "il possède d'autres bières associées à lui.";
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.delete(this.uriControleur + "/" + idTypeBierePossedeBieres)
			.header("Content-Type", this.contentType)) 
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", Matchers.equalTo(erreurMessage)));
	}

	@Test
	public void listerTypeBiereByNomDoitRetourner3Enregistrements() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.uriControleur + "/lister")
						.param("nom", "ipa")
						.header("Content-Type", this.contentType))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(3)));
	}
	
	@After
	public void supprimerEntiteAjoute() throws Exception {
		
		if(doitEffacerEntiteAjoute) {
			this.mockMvc
			.perform(MockMvcRequestBuilders.delete(this.uriControleur + "/" + idTypeBiereAjoute)
			.header("Content-Type", this.contentType))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.messages[0]", Matchers.containsStringIgnoringCase("effacé")));
			
			doitEffacerEntiteAjoute = false;
		}
		
	}
	
}
