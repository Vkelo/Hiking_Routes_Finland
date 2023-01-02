package kansallispuistot;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author vilikelo
 * @version 26 Oct 2022
 *
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import Kansallispuistot.SailoException;
 *  private Kansalllispuistot kansalllispuisto;
 *  private Reitti aku1;
 *  private Reitti aku2;
 *  private int jid1;
 *  private int jid2;
 *  private Kommentti pitsi21;
 *  private Kommentti pitsi11;
 *  private Kommentti pitsi22; 
 *  private Kommentti pitsi12; 
 *  private Kommentti pitsi23;
 *  
 *  @SuppressWarnings("javadoc")
 *  public void alustaKerho() {
 *    kansalllispuisto = new Kansalllispuistot();
 *    aku1 = new Reitti(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
 *    aku2 = new Reitti(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
 *    jid1 = aku1.getTunnusNro();
 *    jid2 = aku2.getTunnusNro();
 *    pitsi21 = new Kommentti(jid2); pitsi21.vastaaPitsinNyplays(jid2);
 *    pitsi11 = new Kommentti(jid1); pitsi11.vastaaPitsinNyplays(jid1);
 *    pitsi22 = new Kommentti(jid2); pitsi22.vastaaPitsinNyplays(jid2); 
 *    pitsi12 = new Kommentti(jid1); pitsi12.vastaaPitsinNyplays(jid1); 
 *    pitsi23 = new Kommentti(jid2); pitsi23.vastaaPitsinNyplays(jid2);
 *    try {
 *    kansalllispuisto.lisaa(aku1);
 *    kansalllispuisto.lisaa(aku2);
 *    kansalllispuisto.lisaa(pitsi21);
 *    kansalllispuisto.lisaa(pitsi11);
 *    kansalllispuisto.lisaa(pitsi22);
 *    kansalllispuisto.lisaa(pitsi12);
 *    kansalllispuisto.lisaa(pitsi23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Kansallispuistot {
    
    private Reitit reitit = new Reitit();
    private Kommentit kommentit = new Kommentit(); 
    
    private String hakemisto = "data";
    
    /**
     * @param args ei käytössä
     */
    public static void main(String [] args)  {
        
        Kansallispuistot kansallispuisto = new Kansallispuistot();
        
        try {
            Reitti karhunkierros1 = new Reitti ();
            Reitti karhunkierros2 = new Reitti ();
            karhunkierros1.rekisteroi();
            karhunkierros2.rekisteroi();
            karhunkierros1.taytaKarhunkierrosTiedoilla();
            karhunkierros2.taytaKarhunkierrosTiedoilla();
            kansallispuisto.lisaa(karhunkierros1);
            kansallispuisto.lisaa(karhunkierros2);  
            int id1 = karhunkierros1.getId();
            int id2 = karhunkierros2.getId();
            Kommentti pitsi11 = new Kommentti(id1);
            pitsi11.vastaaPitsinNyplays(id1);
            kansallispuisto.lisaa(pitsi11);
            Kommentti pitsi12 = new Kommentti(id1);
            pitsi12.vastaaPitsinNyplays(id1);
            kansallispuisto.lisaa(pitsi12);
            Kommentti pitsi21 = new Kommentti(id2);
            pitsi21.vastaaPitsinNyplays(id2);
            kansallispuisto.lisaa(pitsi21);
            Kommentti pitsi22 = new Kommentti(id2);
            pitsi22.vastaaPitsinNyplays(id2);
            kansallispuisto.lisaa(pitsi22);
            Kommentti pitsi23 = new Kommentti(id2);
            pitsi23.vastaaPitsinNyplays(id2);
            kansallispuisto.lisaa(pitsi23);           
            System.out.println("============= Kerhon testi =================");
            Collection <Reitti> reitit = kansallispuisto.etsi("", -1);
            int i = 0;
            for (Reitti reitti : reitit) {
                System.out.println("Jäsen paikassa:" + i);
                reitti.tulosta(System.out);
                System.out.println();
                List<Kommentti> loytyneet = kansallispuisto.annaKommentit(reitti);
                for (Kommentti kommentti : loytyneet)
                    kommentti.tulosta(System.out);
                i++;
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * @param hakuehto millä tavalla etsitään
     * @param k id
     * @return etsittävän
     * @throws SailoException jo menee väärin
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alustaKerho();
     *   Reitti jasen3 = new Reitti(); jasen3.rekisteroi();
     *   jasen3.aseta(1,"Susi Sepe");
     *   kansallispuisto.lisaa(jasen3);
     *   Collection<Reitti> loytyneet = kansallispuisto.etsi("*Susi*",1);
     *   loytyneet.size() === 1;
     *   Iterator<Reitti> it = loytyneet.iterator();
     *   it.next() == jasen3 === true; 
     * </pre>
     */
    public Collection<Reitti> etsi(String hakuehto, int k) throws SailoException { 
        return reitit.etsi(hakuehto, k); 
    } 

    
    /**
     * Tallenttaa kerhon tiedot tiedostoon.  
     * Vaikka jäsenten tallettamien epäonistuisi, niin yritetään silti tallettaa
     * harrastuksia ennen poikkeuksen heittämistä.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            reitit.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            kommentit.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

    
    /**
     * @param reitti mikä viedään lisättävksi
     * @throws SailoException heittää poikkeuksen
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKerho();
     *  kansallispuisto.etsi("*",0).size() === 2;
     *  kansallispuisto.lisaa(aku1);
     *  kansallispuisto.etsi("*",0).size() === 3;
     */
    public void lisaa(Reitti reitti) throws SailoException {
        reitit.lisaa(reitti);            
    }
    
    /**
     * @param kom lisätään uusi kommentti
     * @throws SailoException jos menee pieleen
     */
    public void lisaa(Kommentti kom) throws SailoException {
        kommentit.lisaa(kom);            
    }
    
    /**
     * @param reitti mitä tarkistellaan
     * @throws SailoException jo kusee
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKerho();
     *  kansallispuisto.etsi("*",0).size() === 2;
     *  kansallispuisto.korvaaTaiLisaa(aku1);
     *  kansallispuisto.etsi("*",0).size() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Reitti reitti) throws SailoException {
        reitit.korvaaTaiLisaa(reitti);        
    }
    
    /** 
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy, 
     * niin lisätään uutena harrastuksena. 
     * @param kommentti lisärtävän harrastuksen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Kommentti kommentti) throws SailoException { 
        kommentit.korvaaTaiLisaa(kommentti); 
    }
    
    
    /**
     * @param reitti reitti
     * @return tunnusnro
     *      * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  alustaKerho();
     *  Reitti aku3 = new Reitti();
     *  aku3.rekisteroi();
     *  kansallispuisto.lisaa(aku3);
     *  
     *  List<Kommentti> loytyneet;
     *  loytyneet = kansallispuisto.annaKommentit(aku3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kansallispuisto.annaKommentit(aku1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = kansallispuisto.annaKommentit(aku2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == pitsi21 === true;
     * </pre>
     */
    public List<Kommentti> annaKommentit(Reitti reitti){
        return kommentit.annaKommentit(reitti.getId());
    }
    
    /**
     * @param nimi reitin nimi
     * @throws SailoException jos kusee
     */
    public void lueTiedostosta(String nimi) throws SailoException{
        reitit = new Reitit(); 
        kommentit = new Kommentit();
        setTiedosto(nimi);
        reitit.lueTiedostosta();
        kommentit.lueTiedostosta();
    }
    
    /**
     * palauttaa listan vastaavista rieteistä
     * @param ehto hakuehto
     * @return lista jäsenistä
     */
    public Collection<Reitti> etsi(String ehto) {
        return reitit.etsi(ehto);
    }
    
    /**
     * @param nimi tiedoston nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        reitit.setTiedostonPerusNimi(hakemistonNimi + "reitit");
        kommentit.setTiedostonPerusNimi(hakemistonNimi + "kommentit");
    }
    
    /**
     * @throws SailoException sailo
     */
    public void talleta() throws SailoException{
        //reitit.talleta();
        kommentit.talleta();
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
    
     /**
     * @param reitti mikä halutaan poistaa
     * @return uusi arvo
     */
    public int poista(Reitti reitti) {
         if ( reitti == null ) return 0;
         int ret = reitit.poista(reitti.getId()); 
         kommentit.poistaJasenenHarrastukset(reitti.getId()); 
         return ret; 
     }
     /**
     * @param kommentti mikä poistetaan
     */
    public void poistaKommentti(Kommentti kommentti) { 
         kommentit.poista(kommentti);
     }
}                                                  