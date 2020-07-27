package com.company;

import java.util.ArrayList;

public class Lettre extends Forme {

    private int hauteur = 12;
    //private int largeur = 8;
    private char lettre;
    private String[] tableauBinaire = new String[hauteur];
    private byte [] tableauByte;

    //Constructeur d'une lettre
    public Lettre(int posX, int posY, char lettre, char car) {
        super(posX, posY, car);
        this.lettre = lettre;
        this.tableauByte = Police8x12.getImage(lettre);

        for (int i = 0; i < tableauByte.length; i++) {
            String contenu = "";
            for (int j = 7; j >= 0 ; j--) {
                contenu += (tableauByte[i] >> j) & 1;
            }
            this.tableauBinaire[i] = contenu;
        }
    }

    @Override
    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>();
        for (int i = 0; i < tableauBinaire.length; i++) {
            for (int j = 0; j < tableauBinaire[i].length(); j++) {
                if (tableauBinaire[i].charAt(j) == '1') {
                    points.add(new Point(this.posX + j, this.posY + i));
                }
            }
        }
        return points;
    }

    @Override
    public void renverserForme(int hauteurGrille) {
        for (int i = 0; i < this.tableauBinaire.length/2; i++) {
            String temp = this.tableauBinaire[i];
            this.tableauBinaire[i] = this.tableauBinaire[this.tableauBinaire.length - i - 1];
            this.tableauBinaire[this.tableauBinaire.length - i - 1] = temp;
        }
        this.posY = hauteurGrille - hauteur - posY;

    }

}
