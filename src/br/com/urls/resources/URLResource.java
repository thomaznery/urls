package br.com.urls.resources;

import java.math.BigInteger;
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
	

	//curl -v http://localhost:8080/urls/n
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("id")String id){
		System.out.println(id);
		int idb10 = new BigInteger(id, 36).intValue();
		System.out.println(idb10);
		URL url = App.managerUrl.getUrl((idb10));
		if(!(url == null)){
			URI uri = URI.create(url.getUrl());			
			return Response.status(301).location(uri).build();
		}else{
			return Response.status(404).build();
		}
			
	}
	
	

	//curl -v -X "DELETE" http://localhost:8080/urls/6
	@Path("{id}")
	@DELETE
	public Response deleteUrl(@PathParam("id")String id){
		int idb10 = Integer.parseInt(id, 36);
		if(App.managerUrl.deleteUrl(idb10)){
			return null;
		}
		return Response.status(404).build();
	}
}
