package kansallispuistot;

//import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * @author vilikelo
 * @version 25 Oct 2022
 *
 */
public class Reitti {
    
    private int id;
    private String nimi = "";
    private String aloituspaikka = "";
    private String lopetuspaikka = "";
    private int pituus;
    private String vesistoja = ""; // VAIKO BOOLEAN???
    private String vaativuus = "";
    private String vesipisteita = ""; // VAIKO BOOLEAN???
    private int maakunnanid;
    
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
     * Täyttää tiedot olioon
     */
    public void taytaKarhunkierrosTiedoilla() {
        nimi = "Karhunkierros";
        aloituspaikka = "Tuomiojärvi";
        lopetuspaikka = "Ruka";
        pituus = 82;
        vesistoja = "Kyllä"; 
        vaativuus = "Vaikea";
        vesipisteita = "Kyllä: 1"; 
        maakunnanid = 1;
    }
    
    /**
     * tulostaa pyydetyt asiat
     * @param out on tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", id) + " " + nimi);
        out.println(aloituspaikka + " " + lopetuspaikka+ " " + pituus + "km");
        out.println(vesistoja + " " + vaativuus+ " " + vesipisteita);  
        out.println( "maakunta: " + maakunnanid);  
        out.println(" ");
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
}
