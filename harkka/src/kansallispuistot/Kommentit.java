package kansallispuistot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * @author vilikelo
 * @version 10 Nov 2022
 *
 */
public class Kommentit implements Iterable<Kommentti> {

    private static boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    /** Taulukko harrastuksista */
    private final List<Kommentti> alkiot        = new ArrayList<Kommentti>();

    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {

        Kommentit kommsit = new Kommentit();

        Kommentti pitsi1 = new Kommentti();
        pitsi1.vastaaPitsinNyplays(2);

        Kommentti pitsi2 = new Kommentti();
        pitsi2.vastaaPitsinNyplays(1);

        Kommentti pitsi3 = new Kommentti();
        pitsi3.vastaaPitsinNyplays(2);

        Kommentti pitsi4 = new Kommentti();
        pitsi4.vastaaPitsinNyplays(2);

        kommsit.lisaa(pitsi1);
        kommsit.lisaa(pitsi2);
        kommsit.lisaa(pitsi3);
        kommsit.lisaa(pitsi4);
        try {
            System.out.println(muutettu);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println("============= Kommentit testi =================");

        List<Kommentti> kommentit2 = kommsit.annaKommentit(2);

        for (Kommentti har : kommentit2) {
            System.out.print(har.getJasenNro() + " ");
            har.tulosta(System.out);
        }
        try {
            kommsit.tallenna("data");    
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
        File ftied = new File(hakemisto + "/kommentit" + "/kommentit.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))){
            for (var kom : alkiot) {
                fo.println(kom.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    
    /**
     * Harrastusten alustaminen
     */
    public Kommentit() {
        //
    }

    /**
     * Lisää uuden harrastuksen tietorakenteeseen.  Ottaa harrastuksen omistukseensa.
     * @param har lisättävä harrastus.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kommentti har) {
        alkiot.add(har);
        muutettu = true;
    }
    
    /**
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy,
     * niin lisätään uutena harrastuksena.
     * @param kommentti lisättävän harrastuksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * kommentit kommentit = new kommentit();
     * Kommentti har1 = new Kommentti(), har2 = new Kommentti();
     * har1.rekisteroi(); har2.rekisteroi();
     * kommentit.getLkm() === 0;
     * kommentit.korvaaTaiLisaa(har1); kommentit.getLkm() === 1;
     * kommentit.korvaaTaiLisaa(har2); kommentit.getLkm() === 2;
     * Kommentti har3 = har1.clone();
     * har3.aseta(2,"kkk");
     * Iterator<Kommentti> i2=kommentit.iterator();
     * i2.next() === har1;
     * kommentit.korvaaTaiLisaa(har3); kommentit.getLkm() === 2;
     * i2=kommentit.iterator();
     * Kommentti h = i2.next();
     * h === har3;
     * h == har3 === true;
     * h == har1 === false;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Kommentti kommentti) throws SailoException {
        int id = kommentti.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (alkiot.get(i).getTunnusNro() == id) {
                alkiot.set(i, kommentti);
                muutettu = true;
                return;
            }
        }
        lisaa(kommentti);
    }


    /**
     * Lukee kommentit tiedostosta.
     * @param hakemisto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     *
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  kommentit harrasteet = new kommentit();
     *  Kommentti pitsi21 = new Kommentti(); pitsi21.vastaaPitsinNyplays(2);
     *  Kommentti pitsi11 = new Kommentti(); pitsi11.vastaaPitsinNyplays(1);
     *  Kommentti pitsi22 = new Kommentti(); pitsi22.vastaaPitsinNyplays(2); 
     *  Kommentti pitsi12 = new Kommentti(); pitsi12.vastaaPitsinNyplays(1); 
     *  Kommentti pitsi23 = new Kommentti(); pitsi23.vastaaPitsinNyplays(2); 
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  harrasteet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  harrasteet.lisaa(pitsi21);
     *  harrasteet.lisaa(pitsi11);
     *  harrasteet.lisaa(pitsi22);
     *  harrasteet.lisaa(pitsi12);
     *  harrasteet.lisaa(pitsi23);
     *  harrasteet.tallenna();
     *  harrasteet = new kommentit();
     *  harrasteet.lueTiedostosta(tiedNimi);
     *  Iterator<Kommentti> i = harrasteet.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.next().toString() === pitsi22.toString();
     *  i.next().toString() === pitsi12.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  harrasteet.lisaa(pitsi23);
     *  harrasteet.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/kommentit.dat";
        File ftied = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s.equals("") || s.charAt(0) == ';') continue;
                Kommentti kom = new Kommentti();
                kom.parse(s);
                lisaa(kom);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + nimi + " ei aukea");
        //} catch ( IOException e ) {
        //   throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * Tallentaa jäsenistön tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonPerusNimi);
    }

    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    

    /**
     * Iteraattori kaikkien harrastusten läpikäymiseen
     * @return harrastusiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kommentit harrasteet = new Kommentit();
     *  Kommentti pitsi21 = new Kommentti(2); harrasteet.lisaa(pitsi21);
     *  Kommentti pitsi11 = new Kommentti(1); harrasteet.lisaa(pitsi11);
     *  Kommentti pitsi22 = new Kommentti(2); harrasteet.lisaa(pitsi22);
     *  Kommentti pitsi12 = new Kommentti(1); harrasteet.lisaa(pitsi12);
     *  Kommentti pitsi23 = new Kommentti(2); harrasteet.lisaa(pitsi23);
     * 
     *  Iterator<Kommentti> i2=harrasteet.iterator();
     *  i2.next() === pitsi21;
     *  i2.next() === pitsi11;
     *  i2.next() === pitsi22;
     *  i2.next() === pitsi12;
     *  i2.next() === pitsi23;
     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Kommentti har:harrasteet ) { 
     *    har.getJasenNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kommentti> iterator() {
        return alkiot.iterator();
    }

    /**
     * Haetaan kaikki jäsen kommentit
     * @param tunnusnro jäsenen tunnusnumero jolle harrastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  kommentit harrasteet = new kommentit();
     *  Kommentti pitsi21 = new Kommentti(2); harrasteet.lisaa(pitsi21);
     *  Kommentti pitsi11 = new Kommentti(1); harrasteet.lisaa(pitsi11);
     *  Kommentti pitsi22 = new Kommentti(2); harrasteet.lisaa(pitsi22);
     *  Kommentti pitsi12 = new Kommentti(1); harrasteet.lisaa(pitsi12);
     *  Kommentti pitsi23 = new Kommentti(2); harrasteet.lisaa(pitsi23);
     *  Kommentti pitsi51 = new Kommentti(5); harrasteet.lisaa(pitsi51);
     *  
     *  List<Kommentti> loytyneet;
     *  loytyneet = harrasteet.annaKommentit(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaKommentit(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = harrasteet.annaKommentit(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
     */
    public List<Kommentti> annaKommentit(int tunnusnro) {

        List<Kommentti> loydetyt = new ArrayList<Kommentti>();
        
        for (Kommentti kom : alkiot)
            if (kom.getJasenNro() == tunnusnro) loydetyt.add(kom);
        return loydetyt;
    }
    
    /**

     * Luetaan aikaisemmin annetun nimisestä tiedostosta

     * @throws SailoException jos tulee poikkeus

     */

    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
     /**
     * @param kommentti joka poistetaan
     * @return onko se poistettu
     */
    public boolean poista(Kommentti kommentti) {
         boolean ret = alkiot.remove(kommentti);
         if (ret) muutettu = true;
         return ret;
     }
     /**
     * @param tunnusNro kommentin id
     * @return kuinka mones poistettiin
     */
    public int poistaJasenenHarrastukset(int tunnusNro) {
         int n = 0;
         for (Iterator<Kommentti> it = alkiot.iterator(); it.hasNext();) {
             Kommentti kom = it.next();
             if ( kom.getJasenNro() == tunnusNro ) {
                 it.remove();
                 n++;
             }
         }
         if (n > 0) muutettu = true;
         return n;
     }

}
