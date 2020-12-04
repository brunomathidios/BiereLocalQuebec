package com.ca.biere.local.quebec.commons.ws.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        JsonUtils.objectMapper
        	.findAndRegisterModules()
        	.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
    }

    public static <T> T convertFromJson(String json, Class<T> target) {
        try {
            return JsonUtils.objectMapper.readValue( json, target );
        } catch (IOException e) {
            throw new RuntimeException( String.format( "Error converting from JSON[%s] to %s.", json, target ), e );
        }
    }
    
    public static <T> T converterJsonNodeToObject(JsonNode jNode, Class<T> target){
    	try {
	    	ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.convertValue(jNode, target);
    	} catch(RuntimeException e) {
    		throw new RuntimeException( String.format( "Error converting from JSONNode to Object", jNode ), e );
    	}
    }
    
    public static JsonNode convertFromJsonToJsonNode(String json) {
        try {
            return JsonUtils.objectMapper.readTree( json );
        } catch (IOException e) {
            throw new RuntimeException( String.format( "Error converting from JSON[%s] to JsonNode.", json ), e );
        }
    }
    
    public static <T> List<T> convertFromJsonToList(String json, Class<T> target) {
        try {
            CollectionType toCollection = JsonUtils.objectMapper.getTypeFactory().constructCollectionType( List.class, target );
            
			return JsonUtils.objectMapper.readValue( json, toCollection );
        } catch (IOException e) {
            throw new RuntimeException( String.format( "Error converting from JSON[%s] to List.", json ), e );
        }
    }

    public static String convertToPrettyJson(Object object) {
        try {
            return JsonUtils.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( object );
        } catch (IOException e) {
            throw new RuntimeException( "Error converting object to JSON.", e );
        }
    }

    public static String convertToJson(Object object) {
        try {
            return JsonUtils.objectMapper.writeValueAsString( object );
        } catch (IOException e) {
            throw new RuntimeException( "Error converting object to JSON.", e );
        }
    }
}
