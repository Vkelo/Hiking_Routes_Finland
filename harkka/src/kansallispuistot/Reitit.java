package kansallispuistot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * crc-kortin juttuja tähän
 * @author vilikelo
 * @version 25 Oct 2022
 *
 */
public class Reitit {
        private static final int MAX_REITTIA   = 10;
        private int              lkm           = 0;
        //private String           tiedostonNimi = "";
        private Reitti           alkiot[]      = new Reitti[MAX_REITTIA];
        private String tiedostonPerusNimi = "reitit";
        private boolean muutettu = false;
        private String kokoNimi = "";
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
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
            }


            System.out.println("========== Jäsenet testi ==============");

            for (int i=0; i<reitit.getLkm(); i++) {
                Reitti reitti = reitit.anna(i);
                System.out.println("Reitti nro: " + i);
                reitti.tulosta(System.out);
            }
            
            try {
                reitit.tallenna("data");    
            } catch (SailoException e) {
               // e.printStackTrace();
               System.err.println(e.getMessage());
            }
            
    }
    
    /**
     * tallentaa reitit lisätessä levylle
     * @param hakemisto hakemiston nimi
     * @throws SailoException Sailo
     */
    public void tallenna (String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/reitit" + "/reitit.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))){
            for (int i = 0; i < getLkm(); i++) {
                Reitti reitti = this.anna(i);
                fo.println(reitti.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    
    /**
     * @throws SailoException de
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());

        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Reitti reitti : alkiot) {
                fo.println(reitti.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }    
    
    
    /**
     * @author vilikelo
     * @version 6 Dec 2022
     *
     */
    public class JasenetIterator implements Iterator<Reitti> {
        private int kohdalla = 0;

        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Reitti next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }

        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }

    /**

    * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    public Iterator<Reitti> iterator() {
        return new JasenetIterator();
    }


    /**
     * Lukee jäsenistön tiedostosta. 
     * @param hakemisto on luettava tiedosto
     * @throws SailoException jos lukeminen epäonnistuu
     */

    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/reitit.dat";
        File ftied = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.equals("") || s.charAt(0) == ';') continue;
                Reitti reitti = new Reitti();
                reitti.parse(s);
                lisaa(reitti);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + nimi + " ei aukea");
        //} catch ( IOException e ) {
        //   throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
        muutettu = false;
    }
    
    /**
     * @return reittien lukumäärän
     */
    public int getLkm() {
        return lkm;
    }
   
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Jasenet jasenet = new Jasenet(); 
     *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Reitti> etsi(String hakuehto, int k) { 
        Collection<Reitti> loytyneet = new ArrayList<Reitti>(); 
        for (Reitti reitti : this.alkiot) { 
            loytyneet.add(reitti);  
        } 
        return loytyneet; 
    }

    
    /**
     * @param reitti on reitti mitä lisätään
     * @example
     * <pre name="test">
     * Reitit reitit = new Reitit();
     * Reitti karhunkierros1 = new Reitti(), karhunkierros2 = new Reitti();
     * reitit.getLkm() === 0;
     * </pre>
     */
    public void lisaa(Reitti reitti) {
        if (lkm >= alkiot.length) Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = reitti;
        lkm++;
        muutettu = true;
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
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * @param reitti mitä lisätään
     * @throws SailoException jos kusee
     */
    public void korvaaTaiLisaa(Reitti reitti) throws SailoException {
        int id = reitti.getId();
        for (int i = 0; i < lkm; i++) {
            if(alkiot[i].getId()== id) {
                alkiot[i] = reitti;
                muutettu = true;
                return;
            }
        }
        lisaa (reitti);
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    /**
     * @return rfe
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    /**
     * @param ehto hakuehto
     * @return lista löytyneistä reiteistä
     */
    public Collection<Reitti> etsi(String ehto) {
        ArrayList<Reitti> loytyneet = new ArrayList<Reitti>();
        for (int i = 0; i < getLkm(); i++) {
            Reitti reitti = anna(i);
            if (reitti.getNimi().contains(ehto))
                loytyneet.add(reitti);
        }
        Collections.sort(loytyneet, new Reitti.Vertailija());
        return loytyneet;
    }
    
     /**
     * @param id poistettavan reitin id
     * @return äskön etsitty id
     */
    public int etsiId(int id) { 
         for (int i = 0; i < lkm; i++) 
             if (id == alkiot[i].getId()) return i; 
         return -1; 
     }
    
     /**
     * @param id poistettavan id
     * @return vähän riippuu
     */
    public int poista(int id) { 
         int ind = etsiId(id); 
         if (ind < 0) return 0; 
         lkm--; 
         for (int i = ind; i < lkm; i++) 
             alkiot[i] = alkiot[i + 1]; 
         alkiot[lkm] = null; 
         muutettu = true; 
         return 1; 
     } 

}
