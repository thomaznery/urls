package br.com.urls.resources;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.com.urls.modelos.URL;
import br.com.urls.util.App;

@Path("urls")
public class URLResource {
	
	//funcional
	//curl -v http://localhost:8080/urls/1
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("id")String id){
		URL url = App.managerUrl.getUrl(Integer.parseInt(id));
		if(!(url == null)){
			URI uri = URI.create(url.getUrl());			
			return Response.status(301).location(uri).build();
		}else{
			return Response.status(404).build();
		}
			
	}
	
	
	//funcaional
	//curl -v -X "DELETE" http://localhost:8080/urls/6
	@Path("{id}")
	@DELETE
	public Response deleteUrl(@PathParam("id")int id){
		if(App.managerUrl.deleteUrl(id)){
			return null;
		}
		return Response.status(404).build();
	}
}
