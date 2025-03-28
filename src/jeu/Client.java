package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String SERVER_IP = "https://ton-nom-du-projet.up.railway.app"; // URL de ton serveur Railway
        int SERVER_PORT = 80; // Port par défaut pour HTTP (tu n'as normalement pas besoin de spécifier ce port)

        try {
            // URL complète du serveur (endpoint)
            URL url = new URL(SERVER_IP + "/jeu");

            // Ouverture d'une connexion HTTP vers le serveur
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); // Activer l'envoi de données

            // Scanner pour lire les entrées utilisateur
            Scanner scanner = new Scanner(System.in);

            // Message de bienvenue du serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Message du serveur : " + serverMessage);
            }

            while (true) {
                System.out.print("Entre une action (ou 'quit' pour quitter) : ");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }

                // Envoyer la requête POST avec le message utilisateur
                connection.getOutputStream().write(userInput.getBytes());

                // Lire la réponse du serveur
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response;
                while ((response = responseReader.readLine()) != null) {
                    System.out.println("Réponse du serveur : " + response);
                }
            }

            System.out.println("Déconnexion...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
