package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String SERVER_IP = "https://ton-nom-du-projet.up.railway.app"; // URL de ton serveur Railway
        int SERVER_PORT = 80; // Port par défaut pour HTTP

        try {
            // URL complète du serveur (endpoint)
            URL url = new URL(SERVER_IP + "/jeu");

            // Ouverture d'une connexion HTTP vers le serveur
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); // Activer l'envoi de données

            // Scanner pour lire les entrées utilisateur
            Scanner scanner = new Scanner(System.in);

            // Envoyer un premier message au serveur avant de lire la réponse
            System.out.print("Entre une action (ou 'quit' pour quitter) : ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Déconnexion...");
                return;
            }

            // Envoyer la requête POST avec le message utilisateur
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = userInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lire la réponse du serveur après avoir envoyé les données
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Réponse du serveur : " + serverMessage);
                }
            } else {
                System.out.println("Erreur avec la requête. Code de statut : " + statusCode);
            }

            System.out.println("Déconnexion...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
