package com.ca.biere.local.quebec.gestion.ws.controleur.base.test;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

public abstract class ControleurBaseTest {

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), 
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
}
