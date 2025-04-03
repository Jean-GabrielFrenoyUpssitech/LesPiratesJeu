package jeu;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
	public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")); // Port Railway

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);        
        System.out.println("✅ Serveur HTTP lancé sur le port " + port);

        server.createContext("/jeu", new GameHandler());

        server.start();
        System.out.println("🌍 Serveur démarré sur http://l9b4v4se.up.railway.app");
    }

	static class GameHandler implements HttpHandler {
	    @Override
	    public void handle(HttpExchange exchange) throws IOException {
	        System.out.println("🔍 Requête reçue: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());

	        String response;
	        
	        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
	            String userInput = reader.readLine();
	            
	            if (userInput == null || userInput.isEmpty()) {
	                response = "⚠️ Erreur: Pas de message reçu.";
	                System.out.println("⚠️ Aucune donnée reçue.");
	            } else {
	                System.out.println("💬 Message reçu : " + userInput);
	                response = "Action reçue : " + userInput;
	            }
	        } else {
	            response = "👋 Bienvenue sur le serveur !";
	            System.out.println("✅ Réponse envoyée : " + response);
	        }

	        exchange.getResponseHeaders().set("Content-Type", "text/plain");
	        exchange.sendResponseHeaders(200, response.getBytes().length);
	        OutputStream os = exchange.getResponseBody();
	        os.write(response.getBytes());
	        os.close();
	    }
	}


            // Définir le type de contenu et envoyer la réponse
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
