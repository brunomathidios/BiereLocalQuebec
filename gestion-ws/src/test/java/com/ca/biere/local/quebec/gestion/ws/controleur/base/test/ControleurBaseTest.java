package com.ca.biere.local.quebec.gestion.ws.controleur.base.test;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public abstract class ControleurBaseTest {

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), 
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected MockMultipartHttpServletRequestBuilder getMockMultipartBuilderToHttpPUTMethod(String uri) {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(uri);
		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});
		return builder;
	}
}
