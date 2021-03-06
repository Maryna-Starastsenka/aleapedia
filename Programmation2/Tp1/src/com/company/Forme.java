package com.company;

import java.util.ArrayList;
import java.util.Random;

public abstract class Forme {

	//Coordonnée X
	protected int posX; 

	//Coordonnée Y
	protected int posY;

	//Caractère utilisé par l'instance
	protected char car;

	protected static Random rand = new Random();

	//Constructeur de la classe abstraite
	public Forme (int posX, int posY, char car){
		this.posX = posX;
		this.posY = posY;
		this.car = car;
	}

	//retourne la position X
	public int getPosX(){
		return this.posX;
	}

	//retourne la position Y
	public int getPosY(){
		return this.posY;
	}

	public abstract ArrayList<Point> getPoints();

	//modifie la position X
	public void setPosX (int posX){
		this.posX = posX;
	}

	//modifie la position Y
	public void setPosY (int posY){
		this.posY = posY;
	}

	//retourne le caractère à utiliser pour dessiner la forme
	public char getCar(){
		return this.car;
	}

	public abstract void renverserForme(int hauteurGrille);

	public void brasser() {
		int aleatoireX = rand.nextInt(3) - 1 ;
		int aleatoireY = rand.nextInt(3) - 1 ;

		this.posX = this.posX + aleatoireX;
		this.posY = this.posY + aleatoireY;
	}
}