package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String SERVER_IP = "monserveur.railway.app"; // Remplace par l'URL de ton serveur Railway
        final int SERVER_PORT = 5000; // Railway attribue un port dynamique, mais il doit être configuré côté serveur

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
                    break;
                }

                String serverResponse = in.readLine();
                System.out.println("📩 Réponse du serveur : " + serverResponse);
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur de connexion au serveur.");
            e.printStackTrace();
        }
    }
}
