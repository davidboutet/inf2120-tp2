/**
 * Created by DavidBoutet on 17-06-28.
 */
public class TestApplication {
    public static void main(String [] args){
        ListeGroupesChainee listChaine = new ListeGroupesChainee<IGroupe>();

        AbonneRevue a1 = new AbonneRevue(1, "David Boutet");

        listChaine.ajouter(a1);
    }

    public static void println(Object o){
        System.out.println(o);
    }
}
