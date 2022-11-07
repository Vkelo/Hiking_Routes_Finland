package kansallispuistot;

/**
 * @author vilikelo
 * @version 26 Oct 2022
 *
 */
public class Kansallispuistot {
    
    private Reitit reitit = new Reitit();// reitit
    // private Kommentit kommentit = new Kommentit();  // Kommentit
    
    /**
     * @param args ei käytössä
     */
    public static void main(String [] args)  {
        Kansallispuistot kansallispuisto = new Kansallispuistot();
        
        Reitti karhunkierros1 = new Reitti ();
        Reitti karhunkierros2 = new Reitti ();
        
        karhunkierros1.rekisteroi();
        karhunkierros2.rekisteroi();
        
        karhunkierros1.taytaKarhunkierrosTiedoilla();
        karhunkierros2.taytaKarhunkierrosTiedoilla();
        
        try {
            kansallispuisto.lisaa(karhunkierros1);
            kansallispuisto.lisaa(karhunkierros2);
        } catch (SailoException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }

        
        for (int i=0; i<kansallispuisto.getKansallispuistoja(); i++) {
            Reitti reitti = kansallispuisto.annaReitti(i);
            reitti.tulosta(System.out);
        }
    }
    
    /**
     * @param reitti mikä viedään lisättävksi
     * @throws SailoException heittää poikkeuksen
     */
    public void lisaa(Reitti reitti) throws SailoException {
            reitit.lisaa(reitti);
    }
    
    /**
     * @return reittien määrä
     */
    public int getKansallispuistoja() {
        return reitit.getLkm();
    }
    
    /**
     * @param i mones reitti
     * @return mikä reitti
     */
    public Reitti annaReitti(int i) {
        return reitit.anna(i);
    }
}
