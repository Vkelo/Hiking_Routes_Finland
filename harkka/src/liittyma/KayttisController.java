package liittyma;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
//import javafx.scene.control.Label;
import kansallispuistot.Kansallispuistot;
import kansallispuistot.Reitti;
import kansallispuistot.SailoException;




/**
 * @author vilikelo
 * @version 11 Oct 2022
 *
 */
public class KayttisController implements Initializable {
    
    //@FXML private Label labelVirhe; käytetään ehkä joskus
    @FXML private ListChooser<Reitti> chooserReitit;
    @FXML private ScrollPane panelReitti;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    /**
     * Käsitellään uuden jäsenen lisääminen
     */
    @FXML private void handleUusiJasen() {
        ModalController.showModal(KayttisController.class.getResource("ReitinLisaysDialogView.fxml"),"reitti", null, "" );
        uusiReitti();
    }
    
    /**
     * Käsitellään muokkaaminen
     */
    @FXML private void handleMuokkaus() {
        ModalController.showModal(KayttisController.class.getResource("MuokkaaDialogView.fxml"),"muokkaus", null, "" );
    }
    
    /**
     * Käsitellään kommentointi
     */
    @FXML private void handleKommentointi() {
        ModalController.showModal(KayttisController.class.getResource("KommentoiDialogView.fxml"),"kommentti", null, "" );
    }
    
    /**
     * Käsitellään tulostaminen
     */
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    /**
     * Käsitellään avaaminen
     */
    @FXML private void handleAvaa() {
        tietoja();
    }
    
    /**
     * mennään aliohjelmaan, joka avaa suunnitelman
     */
    @FXML private void handleApua() {
        avustus();
    }
    
    /**
     * näytetään reitin poistamiseen liittyvä dialogi
     */
    @FXML private void handlePoistaReitti() {
        Dialogs.showQuestionDialog("poisto?", "Poistetaanko reitti: Karhunkierros", "Kyllä", "Ei");
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
    
    private Kansallispuistot kansallispuisto;
    private TextArea areaReitti = new TextArea(); //TODO: poista lopuksi
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * näytetään avauskuva
     */
    private void tietoja() {
        ModalController.showModal(KayttisController.class.getResource("InfoaView.fxml"),"info", null, "" );
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
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
     * @param kansallispuisto efv
     */
    public void setKansallispuisto(Kansallispuistot kansallispuisto) {
        this.kansallispuisto = kansallispuisto;
    }
    
    /**
     * 
     * lisätään uusi reitti tuonne ohjelmaan käyttöliittymässä
     */
    private void uusiReitti(){
        Reitti uusi = new Reitti();
        uusi.rekisteroi();
        uusi.taytaKarhunkierrosTiedoilla();
        try {
            kansallispuisto.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        hae(uusi.getId());
    }
    
    private void hae(int rnro) {
        chooserReitit.clear();
        
        int index = 0;
        for (int i=0; i<kansallispuisto.getKansallispuistoja();i++) {
            Reitti reitti = kansallispuisto.annaReitti(i);
            if (reitti.getId() == rnro) index = i; 
            chooserReitit.add("" + reitti.getNimi(), reitti);
        }
        chooserReitit.setSelectedIndex(index);
    }
    
    /**
     * tyhjentää reittilistan ohjelman avatessa
     */
    private void alusta(){
        panelReitti.setContent(areaReitti);
        areaReitti.setFont(new Font("Courier New", 12));
        panelReitti.setFitToHeight(true);
        chooserReitit.clear();
        chooserReitit.addSelectionListener(e -> naytaReitti());
    }
    
    /**
     * 
     */
    protected void naytaReitti() {
        Reitti reittiKohdalla = chooserReitit.getSelectedObject();
        if (reittiKohdalla == null) return;
        areaReitti.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaReitti)) {
            reittiKohdalla.tulosta(os);
        }
     }
        
}

