import java.util.ArrayList;

public class Lettre extends Forme {

    private char lettre;

    private int largeur = 8;

    private int hauteur = 12;

    //Nombres binaires sous forme de String
    private String[] tableauBinaire = new String[hauteur];

    private byte[] tableauByte;

    //Constructeur d'une lettre
    public Lettre(int posX, int posY, char lettre, char car) {

        super(posX, posY, car);
        this.lettre = lettre;

        //Remplit et convertit les nombres en représentation binaire
        this.tableauByte = Police8x12.getImage(lettre);
        for (int i=0; i<tableauByte.length; i++) {
            String contenu = "";
            for (int j = 7; j >= 0; j--) {
                contenu += (tableauByte[i] >> j) & 1;
            }
                this.tableauBinaire[i] = contenu;
        }

	}

    @Override
    public ArrayList<Point> getPoints() {

        ArrayList<Point> points = new ArrayList<Point>();

        for (int i=0;i<tableauBinaire.length;i++) {
            for (int j=0;j<tableauBinaire[i].length();j++) {
                if (tableauBinaire[i].charAt(j) == '1') {
                    points.add(new Point(posX+j, posY+i));
                }
            }
        }

        return points;
    }

    @Override
    public void renverserForme(int hauteurGrille){

        for(int i = 0; i < this.tableauBinaire.length / 2; i++) {
            //Inverse le tableau (les lignes d'une lettre)
            String temp = this.tableauBinaire[i];
            this.tableauBinaire[i] =
            this.tableauBinaire[this.tableauBinaire.length-i-1];
            this.tableauBinaire[this.tableauBinaire.length-i-1] = temp;

        }

        this.posY = hauteurGrille - hauteur - posY;
    }
}
