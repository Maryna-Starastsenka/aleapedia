import java.util.ArrayList;

public class Texte extends Forme {

    private String texte;

    private Lettre[] phrase;

    //Constructeur d'un texte
    public Texte(int posX, int posY, String texte, char car) {

            super(posX, posY, car);
            this.texte = texte;
            this.phrase = new Lettre[texte.length()];

            for (int i = 0; i < texte.length(); i++) {
                this.phrase[i] = 
                new Lettre(this.posX + i*8, this.posY, texte.charAt(i), car);
            }
    }

    @Override
    public ArrayList<Point> getPoints() {

        ArrayList<Point> pointsTexte = new ArrayList<Point>();
        //Appelle le getPoints de chaque lettre, concatène les ArrayLists
        for (int i=0; i<phrase.length;i++) {

            pointsTexte.addAll(this.phrase[i].getPoints());
        }

        return pointsTexte;
    }

    @Override
    public void renverserForme(int hauteurGrille) {
        for (int i=0; i<phrase.length;i++) {
            phrase[i].renverserForme(hauteurGrille);
        }
    }

    @Override
    public void brasser() {

        int aleatoireX = rand.nextInt(3) - 1 ;
        int aleatoireY = rand.nextInt(3) - 1 ;

        for (int i=0; i<phrase.length;i++) {
            phrase[i].setPosX(phrase[i].getPosX()+aleatoireX);
            phrase[i].setPosY(phrase[i].getPosY()+aleatoireY);
        }
    }
}