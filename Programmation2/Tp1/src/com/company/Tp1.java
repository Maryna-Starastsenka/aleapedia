package com.company;

import java.util.*;
public class Tp1 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Dessin nouveauDessin = null;
		int posX, posY, finX, finY, largeur, hauteur, cote, rayon;
		String instruction, texte;
		char lettre;

		do {
			instruction = scan.nextLine();

			if (instruction.equals("fin")) {

				break;
			}
			String[] motsInstruction = instruction.split(" ");

			//Si aucun dessin n'a été initialisé, afficher erreur
			if (!motsInstruction[0].equals("init") && nouveauDessin == null) {
				System.out.println("ERREUR: Aucune surface définie");
				continue;
			}

			switch (motsInstruction[0]) {
				case "init":
					//nombre de colonnes
					largeur = Integer.parseInt(motsInstruction[1]);
					//nombre de lignes
					hauteur = Integer.parseInt(motsInstruction[2]);
					nouveauDessin = new Dessin(largeur, hauteur);
					break;
				case "car":
					char caractere = motsInstruction[1].charAt(0);
					nouveauDessin.setCarActuel(caractere);
					break;
				case "ajouter":

					//Vérifie la forme à ajouter:
					posX = Integer.parseInt(motsInstruction[2]);
					posY = Integer.parseInt(motsInstruction[3]);
					switch (motsInstruction[1]) {

						case "rectangle":
							largeur = Integer.parseInt(motsInstruction[4]);
							hauteur = Integer.parseInt(motsInstruction[5]);
							Forme nouveauRect = new Rectangle(posX, posY,
									largeur, hauteur, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouveauRect);
							break;

						case "carre":
							cote = Integer.parseInt(motsInstruction[4]);
							Rectangle nouveauCarre = new Rectangle(posX,
									posY, cote, cote, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouveauCarre);
							break;

						case "ligne":
							finX = Integer.parseInt(motsInstruction[4]);
							finY = Integer.parseInt(motsInstruction[5]);
							Ligne nouvelleLigne = new Ligne(posX, posY,
									finX, finY, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouvelleLigne);
							break;

						case "cercle":
							rayon = Integer.parseInt(motsInstruction[4]);
							Cercle nouveauCercle = new Cercle(posX, posY,
									rayon, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouveauCercle);
							break;

						case "lettre":
							lettre = motsInstruction[4].charAt(0);
							Lettre nouvelleLettre = new Lettre(posX, posY,
									lettre, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouvelleLettre);
							break;

						case "texte":
							texte = motsInstruction[4];
							Texte nouveauTexte = new Texte(posX, posY,
									texte, nouveauDessin.getCarActuel());
							nouveauDessin.formes.add(nouveauTexte);
							break;

						default:
							System.out.println("ERREUR: Commande invalide");
					}

					break;
				case "renverser":
					nouveauDessin.renverser();
					break;
				case "dessiner":
					nouveauDessin.dessiner();
					break;
				case "brasser":
					nouveauDessin.brasser();
					break;
				default:
					System.out.println("ERREUR: Commande invalide");
			}
		}
		while (scan.hasNext());
	}
}