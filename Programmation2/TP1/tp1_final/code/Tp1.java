import java.util.Scanner;
public class Tp1 {

    public static void main(String[] args) {

        //Lit les commandes tapées
        Scanner scan = new Scanner(System.in);
        Dessin nouveauDessin = null;
        int posX, posY, finX, finY, largeur, hauteur, cote, rayon;
        String instruction;
        char lettre;

        do {
            instruction = scan.nextLine();

            if (instruction.equals("fin")) {

                break;
            }
            //Sépare chaque instruction dans un tableau String
            String[] motsInstruction = instruction.split(" ");

            //Si aucun dessin n'a été initialisé, afficher erreur
            if (!motsInstruction[0].equals("init") && nouveauDessin == null) {
                System.out.println("ERREUR: Aucune surface definie");
                continue;
            }

            //Premier mot
            switch (motsInstruction[0]) {
                case "init":
                    //nombre de colonnes
                    largeur = Integer.parseInt(motsInstruction[1]);
                    //nombre de lignes
                    hauteur = Integer.parseInt(motsInstruction[2]);
                    nouveauDessin = new Dessin(largeur, hauteur);


                    break;
                case "car":
                    char caractere = motsInstruction[1].charAt(0);
                    nouveauDessin.setCarActuel(caractere);
                    break;

                    
                case "ajouter":

                    //Position de la forme
                    posX = Integer.parseInt(motsInstruction[2]);
                    posY = Integer.parseInt(motsInstruction[3]);

                    //Vérifie le deuxième mot après ajouter
                    switch (motsInstruction[1]) {

                        case "rectangle":
                            largeur = Integer.parseInt(motsInstruction[4]);
                            hauteur = Integer.parseInt(motsInstruction[5]);
                            Forme nouveauRect = new Rectangle(posX, posY, 
                                largeur, hauteur, nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouveauRect);
                            break;

                        case "carre":
                            cote = Integer.parseInt(motsInstruction[4]);
                            Rectangle nouveauCarre = new Rectangle(posX, posY,
                             cote, cote, nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouveauCarre);
                            break;

                        case "ligne":
                            finX = Integer.parseInt(motsInstruction[4]);
                            finY = Integer.parseInt(motsInstruction[5]);
                            Ligne nouvelleLigne = new Ligne(posX, posY, finX,
                             finY, nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouvelleLigne);
                            break;

                        case "cercle":
                            rayon = Integer.parseInt(motsInstruction[4]);
                            Cercle nouveauCercle = new Cercle(posX, posY,
                             rayon, nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouveauCercle);
                            break;

                        case "lettre":
                            lettre = motsInstruction[4].charAt(0);
                            Lettre nouvelleLettre = new Lettre(posX, posY,
                             lettre, nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouvelleLettre);
                            break;

                        case "texte":
                            Texte nouveauTexte = new Texte(posX, posY,
                             motsInstruction[4], nouveauDessin.getCarActuel());
                            nouveauDessin.ajouterForme(nouveauTexte);
                            break;

                        //Deuxième mot invalide
                        default:
                            System.out.println("ERREUR: Commande invalide");

                    }

                    break;


                case "dessiner":
                    nouveauDessin.dessiner();
                    break;


                case "renverser":
                    nouveauDessin.renverserGrille();
                    break;


                case "brasser":
                    nouveauDessin.brasser();
                    break;


                default:
                    System.out.println("ERREUR: Commande invalide");

            }
        }

        while (scan.hasNext());

    }
}