package jeu;
import java.io.*;
import java.net.*;

import affichage.IAffichage;

public class Server {
    public static void main(String[] args) {
        final int BASE_PORT = 5000;
        ServerSocket serverSocket = null;
        int port = BASE_PORT;

        // Recherche d'un port disponible entre 5000 et 5009
        while (serverSocket == null && port < BASE_PORT + 10) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("✅ Serveur lancé sur le port " + port);
            } catch (BindException e) {
                port++;  // Si le port est déjà utilisé, on passe au suivant
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        if (serverSocket == null) {
            System.out.println("🚫 Aucun port disponible entre " + BASE_PORT + " et " + (BASE_PORT + 9));
            return;
        }

        try {
            System.out.println("⏳ En attente d'un client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("👤 Client connecté depuis " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("👋 Bienvenue sur le serveur ! Le jeu commence.");

            // Boucle de gestion de la partie
            String message;
            while ((message = in.readLine()) != null) {
            	Jeu jeu = new Jeu(); 
            			jeu=jeu.initJeu();
        		Joueur joueur = jeu.getJoueur()[0];
        		Joueur adversaire = jeu.getJoueur()[1];

        		IAffichage.donnerContexte();
        		IAffichage.donnerRegles();

        		while (joueur.getPv() > 0 && adversaire.getPv() > 0 && joueur.getPopularite() < 6
        				&& adversaire.getPopularite() < 6) {
        			jeu.setNbTourPlusUn();
        			IAffichage.afficherNbTour(jeu.getNbTour());

        			joueur = joueur.getTourJoueur(jeu.getNbTour(), jeu.getJoueur());
        			adversaire = adversaire.getTourJoueur(jeu.getNbTour() + 1, jeu.getJoueur());
        			Jeu.piocher(jeu.getPioche(), joueur);
        			IAffichage.donnerStatusJoueur(joueur, adversaire);

        			joueur.jouerCarte(adversaire);

        		}
        		IAffichage.afficherVictoire(joueur, adversaire);


                if (message.equalsIgnoreCase("quit")) {
                    System.out.println("🔚 Fin de la connexion.");
                    break;
                }

                out.println("Action effectuée : " + message);  // Réponse au client
            }

            clientSocket.close();
            serverSocket.close();
            System.out.println("🔒 Connexion fermée. Serveur arrêté.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
