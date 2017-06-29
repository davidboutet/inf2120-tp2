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
        Boolean ajouter = false;
        Integer gId = element.getId();
        //ajoute au groupe existant
        if(groupeExiste(gId)){
            ajouterAuGroupe(element.getId(), element);
        }else{
            MaillonGroupe<T> maillonGroupe = new MaillonGroupe<T>(element);
            MaillonListe<T> maillonListe = new MaillonListe<T>(maillonGroupe);
            ajouter = this.elements.add(maillonListe);
            this.nbrElements++;
        }
        return ajouter;
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
        return this.nbrElements;
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
        Boolean existe = false;
        if(this.elements.size() > 0){
            int i = 0;
            while (i < this.elements.size() && !existe){
                Integer gId = this.elements.get(i).getInfo().getInfo().getId();
                if(idGroupe == gId){
                    existe = true;
                }
                i++;
            }
        }
        return existe;
    }

    @Override
    public boolean elementExiste(T element) {
        return false;
    }

    @Override
    public boolean remplacer(T element1, T element2) {
        return false;
    }

    //private methods
    //RENDU ICI LE CAVEEEEEE
    private boolean ajouterAuGroupe(int groupeId, T element){
        Boolean ajouter = false,
                inGroup = false;
        int i = 0;
        while (i < this.elements.size() && !ajouter){
            MaillonListe<T> maillonListe = this.elements.get(i);
            MaillonGroupe<T> maillon = maillonListe.getInfo();
            Integer gId = maillon.getInfo().getId();
            if(gId == groupeId){
                if(maillon.getSuivant() == null){
                    MaillonGroupe<T> nouveauMaillon = new MaillonGroupe<T>(element);
                    maillon.setSuivant(nouveauMaillon);
                    maillon.setInfo(nouveauMaillon.getInfo());
                }else {
                    while (maillon.getSuivant() != null){
                        if(maillon.getInfo().equals(element)){
                            inGroup = true;
                        }
                        println(maillon.getInfo().toString());
                        maillon = maillon.getSuivant();
//                    if(!inGroup){
//
//                    }
                    }
                }

            }
        }
        return ajouter;
    }

    //test only
    public static void println(Object o){
        System.out.println(o);
    }
}
