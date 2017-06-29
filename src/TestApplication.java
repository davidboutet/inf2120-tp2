/**
 * Created by DavidBoutet on 17-06-28.
 */
public class TestApplication {
    public static void main(String [] args){
        ListeGroupesChainee listChaine = new ListeGroupesChainee<IGroupe>();

        AbonneRevue a1 = new AbonneRevue(0, "David Boutet");
        AbonneRevue a2 = new AbonneRevue(0, "David Boutet2");

        listChaine.ajouter(a1);
        listChaine.ajouter(a2);
//        println(listChaine.groupeExiste(0));
    }

    public static void println(Object o){
        System.out.println(o);
    }
}
