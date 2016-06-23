package Teste;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.google.gson.Gson;
import com.sun.research.ws.wadl.Response;

import br.com.urls.modelos.URL;
import junit.framework.Assert;

public class AplicationTest {
	@Test
    public void testaQueSuportaNovasUrls(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        URL url = new URL("https://cursos.alura.com.br/courses/webservices-rest-com-jaxrs-e-jersey/sections/5/exercises/8659/answer/4892358");
        String urlJson = new Gson().toJson(url);
        
        

        Entity<String> entity = Entity.entity(urlJson, MediaType.APPLICATION_JSON);

        javax.ws.rs.core.Response response = target.path("/urls").request().post(entity);
        Assert.assertEquals("{'longUrl' :'https://cursos.alura.com.br/courses/webservices-rest-com-jaxrs-e-jersey/sections/5/exercises/8659/answer/4892358'}", response.readEntity(String.class));
    }
}
