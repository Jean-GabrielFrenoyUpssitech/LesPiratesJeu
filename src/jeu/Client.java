package jeu;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String SERVER_IP = "lespiratesjeu.up.railway.app"; // Remplace par ton URL Railway
        int SERVER_PORT = 5000; // Assure-toi que c'est bien le port utilisé sur Railway

        try (Socket socket = new Socket("lespiratesjeu.up.railway.app", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(" Connecté au serveur sur " + SERVER_IP + ":" + SERVER_PORT);

            // Lire le message d'accueil du serveur
            String serverMessage = in.readLine();
            if (serverMessage == null) {
                System.out.println(" Connexion fermée par le serveur.");
                return;
            }
            System.out.println(" Message du serveur : " + serverMessage);

            while (true) {
                System.out.print(" Entre une action (ou 'quit' pour quitter) : ");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }

                out.println(userInput); // Envoyer le message au serveur

                // Lire la réponse du serveur
                String response = in.readLine();
                if (response == null) {
                    System.out.println(" Connexion perdue avec le serveur.");
                    break;
                }

                System.out.println(" Réponse du serveur : " + response);
            }

            System.out.println(" Déconnexion...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}