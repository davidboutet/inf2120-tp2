/**
 * Created by DavidBoutet on 17-06-28.
 */
public class TestApplication {
    public static void main(String [] args){
        ListeGroupesChainee listChaine = new ListeGroupesChainee<IGroupe>();

        AbonneRevue a1 = new AbonneRevue(0, "David Boutet");
        AbonneRevue a2 = new AbonneRevue(0, "David Boutet2");
        AbonneRevue a3 = new AbonneRevue(0, "David Boutet3");
        AbonneRevue a6 = new AbonneRevue(0, "David Boutetremplacer");
        AbonneRevue a4 = new AbonneRevue(5, "David Boutet4");
        AbonneRevue a5 = new AbonneRevue(5, "David Boutet5");

        listChaine.ajouter(a1);
        listChaine.ajouter(a2);
        listChaine.ajouter(a3);
        listChaine.ajouter(a4);
        listChaine.ajouter(a5);





    }

    public static void println(Object o){
        System.out.println(o);
    }
}
