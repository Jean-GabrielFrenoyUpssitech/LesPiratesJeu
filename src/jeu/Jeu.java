package jeu;

import java.security.SecureRandom;

import affichage.IAffichage;

public class Jeu implements IAffichage {
	private Pioche pioche;
	private static Joueur[] joueurs = new Joueur[2];
	private static SecureRandom random;
	private int nbTour=0;
	static {
		try {
			random = SecureRandom.getInstanceStrong();
		} catch (Exception e) {
			e.printStackTrace();
			random = new SecureRandom();
		}
	}
	public void setNbTourPlusUn() {
		nbTour++;
	}
	public int getNbTour() {
		return nbTour;
	}
	public static Pioche initPioche() {

		Carte[] cartes = initCartes();
		Pioche piocheObjet = new Pioche(cartes[0]);

		int[] quantites = { 5, 5, 5, 5, 5, 5, 5, 5 };
		int numTableauPiocheActuel = 0;
		int nbCarteDifferentes = 8;
		for (int i = 0; i < nbCarteDifferentes; i++) {
			for (int j = 0; j < quantites[i]; j++) {
				piocheObjet.getPiocheTableau()[numTableauPiocheActuel] = cartes[i];
				numTableauPiocheActuel++;

			}

		}

		return piocheObjet;
	}

	private static Carte[] initCartes() {
		Carte revolteOrganisee = new RevolteOrganisee(Description.REVOLTEORGANISEE);
		Carte mainDeFer = new MainDeFer(Description.MAINDEFER);
		Carte coupDeSabre = new CoupDeSabre(Description.COUPDESABRE);
		Carte abordageReussi = new AbordageReussi(Description.ABORDAGEREUSSI);
		Carte discoursInspirant = new DiscoursInspirant(Description.DISCOURSINSPIRANT);
		Carte blocageDefensif = new BlocageDefensif(Description.BLOCAGEDEFENSIF);
		Carte echangeForcee = new EchangeForce(Description.ECHANGEFORCE);
		Carte planMachiavelique = new PlanMachiavelique(Description.PLANMACHIAVELIQUE);

		return new Carte[] { blocageDefensif, mainDeFer, revolteOrganisee, coupDeSabre, abordageReussi,
				discoursInspirant, echangeForcee, planMachiavelique };
	}

	public static Pioche shuffle(Pioche objetPioche) {
		Carte tempCarte;
		int numRandom = random.nextInt(39);
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 40; i++) {
				tempCarte = objetPioche.getPiocheTableau()[i];
				objetPioche.getPiocheTableau()[i] = objetPioche.getPiocheTableau()[numRandom];
				objetPioche.getPiocheTableau()[numRandom] = tempCarte;
				numRandom = random.nextInt(39);
			}
		}
		return objetPioche;
	}

	Jeu initJeu() {
		Jeu jeu = new Jeu();
		IAffichage.afficherNbTour(jeu.nbTour);

		Pioche pioche = initPioche();

		jeu.pioche = shuffle(pioche);

		Joueur joueur1 = new Joueur();
		Joueur joueur2 = new Joueur();
		joueur1.initJoueur(jeu, 1);
		joueur2.initJoueur(jeu, 2);
		Joueur[] joueurs = new Joueur[2];
		joueurs[0] = joueur1;
		joueurs[1] = joueur2;
		Jeu.joueurs = joueurs;
		return jeu;
	}

	public Pioche getPioche() {
		return pioche;
	}

	public static void piocher(Pioche objetPioche, Joueur joueur) {
		while (joueur.getNbCarteEnMain() < 5) {
			Carte carte = objetPioche.donnerCarte(objetPioche);
			if (objetPioche.getCarteRestantePioche() < 1) {
				IAffichage.afficherPiocheVide();
			} else {
				if (joueur.getNbCarteEnMain() == 0) {
					joueur.setMain(carte);

				} else {
					joueur.setMain(carte);
					if (joueur.getNbCarteEnMain() < 5) {
					}
				}
				joueur.setNbCarteEnMain();

			}
		}
	}
	public Joueur[] getJoueur() {
		return joueurs;
	}

	/*public static void main(String[] args) {
		Jeu jeu = initJeu();
		Joueur joueur = joueurs[0];
		Joueur adversaire = joueurs[1];

		IAffichage.donnerContexte();
		IAffichage.donnerRegles();

		while (joueur.getPv() > 0 && adversaire.getPv() > 0 && joueur.getPopularite() < 6
				&& adversaire.getPopularite() < 6) {
			jeu.setNbTourPlusUn();
			IAffichage.afficherNbTour(jeu.nbTour);

			joueur = joueur.getTourJoueur(jeu.nbTour, joueurs);
			adversaire = adversaire.getTourJoueur(jeu.nbTour + 1, joueurs);
			piocher(jeu.getPioche(), joueur);
			IAffichage.donnerStatusJoueur(joueur, adversaire);

			joueur.jouerCarte(adversaire);

		}
		IAffichage.afficherVictoire(joueur, adversaire);

	}*/

}