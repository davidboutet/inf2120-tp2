import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Cette classe implemente l'interface IListeGroupes pour la gestion d'une liste de groupes.
 * @author David Boutet
 * Code permanent: BOUD31109107
 * Date: 28 juin 2017
 * @version 1.0.0
 */
public class ListeGroupesChainee<T extends IGroupe> implements IListeGroupes<T> {
    //instance attributes
    private ArrayList<MaillonListe<T>> elements;
    private int nbrElements;
+
    //constructor
    public ListeGroupesChainee(){
        this.elements = new ArrayList<MaillonListe<T>>();
    }

    //methods
    /**
     * Ajoute l'element donne a cette ListeGroupes.
     *
     * Si le groupe dont le numero d'identification est element.getId() existe deja
     * dans cette listeGroupes, l'element est ajoute A LA FIN du groupe existant
     * SI ET SEULEMENT S'IL n'existe aucun element de ce groupe etant egal a
     * l'element donne. Autrement, l'element donne n'est pas ajoute.
     *
     * Si le groupe element.getId() n'existe pas dans cette listeGroupes, un nouveau
     * groupe est ajoute (A LA FIN de cette listeGroupes) et l'element donne devient
     * le premier element du nouveau groupe element.getId().
     *
     * @param element l'element a ajouter a cette listeGroupes.
     * @return true si l'element donne est ajoute, false sinon.
     * @throws NullPointerException si l'element donne est null.
     */
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

    /**
     * Supprime de cette listeGroupes le groupe dont l'id est l'idGroupe donne.
     * Si le groupe ayant l'idGroupe donne n'existe pas dans cette listeGroupes,
     * celle-ci demeure inchangee.
     *
     * @param idGroupe le numero d'identification du groupe a supprimer.
     * @return le nombre d'elements dans le groupe supprime ou 0 si la suppression
     *         n'a pas lieu.
     */
    @Override
    public int supprimerGroupe(int idGroupe) {
        Integer elementSupprime = 0,
                i = 0;
        Boolean trouver = false;
        if(groupeExiste(idGroupe)){
            while (i < this.elements.size() && !trouver){
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                Integer gId = maillon.getInfo().getId();
                if(idGroupe == gId){
                    trouver = true;
                    elementSupprime = taille(idGroupe);
                    this.nbrElements = this.nbrElements - elementSupprime;
                    this.elements.remove(i);
                }
                i++;
            }
        }
        return elementSupprime;
    }

    /**
     * Supprime l'element donne de son groupe d'appartenance.
     *
     * Si apres suppression de l'element le groupe est vide, il est supprime
     * de cette listeGroupes.
     *
     * Si le groupe element.getId() n'existe pas dans cette listeGroupes ou
     * si l'element donne n'est pas dans le groupe element.getId(), cette
     * listeGroupes demeure inchangee.
     *
     * @param element l'element a supprimer de cette listeGroupes.
     * @return true si l'element est supprime, false sinon.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean supprimerElement(T element) {
        Boolean supprimer = false;
        if(this.elementExiste(element)){
            int i = 0;
            while (i < this.elements.size() && !supprimer){
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                MaillonGroupe<T> precedent = maillonListe.getInfo();

                while (maillon != null && !supprimer){
                    if(maillon.getInfo().equals(element)){
                        if(precedent.getSuivant() != null){
                            precedent.setSuivant(precedent.getSuivant().getSuivant());
                        }else{
                            precedent.setSuivant(precedent.getSuivant());
                        }
                        supprimer = true;
                        this.nbrElements--;
                        if(maillonListe.getInfo().getInfo() == null){
                            this.supprimerGroupe(maillon.getInfo().getId());
                        }
                    }

                    //getSuivant() seuelement si maillon est different de precedent
                    if (!maillon.equals(precedent)){
                        precedent = precedent.getSuivant();
                    }
                    maillon = maillon.getSuivant();
                }
                i++;
            }
        }
        return supprimer;
    }

    /**
     * Permet d'obtenir le numero d'identification (l'id) du groupe ayant la
     * postion donnee dans cette listeGroupes, si cette position est valide.
     * Une position valide doit etre comprise entre 0 et nbrGroupes() - 1
     * inclusivement.
     *
     * @param position la position, dans cette listeGroupes, du groupe dont on
     *                 veut obtenir l'id.
     * @return l'id du groupe ayant la position donnee.
     * @throws NoSuchElementException si la position donnee n'est pas une position
     *         valide dans cette listeGroupes.
     */
    @Override
    public int obtenirIdGroupe(int position) throws NoSuchElementException{
        Integer gId;
        if(position < this.elements.size() && position >= 0){
            MaillonListe<T> liste = this.elements.get(position);
            gId = liste.getInfo().getInfo().getId();
        }else {
            throw new NoSuchElementException();
        }
        return gId;
    }

