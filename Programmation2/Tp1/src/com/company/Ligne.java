package com.company;

import java.util.ArrayList;

public class Ligne extends Forme {

    private int finX;
    private int finY;

    //Constructeur d'une ligne
    public Ligne(int posX, int posY, int finX, int finY, char car) {
        super(posX, posY, car);
        this.finX = finX;
        this.finY = finY;
    }

    public int getFinX() { return finX; }

    public int getFinY() { return finY; }

    @Override
    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>();
        int deltaX = this.finX-this.posX;
        int deltaY = this.finY-this.posY;
        int debutLigneX, finLigneX;
        int debutLigneY, finLigneY;

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (this.posX < this.finX) {
                debutLigneX = this.posX;
                finLigneX = this.finX;
            } else {
                debutLigneX = this.finX;
                finLigneX = this.posX;
            }
                for (int x = debutLigneX; x <= finLigneX; x++) {
                    points.add(new Point(x, (Math.round((float) deltaY/
                            deltaX * (x - this.posX) + this.posY))));
            }
        }

        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            if (this.posY < this.finY) {
                debutLigneY = this.posY;
                finLigneY = this.finY;
            } else {
                debutLigneY = this.finY;
                finLigneY = this.posY;
            }
                for (int y = debutLigneY; y <= finLigneY; y++) {
                    points.add(new Point((Math.round((float) deltaX /
                            deltaY * (y - this.posY) + this.posX)), y));
                }
        }
        return points;
    }

    @Override
    public void renverserForme(int hauteurGrille) {
        this.posY = hauteurGrille - this.posY - 1;
        this.finY = hauteurGrille - this.finY - 1;
    }

    @Override
    public void brasser() {

        int aleatoireX = rand.nextInt(3) - 1 ;
        int aleatoireY = rand.nextInt(3) - 1 ;

        this.posX += aleatoireX;
        this.posY += aleatoireY;
        this.finX += aleatoireX;
        this.finY += aleatoireY;

    }
}

