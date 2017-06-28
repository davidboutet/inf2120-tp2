import java.util.ArrayList;

/**
 * Nom: David Boutet
 * Code permanent: BOUD31109107
 * Date: 28 juin 2017
 */
public class ListeGroupesChainee<T extends IGroupe> implements IListeGroupes<T> {
    //instance attributes
    private ArrayList<MaillonListe<T>> elements;
    private int nbrElements;


    //constructor
    public ListeGroupesChainee(){
        this.elements = new ArrayList<MaillonListe<T>>();
    }


    //methods
    @Override
    public boolean ajouter(T element) {
        return false;
    }

    @Override
    public int supprimerGroupe(int idGroupe) {
        return 0;
    }

    @Override
    public boolean supprimerElement(T element) {
        return false;
    }

    @Override
    public int obtenirIdGroupe(int position) {
        return 0;
    }

    @Override
    public T obtenirElement(int idGroupe, int position) {
        return null;
    }

    @Override
    public int taille() {
        return 0;
    }

    @Override
    public int taille(int idGroupe) {
        return 0;
    }

    @Override
    public int nbrGroupes() {
        return 0;
    }

    @Override
    public boolean groupeExiste(int idGroupe) {
        return false;
    }

    @Override
    public boolean elementExiste(T element) {
        return false;
    }

    @Override
    public boolean remplacer(T element1, T element2) {
        return false;
    }
}
