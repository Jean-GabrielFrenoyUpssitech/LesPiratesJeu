package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String host = "192.168.0.34";  // Remplace par l'IP du serveur
        int port = 5001;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("✅ Connecté au serveur sur " + host + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Message d'accueil du serveur
            System.out.println("Serveur : " + in.readLine());

            // Boucle de jeu
            String message;
            while (true) {
                System.out.print("Ton action (jouer une carte / quit) : ");
                message = scanner.nextLine();
                out.println(message);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                // Réponse du serveur (état du jeu, action effectuée, etc.)
                String response = in.readLine();
                System.out.println("Serveur : " + response);
            }

            System.out.println("🔒 Déconnexion...");
        } catch (IOException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
        }

        scanner.close();
    }
}
