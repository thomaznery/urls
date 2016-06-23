package server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.urls.util.App;

public class Servidor {

	public static void main(String[] args) throws IOException {
		HttpServer server = initServer();
		System.out.println("Servidor subiu");
		System.in.read();
		
		
		server.stop();
		System.out.println("Servidor caiu");
		
	}
	
	public static HttpServer initServer(){
		ResourceConfig config = new ResourceConfig().packages("br.com.urls");
		URI uri = URI.create("http://localhost:8080/");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri,config);
		return server;
	}

}
