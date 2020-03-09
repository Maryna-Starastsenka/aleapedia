import java.util.*;
public class Dessin {

	//largeur de la surface
	private int largeur;

	//hauteur de la surface
	private int hauteur;

	//tableau en 2D qui contient les caractères du dessin final
	private char[][] grille;

	//Arraylist des formes instanciées
	ArrayList<Forme> formes = new ArrayList<Forme>();

	//caractère de dessin actuel initialisé à '#'
	private char carActuel = '#';


	//Constructeur de la surface de dessin et ses attributs
	public Dessin(int largeur, int hauteur) {
		this.largeur = largeur; //nbColonnes
		this.hauteur = hauteur; //nbLignes
		this.grille = new char[hauteur][largeur];
		effacerGrille();
	}

	//Ajoute des espaces à toutes les positions
	private void effacerGrille() {
		for (var i = 0; i < grille.length; i++) {
			Arrays.fill(grille[i], ' ');
		}
	}


	//Modifie la grille
	public void dessiner() {

		effacerGrille();

		//Loop à travers nos formes instanciées
		for (int i = 0; i < formes.size(); i++) {
			//Produit une ArrayList des points de toutes les formes
			ArrayList<Point> points = formes.get(i).getPoints();
			carActuel = formes.get(i).getCar();

			//Itération de tous les points et modification la grille
			for (int j = 0; j < points.size(); j++) {
				int x = points.get(j).getX();
				int y = points.get(j).getY();

				//Modifie la grille si on est dans les limites de la grille:
				if (x >= 0 && x < this.largeur && y >= 0 && y < this.hauteur){
					grille[y][x] = this.carActuel;
				}
			}
		}

		afficher();

	}


	//Imprime la grille
	private void afficher() {
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille[i].length; j++) {
				System.out.print(this.grille[i][j]);
			}
			System.out.println();
		}
	}


	//Modifie aléatoirement les positions individuelles de chaque forme
	public void brasser() {
		for (int i = 0; i < formes.size(); i++) {
			formes.get(i).brasser();
		}
	}


	//Modifie le caractère actuel de dessin
	public void setCarActuel(char nouveauCar) {
		this.carActuel = nouveauCar;
	}


	//Appelle la méthode renverser de chaque forme
	public void renverserGrille() {
		for (int i = 0; i < formes.size(); i++) {
			//Renverser doit tenir en compte la hauteur de la grille
			formes.get(i).renverserForme(this.hauteur);

		}
	}


	public char getCarActuel() {
		return this.carActuel;
	}
}