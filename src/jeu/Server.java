package jeu;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")); // Port Railway ou 5000 par défaut

        // Créer un serveur HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("✅ Serveur HTTP lancé sur le port " + port);

        // Définir le gestionnaire de requêtes HTTP pour /jeu
        server.createContext("/jeu", new GameHandler());

        // Démarrer le serveur
        server.start();
        System.out.println("Serveur démarré...");
    }

    // Le gestionnaire pour traiter les requêtes HTTP
    static class GameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response;

            // Si la méthode est POST, traiter le message envoyé
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader reader = new BufferedReader(isr);
                String userInput = reader.readLine();

                if (userInput == null) {
                    response = "⚠️ Erreur: Pas de message reçu.";
                } else {
                    System.out.println("💬 Message reçu : " + userInput);
                    response = "Action reçue : " + userInput;
                }
            } else {
                // Si la méthode n'est pas POST (par exemple GET), envoyer un message de bienvenue
                response = "👋 Bienvenue sur le serveur ! Le jeu commence.";
            }

            // Répondre à la requête
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
