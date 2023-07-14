package com.mycompany.sokoban;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private static Scene scene;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 1366, 738);
        stage.setScene(scene);
        stage.show();

        playSoundLoop("sound.mp3");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void playSoundLoop(String soundFile) {
        URL resource = getClass().getResource("/" + soundFile);
        if (resource != null) {
            Media media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else {
            System.out.println("No se pudo encontrar el archivo de sonido: " + soundFile);
        }
    }

    @Override
    public void stop() throws Exception {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
