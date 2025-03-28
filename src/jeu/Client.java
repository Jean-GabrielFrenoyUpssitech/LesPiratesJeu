package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String SERVER_IP = "lespiratesjeu.up.railway.app"; // URL Railway
        int SERVER_PORT = 5000; // Vérifie que Railway utilise bien 5000

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("✅ Connecté au serveur sur " + SERVER_IP + ":" + SERVER_PORT);

            // Lire le message d'accueil du serveur
            String serverMessage = in.readLine();
            if (serverMessage == null) {
                System.out.println("⚠️ Serveur ne répond pas, fermeture de la connexion.");
                return;
            }
            System.out.println("📨 Message du serveur : " + serverMessage);

            while (true) {
                System.out.print("💬 Entre une action (ou 'quit' pour quitter) : ");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }

                out.println(userInput); // Envoyer le message au serveur

                // Lire la réponse du serveur
                String response = in.readLine();
                if (response == null) {
                    System.out.println("⚠️ Connexion perdue avec le serveur.");
                    break;
                }

                System.out.println("📨 Réponse du serveur : " + response);
            }

            System.out.println("👋 Déconnexion...");
        } catch (IOException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
        }
    }
}
