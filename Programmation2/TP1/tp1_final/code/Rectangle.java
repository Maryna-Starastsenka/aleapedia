
import java.util.ArrayList;

public class Rectangle extends Forme {

	private int largeur;

	private int hauteur;

	//Constructeur, sert aussi pour la forme Carré
	public Rectangle(int posX, int posY, int largeur, int hauteur, char car) {
		super(posX, posY, car);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}


	@Override
	public ArrayList<Point> getPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int y = this.posY; y < this.posY + this.hauteur; y++) {
			for (int x = this.posX; x < this.posX + this.largeur; x++) {
				points.add(new Point(x, y));
			}
		}
		return points;
	}


	@Override
	public void renverserForme(int hauteurGrille) {
		this.posY = hauteurGrille-this.posY-this.hauteur;
	}
}