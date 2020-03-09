import java.util.ArrayList;
import java.util.Random;


public abstract class Forme {

	//Génère des nombres aléatoires
	protected static Random rand = new Random();

	//Coordonnée X
	protected int posX;

	//Coordonnée Y
	protected int posY;

	//Caractère utilisé par l'instance
	protected char car;

	//Constructeur de la classe abstraite
	public Forme (int posX, int posY, char car){
		this.posX = posX;
		this.posY = posY;
		this.car = car;
	}

	public int getPosX(){
		return this.posX;
	}

	//modifie la position X
	public void setPosX (int posX){
		this.posX = posX;
	}

	public int getPosY(){
		return this.posY;
	}

	//modifie la position Y
	public void setPosY (int posY){
		this.posY = posY;
	}

	//Sert à obtenir les points calculés
	public abstract ArrayList<Point> getPoints();

	//retourne le caractère à utiliser pour dessiner la forme
	public char getCar(){
		return this.car;
	}

	public abstract void renverserForme(int hauteurGrille);

	public void brasser() {

		//Génère deux valeurs aléatoires entre -1 et 1
		int aleatoireX = rand.nextInt(3)-1;
		int aleatoireY = rand.nextInt(3)-1;

		this.posX = this.posX + aleatoireX;
		this.posY = this.posY + aleatoireY;

	}
}

