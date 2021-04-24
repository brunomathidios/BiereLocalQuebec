package com.ca.biere.local.quebec.gestion.ws.controleur.test;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ca.biere.local.quebec.gestion.ws.controleur.base.test.ControleurBaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest 
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AmertumeControleurTest extends ControleurBaseTest {

	@Value("${application.domain}/amertumes")
	private String uriControleur; 
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void listerAmertume() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get(this.uriControleur)
			.header("Content-Type", this.contentType))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].enumList").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].enumList").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].enumList", Matchers.hasSize(3)));
	}
}
