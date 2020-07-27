package com.company;

import java.util.*;
public class Dessin {

	//largeur de la surface
	private int largeur;

	//hauteur de la surface
	private int hauteur;

	//tableau en 2D qui contient les caractères du dessin final
	private char[][] grille;

	//Arraylist de formes
	ArrayList < Forme > formes = new ArrayList < Forme > ();

	//caractère de dessin actuel initialisé à '#'
	private char carActuel = '#';

	public char getCarActuel(){
		return this.carActuel;
	}

	//Constructeur de la surface de dessin et ses attributs
	public Dessin(int largeur, int hauteur) {
		this.largeur = largeur; //nbColonnes
		this.hauteur = hauteur; //nbLignes
		this.grille = new char[hauteur][largeur];
		effacerGrille();
	}

	public void effacerGrille() {
		for (var i = 0; i < grille.length; i++) {
			Arrays.fill(grille[i], ' ');
		}
	}

	//modifie la grille en utilisant les formes, appelle les méthodes "get" des formes instanciées afin de dessiner sur la surface
	public void dessiner() {
		effacerGrille();
		for (int i = 0; i < formes.size(); i++) {
			ArrayList < Point > points = formes.get(i).getPoints();
			carActuel = formes.get(i).getCar();

			for (int j = 0; j < points.size(); j++) {
				int y = points.get(j).getY();
				int x = points.get(j).getX();
				if ((y >= 0 && y < this.hauteur) && (x >= 0 && x < this.largeur)) {
					grille[points.get(j).getY()][points.get(j).getX()] = carActuel;
				}
			}
		}
		afficher();

	}

	//inverse l'ordre des lignes du tableau grille
	public void renverser() {
		for (int i = 0; i < formes.size(); i++) {
			formes.get(i).renverserForme(this.hauteur);
		}
	}

	public void brasser() {
		for (int i = 0; i < formes.size(); i++) {
			formes.get(i).brasser();
		}
	}


    public void afficher() {
        for (int i = 0; i < this.grille.length; i++) {
            for (int j = 0; j < this.grille[i].length; j++) {
                System.out.print(this.grille[i][j]);
            }
            System.out.println();
        }
    }

	//modifie le caractère actuel de dessin
	public void setCarActuel(char nouveauCar) {
		this.carActuel = nouveauCar;
	}
}