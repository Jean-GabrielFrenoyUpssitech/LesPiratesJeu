package jeu;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")); // Port Railway

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("✅ Serveur HTTP lancé sur le port " + port);

        server.createContext("/jeu", new GameHandler());

        server.start();
        System.out.println("🌍 Serveur démarré sur http://localhost:" + port);
    }

    static class GameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response;

            if ("POST".equals(exchange.getRequestMethod())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String userInput = reader.readLine();

                if (userInput == null) {
                    response = "⚠️ Erreur: Pas de message reçu.";
                } else {
                    System.out.println("💬 Message reçu : " + userInput);
                    response = "Action reçue : " + userInput;
                }
            } else {
                response = "👋 Bienvenue sur le serveur !";
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
