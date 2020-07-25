Michel Adant, p0681884
Maryna Starastsenka, p1240201


Modifications par rapport au plan de départ:

- Classe main Tp1
Utilisation de Scanner au lieu de la méthode lireCommande(String commande) qui a été prévue dans la classe Dessin du design initial.

- Classe Point
Ajout de la classe Point avec deux attributs X et Y afin de les utiliser dans un ArrayList points. L’ArrayList points est utilisé par chaque sous-classe Forme pour l’enregistrement des points d’une forme. Pour améliorer la cohésion de nos classes, on a voulu garder les calculs des points à l'intérieur des sous-classes de Forme, au lieu de faire le calcul à l'intérieur de la classe Dessin.

- Classe Dessin, ArrayList formes
Utilisation d’un ArrayList qui contient les formes instanciées, au lieu d'un tableau conventionnel. Cela a facilité l'ajout de nouvelles formes à la fin de la liste.
Au lieu d'utiliser la méthode insererForme, nous ajoutons les formes à dessiner dans l’ArrayList formes à partir de la Classe main Tp1.
Ex : nouveauDessin.formes.add(nouveauRect)

- Classe Forme, instance de type Random
Un objet de type Random a été utilisé afin de calculer des valeurs aléatoires pour la méthode brasser().

- Classe Forme, getPoints()
Ajout d’une méthode abstraite getPoints dans les sous-classes de Forme, appellée par la méthode dessiner() dans Dessin, et qui lui retourne les points à dessiner.

- Dans les sous-classes de Forme:
Nous n'avons pas eu besoin d'utiliser plusieurs getters des formes puisque les calculs se faisaient dorénavant à l'intérieur des sous-classes de Forme.
