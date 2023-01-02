package liittyma;

import static liittyma.TietueDialogController.getFieldId; 
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import kansallispuistot.Kansallispuistot;
import kansallispuistot.Kommentti;
import kansallispuistot.Reitti;
import kansallispuistot.SailoException;
import javafx.scene.input.KeyCode;




/**
 * @author vilikelo
 * @version 11 Oct 2022
 *
 */
public class KayttisController implements Initializable {
    
    @FXML private TextField hakuehto; 
    //@FXML private Label labelVirhe; käytetään ehkä joskus
    @FXML private ListChooser<Reitti> chooserReitit;
    @FXML private ScrollPane panelReitti;
    @FXML private GridPane gridReitti;
    @FXML StringGrid<Kommentti> tableKommentit;  

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    /**
     * Käsitellään uuden jäsenen lisääminen
     */
    @FXML private void handleUusiReitti() {
        uusiReitti();
    }
    
    /**
     * Käsitellään kommentointi
     */
    @FXML private void handleKommentointi() {
        uusiKommentti();
    }
    
    /**
     * Käsitellään muokkaaminen
     */
    @FXML private void handleMuokkaus() {
        muokkaa(kentta);
    }
    
    /**
     * näytetään reitin poistamiseen liittyvä dialogi
     */
    @FXML private void handlePoistaReitti() {
        poistaReitti();
    }
    
    /**
     * Käsitellään muokkaaminen
     */
    @FXML private void handleKommentinPoisto() {
        poistaKommentti();
    }

    
    @FXML private void handleHakuehto() {
        hae(0);            
    }        
    
    /**
     * Käsitellään avaaminen
     */
    @FXML private void handleAvaa() {
        avaa();
    }
    
