package kansallispuistot;

//import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import kanta.Tietue;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author vilikelo
 * @version 25 Oct 2022
 *
 */
public class Reitti implements Cloneable, Tietue {
    
    private int id;
    private String nimi = "";
    private String aloituspaikka = "";
    private String lopetuspaikka = "";
    private String pituus;
    private String vesistoja = ""; // VAIKO BOOLEAN???
    private String vaativuus = "";
    private String vesipisteita = ""; // VAIKO BOOLEAN???
    private String maakunta = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[]args) {
        Reitti karhunkierros1 = new Reitti ();
        Reitti karhunkierros2 = new Reitti ();
        
        karhunkierros1.rekisteroi();
        karhunkierros2.rekisteroi();
        
        karhunkierros1.tulosta(System.out);
        
        karhunkierros1.taytaKarhunkierrosTiedoilla();
        karhunkierros2.taytaKarhunkierrosTiedoilla();
        
        karhunkierros1.tulosta(System.out);
        karhunkierros2.tulosta(System.out);
    }
    
    /**
     * @return kuinka monta kenttää on
     */
    @Override
    public int getKenttia() {
        return 9;
    }
    
    /**
     * @return ensimmäinen kenttä jonka halutaan toimivan
     */
    @Override
    public int ekaKentta() {
        return 1;
    }
    
    /**
     * alustetaan reitti tyhjäksi
     */
    public Reitti() {
        // Jossain vaiheessa tulee varmaan jotain
    }
    
    /**
     * antaa k: kentän sisällön 
     * @param k monesko kenttä
     * @return sisältö stringinä
     */
    @Override
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + id;
        case 1: return "" + nimi;
        case 2: return "" + aloituspaikka;
        case 3: return "" + lopetuspaikka;
        case 4: return "" + pituus;
        case 5: return "" + vesistoja;
        case 6: return "" + vaativuus;
        case 7: return "" + vesipisteita;
        case 8: return "" + maakunta;
        default: return "stupido";
        }
    }
    
    /**
     * antaa k: kentän sisällön 
     * @param k monesko kenttä
     * @return sisältö stringinä
     */
    @Override
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "id";
        case 1: return "nimi";
        case 2: return "aloituspaikka";
        case 3: return "lopetuspaikka";
        case 4: return "pituus";
        case 5: return "vesistoja";
        case 6: return "vaativuus";
        case 7: return "vesipisteita";
        case 8: return "maakunta";
        default: return "stupido";
        }
    }
    
    /**
     * Täyttää tiedot olioon
     */
    public void taytaKarhunkierrosTiedoilla() {
        nimi = "Karhunkierros";
        aloituspaikka = "Tuomiojärvi";
        lopetuspaikka = "Ruka";
        pituus = "82";
        vesistoja = "Kyllä"; 
        vaativuus = "Vaikea";
        vesipisteita = "Kyllä: 1"; 
        maakunta = "1";
    }
    
    
    /**
    * 
    * @example
    * <pre name="test">
    *   Reitti reitti = new Reitti();
    *   reitti.parse("   1  |  Karhunkierros   | Tuomiojärvi");
    *   reitti.toString().startsWith("1|Karhunkierros|Tuomiojärvi|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
    * </pre>  
    */
   @Override
   public String toString() {
       return "" +
               getId() + "|" +
               nimi + "|" +
               aloituspaikka + "|" +
               lopetuspaikka + "|" +
               pituus + "|" +
               vesistoja + "|" +
               vaativuus + "|" +
               vesipisteita + "|" +
               maakunta;
   }
   
   /**
 * @param rivi ominaisuus
 */
public void parse(String rivi) {
       var sb = new StringBuilder(rivi);

       setId(Mjonot.erota(sb, '|', getId()));
       nimi = Mjonot.erota(sb, '|', nimi);
       aloituspaikka = Mjonot.erota(sb, '|', aloituspaikka);
       lopetuspaikka = Mjonot.erota(sb, '|', lopetuspaikka);
       pituus = Mjonot.erota(sb, '|', pituus);
       vesistoja = Mjonot.erota(sb, '|', vesistoja);
       vaativuus = Mjonot.erota(sb, '|', vaativuus);
       vesipisteita = Mjonot.erota(sb, '|', vesipisteita);
       maakunta = Mjonot.erota(sb, '|', maakunta);
   }
   
   private void setId(int nr) {
       id = nr;
       if (id >= seuraavaNro) seuraavaNro = id + 1;
   }

    /**
     * tulostaa pyydetyt asiat
     * @param out on tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", id) + " " + nimi);
        out.println(aloituspaikka + " " + lopetuspaikka+ " " + pituus + "km");
        out.println(vesistoja + " " + vaativuus+ " " + vesipisteita);  
        out.println( "maakunta: " + maakunta);  
        out.println(" ");
    }
    
    @Override
    public Reitti clone() throws CloneNotSupportedException{
        Reitti uusi;
        uusi = (Reitti) super.clone();
        return uusi;
    }
    
    /**
     * @param os on output
     * MUKAAN JOS TARVITSEE
     */
    /*public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }*/
    
    /**
     * rekisteroi käyttäjän idnumeroksi oikean numeron
     * @return uusi idnumero
     * @example
     * <pre name="test">
     * Reitti reitti1 = new Reitti();
     * reitti1.getId() === 0;
     * reitti1.rekisteroi();
     * Reitti reitti2 = new Reitti();
     * reitti2.rekisteroi();
     * int n1 = reitti1.getId();
     * int n2 = reitti2.getId();
     * n2 === n1+1;
     * </pre>
     */
    public int rekisteroi() {
        this.id = seuraavaNro;
        seuraavaNro++;
        return this.id;
    }
    
    /**
     * @return reitin tunnuksen
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return palauttaa jäsenen nimen
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * @param k kenttä
     * @param jono mikä laitetaan sinne
     * @return se jono
     */
    @Override
    public String aseta (int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0: 
            setId(Mjonot.erota(sb, '§', getId()));
            return null;
        case 1: 
            nimi = tjono;
            return null;
        case 2:  
            aloituspaikka = tjono;
            return null;
        case 3:
            lopetuspaikka = tjono;
            return null;
        case 4:
            pituus = tjono;
            return null;            
        case 5:
            vesistoja = tjono;
            return null;
        case 6:
            vaativuus = tjono;
            return null;
        case 7:
            vesipisteita = tjono;
            return null;
        case 8:
            maakunta = tjono;
            return null;
        default:
            return "typerys";
        }
    }
    
    /**
     * @author vilikelo
     * @version 10 Dec 2022
     * Vertailee reittejä
     */
    public static class Vertailija implements Comparator<Reitti>{

        @Override
        public int compare(Reitti reitti1, Reitti reitti2) {            
            return reitti1.getNimi().compareTo(reitti2.getNimi());
        }        
    }
}
