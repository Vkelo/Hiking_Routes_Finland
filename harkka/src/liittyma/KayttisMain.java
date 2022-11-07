package liittyma;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import kansallispuistot.Kansallispuistot;


/**
 * @author vilikelo
 * @version 28.9.2022
 * Käyttiksen Main, avaa ohjelman
 */
public class KayttisMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {                      
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KayttisView.fxml"));            
            final Pane root = (Pane)ldr.load();            
            final KayttisController kerhoCtrl = (KayttisController)ldr.getController(); 
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kayttis.css").toExternalForm()); 
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reitit"); 
            
            Kansallispuistot kansallispuisto = new Kansallispuistot();
            kerhoCtrl.setKansallispuisto(kansallispuisto);
            
            //Platform.setImplicitExit(false); // jos tämän laittaa, pitää itse sulkea            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !kerhoCtrl.voikoSulkea() ) event.consume(); // korjaa nimi
            });
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}