    /**
     * mennään aliohjelmaan, joka avaa suunnitelman
     */
    @FXML private void handleApua() {
        avustus();
    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(KayttisController.class.getResource("InfoaView.fxml"), "Reitti", null, "");
    }
    
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea()); 
    } 
    
    /**
     * Käsitellään tallennuskäsky
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    /**
     * Käsitellään lopetuskäsky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    //=======================================================================
    
    private TextField[] edits;   
    private int kentta = 0;    
    private static Kommentti apukommentti = new Kommentti(); 
    //private static Reitti apureitti = new Reitti();
    private Reitti reittiKohdalla;
    private Kansallispuistot kansallispuisto;
    
    
    /**
     * tyhjentää reittilistan ohjelman avatessa
     */
    private void alusta(){
        chooserReitit.clear();
        chooserReitit.addSelectionListener(e -> naytaReitti());
        
        edits = TietueDialogController.luoKentat(gridReitti, new Reitti());
        for (TextField edit : edits)
            if ( edit != null) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(), 0)); 
                }); 
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
                edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);}); 
        }
        int eka = apukommentti.ekaKentta(); 
        int lkm = apukommentti.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apukommentti.getKysymys(k); 
        tableKommentit.initTable(headings); 
        tableKommentit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableKommentit.setEditable(false); 
        tableKommentit.setPlaceholder(new Label("Ei vielä kommentteja")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableKommentit.setColumnSortOrderNumber(1); 
        tableKommentit.setColumnSortOrderNumber(2); 
        tableKommentit.setColumnWidth(1, 60); 
        tableKommentit.setColumnWidth(2, 60);     
        tableKommentit.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaKommentti(); } );
        tableKommentit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKommentti();}); 
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        try {
            kansallispuisto.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * Avataan nettiselain ja sieltä suunnitelma
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2022s/ht/vkelo");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = "data";
        lueTiedosto(uusinimi);
        return true;
    }
    
    /**
     * @param nimi tiedoston nimi 
     * @return virhe
     */
    protected String lueTiedosto(String nimi) {
        try {
            kansallispuisto.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }


    
    /**
     * @param kansallispuisto Asetataan kansallispuisto, jotta sen voi noutaa
     */
    public void setKansallispuisto(Kansallispuistot kansallispuisto) {
        this.kansallispuisto = kansallispuisto;
    }
    
    
    /**
     * 
     * lisätään uusi reitti tuonne ohjelmaan käyttöliittymässä
     */
    protected void uusiReitti(){
        try {
            Reitti uusi = new Reitti();
            uusi = TietueDialogController.kysyTietue(null, uusi, 1);
            if (uusi == null)return;
            uusi.rekisteroi();
            kansallispuisto.lisaa(uusi);
            hae(uusi.getId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }        
    }
    
    /**
     * Uuden kommentin lisääminen, lisaa metodia lisäämään uuden kommentin
     * Rekisteroi kutsutaan kommenttien puolelta
     */
    private void uusiKommentti() {
        if (reittiKohdalla == null) return;
        try {
            Kommentti uusi = new Kommentti(reittiKohdalla.getId());
            uusi = TietueDialogController.kysyTietue(null, uusi, 0);
            if ( uusi == null ) return;
            uusi.rekisteroi();
            kansallispuisto.lisaa(uusi);
            naytaKommentit(reittiKohdalla); 
            tableKommentit.selectRow(1000);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui" + e.getMessage());
        }      
    }
    
    
    /**
     * Kommentin muokkaus, etsitään kommentin tietue ja muokataan sieltä haluttua kohtaa
     * 
     */
    private void muokkaaKommentti() {
        int r = tableKommentit.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Kommentti kom = tableKommentit.getObject();
        if ( kom == null ) return;
        int k = tableKommentit.getColumnNr()+kom.ekaKentta();
        try {
            kom = TietueDialogController.kysyTietue(null, kom.clone(), k);
            if ( kom == null ) return;
            kansallispuisto.korvaaTaiLisaa(kom); 
            naytaKommentit(reittiKohdalla); 
            tableKommentit.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    
 /*
  * Hae toiminta, jota käytetään monessa eri tilanteessa
  * Eniten käytössä reittien etsimisen kanssa 
  */
    private void hae(final int rnr) {
        int rnro = rnr;
        if (rnro == 0) {
            Reitti kohdalla = chooserReitit.getSelectedObject();
            if (kohdalla != null) rnro = kohdalla.getId();
        }
        chooserReitit.clear();
        String ehto = hakuehto.getText();
        Collection <Reitti> reitit = kansallispuisto.etsi(ehto);
        int index = 0;
        int ci = 0;
        for (Reitti reitti:reitit) {
            if (reitti.getId() == rnro) index = ci; 
            chooserReitit.add("" + reitti.getNimi(), reitti);
            ci++;
        }
        chooserReitit.setSelectedIndex(index);
    }
        
    /**
     *  Näyttää siinä tilanteessa valitun reitin
     */
    protected void naytaReitti() {
        reittiKohdalla = chooserReitit.getSelectedObject();
        if (reittiKohdalla == null) return;
        
        TietueDialogController.naytaTietue(edits, reittiKohdalla); 
        naytaKommentit(reittiKohdalla);
     }
    
    
    /**
     * kutsuu näytäkommentti metodia
     * Näyttää valitun reitin kommentit
     * @param reitti
     */
    private void naytaKommentit(Reitti reitti) {
        tableKommentit.clear();
        if (reitti == null) return;
        List<Kommentti> kommentit = kansallispuisto.annaKommentit(reitti);
        if(kommentit.size() == 0) return;
        for (Kommentti kom: kommentit) {
            naytaKommentti(kom);
        }
    }
    
    /**
     * näyttää yksittäisen kommentin naytakommentit metodille
     * @param kom
     */
    private void naytaKommentti(Kommentti kom) {
        int kenttia = kom.getKenttia(); 
        String[] rivi = new String[kenttia-kom.ekaKentta()]; 
        for (int i=0, k=kom.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = kom.anna(k); 
        tableKommentit.add(kom,rivi);

    }
    
    /**
     * yksittäisen reitin muokkaus
     * kutsuu kansallispuistoa tekemään likaisen työn
     * @param k
     */
    private void muokkaa(int k) {
        if (reittiKohdalla == null) return;
        try {
            Reitti reitti = TietueDialogController.kysyTietue(null, reittiKohdalla.clone(), k); 
            if (reitti == null) return;
            kansallispuisto.korvaaTaiLisaa(reitti);
            hae(reitti.getId());     
        } catch (CloneNotSupportedException | SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    /**
     * Tulostaa kaikki reitit, 
     * Mahdollistaa myös yksittäisten reittien tulostamisen jos ne etsitään vasemmalle listaan
     * @param os
     * @param reitti
     */
    private void tulosta (PrintStream os, final Reitti reitti){
        os.println("----------------------------------");
        reitti.tulosta(os);
        os.println("----------------------------------");
        List<Kommentti> kommentit  = kansallispuisto.annaKommentit(reitti);
        for (Kommentti kom: kommentit)
            kom.tulosta(os);
        os.println("----------------------------------");
    }
    
    @SuppressWarnings("unused")
    private void naytaVirhe (String string) {
        //
    }
    
    
    /**
     * Deletes comment, gives all the dirty work to kansallispuistot class
     * 
     */
     private void poistaKommentti() {
         int rivi = tableKommentit.getRowNr();
         if ( rivi < 0 ) return;
         Kommentti kommentti = tableKommentit.getObject();
         if ( kommentti == null ) return;
         kansallispuisto.poistaKommentti(kommentti);
         naytaKommentit(reittiKohdalla);
         int harrastuksia = tableKommentit.getItems().size(); 
         if ( rivi >= harrastuksia ) rivi = harrastuksia -1;
         tableKommentit.getFocusModel().focus(rivi);
         tableKommentit.getSelectionModel().select(rivi);
     }
     
    /**
     * Poistaa reitin, joka on sillä hetkellä valittuna
     * 
     */
     private void poistaReitti() {
         Reitti reitti = reittiKohdalla;
         if ( reitti == null ) return;
         if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko jäsen: " + reitti.getNimi(), "Kyllä", "Ei") )
             return;
         kansallispuisto.poista(reitti);
         int index = chooserReitit.getSelectedIndex();
         hae(0);
         chooserReitit.setSelectedIndex(index);
     }
     
     /**
      * Tulostaa listassa olevat jäsenet tekstialueeseen
      * @param text alue johon tulostetaan
      */
     public void tulostaValitut(TextArea text) {
         try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
             os.println("Tulostetaan kaikki reitit");
             for (Reitti reitti: chooserReitit.getObjects()) { 
                 tulosta(os, reitti);
                 os.println("\r\n");
             }
         }
     }
       
}

