package kansallispuistot;

import java.io.*;
import kanta.Tietue;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * @author vilikelo
 * @version 10 Nov 2022
 *
 */
public class Kommentti implements Cloneable, Tietue {

    private int tunnusNro;
    private int jasenNro;
    private String kommentti = "";

    private static int seuraavaNro = 1;

    /**
     * Testiohjelma Harrastukselle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kommentti kom = new Kommentti();
        kom.vastaaPitsinNyplays(2);
        kom.tulosta(System.out);
    }
    
    /**
     * @return harrastukse kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 3;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "id";
            case 1:
                return "jäsenId";
            case 2:
                return "kommentti";
            default:
                return "???";
        }
    }


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "10";   
     *   har.anna(2) === "Kalastus";   
     *   har.anna(3) === "1949";   
     *   har.anna(4) === "22";   
     *   
     * </pre>
     */
    @Override
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + jasenNro;
            case 2:
                return kommentti;
            default:
                return "???";
        }
    }


    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "Aloitusvuosi väärin jono = \"kissa\"";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "Viikkotunnit väärin jono = \"kissa\"";
     *   har.aseta(4,"20")    === null;
     *   
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                jasenNro = Mjonot.erota(sb, '$', jasenNro);
                return null;
            case 2:
                kommentti = st;
                return null;
            default:
                return "Väärä kentän indeksi";
        }
    }


    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Harrastus kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     * </pre>
     */
    @Override
    public Kommentti clone() throws CloneNotSupportedException { 
        return (Kommentti)super.clone();
    }

    
    /**
     * @param rivi ominaisuus
     */
    public void parse(String rivi) {
           var sb = new StringBuilder(rivi);
           for (int k = 0; k < getKenttia(); k++)
               aseta(k, Mjonot.erota(sb, '|'));

       }
    
    
    /**
     * Palauttaa harrastuksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kommentti tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kommentti kommentti = new Kommentti();
     *   kommentti.parse("   2   |  10  |   Kalastus ");
     *   kommentti.toString()    === "2|10|Kalastus";
     * </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
     }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }

    
    /**
     * Alustetaan kommentti
     */
    public Kommentti() {
        // 
    }

    /**
     * Alustetaan tietyn jäsenen kommentti.  
     * @param jasenNro jäsenen viitenumero 
     */
    public Kommentti(int jasenNro) {
        this.jasenNro = jasenNro;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Harrastukselle.
     * Aloitusvuosi arvotaan, jotta kahdella harrastuksella ei olisi
     * samoja tietoja.
     * @param nro viite henkilöön, jonka harrastuksesta on kyse
     */
    public void vastaaPitsinNyplays(int nro) {
        jasenNro = nro;
        kommentti = "Jeppis";
    }

    /**
     * Tulostetaan harrastuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(kommentti);
    }

    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa harrastukselle seuraavan rekisterinumeron.
     * @return harrastuksen uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Kommentti pitsi1 = new Kommentti();
     *   pitsi1.getId() === 0;
     *   pitsi1.rekisteroi();
     *   Kommentti pitsi2 = new Kommentti();
     *   pitsi2.rekisteroi();
     *   int n1 = pitsi1.getId();
     *   int n2 = pitsi2.getId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    /**
     * Palautetaan harrastuksen oma id
     * @return harrastuksen id
     */

    public int getTunnusNro() {
        return tunnusNro;
    }

    /**
     * Palautetaan mille jäsenelle kommentti kuuluu
     * @return jäsenen id
     */

    public int getJasenNro() {
        return jasenNro;
    }

    /**
     * @return palauttaa kommentin
     */
    public String getNimi() {
        return kommentti;
    }


}
