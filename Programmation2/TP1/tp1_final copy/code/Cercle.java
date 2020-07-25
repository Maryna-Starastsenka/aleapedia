import java.util.ArrayList;

public class Cercle extends Forme {

    private int rayon;

    //Constructeur d'un cercle
    public Cercle(int posX, int posY, int rayon, char car) {
        super(posX, posY, car);
        this.rayon = rayon;
    }

    @Override
    public ArrayList<Point> getPoints() {

        ArrayList<Point> points = new ArrayList<Point>();

        int m = 5 - 4 * this.rayon;
        int y = this.rayon;

        for (int x = 0; x <= y; x++) {

            if (m > 0) {
                y = y - 1;
                m = m - 8 * y;
            } else {
                m = m + 8 * x + 4;
            }
            points.add(new Point(x + this.posX, y + this.posY));
            points.add(new Point(y + this.posX, x + this.posY));
            points.add(new Point(-x + this.posX, y + this.posY));
            points.add(new Point(-y + this.posX, x + this.posY));
            points.add(new Point(x + this.posX, -y + this.posY));
            points.add(new Point(y + this.posX, -x + this.posY));
            points.add(new Point(-x + this.posX, -y + this.posY));
            points.add(new Point(-y + this.posX, -x + this.posY));
        }
        return points;
    }

        @Override
    public void renverserForme(int hauteurGrille) {
        this.posY = hauteurGrille-this.posY-1;
    }
}



