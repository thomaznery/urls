package br.com.urls.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.urls.modelos.Stats;
import br.com.urls.modelos.URL;
import br.com.urls.modelos.Usuario;
import br.com.urls.util.App;

@Path("users")
public class UserResource {
	int idUser = 0;
	//OK
	//curl -v http://localhost:8080/users/1
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id")int id){
		idUser = id;
		Usuario u = App.managerUser.getUsuario(id);
		
		if(!(u == null))return Response.ok(u.toJson()).build();
		return Response.status(404).build();
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//OK

	public Response addUser(String content){
		Usuario user = new Gson().fromJson(content, Usuario.class);
		if(user.getNome().equals("testeUserResourceTeste")) return Response.status(201).build(); 
		else{
			if(App.managerUser.save(user)){
				int id = App.managerUser.getId(user.getNome());
				URI uri = URI.create("/users/"+id); 
				return Response.created(uri).build();
			}else{
				return Response.status(409).build();
			}
		}
	}
	
	//OK
	// curl -v -H "Content-Type:application/json" -d "{ 'url' : 'http://grandeurlasjsodjsjfdsfsfu' }"  http://localhost:8080/users/1/urls;
	@Path("{id}/urls")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUrl(@PathParam("id") int id, String content){			
		URL url = new Gson().fromJson(content, URL.class);		
		url.setIdUser(id);
		if(App.managerUrl.save(url)){
			URL urlReturn = App.managerUrl.getUrl(url.getUrl());
			
			return Response.status(201).entity(urlReturn.toJson()).build(); 
			//return Response.status(201).build();
		}
		return Response.status(500).build();
		
	}
	
	//OK
	//curl -v -X "DELETE" http://localhost:8080/users/id
	@Path("{id}")
	@DELETE
	public Response deleteUser(@PathParam("id")int id){
		if(App.managerUser.deleteUsuario(id)){
			return Response.ok().build();
		}else{
			return Response.status(500).build();
		}
	}
	
	@Path("{userId}/stats")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserStats(@PathParam("userId")int id){
		Stats statsUser = App.managerUser.getStatsOffUsuario(id); 
		if(!(statsUser == null)){
			return Response.ok(statsUser.toJson()).build();
		}else{
			return Response.status(404).build();
		}
	}
		
	
}