    /**
     * Permet d'obtenir l'element qui se trouve a la position donnee, dans le
     * groupe dont l'id est l'idGroupe donne, si la position donnee est valide.
     * Une position valide doit etre comprise entre 0 et taille(idGroupe) - 1
     * inclusivement.
     *
     * @param idGroupe l'id du groupe dans lequel rechercher l'element a retourner.
     * @param position la postition de l'element a retourner, dans le groupe dont
     *                  l'id est l'idGroupe donne.
     * @return l'element a la position donnee, dans le groupe dont l'id est
     *         l'idGroupe donne.
     * @throws NoSuchElementException si le groupe ayant l'idGroupe donne n'existe
     *         pas dans cette listeGroupes ou si la position donnee n'est pas un
     *         indice valide dans le groupe dont l'id est l'idGroupe donne.
     */
    @Override
    public T obtenirElement(int idGroupe, int position){
        T element = null;
        if(groupeExiste(idGroupe)){
            Boolean trouver = false;
            int i = 0;
            while (i < this.elements.size() && !trouver){
                int z = 1;
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                int gId = maillon.getInfo().getId();
                if(gId == idGroupe){
                    while (maillon != null && !trouver){
                        if(z == position){
                            element = maillon.getInfo();
                            trouver = true;
                        }
                        maillon = maillon.getSuivant();
                        z++;
                    }
                    if (!trouver){
                        throw new NoSuchElementException();
                    }
                }
                i++;
            }
        }else{
            throw new NoSuchElementException();
        }
        return element;
    }

    /**
     * Permet d'obtenir le nombre d'elements presents dans cette listeGroupes.
     *
     * @return le nombre d'elements presents dans cette listeGroupes.
     */
    @Override
    public int taille() {
        return this.nbrElements;
    }

    /**
     * Permet d'obtenir le nombre d'elements presents dans le groupe ayant
     * comme numero d'identification l'idGroupe donne.
     *
     * @param idGroupe le numero d'identification du groupe dont on veut
     *                 le nombre d'elements.
     * @return le nombre d'elements dans le groupe ayant l'idGroupe donne ou
     *         -1 si l'idGroupe donne ne correspond a aucun groupe dans cette
     *         listeGroupes.
     */
    @Override
    public int taille(int idGroupe) {
        Boolean trouver = false;
        int taille = -1,
            i = 0;
        if (groupeExiste(idGroupe)){
            while (i < this.elements.size() && !trouver){
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                Integer gId = maillon.getInfo().getId();
                if(idGroupe == gId){
                    taille = 0;
                    trouver = true;
                    while(maillon != null){
                        taille++;
                        maillon = maillon.getSuivant();
                    }
                }
                i++;
            }
        }
        return taille;
    }

    /**
     * Permet d'obtenir le nombre de groupes dans cette listeGroupes.
     *
     * @return le nombre de groupes dans cette listeGroupes.
     */
    @Override
    public int nbrGroupes() {
        return this.elements.size();
    }

    /**
     * Teste si le groupe ayant l'idGroupe donne existe dans cette listeGroupes.
     *
     * @param idGroupe le groupe dont on teste l'existence.
     * @return true si le groupe ayant l'idGroupe donne existe dans cette
     *         listeGroupes, false sinon.
     */
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

