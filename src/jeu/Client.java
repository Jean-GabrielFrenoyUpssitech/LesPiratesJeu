package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class Client {
    public static void main(String[] args) {
    	String SERVER_URL = "http://l9b4v4se.up.railway.app/jeu"; // Remplacer http:// par https://
        int SERVER_PORT = 8080; // Port HTTPS par défaut (443)

        try {
            // Créer l'URL complète pour envoyer la requête à /jeu
            URL url = new URL(SERVER_URL);

            // Ouverture d'une connexion HTTPS vers le serveur
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");  // Utiliser POST pour envoyer des données
            connection.setDoOutput(true);  // Activer l'envoi de données via POST

            // Scanner pour lire les entrées utilisateur
            Scanner scanner = new Scanner(System.in);

            // Demander à l'utilisateur de saisir une action
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
            if (statusCode == HttpsURLConnection.HTTP_OK) {
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
