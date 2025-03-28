package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String SERVER_IP = "monserveur.railway.app"; // Remplace par l'URL de ton serveur Railway
        final int SERVER_PORT = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")); // Port dynamique Railway
        
        while (true) {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.println("✅ Connecté au serveur sur " + SERVER_IP + ":" + SERVER_PORT);
                System.out.println("📨 Message du serveur : " + in.readLine());

                while (true) {
                    System.out.print("💬 Entre une action (ou 'quit' pour quitter) : ");
                    String userInput = scanner.nextLine();
                    out.println(userInput);

                    if (userInput.equalsIgnoreCase("quit")) {
                        System.out.println("🔚 Déconnexion du serveur.");
                        return;
                    }

                    String serverResponse = in.readLine();
                    if (serverResponse == null) {
                        System.out.println("⚠️ Connexion perdue avec le serveur.");
                        break;
                    }
                    System.out.println("📩 Réponse du serveur : " + serverResponse);
                }
            } catch (IOException e) {
                System.out.println("❌ Erreur de connexion au serveur. Nouvelle tentative dans 5 secondes...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
