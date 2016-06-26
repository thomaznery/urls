package br.com.urls.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.urls.modelos.Stats;
import br.com.urls.modelos.URL;
import br.com.urls.util.App;

@Path("stats")
public class StatsResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStats(){
		System.out.println("Dentro do stats");
		Stats stats = App.managerUrl.gerarStats();
		if(!(stats == null)){
			return Response.ok(stats.toJson()).build();
		}else{
			return Response.status(500).build();
		}
		
	}
	
	//curl -v http://localhost:8080/stats/1
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatsOffIdUrl(@PathParam("id") int id){
		URL url = App.managerUrl.getUrl(id);
		if(!(url == null)){
			return Response.ok(url.toJson()).build();
		}else{
			return Response.status(404).build();
		}
	}
}