    /**
     * Teste si l'element donne existe dans cette listeGroupes (s'il existe dans
     * le groupe element.getId() de cette listeGroupes). Un element e1 existe dans
     * un groupe si ce groupe contient un element e2 tel que e1.equals(e2)
     * retourne true.
     *
     * @param element l'element dont on teste l'existence.
     * @return true si element existe dans cette listeGroupes, false sinon.
     * @throws NullPointerException si element est null.
     */
    @Override
    public boolean elementExiste(T element) {
        Boolean trouver = false;
        int i = 0;
        if (groupeExiste(element.getId())){
            while (i < this.elements.size() && !trouver){
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                while (maillon != null){
                    if(maillon.getInfo().equals(element)){
                        trouver = true;
                    }
                    maillon = maillon.getSuivant();
                }
                i++;
            }
        }
        return trouver;
    }

    /**
     * Remplace l'element1 de cette listeGroupes par l'element2 donne.
     *
     * - Si element1 n'existe pas dans le groupe dont l'id est element1.getId(),
     *   le remplacement n'a pas lieu.
     *
     * - Si l'id de element1 est different de l'id de l'element2, le remplacement
     *   n'a pas lieu.
     *
     * - Si le remplacement avait pour effet de causer un doublon dans le groupe,
     *   le remplacement ne n'a pas lieu.
     *
     * @param element1 l'element a remplacer par element2.
     * @param element2 l'element qui remplace l'element1.
     * @return true si le remplacement a eu lieu, false sinon.
     * @throws NullPointerException si element1 ou element2 est null.
     */
    @Override
    public boolean remplacer(T element1, T element2) {
        Boolean remplacer = false;
        if(element1.getId() == element2.getId() && this.elementExiste(element1)){
            int i = 0;
            while (i < this.elements.size() && !remplacer){
                MaillonListe<T> maillonListe = this.elements.get(i);
                MaillonGroupe<T> maillon = maillonListe.getInfo();
                MaillonGroupe<T> precedent = maillonListe.getInfo();

                while (maillon != null && !remplacer){
                    if(maillon.getInfo().equals(element1)){
                        MaillonGroupe<T> maillonSuivant = maillon.getSuivant();
                        MaillonGroupe<T> maillonRemplacant = new MaillonGroupe<T>(element2, maillonSuivant);

                        precedent.setSuivant(maillonRemplacant);
                        maillon.setSuivant(null);
                        remplacer = true;
                    }

                    //getSuivant() seulement si maillon est different de precedent
                    if (!maillon.equals(precedent)){
                        precedent = precedent.getSuivant();
                    }
                    maillon = maillon.getSuivant();
                }
                i++;
            }
        }
        return remplacer;
    }

    //private methods
    /**
     * Ajoute l'element a la fin du groupe existant et retourne true si ajouter
     *
     * @param groupeId id du groupe
     * @param element l'element a ajouter au groupe
     * @return true si l'element a été ajouter au groupe
     */
    private boolean ajouterAuGroupe(int groupeId, T element){
        Boolean ajouter = false,
                inGroup = false;
        int i = 0;
        while (i < this.elements.size() && !ajouter){
            MaillonListe<T> maillonListe = this.elements.get(i);
            MaillonGroupe<T> maillon = maillonListe.getInfo(),
                             dernierMaillon = maillonListe.getInfo();

            if(maillon.getInfo().getId() == groupeId){
                while (maillon != null){
                    //doublons check
                    if (maillon.getInfo().equals(element)){
                        inGroup = true;
                    }
                    //focus sur dernier maillon de la liste
                    if(maillon.getSuivant() != null){
                        dernierMaillon = maillon.getSuivant();
                    }
                    //change le maillon
                    maillon = maillon.getSuivant();
                }

                if(!inGroup){
                    MaillonGroupe<T> nouveauMaillon = new MaillonGroupe<T>(element);
                    dernierMaillon.setSuivant(nouveauMaillon);
                    this.nbrElements++;
                    ajouter = true;
                }
            }
            i++;
        }
        return ajouter;
    }
}
