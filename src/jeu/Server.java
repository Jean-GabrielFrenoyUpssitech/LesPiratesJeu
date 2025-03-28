package jeu;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")); // Port Railway ou 5000 par défaut
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("✅ Serveur lancé sur le port " + port);

            while (true) {
                System.out.println("⏳ En attente d'un client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("👤 Client connecté depuis " + clientSocket.getInetAddress());

                // Envoi du message de bienvenue avant de lancer le thread
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("👋 Bienvenue sur le serveur ! Le jeu commence.");
                
                // Lancer un thread pour chaque client (permet plusieurs joueurs)
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true) // Auto-flush activé
        ) {
            // Message de bienvenue déjà envoyé dans la classe Server, donc ici, on attend les messages du client
            String message;
            while (true) {
                message = in.readLine();
                if (message == null) { // Vérifier si le client s'est déconnecté
                    System.out.println("⚠️ Client déconnecté ou problème de lecture !");
                    break;
                }

                System.out.println("💬 Message reçu : " + message);

                if (message.equalsIgnoreCase("quit")) {
                    System.out.println("🔚 Un joueur s'est déconnecté.");
                    break;
                }

                out.println("Action reçue : " + message);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
