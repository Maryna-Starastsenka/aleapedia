import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class CorrecteurOrthographique {

    // ATTRIBUTS et SETTERS
    private Set<String> ensembleMotsConnu;
    private Map<String, Set<String>> listeAssociativeMotsTronques;

    private String nomTexteACorriger;
    private String nomDictionnaire;

    private ArrayList<String> texteCorrige;
    private ArrayList<String> separateurs;

    public void setNomTexteACorriger(String nomTexteACorriger) {
        this.nomTexteACorriger = nomTexteACorriger;
    }

    public void setNomDictionnaire(String nomDictionnaire) {
        this.nomDictionnaire = nomDictionnaire;
    }

    // CONSTRUCTEUR
    public CorrecteurOrthographique(String nomTexteACorriger, String nomDictionnaire) {
        setNomTexteACorriger(nomTexteACorriger);
        setNomDictionnaire(nomDictionnaire);
    }

    // Parcourir le fichier dictionnaire et construire les structures de données


    public void construireStructuresDonnees() {
        this.ensembleMotsConnu = new HashSet<>();
        this.listeAssociativeMotsTronques = new HashMap<>();

        try {
            var fileReader = new FileReader(this.nomDictionnaire);
            var br = new BufferedReader(fileReader);
            String motCourantDuDico;

            // lire tous les mots du fichier dictionnaire
            while ((motCourantDuDico = br.readLine()) != null) {
                String motCourantDuDicoMinuscule = motCourantDuDico.toLowerCase();
                // construire l'ensemble de vrais mots connus
                this.ensembleMotsConnu.add(motCourantDuDicoMinuscule);
                // construire la liste associative contenant les corrections possibles
                construireListeAssociative(motCourantDuDicoMinuscule);
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l’ouverture du fichier");
        }
    }

    // Association des mots tronques à leurs corrections possibles
    private void construireListeAssociative(String motOriginalMinuscule) {
        for (int i = 0; i < motOriginalMinuscule.length(); i++) {
            String motTronque = tronquer(motOriginalMinuscule, i);

            if (estDansListeAssociative(motTronque)) {
                associerNouvelleCorrectionAMotTronque(motOriginalMinuscule, motTronque);
            } else {
                ajouterNouveauMotTronque(motOriginalMinuscule, motTronque);
            }
        }
    }

    private void ajouterNouveauMotTronque(String motOriginalMinuscule, String motTronque) {
        var motsOriginaux = new HashSet<String>();
        motsOriginaux.add(motOriginalMinuscule);
        this.listeAssociativeMotsTronques.put(motTronque, motsOriginaux);
    }

    private void associerNouvelleCorrectionAMotTronque(String motOriginalMinuscule, String motTronque) {
        this.listeAssociativeMotsTronques.get(motTronque).add(motOriginalMinuscule);
    }

    // Enrichir le texte de l'ensemble de ses corrections
    public void corrigerTexte() {
        var patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F]+");
        var patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F]+");

        this.texteCorrige = new ArrayList<>();
        this.separateurs = new ArrayList<>();

        try {
            var fileReader = new FileReader(this.nomTexteACorriger);
            var scan = new Scanner(fileReader);
            scan.useDelimiter("\\b");

            while (scan.hasNext(patternMot)) {
                String motDuTexte = scan.next(patternMot);
                String motDuTexteMinuscule = motDuTexte.toLowerCase();

                this.separateurs.add(scan.next(patternSeparateur));

                // Le mot est valide
                if (estDansMotsConnus(motDuTexteMinuscule)) {
                    // Aucune correction nécessaire
                    this.texteCorrige.add(motDuTexte);
                } else {
                    var corrections = new HashSet<String>();

                    // CAS A : le mot est dans la liste associative (il manque une seule lettre)
                    if (estDansListeAssociative(motDuTexteMinuscule)) {
                        ajouterCorrectionsDuMotTronque(motDuTexteMinuscule, corrections);
                    }

                    // Pour chaque troncation du mot
                    for (int i = 0; i < motDuTexte.length(); i++) {
                        String motACorriger = tronquer(motDuTexte, i);

                        // CAS B1 : une lettre a été remplacée par une autre
                        if (estDansListeAssociative(motACorriger)) {
                            ajouterCorrectionsDuMotTronque(motACorriger, corrections);
                        }

                        // CAS B2 : il y a une lettre en trop dans le mot
                        if (estDansMotsConnus(motACorriger)) {
                            corrections.add(motACorriger);
                        }
                    }

                    // Si le mot erroné a des corrections, on le formate d'une certaine façon
                    // On met un point d'interrogation sinon
                    this.texteCorrige.add(
                        !corrections.isEmpty()
                                ? formaterMotEtSuggestions(motDuTexte, corrections)
                                : formaterMotEtSuggestionInconnue(motDuTexte)
                    );
                }
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimerTexteCorrigeSurConsole() {
        for (int i = 0; i < this.texteCorrige.size(); i++) {
            System.out.print(this.texteCorrige.get(i));
            System.out.print(this.separateurs.get(i));
        }
    }

    private String tronquer(String mot, int index) {
        return (mot.substring(0, index) + mot.substring(index + 1)).toLowerCase();
    }

    private void ajouterCorrectionsDuMotTronque(String motAVerifier, Set<String> motsCorriges) {
        motsCorriges.addAll(listeAssociativeMotsTronques.get(motAVerifier));
    }

    private String formaterMotEtSuggestionInconnue(String motDuTexte) {
        return "[" + motDuTexte + " => (?)]";
    }

    private String formaterMotEtSuggestions(String motDuTexte, Set<String> corrections) {
        return "[" + motDuTexte + " => " + corrections.toString()
                .replace("[", "").replace(" ", "");
    }

    private boolean estDansListeAssociative(String motDuTexteMinuscule) {
        return listeAssociativeMotsTronques.containsKey(motDuTexteMinuscule);
    }

    private boolean estDansMotsConnus(String motDuTexteMinuscule) {
        return ensembleMotsConnu.contains(motDuTexteMinuscule);
    }
}
