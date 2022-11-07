package kansallispuistot;

/**
 * crc-kortin juttuja tähän
 * @author vilikelo
 * @version 25 Oct 2022
 *
 */
public class Reitit {
        private static final int MAX_REITTIA   = 10;
        private int              lkm           = 0;
        private String           tiedostonNimi = "";
        private Reitti           alkiot[]      = new Reitti[MAX_REITTIA];
        
        /**
         * alustaa reitit
         */
        public Reitit () {
            alkiot = new Reitti [MAX_REITTIA];
        }

    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Reitit reitit = new Reitit();

        Reitti karhunkierros1 = new Reitti(), karhunkierros2 = new Reitti();
        karhunkierros1.rekisteroi();    
        karhunkierros1.taytaKarhunkierrosTiedoilla();
        karhunkierros2.rekisteroi();   
        karhunkierros2.taytaKarhunkierrosTiedoilla();

        try {
            reitit.lisaa(karhunkierros1);
            reitit.lisaa(karhunkierros2);


            System.out.println("========== Jäsenet testi ==============");

            for (int i=0; i<reitit.getLkm(); i++) {
                Reitti reitti = reitit.anna(i);
                System.out.println("Reitti nro: " + i);
                reitti.tulosta(System.out);
            }

        } catch ( SailoException e ) {
            System.out.println(e.getMessage());
        } catch ( IndexOutOfBoundsException e ) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * @return reittien lukumäärän
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param reitti on reitti mitä lisätään
     * @throws SailoException oma poikkeusluokka joka antaa poikkeusviestin
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Reitit reitit = new Reitit();
     * Reitti karhunkierros1 = new Reitti(), karhunkierros2 = new Reitti();
     * reitit.getLkm() === 0;
     * </pre>
     */
    public void lisaa(Reitti reitti) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = reitti;
        lkm++;
    }
    
    /**
     * @param i monennes alkio
     * @return i:nnen alkion
     * @throws IndexOutOfBoundsException ??
     */
    public Reitti anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    /**
     * @throws SailoException poikkeusviesti
     */
    /*public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }*/
    
    /**
     * @param hakemisto
     * @throws SailoException poikkeusviesti
     */
    /*public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }*/




}
